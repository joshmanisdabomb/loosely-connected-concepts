package com.joshmanisdabomb.lcc.creative2;

public interface Creative2Category {

    default int getGroupColor() {
        return 0xFF5B5B5B;
    }

    int getSortValue();

}
