package spout.gamecontent.datadriven.blocktype;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableFeaturePlacerBlock;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CactusFlowerBlock;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.CartographyTableBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.ConduitBlock;
import net.minecraft.world.level.block.CopperBulbBlock;
import net.minecraft.world.level.block.CopperChestBlock;
import net.minecraft.world.level.block.CopperGolemStatueBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.CoralPlantBlock;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.CrafterBlock;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.CreakingHeartBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.CryingObsidianBlock;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.DriedGhastBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.DryVegetationBlock;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.EndGatewayBlock;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.EyeblossomBlock;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FireflyBushBlock;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.FrogspawnBlock;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.HangingRootsBlock;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.HeavyCoreBlock;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.InfestedRotatedPillarBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LoomBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.MangroveLeavesBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.NetherRootsBlock;
import net.minecraft.world.level.block.NetherSproutsBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.PathBlock;
import net.minecraft.world.level.block.PiglinWallSkullBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.PlayerWallHeadBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.PotentSulfurBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.RedstoneWallTorchBlock;
import net.minecraft.world.level.block.RedstoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkCatalystBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.ShelfMushroomBlock;
import net.minecraft.world.level.block.ShortDryGrassBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.SnifferEggBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.SporeBlossomBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.StrawBedBlock;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.StructureVoidBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.SulfurSpikeBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.TallDryGrassBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.TestBlock;
import net.minecraft.world.level.block.TestInstanceBlock;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.TrialSpawnerBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.TwistingVinesBlock;
import net.minecraft.world.level.block.TwistingVinesPlantBlock;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.WeatheringCopperBarsBlock;
import net.minecraft.world.level.block.WeatheringCopperBulbBlock;
import net.minecraft.world.level.block.WeatheringCopperChainBlock;
import net.minecraft.world.level.block.WeatheringCopperChestBlock;
import net.minecraft.world.level.block.WeatheringCopperDoorBlock;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.minecraft.world.level.block.WeatheringCopperGolemStatueBlock;
import net.minecraft.world.level.block.WeatheringCopperGrateBlock;
import net.minecraft.world.level.block.WeatheringCopperSlabBlock;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.WeatheringCopperTrapDoorBlock;
import net.minecraft.world.level.block.WeatheringLanternBlock;
import net.minecraft.world.level.block.WeatheringLightningRodBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.WeepingVinesBlock;
import net.minecraft.world.level.block.WeepingVinesPlantBlock;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.WitherRoseBlock;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.WitherWallSkullBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.material.FlowingFluid;
import spout.branding.SpoutNamespace;
import spout.gamecontent.builtin.block.HalfTransparentSlabBlock;
import spout.gamecontent.builtin.block.HalfTransparentStairBlock;
import spout.gamecontent.builtin.block.QuadBlock;
import spout.gamecontent.builtin.block.TransparentSlabBlock;
import spout.gamecontent.builtin.block.TransparentStairBlock;
import spout.gamecontent.builtin.block.VerticalSlabBlock;
import spout.gamecontent.datadriven.block.BlockCodecs;
import spout.gamecontent.datadriven.block.LiquidBlockFluidDecorator;
import java.util.Optional;
import java.util.function.Function;

/**
 * Built-in values for the {@link BuiltInBlockTypeRegistry#BLOCK_TYPE} registry.
 */
public final class SpoutBlockTypes {

    private SpoutBlockTypes() {
        throw new UnsupportedOperationException();
    }

    public static final SpoutBlockType BLOCK = register("block", BlockCodecs.simpleCodec(Block::new), Block.class);
    public static final SpoutBlockType AIR = register("air", BlockCodecs.simpleCodec(AirBlock::new), AirBlock.class);
    public static final SpoutBlockType AMETHYST = register("amethyst", BlockCodecs.simpleCodec(AmethystBlock::new), AmethystBlock.class);
    public static final SpoutBlockType AMETHYST_CLUSTER = register("amethyst_cluster", BlockCodecs.simpleCodec(
        Codec.FLOAT.fieldOf("height").forGetter(b -> b.height),
        Codec.FLOAT.fieldOf("width").forGetter(b -> b.width),
        AmethystClusterBlock::new
    ), AmethystClusterBlock.class);
    public static final SpoutBlockType ANVIL = register("anvil", BlockCodecs.simpleCodec(AnvilBlock::new), AnvilBlock.class);
    public static final SpoutBlockType ATTACHED_STEM = register("attached_stem", BlockCodecs.simpleCodec(
        BlockCodecs.stemFruitApp(b -> b.fruit),
        ResourceKey.codec(Registries.BLOCK).fieldOf("stem").forGetter(b -> b.stem),
        BlockCodecs.stemSeedApp(b -> b.seed),
        BlockCodecs.supportBlocksApp(b -> b.supportBlocks),
        AttachedStemBlock::new
    ), AttachedStemBlock.class);
    public static final SpoutBlockType AZALEA = register("azalea", BlockCodecs.simpleCodec(AzaleaBlock::new), AzaleaBlock.class);
    public static final SpoutBlockType BAMBOO_SAPLING = register("bamboo_sapling", BlockCodecs.simpleCodec(BambooSaplingBlock::new), BambooSaplingBlock.class);
    public static final SpoutBlockType BAMBOO_STALK = register("bamboo_stalk", BlockCodecs.simpleCodec(BambooStalkBlock::new), BambooStalkBlock.class);
    public static final SpoutBlockType BANNER = register("banner", BlockCodecs.bannerCodec(BannerBlock::new), BannerBlock.class);
    public static final SpoutBlockType BARREL = register("barrel", BlockCodecs.simpleCodec(BarrelBlock::new), BarrelBlock.class);
    public static final SpoutBlockType BARRIER = register("barrier", BlockCodecs.simpleCodec(BarrierBlock::new), BarrierBlock.class);
    public static final SpoutBlockType BASE_CORAL_FAN = register("base_coral_fan", BlockCodecs.simpleCodec(BaseCoralFanBlock::new), BaseCoralFanBlock.class);
    public static final SpoutBlockType BASE_CORAL_PLANT = register("base_coral_plant", BlockCodecs.simpleCodec(BaseCoralPlantBlock::new), BaseCoralPlantBlock.class);
    public static final SpoutBlockType BASE_CORAL_WALL_FAN = register("base_coral_wall_fan", BlockCodecs.simpleCodec(BaseCoralWallFanBlock::new), BaseCoralWallFanBlock.class);
    public static final SpoutBlockType BEACON = register("beacon", BlockCodecs.simpleCodec(BeaconBlock::new), BeaconBlock.class);
    public static final SpoutBlockType BED = register("bed", BlockCodecs.simpleCodec(
        BlockCodecs.dyeColorApp(BedBlock::getColor),
        BedBlock::new
    ), BedBlock.class);
    public static final SpoutBlockType BEEHIVE = register("beehive", BlockCodecs.simpleCodec(BeehiveBlock::new), BeehiveBlock.class);
    public static final SpoutBlockType BEETROOT = register("beetroot", BlockCodecs.simpleCodec(BeetrootBlock::new), BeetrootBlock.class);
    public static final SpoutBlockType BELL = register("bell", BlockCodecs.simpleCodec(BellBlock::new), BellBlock.class);
    public static final SpoutBlockType BIG_DRIPLEAF = register("big_dripleaf", BlockCodecs.simpleCodec(BigDripleafBlock::new), BigDripleafBlock.class);
    public static final SpoutBlockType BIG_DRIPLEAF_STEM = register("big_dripleaf_stem", BlockCodecs.simpleCodec(BigDripleafStemBlock::new), BigDripleafStemBlock.class);
    public static final SpoutBlockType BLAST_FURNACE = register("blast_furnace", BlockCodecs.simpleCodec(BlastFurnaceBlock::new), BlastFurnaceBlock.class);
    public static final SpoutBlockType BONEMEALABLE_FEATURE_PLACER = register("bonemealable_feature_placer", BlockCodecs.simpleCodec(
        BlockCodecs.featureApp(b -> b.feature),
        BonemealableFeaturePlacerBlock::new
    ), BonemealableFeaturePlacerBlock.class);
    public static final SpoutBlockType BREWING_STAND = register("brewing_stand", BlockCodecs.simpleCodec(BrewingStandBlock::new), BrewingStandBlock.class);
    public static final SpoutBlockType BRUSHABLE = register("brushable", BlockCodecs.simpleCodec(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("turns_into").forGetter(BrushableBlock::getTurnsInto),
        BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("brush_sound").forGetter(BrushableBlock::getBrushSound),
        BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("brush_completed_sound").forGetter(BrushableBlock::getBrushCompletedSound),
        BrushableBlock::new
    ), BrushableBlock.class);
    public static final SpoutBlockType BUBBLE_COLUMN = register("bubble_column", BlockCodecs.simpleCodec(BubbleColumnBlock::new), BubbleColumnBlock.class);
    public static final SpoutBlockType BUDDING_AMETHYST = register("budding_amethyst", BlockCodecs.simpleCodec(BuddingAmethystBlock::new), BuddingAmethystBlock.class);
    public static final SpoutBlockType BUSH = register("bush", BlockCodecs.simpleCodec(BushBlock::new), BushBlock.class);
    public static final SpoutBlockType BUTTON = register("button", BlockCodecs.simpleCodec(
        BlockCodecs.blockSetTypeApp(b -> b.type),
        Codec.intRange(1, 1024).fieldOf("ticks_to_stay_pressed").forGetter(b -> b.ticksToStayPressed),
        ButtonBlock::new
    ), ButtonBlock.class);
    public static final SpoutBlockType CACTUS = register("cactus", BlockCodecs.simpleCodec(CactusBlock::new), CactusBlock.class);
    public static final SpoutBlockType CACTUS_FLOWER = register("cactus_flower", BlockCodecs.simpleCodec(CactusFlowerBlock::new), CactusFlowerBlock.class);
    public static final SpoutBlockType CAKE = register("cake", BlockCodecs.simpleCodec(CakeBlock::new), CakeBlock.class);
    public static final SpoutBlockType CALIBRATED_SCULK_SENSOR = register("calibrated_sculk_sensor", BlockCodecs.simpleCodec(CalibratedSculkSensorBlock::new), CalibratedSculkSensorBlock.class);
    public static final SpoutBlockType CAMPFIRE = register("campfire", BlockCodecs.simpleCodec(
        Codec.BOOL.fieldOf("spawn_particles").forGetter(b -> b.spawnParticles),
        Codec.intRange(0, 1000).fieldOf("fire_damage").forGetter(b -> b.fireDamage),
        CampfireBlock::new
    ), CampfireBlock.class);
    public static final SpoutBlockType CANDLE_CAKE = register("candle_cake", BlockCodecs.simpleCodec(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("candle").forGetter(b -> b.candleBlock),
        CandleCakeBlock::new
    ), CandleCakeBlock.class);
    public static final SpoutBlockType CANDLE = register("candle", BlockCodecs.simpleCodec(CandleBlock::new), CandleBlock.class);
    public static final SpoutBlockType CARPET = register("carpet", BlockCodecs.simpleCodec(CarpetBlock::new), CarpetBlock.class);
    public static final SpoutBlockType CARROT = register("carrot", BlockCodecs.simpleCodec(CarrotBlock::new), CarrotBlock.class);
    public static final SpoutBlockType CARTOGRAPHY_TABLE = register("cartography_table", BlockCodecs.simpleCodec(CartographyTableBlock::new), CartographyTableBlock.class);
    public static final SpoutBlockType CAULDRON = register("cauldron", BlockCodecs.simpleCodec(CauldronBlock::new), CauldronBlock.class);
    public static final SpoutBlockType CAVE_VINES = register("cave_vines", BlockCodecs.simpleCodec(CaveVinesBlock::new), CaveVinesBlock.class);
    public static final SpoutBlockType CAVE_VINES_PLANT = register("cave_vines_plant", BlockCodecs.simpleCodec(CaveVinesPlantBlock::new), CaveVinesPlantBlock.class);
    public static final SpoutBlockType CEILING_HANGING_SIGN = register("ceiling_hanging_sign", BlockCodecs.signCodec(CeilingHangingSignBlock::new), CeilingHangingSignBlock.class);
    public static final SpoutBlockType CHAIN = register("chain", BlockCodecs.simpleCodec(ChainBlock::new), ChainBlock.class);
    public static final SpoutBlockType CHEST = register("chest", BlockCodecs.simpleCodec(
        BlockCodecs.chestOpenSoundApp(),
        BlockCodecs.chestCloseSoundApp(),
        (openSound, closeSound, p) -> new ChestBlock(() -> BlockEntityTypes.CHEST, openSound, closeSound, p)
    ), ChestBlock.class);
    public static final SpoutBlockType CHISELED_BOOK_SHELF = register("chiseled_book_shelf", BlockCodecs.simpleCodec(ChiseledBookShelfBlock::new), ChiseledBookShelfBlock.class);
    public static final SpoutBlockType CHORUS_FLOWER = register("chorus_flower", BlockCodecs.simpleCodec(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("plant").forGetter(b -> b.plant),
        ChorusFlowerBlock::new
    ), ChorusFlowerBlock.class);
    public static final SpoutBlockType CHORUS_PLANT = register("chorus_plant", BlockCodecs.simpleCodec(ChorusPlantBlock::new), ChorusPlantBlock.class);
    public static final SpoutBlockType COCOA = register("cocoa", BlockCodecs.simpleCodec(CocoaBlock::new), CocoaBlock.class);
    public static final SpoutBlockType COLORED_FALLING = register("colored_falling", BlockCodecs.coloredFallingCodec(ColoredFallingBlock::new), ColoredFallingBlock.class);
    public static final SpoutBlockType COMMAND = register("command", BlockCodecs.simpleCodec(
        Codec.BOOL.fieldOf("automatic").forGetter(b -> b.automatic),
        CommandBlock::new
    ), CommandBlock.class);
    public static final SpoutBlockType COMPARATOR = register("comparator", BlockCodecs.simpleCodec(ComparatorBlock::new), ComparatorBlock.class);
    public static final SpoutBlockType COMPOSTER = register("composter", BlockCodecs.simpleCodec(ComposterBlock::new), ComposterBlock.class);
    public static final SpoutBlockType CONCRETE_POWDER = register("concrete_powder", BlockCodecs.simpleCodec(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("concrete").forGetter(b -> b.concrete),
        ConcretePowderBlock::new
    ), ConcretePowderBlock.class);
    public static final SpoutBlockType CONDUIT = register("conduit", BlockCodecs.simpleCodec(ConduitBlock::new), ConduitBlock.class);
    public static final SpoutBlockType COPPER_BULB = register("copper_bulb", BlockCodecs.simpleCodec(CopperBulbBlock::new), CopperBulbBlock.class);
    public static final SpoutBlockType COPPER_CHEST = register("copper_chest", BlockCodecs.simpleCodec(
        BlockCodecs.weatherStateApp(CopperChestBlock::getState),
        BlockCodecs.chestOpenSoundApp(),
        BlockCodecs.chestCloseSoundApp(),
        CopperChestBlock::new
    ), CopperChestBlock.class);
    public static final SpoutBlockType COPPER_GOLEM_STATUE = register("copper_golem_statue", BlockCodecs.simpleCodec(
        BlockCodecs.weatherStateApp(CopperGolemStatueBlock::getWeatheringState),
        CopperGolemStatueBlock::new
    ), CopperGolemStatueBlock.class);
    public static final SpoutBlockType CORAL = register("coral", BlockCodecs.simpleCodec(
        BlockCodecs.coralDeadApp(b -> b.deadBlock),
        CoralBlock::new
    ), CoralBlock.class);
    public static final SpoutBlockType CORAL_FAN = register("coral_fan", BlockCodecs.simpleCodec(
        BlockCodecs.coralDeadApp(b -> b.deadBlock),
        CoralFanBlock::new
    ), CoralFanBlock.class);
    public static final SpoutBlockType CORAL_PLANT = register("coral_plant", BlockCodecs.simpleCodec(
        BlockCodecs.coralDeadApp(b -> b.deadBlock),
        CoralPlantBlock::new
    ), CoralPlantBlock.class);
    public static final SpoutBlockType CORAL_WALL_FAN = register("coral_wall_fan", BlockCodecs.simpleCodec(
        BlockCodecs.coralDeadApp(b -> b.deadBlock),
        CoralWallFanBlock::new
    ), CoralWallFanBlock.class);
    public static final SpoutBlockType CRAFTER = register("crafter", BlockCodecs.simpleCodec(CrafterBlock::new), CrafterBlock.class);
    public static final SpoutBlockType CRAFTING_TABLE = register("crafting_table", BlockCodecs.simpleCodec(CraftingTableBlock::new), CraftingTableBlock.class);
    public static final SpoutBlockType CREAKING_HEART = register("creaking_heart", BlockCodecs.simpleCodec(CreakingHeartBlock::new), CreakingHeartBlock.class);
    public static final SpoutBlockType CROP = register("crop", BlockCodecs.simpleCodec(CropBlock::new), CropBlock.class);
    public static final SpoutBlockType CRYING_OBSIDIAN = register("crying_obsidian", BlockCodecs.simpleCodec(CryingObsidianBlock::new), CryingObsidianBlock.class);
    public static final SpoutBlockType DAYLIGHT_DETECTOR = register("daylight_detector", BlockCodecs.simpleCodec(DaylightDetectorBlock::new), DaylightDetectorBlock.class);
    public static final SpoutBlockType DRY_VEGETATION = register("dry_vegetation", BlockCodecs.simpleCodec(DryVegetationBlock::new), DryVegetationBlock.class);
    public static final SpoutBlockType DECORATED_POT = register("decorated_pot", BlockCodecs.simpleCodec(DecoratedPotBlock::new), DecoratedPotBlock.class);
    public static final SpoutBlockType DETECTOR_RAIL = register("detector_rail", BlockCodecs.simpleCodec(DetectorRailBlock::new), DetectorRailBlock.class);
    public static final SpoutBlockType DISPENSER = register("dispenser", BlockCodecs.simpleCodec(DispenserBlock::new), DispenserBlock.class);
    public static final SpoutBlockType DOOR = register("door", BlockCodecs.simpleCodec(
        BlockCodecs.doorBlockSetTypeApp(),
        DoorBlock::new
    ), DoorBlock.class);
    public static final SpoutBlockType DOUBLE_PLANT = register("double_plant", BlockCodecs.simpleCodec(DoublePlantBlock::new), DoublePlantBlock.class);
    public static final SpoutBlockType DRAGON_EGG = register("dragon_egg", BlockCodecs.simpleCodec(DragonEggBlock::new), DragonEggBlock.class);
    public static final SpoutBlockType DRIED_GHAST = register("dried_ghast", BlockCodecs.simpleCodec(DriedGhastBlock::new), DriedGhastBlock.class);
    public static final SpoutBlockType DROP_EXPERIENCE = register("drop_experience", BlockCodecs.simpleCodec(
        IntProviders.codec(0, 10).fieldOf("experience").forGetter(b -> b.xpRange),
        DropExperienceBlock::new
    ), DropExperienceBlock.class);
    public static final SpoutBlockType DROPPER = register("dropper", BlockCodecs.simpleCodec(DropperBlock::new), DropperBlock.class);
    public static final SpoutBlockType ENCHANTMENT_TABLE = register("enchantment_table", BlockCodecs.simpleCodec(EnchantingTableBlock::new), EnchantingTableBlock.class);
    public static final SpoutBlockType ENDER_CHEST = register("ender_chest", BlockCodecs.simpleCodec(EnderChestBlock::new), EnderChestBlock.class);
    public static final SpoutBlockType END_GATEWAY = register("end_gateway", BlockCodecs.simpleCodec(EndGatewayBlock::new), EndGatewayBlock.class);
    public static final SpoutBlockType END_PORTAL = register("end_portal", BlockCodecs.simpleCodec(EndPortalBlock::new), EndPortalBlock.class);
    public static final SpoutBlockType END_PORTAL_FRAME = register("end_portal_frame", BlockCodecs.simpleCodec(EndPortalFrameBlock::new), EndPortalFrameBlock.class);
    public static final SpoutBlockType END_ROD = register("end_rod", BlockCodecs.simpleCodec(EndRodBlock::new), EndRodBlock.class);
    public static final SpoutBlockType EYEBLOSSOM = register("eyeblossom", BlockCodecs.simpleCodec(
        Codec.BOOL.fieldOf("open").forGetter(e -> e.type.open),
        EyeblossomBlock::new
    ), EyeblossomBlock.class);
    public static final SpoutBlockType FARMLAND = register("farmland", BlockCodecs.simpleCodec(
        BlockCodecs.baseBlockApp(b -> b.baseBlock),
        FarmlandBlock::new
    ), FarmlandBlock.class);
    public static final SpoutBlockType FENCE = register("fence", BlockCodecs.simpleCodec(FenceBlock::new), FenceBlock.class);
    public static final SpoutBlockType FENCE_GATE = register("fence_gate", BlockCodecs.simpleCodec(
        BlockCodecs.woodTypeApp(b -> b.type),
        FenceGateBlock::new
    ), FenceGateBlock.class);
    public static final SpoutBlockType FIRE = register("fire", BlockCodecs.simpleCodec(FireBlock::new), FireBlock.class);
    public static final SpoutBlockType FIREFLY_BUSH = register("firefly_bush", BlockCodecs.simpleCodec(FireflyBushBlock::new), FireflyBushBlock.class);
    public static final SpoutBlockType FLOWER = register("flower", BlockCodecs.flowerCodec(FlowerBlock::new), FlowerBlock.class);
    public static final SpoutBlockType FLOWER_BED = register("flower_bed", BlockCodecs.simpleCodec(
        Codec.intRange(1, 16).fieldOf("shape_height").forGetter(b -> b.shapeHeight),
        (shapeHeight, properties) -> new FlowerBedBlock(properties, shapeHeight)
    ), FlowerBedBlock.class);
    public static final SpoutBlockType FLOWER_POT = register("flower_pot", BlockCodecs.simpleCodec(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("potted").forGetter(b -> b.getPotted()),
        FlowerPotBlock::new
    ), FlowerPotBlock.class);
    public static final SpoutBlockType FROGSPAWN = register("frogspawn", BlockCodecs.simpleCodec(FrogspawnBlock::new), FrogspawnBlock.class);
    public static final SpoutBlockType FROSTED_ICE = register("frosted_ice", BlockCodecs.simpleCodec(FrostedIceBlock::new), FrostedIceBlock.class);
    public static final SpoutBlockType FURNACE = register("furnace", BlockCodecs.simpleCodec(FurnaceBlock::new), FurnaceBlock.class);
    public static final SpoutBlockType GLAZED_TERRACOTTA = register("glazed_terracotta", BlockCodecs.simpleCodec(GlazedTerracottaBlock::new), GlazedTerracottaBlock.class);
    public static final SpoutBlockType GLOW_LICHEN = register("glow_lichen", BlockCodecs.simpleCodec(GlowLichenBlock::new), GlowLichenBlock.class);
    public static final SpoutBlockType GRASS = register("grass", BlockCodecs.simpleCodec(GrassBlock::new), GrassBlock.class);
    public static final SpoutBlockType GRINDSTONE = register("grindstone", BlockCodecs.simpleCodec(GrindstoneBlock::new), GrindstoneBlock.class);
    public static final SpoutBlockType HALF_TRANSPARENT = register("half_transparent", BlockCodecs.simpleCodec(HalfTransparentBlock::new), HalfTransparentBlock.class);
    public static final SpoutBlockType HANGING_MOSS = register("hanging_moss", BlockCodecs.simpleCodec(HangingMossBlock::new), HangingMossBlock.class);
    public static final SpoutBlockType HANGING_ROOTS = register("hanging_roots", BlockCodecs.simpleCodec(HangingRootsBlock::new), HangingRootsBlock.class);
    public static final SpoutBlockType HAY = register("hay", BlockCodecs.simpleCodec(HayBlock::new), HayBlock.class);
    public static final SpoutBlockType HEAVY_CORE = register("heavy_core", BlockCodecs.simpleCodec(HeavyCoreBlock::new), HeavyCoreBlock.class);
    public static final SpoutBlockType HONEY = register("honey", BlockCodecs.simpleCodec(HoneyBlock::new), HoneyBlock.class);
    public static final SpoutBlockType HOPPER = register("hopper", BlockCodecs.simpleCodec(HopperBlock::new), HopperBlock.class);
    public static final SpoutBlockType HUGE_MUSHROOM = register("huge_mushroom", BlockCodecs.simpleCodec(HugeMushroomBlock::new), HugeMushroomBlock.class);
    public static final SpoutBlockType ICE = register("ice", BlockCodecs.simpleCodec(IceBlock::new), IceBlock.class);
    public static final SpoutBlockType INFESTED = register("infested", BlockCodecs.infestedCodec(InfestedBlock::new), InfestedBlock.class);
    public static final SpoutBlockType INFESTED_ROTATED_PILLAR = register("infested_rotated_pillar", BlockCodecs.infestedCodec(InfestedRotatedPillarBlock::new), InfestedRotatedPillarBlock.class);
    public static final SpoutBlockType IRON_BARS = register("iron_bars", BlockCodecs.simpleCodec(IronBarsBlock::new), IronBarsBlock.class);
    public static final SpoutBlockType JACK_O_LANTERN = register("jack_o_lantern", BlockCodecs.simpleCodec(CarvedPumpkinBlock::new), CarvedPumpkinBlock.class);
    public static final SpoutBlockType JIGSAW = register("jigsaw", BlockCodecs.simpleCodec(JigsawBlock::new), JigsawBlock.class);
    public static final SpoutBlockType JUKEBOX = register("jukebox", BlockCodecs.simpleCodec(JukeboxBlock::new), JukeboxBlock.class);
    public static final SpoutBlockType KELP = register("kelp", BlockCodecs.simpleCodec(KelpBlock::new), KelpBlock.class);
    public static final SpoutBlockType KELP_PLANT = register("kelp_plant", BlockCodecs.simpleCodec(KelpPlantBlock::new), KelpPlantBlock.class);
    public static final SpoutBlockType LADDER = register("ladder", BlockCodecs.simpleCodec(LadderBlock::new), LadderBlock.class);
    public static final SpoutBlockType LANTERN = register("lantern", BlockCodecs.simpleCodec(LanternBlock::new), LanternBlock.class);
    public static final SpoutBlockType LAVA_CAULDRON = register("lava_cauldron", BlockCodecs.simpleCodec(LavaCauldronBlock::new), LavaCauldronBlock.class);
    public static final SpoutBlockType LAYERED_CAULDRON = register("layered_cauldron", BlockCodecs.simpleCodec(
        Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(b -> b.precipitationType),
        CauldronInteractions.CODEC.fieldOf("interactions").forGetter(b -> b.interactions),
        LayeredCauldronBlock::new
    ), LayeredCauldronBlock.class);
    public static final SpoutBlockType LEAF_LITTER = register("leaf_litter", BlockCodecs.simpleCodec(LeafLitterBlock::new), LeafLitterBlock.class);
    public static final SpoutBlockType LECTERN = register("lectern", BlockCodecs.simpleCodec(LecternBlock::new), LecternBlock.class);
    public static final SpoutBlockType LEVER = register("lever", BlockCodecs.simpleCodec(LeverBlock::new), LeverBlock.class);
    public static final SpoutBlockType LIGHT = register("light", BlockCodecs.simpleCodec(LightBlock::new), LightBlock.class);
    public static final SpoutBlockType LIGHTNING_ROD = register("lightning_rod", BlockCodecs.simpleCodec(LightningRodBlock::new), LightningRodBlock.class);
    public static final SpoutBlockType LIQUID = register("liquid", BlockCodecs.simpleCodec(
        BuiltInRegistries.FLUID.byNameCodec()
            .comapFlatMap(
                fluid -> fluid instanceof FlowingFluid flowing ? DataResult.success(flowing) : DataResult.error(() -> "Not a flowing fluid: " + fluid),
                Function.identity()
            ).fieldOf("fluid").forGetter(b -> ((LiquidBlockFluidDecorator) b).spout$getFluid()),
        LiquidBlock::new
    ), LiquidBlock.class);
    public static final SpoutBlockType LOOM = register("loom", BlockCodecs.simpleCodec(LoomBlock::new), LoomBlock.class);
    public static final SpoutBlockType MAGMA = register("magma", BlockCodecs.simpleCodec(MagmaBlock::new), MagmaBlock.class);
    public static final SpoutBlockType MANGROVE_LEAVES = register("mangrove_leaves", BlockCodecs.tintedParticleLeavesCodec(MangroveLeavesBlock::new), MangroveLeavesBlock.class);
    public static final SpoutBlockType MANGROVE_PROPAGULE = register("mangrove_propagule", BlockCodecs.saplingCodec(MangrovePropaguleBlock::new), MangrovePropaguleBlock.class);
    public static final SpoutBlockType MANGROVE_ROOTS = register("mangrove_roots", BlockCodecs.simpleCodec(MangroveRootsBlock::new), MangroveRootsBlock.class);
    public static final SpoutBlockType MOSSY_CARPET = register("mossy_carpet", BlockCodecs.simpleCodec(MossyCarpetBlock::new), MossyCarpetBlock.class);
    public static final SpoutBlockType MOVING_PISTON = register("moving_piston", BlockCodecs.simpleCodec(MovingPistonBlock::new), MovingPistonBlock.class);
    public static final SpoutBlockType MUD = register("mud", BlockCodecs.simpleCodec(MudBlock::new), MudBlock.class);
    public static final SpoutBlockType MULTIFACE = register("multiface", BlockCodecs.simpleCodec(MultifaceBlock::new), MultifaceBlock.class);
    public static final SpoutBlockType MUSHROOM = register("mushroom", BlockCodecs.simpleCodec(
        BlockCodecs.featureApp(b -> b.feature),
        MushroomBlock::new
    ), MushroomBlock.class);
    public static final SpoutBlockType MYCELIUM = register("mycelium", BlockCodecs.simpleCodec(MyceliumBlock::new), MyceliumBlock.class);
    public static final SpoutBlockType NETHER_FUNGUS = register("nether_fungus", BlockCodecs.simpleCodec(
        BlockCodecs.featureApp(b -> b.feature),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grows_on").forGetter(b -> b.requiredBlock),
        BlockCodecs.supportBlocksApp(b -> b.supportBlocks),
        NetherFungusBlock::new
    ), NetherFungusBlock.class);
    public static final SpoutBlockType NETHER_PORTAL = register("nether_portal", BlockCodecs.simpleCodec(NetherPortalBlock::new), NetherPortalBlock.class);
    public static final SpoutBlockType NETHERRACK = register("netherrack", BlockCodecs.simpleCodec(NetherrackBlock::new), NetherrackBlock.class);
    public static final SpoutBlockType NETHER_ROOTS = register("nether_roots", BlockCodecs.simpleCodec(
        BlockCodecs.supportBlocksApp(b -> b.supportBlocks),
        NetherRootsBlock::new
    ), NetherRootsBlock.class);
    public static final SpoutBlockType NETHER_SPROUTS = register("nether_sprouts", BlockCodecs.simpleCodec(NetherSproutsBlock::new), NetherSproutsBlock.class);
    public static final SpoutBlockType NETHER_WART = register("nether_wart", BlockCodecs.simpleCodec(NetherWartBlock::new), NetherWartBlock.class);
    public static final SpoutBlockType NOTE = register("note", BlockCodecs.simpleCodec(NoteBlock::new), NoteBlock.class);
    public static final SpoutBlockType NYLIUM = register("nylium", BlockCodecs.simpleCodec(NyliumBlock::new), NyliumBlock.class);
    public static final SpoutBlockType OBSERVER = register("observer", BlockCodecs.simpleCodec(ObserverBlock::new), ObserverBlock.class);
    public static final SpoutBlockType PATH = register("path", BlockCodecs.simpleCodec(
        BlockCodecs.baseBlockApp(b -> b.baseBlock),
        PathBlock::new
    ), PathBlock.class);
    public static final SpoutBlockType PIGLIN_WALL_SKULL = register("piglin_wall_skull", BlockCodecs.simpleCodec(PiglinWallSkullBlock::new), PiglinWallSkullBlock.class);
    public static final SpoutBlockType PISTON_BASE = register("piston_base", BlockCodecs.simpleCodec(
        Codec.BOOL.fieldOf("sticky").forGetter(b -> b.isSticky),
        PistonBaseBlock::new
    ), PistonBaseBlock.class);
    public static final SpoutBlockType PISTON_HEAD = register("piston_head", BlockCodecs.simpleCodec(PistonHeadBlock::new), PistonHeadBlock.class);
    public static final SpoutBlockType PITCHER_CROP = register("pitcher_crop", BlockCodecs.simpleCodec(PitcherCropBlock::new), PitcherCropBlock.class);
    public static final SpoutBlockType PLAYER_HEAD = register("player_head", BlockCodecs.simpleCodec(PlayerHeadBlock::new), PlayerHeadBlock.class);
    public static final SpoutBlockType PLAYER_WALL_HEAD = register("player_wall_head", BlockCodecs.simpleCodec(PlayerWallHeadBlock::new), PlayerWallHeadBlock.class);
    public static final SpoutBlockType POINTED_DRIPSTONE = register("pointed_dripstone", BlockCodecs.speleothemCodec(PointedDripstoneBlock::new), PointedDripstoneBlock.class);
    public static final SpoutBlockType POTATO = register("potato", BlockCodecs.simpleCodec(PotatoBlock::new), PotatoBlock.class);
    public static final SpoutBlockType POWDER_SNOW = register("powder_snow", BlockCodecs.simpleCodec(PowderSnowBlock::new), PowderSnowBlock.class);
    public static final SpoutBlockType POWERED = register("powered", BlockCodecs.simpleCodec(PoweredBlock::new), PoweredBlock.class);
    public static final SpoutBlockType POWERED_RAIL = register("powered_rail", BlockCodecs.simpleCodec(PoweredRailBlock::new), PoweredRailBlock.class);
    public static final SpoutBlockType POTENT_SULFUR = register("potent_sulfur", BlockCodecs.simpleCodec(PotentSulfurBlock::new), PotentSulfurBlock.class);
    public static final SpoutBlockType PRESSURE_PLATE = register("pressure_plate", BlockCodecs.simpleCodec(
        BlockCodecs.blockSetTypeApp(b -> b.type),
        PressurePlateBlock::new
    ), PressurePlateBlock.class);
    public static final SpoutBlockType PUMPKIN = register("pumpkin", BlockCodecs.simpleCodec(PumpkinBlock::new), PumpkinBlock.class);
    public static final SpoutBlockType RAIL = register("rail", BlockCodecs.simpleCodec(RailBlock::new), RailBlock.class);
    public static final SpoutBlockType REDSTONE_LAMP = register("redstone_lamp", BlockCodecs.simpleCodec(RedstoneLampBlock::new), RedstoneLampBlock.class);
    public static final SpoutBlockType REDSTONE_ORE = register("redstone_ore", BlockCodecs.simpleCodec(RedStoneOreBlock::new), RedStoneOreBlock.class);
    public static final SpoutBlockType REDSTONE_TORCH = register("redstone_torch", BlockCodecs.simpleCodec(RedstoneTorchBlock::new), RedstoneTorchBlock.class);
    public static final SpoutBlockType REDSTONE_WALL_TORCH = register("redstone_wall_torch", BlockCodecs.simpleCodec(RedstoneWallTorchBlock::new), RedstoneWallTorchBlock.class);
    public static final SpoutBlockType REDSTONE_WIRE = register("redstone_wire", BlockCodecs.simpleCodec(RedstoneWireBlock::new), RedstoneWireBlock.class);
    public static final SpoutBlockType REPEATER = register("repeater", BlockCodecs.simpleCodec(RepeaterBlock::new), RepeaterBlock.class);
    public static final SpoutBlockType RESPAWN_ANCHOR = register("respawn_anchor", BlockCodecs.simpleCodec(RespawnAnchorBlock::new), RespawnAnchorBlock.class);
    public static final SpoutBlockType ROOTED_DIRT = register("rooted_dirt", BlockCodecs.simpleCodec(RootedDirtBlock::new), RootedDirtBlock.class);
    public static final SpoutBlockType ROTATED_PILLAR = register("rotated_pillar", BlockCodecs.simpleCodec(RotatedPillarBlock::new), RotatedPillarBlock.class);
    public static final SpoutBlockType SAND = register("sand", BlockCodecs.coloredFallingCodec(SandBlock::new), SandBlock.class);
    public static final SpoutBlockType SAPLING = register("sapling", BlockCodecs.saplingCodec(SaplingBlock::new), SaplingBlock.class);
    public static final SpoutBlockType SCAFFOLDING = register("scaffolding", BlockCodecs.simpleCodec(ScaffoldingBlock::new), ScaffoldingBlock.class);
    public static final SpoutBlockType SCULK = register("sculk", BlockCodecs.simpleCodec(SculkBlock::new), SculkBlock.class);
    public static final SpoutBlockType SCULK_CATALYST = register("sculk_catalyst", BlockCodecs.simpleCodec(SculkCatalystBlock::new), SculkCatalystBlock.class);
    public static final SpoutBlockType SCULK_SENSOR = register("sculk_sensor", BlockCodecs.simpleCodec(SculkSensorBlock::new), SculkSensorBlock.class);
    public static final SpoutBlockType SCULK_SHRIEKER = register("sculk_shrieker", BlockCodecs.simpleCodec(SculkShriekerBlock::new), SculkShriekerBlock.class);
    public static final SpoutBlockType SCULK_VEIN = register("sculk_vein", BlockCodecs.simpleCodec(SculkVeinBlock::new), SculkVeinBlock.class);
    public static final SpoutBlockType SEAGRASS = register("seagrass", BlockCodecs.simpleCodec(SeagrassBlock::new), SeagrassBlock.class);
    public static final SpoutBlockType SEA_PICKLE = register("sea_pickle", BlockCodecs.simpleCodec(SeaPickleBlock::new), SeaPickleBlock.class);
    public static final SpoutBlockType SHELF = register("shelf", BlockCodecs.simpleCodec(ShelfBlock::new), ShelfBlock.class);
    public static final SpoutBlockType SHELF_MUSHROOM = register("shelf_mushroom", BlockCodecs.simpleCodec(ShelfMushroomBlock::new), ShelfMushroomBlock.class);
    public static final SpoutBlockType SHORT_DRY_GRASS = register("short_dry_grass", BlockCodecs.simpleCodec(ShortDryGrassBlock::new), ShortDryGrassBlock.class);
    public static final SpoutBlockType SHULKER_BOX = register("shulker_box", BlockCodecs.simpleCodec(
        DyeColor.CODEC.optionalFieldOf("color").forGetter(b -> Optional.ofNullable(b.color)),
        (color, properties) -> new ShulkerBoxBlock(color.orElse(null), properties)
    ), ShulkerBoxBlock.class);
    public static final SpoutBlockType SKULL = register("skull", BlockCodecs.skullCodec(SkullBlock::new), SkullBlock.class);
    public static final SpoutBlockType SLAB = register("slab", BlockCodecs.simpleCodec(SlabBlock::new), SlabBlock.class);
    public static final SpoutBlockType SLIME = register("slime", BlockCodecs.simpleCodec(SlimeBlock::new), SlimeBlock.class);
    public static final SpoutBlockType SMALL_DRIPLEAF = register("small_dripleaf", BlockCodecs.simpleCodec(SmallDripleafBlock::new), SmallDripleafBlock.class);
    public static final SpoutBlockType SMITHING_TABLE = register("smithing_table", BlockCodecs.simpleCodec(SmithingTableBlock::new), SmithingTableBlock.class);
    public static final SpoutBlockType SMOKER = register("smoker", BlockCodecs.simpleCodec(SmokerBlock::new), SmokerBlock.class);
    public static final SpoutBlockType SNIFFER_EGG = register("sniffer_egg", BlockCodecs.simpleCodec(SnifferEggBlock::new), SnifferEggBlock.class);
    public static final SpoutBlockType SNOWY_DIRT = register("snowy_dirt", BlockCodecs.simpleCodec(SnowyBlock::new), SnowyBlock.class);
    public static final SpoutBlockType SNOW_LAYER = register("snow_layer", BlockCodecs.simpleCodec(SnowLayerBlock::new), SnowLayerBlock.class);
    public static final SpoutBlockType SOUL_FIRE = register("soul_fire", BlockCodecs.simpleCodec(SoulFireBlock::new), SoulFireBlock.class);
    public static final SpoutBlockType SOUL_SAND = register("soul_sand", BlockCodecs.simpleCodec(SoulSandBlock::new), SoulSandBlock.class);
    public static final SpoutBlockType SPAWNER = register("spawner", BlockCodecs.simpleCodec(SpawnerBlock::new), SpawnerBlock.class);
    public static final SpoutBlockType SPONGE = register("sponge", BlockCodecs.simpleCodec(SpongeBlock::new), SpongeBlock.class);
    public static final SpoutBlockType SPORE_BLOSSOM = register("spore_blossom", BlockCodecs.simpleCodec(SporeBlossomBlock::new), SporeBlossomBlock.class);
    public static final SpoutBlockType STAINED_GLASS = register("stained_glass", BlockCodecs.simpleCodec(
        BlockCodecs.dyeColorApp(StainedGlassBlock::getColor),
        StainedGlassBlock::new
    ), StainedGlassBlock.class);
    public static final SpoutBlockType STAINED_GLASS_PANE = register("stained_glass_pane", BlockCodecs.simpleCodec(
        BlockCodecs.dyeColorApp(StainedGlassPaneBlock::getColor),
        StainedGlassPaneBlock::new
    ), StainedGlassPaneBlock.class);
    public static final SpoutBlockType STAIR = register("stair", BlockCodecs.stairCodec(StairBlock::new), StairBlock.class);
    public static final SpoutBlockType STANDING_SIGN = register("standing_sign", BlockCodecs.signCodec(StandingSignBlock::new), StandingSignBlock.class);
    public static final SpoutBlockType STEM = register("stem", BlockCodecs.simpleCodec(
        BlockCodecs.stemFruitApp(b -> b.fruit),
        ResourceKey.codec(Registries.BLOCK).fieldOf("attached_stem").forGetter(b -> b.attachedStem),
        BlockCodecs.stemSeedApp(b -> b.seed),
        TagKey.codec(Registries.BLOCK).fieldOf("stem_support_blocks").forGetter(b -> b.stemSupportBlocks),
        TagKey.codec(Registries.BLOCK).fieldOf("fruit_support_blocks").forGetter(b -> b.fruitSupportBlocks),
        StemBlock::new
    ), StemBlock.class);
    public static final SpoutBlockType STONECUTTER = register("stonecutter", BlockCodecs.simpleCodec(StonecutterBlock::new), StonecutterBlock.class);
    public static final SpoutBlockType STRAW_BED = register("straw_bed", BlockCodecs.simpleCodec(StrawBedBlock::new), StrawBedBlock.class);
    public static final SpoutBlockType STRUCTURE = register("structure", BlockCodecs.simpleCodec(StructureBlock::new), StructureBlock.class);
    public static final SpoutBlockType STRUCTURE_VOID = register("structure_void", BlockCodecs.simpleCodec(StructureVoidBlock::new), StructureVoidBlock.class);
    public static final SpoutBlockType SUGAR_CANE = register("sugar_cane", BlockCodecs.simpleCodec(SugarCaneBlock::new), SugarCaneBlock.class);
    public static final SpoutBlockType SULFUR_SPIKE = register("sulfur_spike", BlockCodecs.speleothemCodec(SulfurSpikeBlock::new), SulfurSpikeBlock.class);
    public static final SpoutBlockType SWEET_BERRY_BUSH = register("sweet_berry_bush", BlockCodecs.simpleCodec(SweetBerryBushBlock::new), SweetBerryBushBlock.class);
    public static final SpoutBlockType TALL_DRY_GRASS = register("tall_dry_grass", BlockCodecs.simpleCodec(TallDryGrassBlock::new), TallDryGrassBlock.class);
    public static final SpoutBlockType TALL_FLOWER = register("tall_flower", BlockCodecs.simpleCodec(TallFlowerBlock::new), TallFlowerBlock.class);
    public static final SpoutBlockType TALL_GRASS = register("tall_grass", BlockCodecs.simpleCodec(TallGrassBlock::new), TallGrassBlock.class);
    public static final SpoutBlockType TALL_SEAGRASS = register("tall_seagrass", BlockCodecs.simpleCodec(TallSeagrassBlock::new), TallSeagrassBlock.class);
    public static final SpoutBlockType TARGET = register("target", BlockCodecs.simpleCodec(TargetBlock::new), TargetBlock.class);
    public static final SpoutBlockType TEST = register("test", BlockCodecs.simpleCodec(TestBlock::new), TestBlock.class);
    public static final SpoutBlockType TEST_INSTANCE = register("test_instance", BlockCodecs.simpleCodec(TestInstanceBlock::new), TestInstanceBlock.class);
    public static final SpoutBlockType TINTED_GLASS = register("tinted_glass", BlockCodecs.simpleCodec(TintedGlassBlock::new), TintedGlassBlock.class);
    public static final SpoutBlockType TINTED_PARTICLE_LEAVES = register("tinted_particle_leaves", BlockCodecs.tintedParticleLeavesCodec(TintedParticleLeavesBlock::new), TintedParticleLeavesBlock.class);
    public static final SpoutBlockType TNT = register("tnt", BlockCodecs.simpleCodec(TntBlock::new), TntBlock.class);
    public static final SpoutBlockType TORCHFLOWER_CROP = register("torchflower_crop", BlockCodecs.simpleCodec(TorchflowerCropBlock::new), TorchflowerCropBlock.class);
    public static final SpoutBlockType TORCH = register("torch", BlockCodecs.torchCodec(TorchBlock::new), TorchBlock.class);
    public static final SpoutBlockType TRANSPARENT = register("transparent", BlockCodecs.simpleCodec(TransparentBlock::new), TransparentBlock.class);
    public static final SpoutBlockType TRAPDOOR = register("trapdoor", BlockCodecs.simpleCodec(
        BlockCodecs.trapDoorBlockSetTypeApp(),
        TrapDoorBlock::new
    ), TrapDoorBlock.class);
    public static final SpoutBlockType TRAPPED_CHEST = register("trapped_chest", BlockCodecs.simpleCodec(TrappedChestBlock::new), TrappedChestBlock.class);
    public static final SpoutBlockType TRIAL_SPAWNER = register("trial_spawner", BlockCodecs.simpleCodec(TrialSpawnerBlock::new), TrialSpawnerBlock.class);
    public static final SpoutBlockType TRIPWIRE = register("tripwire", BlockCodecs.simpleCodec(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("hook").forGetter(b -> b.hook),
        TripWireBlock::new
    ), TripWireBlock.class);
    public static final SpoutBlockType TRIP_WIRE_HOOK = register("trip_wire_hook", BlockCodecs.simpleCodec(TripWireHookBlock::new), TripWireHookBlock.class);
    public static final SpoutBlockType TURTLE_EGG = register("turtle_egg", BlockCodecs.simpleCodec(TurtleEggBlock::new), TurtleEggBlock.class);
    public static final SpoutBlockType TWISTING_VINES = register("twisting_vines", BlockCodecs.simpleCodec(TwistingVinesBlock::new), TwistingVinesBlock.class);
    public static final SpoutBlockType TWISTING_VINES_PLANT = register("twisting_vines_plant", BlockCodecs.simpleCodec(TwistingVinesPlantBlock::new), TwistingVinesPlantBlock.class);
    public static final SpoutBlockType UNTINTED_PARTICLE_LEAVES = register("untinted_particle_leaves", BlockCodecs.simpleCodec(
        BlockCodecs.fallingParticlesLeavesLeafParticleChanceApp(),
        ParticleTypes.CODEC.fieldOf("leaf_particle").forGetter(e -> e.leafParticle),
        AmbientLeavesBlockSoundPlayer.CODEC.optionalFieldOf("ambient_leaves_block_sound_player", AmbientLeavesBlockSoundPlayer.noAmbientSound()).forGetter(b -> b.ambientLeavesBlockSoundPlayer),
        UntintedParticleLeavesBlock::new
    ), UntintedParticleLeavesBlock.class);
    public static final SpoutBlockType VAULT = register("vault", BlockCodecs.simpleCodec(VaultBlock::new), VaultBlock.class);
    public static final SpoutBlockType VINE = register("vine", BlockCodecs.simpleCodec(VineBlock::new), VineBlock.class);
    public static final SpoutBlockType WALL = register("wall", BlockCodecs.simpleCodec(WallBlock::new), WallBlock.class);
    public static final SpoutBlockType WALL_BANNER = register("wall_banner", BlockCodecs.bannerCodec(WallBannerBlock::new), WallBannerBlock.class);
    public static final SpoutBlockType WALL_HANGING_SIGN = register("wall_hanging_sign", BlockCodecs.signCodec(WallHangingSignBlock::new), WallHangingSignBlock.class);
    public static final SpoutBlockType WALL_SIGN = register("wall_sign", BlockCodecs.signCodec(WallSignBlock::new), WallSignBlock.class);
    public static final SpoutBlockType WALL_SKULL = register("wall_skull", BlockCodecs.skullCodec(WallSkullBlock::new), WallSkullBlock.class);
    public static final SpoutBlockType WALL_TORCH = register("wall_torch", BlockCodecs.torchCodec(WallTorchBlock::new), WallTorchBlock.class);
    public static final SpoutBlockType LILY_PAD = register("lily_pad", BlockCodecs.simpleCodec(LilyPadBlock::new), LilyPadBlock.class);
    public static final SpoutBlockType WATERLOGGED_TRANSPARENT = register("waterlogged_transparent", BlockCodecs.simpleCodec(WaterloggedTransparentBlock::new), WaterloggedTransparentBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_BAR = register("weathering_copper_bar", BlockCodecs.weatheringCopperCodec(WeatheringCopperBarsBlock::new), WeatheringCopperBarsBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_BULB = register("weathering_copper_bulb", BlockCodecs.weatheringCopperCodec(WeatheringCopperBulbBlock::new), WeatheringCopperBulbBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_CHAIN = register("weathering_copper_chain", BlockCodecs.weatheringCopperCodec(WeatheringCopperChainBlock::new), WeatheringCopperChainBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_CHEST = register("weathering_copper_chest", BlockCodecs.simpleCodec(
        BlockCodecs.weatheringCopperWeatheringStateApp(),
        BlockCodecs.chestOpenSoundApp(),
        BlockCodecs.chestCloseSoundApp(),
        WeatheringCopperChestBlock::new
    ), WeatheringCopperChestBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_DOOR = register("weathering_copper_door", BlockCodecs.simpleCodec(
        BlockCodecs.doorBlockSetTypeApp(),
        BlockCodecs.weatheringCopperWeatheringStateApp(),
        WeatheringCopperDoorBlock::new
    ), WeatheringCopperDoorBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_FULL = register("weathering_copper_full", BlockCodecs.weatheringCopperCodec(WeatheringCopperFullBlock::new), WeatheringCopperFullBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_GOLEM_STATUE = register("weathering_copper_golem_statue", BlockCodecs.weatheringCopperCodec(WeatheringCopperGolemStatueBlock::new), WeatheringCopperGolemStatueBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_GRATE = register("weathering_copper_grate", BlockCodecs.weatheringCopperCodec(WeatheringCopperGrateBlock::new), WeatheringCopperGrateBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_SLAB = register("weathering_copper_slab", BlockCodecs.weatheringCopperCodec(WeatheringCopperSlabBlock::new), WeatheringCopperSlabBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_STAIR = register("weathering_copper_stair", BlockCodecs.weatheringCopperStairCodec(WeatheringCopperStairBlock::new), WeatheringCopperStairBlock.class);
    public static final SpoutBlockType WEATHERING_COPPER_TRAPDOOR = register("weathering_copper_trapdoor", BlockCodecs.simpleCodec(
        BlockCodecs.trapDoorBlockSetTypeApp(),
        BlockCodecs.weatheringCopperWeatheringStateApp(),
        WeatheringCopperTrapDoorBlock::new
    ), WeatheringCopperTrapDoorBlock.class);
    public static final SpoutBlockType WEATHERING_LANTERN = register("weathering_lantern", BlockCodecs.weatheringCopperCodec(WeatheringLanternBlock::new), WeatheringLanternBlock.class);
    public static final SpoutBlockType WEATHERING_LIGHTNING_ROD = register("weathering_lightning_rod", BlockCodecs.weatheringCopperCodec(WeatheringLightningRodBlock::new), WeatheringLightningRodBlock.class);
    public static final SpoutBlockType WEB = register("web", BlockCodecs.simpleCodec(WebBlock::new), WebBlock.class);
    public static final SpoutBlockType WEEPING_VINES = register("weeping_vines", BlockCodecs.simpleCodec(WeepingVinesBlock::new), WeepingVinesBlock.class);
    public static final SpoutBlockType WEEPING_VINES_PLANT = register("weeping_vines_plant", BlockCodecs.simpleCodec(WeepingVinesPlantBlock::new), WeepingVinesPlantBlock.class);
    public static final SpoutBlockType WEIGHTED_PRESSURE_PLATE = register("weighted_pressure_plate", BlockCodecs.simpleCodec(
        Codec.intRange(1, 1024).fieldOf("max_weight").forGetter(b -> b.maxWeight),
        BlockCodecs.blockSetTypeApp(b -> b.type),
        WeightedPressurePlateBlock::new
    ), WeightedPressurePlateBlock.class);
    public static final SpoutBlockType WET_SPONGE = register("wet_sponge", BlockCodecs.simpleCodec(WetSpongeBlock::new), WetSpongeBlock.class);
    public static final SpoutBlockType WITHER_ROSE = register("wither_rose", BlockCodecs.flowerCodec(WitherRoseBlock::new), WitherRoseBlock.class);
    public static final SpoutBlockType WITHER_SKULL = register("wither_skull", BlockCodecs.simpleCodec(WitherSkullBlock::new), WitherSkullBlock.class);
    public static final SpoutBlockType WITHER_WALL_SKULL = register("wither_wall_skull", BlockCodecs.simpleCodec(WitherWallSkullBlock::new), WitherWallSkullBlock.class);
    public static final SpoutBlockType WOOL_CARPET = register("wool_carpet", BlockCodecs.simpleCodec(
        DyeColor.CODEC.fieldOf("color").forGetter(WoolCarpetBlock::getColor),
        WoolCarpetBlock::new
    ), WoolCarpetBlock.class);
    public static final SpoutBlockType HALF_TRANSPARENT_SLAB = registerSpout("half_transparent_slab", BlockCodecs.simpleCodec(HalfTransparentSlabBlock::new), HalfTransparentSlabBlock.class);
    public static final SpoutBlockType HALF_TRANSPARENT_STAIR = registerSpout("half_transparent_stair", BlockCodecs.stairCodec(HalfTransparentStairBlock::new), HalfTransparentStairBlock.class);
    public static final SpoutBlockType TRANSPARENT_SLAB = registerSpout("transparent_slab", BlockCodecs.simpleCodec(TransparentSlabBlock::new), TransparentSlabBlock.class);
    public static final SpoutBlockType TRANSPARENT_STAIR = registerSpout("transparent_stair", BlockCodecs.stairCodec(TransparentStairBlock::new), TransparentStairBlock.class);
    public static final SpoutBlockType QUAD = registerSpout("quad", BlockCodecs.simpleCodec(QuadBlock::new), QuadBlock.class);
    public static final SpoutBlockType VERTICAL_SLAB = registerSpout("vertical_slab", BlockCodecs.simpleCodec(VerticalSlabBlock::new), VerticalSlabBlock.class);

    private static SpoutBlockType register(String id, MapCodec<? extends Block> codec, Class<? extends Block> baseClass) {
        return register(Identifier.parse(id), codec, baseClass);
    }

    private static SpoutBlockType registerSpout(String path, MapCodec<? extends Block> codec, Class<? extends Block> baseClass) {
        return register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, path), codec, baseClass);
    }

    private static SpoutBlockType register(Identifier id, MapCodec<? extends Block> codec, Class<? extends Block> baseClass) {
        return register(id, new CodecSpoutBlockType(id, codec, baseClass));
    }

    private static SpoutBlockType register(Identifier id, SpoutBlockType blockType) {
        return Registry.register(BuiltInBlockTypeRegistry.BLOCK_TYPE, id, blockType);
    }

    public static SpoutBlockType bootstrap(Registry<SpoutBlockType> registry) {
        return VERTICAL_SLAB;
    }

}
