package spout.clientview.clientmod.protocol;

/**
 * Possible changes:
 * <ul>
 *     <li>{@link #IDLE} -> {@link #HANDSHAKE_STARTED} - When login phase starts</li>
 *     <li>{@link #HANDSHAKE_STARTED} -> {@link #CLIENT_MOD_DETECTED} - When the client mod detection packet has been received and successfully processed</li>
 *     <li>{@link #HANDSHAKE_STARTED} -> {@link #CLIENT_MOD_NOT_DETECTED} - When the configuration phase starts without having received a client mod detection packet</li>
 *     <li>{@link #CLIENT_MOD_DETECTED} -> {@link #RECEIVED_CUSTOM_CONTENT} - When the custom content packet has been received</li>
 *     <li>{@link #RECEIVED_CUSTOM_CONTENT} -> {@link #ADDED_CUSTOM_CONTENT} - When the custom content packet has been processed</li>
 *     <li>Any state -> {@link #IDLE} - When the client disconnects or starts a new login</li>
 * </ul>
 */
public enum ClientModState {
    /**
     * Not connected or connecting to a server.
     */
    IDLE,
    /**
     * The login phase has started, but no other information is known yet.
     */
    HANDSHAKE_STARTED,
    /**
     * The client mod has been detected during the login phase, but no custom content has been received yet.
     */
    CLIENT_MOD_DETECTED,
    /**
     * The client mod has not been detected during the login phase, and the configuration phase has started.
     */
    CLIENT_MOD_NOT_DETECTED,
    /**
     * Some (but potentially not all) custom content has been received from the server.
     */
    RECEIVED_CUSTOM_CONTENT,
    /**
     * All custom content has been received and added (e.g. registered).
     */
    ADDED_CUSTOM_CONTENT,
}
