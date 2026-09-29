package spout.gamecontent.datadriven.block;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.BiFunction;

/**
 * Provides base methods to create codecs for blocks.
 */
public final class BlockCodecs {

    private BlockCodecs() {
        throw new UnsupportedOperationException();
    }

    private static <B extends Block, T1> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        BiFunction<T1, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            BlockBehaviour.propertiesCodec()
        ).apply(instance, factory));
    }

    private static <B extends Block, T1, T2> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        App<RecordCodecBuilder.Mu<B>, T2> t2,
        Function3<T1, T2, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            t2,
            BlockBehaviour.propertiesCodec()
        ).apply(instance, factory));
    }

    private static <B extends Block, T1, T2, T3> MapCodec<B> simpleCodec(
        App<RecordCodecBuilder.Mu<B>, T1> t1,
        App<RecordCodecBuilder.Mu<B>, T2> t2,
        App<RecordCodecBuilder.Mu<B>, T3> t3,
        Function4<T1, T2, T3, BlockBehaviour.Properties, B> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            t1,
            t2,
            t3,
            BlockBehaviour.propertiesCodec()
        ).apply(instance, factory));
    }

    private static <B extends StairBlock> App<RecordCodecBuilder.Mu<B>, BlockState> stairBaseStateApp() {
        return FormattedBlockStateCodec.CODEC.fieldOf("base_state").forGetter(b -> b.baseState);
    }

    public static <B extends StairBlock> MapCodec<B> stairCodec(
        BiFunction<BlockState, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            stairBaseStateApp(),
            factory
        );
    }

    public static <B extends WeatheringCopperStairBlock> MapCodec<B> weatheringCopperStairCodec(
        Function3<WeatheringCopper.WeatherState, BlockState, BlockBehaviour.Properties, B> factory
    ) {
        return simpleCodec(
            WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopperStairBlock::getAge),
            stairBaseStateApp(),
            factory
        );
    }

}
