package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.joshmanisdabomb.lcc.network.ComputerPowerPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.registry.LCCFonts;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
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
        super.wake();
    }

    @Override
    public Type getType() {
        return Type.CONSOLE;
    }

    protected String getInterpreter(TerminalSession ts) {
        return ts.getState(cs).getString("interpreter");
    }

    protected void setInterpreter(TerminalSession ts, String text) {
        ts.getState(cs).putString("interpreter", text);
    }

    protected void handleCommand(String interpreter, TerminalSession ts) {
        String[] commandAndArgs = interpreter.split(" ", 2);
        String[] args = commandAndArgs.length == 2 ? commandAndArgs[1].split(" ") : new String[0];
        Command c = Command.getFromAlias(commandAndArgs[0]);
        if (c == null) {
            this.writet("computing.lcc.console.unknown");
        } else {
            c.handler.accept(this, ts, args);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        if (this.hasBufferPosition()) {
            for (int i = 0; i < out.length; i++) {
                int j = i + this.getBufferPosition();
                if (j >= buffer.size()) break;
                LCCFonts.FIXED_WIDTH.get().drawString(buffer.get(j), x + 5, y + 4 + (i * 11), 0xD5D5D5);
            }
            LCCFonts.FIXED_WIDTH.get().drawString(new TranslationTextComponent("computing.lcc.console.buffer", this.getBufferPosition() + 1, Math.max(this.buffer.size() - out.length, 0) + 1).getFormattedText(), x + 5, y + 103, 0xD5D5D5);
        } else {
            super.render(ts, partialTicks, x, y);
            String interpreter = this.getInterpreter(ts);
            while (LCCFonts.FIXED_WIDTH.get().getStringWidth("> " + interpreter + "_") > CONSOLE_WIDTH) {
                interpreter = interpreter.substring(1);
            }
            LCCFonts.FIXED_WIDTH.get().drawString("> " + interpreter + ((System.currentTimeMillis() - lastTypeTime) % 1000 <= 500 ? "_" : " "), x + 5, y + 103, 0xD5D5D5);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean keyPressed(TerminalSession ts, int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        String interpreter = this.getInterpreter(ts);
        switch (p_keyPressed_1_) {
            case 265: //up
            case 266: //page up
            case 87:  //w
                if (this.getBufferPosition() > 0) this.changeBufferPosition(-1);
                break;
            case 264: //down
            case 267: //page down
            case 83:  //s
                if (this.hasBufferPosition() && this.getBufferPosition() < buffer.size() - out.length) this.changeBufferPosition(1);
                break;
            case 259: //backspace
                if (this.hasBufferPosition()) {
                    this.endBuffer(true);
                    break;
                }
                this.setInterpreter(ts, interpreter.substring(0, Math.max(interpreter.length() - 1, 0)));
                ts.sendState();
                break;
            case 257: //enter
                if (this.hasBufferPosition()) {
                    this.endBuffer(true);
                    break;
                }
                this.setInterpreter(ts, "");
                ts.sendState();
                this.scroll();
                this.print("> " + interpreter);
                this.handleCommand(interpreter, ts);
                this.writeOutput(cs.getState());
                this.writeBuffer(cs.getState());
                break;
        }
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean charTyped(TerminalSession ts, char p_charTyped_1_, int p_charTyped_2_) {
        if (!this.hasBufferPosition()) {
            String interpreter = this.getInterpreter(ts);
            this.setInterpreter(ts, interpreter + p_charTyped_1_);
            ts.sendState();
        }
        lastTypeTime = System.currentTimeMillis();
        return true;
    }

    protected void displayBuffer() {
        if (!buffer.isEmpty() && buffer.get(buffer.size() - 1).equals("")) buffer.remove(buffer.size() - 1);
        cs.getState().putInt("buffer_position", 0);
    }

    protected void displayLargeBuffer() {
        if (!buffer.isEmpty() && buffer.get(buffer.size() - 1).equals("")) buffer.remove(buffer.size() - 1);
        if (buffer.size() > out.length) this.displayBuffer();
        else this.endBuffer(true);
    }

    protected boolean hasBufferPosition() {
        return buffer != null && cs.getState().contains("buffer_position", Constants.NBT.TAG_INT);
    }

    protected int getBufferPosition() {
        return buffer == null ? -1 : cs.getState().getInt("buffer_position");
    }

    protected void changeBufferPosition(int mod) {
        if (buffer != null) cs.getState().putInt("buffer_position", cs.getState().getInt("buffer_position") + mod);
    }

    @Override
    protected ArrayList<String> endBuffer(boolean output) {
        cs.getState().remove("buffer_position");
        return super.endBuffer(output);
    }

    public enum Command {
        HELP((cos, ts, args) -> {
            cos.startBuffer();
            if (args.length == 0) {
                cos.writet("computing.lcc.console.help.available", Arrays.stream(Command.values()).map(Command::getPrimaryAlias).collect(Collectors.joining(", ")));
            } else {
                Command c = Command.getFromAlias(args[0]);
                if (c == null) {
                    cos.writet("computing.lcc.console.unknown");
                } else {
                    c.write(cos);
                }
            }
            cos.displayLargeBuffer();
        }),
        CLEAR((cos, ts, args) -> {
            cos.clear();
        }),
        MAP((cos, ts, args) -> {
            cos.startBuffer();
            cos.cs.computer.getNetworkDisks().forEach(d -> {
                cos.print(d.toString());
            });
            cos.displayLargeBuffer();
        }),
        USE((cos, ts, args) -> {}),
        LS((cos, ts, args) -> {}),
        CD((cos, ts, args) -> {}),
        MKDIR((cos, ts, args) -> {}),
        HOLD((cos, ts, args) -> {}),
        MKPART((cos, ts, args) -> {}),
        RMPART((cos, ts, args) -> {}),
        LABEL((cos, ts, args) -> {}),
        RESIZE((cos, ts, args) -> {}),
        INSTALL((cos, ts, args) -> {}),
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

        private void write(ConsoleOperatingSystem cos) {
            cos.printt("computing.lcc.console.meta." + this.name().toLowerCase() + ".usage");
            cos.printt("computing.lcc.console.help.aliases", String.join(", ", this.getAliases()));
            cos.writet("computing.lcc.console.meta." + this.name().toLowerCase() + ".description");
        }

        public static Command getFromAlias(String alias) {
            return Arrays.stream(Command.values()).filter(c -> Arrays.stream(c.getAliases()).anyMatch(s -> s.equalsIgnoreCase(alias))).findFirst().orElse(null);
        }
    }

}
