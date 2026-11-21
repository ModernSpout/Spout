package spout.gamecontent.builtin.item.tag;

public final class PlaceOnWaterBlockCallsToUseOnAreSuperCalls {

    private PlaceOnWaterBlockCallsToUseOnAreSuperCalls() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Boolean> state =  ThreadLocal.withInitial(() -> false);

    public static void set() {
        state.set(true);
    }

    public static void unset() {
        state.set(false);
    }

    public static boolean get() {
        return state.get();
    }

}
