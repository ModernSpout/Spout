package spout.clientview.packetmapping.component;

import io.papermc.paper.adventure.AdventureComponent;
import net.kyori.adventure.text.BlockNBTComponent;
import net.kyori.adventure.text.EntityNBTComponent;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.NBTComponent;
import net.kyori.adventure.text.ObjectComponent;
import net.kyori.adventure.text.ScoreComponent;
import net.kyori.adventure.text.SelectorComponent;
import net.kyori.adventure.text.StorageNBTComponent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import net.kyori.adventure.text.object.SpriteObjectContents;
import net.minecraft.network.chat.Component;

public final class PaperComponentTargetUtil extends NMSComponentTargetUtil {

    @Override
    public ComponentTarget getMostSpecificTarget(Component component) {
        if (component instanceof AdventureComponent adventureComponentHolder) {
            net.kyori.adventure.text.Component adventureComponent = adventureComponentHolder.adventure$component();
            if (adventureComponent instanceof TextComponent) {
                return ComponentTarget.TEXT;
            } else if (adventureComponent instanceof TranslatableComponent translatableComponent) {
                if (translatableComponent.fallback() == null) {
                    return ComponentTarget.TRANSLATABLE_WITHOUT_FALLBACK;
                }
                return ComponentTarget.TRANSLATABLE;
            } else if (adventureComponent instanceof ScoreComponent) {
                return ComponentTarget.SCORE;
            } else if (adventureComponent instanceof SelectorComponent) {
                return ComponentTarget.SELECTOR;
            } else if (adventureComponent instanceof KeybindComponent) {
                return ComponentTarget.KEYBIND;
            } else if (adventureComponent instanceof NBTComponent<?>) {
                if (adventureComponent instanceof BlockNBTComponent) {
                    return ComponentTarget.NBT_BLOCK;
                } else if (adventureComponent instanceof EntityNBTComponent) {
                    return ComponentTarget.NBT_ENTITY;
                } else if (adventureComponent instanceof StorageNBTComponent) {
                    return ComponentTarget.NBT_STORAGE;
                }
                return ComponentTarget.NBT;
            } else if (adventureComponent instanceof ObjectComponent objectComponent) {
                ObjectContents objectContents = objectComponent.contents();
                if (objectContents instanceof SpriteObjectContents) {
                    return ComponentTarget.OBJECT_ATLAS;
                } else if (objectContents instanceof PlayerHeadObjectContents) {
                    return ComponentTarget.OBJECT_PLAYER;
                }
                return ComponentTarget.OBJECT;
            }
        }
        return super.getMostSpecificTarget(component);
    }

}
