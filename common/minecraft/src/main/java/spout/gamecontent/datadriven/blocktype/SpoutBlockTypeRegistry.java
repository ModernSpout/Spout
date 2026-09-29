package spout.gamecontent.datadriven.blocktype;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * The class for {@link BuiltInBlockTypeRegistry#BLOCK_TYPE}.
 */
public final class SpoutBlockTypeRegistry extends MappedRegistry<SpoutBlockType> {

    private final Map<MapCodec<? extends Block>, SpoutBlockType> byCodec = new IdentityHashMap<>();

    public SpoutBlockTypeRegistry() {
        super(BuiltInBlockTypeRegistryKey.BLOCK_TYPE, Lifecycle.stable());
    }

    @Override
    public Holder.Reference<SpoutBlockType> register(ResourceKey<SpoutBlockType> key, SpoutBlockType value, RegistrationInfo registrationInfo) {
        return this.register(key, value, registrationInfo, true);
    }

    public Holder.Reference<SpoutBlockType> register(ResourceKey<SpoutBlockType> key, SpoutBlockType value, RegistrationInfo registrationInfo, boolean createMinecraftMirror) {
        if (createMinecraftMirror) {
            MirrorMinecraftBlockTypeRegistry.mirrorFromSpoutToMinecraft(key, value);
        }
        @Nullable MapCodec<? extends Block> blockClassCodec = value.getBlockClassCodec();
        if (blockClassCodec != null) {
            this.byCodec.put(blockClassCodec, value);
        }
        return super.register(key, value, registrationInfo);
    }

    public void registerMinecraftRegistryMirror(ResourceKey<?> key, MapCodec<?> value, RegistrationInfo registrationInfo) {
        Identifier identifier = key.identifier();
        this.register(ResourceKey.create(BuiltInBlockTypeRegistryKey.BLOCK_TYPE, identifier), new CodecSpoutBlockType(identifier, (MapCodec) value), registrationInfo, false);
    }

    /**
     * @return The {@link SpoutBlockType} with the given {@link Block#codec}.
     */
    public @Nullable SpoutBlockType byBlockCodec(MapCodec<? extends Block> codec) {
        return this.byCodec.get(codec);
    }

}
