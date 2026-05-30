package spout.common.protocol;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import spout.branding.SpoutNamespace;
import spout.common.moredatadriven.minecraft.block.BlockStateRegistryEntryIdList;
import spout.common.moredatadriven.minecraft.block.SpoutNonBuiltInBlock;
import spout.common.moredatadriven.minecraft.item.SpoutNonBuiltInItem;
import spout.common.moredatadriven.minecraft.registry.RegistryEntryIdList;
import spout.common.util.minecraft.resources.KeyedValue;
import java.util.Objects;
import java.util.function.Function;

public final class ClientModCustomContentPacketPayload implements CustomPacketPayload {

    private static final Identifier PACKET_ID = Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, "custom_content");
    public static final CustomPacketPayload.Type<ClientModCustomContentPacketPayload> TYPE = new CustomPacketPayload.Type<>(PACKET_ID);
    public static final StreamCodec<FriendlyByteBuf, ClientModCustomContentPacketPayload> STREAM_CODEC = CustomPacketPayload.codec(ClientModCustomContentPacketPayload::write, ClientModCustomContentPacketPayload::new);
    public static final CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, ?> TYPE_AND_CODEC = new CustomPacketPayload.TypeAndCodec<>(TYPE, ClientModCustomContentPacketPayload.STREAM_CODEC);

    private static final Gson GSON = new Gson();

    @Override
    public CustomPacketPayload.Type<ClientModCustomContentPacketPayload> type() {
        return TYPE;
    }

    private final byte @Nullable [] bytes;
    private final Element @Nullable [] elements;

    public ClientModCustomContentPacketPayload(Element[] elements) {
        // Turn the elements into bytes
        FriendlyByteBuf temporaryBuffer = new FriendlyByteBuf(Unpooled.buffer());
        temporaryBuffer.writeVarInt(elements.length);
        for (Element element : elements) {
            element.write(temporaryBuffer);
        }
        this.bytes = new byte[temporaryBuffer.readableBytes()];
        temporaryBuffer.getBytes(temporaryBuffer.readerIndex(), this.bytes);
        // Elements will stay empty
        this.elements = null;
    }

    public ClientModCustomContentPacketPayload(FriendlyByteBuf buffer) {
        // Turn the bytes into elements
        this.elements = new Element[buffer.readVarInt()];
        for (int i = 0; i < this.elements.length; i++) {
            this.elements[i] = new Element(buffer);
        }
        // Bytes will stay empty
        this.bytes = null;
    }

    public Element[] getElements() {
        return Objects.requireNonNull(this.elements);
    }

    private void write(FriendlyByteBuf buffer) {
        buffer.writeBytes(Objects.requireNonNull(this.bytes));
    }

    public static final class Element {

        private final byte @Nullable [] bytes;
        private final @Nullable Contents contents;

        public Element(Contents contents) {
            // Turn the contents into bytes
            FriendlyByteBuf temporaryBuffer = new FriendlyByteBuf(Unpooled.buffer());
            Contents.Type type = contents.getType();
            temporaryBuffer.writeByte(type.ordinal());
            ((StreamCodec) type.codec).encode(temporaryBuffer, contents);
            this.bytes = new byte[temporaryBuffer.readableBytes()];
            temporaryBuffer.getBytes(temporaryBuffer.readerIndex(), this.bytes);
            // Contents will stay empty
            this.contents = null;
        }

        private Element(FriendlyByteBuf buffer) {
            // Turn the bytes into contents
            Contents.Type type = Contents.Type.VALUES[buffer.readByte()];
            this.contents = type.codec.decode(buffer);
            // Bytes will stay empty
            this.bytes = null;
        }

        public int sizeInBytes() {
            return Objects.requireNonNull(this.bytes).length;
        }

        public Contents getContents() {
            return Objects.requireNonNull(this.contents);
        }

        private void write(FriendlyByteBuf buffer) {
            buffer.writeBytes(Objects.requireNonNull(this.bytes));
        }

        public interface Contents {

            /**
             * The {@link Type} of this payload element.
             */
            Type getType();

            /**
             * The content type for an element.
             */
            enum Type {

                /**
                 * A special type indicating the end of the custom content.
                 */
                END(EndContents.CODEC),
                /**
                 * A custom block.
                 */
                BLOCK(BlockContents.CODEC),
                /**
                 * A custom item.
                 */
                ITEM(ItemContents.CODEC),
                /**
                 * A {@link RegistryEntryIdList}.
                 */
                REGISTRY_ENTRY_ID_LIST(RegistryEntryIdListContents.CODEC),
                /**
                 * A {@link BlockStateRegistryEntryIdList}.
                 */
                BLOCK_STATE_REGISTRY_ENTRY_ID_LIST(BlockStateRegistryEntryIdListContents.CODEC);

                private static final Type[] VALUES = values();

                public final StreamCodec<FriendlyByteBuf, ? extends Contents> codec;

                Type(StreamCodec<FriendlyByteBuf, ? extends Contents> codec) {
                    this.codec = codec;
                }

            }

        }

        /**
         * Element contents of type {@link Type#END}.
         */
        public static final class EndContents implements Contents {

            public static final EndContents INSTANCE = new EndContents();

            private static final StreamCodec<FriendlyByteBuf, EndContents> CODEC = StreamCodec.unit(INSTANCE);

            private EndContents() {
            }

            @Override
            public Type getType() {
                return Type.END;
            }

        }

        /**
         * Element contents of a keyed value.
         */
        public static abstract class KeyedValueContents<V> implements Contents {

            protected static <V, C extends KeyedValueContents<V>> StreamCodec<FriendlyByteBuf, C> codec(
                Function<KeyedValue<V>, C> constructor,
                StreamCodec<FriendlyByteBuf, V> valueCodec
            ) {
                return new StreamCodec<>() {

                    @Override
                    public C decode(FriendlyByteBuf input) {
                        Identifier identifier = Identifier.STREAM_CODEC.decode(input);
                        V value = valueCodec.decode(input);
                        return constructor.apply(new KeyedValue<>(identifier, value));
                    }

                    @Override
                    public void encode(FriendlyByteBuf output, C value) {
                        Identifier.STREAM_CODEC.encode(output, value.value.identifier());
                        valueCodec.encode(output, value.value.value());
                    }

                };
            }

            public final KeyedValue<V> value;

            protected KeyedValueContents(KeyedValue<V> value) {
                this.value = value;
            }

        }

        /**
         * {@link KeyedValueContents} for blocks.
         */
        public static final class BlockContents extends KeyedValueContents<SpoutNonBuiltInBlock> {

            private static final StreamCodec<FriendlyByteBuf, BlockContents> CODEC = codec(
                BlockContents::new,
                SpoutNonBuiltInBlock.STREAM_CODEC
            );

            public BlockContents(KeyedValue<SpoutNonBuiltInBlock> value) {
                super(value);
            }

            @Override
            public Type getType() {
                return Type.BLOCK;
            }

        }

        /**
         * {@link KeyedValueContents} for items.
         */
        public static final class ItemContents extends KeyedValueContents<SpoutNonBuiltInItem> {

            private static final StreamCodec<FriendlyByteBuf, ItemContents> CODEC = codec(
                ItemContents::new,
                SpoutNonBuiltInItem.STREAM_CODEC
            );

            public ItemContents(KeyedValue<SpoutNonBuiltInItem> value) {
                super(value);
            }

            @Override
            public Type getType() {
                return Type.ITEM;
            }

        }

        /**
         * Element contents of type {@link Type#REGISTRY_ENTRY_ID_LIST}.
         */
        public static final class RegistryEntryIdListContents implements Contents {

            public static final StreamCodec<FriendlyByteBuf, RegistryEntryIdListContents> CODEC = StreamCodec.of(
                (buf, value) -> RegistryEntryIdList.STREAM_CODEC.encode(buf, value.value),
                buf -> new RegistryEntryIdListContents(RegistryEntryIdList.STREAM_CODEC.decode(buf))
            );

            public final RegistryEntryIdList value;

            public RegistryEntryIdListContents(RegistryEntryIdList value) {
                this.value = value;
            }

            @Override
            public Type getType() {
                return Type.REGISTRY_ENTRY_ID_LIST;
            }

        }

        /**
         * Element contents of type {@link Type#BLOCK_STATE_REGISTRY_ENTRY_ID_LIST}.
         */
        public static final class BlockStateRegistryEntryIdListContents implements Contents {

            public static final StreamCodec<FriendlyByteBuf, BlockStateRegistryEntryIdListContents> CODEC = StreamCodec.of(
                (buf, value) -> BlockStateRegistryEntryIdList.STREAM_CODEC.encode(buf, value.value),
                buf -> new BlockStateRegistryEntryIdListContents(BlockStateRegistryEntryIdList.STREAM_CODEC.decode(buf))
            );

            public final BlockStateRegistryEntryIdList value;

            public BlockStateRegistryEntryIdListContents(BlockStateRegistryEntryIdList value) {
                this.value = value;
            }

            @Override
            public Type getType() {
                return Type.BLOCK_STATE_REGISTRY_ENTRY_ID_LIST;
            }

        }

    }

}
