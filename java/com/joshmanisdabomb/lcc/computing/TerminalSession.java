package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

import static com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity.WIRED_NETWORK;

@OnlyIn(Dist.CLIENT)
public class TerminalSession {

    public final TerminalTileEntity terminal;
    public ComputingSession session;

    private OperatingSystem os;

    public TerminalSession(TerminalTileEntity te) {
        this.terminal = te;
    }

    protected List<ComputingModule> getActiveComputers() {
        List<Pair<BlockPos, SlabType>> modules = WIRED_NETWORK.discover(terminal.getWorld(), Pair.of(terminal.getPos(), null)).getTraversables();
        return modules.stream().map(m -> {
            TileEntity te = terminal.getWorld().getTileEntity(m.getLeft());
            if (te instanceof ComputingTileEntity) {
                return ((ComputingTileEntity)te).getModule(m.getRight());
            }
            return null;
        }).filter(module -> module != null && module.type == ComputingModule.Type.COMPUTER && module.powerState).collect(Collectors.toList());
    }

    public void update() {
        List<ComputingModule> computers = this.getActiveComputers();
        this.session = computers.size() != 1 ? null : computers.get(0).getSession();
        this.os = OperatingSystem.Type.BIOS.factory.apply(terminal, session);
    }

    public boolean active() {
        return os != null;
    }

    public int getBackgroundColor() {
        return this.active() ? os.getBackgroundColor() : 0xFF111111;
    }

    public void render(float partialTicks) {
        if (this.active()) os.render(partialTicks);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return this.active() && os.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

}