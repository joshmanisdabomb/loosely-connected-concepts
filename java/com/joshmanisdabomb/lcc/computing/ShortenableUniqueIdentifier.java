package com.joshmanisdabomb.lcc.computing;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ShortenableUniqueIdentifier {

    UUID getUniqueId();

    static <T extends ShortenableUniqueIdentifier> HashMap<T, String> getShortIds(List<T> main) {
        return ShortenableUniqueIdentifier.getShortIds(main, null);
    }

    static <T extends ShortenableUniqueIdentifier> HashMap<T, String> getShortIds(List<T> main, List<? extends ShortenableUniqueIdentifier> use) {
        List<String> ids = main.stream().map(s -> s.getUniqueId().toString().replace("-", "").toLowerCase()).collect(Collectors.toList());
        if (use != null) ids.addAll(use.stream().map(s -> s.getUniqueId().toString().replace("-", "").toLowerCase()).collect(Collectors.toList()));
        HashMap<T, String> ret = new HashMap<>();
        for (T e : main) {
            for (int i = 1; i <= 32; i++) {
                String search = e.getUniqueId().toString().replace("-", "").toLowerCase().substring(0, i);
                if (ids.stream().filter(id -> id.startsWith(search)).count() == 1) {
                    ret.put(e, search);
                    break;
                }
            }
        }
        return ret;
    }

}
