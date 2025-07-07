package fuzs.eternalnether.init;

import fuzs.eternalnether.world.entity.ai.sensing.ModPiglinBruteSpecificSensor;
import fuzs.eternalnether.world.entity.ai.sensing.PiglinPrisonerSpecificSensor;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;

public final class ModSensorTypes {
    public static final Holder.Reference<SensorType<ModPiglinBruteSpecificSensor>> PIGLIN_BRUTE_SPECIFIC_SENSOR_TYPE = ModRegistry.REGISTRIES.register(
            Registries.SENSOR_TYPE,
            "piglin_brute_specific_sensor",
            () -> new SensorType<>(ModPiglinBruteSpecificSensor::new));
    public static final Holder.Reference<SensorType<PiglinPrisonerSpecificSensor>> PIGLIN_PRISONER_SPECIFIC_SENSOR_TYPE = ModRegistry.REGISTRIES.register(
            Registries.SENSOR_TYPE,
            "piglin_prisoner_specific_sensor",
            () -> new SensorType<>(PiglinPrisonerSpecificSensor::new));

    public static void boostrap() {
        // NO-OP
    }
}
