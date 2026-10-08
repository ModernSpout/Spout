package spout.gamecontent.datadriven.common.enuminjection.pluginbytecode;

public final class EnumSwitchHelper {

    private EnumSwitchHelper() {}

    /**
     * Drop-in replacement for IALOAD on {@code $SwitchMap} arrays.
     * 0 routes to default.
     * */
    public static int safeLoad(int[] arr, int idx) {
        return idx < arr.length ? arr[idx] : 0;
    }

}
