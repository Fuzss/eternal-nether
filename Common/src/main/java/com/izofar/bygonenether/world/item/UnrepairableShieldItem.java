package com.izofar.bygonenether.world.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class UnrepairableShieldItem extends ShieldItem {

    public UnrepairableShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairCandidate) {
        return false;
    }
}
