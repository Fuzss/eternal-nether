package fuzs.eternalnether.services;

import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = ServiceProviderHelper.load(CommonAbstractions.class);

    boolean isPiglinCurrency(ItemStack itemStack);

    boolean canDisableShield(ItemStack itemStack, ItemStack shieldItemStack, LivingEntity livingEntity, LivingEntity attackingLivingEntity);
}
