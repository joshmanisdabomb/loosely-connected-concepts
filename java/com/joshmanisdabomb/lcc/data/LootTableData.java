package com.joshmanisdabomb.lcc.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.*;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCEntities;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class LootTableData extends LootTableProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.inverted();
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();

    protected final HashMap<Block, LootTable.Builder> blocks = new HashMap<>();
    protected final HashMap<EntityType, LootTable.Builder> entities = new HashMap<>();
    protected final HashMap<ResourceLocation, LootTable.Builder> chests = new HashMap<>();
    protected final HashMap<ResourceLocation, LootTable.Builder> gameplay = new HashMap<>();
    private final DataGenerator dg;

    public LootTableData(DataGenerator dg) {
        super(dg);
        this.dg = dg;
    }

    protected void addTables() {
        //Blocks
        dropSelf(LCCBlocks.test_block, LCCBlocks.test_block_2, LCCBlocks.test_block_3, LCCBlocks.test_block_4, LCCBlocks.test_block_5);

        dropOreFortune(LCCItems.ruby, LCCBlocks.ruby_ore);
        dropOreFortune(LCCItems.topaz, LCCBlocks.topaz_ore);
        dropOreFortune(LCCItems.sapphire, LCCBlocks.sapphire_ore);
        dropOreFortune(LCCItems.amethyst, LCCBlocks.amethyst_ore);
        dropOreFortune(this.areaRequired(LCCTags.AreaEffectivity.RAINBOW), LCCItems.neon, LCCBlocks.neon_ore);
        blocks.put(LCCBlocks.chancite_ore,
            LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(Items.COAL).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)).weight(7))
                    .addEntry(ItemLootEntry.builder(Items.IRON_INGOT).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)).weight(6))
                    .addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)).weight(3))
                    .addEntry(ItemLootEntry.builder(Items.DIAMOND).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)).weight(1))
                    .acceptCondition(NO_SILK_TOUCH)
            ).addLootPool(this.silkOnly(LCCBlocks.chancite_ore))
        );
        dropSelf(LCCBlocks.uranium_ore);
        dropSelf(LCCBlocks.ruby_storage, LCCBlocks.topaz_storage, LCCBlocks.sapphire_storage, LCCBlocks.amethyst_storage, LCCBlocks.uranium_storage, LCCBlocks.enriched_uranium_storage);
        dropSelf(this.areaRequired(LCCTags.AreaEffectivity.RAINBOW), LCCBlocks.neon_storage);

        dropSelf(LCCBlocks.road, LCCBlocks.hydrated_soul_sand, LCCBlocks.bounce_pad);

        dropSelf(LCCBlocks.spreader_interface);
        dropSpreader(LCCBlocks.spreaders.values().toArray(new SpreaderBlock[0]));

        dropSelf(LCCBlocks.cracked_mud);

        dropSelf(LCCBlocks.atomic_bomb);

        dropSelf(LCCBlocks.time_rift, LCCBlocks.classic_cobblestone, LCCBlocks.classic_planks, LCCBlocks.classic_sapling, LCCBlocks.classic_gravel, LCCBlocks.classic_sponge, LCCBlocks.classic_rose, LCCBlocks.classic_cyan_flower, LCCBlocks.classic_iron_block, LCCBlocks.classic_gold_block, LCCBlocks.classic_diamond_block, LCCBlocks.classic_smooth_iron_block, LCCBlocks.classic_smooth_gold_block, LCCBlocks.classic_smooth_diamond_block, LCCBlocks.classic_bricks, LCCBlocks.classic_mossy_cobblestone, LCCBlocks.classic_chest, LCCBlocks.crying_obsidian, LCCBlocks.glowing_obsidian, LCCBlocks.cog);
        dropSelf(LCCBlocks.classic_cloth.values().toArray(new Block[0]));
        dropSilk(Blocks.DIRT, LCCBlocks.classic_grass_block);
        blocks.put(LCCBlocks.classic_leaves,
            LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(LCCBlocks.classic_leaves)
                        .acceptCondition(SILK_TOUCH_OR_SHEARS)
                        .alternatively(ItemLootEntry.builder(LCCBlocks.classic_sapling).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.0833F, 0.1F)).acceptCondition(SurvivesExplosion.builder()))
                    )
            )
        );
        dropSilkOnly(LCCBlocks.classic_glass);
        dropPot(LCCBlocks.potted_classic_sapling, LCCBlocks.potted_classic_rose, LCCBlocks.potted_classic_cyan_flower);
        blocks.put(LCCBlocks.nether_reactor,
            LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(LCCBlocks.nether_reactor)
                        .acceptCondition(BlockStateProperty.builder(LCCBlocks.nether_reactor).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(NetherReactorBlock.STATE, NetherReactorBlock.ReactorState.READY)))
                        .acceptCondition(SurvivesExplosion.builder())
                    )
            )
        );

        dropSelf(LCCBlocks.rainbow_gate, LCCBlocks.sparkling_dirt, LCCBlocks.candy_cane_block, LCCBlocks.candy_cane_red, LCCBlocks.candy_cane_green, LCCBlocks.candy_cane_blue, LCCBlocks.stripped_candy_cane, LCCBlocks.candy_cane_coating_red, LCCBlocks.candy_cane_coating_green, LCCBlocks.candy_cane_coating_green, LCCBlocks.stripped_candy_cane_coating, LCCBlocks.refined_candy_cane_red, LCCBlocks.refined_candy_cane_green, LCCBlocks.refined_candy_cane_blue, LCCBlocks.refined_stripped_candy_cane, LCCBlocks.refined_candy_cane_coating_red, LCCBlocks.refined_candy_cane_coating_green, LCCBlocks.refined_candy_cane_coating_blue, LCCBlocks.refined_stripped_candy_cane_coating);
        dropSelf(this.areaRequired(LCCTags.AreaEffectivity.RAINBOW), LCCBlocks.twilight_cobblestone);
        dropItem(this.areaRequired(LCCTags.AreaEffectivity.RAINBOW), LCCBlocks.channelite.get(null), LCCBlocks.channelite.values().toArray(new ChanneliteBlock[0]));
        dropSilk(this.areaRequired(LCCTags.AreaEffectivity.RAINBOW), LCCBlocks.twilight_cobblestone, LCCBlocks.twilight_stone);
        dropSilk(LCCBlocks.sparkling_dirt, LCCBlocks.rainbow_grass_block, LCCBlocks.sugar_grass_block, LCCBlocks.star_plating);
        dropSilk(LCCBlocks.sparkling_dirt, LCCBlocks.sparkling_grass_block.values().toArray(new SparklingGrassBlock[0]));
        dropSelf(LCCBlocks.sparkling_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));
        dropSelf(LCCBlocks.twilight_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));

        dropSelf(LCCBlocks.vivid_log, LCCBlocks.vivid_wood, LCCBlocks.vivid_planks, LCCBlocks.vivid_sapling, LCCBlocks.stripped_vivid_log, LCCBlocks.stripped_vivid_wood, LCCBlocks.vivid_stairs, LCCBlocks.vivid_slab, LCCBlocks.vivid_pressure_plate, LCCBlocks.vivid_button, LCCBlocks.vivid_fence, LCCBlocks.vivid_fence_gate, LCCBlocks.vivid_trapdoor);
        blocks.put(LCCBlocks.vivid_leaves,
            LootTable.builder().addLootPool(silkShears(LCCBlocks.vivid_leaves,
                ItemLootEntry.builder(LCCBlocks.vivid_sapling)
                    .acceptCondition(SurvivesExplosion.builder())
                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.083333336F, 0.1F)
                ).acceptCondition(SurvivesExplosion.builder())
            )).addLootPool(LootPool.builder()
                .addEntry(ItemLootEntry.builder(Items.STICK)
                    .acceptCondition(SurvivesExplosion.builder())
                    .acceptCondition(NOT_SILK_TOUCH_OR_SHEARS)
                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))
                    .acceptFunction(SetCount.builder(RandomValueRange.of(1,2)))
                )
            ).addLootPool(LootPool.builder()
                .addEntry(ItemLootEntry.builder(Items.APPLE)
                    .acceptCondition(SurvivesExplosion.builder())
                    .acceptCondition(NOT_SILK_TOUCH_OR_SHEARS)
                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))
                )
            )
        );
        blocks.put(LCCBlocks.vivid_door,
            LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(LCCBlocks.vivid_door)
                        .acceptCondition(BlockStateProperty.builder(LCCBlocks.vivid_door).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoorBlock.HALF, DoubleBlockHalf.LOWER)))
                        .acceptCondition(SurvivesExplosion.builder())
                    )
            )
        );

        //Entities
        entities.put(LCCEntities.classic_zombie_pigman,
            LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(Items.COOKED_PORKCHOP)
                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 0.5F)))
                    )
            )
        );

        //Gameplay
        gameplay.put(new ResourceLocation(LCC.MODID, "gameplay/nether_reactor"),
            LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(RandomValueRange.of(20.0F, 40.0F))
                    .addEntry(ItemLootEntry.builder(Items.GLOWSTONE_DUST).weight(100))
                    .addEntry(ItemLootEntry.builder(Items.QUARTZ).weight(70))
                    .addEntry(ItemLootEntry.builder(Blocks.SOUL_SAND).weight(30))
                    .addEntry(ItemLootEntry.builder(Blocks.BROWN_MUSHROOM).weight(18))
                    .addEntry(ItemLootEntry.builder(Blocks.RED_MUSHROOM).weight(18))
                    .addEntry(ItemLootEntry.builder(Items.SUGAR_CANE).weight(24))
                    .addEntry(ItemLootEntry.builder(Blocks.CACTUS).weight(24))
                    .addEntry(ItemLootEntry.builder(Items.WHEAT_SEEDS).weight(4))
                    .addEntry(ItemLootEntry.builder(Items.MELON_SEEDS).weight(4))
                    .addEntry(ItemLootEntry.builder(Items.PUMPKIN_SEEDS).weight(4))
                    .addEntry(ItemLootEntry.builder(Items.BEETROOT_SEEDS).weight(4))
                    .addEntry(ItemLootEntry.builder(Items.CARROT).weight(4))
                    .addEntry(ItemLootEntry.builder(Items.POTATO).weight(4))
                    .addEntry(ItemLootEntry.builder(Items.COCOA_BEANS).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.BOOK).weight(6))
                    .addEntry(ItemLootEntry.builder(Items.ARROW).weight(8))
                    .addEntry(ItemLootEntry.builder(Items.OAK_SAPLING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.SPRUCE_SAPLING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.BIRCH_SAPLING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.JUNGLE_SAPLING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.ACACIA_SAPLING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.DARK_OAK_SAPLING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.OAK_DOOR).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.SPRUCE_DOOR).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.BIRCH_DOOR).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.JUNGLE_DOOR).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.ACACIA_DOOR).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.DARK_OAK_DOOR).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.RED_BED).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.PAINTING).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.MAP).weight(2))
                    .addEntry(ItemLootEntry.builder(Items.BOW).weight(1))
                    .addEntry(ItemLootEntry.builder(Items.BOW).acceptFunction(EnchantWithLevels.func_215895_a(new RandomValueRange(6.0F, 22.0F))).weight(1))
            )
        );
    }

    //Happy helpers.

    private LootPool.Builder basic(IItemProvider item) {
        return LootPool.builder()
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(item))
            .acceptCondition(SurvivesExplosion.builder());
    }

    private LootPool.Builder silk(IItemProvider silk, LootEntry.Builder<?> nonSilk) {
        return LootPool.builder()
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(silk).acceptCondition(SILK_TOUCH).alternatively(nonSilk))
            .acceptCondition(SurvivesExplosion.builder());
    }

    private LootPool.Builder silkShears(IItemProvider silk, LootEntry.Builder<?> nonSilk) {
        return LootPool.builder()
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(silk).acceptCondition(SILK_TOUCH_OR_SHEARS).alternatively(nonSilk))
            .acceptCondition(SurvivesExplosion.builder());
    }

    private LootPool.Builder silkOnly(IItemProvider silk) {
        return LootPool.builder()
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(silk))
            .acceptCondition(SILK_TOUCH);
    }

    private LootPool.Builder ore(IItemProvider ore, IItemProvider ingot) {
        return silk(ore, ItemLootEntry.builder(ingot).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)));
    }

    private <B extends Block> void dropOneTable(Function<B, LootPool.Builder> pool, Consumer<LootPool.Builder> lp, B... blocks) {
        for (B b : blocks) {
            LootPool.Builder lpb = pool.apply(b);
            lp.accept(lpb);
            this.blocks.put(b, LootTable.builder().addLootPool(lpb));
        }
    }

    private void dropSelf(Consumer<LootPool.Builder> lp, Block... blocks) {
        this.dropOneTable(this::basic, lp, blocks);
    }

    private void dropSelf(Block... blocks) {
        this.dropSelf(lp -> {}, blocks);
    }

    private void dropItem(Consumer<LootPool.Builder> lp, IItemProvider drop, Block... blocks) {
        this.dropOneTable(b -> this.basic(drop), lp, blocks);
    }

    private void dropItem(IItemProvider drop, Block... blocks) {
        this.dropItem(lp -> {}, drop, blocks);
    }

    private void dropSilk(Consumer<LootPool.Builder> lp, IItemProvider nonSilk, Block... blocks) {
        this.dropOneTable(b -> this.silk(b, ItemLootEntry.builder(nonSilk)), lp, blocks);
    }

    private void dropSilk(IItemProvider nonSilk, Block... blocks) {
        this.dropSilk(lp -> {}, nonSilk, blocks);
    }

    private void dropSilkOnly(Consumer<LootPool.Builder> lp, Block... blocks) {
        this.dropOneTable(this::silkOnly, lp, blocks);
    }

    private void dropSilkOnly(Block... blocks) {
        this.dropSilkOnly(lp -> {}, blocks);
    }

    private void dropOreFortune(Consumer<LootPool.Builder> lp, IItemProvider ingot, Block... blocks) {
        this.dropOneTable(b -> this.ore(b, ingot), lp, blocks);
    }

    private void dropOreFortune(IItemProvider nonSilk, Block... blocks) {
        this.dropOreFortune(lp -> {}, nonSilk, blocks);
    }

    private void dropSpreader(Block... blocks) {
        for (Block b : blocks) {
            this.blocks.put(b, LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(b))
                    .acceptCondition(SurvivesExplosion.builder())
                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.1F, 0.2F, 0.35F, 0.6F))
            ));
        }
    }

    private void dropPot(FlowerPotBlock... blocks) {
        for (FlowerPotBlock b : blocks) {
            this.blocks.put(b, LootTable.builder().addLootPool(basic(b.func_220276_d())).addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(b.getEmptyPot()))
                    .acceptCondition(SurvivesExplosion.builder())
            ));
        }
    }

    private Consumer<LootPool.Builder> areaRequired(LCCTags.AreaEffectivity ae) {
        return lp -> lp.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().tag(ae.equipment)));
    }

    //This isn't built into forge for some reason.

    @Override
    public void act(DirectoryCache cache) {
        this.addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : blocks.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        for (Map.Entry<EntityType, LootTable.Builder> entry : entities.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.ENTITY).build());
        }
        for (Map.Entry<ResourceLocation, LootTable.Builder> entry : chests.entrySet()) {
            tables.put(entry.getKey(), entry.getValue().setParameterSet(LootParameterSets.CHEST).build());
        }
        for (Map.Entry<ResourceLocation, LootTable.Builder> entry : gameplay.entrySet()) {
            tables.put(entry.getKey(), entry.getValue().setParameterSet(LootParameterSets.GENERIC).build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.dg.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

}
