package spout.gamecontent.datadriven.block;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FallingParticlesLeavesBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SpeleothemBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.Feature;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Provides base methods to create codecs for blocks.
 */
public final class BlockCodecs {

    private BlockCodecs() {
        throw new UnsupportedOperationException();
    }

    public static <B extends Block> MapCodec<B> simpleCodec(
        Function<BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockPropertiesCodec.getBuilder()
        ).apply(instance, factory));
    }

    public static <B extends Block, T1> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        BiFunction<T1, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            BlockPropertiesCodec.getBuilder()
        ).apply(instance, factory));
    }

    public static <B extends Block, T1, T2> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        App<RecordCodecBuilder.Mu<B>, T2> t2,
        Function3<T1, T2, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            t2,
            BlockPropertiesCodec.getBuilder()
        ).apply(instance, factory));
    }

    public static <B extends Block, T1, T2, T3> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        App<RecordCodecBuilder.Mu<B>, T2> t2,
        App<RecordCodecBuilder.Mu<B>, T3> t3,
        Function4<T1, T2, T3, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            t2,
            t3,
            BlockPropertiesCodec.getBuilder()
        ).apply(instance, factory));
    }

    public static <B extends Block, T1, T2, T3, T4> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        App<RecordCodecBuilder.Mu<B>, T2> t2,
        App<RecordCodecBuilder.Mu<B>, T3> t3,
        App<RecordCodecBuilder.Mu<B>, T4> t4,
        Function5<T1, T2, T3, T4, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            t2,
            t3,
            t4,
            BlockPropertiesCodec.getBuilder()
        ).apply(instance, factory));
    }

    public static <B extends Block, T1, T2, T3, T4, T5> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        App<RecordCodecBuilder.Mu<B>, T2> t2,
        App<RecordCodecBuilder.Mu<B>, T3> t3,
        App<RecordCodecBuilder.Mu<B>, T4> t4,
        App<RecordCodecBuilder.Mu<B>, T5> t5,
        Function6<T1, T2, T3, T4, T5, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            t2,
            t3,
            t4,
            t5,
            BlockPropertiesCodec.getBuilder()
        ).apply(instance, factory));
    }

    public static MapCodec<Block> baseBlockMapCodec() {
        return BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, Block> baseBlockApp(Function<B, Block> getter) {
        return baseBlockMapCodec().forGetter(getter);
    }

    public static MapCodec<BlockSetType> blockSetTypeMapCodec() {
        return BlockSetType.CODEC.fieldOf("block_set_type");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, BlockSetType> blockSetTypeApp(Function<B, BlockSetType> getter) {
        return blockSetTypeMapCodec().forGetter(getter);
    }

    public static <B extends ChestBlock> App<RecordCodecBuilder.Mu<B>, SoundEvent> chestOpenSoundApp() {
        return BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("open_sound").forGetter(ChestBlock::getOpenChestSound);
    }

    public static <B extends ChestBlock> App<RecordCodecBuilder.Mu<B>, SoundEvent> chestCloseSoundApp() {
        return BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("close_sound").forGetter(ChestBlock::getOpenChestSound);
    }

    public static <B extends ColoredFallingBlock> App<RecordCodecBuilder.Mu<B>, ColorRGBA> coloredFallingDustColorApp() {
        return ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter(b -> b.dustColor);
    }

    public static MapCodec<Block> coralDeadMapCodec() {
        return BuiltInRegistries.BLOCK.byNameCodec().fieldOf("dead");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, Block> coralDeadApp(Function<B, Block> getter) {
        return coralDeadMapCodec().forGetter(getter);
    }

    public static <B extends DoorBlock> App<RecordCodecBuilder.Mu<B>, BlockSetType> doorBlockSetTypeApp() {
        return blockSetTypeApp(DoorBlock::type);
    }

    public static MapCodec<DyeColor> dyeColorMapCodec() {
        return DyeColor.CODEC.fieldOf("color");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, DyeColor> dyeColorApp(Function<B, DyeColor> getter) {
        return dyeColorMapCodec().forGetter(getter);
    }

    public static <B extends FallingParticlesLeavesBlock> App<RecordCodecBuilder.Mu<B>, Float> fallingParticlesLeavesLeafParticleChanceApp() {
        return ExtraCodecs.floatRange(0.0F, 1.0F).fieldOf("leaf_particle_chance").forGetter(e -> e.leafParticleChance);
    }

    public static MapCodec<ResourceKey<Feature>> featureMapCodec() {
        return ResourceKey.codec(Registries.FEATURE).fieldOf("feature");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, ResourceKey<Feature>> featureApp(Function<B, ResourceKey<Feature>> getter) {
        return featureMapCodec().forGetter(getter);
    }

    public static <B extends FlowerBlock> App<RecordCodecBuilder.Mu<B>, SuspiciousStewEffects> flowerSuspiciousStewEffectsApp() {
        return SuspiciousStewEffects.CODEC.fieldOf("suspicious_stew_effects").forGetter(FlowerBlock::getSuspiciousEffects);
    }

    public static <B extends InfestedBlock> App<RecordCodecBuilder.Mu<B>, Block> infestedHostBlockApp() {
        return BuiltInRegistries.BLOCK.byNameCodec().fieldOf("host").forGetter(InfestedBlock::getHostBlock);
    }

    public static <B extends SaplingBlock> App<RecordCodecBuilder.Mu<B>, TreeGrower> saplingTreeGrowerApp() {
        return TreeGrower.CODEC.fieldOf("tree").forGetter(b -> b.treeGrower);
    }

    public static <B extends AbstractSkullBlock> App<RecordCodecBuilder.Mu<B>, SkullBlock.Type> skullKindApp() {
        return SkullBlock.Type.CODEC.fieldOf("kind").forGetter(AbstractSkullBlock::getType);
    }

    public static <B extends SpeleothemBlock> App<RecordCodecBuilder.Mu<B>, BlockState> speleothemBlockToGrowOnApp() {
        return FormattedBlockStateCodec.CODEC.fieldOf("block_to_grow_on").forGetter(b -> b.blockToGrowOn);
    }

    public static <B extends StairBlock> App<RecordCodecBuilder.Mu<B>, BlockState> stairBaseStateApp() {
        return FormattedBlockStateCodec.CODEC.fieldOf("base_state").forGetter(b -> b.baseState);
    }

    public static MapCodec<ResourceKey<Block>> stemFruitMapCodec() {
        return ResourceKey.codec(Registries.BLOCK).fieldOf("fruit");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, ResourceKey<Block>> stemFruitApp(Function<B, ResourceKey<Block>> getter) {
        return stemFruitMapCodec().forGetter(getter);
    }

    public static MapCodec<ResourceKey<Item>> stemSeedMapCodec() {
        return ResourceKey.codec(Registries.ITEM).fieldOf("seed");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, ResourceKey<Item>> stemSeedApp(Function<B, ResourceKey<Item>> getter) {
        return stemSeedMapCodec().forGetter(getter);
    }

    public static MapCodec<TagKey<Block>> supportBlocksMapCodec() {
        return TagKey.codec(Registries.BLOCK).fieldOf("support_blocks");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, TagKey<Block>> supportBlocksApp(Function<B, TagKey<Block>> getter) {
        return supportBlocksMapCodec().forGetter(getter);
    }

    public static <B extends TorchBlock> App<RecordCodecBuilder.Mu<B>, SimpleParticleType> torchFlameParticleApp() {
        return BuiltInRegistries.PARTICLE_TYPE.byNameCodec()
            .comapFlatMap(
                type -> type instanceof SimpleParticleType simple ? DataResult.success(simple) : DataResult.error(() -> "Not a SimpleParticleType: " + type),
                Function.identity()
            )
            .fieldOf("particle_options").forGetter(b -> b.flameParticle);
    }

    public static <B extends TrapDoorBlock> App<RecordCodecBuilder.Mu<B>, BlockSetType> trapDoorBlockSetTypeApp() {
        return blockSetTypeApp(b -> b.type);
    }

    public static MapCodec<WeatheringCopper.WeatherState> weatherStateMapCodec() {
        return WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, WeatheringCopper.WeatherState> weatherStateApp(Function<B, WeatheringCopper.WeatherState> getter) {
        return weatherStateMapCodec().forGetter(getter);
    }

    public static <B extends Block & WeatheringCopper> App<RecordCodecBuilder.Mu<B>, WeatheringCopper.WeatherState> weatheringCopperWeatheringStateApp() {
        return weatherStateApp(WeatheringCopper::getAge);
    }

    public static MapCodec<WoodType> woodTypeMapCodec() {
        return WoodType.CODEC.fieldOf("wood_type");
    }

    public static <B extends Block> App<RecordCodecBuilder.Mu<B>, WoodType> woodTypeApp(Function<B, WoodType> getter) {
        return woodTypeMapCodec().forGetter(getter);
    }

    public static <B extends AbstractBannerBlock> MapCodec<B> bannerCodec(
        BiFunction<DyeColor, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            dyeColorApp(AbstractBannerBlock::getColor),
            factory
        );
    }

    public static <B extends ColoredFallingBlock> MapCodec<B> coloredFallingCodec(
        BiFunction<ColorRGBA, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            coloredFallingDustColorApp(),
            factory
        );
    }

    public static <B extends FlowerBlock> MapCodec<B> flowerCodec(
        BiFunction<SuspiciousStewEffects, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            flowerSuspiciousStewEffectsApp(),
            factory
        );
    }

    public static <B extends InfestedBlock> MapCodec<B> infestedCodec(
        BiFunction<Block, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            infestedHostBlockApp(),
            factory
        );
    }

    public static <B extends SaplingBlock> MapCodec<B> saplingCodec(
        BiFunction<TreeGrower, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            saplingTreeGrowerApp(),
            factory
        );
    }

    public static <B extends SignBlock> MapCodec<B> signCodec(
        BiFunction<WoodType, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            woodTypeApp(SignBlock::type),
            factory
        );
    }

    public static <B extends AbstractSkullBlock> MapCodec<B> skullCodec(
        BiFunction<SkullBlock.Type, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            skullKindApp(),
            factory
        );
    }

    public static <B extends SpeleothemBlock> MapCodec<B> speleothemCodec(
        BiFunction<BlockState, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            speleothemBlockToGrowOnApp(),
            factory
        );
    }

    public static <B extends StairBlock> MapCodec<B> stairCodec(
        BiFunction<BlockState, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            stairBaseStateApp(),
            factory
        );
    }

    public static <B extends TintedParticleLeavesBlock> MapCodec<B> tintedParticleLeavesCodec(
        BiFunction<Float, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            fallingParticlesLeavesLeafParticleChanceApp(),
            factory
        );
    }

    public static <B extends TorchBlock> MapCodec<B> torchCodec(
        BiFunction<SimpleParticleType, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            torchFlameParticleApp(),
            factory
        );
    }

    public static <B extends Block & WeatheringCopper> MapCodec<B> weatheringCopperCodec(
        BiFunction<WeatheringCopper.WeatherState, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            weatheringCopperWeatheringStateApp(),
            factory
        );
    }

    public static <B extends WeatheringCopperStairBlock> MapCodec<B> weatheringCopperStairCodec(
        Function3<WeatheringCopper.WeatherState, BlockState, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            weatheringCopperWeatheringStateApp(),
            stairBaseStateApp(),
            factory
        );
    }

}
