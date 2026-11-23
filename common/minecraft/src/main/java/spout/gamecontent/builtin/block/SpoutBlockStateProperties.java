package spout.gamecontent.builtin.block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Additional block state properties that are not in {@link BlockStateProperties}.
 */
public final class SpoutBlockStateProperties {

    private SpoutBlockStateProperties() {
        throw new UnsupportedOperationException();
    }

    public static final BooleanProperty NORTH_WEST_BOTTOM = BooleanProperty.create("west_down_north");
    public static final BooleanProperty SOUTH_WEST_BOTTOM = BooleanProperty.create("west_down_south");
    public static final BooleanProperty NORTH_WEST_TOP = BooleanProperty.create("west_up_north");
    public static final BooleanProperty SOUTH_WEST_TOP = BooleanProperty.create("west_up_south");
    public static final BooleanProperty NORTH_EAST_BOTTOM = BooleanProperty.create("east_down_north");
    public static final BooleanProperty SOUTH_EAST_BOTTOM = BooleanProperty.create("east_down_south");
    public static final BooleanProperty NORTH_EAST_TOP = BooleanProperty.create("east_up_north");
    public static final BooleanProperty SOUTH_EAST_TOP = BooleanProperty.create("east_up_south");
    public static final EnumProperty<VerticalSlabType> VERTICAL_SLAB_TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty PLANT_BOTTOM = BooleanProperty.create("bottom");
    public static final IntegerProperty CATTAIL_COUNT = IntegerProperty.create("count", 1, 3);
    public static final BooleanProperty CATTAIL_FLUFFY = BooleanProperty.create("fluffy");

}
