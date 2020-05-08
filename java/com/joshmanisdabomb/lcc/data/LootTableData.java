package com.joshmanisdabomb.lcc.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.*;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCEntities;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
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

public class LootTableData extends LootTableProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.inverted();
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);

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
        dropOreFortune(LCCItems.neon, LCCBlocks.neon_ore);
        dropSelf(LCCBlocks.uranium_ore);
        dropSelf(LCCBlocks.ruby_storage, LCCBlocks.topaz_storage, LCCBlocks.sapphire_storage, LCCBlocks.amethyst_storage, LCCBlocks.uranium_storage, LCCBlocks.enriched_uranium_storage, LCCBlocks.neon_storage);

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

        dropSelf(LCCBlocks.rainbow_gate, LCCBlocks.sparkling_dirt, LCCBlocks.twilight_cobblestone, LCCBlocks.candy_cane_block, LCCBlocks.candy_cane_red, LCCBlocks.candy_cane_green, LCCBlocks.candy_cane_blue, LCCBlocks.stripped_candy_cane, LCCBlocks.candy_cane_coating_red, LCCBlocks.candy_cane_coating_green, LCCBlocks.candy_cane_coating_green, LCCBlocks.stripped_candy_cane_coating, LCCBlocks.refined_candy_cane_red, LCCBlocks.refined_candy_cane_green, LCCBlocks.refined_candy_cane_blue, LCCBlocks.refined_stripped_candy_cane, LCCBlocks.refined_candy_cane_coating_red, LCCBlocks.refined_candy_cane_coating_green, LCCBlocks.refined_candy_cane_coating_blue, LCCBlocks.refined_stripped_candy_cane_coating);
        dropItem(LCCBlocks.channelite.get(null), LCCBlocks.channelite.values().toArray(new ChanneliteBlock[0]));
        dropSilk(LCCBlocks.twilight_cobblestone, LCCBlocks.twilight_stone);
        dropSilk(LCCBlocks.sparkling_dirt, LCCBlocks.rainbow_grass_block, LCCBlocks.sugar_grass_block, LCCBlocks.star_plating);
        dropSilk(LCCBlocks.sparkling_dirt, LCCBlocks.sparkling_grass_block.values().toArray(new SparklingGrassBlock[0]));
        dropSelf(LCCBlocks.sparkling_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));
        dropSelf(LCCBlocks.twilight_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));

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

    private LootTable.Builder basic(IItemProvider item) {
        return LootTable.builder().addLootPool(
            LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(item))
                .acceptCondition(SurvivesExplosion.builder())
        );
    }

    private LootTable.Builder silk(IItemProvider silk, LootEntry.Builder<?> nonSilk) {
        return LootTable.builder().addLootPool(
            LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(silk).acceptCondition(SILK_TOUCH).alternatively(nonSilk))
                .acceptCondition(SurvivesExplosion.builder())
        );
    }

    private void dropSelf(Block... blocks) {
        for (Block b : blocks) {
            this.blocks.put(b, basic(b));
        }
    }

    private void dropItem(IItemProvider drop, Block... blocks) {
        for (Block b : blocks) {
            this.blocks.put(b, basic(drop));
        }
    }

    private void dropSilk(IItemProvider nonSilk, Block... blocks) {
        for (Block b : blocks) {
            this.blocks.put(b, silk(b, ItemLootEntry.builder(nonSilk)));
        }
    }

    private void dropSilkOnly(Block... blocks) {
        for (Block b : blocks) {
            this.blocks.put(b, LootTable.builder().addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(b))
                    .acceptCondition(SILK_TOUCH)
            ));
        }
    }

    private void dropOreFortune(IItemProvider nonSilk, Block... blocks) {
        for (Block b : blocks) {
            this.blocks.put(b, silk(b, ItemLootEntry.builder(nonSilk).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))));
        }
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
            this.blocks.put(b, basic(b.func_220276_d()).addLootPool(
                LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(b.getEmptyPot()))
                    .acceptCondition(SurvivesExplosion.builder())
            ));
        }
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
