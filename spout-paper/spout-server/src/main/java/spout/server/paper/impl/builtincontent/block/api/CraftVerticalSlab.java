package spout.server.paper.impl.builtincontent.block.api;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.Axis;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import spout.common.builtincontent.block.VerticalSlabBlock;
import spout.common.builtincontent.block.VerticalSlabType;
import spout.server.paper.api.builtincontent.block.api.VerticalSlab;
import java.util.Set;

public class CraftVerticalSlab extends CraftBlockData implements VerticalSlab {

    private static final EnumProperty<VerticalSlabType> TYPE = VerticalSlabBlock.TYPE;
    private static final EnumProperty<Direction.Axis> AXIS = VerticalSlabBlock.AXIS;
    private static final BooleanProperty WATERLOGGED = VerticalSlabBlock.WATERLOGGED;

    public CraftVerticalSlab(BlockState state) {
        super(state);
    }

    @Override
    public Type getType() {
        return this.get(TYPE, VerticalSlab.Type.class);
    }

    @Override
    public void setType(VerticalSlab.Type type) {
        Preconditions.checkArgument(type != null, "type cannot be null!");
        this.set(TYPE, type);
    }

    @Override
    public Axis getAxis() {
        return this.get(AXIS, Axis.class);
    }

    @Override
    public void setAxis(Axis axis) {
        Preconditions.checkArgument(axis != null, "axis cannot be null!");
        Preconditions.checkArgument(axis == Axis.X || axis == Axis.Z, "Invalid axis, only horizontal axis are allowed for this property!");
        this.set(AXIS, axis);
    }

    @Override
    public Set<Axis> getAxes() {
        return this.getValues(AXIS, Axis.class);
    }

    @Override
    public boolean isWaterlogged() {
        return this.get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        this.set(WATERLOGGED, waterlogged);
    }

}
