package fuzs.eternalnether.common.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jspecify.annotations.Nullable;

public record MobPassengerFeature(Holder<EntityType<?>> passengerEntityType,
                                  Holder<EntityType<?>> vehicleEntityType) implements Feature {
    public static final MapCodec<MobPassengerFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()
                            .fieldOf("passenger")
                            .forGetter(MobPassengerFeature::passengerEntityType),
                    BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()
                            .fieldOf("vehicle")
                            .forGetter(MobPassengerFeature::vehicleEntityType))
            .apply(instance, MobPassengerFeature::new));

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        Mob passenger = this.createMob(level, origin, this.passengerEntityType);
        Mob vehicle = this.createMob(level, origin, this.vehicleEntityType);
        if (passenger != null && vehicle != null) {
            passenger.startRiding(vehicle);
            level.addFreshEntityWithPassengers(vehicle);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private Mob createMob(WorldGenLevel level, BlockPos origin, Holder<EntityType<?>> entityType) {
        BlockPos blockPos = origin.below();
        Mob mob = (Mob) entityType.value().create(level.getLevel(), EntitySpawnReason.STRUCTURE);
        if (mob != null) {
            mob.finalizeSpawn(level,
                    level.getCurrentDifficultyAt(blockPos),
                    EntitySpawnReason.STRUCTURE,
                    null);
            mob.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            mob.setPersistenceRequired();
            return mob;
        } else {
            return null;
        }
    }
}
