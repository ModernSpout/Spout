package spout.client.fabric.moredatadriven.minecraft.type;

import net.minecraft.world.level.material.PushReaction;
import spout.common.util.mojang.codec.ProxyCodec;

/**
 * A proxy enum for {@link PushReaction}.
 */
public enum PushReactionProxy implements ProxyCodec.Proxy<PushReaction> {

    NORMAL(PushReaction.NORMAL),
    DESTROY(PushReaction.DESTROY),
    BLOCK(PushReaction.BLOCK),
    IGNORE(PushReaction.IGNORE),
    PUSH_ONLY(PushReaction.PUSH_ONLY);

    private final PushReaction original;

    PushReactionProxy(PushReaction original) {
        this.original = original;
    }

    @Override
    public PushReaction getOriginal() {
        return this.original;
    }

}
