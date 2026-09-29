package spout.api.clientview.model;

import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;

public interface ClientView {

    AwarenessLevel getAwarenessLevel();

    /**
     * @return The player of this client,
     * or null if not available.
     */
    @Nullable Player getPlayer();

    /**
     * @return The locale (lower-case, in the format that Minecraft uses,
     * such as "{@code ja_jp}" for Japanese) of this client,
     * or null if not available.
     */
    @Nullable String getLocale();

    /**
     * @return True only if this client understands all server-side translatables.
     * false if it can not be guaranteed.
     */
    boolean understandsAllServerSideTranslatables();

    /**
     * @return True only if this client understands all server-side items.
     * false if it can not be guaranteed.
     */
    boolean understandsAllServerSideItems();

    /**
     * @return True only if this client understands all server-side blocks.
     * false if it can not be guaranteed.
     */
    boolean understandsAllServerSideBlocks();

}
