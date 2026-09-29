package spout.clientview.packetmapping.component;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

public abstract class ComponentTargetUtil {

    private static volatile @Nullable ComponentTargetUtil instance;

    public static ComponentTargetUtil get() {
        if (instance == null) {
            instance = ServiceLoader.load(ComponentTargetUtil.class).findFirst().get();
        }
        return instance;
    }

    public abstract ComponentTarget getMostSpecificTarget(Component component);

    public abstract boolean implies(ComponentTarget a, ComponentTarget b);

    public abstract List<ComponentTarget> expandTargets(Collection<ComponentTarget> targets);

    public abstract ComponentTarget getByOrdinal(int ordinal);

}
