package spout.client.fabric.clientview;

/**
 * Possible changes:
 * <ul>
 *     <li>{@link #IDLE} -> {@link #HANDSHAKE_STARTED} - When login phase starts</li>
 *     <li>{@link #HANDSHAKE_STARTED} -> {@link #CLIENT_MOD_DETECTED} - When the client mod detection packet has been received and successfully processed</li>
 *     <li>{@link #HANDSHAKE_STARTED} -> {@link #CLIENT_MOD_NOT_DETECTED} - When the configuration phase starts without having received a client mod detection packet</li>
 *     <li>{@link #CLIENT_MOD_DETECTED} -> {@link #RECEIVED_CUSTOM_CONTENT} - When the custom content packet has been received</li>
 *     <li>{@link #RECEIVED_CUSTOM_CONTENT} -> {@link #ADDED_CUSTOM_CONTENT} - When the custom content packet has been processed</li>
 *     <li>{@link #IDLE} -> {@link #REMOVED_CUSTOM_CONTENT} - When the client is disconnecting and the custom content was never added</li>
 *     <li>{@link #HANDSHAKE_STARTED} -> {@link #REMOVED_CUSTOM_CONTENT} - When the client is disconnecting and the custom content was never added</li>
 *     <li>{@link #CLIENT_MOD_DETECTED} -> {@link #REMOVED_CUSTOM_CONTENT} - When the client is disconnecting and the custom content was never added</li>
 *     <li>{@link #CLIENT_MOD_NOT_DETECTED} -> {@link #REMOVED_CUSTOM_CONTENT} - When the client is disconnecting and the client mod was never detected</li>
 *     <li>{@link #ADDED_CUSTOM_CONTENT} -> {@link #REMOVED_CUSTOM_CONTENT} - When the client is disconnecting and the custom content has been removed</li>
 *     <li>{@link #REMOVED_CUSTOM_CONTENT} -> {@link #IDLE} - When the client has disconnected</li>
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
    /**
     * Received custom content has been removed, or we are disconnecting and it was never successfully added.
     */
    REMOVED_CUSTOM_CONTENT,
}
