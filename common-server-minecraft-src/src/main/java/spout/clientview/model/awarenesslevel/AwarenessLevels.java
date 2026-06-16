package spout.clientview.model.awarenesslevel;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import spout.branding.SpoutNamespace;

/**
 * Holds the built-in {@link AwarenessLevel} values.
 */
public final class AwarenessLevels {

    private AwarenessLevels() {
        throw new UnsupportedOperationException();
    }

    /**
     * For Java clients that have not accepted the server resource pack,
     * and also do not have the client mod.
     *
     * <p>
     * This generally results in data being replaced by the closest or most acceptable vanilla equivalent,
     * with additional rendering potentially being done through the use of vanilla entities.
     * </p>
     */
    public static final AwarenessLevel VANILLA = register("vanilla", new AwarenessLevel(false, false, false));

    /**
     * For Java clients that have accepted the server resource pack,
     * but do not have the client mod.
     *
     * <p>
     * This generally results in data being replaced by hosts that are overridden in the resource pack
     * (such as block states) or having additional data attached that links to the resource pack
     * (such as explicit item model).
     * Additional rendering can be done through the use of entities.
     * </p>
     */
    public static final AwarenessLevel RESOURCE_PACK = register("resource_pack", new AwarenessLevel(true, false, false));

    /**
     * For Java clients that are have the client mod, i.e. they have the mod installed and are able to use
     * a sufficiently up-to-date version of it.
     *
     * <p>
     * This generally results in data being sent as-is, because when joining the server, the client receives
     * the necessary information to interpret the server-side block and item keys directly from then on.
     * </p>
     */
    public static final AwarenessLevel CLIENT_MOD = register("client_mod", new AwarenessLevel(true, true, true));

    private static AwarenessLevel register(String name, AwarenessLevel value) {
        return register(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, name), value);
    }

    private static AwarenessLevel register(Identifier location, AwarenessLevel value) {
        value.id = BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL.size();
        return Registry.register(BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL, location, value);
    }

    public static AwarenessLevel bootstrap(Registry<AwarenessLevel> registry) {
        return CLIENT_MOD;
    }

    /**
     * Return value for {@link #getAll()},
     * or null if not initialized yet.
     */
    private static AwarenessLevel @Nullable [] all;

    /**
     * Convenience function that returns all {@link AwarenessLevel}s.
     *
     * @return An array of {@link AwarenessLevel}s.
     */
    public static AwarenessLevel[] getAll() {
        if (all == null) {
            all = BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL.stream().toArray(AwarenessLevel[]::new);
        }
        return all;
    }

    /**
     * Return value for {@link #getThatDoNotAlwaysUnderstandsAllServerSideTranslatables()},
     * or null if not initialized yet.
     */
    private static AwarenessLevel @Nullable [] thatDoNotAlwaysUnderstandsAllServerSideTranslatables;

    /**
     * Convenience function that returns all {@link AwarenessLevel}s
     * that have {@link AwarenessLevel#alwaysUnderstandsAllServerSideTranslatables()} returning false.
     *
     * @return An array of {@link AwarenessLevel}s.
     */
    public static AwarenessLevel[] getThatDoNotAlwaysUnderstandsAllServerSideTranslatables() {
        if (thatDoNotAlwaysUnderstandsAllServerSideTranslatables == null) {
            thatDoNotAlwaysUnderstandsAllServerSideTranslatables = BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL.stream().filter(level -> !level.alwaysUnderstandsAllServerSideTranslatables()).toArray(AwarenessLevel[]::new);
        }
        return thatDoNotAlwaysUnderstandsAllServerSideTranslatables;
    }

    /**
     * Return value for {@link #getThatDoNotAlwaysUnderstandsAllServerSideBlocks()},
     * or null if not initialized yet.
     */
    private static AwarenessLevel @Nullable [] thatDoNotAlwaysUnderstandsAllServerSideBlocks;

    /**
     * Convenience function that returns all {@link AwarenessLevel}s
     * that have {@link AwarenessLevel#alwaysUnderstandsAllServerSideBlocks()} returning false.
     *
     * @return An array of {@link AwarenessLevel}s.
     */
    public static AwarenessLevel[] getThatDoNotAlwaysUnderstandsAllServerSideBlocks() {
        if (thatDoNotAlwaysUnderstandsAllServerSideBlocks == null) {
            thatDoNotAlwaysUnderstandsAllServerSideBlocks = BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL.stream().filter(level -> !level.alwaysUnderstandsAllServerSideBlocks()).toArray(AwarenessLevel[]::new);
        }
        return thatDoNotAlwaysUnderstandsAllServerSideBlocks;
    }

    /**
     * Return value for {@link #getThatDoNotAlwaysUnderstandsAllServerSideItems()},
     * or null if not initialized yet.
     */
    private static AwarenessLevel @Nullable [] thatDoNotAlwaysUnderstandsAllServerSideItems;

    /**
     * Convenience function that returns all {@link AwarenessLevel}s
     * that have {@link AwarenessLevel#alwaysUnderstandsAllServerSideItems()} returning false.
     *
     * @return An array of {@link AwarenessLevel}s.
     */
    public static AwarenessLevel[] getThatDoNotAlwaysUnderstandsAllServerSideItems() {
        if (thatDoNotAlwaysUnderstandsAllServerSideItems == null) {
            thatDoNotAlwaysUnderstandsAllServerSideItems = BuiltInAwarenessLevelRegistry.AWARENESS_LEVEL.stream().filter(level -> !level.alwaysUnderstandsAllServerSideItems()).toArray(AwarenessLevel[]::new);
        }
        return thatDoNotAlwaysUnderstandsAllServerSideItems;
    }

}
