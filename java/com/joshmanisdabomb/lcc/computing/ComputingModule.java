package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.LCCContainerHelper;
import com.joshmanisdabomb.lcc.item.StorageItem;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity.DISK_NETWORK;
import static com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity.LOCAL_NETWORK;

public class ComputingModule {

    public final ComputingTileEntity te;
    public final Type type;
    public final DyeColor color;
    public final Direction direction;
    public final ITextComponent customName;

    public final LazyOptional<IItemHandlerModifiable> inventory;
    public final SlabType location;

    //Computer Module Only
    public boolean powerState;
    private long readTime = -1;

    public ComputingSession session = null;
    public CompoundNBT state = new CompoundNBT();

    public ComputingModule(ComputingTileEntity te, SlabType location, Type type, DyeColor color, Direction direction, ITextComponent customName) {
        this.te = te;
        this.location = location;
        this.type = type;
        this.color = color;
        this.direction = direction;
        this.customName = customName;
        this.inventory = this.type.inventory.apply(this);
    }

    public void load() {
        this.session = this.getSession(ComputingSession::wake);
    }

    public static ComputingModule read(ComputingTileEntity te, SlabType location, CompoundNBT tag) {
        if (!tag.contains("module", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        ComputingModule.Type module = ComputingModule.Type.values()[tag.getByte("module")];
        if (!tag.contains("color", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        DyeColor color = DyeColor.values()[tag.getByte("color")];
        if (!tag.contains("direction", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        Direction direction = Direction.values()[tag.getByte("direction")];

        ITextComponent name = tag.contains("customName", Constants.NBT.TAG_STRING) ? ITextComponent.Serializer.fromJson(tag.getString("customName")) : null;

        ComputingModule m = new ComputingModule(te, location, module, color, direction, name);

        CompoundNBT invTag = tag.getCompound("inventory");
        m.inventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        if (m.type == ComputingModule.Type.COMPUTER) {
            if (tag.contains("powerState", Constants.NBT.TAG_ANY_NUMERIC)) m.powerState = tag.getBoolean("powerState");
            if (tag.contains("state", Constants.NBT.TAG_COMPOUND)) m.state = tag.getCompound("state");
        }

        return m;
    }

    public CompoundNBT write(CompoundNBT tag) {
        tag.putByte("module", (byte)this.type.ordinal());
        tag.putByte("color", (byte)this.color.ordinal());
        tag.putByte("direction", (byte)this.direction.ordinal());
        tag.putString("customName", ITextComponent.Serializer.toJson(this.customName));
        inventory.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inventory", compound);
        });
        if (this.type == Type.COMPUTER) {
            tag.putBoolean("powerState", this.powerState);
            tag.put("state", this.state);
        }
        return tag;
    }

    public void addSlots(LCCContainerHelper.SlotManager sm, IItemHandlerModifiable moduleInventory) {
        this.type.slotCreator.accept(sm, moduleInventory);
    }

    public boolean hasGui() {
        return this.type.gui;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("block.lcc.computing." + this.type.name().toLowerCase());
    }

    public List<ComputingModule> getLocalDrives() {
        List<Pair<BlockPos, SlabType>> modules = LOCAL_NETWORK.discover(te.getWorld(), new ImmutablePair<>(te.getPos(), this.location)).getTraversables();
        return modules.stream().map(m -> ((ComputingTileEntity)te.getWorld().getTileEntity(m.getLeft())).getModule(m.getRight())).filter(module -> module.type != Type.CASING).collect(Collectors.toList());
    }

    public List<ComputingModule> getNetworkDrives() {
        List<Pair<BlockPos, SlabType>> modules = DISK_NETWORK.discover(te.getWorld(), new ImmutablePair<>(te.getPos(), this.location)).getTraversables();
        return modules.stream().filter(m -> m.getRight() != null).map(m -> ((ComputingTileEntity)te.getWorld().getTileEntity(m.getLeft())).getModule(m.getRight())).filter(module -> module.type != Type.CASING).collect(Collectors.toList());
    }

    public List<ItemStack> getDisks(List<ComputingModule> drives) {
        List<ItemStack> disks = new ArrayList<>();
        for (ComputingModule m : drives) {
            m.inventory.ifPresent(h -> {
                for (int i = 0; i < h.getSlots(); i++) {
                    ItemStack is = h.getStackInSlot(i);
                    if (is.getItem() instanceof StorageItem) {
                        disks.add(is);
                    }
                }
            });
        }
        if (disks.size() > 0) this.readTime = te.getWorld().getGameTime();
        return disks;
    }

    public List<ItemStack> getLocalDisks() {
        return this.getDisks(this.getLocalDrives());
    }

    public List<ItemStack> getNetworkDisks() {
        return this.getDisks(this.getNetworkDrives());
    }

    //Computer Module Only
    public ComputingSession getSession(Consumer<ComputingSession> create) {
        if (this.type == Type.COMPUTER && this.powerState) {
            if (this.session != null) return this.session;
            this.session = new ComputingSession(this);
            create.accept(this.session);
            return this.session;
        }
        return null;
    }

    public boolean isPowered() {
        return this.type == Type.COMPUTER && this.powerState;
    }

    public boolean isReading() {
        return this.readTime == te.getWorld().getGameTime();
    }

    public enum Type {
        CASING(false, cm -> LazyOptional.empty(),  null, 0, 0),
        COMPUTER( true, cm -> LazyOptional.of(() -> new ItemStackHandler(7) {
            @Override
            protected void onContentsChanged(int slot) {
                cm.te.markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == LCCItems.cpu;
                    case 5: return stack.getItem() == LCCItems.gpu;
                    case 6: return stack.getItem() == LCCItems.m2;
                    default: return stack.getItem() == LCCItems.ram;
                }
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        }), (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 17, 27, 0);
            sm.addSlots(moduleInventory, 41, 27, 1, 4, 1);
            sm.addSlot(moduleInventory, 119, 27, 5);
            sm.addSlot(moduleInventory, 143, 27, 6);
        }, 8, 96),
        FLOPPY_DRIVE(true, cm -> LazyOptional.of(() -> new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                cm.te.markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == LCCItems.floppy_disk;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        }), (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 80, 22, 0);
        }, 8, 58),
        CD_DRIVE(true, cm -> LazyOptional.of(() -> new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                cm.te.markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == LCCItems.compact_disc;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        }), (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 80, 20, 0);
        }, 8, 58),
        CARD_READER(true, cm -> LazyOptional.of(() -> new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                cm.te.markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == LCCItems.memory_card;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        }), (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 80, 22, 0);
        }, 8, 58),
        STICK_READER(true, cm -> LazyOptional.of(() -> new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                cm.te.markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == LCCItems.memory_stick;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        }), (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 80, 22, 0);
        }, 8, 58),
        DRIVE_BAY(true, cm -> LazyOptional.of(() -> new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                cm.te.markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == LCCItems.hard_disk_drive || stack.getItem() == LCCItems.solid_state_drive;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        }), (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 80, 22, 0);
        }, 8, 58);

        private final ResourceLocation tileEntityTexture;
        private final boolean gui;

        private final Function<ComputingModule, LazyOptional<IItemHandlerModifiable>> inventory;

        private final BiConsumer<LCCContainerHelper.SlotManager, IItemHandlerModifiable> slotCreator;
        public final int playerInvX;
        public final int playerInvY;

        Type(boolean gui, Function<ComputingModule, LazyOptional<IItemHandlerModifiable>> inventory, BiConsumer<LCCContainerHelper.SlotManager, IItemHandlerModifiable> slotCreator, int playerInvX, int playerInvY) {
            this.tileEntityTexture = new ResourceLocation(LCC.MODID, "textures/entity/tile/" + this.name().toLowerCase() + ".png");
            this.inventory = inventory;

            this.gui = gui;

            this.slotCreator = slotCreator;
            this.playerInvX = playerInvX;
            this.playerInvY = playerInvY;
        }

        public ResourceLocation getTexture() {
            return this.tileEntityTexture;
        }
    }

}