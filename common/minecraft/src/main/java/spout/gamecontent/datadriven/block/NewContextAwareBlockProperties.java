package spout.gamecontent.datadriven.block;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jspecify.annotations.Nullable;
import spout.gamecontent.datadriven.blocktype.SpoutBlockType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A utility class to create {@link BlockBehaviour.Properties}
 * instances that have defaults applied to them based on the
 * {@linkplain ContextAwareBlockPropertiesDecoding context}.
 */
public final class NewContextAwareBlockProperties {

    private NewContextAwareBlockProperties() {
        throw new UnsupportedOperationException();
    }

    /**
     * The initializers per type (by their {@link SpoutBlockType#getIdentifier()}),
     * or null if not initialized yet.
     */
    private static @Nullable Map<Identifier, Consumer<BlockBehaviour.Properties>> initializers;

    private static void addInitializer(String key, Consumer<BlockBehaviour.Properties> value) {
        addInitializer(Identifier.parse(key), value);
    }

    private static void addInitializer(Identifier key, Consumer<BlockBehaviour.Properties> value) {
        initializers.put(key, value);
    }

    private static @Nullable Consumer<BlockBehaviour.Properties> getInitializer(String type) {
        return getInitializer(Identifier.parse(type));
    }

    private static @Nullable Consumer<BlockBehaviour.Properties> getInitializer(Identifier type) {
        if (initializers == null) {
            initializers = new HashMap<>();
            addInitializer("base_pressure_plate", properties -> {
                properties.noCollision()
                    .strength(0.5f)
                    .forceSolidOn()
                    .pushReaction(PushReaction.DESTROY);
            });
            addInitializer("button", properties -> {
                properties.noCollision()
                    .strength(0.5f)
                    .pushReaction(PushReaction.DESTROY);
            });
            addInitializer("door", properties -> {
                properties.noOcclusion()
                    .pushReaction(PushReaction.DESTROY);
            });
            addInitializer("fence", properties -> {
                properties.forceSolidOn();
            });
            addInitializer("fence_gate", properties -> {
                properties.forceSolidOn();
            });
            addInitializer("flower_pot", properties -> {
                properties.instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY);
            });
            addInitializer("ladder", properties -> {
                properties.sound(SoundType.LADDER)
                    .strength(0.4f)
                    .noOcclusion()
                    .forceSolidOff();
            });
            addInitializer("leaves", properties -> {
                properties.mapColor(MapColor.PLANT)
                    .sound(SoundType.GRASS)
                    .strength(0.2f)
                    .randomTicks()
                    .noOcclusion()
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .isValidSpawn(Blocks::ocelotOrParrot) // TODO document on wiki (currently not possible to define in data-driven JSON)
                    .isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never);
            });
            addInitializer("mangrove_leaves", properties -> {
                getInitializer("leaves").accept(properties);
            });
            addInitializer("mangrove_propagule", properties -> {
                getInitializer("leaves").accept(properties);
                properties.offsetType(BlockBehaviour.OffsetType.XZ);
            });
            addInitializer("pressure_plate", properties -> {
                getInitializer("base_pressure_plate").accept(properties);
            });
            addInitializer("sapling", properties -> {
                properties.mapColor(MapColor.PLANT)
                    .noCollision()
                    .sound(SoundType.GRASS)
                    .instabreak()
                    .randomTicks()
                    .pushReaction(PushReaction.DESTROY);
            });
            addInitializer("tinted_particle_leaves", properties -> {
                getInitializer("leaves").accept(properties);
            });
            addInitializer("trapdoor", properties -> {
                properties.noOcclusion()
                    .isValidSpawn(Blocks::never);
            });
            addInitializer("untinted_particle_leaves", properties -> {
                getInitializer("leaves").accept(properties);
            });
            addInitializer("wall", properties -> {
                properties.forceSolidOn();
            });
            addInitializer("weathering_copper_door", properties -> {
                getInitializer("door").accept(properties);
                properties.mapColor(MapColor.COLOR_ORANGE)
                    .strength(3.0f, 6.0f)
                    .requiresCorrectToolForDrops();
            });
            addInitializer("weathering_copper_grate", properties -> { // TODO document on wiki
                properties.strength(3.0F, 6.0F)
                    .sound(SoundType.COPPER_GRATE)
                    .mapColor(MapColor.COLOR_ORANGE)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never);
            });
            addInitializer("weathering_copper_trap_door", properties -> {
                getInitializer("trapdoor").accept(properties);
                properties.mapColor(MapColor.COLOR_ORANGE)
                    .strength(3.0f, 6.0f)
                    .requiresCorrectToolForDrops();
            });
            addInitializer("weighted_pressure_plate", properties -> {
                getInitializer("base_pressure_plate").accept(properties);
            });
        }
        return initializers.get(type);
    }

    /**
     * @return A new {@link BlockBehaviour.Properties} instance,
     * potentially with defaults applied.
     */
    public static BlockBehaviour.Properties create(@Nullable SpoutBlockType type, @Nullable ResourceKey<Block> key) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
        if (type != null) {
            @Nullable Consumer<BlockBehaviour.Properties> initializer = getInitializer(type.getIdentifier());
            if (initializer != null) {
                initializer.accept(properties);
            }
        }
        if (key != null) {
            properties.setId(key);
        }
        return properties;
    }

    /**
     * Calls {@link #create(SpoutBlockType, ResourceKey)} with the values currently in
     * {@link ContextAwareBlockPropertiesDecoding}.
     */
    public static BlockBehaviour.Properties create() {
        return create(ContextAwareBlockPropertiesDecoding.getType(), ContextAwareBlockPropertiesDecoding.getKey());
    }

}
