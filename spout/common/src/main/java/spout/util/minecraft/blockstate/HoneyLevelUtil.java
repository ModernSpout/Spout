package spout.util.minecraft.blockstate;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.Nullable;
import java.util.Objects;

public final class HoneyLevelUtil {

    private HoneyLevelUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean haveSameHoneyLevel(@Nullable BlockState state1, @Nullable BlockState state2) {
        @Nullable Integer level1 = (state1 != null && state1.hasProperty(BlockStateProperties.LEVEL_HONEY)) ? state1.getValue(BlockStateProperties.LEVEL_HONEY) : null;
        @Nullable Integer level2 = (state2 != null && state2.hasProperty(BlockStateProperties.LEVEL_HONEY)) ? state2.getValue(BlockStateProperties.LEVEL_HONEY) : null;
        return Objects.equals(level1, level2);
    }

}
