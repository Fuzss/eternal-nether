package com.izofar.bygonenether.world.entity.ai;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.PiglinBruteAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterials;

import java.util.Optional;

public class ModPiglinBruteAi {

    public static void setAngerTargetToNearestTargetablePlayerIfFound(PiglinBrute piglinBrute, LivingEntity defaultEntity) {
        Optional<Player> optional =
                piglinBrute.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ?
                        piglinBrute.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) :
                        Optional.empty();
        PiglinBruteAi.setAngerTarget(piglinBrute, optional.isPresent() ? optional.get() : defaultEntity);
    }

    public static boolean isWearingGild(LivingEntity entity) {
        for (ItemStack itemstack : entity.getArmorSlots()) {
            if (makesPiglinBrutesNeutral(itemstack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean makesPiglinBrutesNeutral(ItemStack itemStack) {
        return itemStack.getItem() instanceof ArmorItem armorItem &&
                armorItem.getMaterial() == ArmorMaterials.NETHERITE && armorIsGoldTrim(itemStack);
    }

    public static boolean armorIsGoldTrim(ItemStack itemStack) {
        ArmorTrim armorTrim = itemStack.get(DataComponents.TRIM);
        return armorTrim != null && armorTrim.material().is(TrimMaterials.GOLD);
    }
}
