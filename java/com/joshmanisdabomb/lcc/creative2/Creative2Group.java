package com.joshmanisdabomb.lcc.creative2;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gui.inventory.Creative2Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public abstract class Creative2Group extends ItemGroup {

    public static final ArrayList<Creative2Group> GROUPS = new ArrayList<>();
    private static final BinaryOperator<ItemStack> MERGE = (a, b) -> {
        throw new IllegalStateException("Duplicate key " + a);
    };

    private final HashMap<Predicate<ItemStack>, Creative2Category> category_map = new HashMap<>();
    private final HashMap<Predicate<ItemStack>, Integer> value_map = new HashMap<>();

    public final ArrayList<Map<?, ItemStack>> groups = new ArrayList<>();
    private final HashMap<Map<?, ItemStack>, String> group_names = new HashMap<>();
    public final HashMap<Map<?, ItemStack>, Integer> group_items = new HashMap<>();

    public final ArrayList<Map<?, ItemStack>> expandedGroups = new ArrayList<>();

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

    @OnlyIn(Dist.CLIENT)
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

    @OnlyIn(Dist.CLIENT)
    protected boolean hijackCreativeScreen() {
        if (!(Minecraft.getInstance().currentScreen instanceof Creative2Screen)) {
            Minecraft.getInstance().displayGuiScreen(new Creative2Screen(this, LCC.proxy.getClientPlayer()));
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBackgroundImage() {
        return new ResourceLocation(LCC.MODID, "textures/gui/container/creative2.png");
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void initSorting();

    @OnlyIn(Dist.CLIENT)
    protected void set(IItemProvider item, Creative2Category category, int sortValue) {
        set(i -> i.getItem().asItem() == item.asItem(), category, sortValue);
    }

    @OnlyIn(Dist.CLIENT)
    protected <K, V extends IItemProvider> void group(Map<K, V> map, Creative2Category category, ToIntBiFunction<K, IItemProvider> sortValue) {
        group(map, category, sortValue, map.values().iterator().next().asItem().getRegistryName().getPath().replaceAll("_[^_]*$", ""));
    }

    @OnlyIn(Dist.CLIENT)
    protected <K, V extends IItemProvider> void group(Map<K, V> map, Creative2Category category, ToIntBiFunction<K, IItemProvider> sortValue, String name) {
        Map<K, ItemStack> collect = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> new ItemStack(v.getValue().asItem()), MERGE, LinkedHashMap::new));
        ToIntBiFunction<K, ItemStack> func = (k, is) -> sortValue.applyAsInt(k, is.getItem().asItem());
        groupStacks(collect, category, func, name);
    }

    @OnlyIn(Dist.CLIENT)
    protected <K, V extends IItemProvider> void group(V item, Function<ItemStack, K> keys, Creative2Category category, ToIntBiFunction<K, ItemStack> sortValue) {
        group(item, keys, category, sortValue, item.asItem().getRegistryName().getPath());
    }

    @OnlyIn(Dist.CLIENT)
    protected <K, V extends IItemProvider> void group(V item, Function<ItemStack, K> keys, Creative2Category category, ToIntBiFunction<K, ItemStack> sortValue, String name) {
        NonNullList<ItemStack> list = Util.make(NonNullList.create(), l -> item.asItem().fillItemGroup(this, l));
        groupStacks(list.stream().collect(Collectors.toMap(keys, v -> v, MERGE, LinkedHashMap::new)), category, sortValue, name);
    }

    @OnlyIn(Dist.CLIENT)
    protected void set(Predicate<ItemStack> predicate, Creative2Category category, int sortValue) {
        category_map.put(predicate, category);
        value_map.put(predicate, sortValue);
    }

    @OnlyIn(Dist.CLIENT)
    protected <K> void groupStacks(Map<K, ItemStack> map, Creative2Category category, ToIntBiFunction<K, ItemStack> sortValue, String name) {
        for (Map.Entry<K, ItemStack> e : map.entrySet()) {
            set(i -> ItemStack.areItemStacksEqual(i, e.getValue()), category, sortValue.applyAsInt(e.getKey(), e.getValue()));
        }
        groups.add(map);
        group_names.put(map, name);
    }

    @OnlyIn(Dist.CLIENT)
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

    @OnlyIn(Dist.CLIENT)
    protected void collapseGroups(NonNullList<ItemStack> items) {
        ListIterator<ItemStack> it = items.listIterator();
        ArrayList<Map<?, ItemStack>> expandedGroupMark = new ArrayList<>();
        while (it.hasNext()) {
            ItemStack is = it.next();
            expandedGroupMark.clear();
            for (Map<?, ItemStack> group : groups) {
                int k = group_items.computeIfAbsent(group, g -> 0);
                ItemStack[] values = group.values().toArray(new ItemStack[0]);
                if (group.values().stream().anyMatch(s -> ItemStack.areItemStacksEqual(s, is))) {
                    if (expandedGroups.contains(group)) {
                        if (ItemStack.areItemStacksEqual(values[k], is) && !expandedGroupMark.contains(group)) {
                            for (int a = 0; a <= k; a++) it.previous();
                            it.add(is.copy());
                            for (int a = 0; a <= k; a++) it.next();
                            expandedGroupMark.add(group);
                        }
                        break;
                    } else if (!ItemStack.areItemStacksEqual(values[k], is)) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public <V extends IItemProvider> Map<?, ItemStack> getGroup(ItemStack is) {
        return groups.stream().filter(m -> m.values().stream().anyMatch(is2 -> ItemStack.areItemStacksEqual(is, is2))).findFirst().orElse(null);
    }

    @OnlyIn(Dist.CLIENT)
    public String getGroupTranslationKey(Map<?, ItemStack> group) {
        return "itemGroup.lcc.group." + group_names.get(group);
    }

    @OnlyIn(Dist.CLIENT)
    public Creative2Category getCategory(ItemStack is) {
        return category_map.entrySet().stream().filter(e -> e.getKey().test(is)).map(Map.Entry::getValue).findFirst().orElse(null);
    }

}
