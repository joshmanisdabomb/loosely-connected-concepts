package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.network.TerminalStateChangePacket;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity.TERM_NETWORK;

@OnlyIn(Dist.CLIENT)
public class TerminalSession {

    public final TerminalTileEntity terminal;
    public ComputingSession session;

    public boolean blockInput;

    public TerminalSession(TerminalTileEntity te) {
        this.terminal = te;
    }

    protected List<ComputingModule> getActiveComputers() {
        List<Pair<BlockPos, SlabType>> modules = TERM_NETWORK.discover(terminal.getWorld(), Pair.of(terminal.getPos(), null)).getTraversables();
        return modules.stream().map(m -> {
            TileEntity te = terminal.getWorld().getTileEntity(m.getLeft());
            if (te instanceof ComputingTileEntity) {
                return ((ComputingTileEntity)te).getModule(m.getRight());
            }
            return null;
        }).filter(module -> module != null && module.type == ComputingModule.Type.COMPUTER && module.powerState).collect(Collectors.toList());
    }

    public void updateActiveComputer() {
        List<ComputingModule> computers = this.getActiveComputers();
        this.session = computers.size() != 1 ? null : computers.get(0).session;
    }

    public boolean active() {
        return session != null;
    }

    public int getBackgroundColor() {
        return this.active() ? session.getOS().getBackgroundColor(this) : 0xFF111111;
    }

    public void render(float partialTicks, int x, int y) {
        if (this.active()) session.getOS().render(this, partialTicks, x, y);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (blockInput) return true;
        return this.active() && session.getOS().keyPressed(this, p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    public boolean keyReleased(int p_keyReleased_1_, int p_keyReleased_2_, int p_keyReleased_3_) {
        if (blockInput) {
            blockInput = false;
            return true;
        }
        return this.active() && session.getOS().keyReleased(this, p_keyReleased_1_, p_keyReleased_2_, p_keyReleased_3_);
    }

    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        if (blockInput) return true;
        return this.active() && session.getOS().charTyped(this, p_charTyped_1_, p_charTyped_2_);
    }

    public CompoundNBT getState(ComputingSession cs) {
        UUID id = cs.getState().getUniqueId("id");
        if (!terminal.state.getUniqueId("for").equals(id)) {
            terminal.state = new CompoundNBT();
            terminal.state.putUniqueId("for", id);
        }
        return terminal.state;
    }

    public void receiveState() {

    }

    public void sendState() {
        if (terminal.getWorld().isRemote) {
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new TerminalStateChangePacket(terminal.getWorld().getDimension().getType(), terminal.getPos(), terminal.state));
        }
    }

}