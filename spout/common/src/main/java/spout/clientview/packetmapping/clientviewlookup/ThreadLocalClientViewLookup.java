package spout.clientview.packetmapping.clientviewlookup;

import spout.clientview.model.ClientView;
import spout.clientview.model.FallbackClientViewImpl;
import org.jspecify.annotations.Nullable;
import java.lang.ref.WeakReference;

/**
 * A thread-local storage for a {@link ClientViewLookup}.
 *
 * <p>
 * This can be used to pass the current {@link ClientViewLookup} through an opaque call chain.
 * </p>
 */
public final class ThreadLocalClientViewLookup {

    private ThreadLocalClientViewLookup() {
        throw new UnsupportedOperationException();
    }

    /**
     * A {@link ThreadLocal} holding the {@link ClientViewLookup}
     * for which packets are being encoded on the current thread.
     */
    private static final ThreadLocal<@Nullable WeakReference<ClientViewLookup>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * @return A {@link WeakReference} to the {@link ClientViewLookup} in {@link #THREAD_LOCAL},
     * or null if it doesn't exist.
     */
    public static @Nullable WeakReference<ClientViewLookup> getWeakReference() {
        return THREAD_LOCAL.get();
    }

    /**
     * @return The {@link ClientViewLookup} in {@link #THREAD_LOCAL},
     * or null if it doesn't exist.
     */
    public static @Nullable ClientViewLookup get() {
        @Nullable WeakReference<ClientViewLookup> clientViewProviderReference = getWeakReference();
        return clientViewProviderReference == null ? null : clientViewProviderReference.get();
    }

    /**
     * Stores the given {@link ClientViewLookup} in {@link #THREAD_LOCAL}.
     */
    public static void setWeakReference(@Nullable WeakReference<ClientViewLookup> lookup) {
        THREAD_LOCAL.set(lookup);
    }

    /**
     * Stores the given {@link ClientViewLookup} in {@link #THREAD_LOCAL}.
     */
    public static void set(@Nullable ClientViewLookup lookup) {
        setWeakReference(lookup == null ? null : new WeakReference<>(lookup));
    }

    /**
     * Removes the {@link ClientViewLookup} in {@link #THREAD_LOCAL}.
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * Utility function to get the {@link ClientView} from the {@link ClientViewLookup} in {@link #THREAD_LOCAL},
     * or null if it doesn't exist.
     */
    public static @Nullable ClientView getThreadLocalClientView() {
        @Nullable ClientViewLookup clientViewLookup = get();
        return clientViewLookup == null ? null : clientViewLookup.getClientView();
    }

    /**
     * Utility function, the same as {@link #getThreadLocalClientView}, but will return
     * {@link FallbackClientViewImpl#INSTANCE} instead of null.
     */
    public static ClientView getThreadLocalClientViewOrFallback() {
        @Nullable ClientView clientView = getThreadLocalClientView();
        return clientView != null ? clientView : FallbackClientViewImpl.INSTANCE;
    }

}
