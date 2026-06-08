package spout.gamecontent.datadriven.blocktype;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

/**
 * A utility class that provides functionality to keep
 * the values of {@link BuiltInBlockTypeRegistry#BLOCK_TYPE}
 * and {@link BuiltInRegistries#BLOCK_TYPE} synchronized.
 */
public final class MirrorMinecraftBlockTypeRegistry {

    private MirrorMinecraftBlockTypeRegistry() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Boolean> currentlyMirroringFromSpoutToMinecraft = ThreadLocal.withInitial(() -> false);

    public static void mirrorFromMinecraftToSpoutIfNecessary(Registry<?> registry, ResourceKey<?> key, Object value, RegistrationInfo registrationInfo) {
        if (value instanceof MapCodec<?> mapCodec && registry.key().equals(Registries.BLOCK_TYPE) && !currentlyMirroringFromSpoutToMinecraft.get()) {
            BuiltInBlockTypeRegistry.BLOCK_TYPE.registerMinecraftRegistryMirror(key, mapCodec, registrationInfo);
        }
    }

    public static void mirrorFromSpoutToMinecraft(ResourceKey<SpoutBlockType> key, SpoutBlockType value) {
        currentlyMirroringFromSpoutToMinecraft.set(true);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, ResourceKey.create(BuiltInRegistries.BLOCK_TYPE.key(), key.identifier()), value.getBlockClassCodec());
        currentlyMirroringFromSpoutToMinecraft.set(false);
    }

}
