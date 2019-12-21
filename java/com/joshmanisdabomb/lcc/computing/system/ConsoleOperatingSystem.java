package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.joshmanisdabomb.lcc.network.ComputerPowerPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.registry.LCCFonts;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleOperatingSystem extends LinedOperatingSystem {

    @OnlyIn(Dist.CLIENT)
    private long lastTypeTime = 0;

    private static final Object[] PRETRANSLATION_PARAMS = IntStream.range(1,10).mapToObj(i -> "%" + i + "$s").toArray(String[]::new);
    private static final Pattern ARGUMENT_MATCHER = Pattern.compile("\"[^\"]*\"|[^ ]+");

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

    protected StorageInfo.Partition using(List<ItemStack> disks) {
        if (!cs.getState().hasUniqueId("using")) return null;
        StorageInfo.Partition using = this.getPartition(disks, cs.getState().getUniqueId("using"));
        if (using == null) this.use(null);
        return using;
    }

    protected void use(StorageInfo.Partition partition) {
        if (partition == null) {
            cs.getState().remove("usingMost");
            cs.getState().remove("usingLeast");
        }
        else cs.getState().putUniqueId("using", partition.id);
    }

    protected void handleCommand(String interpreter, TerminalSession ts) {
        String[] commandAndArgs = interpreter.split(" ", 2);
        String[] args = new String[0];
        if (commandAndArgs.length == 2) {
            Matcher m = ARGUMENT_MATCHER.matcher(commandAndArgs[1]);
            ArrayList<String> a = new ArrayList<>();
            while (m.find()) {
                String arg = m.group();
                if (arg.startsWith("\"") && arg.endsWith("\"")) arg = arg.substring(1, arg.length() - 1);
                a.add(arg);
            }
            args = a.toArray(args);
        }
        Command c = Command.getFromAlias(commandAndArgs[0]);
        if (c == null) {
            this.print("> " + interpreter);
            this.writet("computing.lcc.console.unknown");
        } else {
            if (c.isServerSide()) {
                this.passCommand(c, args, interpreter);
            } else {
                this.print("> " + interpreter);
                c.c.handle(this, args, ts);
            }
        }
    }

    private void passCommand(Command command, String[] args, String interpreter) {
        ListNBT workQueue = cs.getState().getList("work_queue", Constants.NBT.TAG_COMPOUND);
        CompoundNBT work = new CompoundNBT();
        work.putString("command", command.getName());
        work.putString("interpreter", interpreter);

        ListNBT arguments = new ListNBT();
        for (String arg : args) {
            arguments.add(new StringNBT(arg));
        }
        work.put("args", arguments);

        ListNBT pretranslations = new ListNBT();
        for (String pretranslation : command.pretranslations) {
            pretranslations.add(new StringNBT(pretranslation));
        }
        work.put("pret", pretranslations);

        workQueue.add(work);

        cs.getState().put("work_queue", workQueue);
    }

    @Override
    public void processWork(ListNBT workQueue) {
        for (INBT t : workQueue) {
            CompoundNBT work = (CompoundNBT)t;
            Command c = Command.byName(work.getString("command"));
            String[] args = work.getList("args", Constants.NBT.TAG_STRING).stream().map(INBT::getString).toArray(String[]::new);
            String[] pretranslations = work.getList("pret", Constants.NBT.TAG_STRING).stream().map(INBT::getString).toArray(String[]::new);
            if (c == null || !c.isServerSide()) continue;
            this.endBuffer(false);
            this.print("> " + work.getString("interpreter"));
            c.s.handle(this, args, pretranslations);
        }
        this.writeOutput(cs.getState());
        this.writeBuffer(cs.getState());
    }

    @Override
    public void onReceiveState() {
        this.readOutput(cs.getState());
        this.readBuffer(cs.getState());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        if (this.hasBufferPosition()) {
            for (int i = 0; i < out.length; i++) {
                int j = i + this.getBufferPosition();
                if (j >= buffer.size()) break;
                LCCFonts.FIXED_WIDTH.get().drawString(buffer.get(j), x + CONSOLE_OFFSET, y + 4 + (i * 11), 0xD5D5D5);
            }
            LCCFonts.FIXED_WIDTH.get().drawString(new TranslationTextComponent("computing.lcc.console.buffer", this.getBufferPosition() + 1, Math.max(this.buffer.size() - out.length, 0) + 1).getFormattedText(), x + CONSOLE_OFFSET, y + 103, 0xD5D5D5);
        } else {
            super.render(ts, partialTicks, x, y);
            String interpreter = this.getInterpreter(ts);
            while (LCCFonts.FIXED_WIDTH.get().getStringWidth("> " + interpreter + "_") > CONSOLE_WIDTH) {
                interpreter = interpreter.substring(1);
            }
            LCCFonts.FIXED_WIDTH.get().drawString("> " + interpreter + ((System.currentTimeMillis() - lastTypeTime) % 1000 <= 500 ? "_" : " "), x + CONSOLE_OFFSET, y + 103, 0xD5D5D5);
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
                if (this.getBufferPosition() > 0) {
                    this.changeBufferPosition(-1);
                    cs.sendState();
                }
                break;
            case 264: //down
            case 267: //page down
            case 83:  //s
                if (this.hasBufferPosition() && this.getBufferPosition() < buffer.size() - out.length) {
                    this.changeBufferPosition(1);
                    cs.sendState();
                }
                break;
            case 259: //backspace
                if (this.hasBufferPosition()) {
                    this.endBuffer(true);
                    cs.sendState();
                    break;
                }
                this.setInterpreter(ts, interpreter.substring(0, Math.max(interpreter.length() - 1, 0)));
                ts.sendState();
                break;
            case 257: //enter
                if (this.hasBufferPosition()) {
                    this.endBuffer(true);
                    cs.sendState();
                    break;
                }
                this.setInterpreter(ts, "");
                ts.sendState();
                this.scroll();
                this.handleCommand(interpreter, ts);
                this.writeOutput(cs.getState());
                this.writeBuffer(cs.getState());
                cs.sendState();
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

    public enum Command implements IStringSerializable {
        HELP((cos, args, ts) -> {
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
        CLEAR((cos, args, pretranslations) -> {
            cos.clear();
        }),
        MAP((cos, args, pretranslations) -> {
            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();

            Map<ItemStack, String> shortIds = new HashMap<>();
            Map<StorageInfo.Partition, String> shortPartitionIds = new HashMap<>();
            LinkedHashMap<ItemStack, List<StorageInfo.Partition>> map = cos.getDiskMap(disks, String.join(" ", args), shortIds, shortPartitionIds, true);

            cos.startBuffer();
            for (Map.Entry<ItemStack, List<StorageInfo.Partition>> e : map.entrySet()) {
                StorageInfo i = new StorageInfo(e.getKey());
                cos.alignOrPrint(e.getKey().getDisplayName().getFormattedText() + " #" + shortIds.get(e.getKey()), i.getUsedSpace() + "/" + i.getSize());
                if (e.getValue().size() < 1) {
                    cos.print(" - " + pretranslations[0]);
                } else {
                    for (int j = 0; j < e.getValue().size(); j++) {
                        StorageInfo.Partition p = e.getValue().get(j);
                        cos.alignOrPrint(" " + (j == e.getValue().size() - 1 ? '\u2514' : '\u251C') + " " + p.name + " #" + shortPartitionIds.get(p), p.type.isOS() ? Integer.toString(p.size) : (p.getUsedSpace() + "/" + p.size));
                    }
                }
            }
            cos.displayLargeBuffer();
        }, "computing.lcc.console.map.no_partitions"),
        USE((cos, args, pretranslations) -> {
            String disk = null, partition = null;
            FolderPath path = null;
            if (args.length > 2) {
                partition = String.join(" ", args);
            } else if (args.length <= 0) {
                List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
                StorageInfo.Partition using = cos.using(disks);
                if (using != null) {
                    ItemStack d = cos.getPartitionDisk(disks, using);
                    cos.write(String.format(pretranslations[0], using.name, StorageInfo.getShortPartitionId(disks, using, true), d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
                } else {
                    cos.write(pretranslations[6]);
                }
                return;
            } else if (args.length == 2) {
                partition = args[0];
                disk = args[1];
            } else {
                SystemPath sp = new SystemPath(args[0]);
                if (sp.valid && sp.disk != null && sp.partition != null) {
                    disk = sp.disk;
                    partition = sp.partition;
                    path = sp.folders;
                } else {
                    partition = args[0];
                }
            }

            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
            List<StorageInfo.Partition> partitions = cos.searchPartitions(disks, partition, disk, false);
            if (partitions.size() <= 0) {
                cos.write(String.format(pretranslations[disk != null && !disk.isEmpty() ? 3 : 2], partition, disk));
            } else if (partitions.size() > 1) {
                cos.write(String.format(pretranslations[disk != null && !disk.isEmpty() ? 5 : 4], partition, disk));
            } else {
                StorageInfo.Partition using = partitions.get(0);
                ItemStack d = cos.getPartitionDisk(disks, using);
                cos.use(using);
                cos.write(String.format(pretranslations[0], using.name, StorageInfo.getShortPartitionId(disks, using, true), d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
            }
        }, "computing.lcc.console.use.success", "computing.lcc.console.cd.success", "computing.lcc.console.use.no_results", "computing.lcc.console.use.no_results.disk", "computing.lcc.console.use.many_results", "computing.lcc.console.use.many_results.disk", "computing.lcc.console.use.none"),
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
        }),
        SHUTDOWN((cos, ts, args) -> {
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new ComputerPowerPacket(cos.cs.computer.te.getWorld().getDimension().getType(), cos.cs.computer.te.getPos(), Minecraft.getInstance().player.getUniqueID(), cos.cs.computer.location, cos.cs.computer.powerState = false));
            cos.cs.computer.session = null;
        });

        private final ClientHandler c;
        private final ServerHandler s;
        private final String[] pretranslations;
        private final String[] aliases;

        Command(ClientHandler c) {
            this.c = c;
            this.s = null;
            this.aliases = new TranslationTextComponent("computing.lcc.console.meta." + this.name().toLowerCase()).getString().split(",");
            this.pretranslations = null;
        }

        Command(ServerHandler s, String... pretranslations) {
            this.c = null;
            this.s = s;
            this.aliases = new TranslationTextComponent("computing.lcc.console.meta." + this.name().toLowerCase()).getString().split(",");
            this.pretranslations = Arrays.stream(pretranslations).map(k -> new TranslationTextComponent(k, PRETRANSLATION_PARAMS).getFormattedText()).toArray(String[]::new);
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

        public boolean isServerSide() {
            return this.c == null && this.s != null;
        }

        public static Command getFromAlias(String alias) {
            return Arrays.stream(Command.values()).filter(c -> Arrays.stream(c.getAliases()).anyMatch(s -> s.equalsIgnoreCase(alias))).findFirst().orElse(null);
        }

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }

        public static Command byName(String name) {
            return Arrays.stream(Command.values()).filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        }

        @FunctionalInterface
        public interface ClientHandler {
            void handle(ConsoleOperatingSystem cos, String[] args, TerminalSession ts);
        }

        @FunctionalInterface
        public interface ServerHandler {
            void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations);
        }

    }

}
