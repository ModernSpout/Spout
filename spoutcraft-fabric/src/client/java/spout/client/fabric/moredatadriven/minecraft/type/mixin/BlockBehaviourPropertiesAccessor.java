package spout.client.fabric.moredatadriven.minecraft.type.mixin;

import net.minecraft.resources.DependantName;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockBehaviourPropertiesAccessor {

    @Accessor("mapColor")
    void setMapColor(Function<BlockState, MapColor> mapColor);

    @Accessor("hasCollision")
    void setHasCollision(boolean hasCollision);

    @Accessor("soundType")
    void setSoundType(SoundType soundType);

    @Accessor("lightEmission")
    void setLightEmission(ToIntFunction<BlockState> lightEmission);

    @Accessor("explosionResistance")
    void setExplosionResistance(float explosionResistance);

    @Accessor("destroyTime")
    void setDestroyTime(float destroyTime);

    @Accessor("requiresCorrectToolForDrops")
    void setRequiresCorrectToolForDrops(boolean requiresCorrectToolForDrops);

    @Accessor("isRandomlyTicking")
    void setIsRandomlyTicking(boolean isRandomlyTicking);

    @Accessor("friction")
    void setFriction(float friction);

    @Accessor("speedFactor")
    void setSpeedFactor(float speedFactor);

    @Accessor("jumpFactor")
    void setJumpFactor(float jumpFactor);

    @Accessor("id")
    ResourceKey<Block> getId();

    @Accessor("id")
    void setId(ResourceKey<Block> id);

    @Accessor("drops")
    void setDrops(DependantName<Block, Optional<ResourceKey<LootTable>>> drops);

    @Accessor("descriptionId")
    void setDescriptionId(DependantName<Block, String> descriptionId);

    @Accessor("canOcclude")
    void setCanOcclude(boolean canOcclude);

    @Accessor("isAir")
    void setIsAir(boolean isAir);

    @Accessor("ignitedByLava")
    void setIgnitedByLava(boolean ignitedByLava);

    @Accessor("liquid")
    void setLiquid(boolean liquid);

    @Accessor("forceSolidOff")
    void setForceSolidOff(boolean forceSolidOff);

    @Accessor("forceSolidOn")
    void setForceSolidOn(boolean forceSolidOn);

    @Accessor("pushReaction")
    void setPushReaction(PushReaction pushReaction);

    @Accessor("spawnTerrainParticles")
    void setSpawnTerrainParticles(boolean spawnTerrainParticles);

    @Accessor("instrument")
    void setInstrument(NoteBlockInstrument instrument);

    @Accessor("replaceable")
    void setReplaceable(boolean replaceable);

    @Accessor("isValidSpawn")
    void setIsValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> predicate);

    @Accessor("isRedstoneConductor")
    void setIsRedstoneConductor(BlockBehaviour.StatePredicate predicate);

    @Accessor("isSuffocating")
    void setIsSuffocating(BlockBehaviour.StatePredicate predicate);

    @Accessor("isViewBlocking")
    void setIsViewBlocking(BlockBehaviour.StatePredicate predicate);

    @Accessor("postProcess")
    void setPostProcess(BlockBehaviour.PostProcess postProcess);

    @Accessor("emissiveRendering")
    void setEmissiveRendering(BlockBehaviour.StatePredicate predicate);

    @Accessor("dynamicShape")
    void setDynamicShape(boolean dynamicShape);

    @Accessor("requiredFeatures")
    void setRequiredFeatures(FeatureFlagSet requiredFeatures);

    @Accessor("offsetFunction")
    void setOffsetFunction(BlockBehaviour.@Nullable OffsetFunction offsetFunction);

}
