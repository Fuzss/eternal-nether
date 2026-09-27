package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.world.entity.monster.WarpedEnderman;
import fuzs.puzzleslib.common.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(EternalNether.MOD_ID);
    public static final Holder.Reference<EntityDataSerializer<WarpedEnderman.Variant>> WARPED_ENDER_MAN_VARIANT_ENTITY_DATA_SERIALIZER = REGISTRIES.registerEntityDataSerializer(
            "warped_ender_man_variant",
            () -> EntityDataSerializer.forValueType(WarpedEnderman.Variant.STREAM_CODEC));
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(() -> new ItemStack(
            ModItems.WARPED_ENDER_PEARL), (CreativeModeTab.DisplayItemsGenerator generator) -> {
        return (CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) -> {
            output.accept(ModItems.COBBLED_BLACKSTONE.value());
            output.accept(ModItems.WITHERED_BLACKSTONE.value());
            ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getItemVariants()
                    .values()
                    .forEach((Holder.Reference<Item> holder) -> {
                        output.accept(holder.value());
                    });
            ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getItemVariants()
                    .values()
                    .forEach((Holder.Reference<Item> holder) -> {
                        output.accept(holder.value());
                    });
            output.accept(ModItems.WARPED_NETHER_BRICKS.value());
            ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getItemVariants()
                    .values()
                    .forEach((Holder.Reference<Item> holder) -> {
                        output.accept(holder.value());
                    });
            generator.accept(parameters, output);
        };
    });

    public static void boostrap() {
        ModBlocks.boostrap();
        ModEntityTypes.boostrap();
        ModItems.boostrap();
        ModBlockFamilies.bootstrap();
        ModStructureTypes.boostrap();
        ModFeatureTypes.boostrap();
        ModSensorTypes.boostrap();
        ModSoundEvents.boostrap();
    }
}
