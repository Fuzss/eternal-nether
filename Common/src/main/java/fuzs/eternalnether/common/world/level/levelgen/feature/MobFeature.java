package fuzs.eternalnether.common.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record MobFeature(WeightedList<Holder<EntityType<?>>> entities) implements Feature {
    public static final MapCodec<MobFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(WeightedList.codec(
                    BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()).fieldOf("entities").forGetter(MobFeature::entities))
            .apply(instance, MobFeature::new));

    @SuppressWarnings("unchecked")
    public MobFeature(Holder<? extends EntityType<?>> entityType) {
        this(WeightedList.of((Holder<EntityType<?>>) entityType));
    }

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        BlockPos position = origin.below();
        Entity entity = this.entities().getRandom(random).<Entity>map((Holder<EntityType<?>> holder) -> {
            return holder.value().create(level.getLevel(), EntitySpawnReason.STRUCTURE);
        }).orElse(null);
        if (entity != null) {
            entity.snapTo(position.getX() + 0.5, position.getY(), position.getZ() + 0.5, 0.0F, 0.0F);
            if (entity instanceof Mob mob) {
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(position), EntitySpawnReason.STRUCTURE, null);
                mob.setPersistenceRequired();
            }

            level.addFreshEntity(entity);
            return true;
        } else {
            return false;
        }
    }
}
