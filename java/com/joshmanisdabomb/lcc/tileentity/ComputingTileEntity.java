package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.joshmanisdabomb.lcc.container.LCCContainerHelper;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputingTileEntity extends TileEntity implements INamedContainerProvider {

    protected ComputingModule top = null;
    protected ComputingModule bottom = null;

    private LazyOptional<CombinedInvWrapper> totalInventory = LazyOptional.empty();

    public boolean _containerModuleIsTop;

    public ComputingTileEntity() {
        super(LCCTileEntities.computing);
    }

    public void read(CompoundNBT tag) {
        if (tag.contains("TopModule", Constants.NBT.TAG_COMPOUND)) {
            this.top = this.readModule(tag.getCompound("TopModule"));
        }
        if (tag.contains("BottomModule", Constants.NBT.TAG_COMPOUND)) {
            this.bottom = this.readModule(tag.getCompound("BottomModule"));
        }
        super.read(tag);
    }

    public CompoundNBT write(CompoundNBT tag) {
        if (this.top != null) tag.put("TopModule", this.top.write(new CompoundNBT()));
        if (this.bottom != null) tag.put("BottomModule", this.bottom.write(new CompoundNBT()));
        return super.write(tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = this.write(new CompoundNBT());
        return new SUpdateTileEntityPacket(getPos(), 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ComputingContainer(i, this, _containerModuleIsTop ? SlabType.TOP : SlabType.BOTTOM, playerEntity, playerInventory);
    }

    public void setModule(ComputingModuleType type, DyeColor color, Direction facing, ITextComponent customName, SlabType module) {
        ComputingModule m = new ComputingModule(type, color, facing, customName);
        switch (module) {
            case TOP:
                this.top = m;
                break;
            case BOTTOM:
                this.bottom = m;
                break;
            default:
                break;
        }
        this.calculateTotalInventory();
    }

    public void clearModule(SlabType module) {
        switch (module) {
            case TOP:
                this.top = null;
                break;
            case BOTTOM:
                this.bottom = null;
                break;
            default:
                break;
        }
        this.calculateTotalInventory();
    }

    public ComputingModule getModule(SlabType module) {
        switch (module) {
            case TOP: return this.top;
            case BOTTOM: return this.bottom;
            default: return null;
        }
    }

    public ArrayList<ComputingModule> getInstalledModules() {
        ArrayList<ComputingModule> modules = new ArrayList<>();
        if (this.top != null) modules.add(this.top);
        if (this.bottom != null) modules.add(this.bottom);
        return modules;
    }

    public boolean isModuleConnectedAbove(SlabType module) {
        if (module == SlabType.BOTTOM) {
            ComputingModule cm = this.getModule(flip(module));
            if (cm == null) return false;
            return cm.color == this.getModule(module).color;
        } else {
            TileEntity te = this.world.getTileEntity(pos.up());
            if (te instanceof ComputingTileEntity) {
                ComputingModule cm = ((ComputingTileEntity)te).getModule(flip(module));
                if (cm == null) return false;
                return cm.color == this.getModule(module).color;
            }
            return false;
        }
    }

    public boolean isModuleConnectedBelow(SlabType module) {
        if (module == SlabType.TOP) {
            ComputingModule cm = this.getModule(flip(module));
            if (cm == null) return false;
            return cm.color == this.getModule(module).color;
        } else {
            TileEntity te = this.world.getTileEntity(pos.down());
            if (te instanceof ComputingTileEntity) {
                ComputingModule cm = ((ComputingTileEntity)te).getModule(flip(module));
                if (cm == null) return false;
                return cm.color == this.getModule(module).color;
            }
            return false;
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            switch (side) {
                case UP:
                    if (this.top == null) return LazyOptional.empty();
                    else return this.top.inventory.cast();
                case DOWN:
                    if (this.bottom == null) return LazyOptional.empty();
                    else return this.bottom.inventory.cast();
                default:
                    return totalInventory.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public void calculateTotalInventory() {
        this.totalInventory = LazyOptional.empty();
        if (this.top != null && this.top.inventory.isPresent() && this.bottom != null && this.bottom.inventory.isPresent()) {
            this.top.inventory.ifPresent(h -> {
                this.bottom.inventory.ifPresent(h2 -> {
                    totalInventory = LazyOptional.of(() -> new CombinedInvWrapper(h, h2));
                });
            });
        }
        else if (this.top != null && this.top.inventory.isPresent()) this.top.inventory.ifPresent(h -> totalInventory = LazyOptional.of(() -> new CombinedInvWrapper(h)));
        else if (this.bottom != null && this.bottom.inventory.isPresent()) this.bottom.inventory.ifPresent(h -> totalInventory = LazyOptional.of(() -> new CombinedInvWrapper(h)));
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.lcc.computing");
    }

    public enum ComputingModuleType {
        CASING(new ResourceLocation(LCC.MODID, "textures/entity/tile/computer_casing.png"), false, (cm, te) -> {}, (cm, tag) -> {}, (cm, tag) -> {}, null, 0, 0),
        COMPUTER(new ResourceLocation(LCC.MODID, "textures/entity/tile/computer.png"), true, (cm, te) -> {
            cm.inventory = LazyOptional.of(() -> new ItemStackHandler(7) {
                @Override
                protected void onContentsChanged(int slot) {
                    te.markDirty();
                }
            });
            cm.powerState = false;
        }, (cm, tag) -> {
            cm.powerState = tag.getBoolean("PowerState");
        }, (cm, tag) -> {
            tag.putBoolean("PowerState", cm.powerState);
        }, (sm, moduleInventory) -> {
            sm.addSlot(moduleInventory, 17, 27, 0);
            sm.addSlots(moduleInventory, 41, 27, 1, 4, 1);
            sm.addSlot(moduleInventory, 119, 27, 5);
            sm.addSlot(moduleInventory, 143, 27, 6);
        }, 8, 96);

        private final ResourceLocation tileEntityTexture;
        private final boolean gui;
        private final BiConsumer<ComputingModule, ComputingTileEntity> moduleModifiers;

        private final BiConsumer<ComputingModule, CompoundNBT> read;
        private final BiConsumer<ComputingModule, CompoundNBT> write;

        private final BiConsumer<LCCContainerHelper.SlotManager, IItemHandlerModifiable> slotCreator;
        public final int playerInvX;
        public final int playerInvY;

        ComputingModuleType(ResourceLocation tileEntityTexture, boolean gui, BiConsumer<ComputingModule, ComputingTileEntity> moduleModifiers, BiConsumer<ComputingModule, CompoundNBT> read, BiConsumer<ComputingModule, CompoundNBT> write, BiConsumer<LCCContainerHelper.SlotManager, IItemHandlerModifiable> slotCreator, int playerInvX, int playerInvY) {
            this.tileEntityTexture = tileEntityTexture;
            this.gui = gui;
            this.moduleModifiers = moduleModifiers;

            this.read = read;
            this.write = write;

            this.slotCreator = slotCreator;
            this.playerInvX = playerInvX;
            this.playerInvY = playerInvY;
        }

        public ResourceLocation getTexture() {
            return this.tileEntityTexture;
        }

        public void modify(ComputingModule cm, ComputingTileEntity te) {
            moduleModifiers.accept(cm, te);
        }
    }

    public class ComputingModule {
        public final ComputingModuleType type;
        public final DyeColor color;
        public final Direction direction;
        public final ITextComponent customName;

        public LazyOptional<IItemHandlerModifiable> inventory = LazyOptional.empty();

        public boolean powerState;

        private ComputingModule(ComputingModuleType type, DyeColor color, Direction direction, ITextComponent customName) {
            this.type = type;
            this.color = color;
            this.direction = direction;
            this.customName = customName;
            this.type.modify(this, ComputingTileEntity.this);
        }

        private CompoundNBT write(CompoundNBT tag) {
            tag.putByte("module", (byte)this.type.ordinal());
            tag.putByte("color", (byte)this.color.ordinal());
            tag.putByte("direction", (byte)this.direction.ordinal());
            tag.putString("customName", ITextComponent.Serializer.toJson(this.customName));
            inventory.ifPresent(h -> {
                CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
                tag.put("inventory", compound);
            });
            this.type.write.accept(this, tag);
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
    }

    private ComputingModule readModule(CompoundNBT tag) {
        if (!tag.contains("module", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        ComputingModuleType module = ComputingModuleType.values()[tag.getByte("module")];
        if (!tag.contains("color", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        DyeColor color = DyeColor.values()[tag.getByte("color")];
        if (!tag.contains("direction", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        Direction direction = Direction.values()[tag.getByte("direction")];
        if (!tag.contains("customName", Constants.NBT.TAG_STRING)) return null;
        ITextComponent name = ITextComponent.Serializer.fromJson(tag.getString("customName"));

        ComputingModule m = new ComputingModule(module, color, direction, name);

        CompoundNBT invTag = tag.getCompound("inventory");
        m.inventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        m.type.read.accept(m, tag);

        return m;
    }

}
