package com.joshmanisdabomb.lcc.creative2;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gui.inventory.Creative2Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public abstract class Creative2Group extends ItemGroup {

    public static final ArrayList<Creative2Group> GROUPS = new ArrayList<>();

    private final HashMap<Predicate<ItemStack>, Creative2Category> category_map = new HashMap<>();
    private final HashMap<Predicate<ItemStack>, Integer> value_map = new HashMap<>();

    public final ArrayList<Map<? extends Enum<?>, ? extends IItemProvider>> groups = new ArrayList<>();
    private final HashMap<Map<? extends Enum<?>, ? extends IItemProvider>, String> group_translations = new HashMap<>();
    public final HashMap<Map<? extends Enum<?>, ? extends IItemProvider>, Integer> group_items = new HashMap<>();

    public final ArrayList<Map<? extends Enum<?>, ? extends IItemProvider>> expandedGroups = new ArrayList<>();

    private final Comparator<ItemStack> SORTER = (i1, i2) -> {
        if (i1.isEmpty() || i2.isEmpty()) return 0;

        Creative2Category ic1 = category_map.entrySet().stream().filter(e -> e.getKey().test(i1)).map(Map.Entry::getValue).findFirst().orElse(null);
        Creative2Category ic2 = category_map.entrySet().stream().filter(e -> e.getKey().test(i2)).map(Map.Entry::getValue).findFirst().orElse(null);

        if (ic1 == null) System.err.println(i1.toString() + " needs a LCC group category!");
        if (ic2 == null) System.err.println(i2.toString() + " needs a LCC group category!");
        if (ic1 != ic2) return (ic1 == null ? -1 : ic1.getSortValue()) - (ic2 == null ? -1 : ic2.getSortValue());

        Integer iv1 = value_map.entrySet().stream().filter(e -> e.getKey().test(i1)).map(Map.Entry::getValue).findFirst().orElse(null);
        Integer iv2 = value_map.entrySet().stream().filter(e -> e.getKey().test(i2)).map(Map.Entry::getValue).findFirst().orElse(null);

        if (iv1 == null) System.err.println(i1.toString() + " needs a LCC group sort value!");
        if (iv2 == null) System.err.println(i2.toString() + " needs a LCC group sort value!");
        return (iv1 == null ? Integer.MIN_VALUE : iv1) - (iv2 == null ? Integer.MIN_VALUE : iv2);
    };

    public Creative2Group(String label) {
        super(label);
        GROUPS.add(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fill(NonNullList<ItemStack> items) {
        if (this.hijackCreativeScreen()) return;
        super.fill(items);
        items.sort(SORTER);
    }

    public void fillNoSearch(NonNullList<ItemStack> items) {
        this.fill(items);
        this.collapseGroups(items);
        this.addSpacing(items);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getSearchbarWidth() {
        return 139;
    }

    protected boolean hijackCreativeScreen() {
        if (!(Minecraft.getInstance().currentScreen instanceof Creative2Screen)) {
            Minecraft.getInstance().displayGuiScreen(new Creative2Screen(this, LCC.proxy.getClientPlayer()));
            return true;
        }
        return false;
    }

    @Override
    public ResourceLocation getBackgroundImage() {
        return new ResourceLocation(LCC.MODID, "textures/gui/container/creative2.png");
    }

    public abstract void initSorting();

    protected void set(IItemProvider item, Creative2Category category, int sortValue) {
        set(i -> i.getItem().asItem() == item.asItem(), category, sortValue);
    }

    protected <K extends Enum<K>, V extends IItemProvider> void group(Map<K, V> map, Creative2Category category, ToIntFunction<K> sortValue) {
        for (Map.Entry<K, V> e : map.entrySet()) {
            set(e.getValue(), category, sortValue.applyAsInt(e.getKey()));
        }
        groups.add(map);
    }

    protected void set(Predicate<ItemStack> predicate, Creative2Category category, int sortValue) {
        category_map.put(predicate, category);
        value_map.put(predicate, sortValue);
    }

    protected void addSpacing(NonNullList<ItemStack> items) {
        NonNullList<ItemStack> items2 = NonNullList.create();
        int categorySize = 0;
        for (int i = 0; i < items.size(); i++) {
            ItemStack s = items.get(i);
            categorySize++;
            items2.add(s);

            if (i < items.size() - 1) {
                ItemStack s2 = items.get(i + 1);

                Creative2Category ic1 = category_map.entrySet().stream().filter(e -> e.getKey().test(s)).map(Map.Entry::getValue).findFirst().orElse(null);
                Creative2Category ic2 = category_map.entrySet().stream().filter(e -> e.getKey().test(s2)).map(Map.Entry::getValue).findFirst().orElse(null);

                if (ic1 == null) System.err.println(s.toString() + " needs a LCC group category!");
                if (ic2 == null) System.err.println(s2.toString() + " needs a LCC group category!");
                if (ic1 != ic2) {
                    int emptySpaces = 9 - (((categorySize - 1) % 9) + 1);
                    for (int j = 0; j < emptySpaces; j++) {
                        items2.add(ItemStack.EMPTY);
                    }
                    categorySize = 0;
                }
            }
        }
        items.clear();
        items.addAll(items2);
    }

    protected void collapseGroups(NonNullList<ItemStack> items) {
        ListIterator<ItemStack> it = items.listIterator();
        ArrayList<Map<? extends Enum<?>, ? extends IItemProvider>> expandedGroupMark = new ArrayList<>();
        while (it.hasNext()) {
            ItemStack is = it.next();
            Item i = is.getItem();
            expandedGroupMark.clear();
            for (Map<? extends Enum<?>, ? extends IItemProvider> group : groups) {
                int k = group_items.computeIfAbsent(group, g -> 0);
                IItemProvider[] values = group.values().toArray(new IItemProvider[0]);
                if (group.values().stream().map(IItemProvider::asItem).anyMatch(i2 -> i2 == i)) {
                    if (expandedGroups.contains(group)) {
                        if (i.asItem() == values[k].asItem() && !expandedGroupMark.contains(group)) {
                            for (int a = 0; a <= k; a++) it.previous();
                            it.add(is.copy());
                            for (int a = 0; a <= k; a++) it.next();
                            expandedGroupMark.add(group);
                        }
                        break;
                    } else if (i.asItem() != values[k].asItem()) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

    public <V extends IItemProvider> Map<? extends Enum<?>, V> getGroup(V item) {
        return (Map<? extends Enum<?>, V>)groups.stream().filter(m -> m.values().stream().map(IItemProvider::asItem).anyMatch(i -> i.asItem() == item.asItem())).findFirst().orElse(null);
    }

    public <K extends Enum<K>, V extends IItemProvider> Map<K, V> getGroup(V item, Class<K> hint) {
        return (Map<K, V>)getGroup(item);
    }

    public int getGroupSlot(Map<? extends Enum<?>, ? extends IItemProvider> group, NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            int k = i;
            if (group.values().stream().map(IItemProvider::asItem).anyMatch(ii -> ii == items.get(k).getItem())) return i;
        }
        return -1;
    }

    public String getGroupTranslationKey(Map<? extends Enum<?>, IItemProvider> group) {
        return group_translations.computeIfAbsent(group, k -> "itemGroup.lcc.group." + group.values().iterator().next().asItem().getRegistryName().getPath().replaceAll("_[^_]*$", ""));
    }

    public Creative2Category getCategory(ItemStack is) {
        return category_map.entrySet().stream().filter(e -> e.getKey().test(is)).map(Map.Entry::getValue).findFirst().orElse(null);
    }

}
