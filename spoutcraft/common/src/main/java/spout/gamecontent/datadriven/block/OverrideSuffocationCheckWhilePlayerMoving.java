package spout.gamecontent.datadriven.block;

/**
 * Override suffocating and view blocking behavior
 * because the client interprets suffocation as blocking motion
 * in some instances where it should not (e.g. custom stairs and walls).
 */
public final class OverrideSuffocationCheckWhilePlayerMoving {

    private OverrideSuffocationCheckWhilePlayerMoving() {
        throw new UnsupportedOperationException();
    }

    public static boolean isMoving;

}
