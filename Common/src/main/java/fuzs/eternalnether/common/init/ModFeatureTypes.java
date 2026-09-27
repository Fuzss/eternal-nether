package fuzs.eternalnether.common.init;

import com.mojang.serialization.MapCodec;
import fuzs.eternalnether.common.world.level.levelgen.feature.MobFeature;
import fuzs.eternalnether.common.world.level.levelgen.feature.MobPassengerFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class ModFeatureTypes {
    public static final Holder.Reference<MapCodec<? extends Feature>> MOB_FEATURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.FEATURE_TYPE,
            "mob_feature",
            () -> MobFeature.CODEC);
    public static final Holder.Reference<MapCodec<? extends Feature>> MOB_PASSENGER_FEATURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.FEATURE_TYPE,
            "mob_passenger_feature",
            () -> MobPassengerFeature.CODEC);

    public static void boostrap() {
        // NO-OP
    }
}
