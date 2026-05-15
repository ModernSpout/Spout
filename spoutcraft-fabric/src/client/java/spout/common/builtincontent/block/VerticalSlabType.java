package spout.common.builtincontent.block;

import net.minecraft.util.StringRepresentable;

/**
 * Which part of a vertical slab is filled.
 */
public enum VerticalSlabType implements StringRepresentable {

    HIGHER("higher"),
    LOWER("lower"),
    DOUBLE("double");

    private final String name;

    VerticalSlabType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}
