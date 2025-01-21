package fuzs.eternalnether.world.level.levelgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MobFeature extends Feature<NoneFeatureConfiguration> {
    private final WeightedRandomList<WeightedEntry.Wrapper<Holder<? extends EntityType<? extends Mob>>>> entityTypes;

    public MobFeature(Holder<? extends EntityType<? extends Mob>> entityType) {
        this(WeightedRandomList.create(WeightedEntry.wrap(entityType, 1)));
    }

    public MobFeature(WeightedRandomList<WeightedEntry.Wrapper<Holder<? extends EntityType<? extends Mob>>>> entityTypes) {
        super(NoneFeatureConfiguration.CODEC);
        this.entityTypes = entityTypes;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos position = context.origin().below();
        Mob mob = this.entityTypes.getRandom(context.random())
                .map(WeightedEntry.Wrapper::data)
                .map(Holder::value)
                .map(entityType -> {
                    return entityType.create(context.level().getLevel());
                })
                .orElse(null);
        if (mob != null) {
            mob.moveTo(position.getX() + 0.5, position.getY(), position.getZ() + 0.5, 0.0F, 0.0F);
            mob.finalizeSpawn(context.level(),
                    context.level().getCurrentDifficultyAt(position),
                    MobSpawnType.SPAWNER,
                    null);
            mob.setPersistenceRequired();
            context.level().addFreshEntity(mob);
            return true;
        } else {
            return false;
        }
    }
}
