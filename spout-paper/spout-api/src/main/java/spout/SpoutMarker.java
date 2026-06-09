package spout;

/**
 * This class doesn't do anything.
 *
 * <p>
 * Its presence can be used by plugins to check whether this server supports Spout:
 * if {@code Class.forName("spout.SpoutMarker")}
 * does not throw a {@link ClassNotFoundException}, then the server supports Spout.
 * </p>
 */
public final class SpoutMarker {

    private SpoutMarker() {
        throw new UnsupportedOperationException();
    }

}
