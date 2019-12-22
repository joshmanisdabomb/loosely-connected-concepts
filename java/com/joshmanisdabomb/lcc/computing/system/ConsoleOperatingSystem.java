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
import org.lwjgl.glfw.GLFW;

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

    protected void prompt(CompoundNBT confirmWork) {
        cs.getState().put("prompt", confirmWork);
    }

    protected boolean hasPrompt() {
        return !cs.getState().getCompound("prompt").isEmpty();
    }

    protected void answer(boolean confirm) {
        this.scroll();
        if (confirm) {
            CompoundNBT work = cs.getState().getCompound("prompt");

            ListNBT workQueue = cs.getState().getList("work_queue", Constants.NBT.TAG_COMPOUND);
            work.putString("type", "prompt");
            workQueue.add(work);

            cs.getState().put("work_queue", workQueue);
        } else {
            this.writet("computing.lcc.console.cancel");
        }
        cs.getState().remove("prompt");
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
        work.putString("type", "command");
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
        workQueue.stream().filter(n -> n instanceof CompoundNBT && ((CompoundNBT)n).getString("type").equals("command")).forEach(n -> {
            CompoundNBT work = (CompoundNBT)n;
            Command c = Command.byName(work.getString("command"));
            String[] args = work.getList("args", Constants.NBT.TAG_STRING).stream().map(INBT::getString).toArray(String[]::new);
            String[] pretranslations = work.getList("pret", Constants.NBT.TAG_STRING).stream().map(INBT::getString).toArray(String[]::new);
            if (c == null || !c.isServerSide()) return;
            this.endBuffer(false);
            this.print("> " + work.getString("interpreter"));
            c.s.handle(this, args, pretranslations, work);
        });
        workQueue.stream().filter(n -> n instanceof CompoundNBT && ((CompoundNBT)n).getString("type").equals("prompt")).forEach(n -> {
            CompoundNBT work = (CompoundNBT)n;
            switch (work.getString("action")) {
                case "rmpart":
                    List<ItemStack> disks = this.cs.computer.getNetworkDisks();
                    UUID id = work.getUniqueId("part");
                    StorageInfo.Partition part = this.getPartition(disks, id);
                    if (part != null) {
                        ItemStack disk = this.getPartitionDisk(disks, part);
                        if (disk != null) {
                            StorageInfo inf = new StorageInfo(disk);
                            ArrayList<StorageInfo.Partition> partitions = inf.getPartitions();
                            if (partitions.removeIf(p -> p.id.equals(id))) {
                                inf.setPartitions(partitions);
                                this.write(work.getString("success"));
                                break;
                            }
                        }
                    }
                    this.write(work.getString("missing"));
                    break;
                default:
                    break;
            }
        });
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
        } else if (this.hasPrompt()) {
            super.render(ts, partialTicks, x, y);
            LCCFonts.FIXED_WIDTH.get().drawString("\u2588 " + new TranslationTextComponent("computing.lcc.console.prompt").getFormattedText(), x + CONSOLE_OFFSET, y + 103, 0xD5D5D5);
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
            case GLFW.GLFW_KEY_UP:
            case GLFW.GLFW_KEY_PAGE_UP:
            case GLFW.GLFW_KEY_W:
                if (this.hasPrompt()) break;
                if (this.getBufferPosition() > 0) {
                    this.changeBufferPosition(-1);
                    cs.sendState();
                }
                break;
            case GLFW.GLFW_KEY_DOWN:
            case GLFW.GLFW_KEY_PAGE_DOWN:
            case GLFW.GLFW_KEY_S:
                if (this.hasPrompt()) break;
                if (this.hasBufferPosition() && this.getBufferPosition() < buffer.size() - out.length) {
                    this.changeBufferPosition(1);
                    cs.sendState();
                }
                break;
            case GLFW.GLFW_KEY_BACKSPACE:
                if (this.hasPrompt()) break;
                if (this.hasBufferPosition()) {
                    this.endBuffer(true);
                    cs.sendState();
                    break;
                }
                this.setInterpreter(ts, interpreter.substring(0, Math.max(interpreter.length() - 1, 0)));
                ts.sendState();
                break;
            case GLFW.GLFW_KEY_ENTER:
                if (this.hasPrompt()) break;
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
            case GLFW.GLFW_KEY_Y:
                if (this.hasPrompt()) {
                    this.answer(true);
                    ts.blockInput = true;
                }
                this.writeOutput(cs.getState());
                this.writeBuffer(cs.getState());
                cs.sendState();
                break;
            case GLFW.GLFW_KEY_N:
                if (this.hasPrompt()) {
                    this.answer(false);
                    ts.blockInput = true;
                }
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
        if (!this.hasBufferPosition() && !this.hasPrompt()) {
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
        CLEAR((cos, args, ts) -> {
            cos.clear();
        }),
        MAP((cos, args, pretranslations, work) -> {
            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
            String search = String.join(" ", args);

            Map<ItemStack, String> shortIds = new HashMap<>();
            Map<StorageInfo.Partition, String> shortPartitionIds = new HashMap<>();

            LinkedHashMap<ItemStack, List<StorageInfo.Partition>> map;
            SystemPath sp = new SystemPath(search);
            if (sp.valid && sp.disk != null && sp.partition != null) {
                List<StorageInfo.Partition> partitions = cos.searchPartitions(disks, sp.partition, sp.disk, false);
                map = cos.getDiskMap(disks, partitions, shortIds, shortPartitionIds);
            } else {
                map = cos.getDiskMap(disks, search, shortIds, shortPartitionIds, true);
            }

            cos.startBuffer();
            for (Map.Entry<ItemStack, List<StorageInfo.Partition>> e : map.entrySet()) {
                StorageInfo i = new StorageInfo(e.getKey());
                cos.alignOrPrint(e.getKey().getDisplayName().getFormattedText() + " #" + shortIds.get(e.getKey()), i.getUsedSpace() + "/" + i.getSize());
                if (e.getValue().size() < 1) {
                    cos.print(" - " + pretranslations[0]);
                } else {
                    for (int j = 0; j < e.getValue().size(); j++) {
                        StorageInfo.Partition p = e.getValue().get(j);
                        cos.alignOrPrint(" " + (j == e.getValue().size() - 1 ? '\u2514' : '\u251C') + " " + p.name + " #" + shortPartitionIds.get(p), p.type.isOS() ? p.start + ":" + p.size : (p.getUsedSpace() + "/" + p.size));
                    }
                }
            }
            cos.displayLargeBuffer();
        },
            "computing.lcc.console.map.no_partitions"
        ),
        USE((cos, args, pretranslations, work) -> {
            String disk = null, partition = null;
            FolderPath path = null;
            if (args.length > 2) {
                partition = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));
                disk = args[args.length - 1];
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
        },
            "computing.lcc.console.use.success",
            "computing.lcc.console.cd.success",
            "computing.lcc.console.use.no_results",
            "computing.lcc.console.use.no_results.disk",
            "computing.lcc.console.use.many_results",
            "computing.lcc.console.use.many_results.disk",
            "computing.lcc.console.use.none"
        ),
        LS((cos, args, ts) -> {}),
        CD((cos, args, ts) -> {}),
        MKDIR((cos, args, ts) -> {}),
        HOLD((cos, args, ts) -> {}),
        MKPART((cos, args, pretranslations, work) -> {
            if (args.length < 2) {
                cos.write(String.format(pretranslations[5], work.getString("interpreter").split(" ", 2)[0]));
                return;
            }
            StorageInfo.Partition.PartitionType type = null;
            int partitionTypeOffset = 1;
            if (args.length > 2) {
                for (partitionTypeOffset = args.length - 2; partitionTypeOffset > 0; partitionTypeOffset--) {
                    type = StorageInfo.Partition.PartitionType.byName(args[partitionTypeOffset]);
                    if (type != null && !type.isOS()) break;
                }
            } else {
                type = StorageInfo.Partition.PartitionType.byName(args[partitionTypeOffset]);
            }
            if (type == null || type.isOS()) {
                cos.write(String.format(pretranslations[1], Arrays.stream(StorageInfo.Partition.PartitionType.values()).filter(p -> !p.isOS()).map(StorageInfo.Partition.PartitionType::getName).collect(Collectors.joining(", "))));
                return;
            }
            int size = -1;
            try {
                size = Integer.valueOf(args[args.length - 1]);
                if (size <= 0) {
                    cos.write(pretranslations[2]);
                    return;
                }
            } catch (NumberFormatException ignored) {}
            String partition = String.join(" ", Arrays.copyOfRange(args, 0, partitionTypeOffset));
            String disk = args.length <= (size < 0 ? 2 : 3) ? null : String.join(" ", Arrays.copyOfRange(args, partitionTypeOffset + 1, args.length - (size > 0 ? 1 : 0)));
            if (disk == null) {
                SystemPath sp = new SystemPath(partition);
                if (sp.valid && sp.disk != null && sp.partition != null) {
                    disk = sp.disk;
                    partition = sp.partition;
                }
            }
            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
            ItemStack d;
            if (disk == null) {
                StorageInfo.Partition p = cos.using(disks);
                if (p == null) {
                    cos.write(pretranslations[6]);
                    return;
                }
                d = cos.getPartitionDisk(disks, p);
            } else {
                List<ItemStack> results = cos.searchDisks(disks, disk, false);
                if (results.size() <= 0) {
                    cos.write(String.format(pretranslations[7], disk));
                    return;
                } else if (results.size() > 1) {
                    cos.write(String.format(pretranslations[8], disk));
                    return;
                }
                d = results.get(0);
            }
            StorageInfo inf = new StorageInfo(d);
            String finalPartition = partition;
            if (inf.getPartitions().stream().anyMatch(p -> p.name.equalsIgnoreCase(finalPartition))) {
                cos.write(String.format(pretranslations[9], partition, d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
                return;
            }
            int space = inf.getPartitionableSpace();
            if (space <= 0) {
                cos.write(pretranslations[4]);
                return;
            }
            if (size <= 0) size = space;
            if (size > space) {
                cos.write(String.format(pretranslations[3], space));
                return;
            }
            StorageInfo.Partition newPart = new StorageInfo.Partition(UUID.randomUUID(), partition, type, size);
            inf.addPartition(newPart);
            cos.use(newPart);
            cos.write(String.format(pretranslations[0], newPart.type.getName(), newPart.name, StorageInfo.getShortPartitionId(disks, newPart, true), newPart.size, d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
        },
            "computing.lcc.console.mkpart.success",
            "computing.lcc.console.mkpart.invalid_type",
            "computing.lcc.console.mkpart.invalid_size",
            "computing.lcc.console.mkpart.low_space",
            "computing.lcc.console.mkpart.no_space",
            "computing.lcc.console.few_args",
            "computing.lcc.console.mkpart.no_disk",
            "computing.lcc.console.mkpart.invalid_disk",
            "computing.lcc.console.mkpart.many_disk",
            "computing.lcc.console.mkpart.existing"
        ),
        RMPART((cos, args, pretranslations, work) -> {
            String disk = null, partition = null;
            if (args.length > 1) {
                partition = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));
                disk = args[args.length - 1];
            } else if (args.length == 1) {
                SystemPath sp = new SystemPath(args[0]);
                if (sp.valid && sp.disk != null && sp.partition != null) {
                    disk = sp.disk;
                    partition = sp.partition;
                } else {
                    partition = args[0];
                }
            }

            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
            StorageInfo.Partition p;
            if (partition == null) {
                p = cos.using(disks);
                if (p == null) {
                    cos.write(pretranslations[6]);
                    return;
                }
            } else {
                List<StorageInfo.Partition> results = cos.searchPartitions(disks, partition, disk, false);
                if (results.size() <= 0) {
                    cos.write(String.format(pretranslations[disk != null && !disk.isEmpty() ? 3 : 2], partition, disk));
                    return;
                } else if (results.size() > 1) {
                    cos.write(String.format(pretranslations[disk != null && !disk.isEmpty() ? 5 : 4], partition, disk));
                    return;
                }
                p = results.get(0);
            }

            ItemStack d = cos.getPartitionDisk(disks, p);
            cos.line("-");
            cos.scroll();
            cos.print(String.format(pretranslations[0], p.name, StorageInfo.getShortPartitionId(disks, p, true), d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
            cos.line("-");
            CompoundNBT w = new CompoundNBT();
            w.putString("action", "rmpart");
            w.putUniqueId("part", p.id);
            w.putString("success", String.format(pretranslations[1], p.name));
            w.putString("missing", pretranslations[7]);
            cos.prompt(w);
        },
            "computing.lcc.console.rmpart.prompt",
            "computing.lcc.console.rmpart.success",
            "computing.lcc.console.rmpart.no_results",
            "computing.lcc.console.rmpart.no_results.disk",
            "computing.lcc.console.rmpart.many_results",
            "computing.lcc.console.rmpart.many_results.disk",
            "computing.lcc.console.rmpart.invalid_use",
            "computing.lcc.console.rmpart.missing"
        ),
        LABEL((cos, args, ts) -> {}),
        RESIZE((cos, args, ts) -> {}),
        INSTALL((cos, args, pretranslations, work) -> {
            int sector = -1;
            int size = -1;
            if (args.length > 0) {
                String[] properties = args[0].split(":", 2);
                try {
                    sector = Integer.valueOf(properties[0]);
                    if (sector < 0) {
                        cos.write(pretranslations[3]);
                        return;
                    }
                    if (sector > cos.getType().size) {
                        cos.write(String.format(pretranslations[11], cos.getType().size));
                        return;
                    }
                } catch (NumberFormatException ignored) {}
                if (sector >= 0 && properties.length > 1) {
                    try {
                        size = Integer.valueOf(properties[1]);
                        if (size <= 0) {
                            cos.write(pretranslations[4]);
                            return;
                        }
                        if (sector + size > cos.getType().size) {
                            cos.write(String.format(pretranslations[12], cos.getType().size - sector));
                            return;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
            String partition = null, disk = null;
            int noSector = sector == -1 ? -1 : 0;
            if (args.length > 1 + noSector) {
                SystemPath sp = new SystemPath(String.join(" ", Arrays.copyOfRange(args, 1 + noSector, args.length)));
                if (sp.valid && sp.disk != null && sp.partition != null) {
                    disk = sp.disk;
                    partition = sp.partition;
                } else {
                    if (args.length > 2 + noSector) {
                        disk = String.join(" ", Arrays.copyOfRange(args, 1 + noSector, args.length - 1));
                        partition = args[args.length - 1];
                    } else {
                        disk = args[1 + noSector];
                    }
                }
            }
            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
            ItemStack d;
            if (disk == null) {
                StorageInfo.Partition p = cos.using(disks);
                if (p == null) {
                    cos.write(pretranslations[7]);
                    return;
                }
                d = cos.getPartitionDisk(disks, p);
            } else {
                List<ItemStack> results = cos.searchDisks(disks, disk, false);
                if (results.size() <= 0) {
                    cos.write(String.format(pretranslations[8], disk));
                    return;
                } else if (results.size() > 1) {
                    cos.write(String.format(pretranslations[9], disk));
                    return;
                }
                d = results.get(0);
            }
            if (sector < 0) sector = 0;
            StorageInfo inf = new StorageInfo(d);
            if (partition == null) {
                partition = "Console OS";
                int i = 1;
                while (true) {
                    String finalPartition = partition;
                    if (!inf.getPartitions().stream().anyMatch(p -> p.name.equalsIgnoreCase(finalPartition))) break;
                    partition = "Console OS " + ++i;
                }
            } else {
                String finalPartition = partition;
                if (inf.getPartitions().stream().anyMatch(p -> p.name.equalsIgnoreCase(finalPartition))) {
                    cos.write(String.format(pretranslations[13], partition, d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
                    return;
                }
            }
            if (inf.getPartitions().stream().anyMatch(p -> p.type.isOS() && p.start <= 0 && p.size >= p.type.os.size)) {
                cos.write(pretranslations[10]);
                return;
            }
            int space = inf.getPartitionableSpace();
            if (space <= 0) {
                cos.write(pretranslations[6]);
                return;
            }
            if (size <= 0) size = cos.getType().size - sector;
            if (size > space) {
                cos.write(String.format(pretranslations[5], space));
                return;
            }
            StorageInfo.Partition newPart = new StorageInfo.Partition(UUID.randomUUID(), partition, StorageInfo.Partition.PartitionType.OS_CONSOLE, size);
            newPart.start = sector;
            inf.addPartition(newPart);
            cos.write(String.format(pretranslations[sector == 0 && size == cos.getType().size ? 0 : 1], newPart.name, StorageInfo.getShortPartitionId(disks, newPart, true), d.getDisplayName().getFormattedText(), StorageInfo.getShortId(disks, d, true)));
            if (sector + size < cos.getType().size) {
                cos.scroll();
                cos.write(String.format(pretranslations[2], sector + size, cos.getType().size - (sector + size)));
            }
        },
            "computing.lcc.console.install.success",
            "computing.lcc.console.install.success.partial",
            "computing.lcc.console.install.success.partial.continue",
            "computing.lcc.console.install.invalid_sector",
            "computing.lcc.console.install.invalid_size",
            "computing.lcc.console.install.low_space",
            "computing.lcc.console.install.no_space",
            "computing.lcc.console.install.no_disk",
            "computing.lcc.console.install.invalid_disk",
            "computing.lcc.console.install.many_disk",
            "computing.lcc.console.install.os_installed",
            "computing.lcc.console.install.max_sector",
            "computing.lcc.console.install.max_size",
            "computing.lcc.console.install.existing"
        ),
        REBOOT((cos, args, ts) -> {
            cos.cs.computer.session = null;
            cos.cs.computer.session = cos.cs.computer.getSession(ComputingSession::boot);
        }),
        SHUTDOWN((cos, args, ts) -> {
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
            void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work);
        }

    }

}
