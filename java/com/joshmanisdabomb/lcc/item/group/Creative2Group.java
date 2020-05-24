package com.joshmanisdabomb.lcc.item.group;

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

    private final HashMap<Predicate<ItemStack>, LCCGroupCategory> category_map = new HashMap<>();
    private final HashMap<Predicate<ItemStack>, Integer> value_map = new HashMap<>();

    public final ArrayList<Map<? extends Enum<?>, ? extends IItemProvider>> groups = new ArrayList<>();
    private final HashMap<Map<? extends Enum<?>, ? extends IItemProvider>, String> group_keys = new HashMap<>();

    private final Comparator<ItemStack> SORTER = (i1, i2) -> {
        if (i1.isEmpty() || i2.isEmpty()) return 0;

        LCCGroupCategory ic1 = null, ic2 = null;
        for (Map.Entry<Predicate<ItemStack>, LCCGroupCategory> e : category_map.entrySet()) {
            if (ic1 != null && ic2 != null) break;
            if (ic1 == null && e.getKey().test(i1)) ic1 = e.getValue();
            if (ic2 == null && e.getKey().test(i2)) ic2 = e.getValue();
        }
        if (ic1 == null) System.err.println(i1.toString() + " needs a LCC group category!");
        if (ic2 == null) System.err.println(i2.toString() + " needs a LCC group category!");
        if (ic1 != ic2) return (ic1 == null ? -1 : ic1.ordinal()) - (ic2 == null ? -1 : ic2.ordinal());

        Integer iv1 = null, iv2 = null;
        for (Map.Entry<Predicate<ItemStack>, Integer> e : value_map.entrySet()) {
            if (iv1 != null && iv2 != null) break;
            if (iv1 == null && e.getKey().test(i1)) iv1 = e.getValue();
            if (iv2 == null && e.getKey().test(i2)) iv2 = e.getValue();
        }
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

    protected void set(IItemProvider item, LCCGroupCategory category, int sortValue) {
        set(i -> i.getItem().asItem() == item.asItem(), category, sortValue);
    }

    protected <K extends Enum<K>, V extends IItemProvider> void group(Map<K, V> map, LCCGroupCategory category, ToIntFunction<K> sortValue) {
        for (Map.Entry<K, V> e : map.entrySet()) {
            set(e.getValue(), category, sortValue.applyAsInt(e.getKey()));
        }
        groups.add(map);
    }

    protected void set(Predicate<ItemStack> predicate, LCCGroupCategory category, int sortValue) {
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

                LCCGroupCategory ic1 = null, ic2 = null;
                for (Map.Entry<Predicate<ItemStack>, LCCGroupCategory> e : category_map.entrySet()) {
                    if (ic1 != null && ic2 != null) break;
                    if (ic1 == null && e.getKey().test(s)) ic1 = e.getValue();
                    if (ic2 == null && e.getKey().test(s2)) ic2 = e.getValue();
                }
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
        Iterator<ItemStack> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next().getItem();
            for (Map<? extends Enum<?>, ? extends IItemProvider> group : groups) {
                if (group.values().stream().skip(1).map(IItemProvider::asItem).anyMatch(i2 -> i2 == i)) {
                    it.remove();
                    break;
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

    public static <K extends Enum<K>, V extends IItemProvider> int getGroupSlot(Map<? extends Enum<?>, ? extends IItemProvider> group, NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            int k = i;
            if (group.values().stream().map(IItemProvider::asItem).anyMatch(ii -> ii == items.get(k).getItem())) return i;
        }
        return -1;
    }

    public String getGroupTranslationKey(Map<? extends Enum<?>, IItemProvider> group) {
        return group_keys.computeIfAbsent(group, k -> "itemGroup.lcc.group." + group.values().iterator().next().asItem().getRegistryName().getPath().replaceAll("_[^_]*$", ""));
    }

    public enum LCCGroupCategory {

        RESOURCES,
        TOOLS,
        GIZMOS,
        RAINBOW,
        SPREADERS,
        WASTELAND,
        NUCLEAR,
        COMPUTING,
        NOSTALGIA,
        POWER,
        HEALTH,
        TESTING

    }

}
