package com.joshmanisdabomb.lcc.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCDimensions;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class AdvancementData extends AdvancementProvider {

    private final DataGenerator dg;

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public AdvancementData(DataGenerator dg) {
        super(dg);
        this.dg = dg;
    }

    private void registerAdvancements(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.builder()
            .withDisplay(LCCBlocks.test_block,
                new TranslationTextComponent("advancements.lcc.main.root.title"),
                new TranslationTextComponent("advancements.lcc.main.root.description"),
                new ResourceLocation(LCC.MODID, "textures/gui/advancements/backgrounds/main.png"),
                FrameType.TASK, false, false, false)
            .withCriterion("ruby", InventoryChangeTrigger.Instance.forItems(LCCItems.ruby))
            .withCriterion("topaz", InventoryChangeTrigger.Instance.forItems(LCCItems.topaz))
            .withCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
            .withCriterion("diamond", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND))
            .withCriterion("sapphire", InventoryChangeTrigger.Instance.forItems(LCCItems.sapphire))
            .withCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(LCCItems.amethyst))
            .withRequirementsStrategy(IRequirementsStrategy.OR)
            .register(consumer, "lcc:main/root");

        Advancement all_gems = Advancement.Builder.builder()
            .withParent(root)
            .withDisplay(LCCItems.topaz,
                new TranslationTextComponent("advancements.lcc.main.all_gems.title"),
                new TranslationTextComponent("advancements.lcc.main.all_gems.description"),
                null,
                FrameType.TASK, true, true, false)
            .withCriterion("ruby", InventoryChangeTrigger.Instance.forItems(LCCItems.ruby))
            .withCriterion("topaz", InventoryChangeTrigger.Instance.forItems(LCCItems.topaz))
            .withCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
            .withCriterion("diamond", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND))
            .withCriterion("sapphire", InventoryChangeTrigger.Instance.forItems(LCCItems.sapphire))
            .withCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(LCCItems.amethyst))
            .withRequirementsStrategy(IRequirementsStrategy.AND)
            .register(consumer, "lcc:main/all_gems");

        Advancement rainbow_dimension = Advancement.Builder.builder()
            .withParent(all_gems)
            .withDisplay(LCCBlocks.rainbow_grass_block,
                new TranslationTextComponent("advancements.lcc.main.rainbow_dimension.title"),
                new TranslationTextComponent("advancements.lcc.main.rainbow_dimension.description"),
                null,
                FrameType.TASK, true, true, false)
            .withCriterion("entered_rainbow", ChangeDimensionTrigger.Instance.changedDimensionTo(LCCDimensions.rainbow.getType()))
            .register(consumer, "lcc:main/rainbow_dimension");

        Advancement enrich_uranium = Advancement.Builder.builder()
            .withParent(root)
            .withDisplay(LCCItems.enriched_uranium,
                new TranslationTextComponent("advancements.lcc.main.enrich.title"),
                new TranslationTextComponent("advancements.lcc.main.enrich.description"),
                null,
                FrameType.TASK, true, true, false)
            .withCriterion("nugget", InventoryChangeTrigger.Instance.forItems(LCCItems.enriched_uranium_nugget))
            .withCriterion("ingot", InventoryChangeTrigger.Instance.forItems(LCCItems.enriched_uranium))
            .withCriterion("block", InventoryChangeTrigger.Instance.forItems(LCCBlocks.enriched_uranium_storage))
            .withRequirementsStrategy(IRequirementsStrategy.OR)
            .register(consumer, "lcc:main/enrich_uranium");

        /*Advancement enrich_uranium_first = Advancement.Builder.builder()
            .withParent(root)
            .withDisplay(LCCBlocks.atomic_bomb,
                new TranslationTextComponent("advancements.lcc.main.enrich.title"),
                new TranslationTextComponent("advancements.lcc.main.enrich.description"),
                null,
                FrameType.CHALLENGE, true, true, true)
            .withCriterion("first", first to acheieve above advancement)
            .withRequirementsStrategy(IRequirementsStrategy.OR)
            .withRewards(AdvancementRewards.Builder.experience(50))
            .register(consumer, "lcc:main/enrich_uranium_first");*/

        //TODO be in nuclear winter 5 and then in nuclear winter 0 in the same world - consumed by darkness challenge
        //TODO satellite race, space race

        Advancement rainbow_root = Advancement.Builder.builder()
            .withDisplay(LCCBlocks.rainbow_grass_block,
                new TranslationTextComponent("advancements.lcc.rainbow.root.title"),
                new TranslationTextComponent("advancements.lcc.rainbow.root.description"),
                new ResourceLocation(LCC.MODID, "textures/gui/advancements/backgrounds/rainbow.png"),
                FrameType.TASK, false, false, false)
            .withCriterion("entered_rainbow", ChangeDimensionTrigger.Instance.changedDimensionTo(LCCDimensions.rainbow.getType()))
            .register(consumer, "lcc:rainbow/root");

        Advancement twilight_stone = Advancement.Builder.builder()
            .withParent(rainbow_root)
            .withDisplay(LCCBlocks.twilight_stone,
                new TranslationTextComponent("advancements.lcc.rainbow.stone.title"),
                new TranslationTextComponent("advancements.lcc.rainbow.stone.description"),
                null,
                FrameType.TASK, true, true, false)
            .withCriterion("stone", InventoryChangeTrigger.Instance.forItems(LCCBlocks.twilight_stone))
            .withCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(LCCBlocks.twilight_cobblestone))
            .withRequirementsStrategy(IRequirementsStrategy.OR)
            .register(consumer, "lcc:rainbow/stone");

        Advancement neon = Advancement.Builder.builder()
            .withParent(twilight_stone)
            .withDisplay(LCCItems.neon,
                new TranslationTextComponent("advancements.lcc.rainbow.neon.title"),
                new TranslationTextComponent("advancements.lcc.rainbow.neon.description"),
                null,
                FrameType.TASK, true, true, false)
            .withCriterion("neon", InventoryChangeTrigger.Instance.forItems(LCCItems.neon))
            .register(consumer, "lcc:rainbow/neon");
    }

    @Override
    @AdaptedFromSource
    public void act(DirectoryCache cache) {
        Path path = this.dg.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate advancement " + p_204017_3_.getId());
            } else {
                Path path1 = path.resolve("data/" + p_204017_3_.getId().getNamespace() + "/advancements/" + p_204017_3_.getId().getPath() + ".json");

                try {
                    IDataProvider.save(GSON, cache, p_204017_3_.copy().serialize(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }
            }
        };

        this.registerAdvancements(consumer);
    }
}
