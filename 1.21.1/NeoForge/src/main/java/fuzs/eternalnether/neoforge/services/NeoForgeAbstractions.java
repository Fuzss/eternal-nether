package fuzs.eternalnether.neoforge.services;

import fuzs.eternalnether.services.CommonAbstractions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class NeoForgeAbstractions implements CommonAbstractions {

    @Override
    public boolean isPiglinCurrency(ItemStack itemStack) {
        return itemStack.isPiglinCurrency();
    }

    @Override
    public boolean canDisableShield(ItemStack itemStack, ItemStack shieldItemStack, LivingEntity livingEntity, LivingEntity attackingLivingEntity) {
        return itemStack.canDisableShield(shieldItemStack, livingEntity, attackingLivingEntity);
    }
}
