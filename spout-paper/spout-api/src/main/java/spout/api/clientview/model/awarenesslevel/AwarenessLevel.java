package spout.api.clientview.model.awarenesslevel;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.Keyed;
import spout.api.SpoutBuiltInRegistry;
import spout.api.clientview.model.ClientView;
import spout.branding.SpoutNamespace;

/**
 * This interface represents the major categorization of the client's capability
 * to interpret data sent by the server.
 */
public interface AwarenessLevel extends Keyed {

    /**
     * @return True if every {@link ClientView} with this {@link AwarenessLevel}
     * will have {@link ClientView#understandsAllServerSideTranslatables} returning true.
     */
    boolean alwaysUnderstandsAllServerSideTranslatables();

    /**
     * @return True if every {@link ClientView} with this {@link AwarenessLevel}
     * will have {@link ClientView#understandsAllServerSideItems} returning true.
     */
    boolean alwaysUnderstandsAllServerSideItems();

    /**
     * @return True if every {@link ClientView} with this {@link AwarenessLevel}
     * will have {@link ClientView#understandsAllServerSideBlocks} returning true.
     */
    boolean alwaysUnderstandsAllServerSideBlocks();

    /**
     * For Java clients that have not accepted the server resource pack,
     * and also do not have the client mod.
     *
     * <p>
     * This generally results in data being replaced by the closest or most acceptable vanilla equivalent,
     * with additional rendering potentially being done through the use of vanilla entities.
     * </p>
     */
    AwarenessLevel VANILLA = getAwarenessLevel("vanilla");

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
    AwarenessLevel RESOURCE_PACK = getAwarenessLevel("resource_pack");

    /**
     * For Java clients that are have the client mod, i.e. they have the mod installed and are able to use
     * a sufficiently up-to-date version of it.
     *
     * <p>
     * This generally results in data being sent as-is, because when joining the server, the client receives
     * the necessary information to interpret the server-side block and item keys directly from then on.
     * </p>
     */
    AwarenessLevel CLIENT_MOD = getAwarenessLevel("client_mod");

    private static AwarenessLevel getAwarenessLevel(@KeyPattern.Value final String key) {
        return SpoutBuiltInRegistry.AWARENESS_LEVEL.getOrThrow(Key.key(SpoutNamespace.SPOUT, key));
    }

}
