package com.joshmanisdabomb.lcc.creative2;

import java.util.Set;

public interface Creative2GroupKey {

    default int getSelectionWidth() {
        return 2;
    }

    default int getSelectionHeight() {
        return 2;
    }

    float[] getSelectionColor();

    default float[] getSelectionHoverColor() {
        return new float[]{1.0F, 1.0F, 1.0F, 0.5019608F};
    }

    default Enum<?> getGroupKey(Set<? extends Enum<?>> groupKeys) {
        return (Enum<?>)this;
    }

}