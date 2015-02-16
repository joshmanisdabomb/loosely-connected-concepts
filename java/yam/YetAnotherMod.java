package yam;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import yam.biome.BiomeMoon;
import yam.biome.BiomeRainbow;
import yam.biome.BiomeSheol;
import yam.biome.BiomeWasteland;
import yam.blocks.BlockBouncepad;
import yam.blocks.BlockCandyCane;
import yam.blocks.BlockClassicChest;
import yam.blocks.BlockCloud;
import yam.blocks.BlockComputer;
import yam.blocks.BlockCryingObsidian;
import yam.blocks.BlockCustomGrass;
import yam.blocks.BlockCustomLeaves;
import yam.blocks.BlockCustomSapling;
import yam.blocks.BlockCustomTorch;
import yam.blocks.BlockCustomWool;
import yam.blocks.BlockDerek;
import yam.blocks.BlockGeneric;
import yam.blocks.BlockGlowingObsidian;
import yam.blocks.BlockHotCoal;
import yam.blocks.BlockInternet;
import yam.blocks.BlockNuke;
import yam.blocks.BlockNukeFire;
import yam.blocks.BlockPlayerPlate;
import yam.blocks.BlockPoop;
import yam.blocks.BlockQuicksand;
import yam.blocks.BlockRadioactive;
import yam.blocks.BlockRainbow;
import yam.blocks.BlockRainbowGolem;
import yam.blocks.BlockRainbowGrass;
import yam.blocks.BlockRainbowPortal;
import yam.blocks.BlockReactor;
import yam.blocks.BlockRustBlock;
import yam.blocks.BlockSlices;
import yam.blocks.BlockSpreading;
import yam.blocks.BlockStonecutter;
import yam.blocks.BlockTickField;
import yam.blocks.BlockTrashCan;
import yam.blocks.entity.TileEntityClassicChest;
import yam.blocks.entity.TileEntityComputer;
import yam.blocks.entity.TileEntityStonecutter;
import yam.blocks.entity.TileEntityTickField;
import yam.blocks.entity.TileEntityTrashCan;
import yam.blocks.itemblocks.ItemBlockExtraInfo;
import yam.blocks.itemblocks.ItemBlockRadioactive;
import yam.blocks.sounds.BlockClassicSounds;
import yam.blocks.sounds.BlockSounds;
import yam.command.CmdExplode;
import yam.command.CmdGetPos;
import yam.command.CmdHarm;
import yam.command.CmdHeal;
import yam.command.CmdMotion;
import yam.command.CmdRepeat;
import yam.command.CmdSudo;
import yam.command.CmdSuperEnchant;
import yam.dimension.moon.MoonDimProvider;
import yam.dimension.rainbow.RainbowDimProvider;
import yam.dimension.sheol.SheolDimProvider;
import yam.enchants.EnchantGeneric;
import yam.entity.EntityAmplifyBomb;
import yam.entity.EntityAmplislime;
import yam.entity.EntityBullet;
import yam.entity.EntityDerek;
import yam.entity.EntityDerekJr;
import yam.entity.EntityFly;
import yam.entity.EntityHalfPlayer;
import yam.entity.EntityLollipopper;
import yam.entity.EntityMLGArrow;
import yam.entity.EntityMummy;
import yam.entity.EntityNukeMissile;
import yam.entity.EntityNukePrimed;
import yam.entity.EntityPsychoPig;
import yam.entity.EntityRainbot;
import yam.entity.EntityRainbowGolem;
import yam.entity.EntityTick;
import yam.entity.EntityUnicorn;
import yam.events.Events;
import yam.gen.WorldGen;
import yam.gui.GuiHandler;
import yam.items.ItemAmplifyBomb;
import yam.items.ItemAntispreaderRemote;
import yam.items.ItemAtomSplitter;
import yam.items.ItemBlaster;
import yam.items.ItemEnergyCrystal;
import yam.items.ItemExtraJump;
import yam.items.ItemGeneric;
import yam.items.ItemHeart;
import yam.items.ItemHeartContainer;
import yam.items.ItemNoms;
import yam.items.ItemNomsNewStack;
import yam.items.ItemNukeRemote;
import yam.items.ItemPill;
import yam.items.ItemPlacer;
import yam.items.ItemRadioactive;
import yam.items.ItemRainbowSeeds;
import yam.items.ItemRainbowSpawnEgg;
import yam.items.ItemSmartphone;
import yam.items.ItemSpawnEgg;
import yam.items.ItemSpreaderRemote;
import yam.items.ItemTest;
import yam.items.ItemUSB;
import yam.items.ItemUSBFull;
import yam.items.tools.ItemBackstabSword;
import yam.items.tools.ItemCosmeticArmor;
import yam.items.tools.ItemCustomArmor;
import yam.items.tools.ItemCustomAxe;
import yam.items.tools.ItemCustomHoe;
import yam.items.tools.ItemCustomPickaxe;
import yam.items.tools.ItemCustomShovel;
import yam.items.tools.ItemCustomSword;
import yam.items.tools.ItemFallingBoots;
import yam.items.tools.ItemGlassesCosmetic;
import yam.items.tools.ItemPearlNecklace;
import yam.items.tools.ItemRepeater;
import yam.packet.PacketParticle;
import yam.packet.PacketParticleExplosion;
import yam.packet.PacketParticleSpark;
import yam.packet.PacketParticleSparkUpwards;
import yam.particle.ParticleHandler.ParticleType;
import yam.proxy.ServerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = YetAnotherMod.MODID, name = YetAnotherMod.NAME, version = YetAnotherMod.VERSION)
public class YetAnotherMod
{
	//TODO Rainbow dimension customization. (Neon ore)
	//FIX  Wasteland biome.
	//TODO Add old mod shit.
	//TODO Nuclear missile new model and texture.
	//TODO Self destruct remote.
	//TODO Complete survival recipes.
	//TODO Duplicator blocks.
	//TODO Entity cloners.
	//TODO Billie Jean blocks.
	//TODO Drills and builder blocks.
	//TODO Halfplayer homes. (Higher tiers homes and populate chests, furnaces and random home modules for tier 3.)
	//TODO Rust block stages. (Tile entity.)
	//TODO Space. Moons and planets and shit.
	//TODO Custom experience drops from mobs.
	//TODO Wither, blast, daze bow enchantments.
	//TODO Healing armor enchantment. (Armour)
	//TODO Cactus armor, hurts you but very powerful.
	//TODO Treants.
	//TODO Change infected potion to fuzzy potion effect, add new fuzzy entity.
	//TODO Change lollipopper to throw lollipops that can be collected.
	//TODO Crawling zombies.
	//TODO Mummies and pyramids.
	//TODO XPiggy Bank.
	//TODO Neon armour leaves trail.
	//TODO Backstab.
	//TODO Dubstep guns.
	//TODO Computers that power rockets and 3D print Nostalgia objects.
	//TODO Smartphones that can send PMs to other players with a cost.
	//TODO Internet blocks that cost less to do internet things with each generation.
	//TODO Charge smartphones.
	
	//Forge and initalization shit
    public static final String MODID = "yam";
    public static final String NAME = "Yet Another Mod";
    public static final String VERSION = "Beta 1.3";
    
	public static final float bbConstant = 0.005F;

    public static int rainbowDimID;
    public static int sheolDimID;
    public static int moonDimID;
    public static int asmiaDimID;
   
    @Instance(MODID)
    public static YetAnotherMod instance;
    
    @SidedProxy(clientSide="yam.proxy.ClientProxy", serverSide="yam.proxy.ServerProxy")
    public static ServerProxy proxy;
    
    //Test stuff
    public static Block blockTest;
    public static Item itemTest;
    
    //Misc.
    public static Block mud;
    public static Block quicksand;
    public static Block diamondPressurePlate;
    
    //Nuke
    public static Block nuke;
    public static Block nuclearFire;
    public static Item atomSplitter;
    public static Block nuclearWaste;
    
    //Ores
    public static Block cactusBlock;
    public static Item cactusSpine;
    public static Item cactusSword;
    public static Item cactusPickaxe;
    public static Item cactusShovel;
    public static Item cactusAxe;
    public static Item cactusHoe;
    public static int cactusHelmetID;
    public static int cactusChestplateID;
    public static int cactusLeggingsID;
    public static int cactusBootsID;
    public static Item cactusHelmet;
    public static Item cactusChestplate;
    public static Item cactusLeggings;
    public static Item cactusBoots;
    
    public static Block rubyOre;
    public static Block rubyBlock;
    public static Item ruby;
    public static Item rubySword;
    public static Item rubyPickaxe;
    public static Item rubyShovel;
    public static Item rubyAxe;
    public static Item rubyHoe;
    public static int rubyHelmetID;
    public static int rubyChestplateID;
    public static int rubyLeggingsID;
    public static int rubyBootsID;
    public static Item rubyHelmet;
    public static Item rubyChestplate;
    public static Item rubyLeggings;
    public static Item rubyBoots;
    
    public static Item emeraldSword;
    public static Item emeraldPickaxe;
    public static Item emeraldShovel;
    public static Item emeraldAxe;
    public static Item emeraldHoe;
    public static int emeraldHelmetID;
    public static int emeraldChestplateID;
    public static int emeraldLeggingsID;
    public static int emeraldBootsID;
    public static Item emeraldHelmet;
    public static Item emeraldChestplate;
    public static Item emeraldLeggings;
    public static Item emeraldBoots;
    
    public static Item lapisGel;
    public static Item lapisIngot;
    public static Item lapisSword;
    public static Item lapisPickaxe;
    public static Item lapisShovel;
    public static Item lapisAxe;
    public static Item lapisHoe;
    public static int lapisHelmetID;
    public static int lapisChestplateID;
    public static int lapisLeggingsID;
    public static int lapisBootsID;
    public static Item lapisHelmet;
    public static Item lapisChestplate;
    public static Item lapisLeggings;
    public static Item lapisBoots;
    
    public static Block crystalOre;
    public static Block crystalBlock;
    public static Item crystalShard;
    public static Item crystal;
    public static Item crystalIngot;
    public static Item crystalSword;
    public static Item crystalPickaxe;
    public static Item crystalShovel;
    public static Item crystalAxe;
    public static Item crystalHoe;
    public static Item crystalRepeater;
    public static int crystalHelmetID;
    public static int crystalChestplateID;
    public static int crystalLeggingsID;
    public static int crystalBootsID;
    public static Item crystalHelmet;
    public static Item crystalChestplate;
    public static Item crystalLeggings;
    public static Item crystalBoots;
    public static Item crystalEnergy;
    
    public static Block pearlOre;
    public static Block pearlBlock;
    public static Item pearl;
    public static int pearlNecklaceID;
    public static Item pearlNecklace;
    public static int pearlNecklaceCosmeticID;
    public static Item pearlNecklaceCosmetic;
    
    public static Item uranium;
    public static Block uraniumOre;
    public static Block uraniumBlock;
    public static int hazmatHelmetID;
    public static int hazmatChestplateID;
    public static int hazmatLeggingsID;
    public static int hazmatBootsID;
    public static Item hazmatHelmet;
    public static Item hazmatChestplate;
    public static Item hazmatLeggings;
    public static Item hazmatBoots;

    public static Block spikes;
    public static Block bloodSpikes;

    public static Item wireRed;
    public static Item wireGreen;
    public static Item wireBlue;
    public static Item wireBundled;
    public static Item keyboard;
    public static Item mouse;
    public static Item processor;
    public static Item battery;
    public static Item printer3D;
    public static Block computerCase;
    public static Block computer;
    public static Item smartphoneDisabled;
    public static Item smartphone;
    public static Item usb;
    public static Item usbOS;
    public static Block internet1G;
    public static Block internet2G;
    public static Block internet3G;
    public static Block internet4G;

    public static Item pillBlack;
    public static Item pillBlue;
    public static Item pillCyanWhite;
    public static Item pillGreen;
    public static Item pillOrangeCyan;
    public static Item pillOrangeYellow;
    public static Item pillRed;
    public static Item pillRedBlue;
    public static Item pillWhite;
    
    public static Block tickField;
    public static Block trashCanBlock;
    public static Item trashCan;

    public static Item halfplayerSpawnEgg;
    public static Item psychopigSpawnEgg;
    public static Item mummySpawnEgg;
    public static Item flySpawnEgg;
    public static Item derekJrSpawnEgg;
    public static Item tickSpawnEgg;
    public static Item lollipopperSpawnEgg;
    public static Item rainbotSpawnEgg;
    public static Item amplislimeSpawnEgg;
    public static Item unicornSpawnEgg;

    public static Item cloudSword;
    public static Item cloudPickaxe;
    public static Item cloudShovel;
    public static Item cloudAxe;
    public static Item cloudHoe;
    public static int cloudHelmetID;
    public static int cloudChestplateID;
    public static int cloudLeggingsID;
    public static int cloudBootsID;
    public static Item cloudHelmet;
    public static Item cloudChestplate;
    public static Item cloudLeggings;
    public static Item cloudBoots;
    public static Item cloudBottle;
    
    public static Block neonOre;
    public static Block neonBlock;
    public static Item neon;
    public static Item neonSword;
    public static Item neonPickaxe;
    public static Item neonShovel;
    public static Item neonAxe;
    public static Item neonHoe;
    public static int neonHelmetID;
    public static int neonChestplateID;
    public static int neonLeggingsID;
    public static int neonBootsID;
    public static Item neonHelmet;
    public static Item neonChestplate;
    public static Item neonLeggings;
    public static Item neonBoots;
    public static Item neonBottle;
    
    //Sheol
    public static Block depthStone;
    public static Block hotCoal;
	
    public static Item blaster;
    
    //Bounce Pads
    public static Block bounceBlue;
    public static Block bounceGreen;
    public static Block bounceYellow;
    public static Block bounceOrange;
    public static Block bounceRed;
    public static Block bounceCushion;

    //Spreaders
    public static Block spreader1;
    public static Block spreader2;
    public static Block spreader3;
    public static Block spreaderc1;
    public static Block spreaderc2;
    public static Block spreaderc3;
    public static Block eater1;
    public static Block eater2;
    public static Block eater3;
    public static Block eaterc1;
    public static Block eaterc2;
    public static Block eaterc3;
    public static Block antispreader;
    
    //Remotes
    public static Item spreaderRemoteWood;
    public static Item spreaderRemoteStone;
    public static Item spreaderRemoteIron;
    public static Item spreaderRemoteGold;
    public static Item spreaderRemoteDiamond;
    public static Item spreaderRemoteRuby;
    public static Item spreaderRemoteEmerald;
    public static Item spreaderRemoteLapis;
    public static Item spreaderRemoteCrystal;
    public static Item antispreaderRemoteWood;
    public static Item antispreaderRemoteStone;
    public static Item antispreaderRemoteIron;
    public static Item antispreaderRemoteGold;
    public static Item antispreaderRemoteDiamond;
    public static Item antispreaderRemoteRuby;
    public static Item antispreaderRemoteEmerald;
    public static Item antispreaderRemoteLapis;
    public static Item antispreaderRemoteCrystal;

    public static Item nukeRemote;

    //Bricks
    public static Block bricksCoal;
    public static Block bricksIron;
    public static Block bricksGold;
    public static Block bricksDiamond;
    public static Block bricksObsidian;
    public static Block bricksRedstone;
    public static Block bricksRuby;
    public static Block bricksEmerald;
    public static Block bricksLapis;
    public static Block bricksCrystal;
    public static Block bricksUranium;
    public static Block bricksPearl;

    //Wasteland Shit
    public static Block crackedMud;
    public static Block rustOre;
    public static Item rust;
    public static Block rustBlock;
    public static Block reinforcedStone;
    public static Block reinforcedGlass;
    public static Block reinforcedWool;
    public static Block poop;
    public static Block derekHead;
    public static Block derekHeart;
    public static Block derekSoul;
    public static Block derekSkin;

    public static Item hearthalf;
    public static Item heart;
    public static Item crystalheart;
    
    //Rainbow Shit
    public static Block rainbowBlock;
    public static Block rainbowPortal;
    public static Block rainbowDirt;
    public static Block rainbowStone;
    public static Block rainbowStoneRefined;
    public static Block rainbowStream;
    public static Block rainbowGrassWhite;
    public static Block rainbowGrassLGray;
    public static Block rainbowGrassGray;
    public static Block rainbowGrassBlack;
    public static Block rainbowGrassRed;
    public static Block rainbowGrassOrange;
    public static Block rainbowGrassYellow;
    public static Block rainbowGrassLime;
    public static Block rainbowGrassGreen;
    public static Block rainbowGrassLBlue;
    public static Block rainbowGrassCyan;
    public static Block rainbowGrassBlue;
    public static Block rainbowGrassPurple;
    public static Block rainbowGrassMagenta;
    public static Block rainbowGrassPink;
    public static Block rainbowGrassBrown;
    public static Block crystalEnergyOre;
    public static Block rainbowGolemHead;
    public static Block cloud;
    public static Item rainbowSeeds;
    public static Item lollipopStick;
    public static Item amplifyBomb;

    public static Item candyCaneRed;
    public static Block candyCaneRedBlock;
    public static Block candyCanePlanksRed;
    public static Item candyCaneGreen;
    public static Block candyCaneGreenBlock;
    public static Block candyCanePlanksGreen;
    public static Item candyCaneBlue;
    public static Block candyCaneBlueBlock;
    public static Block candyCanePlanksBlue;
    
    //Foodies
    public static Block saltOre;
    public static Item flour;
    public static Item salt;
    public static Item dough;
    public static Item doughCooked;
    public static Item cherry;
    public static Item orange;
    public static Item lemon;
    public static Item lime;
    public static Item cheeseBucket;
    public static Item cheese;
    public static Block cheeseBlock;
    public static Block pizzaBlock;
    public static Item pizza;
    public static Item lollipop;

    public static Item doritosOriginal;
    public static Item doritosTangy;
    public static Item doritosChilli;
    public static Item doritosJalepeno;
    public static Item doritosSalted;
    public static Item doritosLime;
    public static Item doritosBBQ; 

    public static Item dewOriginal;
    public static Item dewCodeRed;
    public static Item dewLiveWire;
    public static Item dewBaja;
    public static Item dewVoltage;
    public static Item dewWhiteout;
    public static Item dewSpecial;
    
    //Glasses
    public static int glassesID;
    public static Item glasses;
    public static int glassesWhiteID;
    public static Item glassesWhite;
    public static int glassesLGrayID;
    public static Item glassesLGray;
    public static int glassesGrayID;
    public static Item glassesGray;
    public static int glassesBlackID;
    public static Item glassesBlack;
    public static int glassesRedID;
    public static Item glassesRed;
    public static int glassesOrangeID;
    public static Item glassesOrange;
    public static int glassesYellowID;
    public static Item glassesYellow;
    public static int glassesGreenID;
    public static Item glassesGreen;
    public static int glassesLimeID;
    public static Item glassesLime;
    public static int glassesLBlueID;
    public static Item glassesLBlue;
    public static int glassesCyanID;
    public static Item glassesCyan;
    public static int glassesBlueID;
    public static Item glassesBlue;
    public static int glassesPurpleID;
    public static Item glassesPurple;
    public static int glassesMagentaID;
    public static Item glassesMagenta;
    public static int glassesPinkID;
    public static Item glassesPink;
    public static int glassesBrownID;
    public static Item glassesBrown;
    
    //Peripherals
    public static Item peripheralAntispreader;
    public static Item peripheralSpreader;
    public static Item peripheralNuclear;
    
    //Nostalgia
    public static Block nostalgiaBricks;
    public static Block nostalgiaCobble;
    public static Block nostalgiaCobbleMossy;
    public static Block nostalgiaChest;
    public static Block nostalgiaSapling;
    public static Block nostalgiaLeaves;
    public static Block nostalgiaGravel;
    public static Block nostalgiaGrass;
    public static Block nostalgiaIron;
    public static Block nostalgiaStonecutter;
    public static Block nostalgiaReactor;
    public static Block nostalgiaReactorActive;
    public static Block nostalgiaObsidianCrying;
    public static Block nostalgiaObsidianGlowing;

    public static Block nostalgiaWoolRed;
    public static Block nostalgiaWoolOrange;
    public static Block nostalgiaWoolYellow;
    public static Block nostalgiaWoolLime;
    public static Block nostalgiaWoolGreen;
    public static Block nostalgiaWoolTurquoise;
    public static Block nostalgiaWoolAqua;
    public static Block nostalgiaWoolLightBlue;
    public static Block nostalgiaWoolLavender;
    public static Block nostalgiaWoolPurple;
    public static Block nostalgiaWoolLightPurple;
    public static Block nostalgiaWoolMagenta;
    public static Block nostalgiaWoolHotPink;

    public static Item nostalgiaPorkchopRaw;
    public static Item nostalgiaPorkchopCooked;

    public static int nostalgiaClothHelmetID;
    public static Item nostalgiaClothHelmet;
    public static int nostalgiaClothChestplateID;
    public static Item nostalgiaClothChestplate;
    public static int nostalgiaClothLeggingsID;
    public static Item nostalgiaClothLeggings;
    public static int nostalgiaClothBootsID;
    public static Item nostalgiaClothBoots;
    public static int nostalgiaClothmailHelmetID;
    public static Item nostalgiaClothmailHelmet;
    public static int nostalgiaClothmailChestplateID;
    public static Item nostalgiaClothmailChestplate;
    public static int nostalgiaClothmailLeggingsID;
    public static Item nostalgiaClothmailLeggings;
    public static int nostalgiaClothmailBootsID;
    
    public static Block moonRock;
    public static Block torchOff;
    public static int spacesuitHelmetID;
    public static Item spacesuitHelmet;
    public static int spacesuitChestplateID;
    public static Item spacesuitChestplate;
    public static int spacesuitLeggingsID;
    public static Item spacesuitLeggings;
    public static int spacesuitBootsID;
    public static Item spacesuitBoots;
    
    //Item Materials
    public static ToolMaterial tmRuby;
    public static ToolMaterial tmEmerald;
    public static ToolMaterial tmLapis;
    public static ToolMaterial tmCrystal;
    public static ToolMaterial tmCactus;
    public static ToolMaterial tmCloud;
    public static ToolMaterial tmNeon;
    public static ArmorMaterial amRuby;
    public static ArmorMaterial amEmerald;
    public static ArmorMaterial amLapis;
    public static ArmorMaterial amCrystal;
    public static ArmorMaterial amCactus;
    public static ArmorMaterial amCloud;
    public static ArmorMaterial amNeon;
    public static ArmorMaterial amCosmetic;
    
    //Enchantments
    public static final Enchantment poisonAspect = new EnchantGeneric("poisonAspect", 174, 4, 1, 3, 15, 50, EnumEnchantmentType.weapon);
    public static final Enchantment frozenAspect = new EnchantGeneric("frozenAspect", 175, 5, 1, 3, 15, 50, EnumEnchantmentType.weapon);
    public static final Enchantment lifesteal = new EnchantGeneric("lifesteal", 176, 7, 1, 5, 17, 60, EnumEnchantmentType.weapon);
    public static final Enchantment mandible = new EnchantGeneric("mandible", 177, 4, 1, 5, 11, 40, EnumEnchantmentType.weapon);
    public static final Enchantment spring = new EnchantGeneric("spring", 178, 3, 1, 3, 10, 60, EnumEnchantmentType.weapon);
    public static final Enchantment wisdom = new EnchantGeneric("wisdom", 179, 6, 1, 4, 17, 60, EnumEnchantmentType.weapon);
    public static final Enchantment charged = new EnchantGeneric("charged", 180, 10, 1, 5, 17, 60, EnumEnchantmentType.weapon);
    public static final Enchantment lacerate = new EnchantGeneric("lacerate", 181, 5, 1, 3, 11, 50, EnumEnchantmentType.weapon);
    
    //Biomes
    public static BiomeGenBase biomeWasteland;
    public static BiomeGenBase biomeRainbow;
    public static BiomeGenBase biomeSheol;
    public static BiomeGenBase biomeMoon;
    
    //Block Sound Types
    public static final SoundType soundTypeRainbow = new BlockSounds("rainbow", 1, 1);
    public static final SoundType soundTypeSpace = new BlockSounds("space", 1, 1);
    public static final SoundType soundTypePoop = new BlockSounds("poop", 1, 1);
    public static final SoundType soundTypeClassicStone = new BlockClassicSounds("stone", 1, 1);
    public static final SoundType soundTypeClassicMetal = new BlockClassicSounds("metal", 1, 1);
    public static final SoundType soundTypeClassicWood = new BlockClassicSounds("wood", 1, 1);
    public static final SoundType soundTypeClassicGrass = new BlockClassicSounds("grass", 1, 1);
    public static final SoundType soundTypeClassicGravel = new BlockClassicSounds("gravel", 1, 1);
    public static final SoundType soundTypeClassicCloth = new BlockClassicSounds("cloth", 1, 1);
    
    public static SimpleNetworkWrapper channel;
    
    //Entity IDs
    public static int halfPlayer;
    public static int nukePrimed;
    public static int nukeMissile;
    public static int bullet;
    public static int mlgArrow;
    public static int amplifybomb;
    public static int psychoPig;
    public static int mummy;
    public static int fly;
    public static int derek;
    public static int derekJr;
    public static int tick;
    public static int lollipopper;
    public static int rainbowGolem;
    public static int rainbot;
    public static int amplislime;
    public static int unicorn;
    
    //Creative Tab
    public static CreativeTabs global = new CreativeTabs("yamGlobalTab") {
    	@Override
    	public Item getTabIconItem() {
    		return Item.getItemFromBlock(blockTest);
    	}
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	halfPlayer = registerEntity(EntityHalfPlayer.class, "halfplayer", null, 0, 0, 0);
    	nukePrimed = registerEntity(EntityNukePrimed.class, "primedNuke", null, 0, 0, 0);
    	nukeMissile = registerEntity(EntityNukeMissile.class, "missileNuke", null, 0, 0, 0);
    	bullet = registerEntity(EntityBullet.class, "bullet", null, 0, 0, 0);
    	amplifybomb = registerEntity(EntityAmplifyBomb.class, "amplifyBomb", null, 0, 0, 0);
    	mlgArrow = registerEntity(EntityMLGArrow.class, "mlgArrow", null, 0, 0, 0);
    	psychoPig = registerEntity(EntityPsychoPig.class, "psychoPig", EnumCreatureType.monster, 1, 1, 1, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SWAMP);
    	mummy = registerEntity(EntityMummy.class, "mummy", EnumCreatureType.monster, 250, 1, 4, BiomeDictionary.Type.DESERT);
    	fly = registerEntity(EntityFly.class, "fly", null, 0, 0, 0);
    	derek = registerEntity(EntityDerek.class, "derek", null, 0, 0, 0);
    	derekJr = registerEntity(EntityDerekJr.class, "derekJr", null, 0, 0, 0);
    	tick = registerEntity(EntityTick.class, "tick", null, 0, 0, 0);
    	lollipopper = registerEntity(EntityLollipopper.class, "lollipopper", null, 0, 0, 0);
    	rainbowGolem = registerEntity(EntityRainbowGolem.class, "rainbowGolem", null, 0, 0, 0);
    	rainbot = registerEntity(EntityRainbot.class, "rainbot", null, 0, 0, 0);
    	amplislime = registerEntity(EntityAmplislime.class, "amplislime", null, 0, 0, 0);
    	unicorn = registerEntity(EntityUnicorn.class, "unicorn", null, 0, 0, 0);
    	
    	proxy.registerRenderers();
    	
    	channel = NetworkRegistry.INSTANCE.newSimpleChannel("YetAnotherMod");
    	if (event.getSide() == Side.CLIENT) {
	    	channel.registerMessage(PacketParticle.Handler.class, PacketParticle.class, 0, Side.CLIENT);
	    	channel.registerMessage(PacketParticleSpark.Handler.class, PacketParticleSpark.class, 1, Side.CLIENT);
	    	channel.registerMessage(PacketParticleSparkUpwards.Handler.class, PacketParticleSparkUpwards.class, 2, Side.CLIENT);
	    	channel.registerMessage(PacketParticleExplosion.Handler.class, PacketParticleExplosion.class, 3, Side.CLIENT);
    	}
    }

	@EventHandler
    public void init(FMLInitializationEvent event)
    {
    	blockTest = new BlockGeneric(Material.ground, "testFront", "testSide", "testTop", "testBottom").setBlockName("blockTest");
    	GameRegistry.registerBlock(blockTest, "blockTest");
    	itemTest = new ItemTest("test").setUnlocalizedName("itemTest");
    	GameRegistry.registerItem(itemTest, "itemTest");

    	mud = new BlockGeneric(Material.ground, "mud").setBlockName("mud").setHardness(0.8F).setResistance(4F).setStepSound(Block.soundTypeGravel);
    	((BlockGeneric) mud).setSlipperiness(1.07F);
    	((BlockGeneric) mud).setExtraInformation("Slippery: \247aYes");
    	mud.setHarvestLevel("shovel", 0);
    	GameRegistry.registerBlock(mud, ItemBlockExtraInfo.class, "mud");
    	quicksand = new BlockQuicksand(Material.sand, "quicksand").setBlockName("quicksand").setHardness(2.2F).setResistance(2.5F).setLightOpacity(0).setStepSound(Block.soundTypeSand);
    	quicksand.setHarvestLevel("shovel", 0);
    	GameRegistry.registerBlock(quicksand, ItemBlockExtraInfo.class, "quicksand");
    	diamondPressurePlate = new BlockPlayerPlate();
    	diamondPressurePlate.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(diamondPressurePlate, ItemBlockExtraInfo.class, "diamondPressurePlate");
    	
    	bounceBlue = new BlockBouncepad("blue", 0.8D, 5.5D).setBlockName("bounceBlue");
    	GameRegistry.registerBlock(bounceBlue, ItemBlockExtraInfo.class, "bounceBlue");
    	bounceGreen = new BlockBouncepad("green", 1.2D, 9D).setBlockName("bounceGreen");
    	GameRegistry.registerBlock(bounceGreen, ItemBlockExtraInfo.class, "bounceGreen");
    	bounceYellow = new BlockBouncepad("yellow", 1.6D, 14D).setBlockName("bounceYellow");
    	GameRegistry.registerBlock(bounceYellow, ItemBlockExtraInfo.class, "bounceYellow");
    	bounceOrange = new BlockBouncepad("orange", 2D, 20D).setBlockName("bounceOrange");
    	GameRegistry.registerBlock(bounceOrange, ItemBlockExtraInfo.class, "bounceOrange");
    	bounceRed = new BlockBouncepad("red", 3D, 39D).setBlockName("bounceRed");
    	GameRegistry.registerBlock(bounceRed, ItemBlockExtraInfo.class, "bounceRed");
    	bounceCushion = new BlockBouncepad("cushion", 0D, 0D).setBlockName("bounceCushion");
    	GameRegistry.registerBlock(bounceCushion, ItemBlockExtraInfo.class, "bounceCushion");
    	
    	spreader1 = new BlockSpreading("spreader1", 0, false, false);
    	GameRegistry.registerBlock(spreader1, ItemBlockExtraInfo.class, "spreader1");
    	spreader2 = new BlockSpreading("spreader2", 1, false, false);
    	GameRegistry.registerBlock(spreader2, ItemBlockExtraInfo.class, "spreader2");
    	spreader3 = new BlockSpreading("spreader3", 2, false, false);
    	GameRegistry.registerBlock(spreader3, ItemBlockExtraInfo.class, "spreader3");
    	spreaderc1 = new BlockSpreading("spreaderc1", 0, true, false);
    	GameRegistry.registerBlock(spreaderc1, ItemBlockExtraInfo.class, "spreaderc1");
    	spreaderc2 = new BlockSpreading("spreaderc2", 1, true, false);
    	GameRegistry.registerBlock(spreaderc2, ItemBlockExtraInfo.class, "spreaderc2");
    	spreaderc3 = new BlockSpreading("spreaderc3", 2, true, false);
    	GameRegistry.registerBlock(spreaderc3, ItemBlockExtraInfo.class, "spreaderc3");
    	eater1 = new BlockSpreading("eater1", 0, false, true);
    	GameRegistry.registerBlock(eater1, ItemBlockExtraInfo.class, "eater1");
    	eater2 = new BlockSpreading("eater2", 1, false, true);
    	GameRegistry.registerBlock(eater2, ItemBlockExtraInfo.class, "eater2");
    	eater3 = new BlockSpreading("eater3", 2, false, true);
    	GameRegistry.registerBlock(eater3, ItemBlockExtraInfo.class, "eater3");
    	eaterc1 = new BlockSpreading("eaterc1", 0, true, true);
    	GameRegistry.registerBlock(eaterc1, ItemBlockExtraInfo.class, "eaterc1");
    	eaterc2 = new BlockSpreading("eaterc2", 1, true, true);
    	GameRegistry.registerBlock(eaterc2, ItemBlockExtraInfo.class, "eaterc2");
    	eaterc3 = new BlockSpreading("eaterc3", 2, true, true);
    	GameRegistry.registerBlock(eaterc3, ItemBlockExtraInfo.class, "eaterc3");
    	antispreader = new BlockGeneric(Material.ground, "spreaders/antispreader").setBlockName("antispreader").setHardness(0.3F).setResistance(0F).setStepSound(Block.soundTypeGlass).setLightLevel(1F).setLightOpacity(0);
    	GameRegistry.registerBlock(antispreader, "antispreader");

    	bricksCoal = new BlockGeneric(Material.rock, "bricks/coal").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksCoal");
    	bricksCoal.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(bricksCoal, "bricksCoal");
    	bricksIron = new BlockGeneric(Material.rock, "bricks/iron").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksIron");
    	bricksIron.setHarvestLevel("pickaxe", 1);
    	GameRegistry.registerBlock(bricksIron, "bricksIron");
    	bricksGold = new BlockGeneric(Material.rock, "bricks/gold").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksGold");
    	bricksGold.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(bricksGold, "bricksGold");
    	bricksDiamond = new BlockGeneric(Material.rock, "bricks/diamond").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksDiamond");
    	bricksDiamond.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(bricksDiamond, "bricksDiamond");
    	bricksObsidian = new BlockGeneric(Material.rock, "bricks/obsidian").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksObsidian");
    	bricksObsidian.setHarvestLevel("pickaxe", 3);
    	GameRegistry.registerBlock(bricksObsidian, "bricksObsidian");
    	bricksRedstone = new BlockGeneric(Material.rock, "bricks/redstone").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksRedstone");
    	bricksRedstone.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(bricksRedstone, "bricksRedstone");
    	bricksRuby = new BlockGeneric(Material.rock, "bricks/ruby").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksRuby");
    	bricksRuby.setHarvestLevel("pickaxe", 3);
    	GameRegistry.registerBlock(bricksRuby, "bricksRuby");
    	bricksEmerald = new BlockGeneric(Material.rock, "bricks/emerald").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksEmerald");
    	bricksEmerald.setHarvestLevel("pickaxe", 3);
    	GameRegistry.registerBlock(bricksEmerald, "bricksEmerald");
    	bricksLapis = new BlockGeneric(Material.rock, "bricks/lapis").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksLapis");
    	bricksLapis.setHarvestLevel("pickaxe", 3);
    	GameRegistry.registerBlock(bricksLapis, "bricksLapis");
    	bricksCrystal = new BlockGeneric(Material.rock, "bricks/crystal").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksCrystal").setLightOpacity(0);
    	bricksCrystal.setHarvestLevel("pickaxe", 4);
    	((BlockGeneric) bricksCrystal).setRenderType(0, false);
    	((BlockGeneric) bricksCrystal).setTransparency();
    	GameRegistry.registerBlock(bricksCrystal, "bricksCrystal");
    	bricksUranium = new BlockGeneric(Material.rock, "bricks/uranium").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksUranium");
    	bricksUranium.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(bricksUranium, "bricksUranium");
    	bricksPearl = new BlockGeneric(Material.rock, "bricks/pearl").setHardness(3.3F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("bricksPearl");
    	bricksPearl.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(bricksPearl, "bricksPearl");
    	
    	tmRuby = EnumHelper.addToolMaterial("Ruby", 4, (int)Math.pow(ToolMaterial.EMERALD.getMaxUses(), 1.05), 12.0F, 4.0F, 15); tmRuby.customCraftingMaterial = ruby;
    	tmEmerald = EnumHelper.addToolMaterial("RealEmerald", 4, tmRuby.getMaxUses(), 12.0F, 4.0F, 15); tmEmerald.customCraftingMaterial = Items.emerald;
    	tmLapis = EnumHelper.addToolMaterial("Lapis", 4, tmRuby.getMaxUses(), 12.0F, 4.0F, 15); tmLapis.customCraftingMaterial = lapisIngot;
    	tmCrystal = EnumHelper.addToolMaterial("Crystal", 5, (int)Math.pow(tmRuby.getMaxUses(), 1.05), 16.0F, 5.0F, 20); tmCrystal.customCraftingMaterial = crystalIngot;
    	tmCactus = EnumHelper.addToolMaterial("Cactus", 4, ToolMaterial.WOOD.getMaxUses(), 16.0F, 5.0F, 30); tmCactus.customCraftingMaterial = Item.getItemFromBlock(cactusBlock);
    	tmCloud = EnumHelper.addToolMaterial("Cloud", 6, (int)Math.pow(tmCrystal.getMaxUses(), 1.1), 20.0F, 6.0F, 25); tmCloud.customCraftingMaterial = Item.getItemFromBlock(cloud);
    	tmNeon = EnumHelper.addToolMaterial("Neon", 7, (int)Math.pow(tmCloud.getMaxUses(), 1.04), 25.0F, 7.0F, 30); tmNeon.customCraftingMaterial = neon;

    	int a = ArmorMaterial.DIAMOND.getDamageReductionAmount(0);
    	int b = ArmorMaterial.DIAMOND.getDamageReductionAmount(1);
    	int c = ArmorMaterial.DIAMOND.getDamageReductionAmount(2);
    	int d = ArmorMaterial.DIAMOND.getDamageReductionAmount(3);
    	
    	amRuby = EnumHelper.addArmorMaterial("Ruby", 45, new int[]{a, b+1, c+1, d}, 19);
    	amEmerald = EnumHelper.addArmorMaterial("RealEmerald", 45, new int[]{a, b+1, c+1, d}, 19);
    	amLapis = EnumHelper.addArmorMaterial("Lapis", 45, new int[]{a, b+1, c+1, d}, 19);
    	amCrystal = EnumHelper.addArmorMaterial("Crystal", 55, new int[]{a+1, b+2, c+2, d+1}, 25);
    	amCactus = EnumHelper.addArmorMaterial("Cactus", ArmorMaterial.CLOTH.getDurability(3), new int[]{a+1, b+2, c+2, d+1}, 30);
    	amCloud = EnumHelper.addArmorMaterial("Cloud", 65, new int[]{a+1, b+2, c+2, d+1}, 30);
    	amNeon = EnumHelper.addArmorMaterial("Neon", 72, new int[]{a+1, b+2, c+2, d+1}, 35);
    	amCosmetic = EnumHelper.addArmorMaterial("Cosmetic", 0, new int[]{0, 0, 0, 0}, 0);

    	cactusBlock = new BlockGeneric(Material.cactus, "cactus").setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeCloth).setBlockName("cactusBlock");
    	cactusBlock.setHarvestLevel("axe", 0);
    	GameRegistry.registerBlock(cactusBlock, "cactusBlock");
    	cactusSpine = new ItemGeneric("cactus/spine").setUnlocalizedName("cactusSpine");
    	GameRegistry.registerItem(cactusSpine, "cactusSpine");
    	cactusSword = new ItemCustomSword("cactus/sword", tmCactus).setUnlocalizedName("cactusSword");
    	GameRegistry.registerItem(cactusSword, "cactusSword");
    	cactusPickaxe = new ItemCustomPickaxe("cactus/pickaxe", tmCactus).setUnlocalizedName("cactusPickaxe");
    	GameRegistry.registerItem(cactusPickaxe, "cactusPickaxe");
    	cactusShovel = new ItemCustomShovel("cactus/shovel", tmCactus).setUnlocalizedName("cactusShovel");
    	GameRegistry.registerItem(cactusShovel, "cactusShovel");
    	cactusAxe = new ItemCustomAxe("cactus/axe", tmCactus).setUnlocalizedName("cactusAxe");
    	GameRegistry.registerItem(cactusAxe, "cactusAxe");
    	cactusHoe = new ItemCustomHoe("cactus/hoe", tmCactus).setUnlocalizedName("cactusHoe");
    	GameRegistry.registerItem(cactusHoe, "cactusHoe");
    	cactusHelmet = new ItemCustomArmor(amCactus, 5.6D, "cactus", cactusHelmetID, 0, false).setUnlocalizedName("cactusHelmet");
    	GameRegistry.registerItem(cactusHelmet, "cactusHelmet");
    	cactusChestplate = new ItemCustomArmor(amCactus, 5.6D, "cactus", cactusChestplateID, 1, false).setUnlocalizedName("cactusChestplate");
    	GameRegistry.registerItem(cactusChestplate, "cactusChestplate");
    	cactusLeggings = new ItemCustomArmor(amCactus, 5.6D, "cactus", cactusLeggingsID, 2, false).setUnlocalizedName("cactusLeggings");
    	GameRegistry.registerItem(cactusLeggings, "cactusLeggings");
    	cactusBoots = new ItemCustomArmor(amCactus, 5.6D, "cactus", cactusBootsID, 3, false).setUnlocalizedName("cactusBoots");
    	GameRegistry.registerItem(cactusBoots, "cactusBoots");
    	
    	ruby = new ItemGeneric("ruby/gem").setUnlocalizedName("ruby");
    	GameRegistry.registerItem(ruby, "ruby");
    	rubyOre = new BlockGeneric(Material.rock, "ores/ruby").setHardness(10.0F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("rubyOre");
    	((BlockGeneric) rubyOre).setDrops(ruby, 1, 1);
    	rubyOre.setHarvestLevel("pickaxe", 3);
    	GameRegistry.registerBlock(rubyOre, "rubyOre");
    	rubyBlock = new BlockGeneric(Material.iron, "storage/ruby").setHardness(12.0F).setResistance(6.0F).setStepSound(Block.soundTypeMetal).setBlockName("rubyBlock");
    	rubyBlock.setHarvestLevel("pickaxe", 3);
    	GameRegistry.registerBlock(rubyBlock, "rubyBlock");
    	rubySword = new ItemCustomSword("ruby/sword", tmRuby).setUnlocalizedName("rubySword");
    	GameRegistry.registerItem(rubySword, "rubySword");
    	rubyPickaxe = new ItemCustomPickaxe("ruby/pickaxe", tmRuby).setUnlocalizedName("rubyPickaxe");
    	GameRegistry.registerItem(rubyPickaxe, "rubyPickaxe");
    	rubyShovel = new ItemCustomShovel("ruby/shovel", tmRuby).setUnlocalizedName("rubyShovel");
    	GameRegistry.registerItem(rubyShovel, "rubyShovel");
    	rubyAxe = new ItemCustomAxe("ruby/axe", tmRuby).setUnlocalizedName("rubyAxe");
    	GameRegistry.registerItem(rubyAxe, "rubyAxe");
    	rubyHoe = new ItemCustomHoe("ruby/hoe", tmRuby).setUnlocalizedName("rubyHoe");
    	GameRegistry.registerItem(rubyHoe, "rubyHoe");
    	rubyHelmet = new ItemCustomArmor(amRuby, 5.3D, "ruby", rubyHelmetID, 0, false).setUnlocalizedName("rubyHelmet");
    	GameRegistry.registerItem(rubyHelmet, "rubyHelmet");
    	rubyChestplate = new ItemCustomArmor(amRuby, 5.3D, "ruby", rubyChestplateID, 1, false).setUnlocalizedName("rubyChestplate");
    	GameRegistry.registerItem(rubyChestplate, "rubyChestplate");
    	rubyLeggings = new ItemCustomArmor(amRuby, 5.3D, "ruby", rubyLeggingsID, 2, false).setUnlocalizedName("rubyLeggings");
    	GameRegistry.registerItem(rubyLeggings, "rubyLeggings");
    	rubyBoots = new ItemCustomArmor(amRuby, 5.3D, "ruby", rubyBootsID, 3, false).setUnlocalizedName("rubyBoots");
    	GameRegistry.registerItem(rubyBoots, "rubyBoots");
    	
    	emeraldSword = new ItemCustomSword("emerald/sword", tmEmerald).setUnlocalizedName("emeraldSword");
    	GameRegistry.registerItem(emeraldSword, "emeraldSword");
    	emeraldPickaxe = new ItemCustomPickaxe("emerald/pickaxe", tmEmerald).setUnlocalizedName("emeraldPickaxe");
    	GameRegistry.registerItem(emeraldPickaxe, "emeraldPickaxe");
    	emeraldShovel = new ItemCustomShovel("emerald/shovel", tmEmerald).setUnlocalizedName("emeraldShovel");
    	GameRegistry.registerItem(emeraldShovel, "emeraldShovel");
    	emeraldAxe = new ItemCustomAxe("emerald/axe", tmEmerald).setUnlocalizedName("emeraldAxe");
    	GameRegistry.registerItem(emeraldAxe, "emeraldAxe");
    	emeraldHoe = new ItemCustomHoe("emerald/hoe", tmEmerald).setUnlocalizedName("emeraldHoe");
    	GameRegistry.registerItem(emeraldHoe, "emeraldHoe");
    	emeraldHelmet = new ItemCustomArmor(amEmerald, 5.3D, "emerald", emeraldHelmetID, 0, false).setUnlocalizedName("emeraldHelmet");
    	GameRegistry.registerItem(emeraldHelmet, "emeraldHelmet");
    	emeraldChestplate = new ItemCustomArmor(amEmerald, 5.3D, "emerald", emeraldChestplateID, 1, false).setUnlocalizedName("emeraldChestplate");
    	GameRegistry.registerItem(emeraldChestplate, "emeraldChestplate");
    	emeraldLeggings = new ItemCustomArmor(amEmerald, 5.3D, "emerald", emeraldLeggingsID, 2, false).setUnlocalizedName("emeraldLeggings");
    	GameRegistry.registerItem(emeraldLeggings, "emeraldLeggings");
    	emeraldBoots = new ItemCustomArmor(amEmerald, 5.3D, "emerald", emeraldBootsID, 3, false).setUnlocalizedName("emeraldBoots");
    	GameRegistry.registerItem(emeraldBoots, "emeraldBoots");
    	
    	lapisGel = new ItemGeneric("lapis/gel").setUnlocalizedName("lapisGel");
    	GameRegistry.registerItem(lapisGel, "lapisGel");
    	lapisIngot = new ItemGeneric("lapis/ingot").setUnlocalizedName("lapisIngot");
    	GameRegistry.registerItem(lapisIngot, "lapisIngot");
    	lapisSword = new ItemCustomSword("lapis/sword", tmLapis).setUnlocalizedName("lapisSword");
    	GameRegistry.registerItem(lapisSword, "lapisSword");
    	lapisPickaxe = new ItemCustomPickaxe("lapis/pickaxe", tmLapis).setUnlocalizedName("lapisPickaxe");
    	GameRegistry.registerItem(lapisPickaxe, "lapisPickaxe");
    	lapisShovel = new ItemCustomShovel("lapis/shovel", tmLapis).setUnlocalizedName("lapisShovel");
    	GameRegistry.registerItem(lapisShovel, "lapisShovel");
    	lapisAxe = new ItemCustomAxe("lapis/axe", tmLapis).setUnlocalizedName("lapisAxe");
    	GameRegistry.registerItem(lapisAxe, "lapisAxe");
    	lapisHoe = new ItemCustomHoe("lapis/hoe", tmLapis).setUnlocalizedName("lapisHoe");
    	GameRegistry.registerItem(lapisHoe, "lapisHoe");
    	lapisHelmet = new ItemCustomArmor(amLapis, 5.3D, "lapis", lapisHelmetID, 0, false).setUnlocalizedName("lapisHelmet");
    	GameRegistry.registerItem(lapisHelmet, "lapisHelmet");
    	lapisChestplate = new ItemCustomArmor(amLapis, 5.3D, "lapis", lapisChestplateID, 1, false).setUnlocalizedName("lapisChestplate");
    	GameRegistry.registerItem(lapisChestplate, "lapisChestplate");
    	lapisLeggings = new ItemCustomArmor(amLapis, 5.3D, "lapis", lapisLeggingsID, 2, false).setUnlocalizedName("lapisLeggings");
    	GameRegistry.registerItem(lapisLeggings, "lapisLeggings");
    	lapisBoots = new ItemCustomArmor(amLapis, 5.3D, "lapis", lapisBootsID, 3, false).setUnlocalizedName("lapisBoots");
    	GameRegistry.registerItem(lapisBoots, "lapisBoots");
    	
    	crystalShard = new ItemGeneric("crystal/shard").setUnlocalizedName("crystalShard");
    	GameRegistry.registerItem(crystalShard, "crystalShard");
    	crystal = new ItemGeneric("crystal/gem").setUnlocalizedName("crystal");
    	GameRegistry.registerItem(crystal, "crystal");
    	crystalIngot = new ItemGeneric("crystal/ingot").setUnlocalizedName("crystalIngot");
    	GameRegistry.registerItem(crystalIngot, "crystalIngot");
    	crystalOre = new BlockGeneric(Material.rock, "ores/crystal").setHardness(10.0F).setResistance(3.0F).setStepSound(Block.soundTypeGlass).setBlockName("crystalOre").setLightOpacity(0);
    	((BlockGeneric) crystalOre).setDrops(crystalShard, 1, 4);
    	((BlockGeneric) crystalOre).setRenderType(0, false);
    	((BlockGeneric) crystalOre).setTransparency();
    	crystalOre.setHarvestLevel("pickaxe", 4);
    	GameRegistry.registerBlock(crystalOre, "crystalOre");
    	crystalBlock = new BlockGeneric(Material.iron, "storage/crystal").setHardness(8.0F).setResistance(1.0F).setStepSound(Block.soundTypeGlass).setBlockName("crystalBlock").setLightOpacity(0);
    	((BlockGeneric) crystalBlock).setRenderType(0, false);
    	((BlockGeneric) crystalBlock).setTransparency();
    	crystalBlock.setHarvestLevel("pickaxe", 4);
    	GameRegistry.registerBlock(crystalBlock, "crystalBlock");
    	crystalSword = new ItemCustomSword("crystal/sword", tmCrystal).setUnlocalizedName("crystalSword");
    	GameRegistry.registerItem(crystalSword, "crystalSword");
    	crystalPickaxe = new ItemCustomPickaxe("crystal/pickaxe", tmCrystal).setUnlocalizedName("crystalPickaxe");
    	GameRegistry.registerItem(crystalPickaxe, "crystalPickaxe");
    	crystalShovel = new ItemCustomShovel("crystal/shovel", tmCrystal).setUnlocalizedName("crystalShovel");
    	GameRegistry.registerItem(crystalShovel, "crystalShovel");
    	crystalAxe = new ItemCustomAxe("crystal/axe", tmCrystal).setUnlocalizedName("crystalAxe");
    	GameRegistry.registerItem(crystalAxe, "crystalAxe");
    	crystalHoe = new ItemCustomHoe("crystal/hoe", tmCrystal).setUnlocalizedName("crystalHoe");
    	GameRegistry.registerItem(crystalHoe, "crystalHoe");
    	crystalRepeater = new ItemRepeater("crystal/repeater", tmCrystal.getMaxUses(), 3).setUnlocalizedName("crystalRepeater");
    	GameRegistry.registerItem(crystalRepeater, "crystalRepeater");
    	crystalHelmet = new ItemCustomArmor(amCrystal, 5.6D, "crystal", crystalHelmetID, 0, false).setUnlocalizedName("crystalHelmet");
    	GameRegistry.registerItem(crystalHelmet, "crystalHelmet");
    	crystalChestplate = new ItemCustomArmor(amCrystal, 5.6D, "crystal", crystalChestplateID, 1, false).setUnlocalizedName("crystalChestplate");
    	GameRegistry.registerItem(crystalChestplate, "crystalChestplate");
    	crystalLeggings = new ItemCustomArmor(amCrystal, 5.6D, "crystal", crystalLeggingsID, 2, false).setUnlocalizedName("crystalLeggings");
    	GameRegistry.registerItem(crystalLeggings, "crystalLeggings");
    	crystalBoots = new ItemCustomArmor(amCrystal, 5.6D, "crystal", crystalBootsID, 3, false).setUnlocalizedName("crystalBoots");
    	GameRegistry.registerItem(crystalBoots, "crystalBoots");
    	crystalEnergy = new ItemEnergyCrystal("crystal/energy").setUnlocalizedName("crystalEnergy");
    	GameRegistry.registerItem(crystalEnergy, "crystalEnergy");

    	pearl = new ItemGeneric("pearl/stone").setUnlocalizedName("pearl");
    	GameRegistry.registerItem(pearl, "pearl");
    	pearlOre = new BlockGeneric(Material.rock, "ores/pearl").setHardness(2.0F).setResistance(4.0F).setStepSound(Block.soundTypeGravel).setBlockName("pearlOre");
    	((BlockGeneric) pearlOre).setDrops(pearl, 1, 1);
    	pearlOre.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(pearlOre, "pearlOre");
    	pearlBlock = new BlockGeneric(Material.iron, "storage/pearl").setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setBlockName("pearlBlock");
    	pearlBlock.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(pearlBlock, "pearlBlock");
    	pearlNecklace = new ItemPearlNecklace(pearlNecklaceID).setUnlocalizedName("pearlNecklace");
    	GameRegistry.registerItem(pearlNecklace, "pearlNecklace");
    	pearlNecklaceCosmetic = new ItemCosmeticArmor(YetAnotherMod.amCosmetic, "pearl", pearlNecklaceCosmeticID, 0, true).setUnlocalizedName("pearlNecklaceCosmetic");
    	GameRegistry.registerItem(pearlNecklaceCosmetic, "pearlNecklaceCosmetic");
    	
    	uranium = new ItemRadioactive("uranium", 1).setUnlocalizedName("uranium");
    	GameRegistry.registerItem(uranium, "uranium");
    	uraniumOre = new BlockRadioactive(Material.rock, "ores/uranium", 0).setHardness(25.0F).setResistance(6000000.0F).setStepSound(Block.soundTypeStone).setBlockName("uraniumOre");
    	((BlockGeneric) uraniumOre).setDrops(uranium, 1, 1);
    	uraniumOre.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(uraniumOre, ItemBlockRadioactive.class, "uraniumOre");
    	uraniumBlock = new BlockRadioactive(Material.iron, "storage/uranium", 2).setHardness(25.0F).setResistance(6000000.0F).setStepSound(Block.soundTypeMetal).setBlockName("uraniumBlock");
    	uraniumBlock.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(uraniumBlock, ItemBlockRadioactive.class, "uraniumBlock");
    	hazmatHelmet = new ItemCosmeticArmor(amCosmetic, "hazmat", hazmatHelmetID, 0, false).setUnlocalizedName("hazmatHelmet");
    	((ItemCosmeticArmor)hazmatHelmet).setExtraInformation("Immunity to: \2472Radiation");
    	GameRegistry.registerItem(hazmatHelmet, "hazmatHelmet");
    	hazmatChestplate = new ItemCosmeticArmor(amCosmetic, "hazmat", hazmatChestplateID, 1, false).setUnlocalizedName("hazmatChestplate");
    	((ItemCosmeticArmor)hazmatChestplate).setExtraInformation("Immunity to: \2472Radiation");
    	GameRegistry.registerItem(hazmatChestplate, "hazmatChestplate");
    	hazmatLeggings = new ItemCosmeticArmor(amCosmetic, "hazmat", hazmatLeggingsID, 2, false).setUnlocalizedName("hazmatLeggings");
    	((ItemCosmeticArmor)hazmatLeggings).setExtraInformation("Immunity to: \2472Radiation");
    	GameRegistry.registerItem(hazmatLeggings, "hazmatLeggings");
    	hazmatBoots = new ItemCosmeticArmor(amCosmetic, "hazmat", hazmatBootsID, 3, false).setUnlocalizedName("hazmatBoots");
    	((ItemCosmeticArmor)hazmatBoots).setExtraInformation("Immunity to: \2472Radiation");
    	GameRegistry.registerItem(hazmatBoots, "hazmatBoots");
    	nuke = new BlockNuke("nuke/side", "nuke/side", "nuke/top", "nuke/bottom").setBlockName("nuke");
    	GameRegistry.registerBlock(nuke, ItemBlockExtraInfo.class, "nuke");
    	nuclearFire = new BlockNukeFire("nuke/fire").setBlockName("nuclearFire");
    	GameRegistry.registerBlock(nuclearFire, "nuclearFire");
    	nuclearWaste = new BlockRadioactive(Material.rock, "nuke/waste", 0).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(Block.soundTypeStone).setBlockName("nuclearWaste");
    	GameRegistry.registerBlock(nuclearWaste, ItemBlockRadioactive.class, "nuclearWaste");
    	nukeRemote = new ItemNukeRemote("nuke", 6).setUnlocalizedName("nukeRemote");
    	GameRegistry.registerItem(nukeRemote, "nukeRemote");
    	atomSplitter = new ItemAtomSplitter("atomSplitter", YetAnotherMod.nuclearFire, 10).setUnlocalizedName("atomSplitter");
    	GameRegistry.registerItem(atomSplitter, "atomSplitter");
    	
    	flour = new ItemGeneric("food/flour").setUnlocalizedName("flour");
    	GameRegistry.registerItem(flour, "flour");
    	salt = new ItemGeneric("food/salt").setUnlocalizedName("salt");
    	GameRegistry.registerItem(salt, "salt");
    	saltOre = new BlockGeneric(Material.rock, "ores/salt").setHardness(3.0F).setResistance(6.0F).setStepSound(Block.soundTypeStone).setBlockName("saltOre");
    	((BlockGeneric) saltOre).setDrops(salt, 1, 1);
    	saltOre.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(saltOre, "saltOre");
    	dough = new ItemGeneric("food/doughRaw").setUnlocalizedName("dough");
    	GameRegistry.registerItem(dough, "dough");
    	doughCooked = new ItemGeneric("food/doughCooked").setUnlocalizedName("doughCooked");
    	GameRegistry.registerItem(doughCooked, "doughCooked");
    	cherry = new ItemNoms("cherry", 4, 0.3F, false).setUnlocalizedName("cherry");
    	GameRegistry.registerItem(cherry, "cherry");
    	orange = new ItemNoms("orange", 4, 0.3F, false).setUnlocalizedName("orange");
    	GameRegistry.registerItem(orange, "orange");
    	lemon = new ItemNoms("lemon", 4, 0.3F, false).setUnlocalizedName("lemon");
    	GameRegistry.registerItem(lemon, "lemon");
    	lime = new ItemNoms("lime", 4, 0.3F, false).setUnlocalizedName("lime");
    	GameRegistry.registerItem(lime, "lime");
    	cheeseBlock = new BlockGeneric(Material.gourd, "storage/cheese").setHardness(1.0F).setResistance(3.0F).setStepSound(Block.soundTypeWood).setBlockName("cheeseBlock");
    	cheeseBlock.setHarvestLevel("axe", 0);
    	GameRegistry.registerBlock(cheeseBlock, "cheeseBlock");
    	cheeseBucket = new ItemGeneric("food/cheeseBucket").setMaxStackSize(1).setContainerItem(Items.bucket).setUnlocalizedName("cheeseBucket");
    	GameRegistry.registerItem(cheeseBucket, "cheeseBucket");
    	cheese = new ItemNoms("cheese", 4, 0.3F, false).setUnlocalizedName("cheese");
    	GameRegistry.registerItem(cheese, "cheese");
    	pizzaBlock = new BlockSlices("pizza").setBlockName("pizza");
    	GameRegistry.registerBlock(pizzaBlock, "pizzaBlock");
    	pizza = new ItemPlacer("food/pizza", pizzaBlock).setMaxStackSize(1).setUnlocalizedName("pizza");
    	GameRegistry.registerItem(pizza, "pizza");
    	lollipopStick = new ItemGeneric("rainbow/stick").setUnlocalizedName("lollipopStick");
    	GameRegistry.registerItem(lollipopStick, "lollipopStick");
    	lollipop = new ItemNomsNewStack("lollipop", lollipopStick, 5, 0.1F, false).setUnlocalizedName("lollipop");
    	((ItemNomsNewStack) lollipop).setDrinkable();
    	GameRegistry.registerItem(lollipop, "lollipop");

    	doritosOriginal = new ItemNoms("doritos/cool", 7, 0.8F, false).setUnlocalizedName("doritosOriginal");
    	((ItemNoms) doritosOriginal).setAlwaysEdible();
    	((ItemNoms) doritosOriginal).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosOriginal, "doritosOriginal");
    	doritosTangy = new ItemNoms("doritos/tangy", 7, 0.8F, false).setUnlocalizedName("doritosTangy");
    	((ItemNoms) doritosTangy).setAlwaysEdible();
    	((ItemNoms) doritosTangy).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosTangy, "doritosTangy");
    	doritosChilli = new ItemNoms("doritos/chilli", 7, 0.8F, false).setUnlocalizedName("doritosChilli");
    	((ItemNoms) doritosChilli).setAlwaysEdible();
    	((ItemNoms) doritosChilli).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosChilli, "doritosChilli");
    	doritosJalepeno = new ItemNoms("doritos/jalepeno", 7, 0.8F, false).setUnlocalizedName("doritosJalepeno");
    	((ItemNoms) doritosJalepeno).setAlwaysEdible();
    	((ItemNoms) doritosJalepeno).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosJalepeno, "doritosJalepeno");
    	doritosSalted = new ItemNoms("doritos/salted", 7, 0.8F, false).setUnlocalizedName("doritosSalted");
    	((ItemNoms) doritosSalted).setAlwaysEdible();
    	((ItemNoms) doritosSalted).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosSalted, "doritosSalted");
    	doritosLime = new ItemNoms("doritos/lime", 7, 0.8F, false).setUnlocalizedName("doritosLime");
    	((ItemNoms) doritosLime).setAlwaysEdible();
    	((ItemNoms) doritosLime).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosLime, "doritosLime");
    	doritosBBQ = new ItemNoms("doritos/bbq", 7, 0.8F, false).setUnlocalizedName("doritosBBQ");
    	((ItemNoms) doritosBBQ).setAlwaysEdible();
    	((ItemNoms) doritosBBQ).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	GameRegistry.registerItem(doritosBBQ, "doritosBBQ");

    	dewOriginal = new ItemNoms("dew/original", 7, 0.8F, false).setUnlocalizedName("dewOriginal");
    	((ItemNoms) dewOriginal).setAlwaysEdible();
    	((ItemNoms) dewOriginal).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewOriginal).setDrinkable();
    	GameRegistry.registerItem(dewOriginal, "dewOriginal");
    	dewCodeRed = new ItemNoms("dew/codered", 7, 0.8F, false).setUnlocalizedName("dewCodeRed");
    	((ItemNoms) dewCodeRed).setAlwaysEdible();
    	((ItemNoms) dewCodeRed).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewCodeRed).setDrinkable();
    	GameRegistry.registerItem(dewCodeRed, "dewCodeRed");
    	dewLiveWire = new ItemNoms("dew/livewire", 7, 0.8F, false).setUnlocalizedName("dewLiveWire");
    	((ItemNoms) dewLiveWire).setAlwaysEdible();
    	((ItemNoms) dewLiveWire).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewLiveWire).setDrinkable();
    	GameRegistry.registerItem(dewLiveWire, "dewLiveWire");
    	dewBaja = new ItemNoms("dew/baja", 7, 0.8F, false).setUnlocalizedName("dewBaja");
    	((ItemNoms) dewBaja).setAlwaysEdible();
    	((ItemNoms) dewBaja).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewBaja).setDrinkable();
    	GameRegistry.registerItem(dewBaja, "dewBaja");
    	dewVoltage = new ItemNoms("dew/voltage", 7, 0.8F, false).setUnlocalizedName("dewVoltage");
    	((ItemNoms) dewVoltage).setAlwaysEdible();
    	((ItemNoms) dewVoltage).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewVoltage).setDrinkable();
    	GameRegistry.registerItem(dewVoltage, "dewVoltage");
    	dewWhiteout = new ItemNoms("dew/whiteout", 7, 0.8F, false).setUnlocalizedName("dewWhiteout");
    	((ItemNoms) dewWhiteout).setAlwaysEdible();
    	((ItemNoms) dewWhiteout).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewWhiteout).setDrinkable();
    	GameRegistry.registerItem(dewWhiteout, "dewWhiteout");
    	dewSpecial = new ItemNoms("dew/special", 7, 0.8F, false).setUnlocalizedName("dewSpecial");
    	((ItemNoms) dewSpecial).setAlwaysEdible();
    	((ItemNoms) dewSpecial).setPotionEffect(CustomPotion.mlg.id, 10, 0, 1.0F);
    	((ItemNoms) dewSpecial).setDrinkable();
    	GameRegistry.registerItem(dewSpecial, "dewSpecial");
    	
    	crackedMud = new BlockGeneric(Material.rock, "wasteland/mud").setHardness(7.0F).setResistance(50.0F).setStepSound(Block.soundTypeStone).setBlockName("crackedMud");
    	crackedMud.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(crackedMud, "crackedMud");
    	rust = new ItemGeneric("rust/rust").setUnlocalizedName("rust");
    	GameRegistry.registerItem(rust, "rust");
    	rustOre = new BlockGeneric(Material.rock, "ores/rust").setHardness(16.0F).setResistance(8.0F).setStepSound(Block.soundTypeStone).setBlockName("rustOre");
    	((BlockGeneric) rustOre).setDrops(rust, 1, 1);
    	rustOre.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(rustOre, "rustOre");
    	rustBlock = new BlockRustBlock(Material.iron, "storage/rust").setBlockUnbreakable().setHardness(18.0F).setResistance(8.0F).setStepSound(Block.soundTypeMetal).setBlockName("rustBlock");
    	((BlockGeneric) rustBlock).setDrops(Item.getItemFromBlock(Blocks.iron_block), 8, 1);
    	rustBlock.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(rustBlock, "rustBlock");
    	reinforcedStone = new BlockGeneric(Material.rock, "wasteland/stone").setHardness(60.0F).setResistance(999999999.0F).setStepSound(Block.soundTypeMetal).setBlockName("reinforcedStone");
    	GameRegistry.registerBlock(reinforcedStone, "reinforcedStone");
    	reinforcedGlass = new BlockGeneric(Material.rock, "wasteland/glass").setHardness(50.0F).setResistance(999999999.0F).setLightOpacity(0).setStepSound(Block.soundTypeGlass).setBlockName("reinforcedGlass");
    	((BlockGeneric) reinforcedGlass).setRenderType(0, false);
    	((BlockGeneric) reinforcedGlass).setTransparency();
    	GameRegistry.registerBlock(reinforcedGlass, "reinforcedGlass");
    	reinforcedWool = new BlockGeneric(Material.rock, "wasteland/wool").setHardness(50.0F).setResistance(999999999.0F).setStepSound(Block.soundTypeCloth).setBlockName("reinforcedWool");
    	GameRegistry.registerBlock(reinforcedWool, "reinforcedWool");
    	poop = new BlockPoop("wasteland/poop").setHardness(1.0F).setResistance(0.0F).setStepSound(soundTypePoop).setLightOpacity(0).setBlockName("poop");
    	((BlockGeneric)poop).setRenderType(0, false);
    	GameRegistry.registerBlock(poop, "poop");
    	derekHead = new BlockDerek("wasteland/derek/head").setHardness(14.0F).setResistance(16.0F).setStepSound(Block.soundTypeSnow).setBlockName("derekHead");
    	GameRegistry.registerBlock(derekHead, "derekHead");
    	derekHeart = new BlockGeneric(Material.ground, "wasteland/derek/heart").setHardness(14.0F).setResistance(16.0F).setStepSound(Block.soundTypeSnow).setBlockName("derekHeart");
    	GameRegistry.registerBlock(derekHeart, "derekHeart");
    	derekSoul = new BlockGeneric(Material.ground, "wasteland/derek/soul").setHardness(14.0F).setResistance(16.0F).setStepSound(Block.soundTypeSnow).setBlockName("derekSoul");
    	GameRegistry.registerBlock(derekSoul, "derekSoul");
    	derekSkin = new BlockGeneric(Material.ground, "wasteland/derek/skin").setHardness(14.0F).setResistance(16.0F).setStepSound(Block.soundTypeSnow).setBlockName("derekSkin");
    	GameRegistry.registerBlock(derekSkin, "derekSkin");
    	
    	hearthalf = new ItemHeart("half", 1.0F).setUnlocalizedName("hearthalf");
    	GameRegistry.registerItem(hearthalf, "hearthalf");
    	heart = new ItemHeart("whole", 2.0F).setUnlocalizedName("heart");
    	GameRegistry.registerItem(heart, "heart");
    	crystalheart = new ItemHeartContainer("crystal", 2.0F).setUnlocalizedName("crystalheart");
    	GameRegistry.registerItem(crystalheart, "crystalheart");
    	
    	pillBlack = new ItemPill("black").setUnlocalizedName("pillBlack");
    	GameRegistry.registerItem(pillBlack, "pillBlack");
    	pillBlue = new ItemPill("blue").setUnlocalizedName("pillBlue");
    	GameRegistry.registerItem(pillBlue, "pillBlue");
    	pillCyanWhite = new ItemPill("cyanwhite").setUnlocalizedName("pillCyanWhite");
    	GameRegistry.registerItem(pillCyanWhite, "pillCyanWhite");
    	pillGreen = new ItemPill("green").setUnlocalizedName("pillGreen");
    	GameRegistry.registerItem(pillGreen, "pillGreen");
    	pillOrangeCyan = new ItemPill("orangecyan").setUnlocalizedName("pillOrangeCyan");
    	GameRegistry.registerItem(pillOrangeCyan, "pillOrangeCyan");
    	pillOrangeYellow = new ItemPill("orangeyellow").setUnlocalizedName("pillOrangeYellow");
    	GameRegistry.registerItem(pillOrangeYellow, "pillOrangeYellow");
    	pillRed = new ItemPill("red").setUnlocalizedName("pillRed");
    	GameRegistry.registerItem(pillRed, "pillRed");
    	pillRedBlue = new ItemPill("redblue").setUnlocalizedName("pillRedBlue");
    	GameRegistry.registerItem(pillRedBlue, "pillRedBlue");
    	pillWhite = new ItemPill("white").setUnlocalizedName("pillWhite");
    	GameRegistry.registerItem(pillWhite, "pillWhite");

    	rainbowBlock = new BlockRainbow("linear", "radial").setHardness(75.0F).setResistance(200.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowBlock").setLightLevel(1F).setLightOpacity(0);
    	rainbowBlock.setHarvestLevel("pickaxe", 4);
    	GameRegistry.registerBlock(rainbowBlock, "rainbowBlock");
    	rainbowPortal = new BlockRainbowPortal("rainbow/portal").setBlockName("rainbowPortal").setStepSound(soundTypeRainbow);
    	GameRegistry.registerBlock(rainbowPortal, "rainbowPortal");
    	rainbowDirt = new BlockGeneric(Material.ground, "rainbow/dirt").setHardness(12.0F).setResistance(2.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowDirt");
    	rainbowDirt.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowDirt, "rainbowDirt");
    	rainbowStone = new BlockGeneric(Material.rock, "rainbow/stone").setHardness(30.0F).setResistance(15.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowStone").setLightOpacity(5);
    	rainbowStone.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(rainbowStone, "rainbowStone");
    	rainbowStoneRefined = new BlockGeneric(Material.rock, "rainbow/stoneRefined").setHardness(37.0F).setResistance(25.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowStoneRefined");
    	rainbowStoneRefined.setHarvestLevel("pickaxe", 5);
    	GameRegistry.registerBlock(rainbowStoneRefined, "rainbowStoneRefined");
    	rainbowStream = new BlockGeneric(Material.rock, "rainbow/stream").setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypeRainbow).setLightOpacity(0).setLightLevel(1.0F).setBlockName("rainbowStream");
    	((BlockGeneric) rainbowStream).canPassThrough();
    	((BlockGeneric) rainbowStream).setDamaging(CustomDamage.rainbowStream, 999999.0F);
    	GameRegistry.registerBlock(rainbowStream, "rainbowStream");
    	rainbowGrassWhite = new BlockRainbowGrass("white").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassWhite");
    	rainbowGrassWhite.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassWhite, "rainbowGrassWhite");
    	rainbowGrassLGray = new BlockRainbowGrass("lgray").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassLGray");
    	rainbowGrassLGray.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassLGray, "rainbowGrassLGray");
    	rainbowGrassGray = new BlockRainbowGrass("gray").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassGray");
    	rainbowGrassGray.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassGray, "rainbowGrassGray");
    	rainbowGrassBlack = new BlockRainbowGrass("black").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassBlack");
    	rainbowGrassBlack.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassBlack, "rainbowGrassBlack");
    	rainbowGrassRed = new BlockRainbowGrass("red").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassRed");
    	rainbowGrassRed.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassRed, "rainbowGrassRed");
    	rainbowGrassOrange = new BlockRainbowGrass("orange").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassOrange");
    	rainbowGrassOrange.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassOrange, "rainbowGrassOrange");
    	rainbowGrassYellow = new BlockRainbowGrass("yellow").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassYellow");
    	rainbowGrassYellow.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassYellow, "rainbowGrassYellow");
    	rainbowGrassGreen = new BlockRainbowGrass("green").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassGreen");
    	rainbowGrassGreen.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassGreen, "rainbowGrassGreen");
    	rainbowGrassLime = new BlockRainbowGrass("lime").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassLime");
    	rainbowGrassLime.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassLime, "rainbowGrassLime");
    	rainbowGrassLBlue = new BlockRainbowGrass("lblue").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassLBlue");
    	rainbowGrassLBlue.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassLBlue, "rainbowGrassLBlue");
    	rainbowGrassCyan = new BlockRainbowGrass("cyan").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassCyan");
    	rainbowGrassCyan.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassCyan, "rainbowGrassCyan");
    	rainbowGrassBlue = new BlockRainbowGrass("blue").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassBlue");
    	rainbowGrassBlue.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassBlue, "rainbowGrassBlue");
    	rainbowGrassPurple = new BlockRainbowGrass("purple").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassPurple");
    	rainbowGrassPurple.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassPurple, "rainbowGrassPurple");
    	rainbowGrassMagenta = new BlockRainbowGrass("magenta").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassMagenta");
    	rainbowGrassMagenta.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassMagenta, "rainbowGrassMagenta");
    	rainbowGrassPink = new BlockRainbowGrass("pink").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassPink");
    	rainbowGrassPink.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassPink, "rainbowGrassPink");
    	rainbowGrassBrown = new BlockRainbowGrass("brown").setHardness(15.0F).setResistance(3.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGrassBrown");
    	rainbowGrassBrown.setHarvestLevel("shovel", 5);
    	GameRegistry.registerBlock(rainbowGrassBrown, "rainbowGrassBrown");
    	crystalEnergyOre = new BlockGeneric(Material.rock, "ores/energycrystal").setHardness(40.0F).setResistance(20.0F).setStepSound(Block.soundTypeGlass).setBlockName("crystalEnergyOre").setLightOpacity(0);
    	((BlockGeneric) crystalEnergyOre).setDrops(crystalEnergy, 1, 1);
    	((BlockGeneric) crystalEnergyOre).setRenderType(0, false);
    	((BlockGeneric) crystalEnergyOre).setTransparency();
    	crystalEnergyOre.setHarvestLevel("pickaxe", 6);
    	GameRegistry.registerBlock(crystalEnergyOre, "crystalEnergyOre");
    	rainbowGolemHead = new BlockRainbowGolem().setHardness(40.0F).setResistance(20.0F).setStepSound(soundTypeRainbow).setBlockName("rainbowGolemHead");
    	rainbowGolemHead.setHarvestLevel("pickaxe", 6);
    	GameRegistry.registerBlock(rainbowGolemHead, "rainbowGolemHead");
    	rainbowSeeds = new ItemRainbowSeeds("rainbow/seeds").setUnlocalizedName("rainbowSeeds");
    	GameRegistry.registerItem(rainbowSeeds, "rainbowSeeds");
    	amplifyBomb = new ItemAmplifyBomb("rainbow/amplifyBomb").setUnlocalizedName("amplifyBomb");
    	GameRegistry.registerItem(amplifyBomb, "amplifyBomb");

    	candyCaneRedBlock = new BlockCandyCane(Material.rock, "rainbow/cane/red/cane").setBlockName("candyCaneRedBlock");
    	GameRegistry.registerBlock(candyCaneRedBlock, "candyCaneRedBlock");
    	candyCaneRed = new ItemPlacer("rainbow/cane/red", candyCaneRedBlock).setUnlocalizedName("candyCaneRed");
    	GameRegistry.registerItem(candyCaneRed, "candyCaneRed");
    	candyCanePlanksRed = new BlockGeneric(Material.rock, "rainbow/cane/red/planks").setHardness(30.0F).setResistance(20.0F).setStepSound(soundTypeRainbow).setBlockName("candyCaneRedPlanks");
    	candyCanePlanksRed.setHarvestLevel("axe", 5);
    	GameRegistry.registerBlock(candyCanePlanksRed, "candyCaneRedPlanks");
    	candyCaneGreenBlock = new BlockCandyCane(Material.rock, "rainbow/cane/green/cane").setBlockName("candyCaneGreenBlock");
    	GameRegistry.registerBlock(candyCaneGreenBlock, "candyCaneGreenBlock");
    	candyCaneGreen = new ItemPlacer("rainbow/cane/green", candyCaneGreenBlock).setUnlocalizedName("candyCaneGreen");
    	GameRegistry.registerItem(candyCaneGreen, "candyCaneGreen");
    	candyCanePlanksGreen = new BlockGeneric(Material.rock, "rainbow/cane/green/planks").setHardness(30.0F).setResistance(20.0F).setStepSound(soundTypeRainbow).setBlockName("candyCaneGreenPlanks");
    	candyCanePlanksGreen.setHarvestLevel("axe", 5);
    	GameRegistry.registerBlock(candyCanePlanksGreen, "candyCaneGreenPlanks");
    	candyCaneBlueBlock = new BlockCandyCane(Material.rock, "rainbow/cane/blue/cane").setBlockName("candyCaneBlueBlock");
    	GameRegistry.registerBlock(candyCaneBlueBlock, "candyCaneBlueBlock");
    	candyCaneBlue = new ItemPlacer("rainbow/cane/blue", candyCaneBlueBlock).setUnlocalizedName("candyCaneBlue");
    	GameRegistry.registerItem(candyCaneBlue, "candyCaneBlue");
    	candyCanePlanksBlue = new BlockGeneric(Material.rock, "rainbow/cane/blue/planks").setHardness(30.0F).setResistance(20.0F).setStepSound(soundTypeRainbow).setBlockName("candyCaneBluePlanks");
    	candyCanePlanksBlue.setHarvestLevel("axe", 5);
    	GameRegistry.registerBlock(candyCanePlanksBlue, "candyCaneBluePlanks");

    	cloud = new BlockCloud("rainbow/cloud").setBlockName("cloud");
    	((BlockGeneric) cloud).setRenderType(0, false);
    	GameRegistry.registerBlock(cloud, "cloud");
    	cloudSword = new ItemCustomSword("cloud/sword", tmCloud).setUnlocalizedName("cloudSword");
    	GameRegistry.registerItem(cloudSword, "cloudSword");
    	cloudPickaxe = new ItemCustomPickaxe("cloud/pickaxe", tmCloud).setUnlocalizedName("cloudPickaxe");
    	GameRegistry.registerItem(cloudPickaxe, "cloudPickaxe");
    	cloudShovel = new ItemCustomShovel("cloud/shovel", tmCloud).setUnlocalizedName("cloudShovel");
    	GameRegistry.registerItem(cloudShovel, "cloudShovel");
    	cloudAxe = new ItemCustomAxe("cloud/axe", tmCloud).setUnlocalizedName("cloudAxe");
    	GameRegistry.registerItem(cloudAxe, "cloudAxe");
    	cloudHoe = new ItemCustomHoe("cloud/hoe", tmCloud).setUnlocalizedName("cloudHoe");
    	GameRegistry.registerItem(cloudHoe, "cloudHoe");
    	cloudHelmet = new ItemCustomArmor(amCloud, 5.8D, "cloud", cloudHelmetID, 0, false).setUnlocalizedName("cloudHelmet");
    	GameRegistry.registerItem(cloudHelmet, "cloudHelmet");
    	cloudChestplate = new ItemCustomArmor(amCloud, 5.8D, "cloud", cloudChestplateID, 1, false).setUnlocalizedName("cloudChestplate");
    	GameRegistry.registerItem(cloudChestplate, "cloudChestplate");
    	cloudLeggings = new ItemCustomArmor(amCloud, 5.8D, "cloud", cloudLeggingsID, 2, false).setUnlocalizedName("cloudLeggings");
    	GameRegistry.registerItem(cloudLeggings, "cloudLeggings");
    	cloudBoots = new ItemFallingBoots(amCloud, 5.8D, "cloud", cloudBootsID).setUnlocalizedName("cloudBoots");
    	GameRegistry.registerItem(cloudBoots, "cloudBoots");
    	cloudBottle = new ItemExtraJump("cloud/bottle", ParticleType.RAINBOW, 0.8D).setUnlocalizedName("cloudBottle");
    	GameRegistry.registerItem(cloudBottle, "cloudBottle");

    	neon = new ItemGeneric("neon/item").setUnlocalizedName("neon");
    	GameRegistry.registerItem(neon, "neon");
    	neonOre = new BlockGeneric(Material.rock, "ores/neon").setHardness(40.0F).setResistance(40.0F).setStepSound(soundTypeRainbow).setLightOpacity(0).setLightLevel(1.0f).setBlockName("neonOre");
    	((BlockGeneric) neonOre).setDrops(neon, 1, 1);
    	neonOre.setHarvestLevel("pickaxe", 6);
    	GameRegistry.registerBlock(neonOre, "neonOre");
    	neonBlock = new BlockGeneric(Material.iron, "storage/neon").setHardness(50.0F).setResistance(50.0F).setStepSound(soundTypeRainbow).setLightOpacity(0).setLightLevel(1.0f).setBlockName("neonBlock");
    	neonBlock.setHarvestLevel("pickaxe", 6);
    	GameRegistry.registerBlock(neonBlock, "neonBlock");
    	neonSword = new ItemBackstabSword("neon/sword", ParticleType.NEON, tmNeon).setUnlocalizedName("neonSword");
    	GameRegistry.registerItem(neonSword, "neonSword");
    	neonPickaxe = new ItemCustomPickaxe("neon/pickaxe", tmNeon).setUnlocalizedName("neonPickaxe");
    	GameRegistry.registerItem(neonPickaxe, "neonPickaxe");
    	neonShovel = new ItemCustomShovel("neon/shovel", tmNeon).setUnlocalizedName("neonShovel");
    	GameRegistry.registerItem(neonShovel, "neonShovel");
    	neonAxe = new ItemCustomAxe("neon/axe", tmNeon).setUnlocalizedName("neonAxe");
    	GameRegistry.registerItem(neonAxe, "neonAxe");
    	neonHoe = new ItemCustomHoe("neon/hoe", tmNeon).setUnlocalizedName("neonHoe");
    	GameRegistry.registerItem(neonHoe, "neonHoe");
    	neonHelmet = new ItemCustomArmor(amNeon, 6.0D, "neon", neonHelmetID, 0, false).setUnlocalizedName("neonHelmet");
    	GameRegistry.registerItem(neonHelmet, "neonHelmet");
    	neonChestplate = new ItemCustomArmor(amNeon, 6.0D, "neon", neonChestplateID, 1, false).setUnlocalizedName("neonChestplate");
    	GameRegistry.registerItem(neonChestplate, "neonChestplate");
    	neonLeggings = new ItemCustomArmor(amNeon, 6.0D, "neon", neonLeggingsID, 2, false).setUnlocalizedName("neonLeggings");
    	GameRegistry.registerItem(neonLeggings, "neonLeggings");
    	neonBoots = new ItemCustomArmor(amNeon, 6.0D, "neon", neonBootsID, 3, false).setUnlocalizedName("neonBoots");
    	GameRegistry.registerItem(neonBoots, "neonBoots");
    	neonBottle = new ItemExtraJump("neon/bottle", ParticleType.NEON, 0.8D).setUnlocalizedName("neonBottle");
    	GameRegistry.registerItem(neonBottle, "neonBottle");

    	depthStone = new BlockGeneric(Material.rock, "sheol/depthStone").setBlockName("depthStone");
    	GameRegistry.registerBlock(depthStone, "depthStone");
    	hotCoal = new BlockHotCoal(Material.rock, "sheol/hotCoal").setBlockName("hotCoal");
    	GameRegistry.registerBlock(hotCoal, "hotCoal");
    	
    	spreaderRemoteWood = new ItemSpreaderRemote("wood", 8, 2).setUnlocalizedName("spreaderRemoteWood");
    	GameRegistry.registerItem(spreaderRemoteWood, "spreaderRemoteWood");
    	spreaderRemoteStone = new ItemSpreaderRemote("stone", 16, 4).setUnlocalizedName("spreaderRemoteStone");
    	GameRegistry.registerItem(spreaderRemoteStone, "spreaderRemoteStone");
    	spreaderRemoteIron = new ItemSpreaderRemote("iron", 24, 8).setUnlocalizedName("spreaderRemoteIron");
    	GameRegistry.registerItem(spreaderRemoteIron, "spreaderRemoteIron");
    	spreaderRemoteGold = new ItemSpreaderRemote("gold", 32, 1).setUnlocalizedName("spreaderRemoteGold");
    	GameRegistry.registerItem(spreaderRemoteGold, "spreaderRemoteGold");
    	spreaderRemoteDiamond = new ItemSpreaderRemote("diamond", 40, 16).setUnlocalizedName("spreaderRemoteDiamond");
    	GameRegistry.registerItem(spreaderRemoteDiamond, "spreaderRemoteDiamond");
    	spreaderRemoteRuby = new ItemSpreaderRemote("ruby", 80, 24).setUnlocalizedName("spreaderRemoteRuby");
    	GameRegistry.registerItem(spreaderRemoteRuby, "spreaderRemoteRuby");
    	spreaderRemoteEmerald = new ItemSpreaderRemote("emerald", 80, 24).setUnlocalizedName("spreaderRemoteEmerald");
    	GameRegistry.registerItem(spreaderRemoteEmerald, "spreaderRemoteEmerald");
    	spreaderRemoteLapis = new ItemSpreaderRemote("lapis", 80, 24).setUnlocalizedName("spreaderRemoteLapis");
    	GameRegistry.registerItem(spreaderRemoteLapis, "spreaderRemoteLapis");
    	spreaderRemoteCrystal = new ItemSpreaderRemote("crystal", 128, 32).setUnlocalizedName("spreaderRemoteCrystal");
    	GameRegistry.registerItem(spreaderRemoteCrystal, "spreaderRemoteCrystal");

    	antispreaderRemoteWood = new ItemAntispreaderRemote("wood", 8, 0).setUnlocalizedName("antispreaderRemoteWood");
    	GameRegistry.registerItem(antispreaderRemoteWood, "antispreaderRemoteWood");
    	antispreaderRemoteStone = new ItemAntispreaderRemote("stone", 16, 1).setUnlocalizedName("antispreaderRemoteStone");
    	GameRegistry.registerItem(antispreaderRemoteStone, "antispreaderRemoteStone");
    	antispreaderRemoteIron = new ItemAntispreaderRemote("iron", 24, 2).setUnlocalizedName("antispreaderRemoteIron");
    	GameRegistry.registerItem(antispreaderRemoteIron, "antispreaderRemoteIron");
    	antispreaderRemoteGold = new ItemAntispreaderRemote("gold", 32, 3).setUnlocalizedName("antispreaderRemoteGold");
    	GameRegistry.registerItem(antispreaderRemoteGold, "antispreaderRemoteGold");
    	antispreaderRemoteDiamond = new ItemAntispreaderRemote("diamond", 40, 4).setUnlocalizedName("antispreaderRemoteDiamond");
    	GameRegistry.registerItem(antispreaderRemoteDiamond, "antispreaderRemoteDiamond");
    	antispreaderRemoteRuby = new ItemAntispreaderRemote("ruby", 80, 6).setUnlocalizedName("antispreaderRemoteRuby");
    	GameRegistry.registerItem(antispreaderRemoteRuby, "antispreaderRemoteRuby");
    	antispreaderRemoteEmerald = new ItemAntispreaderRemote("emerald", 80, 6).setUnlocalizedName("antispreaderRemoteEmerald");
    	GameRegistry.registerItem(antispreaderRemoteEmerald, "antispreaderRemoteEmerald");
    	antispreaderRemoteLapis = new ItemAntispreaderRemote("lapis", 80, 6).setUnlocalizedName("antispreaderRemoteLapis");
    	GameRegistry.registerItem(antispreaderRemoteLapis, "antispreaderRemoteLapis");
    	antispreaderRemoteCrystal = new ItemAntispreaderRemote("crystal", 128, 8).setUnlocalizedName("antispreaderRemoteCrystal");
    	GameRegistry.registerItem(antispreaderRemoteCrystal, "antispreaderRemoteCrystal");

    	trashCanBlock = new BlockTrashCan().setBlockName("trashCanBlock");
    	((BlockGeneric)trashCanBlock).setDrops(trashCan, 1, 1);
    	((BlockGeneric)trashCanBlock).setRenderType(0, false);
    	GameRegistry.registerBlock(trashCanBlock, "trashCanBlock");
    	trashCan = new ItemPlacer("trashCan", trashCanBlock).setUnlocalizedName("trashCan");
    	GameRegistry.registerItem(trashCan, "trashCan");
    	tickField = new BlockTickField().setBlockName("tickField");
    	GameRegistry.registerBlock(tickField, "tickField");
    	
    	glasses = new ItemGlassesCosmetic("default", glassesID).setUnlocalizedName("glasses");
    	GameRegistry.registerItem(glasses, "glasses");
    	glassesWhite = new ItemGlassesCosmetic("white", glassesWhiteID).setUnlocalizedName("glassesWhite");
    	GameRegistry.registerItem(glassesWhite, "glassesWhite");
    	glassesLGray = new ItemGlassesCosmetic("lightgray", glassesLGrayID).setUnlocalizedName("glassesLGray");
    	GameRegistry.registerItem(glassesLGray, "glassesLGray");
    	glassesGray = new ItemGlassesCosmetic("gray", glassesGrayID).setUnlocalizedName("glassesGray");
    	GameRegistry.registerItem(glassesGray, "glassesGray");
    	glassesBlack = new ItemGlassesCosmetic("black", glassesBlackID).setUnlocalizedName("glassesBlack");
    	GameRegistry.registerItem(glassesBlack, "glassesBlack");
    	glassesRed = new ItemGlassesCosmetic("red", glassesRedID).setUnlocalizedName("glassesRed");
    	GameRegistry.registerItem(glassesRed, "glassesRed");
    	glassesOrange = new ItemGlassesCosmetic("orange", glassesOrangeID).setUnlocalizedName("glassesOrange");
    	GameRegistry.registerItem(glassesOrange, "glassesOrange");
    	glassesYellow = new ItemGlassesCosmetic("yellow", glassesYellowID).setUnlocalizedName("glassesYellow");
    	GameRegistry.registerItem(glassesYellow, "glassesYellow");
    	glassesGreen = new ItemGlassesCosmetic("green", glassesGreenID).setUnlocalizedName("glassesGreen");
    	GameRegistry.registerItem(glassesGreen, "glassesGreen");
    	glassesLime = new ItemGlassesCosmetic("lime", glassesLimeID).setUnlocalizedName("glassesLime");
    	GameRegistry.registerItem(glassesLime, "glassesLime");
    	glassesLBlue = new ItemGlassesCosmetic("lightblue", glassesLBlueID).setUnlocalizedName("glassesLBlue");
    	GameRegistry.registerItem(glassesLBlue, "glassesLBlue");
    	glassesCyan = new ItemGlassesCosmetic("cyan", glassesCyanID).setUnlocalizedName("glassesCyan");
    	GameRegistry.registerItem(glassesCyan, "glassesCyan");
    	glassesBlue = new ItemGlassesCosmetic("blue", glassesBlueID).setUnlocalizedName("glassesBlue");
    	GameRegistry.registerItem(glassesBlue, "glassesBlue");
    	glassesPurple = new ItemGlassesCosmetic("purple", glassesPurpleID).setUnlocalizedName("glassesPurple");
    	GameRegistry.registerItem(glassesPurple, "glassesPurple");
    	glassesMagenta = new ItemGlassesCosmetic("magenta", glassesMagentaID).setUnlocalizedName("glassesMagenta");
    	GameRegistry.registerItem(glassesMagenta, "glassesMagenta");
    	glassesPink = new ItemGlassesCosmetic("pink", glassesPinkID).setUnlocalizedName("glassesPink");
    	GameRegistry.registerItem(glassesPink, "glassesPink");
    	glassesBrown = new ItemGlassesCosmetic("brown", glassesBrownID).setUnlocalizedName("glassesBrown");
    	GameRegistry.registerItem(glassesBrown, "glassesBrown");

    	spikes = new BlockGeneric(Material.rock, "spikes/default").setHardness(3.0F).setResistance(7.5F).setLightOpacity(0).setStepSound(Block.soundTypeMetal).setBlockName("spikes");
    	spikes.setHarvestLevel("pickaxe", 2);
    	((BlockGeneric) spikes).setDamaging(CustomDamage.spikes, 3.0F);
    	((BlockGeneric) spikes).setRenderType(1, false);
    	((BlockGeneric) spikes).setExtraInformation("Damage: \247e1.5 hearts");
    	((BlockGeneric) spikes).setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    	GameRegistry.registerBlock(spikes, ItemBlockExtraInfo.class, "spikes");
    	bloodSpikes = new BlockGeneric(Material.rock, "spikes/blood").setHardness(4.0F).setResistance(15.0F).setLightOpacity(0).setStepSound(Block.soundTypeMetal).setBlockName("bloodSpikes");
    	bloodSpikes.setHarvestLevel("pickaxe", 3);
    	((BlockGeneric) bloodSpikes).setDamaging(CustomDamage.spikes, 6.0F);
    	((BlockGeneric) bloodSpikes).setRenderType(1, false);
    	((BlockGeneric) bloodSpikes).setExtraInformation("Damage: \247e3.0 hearts");
    	((BlockGeneric) bloodSpikes).setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    	GameRegistry.registerBlock(bloodSpikes, ItemBlockExtraInfo.class, "bloodSpikes");

    	peripheralSpreader = new ItemGeneric("peripheral/spreader").setUnlocalizedName("peripheralSpreader");
    	GameRegistry.registerItem(peripheralSpreader, "peripheralSpreader");
    	peripheralAntispreader = new ItemGeneric("peripheral/antispreader").setUnlocalizedName("peripheralAntispreader");
    	GameRegistry.registerItem(peripheralAntispreader, "peripheralAntispreader");
    	peripheralNuclear = new ItemGeneric("peripheral/nuclear").setUnlocalizedName("peripheralNuclear");
    	GameRegistry.registerItem(peripheralNuclear, "peripheralNuclear");
    	
    	blaster = new ItemBlaster("blaster", "pew", 7, 50, 17).setFull3D().setUnlocalizedName("blaster");
    	GameRegistry.registerItem(blaster, "blaster");

    	wireRed = new ItemGeneric("wiring/red").setUnlocalizedName("wireRed");
    	GameRegistry.registerItem(wireRed, "wireRed");
    	wireGreen = new ItemGeneric("wiring/green").setUnlocalizedName("wireGreen");
    	GameRegistry.registerItem(wireGreen, "wireGreen");
    	wireBlue = new ItemGeneric("wiring/blue").setUnlocalizedName("wireBlue");
    	GameRegistry.registerItem(wireBlue, "wireBlue");
    	wireBundled = new ItemGeneric("wiring/bundled").setUnlocalizedName("wireBundled");
    	GameRegistry.registerItem(wireBundled, "wireBundled");
    	keyboard = new ItemGeneric("parts/keyboard").setUnlocalizedName("keyboard");
    	GameRegistry.registerItem(keyboard, "keyboard");
    	mouse = new ItemGeneric("parts/mouse").setUnlocalizedName("mouse");
    	GameRegistry.registerItem(mouse, "mouse");
    	processor = new ItemGeneric("parts/processor").setUnlocalizedName("processor");
    	GameRegistry.registerItem(processor, "processor");
    	battery = new ItemGeneric("parts/battery").setUnlocalizedName("battery");
    	GameRegistry.registerItem(battery, "battery");
    	printer3D = new ItemGeneric("parts/printer3D").setUnlocalizedName("printer3D");
    	GameRegistry.registerItem(printer3D, "printer3D");
    	usb = new ItemUSB().setUnlocalizedName("usb");
    	GameRegistry.registerItem(usb, "usb");
    	usbOS = new ItemUSBFull().setUnlocalizedName("usbOS");
    	GameRegistry.registerItem(usbOS, "usbOS");
    	computerCase = new BlockGeneric(Material.iron, "computer/case", "computer/side", "computer/top", "computer/bottom").setHardness(7.5f).setResistance(13.0f).setStepSound(Block.soundTypeMetal).setBlockName("computerCase");
    	computerCase.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(computerCase, "computerCase");
    	computer = new BlockComputer(Material.iron, "computer").setBlockName("computer");
    	GameRegistry.registerBlock(computer, "computer");
    	smartphoneDisabled = new ItemGeneric("phone/off").setUnlocalizedName("smartphoneDisabled");
    	GameRegistry.registerItem(smartphoneDisabled, "smartphoneDisabled");
    	smartphone = new ItemSmartphone("phone/on").setUnlocalizedName("smartphone");
    	GameRegistry.registerItem(smartphone, "smartphone");
    	internet1G = new BlockInternet(Material.iron, "1G", 1.0f).setBlockName("internet1G");
    	GameRegistry.registerBlock(internet1G, "internet1G");
    	internet2G = new BlockInternet(Material.iron, "2G", 0.5f).setBlockName("internet2G");
    	GameRegistry.registerBlock(internet2G, "internet2G");
    	internet3G = new BlockInternet(Material.iron, "3G", 0.3f).setBlockName("internet3G");
    	GameRegistry.registerBlock(internet3G, "internet3G");
    	internet4G = new BlockInternet(Material.iron, "4G", 0.1f).setBlockName("internet4G");
    	GameRegistry.registerBlock(internet4G, "internet4G");

    	nostalgiaBricks = new BlockGeneric(Material.rock, "nostalgia/bricks").setHardness(2.0F).setResistance(30.0F).setStepSound(soundTypeClassicStone).setBlockName("nostalgiaBricks");
    	nostalgiaBricks.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(nostalgiaBricks, "nostalgiaBricks");
    	nostalgiaCobble = new BlockGeneric(Material.rock, "nostalgia/cobble").setHardness(2.0F).setResistance(30.0F).setStepSound(soundTypeClassicStone).setBlockName("nostalgiaCobble");
    	nostalgiaCobble.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(nostalgiaCobble, "nostalgiaCobble");
    	nostalgiaCobbleMossy = new BlockGeneric(Material.rock, "nostalgia/mossycobble").setHardness(2.0F).setResistance(30.0F).setStepSound(soundTypeClassicStone).setBlockName("nostalgiaCobbleMossy");
    	nostalgiaCobbleMossy.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(nostalgiaCobbleMossy, "nostalgiaCobbleMossy");
    	nostalgiaChest = new BlockClassicChest().setHardness(2.5F).setResistance(12.5F).setStepSound(soundTypeClassicWood).setBlockName("nostalgiaChest");
    	nostalgiaChest.setHarvestLevel("axe", 0);
    	GameRegistry.registerBlock(nostalgiaChest, "nostalgiaChest");
    	nostalgiaGrass = new BlockCustomGrass("nostalgia/grass/top", "nostalgia/grass/side", "nostalgia/grass/dirt", Blocks.dirt).setHardness(0.6F).setResistance(3.0F).setStepSound(soundTypeClassicGrass).setBlockName("nostalgiaGrass");
    	nostalgiaGrass.setHarvestLevel("shovel", 0);
    	GameRegistry.registerBlock(nostalgiaGrass, "nostalgiaGrass");
    	nostalgiaGravel = new BlockGeneric(Material.ground, "nostalgia/gravel").setHardness(0.6F).setResistance(3.0F).setStepSound(soundTypeClassicGravel).setBlockName("nostalgiaGravel");
    	nostalgiaGravel.setHarvestLevel("shovel", 0);
    	((BlockGeneric)nostalgiaGravel).setGravity(false);
    	GameRegistry.registerBlock(nostalgiaGravel, "nostalgiaGravel");
    	nostalgiaIron = new BlockGeneric(Material.iron, "nostalgia/ironblock").setHardness(5.0F).setResistance(30.0F).setStepSound(soundTypeClassicMetal).setBlockName("nostalgiaIron");
    	nostalgiaIron.setHarvestLevel("pickaxe", 1);
    	GameRegistry.registerBlock(nostalgiaIron, "nostalgiaIron");
    	nostalgiaSapling = new BlockCustomSapling("nostalgia/sapling");
    	GameRegistry.registerBlock(nostalgiaSapling, "nostalgiaSapling");
    	nostalgiaLeaves = new BlockCustomLeaves("nostalgia/leaves", nostalgiaSapling).setHardness(0.2F).setResistance(1.0F).setStepSound(soundTypeClassicGrass).setBlockName("nostalgiaLeaves");
    	GameRegistry.registerBlock(nostalgiaLeaves, "nostalgiaLeaves");
    	nostalgiaObsidianCrying = new BlockCryingObsidian().setBlockName("nostalgiaObsidianCrying");
    	GameRegistry.registerBlock(nostalgiaObsidianCrying, "nostalgiaObsidianCrying");
    	nostalgiaObsidianGlowing = new BlockGlowingObsidian().setBlockName("nostalgiaObsidianGlowing");
    	GameRegistry.registerBlock(nostalgiaObsidianGlowing, "nostalgiaObsidianGlowing");
    	nostalgiaReactorActive = new BlockGeneric(Material.iron, "nostalgia/reactor/on").setCreativeTab(null).setBlockName("nostalgiaReactorActive");
    	nostalgiaReactorActive.setHarvestLevel("pickaxe", 2);
    	GameRegistry.registerBlock(nostalgiaReactorActive, "nostalgiaReactorActive");
    	nostalgiaReactor = new BlockReactor(nostalgiaReactorActive).setBlockName("nostalgiaReactor");
    	GameRegistry.registerBlock(nostalgiaReactor, "nostalgiaReactor");
    	nostalgiaStonecutter = new BlockStonecutter().setHardness(3.5F).setResistance(17.5F).setStepSound(soundTypeClassicStone).setBlockName("nostalgiaStonecutter");
    	nostalgiaStonecutter.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(nostalgiaStonecutter, "nostalgiaStonecutter");

    	nostalgiaWoolRed = new BlockCustomWool("nostalgia/wool/red").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolRed");
    	GameRegistry.registerBlock(nostalgiaWoolRed, "nostalgiaWoolRed");
    	nostalgiaWoolOrange = new BlockCustomWool("nostalgia/wool/orange").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolOrange");
    	GameRegistry.registerBlock(nostalgiaWoolOrange, "nostalgiaWoolOrange");
    	nostalgiaWoolYellow = new BlockCustomWool("nostalgia/wool/yellow").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolYellow");
    	GameRegistry.registerBlock(nostalgiaWoolYellow, "nostalgiaWoolYellow");
    	nostalgiaWoolLime = new BlockCustomWool("nostalgia/wool/lime").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolLime");
    	GameRegistry.registerBlock(nostalgiaWoolLime, "nostalgiaWoolLime");
    	nostalgiaWoolGreen = new BlockCustomWool("nostalgia/wool/green").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolGreen");
    	GameRegistry.registerBlock(nostalgiaWoolGreen, "nostalgiaWoolGreen");
    	nostalgiaWoolTurquoise = new BlockCustomWool("nostalgia/wool/turquoise").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolTurquoise");
    	GameRegistry.registerBlock(nostalgiaWoolTurquoise, "nostalgiaWoolTurquoise");
    	nostalgiaWoolAqua = new BlockCustomWool("nostalgia/wool/aquablue").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolAqua");
    	GameRegistry.registerBlock(nostalgiaWoolAqua, "nostalgiaWoolAqua");
    	nostalgiaWoolLightBlue = new BlockCustomWool("nostalgia/wool/lightblue").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolLightBlue");
    	GameRegistry.registerBlock(nostalgiaWoolLightBlue, "nostalgiaWoolLightBlue");
    	nostalgiaWoolLavender = new BlockCustomWool("nostalgia/wool/lavender").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolLavender");
    	GameRegistry.registerBlock(nostalgiaWoolLavender, "nostalgiaWoolLavender");
    	nostalgiaWoolPurple = new BlockCustomWool("nostalgia/wool/purple").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolPurple");
    	GameRegistry.registerBlock(nostalgiaWoolPurple, "nostalgiaWoolPurple");
    	nostalgiaWoolLightPurple = new BlockCustomWool("nostalgia/wool/lightpurple").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolLightPurple");
    	GameRegistry.registerBlock(nostalgiaWoolLightPurple, "nostalgiaWoolLightPurple");
    	nostalgiaWoolMagenta = new BlockCustomWool("nostalgia/wool/magenta").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolMagenta");
    	GameRegistry.registerBlock(nostalgiaWoolMagenta, "nostalgiaWoolMagenta");
    	nostalgiaWoolHotPink = new BlockCustomWool("nostalgia/wool/hotpink").setHardness(0.8F).setResistance(4.0F).setStepSound(soundTypeClassicCloth).setBlockName("nostalgiaWoolHotPink");
    	GameRegistry.registerBlock(nostalgiaWoolHotPink, "nostalgiaWoolHotPink");

    	moonRock = new BlockGeneric(Material.rock, "space/moon/rock").setHardness(1.0F).setResistance(3.0F).setStepSound(soundTypeSpace).setBlockName("moonRock");
    	moonRock.setHarvestLevel("pickaxe", 0);
    	GameRegistry.registerBlock(moonRock, "moonRock");
    	torchOff = new BlockCustomTorch("space/torch").setBlockName("torchOff");
    	((BlockGeneric)torchOff).setDrops(Items.stick,1,1);
    	GameRegistry.registerBlock(torchOff, "torchOff");
    	spacesuitHelmet = new ItemCosmeticArmor(amCosmetic, "spacesuit", spacesuitHelmetID, 0, false).setUnlocalizedName("spacesuitHelmet");
    	GameRegistry.registerItem(spacesuitHelmet, "spacesuitHelmet");
    	spacesuitChestplate = new ItemCosmeticArmor(amCosmetic, "spacesuit", spacesuitChestplateID, 1, false).setUnlocalizedName("spacesuitChestplate");
    	GameRegistry.registerItem(spacesuitChestplate, "spacesuitChestplate");
    	spacesuitLeggings = new ItemCosmeticArmor(amCosmetic, "spacesuit", spacesuitLeggingsID, 2, false).setUnlocalizedName("spacesuitLeggings");
    	GameRegistry.registerItem(spacesuitLeggings, "spacesuitLeggings");
    	spacesuitBoots = new ItemCosmeticArmor(amCosmetic, "spacesuit", spacesuitBootsID, 3, false).setUnlocalizedName("spacesuitBoots");
    	GameRegistry.registerItem(spacesuitBoots, "spacesuitBoots");
    	
    	halfplayerSpawnEgg = new ItemSpawnEgg(halfPlayer, 32767, 7556134).setUnlocalizedName("halfplayerSpawnEgg");
    	GameRegistry.registerItem(halfplayerSpawnEgg, "halfplayerSpawnEgg");
    	psychopigSpawnEgg = new ItemSpawnEgg(psychoPig, 15771042, 0).setUnlocalizedName("psychopigSpawnEgg");
    	GameRegistry.registerItem(psychopigSpawnEgg, "psychopigSpawnEgg");
    	mummySpawnEgg = new ItemSpawnEgg(mummy, Integer.parseInt("AA8800", 16), Integer.parseInt("666600", 16)).setUnlocalizedName("mummySpawnEgg");
    	GameRegistry.registerItem(mummySpawnEgg, "mummySpawnEgg");
    	flySpawnEgg = new ItemSpawnEgg(fly, Integer.parseInt("333333", 16), Integer.parseInt("000000", 16)).setUnlocalizedName("flySpawnEgg");
    	GameRegistry.registerItem(flySpawnEgg, "flySpawnEgg");
    	derekJrSpawnEgg = new ItemSpawnEgg(derekJr, Integer.parseInt("EBC6AB", 16), Integer.parseInt("802525", 16)).setUnlocalizedName("derekJrSpawnEgg");
    	GameRegistry.registerItem(derekJrSpawnEgg, "derekJrSpawnEgg");
    	tickSpawnEgg = new ItemSpawnEgg(tick, Integer.parseInt("EBC6AB", 16), Integer.parseInt("808080", 16)).setUnlocalizedName("tickSpawnEgg");
    	GameRegistry.registerItem(tickSpawnEgg, "tickSpawnEgg");
    	
    	lollipopperSpawnEgg = new ItemRainbowSpawnEgg(lollipopper, Integer.parseInt("FFFFFF", 16)).setUnlocalizedName("lollipopperSpawnEgg");
    	GameRegistry.registerItem(lollipopperSpawnEgg, "lollipopperSpawnEgg");
    	rainbotSpawnEgg = new ItemRainbowSpawnEgg(rainbot, Integer.parseInt("666666", 16)).setUnlocalizedName("rainbotSpawnEgg");
    	GameRegistry.registerItem(rainbotSpawnEgg, "rainbotSpawnEgg");
    	amplislimeSpawnEgg = new ItemRainbowSpawnEgg(amplislime, Integer.parseInt("FF9922", 16)).setUnlocalizedName("amplislimeSpawnEgg");
    	GameRegistry.registerItem(amplislimeSpawnEgg, "amplislimeSpawnEgg");
    	unicornSpawnEgg = new ItemRainbowSpawnEgg(unicorn, Integer.parseInt("CCBBEE", 16)).setUnlocalizedName("unicornSpawnEgg");
    	GameRegistry.registerItem(unicornSpawnEgg, "unicornSpawnEgg");
    	
    	Recipes.loadRecipes();
    	Achieves.loadAchievements();

    	Events e = new Events();
    	MinecraftForge.EVENT_BUS.register(e);
    	MinecraftForge.TERRAIN_GEN_BUS.register(e);

    	GameRegistry.registerTileEntity(TileEntityTrashCan.class, MODID+":TileEntityTrashCan");
    	GameRegistry.registerTileEntity(TileEntityTickField.class, MODID+":TileEntityTickField");
    	GameRegistry.registerTileEntity(TileEntityClassicChest.class, MODID+":TileEntityClassicChest");
    	GameRegistry.registerTileEntity(TileEntityStonecutter.class, MODID+":TileEntityStonecutter");
    	GameRegistry.registerTileEntity(TileEntityComputer.class, MODID+":TileEntityComputer");
    	//GameRegistry.registerTileEntity(TileEntityComputerSpace.class, MODID+":TileEntityComputerSpace");
    	//GameRegistry.registerTileEntity(TileEntityComputerDev.class, MODID+":TileEntityComputerSpace");
    	proxy.registerTileEntitySpecialRenderer();
    	
    	BiomeDictionary.registerBiomeType(biomeWasteland = new BiomeWasteland(50), BiomeDictionary.Type.DESERT, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WASTELAND);
    	BiomeDictionary.registerBiomeType(biomeRainbow = new BiomeRainbow(51));
    	BiomeDictionary.registerBiomeType(biomeSheol = new BiomeSheol(52));
    	BiomeDictionary.registerBiomeType(biomeMoon = new BiomeMoon(53));
    	BiomeDictionary.registerAllBiomesAndGenerateEvents();
    	
    	GameRegistry.registerWorldGenerator(new WorldGen(), 1000);
 
    	rainbowDimID = DimensionManager.getNextFreeDimId();
    	DimensionManager.registerProviderType(rainbowDimID, RainbowDimProvider.class, false);
    	DimensionManager.registerDimension(rainbowDimID, rainbowDimID);
    	sheolDimID = DimensionManager.getNextFreeDimId();
    	DimensionManager.registerProviderType(sheolDimID, SheolDimProvider.class, false);
    	DimensionManager.registerDimension(sheolDimID, sheolDimID);
    	moonDimID = DimensionManager.getNextFreeDimId();
    	DimensionManager.registerProviderType(moonDimID, MoonDimProvider.class, false);
    	DimensionManager.registerDimension(moonDimID, moonDimID);
    	asmiaDimID = DimensionManager.getNextFreeDimId();
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
	
	@EventHandler
    public void loadServer(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdGetPos());
        event.registerServerCommand(new CmdHarm());
        event.registerServerCommand(new CmdHeal());
        event.registerServerCommand(new CmdExplode());
        event.registerServerCommand(new CmdSuperEnchant());
        event.registerServerCommand(new CmdMotion());
        event.registerServerCommand(new CmdSudo());
        event.registerServerCommand(new CmdRepeat());
    }
    
    //Custom methods
    public static int registerEntity(Class entityClass, String name, EnumCreatureType type, int weight, int min, int max, BiomeDictionary.Type... spawns) {
	    int entityID = EntityRegistry.findGlobalUniqueEntityId();
	
	    EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
	    EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
	    
	    for (BiomeDictionary.Type biome : spawns) {
	    	EntityRegistry.addSpawn(entityClass, weight, min, max, type, BiomeDictionary.getBiomesForType(biome));
	    }
    		    
	    return entityID;
	}
    
}
