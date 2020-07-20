package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.item.*;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

import java.util.function.ObjIntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ItemAssetData extends ItemModelProvider implements LCCAssetGenerator<Item> {

    private final DataGenerator dg;
    private final BlockAssetData blocks;

    ModelFile cable;
    ModelFile spreader;

    public ItemAssetData(DataGenerator dg, ExistingFileHelper fileHelper, BlockAssetData blocks) {
        super(dg, LCC.MODID, fileHelper);
        this.dg = dg;
        this.blocks = blocks;
    }

    @Override
    public String getDefaultFolder() {
        return ModelProvider.ITEM_FOLDER;
    }

    private void genericModels() {
        this.cable = this.getBuilder("lcc:cable").element().from(6, 7, 3).to(10, 11, 13)
            .face(Direction.NORTH).uvs(6, 5, 10, 9).texture("#cable").end()
            .face(Direction.EAST).uvs(13, 9, 3, 5).texture("#cable").end()
            .face(Direction.SOUTH).uvs(6, 5, 10, 9).texture("#cable").end()
            .face(Direction.WEST).uvs(3, 5, 13, 9).texture("#cable").end()
            .face(Direction.UP).uvs(6, 3, 10, 13).texture("#cable").end()
            .face(Direction.DOWN).uvs(6, 3, 10, 13).texture("#cable").end()
        .end().transforms()
            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).scale(0.375F).rotation(75, 45, 0).translation(0, 1.5F, 0).end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).scale(0.375F).rotation(75, 45, 0).translation(0, 1.5F, 0).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).scale(0.4F).rotation(0, -48, 0).translation(2.5F, 0, 0).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).scale(0.4F).rotation(0, -48, 0).translation(2.5F, 0, 0).end()
            .transform(ModelBuilder.Perspective.HEAD).scale(1F).translation(0, 7.5F, 0).end()
            .transform(ModelBuilder.Perspective.GROUND).scale(0.25F).rotation(0, 0, 0).translation(0, 3, 0).end()
            .transform(ModelBuilder.Perspective.FIXED).scale(0.5F).rotation(0, 90, 0).translation(0, 0, -1).end()
            .transform(ModelBuilder.Perspective.GUI).scale(0.625F).rotation(30, 225, 0).translation(0, 0, 0).end()
        .end();
        ;
        this.spreader = this.singleTexture("lcc:spreader", path("colorable", ModelProvider.BLOCK_FOLDER), "all", path("spreader/age_0", ModelProvider.BLOCK_FOLDER));
    }

    @Override
    protected void registerModels() {
        this.genericModels();

        this.addAll(this::simpleItem, LCCItems.test_item, LCCItems.plastic, LCCItems.chromatic_core, LCCItems.spreader_essence);

        this.addAll(item -> this.simpleItem(item, path(item, path -> "resources/ingot/" + path)), LCCItems.ruby, LCCItems.topaz, LCCItems.sapphire, LCCItems.amethyst, LCCItems.uranium, LCCItems.neon);
        this.addAll(item -> this.simpleItem(item, path(item, path -> "resources/nugget/" + path.replace("_nugget", ""))), LCCItems.uranium_nugget, LCCItems.neon_nugget);
        this.simpleItem(LCCItems.enriched_uranium, path("resources/ingot/uranium_enriched"));
        this.simpleItem(LCCItems.enriched_uranium_nugget, path("resources/nugget/uranium_enriched"));

        //Automated Equipment
        this.addAll(item -> this.handheldItem(item, path(item, path -> "equipment/" + path)), LCCItems.all.stream().filter(i -> i instanceof SwordItem || i instanceof PickaxeItem || i instanceof ShovelItem || i instanceof AxeItem || i instanceof HoeItem).toArray(Item[]::new));
        this.addAll(item -> this.simpleItem(item, path(item, path -> "equipment/" + path)), LCCItems.all.stream().filter(i -> i instanceof CustomArmorItem).toArray(Item[]::new));

        this.addAll(item -> this.simpleItem(item, path(item, path -> "wasteland/" + path)), LCCItems.oil_bucket);

        this.addAll(item -> this.simpleItem(item, path(item, path -> "computing/" + path)), LCCItems.chipset);
        this.stepPredicatedItem(LCCItems.microchip, "computing/microchip", VisualStackItem.PREDICATE, IntStream.range(1,9).mapToDouble(i -> i / 8D).toArray(), (m, value) -> {});
        this.stepPredicatedItem(LCCItems.cpu, "computing/cpu", ComputingItem.PREDICATE, IntStream.range(1,4).mapToDouble(i -> i).toArray(), (m, value) -> {});
        this.stepPredicatedItem(LCCItems.ram, "computing/ram", ComputingItem.PREDICATE, IntStream.range(1,5).mapToDouble(i -> i).toArray(), (m, value) -> {});
        this.stepPredicatedItem(LCCItems.gpu, "computing/gpu", ComputingItem.PREDICATE, IntStream.range(1,7).mapToDouble(i -> i).toArray(), (m, value) -> {});
        this.addAll(item -> this.simpleItem(item, path(item, path -> "computing/" + path + "_base")).texture("layer1", path(item, path -> "computing/" + path + "_color")), LCCItems.floppy_disk, LCCItems.compact_disc, LCCItems.memory_card, LCCItems.solid_state_drive, LCCItems.m2);
        this.stepPredicatedItem(LCCItems.memory_stick, "computing/memory_stick_base", ComputingItem.PREDICATE, new double[] {1.5, 3.4}, (m, index) -> m.texture("layer1", path("computing/memory_stick_color_" + index))).texture("layer1", path("computing/memory_stick_color_1"));
        this.stepPredicatedItem(LCCItems.hard_disk_drive, "computing/hard_disk_drive_base", ComputingItem.PREDICATE, new double[] {3, 5}, (m, index) -> m.texture("layer1", path("computing/hard_disk_drive_color_" + index))).texture("layer1", path("computing/hard_disk_drive_color_1"));

        this.addAll(item -> this.simpleItem(item, path(item, path -> "heart/" + path.replace("_heart", ""))), LCCItems.all.stream().filter(i -> i instanceof HeartItem).toArray(Item[]::new));

        this.getBuilder(name(LCCItems.gauntlet)).parent(new ModelFile.UncheckedModelFile("builtin/entity")).transforms()
            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).scale(1).rotation(90, 180, 0).translation(-8, 6, 6).end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).scale(-1, 1, 1).rotation(90, 180, 0).translation(-8, 6, 6).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).scale(1.0F).rotation(0, 0, 111.5F).translation(4.25F, 2.5F, 0).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).scale(1.0F).rotation(0, 180, -111.5F).translation(4.25F, 2.5F, -16).end()
            .transform(ModelBuilder.Perspective.HEAD).scale(2, 1.5F, 1.5F).rotation(180, 0, 0).translation(16, -7.75F, -12.25F).end()
            .transform(ModelBuilder.Perspective.GROUND).scale(-0.5F, 0.5F, -0.5F).rotation(180, 0, 0).translation(-4, 2, 4).end()
            .transform(ModelBuilder.Perspective.FIXED).scale(-1, 1, -1).rotation(180, -90, 0).translation(8, -0.5F, 8).end()
            .transform(ModelBuilder.Perspective.GUI).scale(-0.875F, 0.875F, -0.875F).rotation(-150, 45, 0).translation(-10, -0.375F, 0).end()
            .end();
        this.cubeAll(name(LCCBlocks.crying_obsidian.asItem()), path("nostalgia/crying_obsidian_static", ModelProvider.BLOCK_FOLDER));

        //BLockItems
        this.addAll(item -> this.simpleItem(item, path(item, path -> "nostalgia/" + path.replace("classic_", ""), ModelProvider.BLOCK_FOLDER)), Stream.of(LCCBlocks.classic_sapling, LCCBlocks.classic_rose, LCCBlocks.classic_cyan_flower, LCCBlocks.cog).map(Block::asItem).toArray(Item[]::new));
        this.simpleBlock(LCCBlocks.atomic_bomb.asItem(), path("template_atomic_bomb"))
            .texture("1", path("atomic_bomb/tail_side", ModelProvider.BLOCK_FOLDER))
            .texture("2", path("atomic_bomb/tail", ModelProvider.BLOCK_FOLDER))
            .texture("3", path("atomic_bomb/fin", ModelProvider.BLOCK_FOLDER))
            .texture("4", path("atomic_bomb/core", ModelProvider.BLOCK_FOLDER))
            .texture("5", path("atomic_bomb/head", ModelProvider.BLOCK_FOLDER));
        this.addAll(item -> this.simpleBlock(item, this.spreader.getLocation()), LCCBlocks.spreaders.values().stream().map(Block::asItem).toArray(Item[]::new));
        this.addAll(item -> this.simpleBlock(item, this.cable.getLocation()).texture("cable", item == LCCBlocks.networking_cable.asItem() ? mcLoc(ModelProvider.BLOCK_FOLDER + "/lime_wool") : mcLoc(ModelProvider.BLOCK_FOLDER + "/purple_wool")), LCCBlocks.networking_cable.asItem(), LCCBlocks.terminal_cable.asItem());
        this.orientable(name(LCCBlocks.classic_chest.asItem()), path("nostalgia/chest_side", ModelProvider.BLOCK_FOLDER), path("nostalgia/chest_front", ModelProvider.BLOCK_FOLDER), path("nostalgia/chest_top", ModelProvider.BLOCK_FOLDER));
        this.simpleBlock(LCCBlocks.nether_reactor.asItem(), path("nether_reactor_ready", ModelProvider.BLOCK_FOLDER));
        this.simpleBlock(LCCBlocks.channelite.get(null).asItem(), path("channelite_empty_none", ModelProvider.BLOCK_FOLDER));
        this.simpleBlock(LCCBlocks.bounce_pad.asItem(), path("template_bounce_pad"))
            .texture("0", path("bounce_pad/base_h", ModelProvider.BLOCK_FOLDER))
            .texture("1", path("bounce_pad/inner", ModelProvider.BLOCK_FOLDER))
            .texture("2", path("bounce_pad/setting", ModelProvider.BLOCK_FOLDER))
            .texture("3", path("bounce_pad/base_v", ModelProvider.BLOCK_FOLDER))
            .texture("4", path("bounce_pad/pad", ModelProvider.BLOCK_FOLDER))
            .texture("5", path("bounce_pad/pad_side", ModelProvider.BLOCK_FOLDER));
        this.simpleBlock(LCCBlocks.rainbow_gate.asItem(), mcLoc(ModelProvider.BLOCK_FOLDER + "/block"))
            .element().from(5, 0, 5).to(11, 16, 11)
                .face(Direction.NORTH).uvs(5, 0, 11, 16).texture("#1").end()
                .face(Direction.EAST).uvs(5, 0, 11, 16).texture("#1").end()
                .face(Direction.SOUTH).uvs(5, 0, 11, 16).texture("#1").end()
                .face(Direction.WEST).uvs(5, 0, 11, 16).texture("#1").end()
                .face(Direction.UP).uvs(5, 5, 11, 11).texture("#0").end()
                .face(Direction.DOWN).uvs(5, 5, 11, 11).texture("#0").end()
            .end().transforms()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).scale(0.375F).rotation(75, 45, 0).translation(0, 1.5F, 0).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).scale(0.375F).rotation(75, 45, 0).translation(0, 1.5F, 0).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).scale(0.4F).rotation(0, 45, 0).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).scale(0.4F).rotation(0, 225, 0).end()
                .transform(ModelBuilder.Perspective.HEAD).translation(0, 14.25F, 0).end()
                .transform(ModelBuilder.Perspective.GROUND).scale(0.25F).rotation(0, 0, 0).translation(0, 3, 0).end()
                .transform(ModelBuilder.Perspective.FIXED).scale(0.5F).end()
                .transform(ModelBuilder.Perspective.GUI).scale(0.625F).rotation(30, 225, 0).end()
            .end()
            .texture("0", path("rainbow/gate/base", ModelProvider.BLOCK_FOLDER))
            .texture("1", path("rainbow/gate/item", ModelProvider.BLOCK_FOLDER));
        this.getBuilder(name(LCCBlocks.time_rift.asItem())).parent(new ModelFile.UncheckedModelFile("builtin/entity")).transforms()
            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).scale(0.375F).rotation(75, 45, 0).translation(0, 2.5F, 0).end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).scale(0.375F).rotation(75, 45, 0).translation(0, 2.5F, 0).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).scale(0.4F).rotation(0, 45, 0).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).scale(0.4F).rotation(0, 225, 0).end()
            .transform(ModelBuilder.Perspective.GROUND).scale(0.25F).rotation(0, 0, 0).translation(0, 3, 0).end()
            .transform(ModelBuilder.Perspective.FIXED).scale(0.5F).end()
            .transform(ModelBuilder.Perspective.GUI).scale(0.625F).rotation(30, 225, 0).end()
            .end();
        this.cubeAll(name(LCCBlocks.test_block_5.asItem()), path("test/5/default", ModelProvider.BLOCK_FOLDER));

        this.simpleItem(LCCBlocks.vivid_sapling.asItem(), path("rainbow/wood/sapling", ModelProvider.BLOCK_FOLDER));
        this.simpleItem(LCCBlocks.vivid_door.asItem(), path("rainbow/door"));
        this.simpleBlock(LCCBlocks.vivid_button.asItem(), mcLoc(ModelProvider.BLOCK_FOLDER + "/button_inventory")).texture("texture", path("rainbow/wood/planks", ModelProvider.BLOCK_FOLDER));
        this.simpleBlock(LCCBlocks.vivid_fence.asItem(), mcLoc(ModelProvider.BLOCK_FOLDER + "/fence_inventory")).texture("texture", path("rainbow/wood/planks", ModelProvider.BLOCK_FOLDER));
        this.simpleBlock(LCCBlocks.vivid_trapdoor.asItem(), path(name(LCCBlocks.vivid_trapdoor.asItem()) + "_bottom", ModelProvider.BLOCK_FOLDER));

        this.addAll(item -> this.simpleBlock(item, path(item, path -> item.getModule().name().toLowerCase(), ModelProvider.BLOCK_FOLDER)), LCCItems.all.stream().filter(bi -> bi instanceof ComputingBlockItem).toArray(ComputingBlockItem[]::new));

        this.addAll(this::simpleBlock, LCCBlocks.allItem.stream().filter(bi -> !this.generatedModels.containsKey(path(bi.getRegistryName().getPath()))).toArray(Item[]::new));
    }

    @Override
    public String getName() {
        return "Item Models";
    }

    //Happy helpers.
    private ItemModelBuilder simpleItem(Item item) {
        return this.simpleItem(item, path(name(item)));
    }

    private ItemModelBuilder simpleItem(Item item, ResourceLocation texture) {
        return this.simpleItem(name(item), texture);
    }

    private ItemModelBuilder simpleItem(String name, ResourceLocation texture) {
        return this.singleTexture(name, mcLoc(ModelProvider.ITEM_FOLDER + "/generated"), "layer0", texture);
    }

    private ItemModelBuilder simpleBlock(Item item) {
        return this.withExistingParent(name(item), path(name(item), ModelProvider.BLOCK_FOLDER));
    }

    private ItemModelBuilder simpleBlock(Item item, ResourceLocation parent) {
        return this.withExistingParent(name(item), parent);
    }

    private ItemModelBuilder stepPredicatedItem(Item item, String texture, ResourceLocation predicate, double[] values, ObjIntConsumer<ItemModelBuilder> model) {
        ItemModelBuilder builder = this.simpleItem(item, path(texture + "_1"));
        builder.override().predicate(predicate, 0).model(builder).end();
        for (int i = 0; i < values.length; i++) {
            float value = (float)values[i];
            ItemModelBuilder b = this.simpleItem(name(item) + "_" + (i + 2), path(texture + "_" + (i + 2)));
            model.accept(b, (i + 2));
            builder.override().predicate(predicate, value).model(b).end();
        }
        return builder;
    }

    private ItemModelBuilder handheldItem(Item item, ResourceLocation texture) {
        return this.singleTexture(name(item), mcLoc(ModelProvider.ITEM_FOLDER + "/handheld"), "layer0", texture);
    }

}
