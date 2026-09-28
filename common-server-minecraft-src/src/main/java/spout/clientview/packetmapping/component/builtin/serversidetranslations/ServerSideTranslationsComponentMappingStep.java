package spout.clientview.packetmapping.component.builtin.serversidetranslations;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import spout.api.clientview.model.ClientView;
import spout.clientview.packetmapping.component.apply.ComponentMappingHandle;
import spout.server.paper.api.packetmapping.component.translatable.ServerSideTranslations;
import spout.util.mapping.handle.MappingStep;

/**
 * A {@link MappingStep} that applies the {@linkplain ServerSideTranslations registered server-side translations}.
 */
public final class ServerSideTranslationsComponentMappingStep implements MappingStep<ComponentMappingHandle> {

    @Override
    public void apply(ComponentMappingHandle handle) {
        ClientView clientView = handle.getContext().getClientView();
        if (clientView.understandsAllServerSideTranslatables()) return;
        Component immutable = handle.getImmutable();
        ComponentContents contents = immutable.getContents();
        if (contents instanceof TranslatableContents translatableContents) {
            String key = translatableContents.getKey();
            ServerSideTranslations.ServerSideTranslation translation = ServerSideTranslations.get().get(key, clientView.getLocale());
            if (translation != null) {
                if (translation.overrideClientSide()) {
                    handle.setMutable(Component.literal(translation.translation()).withStyle(immutable.getStyle()));
                } else {
                    handle.setMutable(Component.translatableWithFallback(key, translation.translation()).withStyle(immutable.getStyle()));
                }
            }
        }
    }

}
