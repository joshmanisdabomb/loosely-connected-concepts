package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.*;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.misc.Util;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class BlockAssetData extends BlockStateProvider implements LCCAssetGenerator<Block> {

    private final DataGenerator dg;

    ModelFile noshadeCube;
    ModelFile noshadeCubeColumn;
    ModelFile overlay;
    ModelFile grassBlockIndex1;
    ModelFile colorable;
    ModelFile colorableOrientable;
    ModelFile computing;
    ModelFile computing_top;

    private HashMap<ComputingModule.Type, ModelFile[]> computingMap = new HashMap<>();

    public BlockAssetData(DataGenerator dg, ExistingFileHelper fileHelper) {
        super(dg, LCC.MODID, fileHelper);
        this.dg = dg;
    }

    @Override
    public String getDefaultFolder() {
        return ModelProvider.BLOCK_FOLDER;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        super.act(cache);

        //Fix blockstate files to be lowercase.
        this.forceBlockstateLowercase(LCCBlocks.classic_chest);
        this.forceBlockstateLowercase(LCCBlocks.vivid_button);
    }

    private void genericModels() {
        this.noshadeCube = models().getBuilder("lcc:noshade_cube").ao(false)
            .element().allFaces((dir, face) -> face.texture("#" + dir.getName()).cullface(dir)).shade(false).end()
            .transforms()
            .transform(ModelBuilder.Perspective.GUI).rotation(30, 225, 0).scale(0.625f).end()
            .transform(ModelBuilder.Perspective.GROUND).translation(0, 3, 0).scale(0.25f).end()
            .transform(ModelBuilder.Perspective.FIXED).scale(0.5f).end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(75, 45, 0).translation(0, 2.5f, 0).scale(0.375f).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, 45, 0).scale(0.4f).end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, 225, 0).scale(0.4f).end()
            .end();
        this.noshadeCubeColumn = models().withExistingParent("lcc:noshade_cube_column", this.noshadeCube.getLocation()).ao(false)
            .texture("particle", "#side")
            .texture("down", "#end")
            .texture("up", "#end")
            .texture("north", "#side")
            .texture("east", "#side")
            .texture("south", "#side")
            .texture("west", "#side");
        this.overlay = models().withExistingParent("lcc:overlay", "block")
            .element().from(0,0,0).to(16,16,16).allFaces((dir, face) -> face.uvs(0,0,16,16).texture("#base").cullface(dir)).end()
            .element().from(0,0,0).to(16,16,16).allFaces((dir, face) -> face.uvs(0,0,16,16).texture("#overlay").cullface(dir)).end();
        this.grassBlockIndex1 = models().withExistingParent("lcc:grass_block_index_1", "block")
            .element().from(0,0,0).to(16,16,16).allFaces((dir, face) -> face.uvs(0,0,16,16).texture(dir == Direction.UP ? "#top" : (dir == Direction.DOWN ? "#bottom" : "#side")).tintindex(dir == Direction.UP ? 1 : -1).cullface(dir)).end()
            .element().from(0,0,0).to(16,16,16)
            .face(Direction.NORTH).uvs(0,0,16,16).texture("#overlay").tintindex(1).cullface(Direction.NORTH).end()
            .face(Direction.EAST).uvs(0,0,16,16).texture("#overlay").tintindex(1).cullface(Direction.EAST).end()
            .face(Direction.SOUTH).uvs(0,0,16,16).texture("#overlay").tintindex(1).cullface(Direction.SOUTH).end()
            .face(Direction.WEST).uvs(0,0,16,16).texture("#overlay").tintindex(1).cullface(Direction.WEST).end()
            .end();
        this.colorableOrientable = models().withExistingParent("lcc:colorable_orientable", "block")
            .element().from(0,0,0).to(16,16,16).allFaces((dir, face) -> face.uvs(0,0,16,16).texture(dir.getAxis().isVertical() ? "#top" : (dir == Direction.NORTH ? "#front" : "#side")).tintindex(0).cullface(dir)).end();
        this.colorable = models().withExistingParent("lcc:colorable", this.colorableOrientable.getLocation())
            .texture("particle", "#all")
            .texture("top", "#all")
            .texture("front", "#all")
            .texture("side", "#all");

        this.computing = models().withExistingParent("lcc:computing", "block")
            .element().from(0,0,0).to(16,8,16).allFaces((dir, face) -> face.uvs(0, 0, 16, dir.getAxis().isVertical() ? 16 : 8).cullface(dir == Direction.UP ? null : dir).texture(dir.getAxis().isVertical() ? "#top" : "#side").tintindex(1)).end()
            .element().from(0,0,0).to(16,8,16).face(Direction.NORTH).uvs(0, 8, 16, 16).cullface(Direction.NORTH).texture("#front").tintindex(1).end().end()
            .element().from(0,0,0).to(16,8,16).face(Direction.NORTH).uvs(0, 8, 16, 16).cullface(Direction.NORTH).texture("#frontwhite").end().end();
        this.computing_top = models().withExistingParent("lcc:computing_top", "block")
            .element().from(0,8,0).to(16,16,16).allFaces((dir, face) -> face.uvs(0, dir.getAxis().isVertical() ? 0 : 8, 16, 16).cullface(dir == Direction.DOWN ? null : dir).texture(dir.getAxis().isVertical() ? "#top" : "#side").tintindex(2)).end()
            .element().from(0,8,0).to(16,16,16).face(Direction.NORTH).uvs(0, 0, 16, 8).cullface(Direction.NORTH).texture("#front").tintindex(2).end().end()
            .element().from(0,8,0).to(16,16,16).face(Direction.NORTH).uvs(0, 0, 16, 8).cullface(Direction.NORTH).texture("#frontwhite").end().end();
        for (ComputingModule.Type mod : ComputingModule.Type.values()) {
            ModelFile[] models = new ModelFile[8];
            for (int i = 0; i < 8; i++) {
                String module = mod.name().toLowerCase();
                String suffix = (i % 4 == 0 ? "" : ("_" + (i % 4 >= 2 ? "u" : "") + (i % 2 == 1 ? "d" : "")));
                models[i] = models().withExistingParent("lcc:" + module + (i >= 4 ? "_top" : "") + suffix, (i >= 4 ? this.computing_top : this.computing).getLocation())
                    .texture("top", path("computing/casing_top"))
                    .texture("side", path("computing/casing_side" + suffix))
                    .texture("front", path("computing/" + module + "_front"))
                    .texture("frontwhite", path("computing/" + (mod == ComputingModule.Type.COMPUTER ? module : "casing") + "_frontwhite"))
                    .texture("particle", "#top");
            }
            computingMap.put(mod, models);
        }
    }

    @Override
    protected void registerStatesAndModels() {
        this.genericModels();

        //Blockstates
        this.simpleBlock(LCCBlocks.test_block, path("test/1"));
        this.horizontalBlock(LCCBlocks.test_block_2, path("test/2_side"), path("test/2_front"), path("test/2_top"));
        this.directionalBlock(LCCBlocks.test_block_3, models().orientableVertical(name(LCCBlocks.test_block_3), path("test/2_side"), path("test/2_front")));
        this.axisBlock(LCCBlocks.test_block_4, path("test/4"));
        this.simpleBlock(LCCBlocks.test_block_5, models().withExistingParent(name(LCCBlocks.test_block_5), "block").texture("particle", path("test/5/default")));

        this.addAll(this::simpleBlock, LCCBlocks.hydrated_soul_sand);
        this.addAll(block -> this.simpleBlock(block, model -> ConfiguredModel.allYRotations(model, 0, false)), LCCBlocks.nuclear_waste);
        this.simpleBlock(LCCBlocks.hydrated_soul_sand_bubble_column, models().withExistingParent(name(LCCBlocks.hydrated_soul_sand_bubble_column), "block"));
        this.simpleBlock(LCCBlocks.road,
            models().withExistingParent(name(LCCBlocks.road), "block").element().from(0,0,0).to(16,15,16).allFaces((dir, face) -> face.uvs(0, dir.getAxis().isHorizontal() ? 1 : 0, 16, 16).cullface(dir == Direction.UP ? null : dir).texture(dir == Direction.UP ? "#top" : (dir == Direction.DOWN ? "#bottom" : "#side"))).end()
                .texture("particle", path("road/side_base"))
                .texture("top", path("road/top_default"))
                .texture("side", path("road/side_base"))
                .texture("bottom", path("road/bottom_base"))
        );

        this.addAll(block -> this.simpleBlock(block, path(block, path -> "resources/ore/" + path.replace("_ore", ""))), LCCBlocks.ruby_ore, LCCBlocks.topaz_ore, LCCBlocks.sapphire_ore, LCCBlocks.amethyst_ore, LCCBlocks.uranium_ore, LCCBlocks.neon_ore);
        this.addAll(block -> this.simpleBlock(block, path(block, path -> "resources/storage/" + path.replace("_storage", ""))), LCCBlocks.ruby_storage, LCCBlocks.topaz_storage, LCCBlocks.sapphire_storage, LCCBlocks.amethyst_storage, LCCBlocks.uranium_storage, LCCBlocks.neon_storage);
        this.simpleBlock(LCCBlocks.enriched_uranium_storage, path("resources/storage/uranium_enriched"));

        this.simpleBlock(LCCBlocks.rainbow_gate, models().withExistingParent(name(LCCBlocks.rainbow_gate), "block").texture("particle", path("rainbow/gate/base")));
        this.getVariantBuilder(LCCBlocks.rainbow_portal)
            .forAllStates(state -> {
                int y = state.get(RainbowPortalBlock.Y);
                Direction.Axis a = state.get(RainbowPortalBlock.AXIS);
                ResourceLocation texture = path("rainbow/gate/portal_" + y);
                if (state.get(RainbowPortalBlock.MIDDLE)) {
                    return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(name(LCCBlocks.rainbow_portal) + "_" + y + "_invisible", "block").texture("particle", texture))
                        .rotationY(a == Direction.Axis.Z ? 90 : 0)
                        .build();
                } else {
                    return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(name(LCCBlocks.rainbow_portal) + "_" + y, "block")
                            .element().from(-8, 0, 7).to(24, 16, 9)
                            .face(Direction.NORTH).uvs(0, 0, 16, 16).end()
                            .face(Direction.SOUTH).uvs(0, 0, 16, 16).end()
                            .texture("#portal").end().texture("portal", texture).texture("particle", texture)
                        )
                        .rotationY(a == Direction.Axis.Z ? 90 : 0)
                        .build();
                }
            });
        this.addAll(block -> {
            BlockModelBuilder normal = models().cubeAll(name(block), path(block, path -> path.replace("twilight_", "rainbow/")));
            BlockModelBuilder mirrored = models().singleTexture(name(block) + "_mirrored", mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"), "all", path(block, path -> path.replace("twilight_", "rainbow/")));
            this.simpleBlock(block, ConfiguredModel.builder().modelFile(normal).nextModel().modelFile(normal).rotationY(180).nextModel().modelFile(mirrored).rotationY(0).nextModel().modelFile(mirrored).rotationY(180).build());
        }, LCCBlocks.twilight_stone);
        this.addAll(block -> this.simpleBlock(block, path(block, path -> path.replace("twilight_", "rainbow/"))), LCCBlocks.twilight_cobblestone);
        this.addAll(block -> this.simpleBlock(block, ConfiguredModel.allYRotations(
            models().cubeAll(name(block), path(block, path -> path.replace("sparkling_", "rainbow/")))
        , 0, false)), LCCBlocks.sparkling_dirt);
        this.simpleBlock(LCCBlocks.rainbow_grass_block, ConfiguredModel.allYRotations(models().withExistingParent(name(LCCBlocks.rainbow_grass_block), "grass_block")
            .texture("particle", path("rainbow/dirt"))
            .texture("bottom", path("rainbow/dirt"))
            .texture("side", path("rainbow/grass_side"))
            .texture("top", path("rainbow/rainbow_grass_top"))
            .texture("overlay", path("rainbow/rainbow_grass_overlay"))
        , 0, false));
        this.simpleBlock(LCCBlocks.star_plating, ConfiguredModel.allYRotations(models().withExistingParent(name(LCCBlocks.star_plating), "grass_block")
                .texture("particle", path("rainbow/dirt"))
                .texture("bottom", path("rainbow/dirt"))
                .texture("side", path("rainbow/grass_side"))
                .texture("top", path("rainbow/star_plating_top"))
                .texture("overlay", path("rainbow/star_plating_overlay"))
            , 0, false));
        this.simpleBlock(LCCBlocks.sugar_grass_block, ConfiguredModel.allYRotations(models().cubeBottomTop(name(LCCBlocks.sugar_grass_block),
            path("rainbow/sugar_grass_side"),
            path("rainbow/dirt"),
            path("rainbow/sugar_grass_top")
        ), 0, false));
        this.addAll(block -> this.simpleBlock(block, ConfiguredModel.allYRotations(models().withExistingParent(name(block), this.grassBlockIndex1.getLocation())
            .texture("particle", path("rainbow/dirt"))
            .texture("bottom", path("rainbow/dirt"))
            .texture("side", path("rainbow/grass_side"))
            .texture("top", path("rainbow/colored_grass_top"))
            .texture("overlay", path("rainbow/colored_grass_overlay"))
        , 0, false)), LCCBlocks.sparkling_grass_block.values().toArray(new SparklingGrassBlock[0]));
        this.addAll(block -> this.candyCane(block,
            path("rainbow/candy_cane/end"),
            path(block, path -> path.replace("candy_cane_", "rainbow/candy_cane/") + "_0"),
            path(block, path -> path.replace("candy_cane_", "rainbow/candy_cane/") + "_1")
        ), LCCBlocks.candy_cane_red, LCCBlocks.candy_cane_green, LCCBlocks.candy_cane_blue);
        this.addAll(block -> this.candyCaneCoating(block,
            path(block, path -> path.replace("candy_cane_coating_", "rainbow/candy_cane/") + "_0"),
            path(block, path -> path.replace("candy_cane_coating_", "rainbow/candy_cane/") + "_1")
        ), LCCBlocks.candy_cane_coating_red, LCCBlocks.candy_cane_coating_green, LCCBlocks.candy_cane_coating_blue);
        this.addAll(block -> this.axisBlock(block,
            path(block, path -> path.replace("refined_candy_cane_", "rainbow/candy_cane/refined_")),
            path("rainbow/candy_cane/end")
        ), LCCBlocks.refined_candy_cane_red, LCCBlocks.refined_candy_cane_green, LCCBlocks.refined_candy_cane_blue);
        this.addAll(block -> this.axisBlock(block,
            path(block, path -> path.replace("refined_candy_cane_coating_", "rainbow/candy_cane/refined_")),
            path(block, path -> path.replace("refined_candy_cane_coating_", "rainbow/candy_cane/refined_"))
        ), LCCBlocks.refined_candy_cane_coating_red, LCCBlocks.refined_candy_cane_coating_green, LCCBlocks.refined_candy_cane_coating_blue);
        this.addAll(block -> this.axisBlock(block,
            path(block, path -> "rainbow/candy_cane/" + path.split("_")[0]),
            path("rainbow/candy_cane/end")
        ), LCCBlocks.stripped_candy_cane, LCCBlocks.refined_stripped_candy_cane);
        this.addAll(block -> this.axisBlock(block,
            path(block, path -> "rainbow/candy_cane/" + path.split("_")[0]),
            path(block, path -> "rainbow/candy_cane/" + path.split("_")[0])
        ), LCCBlocks.stripped_candy_cane_coating, LCCBlocks.refined_stripped_candy_cane_coating);
        this.simpleBlock(LCCBlocks.candy_cane_block, path("rainbow/candy_cane/end"));
        this.addAll(block -> this.channelite(block, path(block, path -> path.replace("channelite_", "rainbow/channelite/")), block.getColor() == null ? "none" : "invisible", block.getColor() == null ? "" : "end"), LCCBlocks.channelite.values().toArray(new ChanneliteBlock[0]));
        this.addAll(block -> this.simpleBlock(block, models().withExistingParent(name(block), this.overlay.getLocation())
            .texture("particle", path("rainbow/channelite/sparkling"))
            .texture("base", path("rainbow/channelite/sparkling"))
            .texture("overlay", path(block, path -> path.replace("sparkling_channelite_source_", "rainbow/channelite/") + "_source"))
        ), LCCBlocks.sparkling_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));
        this.addAll(block -> this.simpleBlock(block, models().withExistingParent(name(block), this.overlay.getLocation())
            .texture("particle", path("rainbow/channelite/twilight"))
            .texture("base", path("rainbow/channelite/twilight"))
            .texture("overlay", path(block, path -> path.replace("twilight_channelite_source_", "rainbow/channelite/") + "_source"))
        ), LCCBlocks.twilight_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));
        this.addAll(block -> this.axisBlock(block, path(block, path -> "rainbow/wood/" + path.replace("vivid_", "")), path(block, path -> "rainbow/wood/" + path.replace("vivid_", "") + "_top")), LCCBlocks.vivid_log, LCCBlocks.stripped_vivid_log);
        this.addAll(block -> this.axisBlock(block, path(block, path -> "rainbow/wood/" + path.replace("vivid_wood", "log")), path(block, path -> "rainbow/wood/" + path.replace("vivid_wood", "log"))), LCCBlocks.vivid_wood, LCCBlocks.stripped_vivid_wood);
        this.addAll(block -> this.simpleBlock(block, path(block, path -> path.replace("vivid_", "rainbow/wood/"))), LCCBlocks.vivid_planks, LCCBlocks.vivid_leaves);
        this.addAll(block -> this.simpleBlock(block, models().cross(name(block), path(block, path -> path.replace("vivid_", "rainbow/wood/")))), LCCBlocks.vivid_sapling);
        this.addAll(block -> this.simpleBlock(block, models().withExistingParent(name(block), "flower_pot_cross").texture("plant", path(block, path -> path.replace("potted_vivid_", "rainbow/wood/")))), LCCBlocks.potted_vivid_sapling);
        this.stairsBlock(LCCBlocks.vivid_stairs, path("rainbow/wood/planks"));
        this.slabBlock(LCCBlocks.vivid_slab, path(name(LCCBlocks.vivid_planks)), path("rainbow/wood/planks"));
        this.doorBlock(LCCBlocks.vivid_door, path("rainbow/wood/door_bottom"), path("rainbow/wood/door_top"));
        this.pressurePlateBlock(LCCBlocks.vivid_pressure_plate, path("rainbow/wood/planks"));
        this.buttonBlock(LCCBlocks.vivid_button, path("rainbow/wood/planks"));
        this.fenceBlock(LCCBlocks.vivid_fence, path("rainbow/wood/planks"));
        this.fenceGateBlock(LCCBlocks.vivid_fence_gate, path("rainbow/wood/planks"));
        this.trapdoorBlock(LCCBlocks.vivid_trapdoor, path("rainbow/wood/trapdoor"), true);

        this.simpleBlock(LCCBlocks.time_rift, models().withExistingParent(name(LCCBlocks.time_rift), "block").texture("particle", path("time_rift")));
        this.addAll(block -> this.simpleBlock(block, path(block, path -> "nostalgia/" + path.replace("classic_", ""))), LCCBlocks.classic_bricks, LCCBlocks.classic_cobblestone, LCCBlocks.classic_glass, LCCBlocks.classic_gravel, LCCBlocks.classic_sponge, LCCBlocks.classic_mossy_cobblestone, LCCBlocks.classic_leaves, LCCBlocks.classic_planks, LCCBlocks.glowing_obsidian);
        this.addAll(block -> this.simpleBlock(block, path(block, path -> path.replace("classic_", "nostalgia/"))), LCCBlocks.classic_cloth.values().toArray(new Block[0]));
        this.addAll(block -> this.simpleBlock(block, models().cubeBottomTop(name(block),
            path(block, path -> path.replace("classic_", "nostalgia/") + "_side"),
            path(block, path -> path.replace("classic_", "nostalgia/") + "_bottom"),
            path(block, path -> path.replace("classic_", "nostalgia/") + "_top")
        )), LCCBlocks.classic_iron_block, LCCBlocks.classic_gold_block, LCCBlocks.classic_diamond_block);
        this.addAll(block -> this.simpleBlock(block, path(block, path -> path.replace("classic_smooth_", "nostalgia/"))), LCCBlocks.classic_smooth_iron_block, LCCBlocks.classic_smooth_gold_block, LCCBlocks.classic_smooth_diamond_block);
        this.getVariantBuilder(LCCBlocks.classic_grass_block)
            .partialState().with(FunctionalGrassBlock.SNOWY, false).modelForState()
                .modelFile(models().cubeBottomTop(name(LCCBlocks.classic_grass_block),
                    path("nostalgia/grass_block_side"),
                    mcLoc(ModelProvider.BLOCK_FOLDER + "/dirt"),
                    path("nostalgia/grass_block_top")
                )).addModel()
            .partialState().with(FunctionalGrassBlock.SNOWY, true).modelForState()
                .modelFile(models().cubeBottomTop(name(LCCBlocks.classic_grass_block) + "_snowy",
                    path("nostalgia/grass_block_snowy"),
                    mcLoc(ModelProvider.BLOCK_FOLDER + "/dirt"),
                    mcLoc(ModelProvider.BLOCK_FOLDER + "/snow")
                )).addModel();
        this.getVariantBuilder(LCCBlocks.classic_chest)
            .forAllStates(state -> {
                ChestType type = state.get(ClassicChestBlock.TYPE);
                return ConfiguredModel.builder()
                    .modelFile(models().cube(name(LCCBlocks.classic_chest) + "_" + type.getName(),
                        path("nostalgia/chest_top"),
                        path("nostalgia/chest_top"),
                        path("nostalgia/chest_" + (type != ChestType.SINGLE ? (type.opposite().getName() + "_") : "") + "front"),
                        path("nostalgia/chest_" + (type != ChestType.SINGLE ? (type.getName() + "_back") : "side")),
                        path("nostalgia/chest_side"),
                        path("nostalgia/chest_side")
                    ).texture("particle", path("nostalgia/chest_front")))
                    .rotationY((int)state.get(ClassicChestBlock.FACING).getOpposite().getHorizontalAngle())
                    .build();
            });
        this.getVariantBuilder(LCCBlocks.nether_reactor)
            .forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models().cubeAll(name(LCCBlocks.nether_reactor) + "_" + state.get(NetherReactorBlock.STATE).getName(), path("nostalgia/nether_reactor_" + state.get(NetherReactorBlock.STATE).getName())))
                .build()
            );
        this.addAll(block -> this.simpleBlock(block, models().cross(name(block), path(block, path -> path.replace("classic_", "nostalgia/")))), LCCBlocks.classic_sapling, LCCBlocks.classic_rose, LCCBlocks.classic_cyan_flower);
        this.addAll(block -> this.simpleBlock(block, models().withExistingParent(name(block), "flower_pot_cross").texture("plant", path(block, path -> path.replace("potted_classic_", "nostalgia/")))), LCCBlocks.potted_classic_sapling, LCCBlocks.potted_classic_rose, LCCBlocks.potted_classic_cyan_flower);
        this.simpleBlock(LCCBlocks.classic_tnt, models().cubeBottomTop(name(LCCBlocks.classic_tnt),
            path("nostalgia/tnt_side"),
            path("nostalgia/tnt_bottom"),
            path("nostalgia/tnt_top")
        ));
        this.simpleBlock(LCCBlocks.cog, models().withExistingParent(name(LCCBlocks.cog), "block").texture("particle", path("nostalgia/cog")));
        this.simpleBlock(LCCBlocks.crying_obsidian, models().withExistingParent(name(LCCBlocks.crying_obsidian), "block").texture("particle", path("nostalgia/crying_obsidian_static")));

        this.getVariantBuilder(LCCBlocks.atomic_bomb)
            .forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models().withExistingParent(name(LCCBlocks.atomic_bomb) + "_" + state.get(AtomicBombBlock.SEGMENT).getName(), new ResourceLocation(LCC.MODID, "template_atomic_bomb_" + state.get(AtomicBombBlock.SEGMENT).getName()))
                    .texture("1", path("atomic_bomb/tail_side"))
                    .texture("2", path("atomic_bomb/tail"))
                    .texture("3", path("atomic_bomb/fin"))
                    .texture("4", path("atomic_bomb/core"))
                    .texture("5", path("atomic_bomb/head"))
                    .texture("particle", path("atomic_bomb/tail"))
                )
                .rotationY((int)state.get(AtomicBombBlock.FACING).getOpposite().getHorizontalAngle())
                .build()
            );

        this.bouncePad(LCCBlocks.bounce_pad, path("bounce_pad/base_h"), path("bounce_pad/base_v"), path("bounce_pad/inner"), path("bounce_pad/setting"));

        this.simpleBlock(LCCBlocks.computing, models().withExistingParent(name(LCCBlocks.computing), "block"));
        this.addAll(block -> this.cable(block, block == LCCBlocks.networking_cable ? mcLoc(ModelProvider.BLOCK_FOLDER + "/lime_wool") : mcLoc(ModelProvider.BLOCK_FOLDER + "/purple_wool"), 4), LCCBlocks.networking_cable, LCCBlocks.terminal_cable);
        this.addAll(block -> this.horizontalBlock(block, models().withExistingParent(name(block), this.colorableOrientable.getLocation())
            .texture("top", path("computing/casing_top"))
            .texture("front", path("computing/terminal_front"))
            .texture("side", path("computing/casing_top"))
            .texture("particle", path("computing/casing_top"))
        ), LCCBlocks.terminals.values().toArray(new TerminalBlock[0]));

        this.simpleBlock(LCCBlocks.spreader_interface, models().withExistingParent(name(LCCBlocks.spreader_interface), new ResourceLocation(LCC.MODID, "template_spreader_interface"))
            .texture("0", path("spreader/interface/lid_top"))
            .texture("1", path("spreader/interface/lid_side"))
            .texture("2", path("spreader/interface/base_side"))
            .texture("3", path("spreader/interface/base_top"))
            .texture("4", path("spreader/interface/core_side"))
            .texture("5", path("spreader/interface/legs"))
            .texture("6", path("spreader/interface/port"))
            .texture("particle", path("spreader/interface/particle"))
        );
        this.addAll(block -> this.getVariantBuilder(block).forAllStates(state -> {
            int age = state.get(SpreaderBlock.AGE);
            return ConfiguredModel.allRotations(models().withExistingParent(name(block) + "_" + age, this.colorable.getLocation()).texture("all", path("spreader/age_" + age)), false);
        }), LCCBlocks.spreaders.values().toArray(new SpreaderBlock[0]));

        this.addAll(block -> this.simpleBlock(block, ConfiguredModel.allYRotations(models().cubeAll(name(block), path(block, path -> "wasteland/" + path)), 0, false)), LCCBlocks.cracked_mud);
        this.simpleBlock(LCCBlocks.oil, models().withExistingParent(name(LCCBlocks.oil), "block").texture("particle", path("wasteland/oil_still")));
    }

    //Happy helpers.
    private void simpleBlock(Block block, ResourceLocation texture) {
        this.simpleBlock(block, models().cubeAll(name(block), texture));
    }

    private void bouncePad(BouncePadBlock block, ResourceLocation base_h, ResourceLocation base_v, ResourceLocation inner, ResourceLocation setting) {
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(LCCBlocks.bounce_pad)
            .part().modelFile(
                models().withExistingParent(name(block), new ResourceLocation(LCC.MODID, "template_bounce_pad"))
                    .texture("0", base_h)
                    .texture("1", inner)
                    .texture("2", setting)
                    .texture("3", base_v)
                    .texture("particle", base_v)
            ).addModel().end();
        for (int i : BouncePadBlock.SETTING.getAllowedValues()) {
            builder.part().modelFile(models().withExistingParent(name(block) + "_" + i, new ResourceLocation(LCC.MODID, "template_bounce_pad_" + i)).texture("2", setting)).addModel().condition(BouncePadBlock.SETTING, i).end();
        }
    }

    private void candyCane(CandyCaneBlock block, ResourceLocation end, ResourceLocation side1, ResourceLocation side2) {
        this.getVariantBuilder(block)
            .forAllStates(state -> {
                boolean sAlt = state.get(CandyCaneBlock.SIDE_ALTERNATE);
                ResourceLocation a = sAlt ? side1 : side2;
                ResourceLocation b = sAlt ? side2 : side1;
                return ConfiguredModel.builder()
                    .modelFile(models().cube(name(block) + (sAlt ? "_s" : ""), end, end, a, a, b, b).texture("particle", "#north"))
                    .rotationX(state.get(CandyCaneBlock.AXIS) == Direction.Axis.Y ? 0 : 90)
                    .rotationY(state.get(CandyCaneBlock.AXIS) == Direction.Axis.X ? 90 : 0)
                    .build();
            });
    }

    private void candyCaneCoating(CandyCaneBlock block, ResourceLocation side1, ResourceLocation side2) {
        this.getVariantBuilder(block)
            .forAllStates(state -> {
                boolean sAlt = state.get(CandyCaneBlock.SIDE_ALTERNATE);
                ResourceLocation a = sAlt ? side1 : side2;
                ResourceLocation b = sAlt ? side2 : side1;
                boolean eAlt = state.get(CandyCaneBlock.END_ALTERNATE);
                return ConfiguredModel.builder()
                    .modelFile(models().cube(name(block) + (sAlt || eAlt ? "_" : "") + (sAlt ? "s" : "") + (eAlt ? "e" : ""), eAlt ? side2 : side1, eAlt ? side2 : side1, a, a, b, b).texture("particle", "#north"))
                    .rotationX(state.get(CandyCaneBlock.AXIS) == Direction.Axis.Y ? 0 : 90)
                    .rotationY(state.get(CandyCaneBlock.AXIS) == Direction.Axis.X ? 90 : 0)
                    .build();
            });
    }

    private void channelite(ChanneliteBlock block, ResourceLocation baseName, String invisibleSuffix, String endSuffix) {
        this.getVariantBuilder(block)
            .forAllStates(state -> {
                Direction dir = state.get(ChanneliteBlock.FACING);
                ChanneliteBlock.ChanneliteConnection c = state.get(ChanneliteBlock.CONNECTION);
                String end2 = block.getColor() != null ? ("_" + endSuffix) : "";
                String end = baseName.getPath() + (c == ChanneliteBlock.ChanneliteConnection.INVISIBLE ? ("_" + invisibleSuffix) : (end2.isEmpty() ? "_none" : ""));
                return ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(name(block) + "_" + c.getName(), this.noshadeCubeColumn.getLocation())
                        .texture("end", new ResourceLocation(baseName.getNamespace(), end + end2))
                        .texture("side", new ResourceLocation(baseName.getNamespace(), baseName.getPath() + "_" + (c == ChanneliteBlock.ChanneliteConnection.INVISIBLE ? invisibleSuffix : c.getName())))
                    )
                    .rotationX(dir == Direction.DOWN ? 180 : (dir.getAxis().isHorizontal() ? 90 : 0))
                    .rotationY(dir.getAxis().isVertical() ? 0 : (((int)dir.getHorizontalAngle()) + 180) % 360)
                    .build();
            });
    }

    private float cableCoords(Direction part, Direction coord, float w, boolean positive) {
        if (part == coord) return positive ? 16 : 0;
        if (part == coord.getOpposite() ^ positive) return 8+w;
        return 8-w;
    }

    private BlockModelBuilder cableModel(Direction d, String path, float w) {
        ModelBuilder<BlockModelBuilder>.ElementBuilder element = models().getBuilder(path + "_" + d.getName())
            .element().from(cableCoords(d, Direction.WEST, w, false), cableCoords(d, Direction.DOWN, w, false), cableCoords(d, Direction.NORTH, w, false)).to(cableCoords(d, Direction.EAST, w, true), cableCoords(d, Direction.UP, w, true), cableCoords(d, Direction.SOUTH, w, true));
        for (Direction f : Direction.values()) {
            if (f != d.getOpposite()) {
                Direction[] p = Util.PERPENDICULARS.get(f);
                element.face(f).uvs(cableCoords(d, p[3], w, false), cableCoords(d, p[0], w, false), cableCoords(d, p[1], w, true), cableCoords(d, p[2], w, true)).cullface(f == d ? d : null).end();
            }
        }
        return element.texture("#cable").end();
    }

    private void cable(Block block, ResourceLocation all, int thickness) {
        float w = thickness / 2f;

        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block)
            .part().modelFile(
                models().getBuilder(name(block) + "_center").element().from(8-w, 8-w, 8-w).to(8+w, 8+w, 8+w).allFaces((dir, face) -> face.uvs(8-w, 8-w, 8+w, 8+w).texture("#cable")).end().texture("cable", all).texture("particle", all)
            ).addModel().end();
        for (Direction d : Direction.values()) {
            builder.part().modelFile(cableModel(d, name(block), w).texture("cable", all)).addModel().condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(d), true).end();
        }
    }

    private void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        this.getVariantBuilder(block)
            .forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models().withExistingParent(name(block) + (state.get(PressurePlateBlock.POWERED) ? "_down" : ""), "pressure_plate_" + (state.get(PressurePlateBlock.POWERED) ? "down" : "up")).texture("texture", texture))
                .build());
    }

    private void buttonBlock(AbstractButtonBlock block, ResourceLocation texture) {
        this.getVariantBuilder(block)
            .forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models().withExistingParent(name(block) + "_" + (state.get(AbstractButtonBlock.POWERED) ? "pressed" : ""), "button" + (state.get(PressurePlateBlock.POWERED) ? "_pressed" : "")).texture("texture", texture))
                .rotationX(state.get(AbstractButtonBlock.FACE).ordinal() * 90)
                .rotationY(((int)state.get(AbstractButtonBlock.HORIZONTAL_FACING).getHorizontalAngle() + 180) % 360)
                .uvLock(state.get(AbstractButtonBlock.FACE) == AttachFace.WALL)
                .build());
    }

    private void forceBlockstateLowercase(Block block) {
        ResourceLocation name = block.getRegistryName();
        Path p = dg.getOutputFolder().resolve("assets/" + name.getNamespace() + "/blockstates/" + name.getPath() + ".json");
        try {
            List<String> contents = Files.readAllLines(p);
            BufferedWriter writer = Files.newBufferedWriter(p);
            for (String line : contents) {
                writer.write(line.toLowerCase());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
