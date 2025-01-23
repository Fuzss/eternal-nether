package fuzs.eternalnether.world.item;

import fuzs.eternalnether.init.ModItems;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.data.MutableInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CutlassItem extends UnrepairableShieldItem {

    public CutlassItem(Properties properties) {
        super(properties);
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2);
    }

    public static EventResult onUseItemStart(LivingEntity livingEntity, ItemStack itemStack, MutableInt remainingUseDuration) {
        // remove shield activation delay
        if (itemStack.is(ModItems.CUTLASS)) {
            remainingUseDuration.mapInt((int value) -> value - 5);
        }
        return EventResult.PASS;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }
}
