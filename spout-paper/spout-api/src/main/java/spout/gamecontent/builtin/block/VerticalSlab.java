package spout.gamecontent.builtin.block;

import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Waterlogged;

public interface VerticalSlab extends Orientable, Waterlogged {

    /**
     * @return The type.
     */
    Type getType();

    /**
     * Sets the type.
     *
     * @param type The new type.
     */
    void setType(Type type);

    /**
     * The type of the vertical slab.
     */
    enum Type {
        /**
         * The vertical slab occupies the higher (greater coordinate value) x or z half of the block.
         */
        HIGHER,
        /**
         * The vertical slab occupies the lower (lesser coordinate value) x or z half of the block.
         */
        LOWER,
        /**
         * The vertical slab occupies the entire block.
         */
        DOUBLE;
    }

}
