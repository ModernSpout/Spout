package spout.server.paper.api.resourcepack.construct;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.Nullable;
import spout.api.clientview.model.awarenesslevel.AwarenessLevel;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Provides functionality to edit the constructed Spout server resource pack.
 */
public interface ResourcePackConstructEvent extends LifecycleEvent {

    /**
     * @param awarenessLevel The {@link AwarenessLevel} for which to modify the resource pack.
     * @return The {@link ResourcePackPath} at the given string path,
     * where directories are separated by forward slashes ({@code /}),
     * for example {@code "assets/example/models/item/ash.json"} to get the path to the model of the
     * {@code "example:ash"} item.
     * @throws IllegalArgumentException If the given {@link AwarenessLevel}
     *                                  does not support a resource pack.
     */
    ResourcePackPath path(AwarenessLevel awarenessLevel, String path);

    /**
     * @param awarenessLevel The {@link AwarenessLevel} for which to modify the resource pack.
     * @param directoryName  The name of the directory in which the asset is.
     *                       This must not start with {@code "assets/"} (it is automatically added).
     *                       The name is allowed to contain forward slashes,
     *                       e.g. {@code "models/item"}, with namespaced key {@code "example:ash"}
     *                       and extension {@code "json"}, to get the path
     *                       {@code "assets/example/models/item/ash.json"}.
     * @param key            A {@link NamespacedKey} of the asset.
     *                       The key part is allowed to contain forward slashes,
     *                       e.g. {@code "item/ash"}, with namespace {@code "example"}
     *                       and directory {@code "models"} and extension {@code "json"}, to get the path
     *                       {@code "assets/example/models/item/ash.json"}.
     * @param extension      The file extension, for example {@code "json"} or {@code "png"}.
     * @return The {@link ResourcePackPath} for the asset in the given directory,
     * at the given {@link NamespacedKey}.
     * @throws IllegalArgumentException If the given {@link AwarenessLevel}
     *                                  does not support a resource pack.
     */
    ResourcePackPath asset(AwarenessLevel awarenessLevel, String directoryName, NamespacedKey key, @Nullable String extension);

    /**
     * @see #asset(AwarenessLevel, String, NamespacedKey, String)
     */
    ResourcePackPath asset(AwarenessLevel awarenessLevel, String directoryName, Keyed keyed, @Nullable String extension);

    /**
     * @see #asset(AwarenessLevel, String, NamespacedKey, String)
     */
    ResourcePackPath asset(AwarenessLevel awarenessLevel, String directoryName, Key keyed, @Nullable String extension);

    /**
     * @see #asset(AwarenessLevel, String, NamespacedKey, String)
     */
    ResourcePackPath asset(AwarenessLevel awarenessLevel, String directoryName, net.kyori.adventure.key.Keyed keyed, @Nullable String extension);

    /**
     * Copies a plugin resource to the resource pack.
     *
     * @param pathInPluginResources A path to a file in the plugin's resources
     *                              (relative to the {@code src/main/resources} folder),
     *                              for example {@code "resource_pack/assets/example/models/block/ash_block.json"}.
     * @param pathInResourcePack    A path to a file in the resource pack,
     *                              for example {@code "assets/example/models/block/ash_block.json"}.
     */
    void copyPluginResource(PluginBootstrap bootstrap, AwarenessLevel awarenessLevel, String pathInPluginResources, String pathInResourcePack) throws IOException;

    /**
     * @see #copyPluginResource(PluginBootstrap, AwarenessLevel, String, String)
     */
    void copyPluginResource(PluginBootstrap bootstrap, AwarenessLevel[] awarenessLevels, String pathInPluginResources, String pathInResourcePack) throws IOException;

    /**
     * @see #copyPluginResource(PluginBootstrap, AwarenessLevel, String, String)
     */
    void copyPluginResource(PluginBootstrap bootstrap, Iterable<AwarenessLevel> awarenessLevels, String pathInPluginResources, String pathInResourcePack) throws IOException;

    /**
     * @see #copyPluginResource(PluginBootstrap, AwarenessLevel, String, String)
     */
    void copyPluginResource(Class<? extends PluginBootstrap> bootstrapClass, AwarenessLevel awarenessLevel, String pathInPluginResources, String pathInResourcePack) throws IOException;

    /**
     * @see #copyPluginResource(PluginBootstrap, AwarenessLevel, String, String)
     */
    void copyPluginResource(Class<? extends PluginBootstrap> bootstrapClass, AwarenessLevel[] awarenessLevels, String pathInPluginResources, String pathInResourcePack) throws IOException;

    /**
     * @see #copyPluginResource(PluginBootstrap, AwarenessLevel, String, String)
     */
    void copyPluginResource(Class<? extends PluginBootstrap> bootstrapClass, Iterable<AwarenessLevel> awarenessLevels, String pathInPluginResources, String pathInResourcePack) throws IOException;

    /**
     * Copies plugin resources to the resource pack.
     *
     * @param pathInPluginResources A path to a folder in the plugin's resources
     *                              (relative to the {@code src/main/resources} folder),
     *                              for example {@code "resource_pack/assets/example/models"}.
     * @param pathInResourcePack    A path to a folder in the resource pack,
     *                              for example {@code "assets/example/models"}.
     *                              To copy to the root of the resource pack, put a blank string ({@code ""}).
     * @param filter                A filter on the file names (relative to the root of the {@code pathInResourcePack}),
     *                              or null if not required. If present, only file names for which the predicate
     *                              returns true will be copied.
     */
    void copyPluginResources(BootstrapContext context, AwarenessLevel awarenessLevel, String pathInPluginResources, String pathInResourcePack, @Nullable Predicate<String> filter) throws IOException;

    /**
     * @see #copyPluginResources(BootstrapContext, AwarenessLevel, String, String, Predicate)
     */
    void copyPluginResources(BootstrapContext context, AwarenessLevel[] awarenessLevels, String pathInPluginResources, String pathInResourcePack, @Nullable Predicate<String> filter) throws IOException;

    /**
     * @see #copyPluginResources(BootstrapContext, AwarenessLevel, String, String, Predicate)
     */
    void copyPluginResources(BootstrapContext context, Iterable<AwarenessLevel> awarenessLevels, String pathInPluginResources, String pathInResourcePack, @Nullable Predicate<String> filter) throws IOException;

    /**
     * The same as {@link #copyPluginResources(BootstrapContext, AwarenessLevel, String, String, Predicate)},
     * but for the given {@link BootstrapContext#getPluginSource()}.
     */
    void copyPluginResources(Path pluginSource, AwarenessLevel awarenessLevel, String pathInPluginResources, String pathInResourcePack, @Nullable Predicate<String> filter) throws IOException;

    /**
     * @see #copyPluginResources(Path, AwarenessLevel, String, String, Predicate)
     */
    void copyPluginResources(Path pluginSource, AwarenessLevel[] awarenessLevels, String pathInPluginResources, String pathInResourcePack, @Nullable Predicate<String> filter) throws IOException;

    /**
     * @see #copyPluginResources(Path, AwarenessLevel, String, String, Predicate)
     */
    void copyPluginResources(Path pluginSource, Iterable<AwarenessLevel> awarenessLevels, String pathInPluginResources, String pathInResourcePack, @Nullable Predicate<String> filter) throws IOException;

}
