package com.izofar.bygonenether.init;

import com.izofar.bygonenether.world.entity.ai.sensing.ModPiglinBruteSpecificSensor;
import com.izofar.bygonenether.world.entity.ai.sensing.PiglinPrisonerSpecificSensor;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;

public class ModSensorTypes {
    public static final Holder.Reference<SensorType<ModPiglinBruteSpecificSensor>> PIGLIN_BRUTE_SPECIFIC_SENSOR = ModRegistry.REGISTRIES.register(
            Registries.SENSOR_TYPE,
            "piglin_brute_specific_sensor",
            () -> new SensorType<>(ModPiglinBruteSpecificSensor::new));
    public static final Holder.Reference<SensorType<PiglinPrisonerSpecificSensor>> PIGLIN_PRISONER_SPECIFIC_SENSOR = ModRegistry.REGISTRIES.register(
            Registries.SENSOR_TYPE,
            "piglin_prisoner_specific_sensor",
            () -> new SensorType<>(PiglinPrisonerSpecificSensor::new));

    public static void boostrap() {
        // NO-OP
    }
}
