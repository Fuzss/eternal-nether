package fuzs.eternalnether.common.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;

public class WitheredBoneMealItem extends BoneMealItem {
    private static final int GROWTH_BONUS_COUNT = 3;

    public WitheredBoneMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos growthPos = clickedPos.relative(context.getClickedFace());
        if (growCrop(context.getItemInHand(), level, clickedPos)) {
            if (!level.isClientSide()) {
                context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, clickedPos, 15);
            }

            return InteractionResult.SUCCESS;
        } else {
            BlockState blockState = level.getBlockState(clickedPos);
            if (blockState.isFaceSturdy(level, clickedPos, context.getClickedFace())) {
                if (growWaterPlant(context.getItemInHand(), level, growthPos, context.getClickedFace())) {
                    if (!level.isClientSide()) {
                        context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                        level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, growthPos, 15);
                    }

                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        }
    }

    public static boolean growCrop(ItemStack itemStack, Level level, BlockPos blockPos) {
        boolean isSuccess = false;
        for (int i = 0; i < GROWTH_BONUS_COUNT; i++) {
            if (BoneMealItem.growCrop(itemStack, level, blockPos)) {
                isSuccess = true;
            } else {
                return isSuccess;
            }
        }

        return true;
    }

    public static boolean growWaterPlant(ItemStack itemStack, Level level, BlockPos blockPos, @Nullable Direction direction) {
        boolean isSuccess = false;
        for (int i = 0; i < GROWTH_BONUS_COUNT; i++) {
            if (BoneMealItem.growWaterPlant(itemStack, level, blockPos, direction)) {
                isSuccess = true;
            } else {
                return isSuccess;
            }
        }

        return true;
    }
}
