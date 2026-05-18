package spout.server.paper.impl.packetmapping.block.light;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A utility class to track for the current thread light processing
 * whether extra light sending is necessary.
 */
public final class CurrentTaskRequiresExtraLightSending {

    private CurrentTaskRequiresExtraLightSending() {
        throw new UnsupportedOperationException();
    }

    private static final AtomicInteger requiredCount = new AtomicInteger(0); // Plain access is faster than ThreadLocal, so use it as a pre-check
    private static final ThreadLocal<Boolean> required = ThreadLocal.withInitial(() -> false);

    public static void set() {
        required.set(true);
        requiredCount.incrementAndGet();
    }

    public static void clear() {
        required.set(false);
        requiredCount.decrementAndGet();
    }

    public static boolean get() {
        return requiredCount.getPlain() > 0 && required.get();
    }

}
