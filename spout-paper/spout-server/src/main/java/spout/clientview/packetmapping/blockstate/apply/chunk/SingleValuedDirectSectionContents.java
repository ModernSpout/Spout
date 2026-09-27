package spout.clientview.packetmapping.blockstate.apply.chunk;

import net.minecraft.network.VarInt;

/**
 * {@link DirectSectionContents} where each block has the same block state id.
 */
public final class SingleValuedDirectSectionContents extends DirectSectionContents {

    /**
     * The single block state id.
     */
    private int blockStateIndexInRegistry;

    SingleValuedDirectSectionContents() {
        super();
    }

    @Override
    public int getBlockStateId(int blockIndex) {
        return this.blockStateIndexInRegistry;
    }

    public SingleValuedDirectSectionContents setBlockStateIndexInRegistry(int blockStateIndexInRegistry) {
        this.blockStateIndexInRegistry = blockStateIndexInRegistry;
        return this;
    }

    @Override
    public short getNonEmptyBlockCount() {
        return IS_NON_EMPTY_BLOCK_STATE[this.blockStateIndexInRegistry] ? (short) 4096 : 0;
    }

    @Override
    public short getFluidCount() {
        return IS_FLUID_STATE[this.blockStateIndexInRegistry] ? (short) 4096 : 0;
    }

    @Override
    public byte getValidMinimalBitsPerEntry(byte globalPaletteBitsPerEntry) {
        return 0;
    }

    @Override
    public int getPalettedContainerSizeInBytes(byte bitsPerEntry) {
        return 1 + VarInt.getByteSize(this.blockStateIndexInRegistry);
    }

    @Override
    protected void writeAsPalettedContainerInternal(final ChunkPacketBlockMapperWriter writer, final byte bitsPerEntry) {
        this.writeAsSingleValuedPalettedContainer(writer);
    }

    @Override
    public void clear() {
    }

}
