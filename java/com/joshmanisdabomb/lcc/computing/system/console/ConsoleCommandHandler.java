package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface ConsoleCommandHandler {

    @FunctionalInterface
    interface Client extends ConsoleCommandHandler {

        void handle(ConsoleOperatingSystem cos, String[] args, TerminalSession ts);

        default String getDiskName(ItemStack disk) {
            return new StorageInfo(disk).getDisplayName(null);
        }

    }

    @FunctionalInterface
    interface Server extends ConsoleCommandHandler {

        String[] DEFAULT_PRETRANSLATIONS = new String[]{
            "computing.lcc.bios",
            "computing.lcc.console",
            "computing.lcc.graphical",
            "computing.lcc.items",
            "item.lcc.floppy_disk",
            "item.lcc.compact_disc",
            "item.lcc.memory_card",
            "item.lcc.memory_stick",
            "item.lcc.hard_disk_drive",
            "item.lcc.solid_state_drive",
            "item.lcc.m2"
        };

        default String format(String[] pretranslations, String key, Object... params) {
            String[] pt = Stream.concat(Arrays.stream(DEFAULT_PRETRANSLATIONS), Arrays.stream(this.getPretranslateKeys())).toArray(String[]::new);
            String value = null;
            for (int i = 0; i < pt.length; i++) {
                if (pt[i].equals(key)) value = pretranslations[i];
            }
            if (value == null) return null;
            return String.format(value, params);
        }

        default String getDiskName(String[] pretranslations, ItemStack disk) {
            return new StorageInfo(disk).getDisplayName(this.getTranslator(pretranslations));
        }

        default UnaryOperator<String> getTranslator(String[] pretranslations) {
            return k -> this.format(pretranslations, k);
        }

        void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work);

        default String[] getPretranslateKeys() {
            return new String[0];
        }

    }

}
