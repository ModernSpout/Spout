package spout.api.clientview.packetmapping.component;

/**
 * Conversion utility for {@link ComponentTarget}.
 */
public final class CraftComponentTarget {

    private CraftComponentTarget() {
        throw new UnsupportedOperationException();
    }

    public static spout.clientview.packetmapping.component.ComponentTarget fromBukkit(ComponentTarget target) {
        return spout.clientview.packetmapping.component.ComponentTarget.valueOf(target.name());
    }

    public static ComponentTarget toBukkit(spout.clientview.packetmapping.component.ComponentTarget target) {
        return ComponentTarget.valueOf(target.name());
    }

}
