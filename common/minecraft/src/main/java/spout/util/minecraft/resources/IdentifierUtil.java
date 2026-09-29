package spout.util.minecraft.resources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import spout.branding.SpoutNamespace;

public final class IdentifierUtil {

    private IdentifierUtil() {
        throw new UnsupportedOperationException();
    }

    public static Identifier parseWithSpoutNamespaceAsDefault(String identifier) {
        return identifier.indexOf(':') == -1 ? Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, identifier) : Identifier.parse(identifier);
    }

    public static final Codec<Identifier> CODEC_WITH_SPOUT_NAMESPACE_AS_DEFAULT = Codec.STRING.xmap(
        IdentifierUtil::parseWithSpoutNamespaceAsDefault,
        Identifier::toString
    );

    /**
     * Analogous to {@link Registry#byNameCodec()}.
     */
    public static <T> Codec<T> byNameWithSpoutNamespaceAsDefaultCodec(Registry<T> registry) {
        return referenceHolderWithLifecycleWithSpoutNamespaceAsDefault(registry).flatComapMap(Holder.Reference::value, value -> safeCastToReference(registry, registry.wrapAsHolder(value)));
    }

    /**
     * Analogous to {@link Registry#holderByNameCodec()}.
     */
    public static <T> Codec<Holder<T>> holderByNameWithSpoutNamespaceAsDefaultCodec(Registry<T> registry) {
        return referenceHolderWithLifecycleWithSpoutNamespaceAsDefault(registry).flatComapMap((holder) -> holder, holder -> safeCastToReference(registry, holder));
    }

    /**
     * Auxiliary method for {@link #holderByNameWithSpoutNamespaceAsDefaultCodec}.
     *
     * <p>
     * Analogous to {@link Registry#safeCastToReference(Holder)}.
     * </p>
     */
    private static <T> DataResult<Holder.Reference<T>> safeCastToReference(Registry<T> registry, Holder<T> holder) {
        if (holder instanceof Holder.Reference<T> reference) {
            return DataResult.success(reference);
        } else {
            return DataResult.error(() -> "Unregistered holder in " + registry.key() + ": " + holder);
        }
    }

    /**
     * Analogous to {@link Registry#referenceHolderWithLifecycle()}.
     */
    public static <T> Codec<Holder.Reference<T>> referenceHolderWithLifecycleWithSpoutNamespaceAsDefault(Registry<T> registry) {
        Codec<Holder.Reference<T>> referenceCodec = CODEC_WITH_SPOUT_NAMESPACE_AS_DEFAULT.comapFlatMap((name) -> registry.get(name).map(DataResult::success).orElseGet(() -> DataResult.error(() ->
            "Unknown registry key in " + registry.key() + ": " + name
        )), (holder) -> holder.key().identifier());
        return ExtraCodecs.overrideLifecycle(referenceCodec, (e) -> registry.registrationInfo(e.key()).map(RegistrationInfo::lifecycle).orElse(Lifecycle.experimental()));
    }

    /**
     * @return The given {@link Identifier} with the given suffix added to the key's {@link Identifier#getPath()}.
     */
    public static Identifier addPathSuffix(Identifier identifier, String suffix) {
        return Identifier.fromNamespaceAndPath(identifier.getNamespace(), identifier.getPath() + suffix);
    }

}
