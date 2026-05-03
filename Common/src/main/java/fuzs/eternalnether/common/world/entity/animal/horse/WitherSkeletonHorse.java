package fuzs.eternalnether.common.world.entity.animal.horse;

import fuzs.eternalnether.common.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class WitherSkeletonHorse extends SkeletonHorse {
    /**
     * Copied from {@code SkeletonHorse#BABY_DIMENSIONS} from Minecraft 1.21.11.
     *
     * @see SkeletonHorse#BABY_DIMENSIONS
     */
    private static final EntityDimensions BABY_DIMENSIONS = ModEntityTypes.WITHER_SKELETON_HORSE.value()
            .getDimensions()
            .withAttachments(EntityAttachments.builder()
                    .attach(EntityAttachment.PASSENGER,
                            0.0F,
                            ModEntityTypes.WITHER_SKELETON_HORSE.value().getHeight() - 0.03125F,
                            0.0F))
            .scale(0.5F);

    public WitherSkeletonHorse(EntityType<? extends SkeletonHorse> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 35.0).add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide() && this.random.nextInt(3) == 0) {
            this.level()
                    .addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                            this.getX(this.random.nextGaussian() * 0.25),
                            this.getRandomY() + 0.15,
                            this.getZ(this.random.nextGaussian() * 0.25),
                            0.0,
                            this.random.nextDouble() * -0.05,
                            0.0);
        }

        this.floatHorse();
        super.aiStep();
    }

    /**
     * @see Strider#floatStrider()
     */
    private void floatHorse() {
        if (this.isInLava()) {
            CollisionContext context = CollisionContext.of(this);
            if (context.isAbove(this.getLiquidCollisionShape(), this.blockPosition(), true) && !this.level()
                    .getFluidState(this.blockPosition().above())
                    .is(FluidTags.LAVA)) {
                this.setOnGround(true);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5).add(0.0, 0.05, 0.0));
            }
        }
    }

    @Override
    public VoxelShape getLiquidCollisionShape() {
        return Block.column(16.0, 0.0, 8.0);
    }

    @Override
    public boolean isTamed() {
        return true;
    }

    @Override
    public boolean canStandOnFluid(FluidState fluid) {
        return fluid.is(FluidTags.LAVA);
    }

    /**
     * @see Strider#canAddPassenger(Entity)
     */
    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return super.canAddPassenger(passenger) && !this.isEyeInFluid(FluidTags.LAVA);
    }

    /**
     * @see net.minecraft.world.entity.monster.Strider.StriderPathNavigation
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level) {
            @Override
            protected boolean hasValidPathType(PathType pathType) {
                return pathType == PathType.LAVA || pathType == PathType.FIRE || pathType == PathType.FIRE_IN_NEIGHBOR
                        || super.hasValidPathType(pathType);
            }

            @Override
            public boolean isStableDestination(BlockPos pos) {
                return this.level.getBlockState(pos).is(Blocks.LAVA) || super.isStableDestination(pos);
            }
        };
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        // Step sounds for fluids normally don't play.
        super.playStepSound(blockPos, blockState.is(Blocks.LAVA) ? Blocks.MAGMA_BLOCK.defaultBlockState() : blockState);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.discard("SkeletonTrap");
        output.discard("SkeletonTrapTime");
    }

    @Override
    public boolean isTrap() {
        return false;
    }

    @Override
    public void setTrap(boolean trap) {
        // NO-OP
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return ModEntityTypes.WITHER_SKELETON_HORSE.value().create(level, EntitySpawnReason.BREEDING);
    }
}
