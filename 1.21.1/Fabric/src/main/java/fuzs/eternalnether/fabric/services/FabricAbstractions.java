package fuzs.eternalnether.fabric.services;

import fuzs.eternalnether.services.CommonAbstractions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class FabricAbstractions implements CommonAbstractions {

    @Override
    public boolean isPiglinCurrency(ItemStack itemStack) {
        return itemStack.is(Items.GOLD_INGOT);
    }

    @Override
    public boolean canDisableShield(ItemStack itemStack, ItemStack shieldItemStack, LivingEntity livingEntity, LivingEntity attackingLivingEntity) {
        return itemStack.getItem() instanceof AxeItem;
    }
}
