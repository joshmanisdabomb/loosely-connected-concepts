package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.joshmanisdabomb.lcc.gui.ComputerScreen;
import com.joshmanisdabomb.lcc.network.ComputerPowerPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConsoleOperatingSystem extends LinedOperatingSystem {

    @OnlyIn(Dist.CLIENT)
    private long lastTypeTime = 0;

    public ConsoleOperatingSystem(ComputingSession cs) {
        super(cs, 9);
    }

    @Override
    public void boot() {
        super.boot();
    }

    @Override
    public void wake() {
        this.readOutput(cs.getState());
    }

    @Override
    public Type getType() {
        return Type.CONSOLE;
    }

    private String getInterpreter(TerminalSession ts) {
        return ts.getState(cs).getString("interpreter");
    }

    private void setInterpreter(TerminalSession ts, String text) {
        ts.getState(cs).putString("interpreter", text);
    }

    private void handleCommand(String interpreter, TerminalSession ts) {
        String[] commandAndArgs = interpreter.split(" ", 2);
        String[] args = commandAndArgs.length == 2 ? commandAndArgs[1].split(" ") : new String[0];
        Command c = Command.getFromAlias(commandAndArgs[0]);
        if (c == null) {
            this.printt("computing.lcc.console.unknown");
        } else {
            c.handler.accept(this, ts, args);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        super.render(ts, partialTicks, x, y);
        String interpreter = this.getInterpreter(ts);
        Minecraft.getInstance().fontRenderer.drawString("> " + interpreter + ((System.currentTimeMillis() - lastTypeTime) % 1000 <= 500 ? "_" : " "), x + 5, y + 103, 0xD5D5D5);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean keyPressed(TerminalSession ts, int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        String interpreter = this.getInterpreter(ts);
        switch (p_keyPressed_1_) {
            case 259: //backspace
                this.setInterpreter(ts, interpreter.substring(0, Math.max(interpreter.length() - 1, 0)));
                ts.sendState();
                break;
            case 257: //enter
                this.setInterpreter(ts, "");
                ts.sendState();
                this.print("> " + interpreter);
                this.handleCommand(interpreter, ts);
                this.writeOutput(cs.getState());
                break;
        }
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean charTyped(TerminalSession ts, char p_charTyped_1_, int p_charTyped_2_) {
        String interpreter = this.getInterpreter(ts);
        this.setInterpreter(ts, interpreter + p_charTyped_1_);
        lastTypeTime = System.currentTimeMillis();
        ts.sendState();
        return true;
    }

    public enum Command {
        HELP((cos, ts, args) -> {
            if (args.length == 0) {
                cos.printt("computing.lcc.console.help.available", Arrays.stream(Command.values()).map(Command::getPrimaryAlias).collect(Collectors.joining(", ")));
            } else {
                Command c = Command.getFromAlias(args[0]);
                if (c == null) {
                    cos.printt("computing.lcc.console.unknown");
                } else {
                    c.print(cos);
                }
            }
        }),
        CLEAR((cos, ts, args) -> {
            cos.clear();
        }),
        DISKS((cos, ts, args) -> {
            cos.cs.computer.getNetworkDisks().forEach(d -> cos.printt(d.toString()));
        }),
        USE((cos, ts, args) -> {}),
        LS((cos, ts, args) -> {}),
        CD((cos, ts, args) -> {}),
        MKDIR((cos, ts, args) -> {}),
        HOLD((cos, ts, args) -> {}),
        REBOOT((cos, ts, args) -> {
            cos.cs.computer.session = null;
            cos.cs.computer.session = cos.cs.computer.getSession(ComputingSession::boot);
            cos.cs.sendState();
        }),
        SHUTDOWN((cos, ts, args) -> {
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new ComputerPowerPacket(cos.cs.computer.te.getWorld().getDimension().getType(), cos.cs.computer.te.getPos(), Minecraft.getInstance().player.getUniqueID(), cos.cs.computer.location, cos.cs.computer.powerState = false));
            cos.cs.computer.session = null;
        });

        private final TriConsumer<ConsoleOperatingSystem, TerminalSession, String[]> handler;
        private final String[] aliases;

        Command(TriConsumer<ConsoleOperatingSystem, TerminalSession, String[]> handler) {
            this.handler = handler;
            this.aliases = new TranslationTextComponent("computing.lcc.console.meta." + this.name().toLowerCase()).getString().split(",");
        }

        public String getPrimaryAlias() {
            return aliases[0];
        }

        public String[] getAliases() {
            return aliases;
        }

        private void print(ConsoleOperatingSystem cos) {
            cos.printt("computing.lcc.console.meta." + this.name().toLowerCase() + ".usage");
            cos.printt("computing.lcc.console.help.aliases", String.join(", ", this.getAliases()));
            cos.printt("computing.lcc.console.meta." + this.name().toLowerCase() + ".description");
        }

        public static Command getFromAlias(String alias) {
            return Arrays.stream(Command.values()).filter(c -> Arrays.stream(c.getAliases()).anyMatch(s -> s.equalsIgnoreCase(alias))).findFirst().orElse(null);
        }
    }

}
