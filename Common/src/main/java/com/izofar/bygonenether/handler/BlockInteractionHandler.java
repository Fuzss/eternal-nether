package com.izofar.bygonenether.handler;

import com.izofar.bygonenether.world.entity.ai.PiglinPrisonerAi;
import com.izofar.bygonenether.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockInteractionHandler {

    public static EventResult onAttackBlock(Player player, Level level, InteractionHand interactionHand, BlockPos pos, Direction direction) {
        if (!player.getAbilities().instabuild && level.getBlockState(pos).is(ModRegistry.WITHERED_BLOCK_TAG_KEY) &&
                !(player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof TieredItem tieredItem &&
                        tieredItem.getTier().getLevel() >= Tiers.NETHERITE.getLevel())) {
            return EventResult.INTERRUPT;
        } else {
            return EventResult.PASS;
        }
    }

    public static EventResult onBreakBlock(ServerLevel level, BlockPos pos, BlockState state, Player player, ItemStack itemInHand) {
        if (state.is(Blocks.IRON_BARS)) {
            PiglinPrisonerAi.exciteNearbyPiglins(player, false);
        }
        return EventResult.PASS;
    }
}