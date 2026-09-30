package spout.clientview.clientmod.protocol;

public final class CurrentLoadedContentDiagnosticSummary {

    public static final CurrentLoadedContentDiagnosticSummary INSTANCE = new CurrentLoadedContentDiagnosticSummary();

    private int blocks;
    private int items;
    private int blockStates;
    private int registryEntryIdLists;
    private int blockStateRegistryEntryIdLists;

    public int getBlocks() {
        return this.blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getItems() {
        return this.items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public int getBlockStates() {
        return this.blockStates;
    }

    public void setBlockStates(int blockStates) {
        this.blockStates = blockStates;
    }

    public int getRegistryEntryIdLists() {
        return this.registryEntryIdLists;
    }

    public void setRegistryEntryIdLists(int registryEntryIdLists) {
        this.registryEntryIdLists = registryEntryIdLists;
    }

    public int getBlockStateRegistryEntryIdLists() {
        return this.blockStateRegistryEntryIdLists;
    }

    public void setBlockStateRegistryEntryIdLists(int blockStateRegistryEntryIdLists) {
        this.blockStateRegistryEntryIdLists = blockStateRegistryEntryIdLists;
    }

    public void reset() {
        this.blocks = 0;
        this.items = 0;
        this.blockStates = 0;
        this.registryEntryIdLists = 0;
        this.blockStateRegistryEntryIdLists = 0;
    }

}
