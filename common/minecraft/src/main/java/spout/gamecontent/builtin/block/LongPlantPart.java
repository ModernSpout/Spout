package spout.gamecontent.builtin.block;

import net.minecraft.util.StringRepresentable;

/**
 * A part of a long plant.
 */
public enum LongPlantPart implements StringRepresentable {

    SINGLE("single"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;

    LongPlantPart(String name) {
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
