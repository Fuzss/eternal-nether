package fuzs.eternalnether.mixin;

import fuzs.eternalnether.world.entity.monster.piglin.ModPiglinBruteAi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.PiglinBruteAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PiglinAi.class)
abstract class PiglinAiMixin {

    @Inject(
            method = "angerNearbyPiglins", at = @At(value = "TAIL")
    )
    private static void angerNearbyPiglins(Player player, boolean angerOnlyIfCanSee, CallbackInfo callback) {
        List<PiglinBrute> list = player.level()
                .getEntitiesOfClass(PiglinBrute.class, player.getBoundingBox().inflate(16.0));
        list.stream()
                .filter(PiglinAiMixin::isIdle)
                .filter((PiglinBrute piglinBrute) -> !angerOnlyIfCanSee || BehaviorUtils.canSee(piglinBrute, player))
                .forEach((PiglinBrute piglinBrute) -> {
                    if (piglinBrute.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                        ModPiglinBruteAi.setAngerTargetToNearestTargetablePlayerIfFound(piglinBrute, player);
                    } else {
                        PiglinBruteAi.setAngerTarget(piglinBrute, player);
                    }
                });
    }

    @Shadow
    private static boolean isIdle(AbstractPiglin piglin) {
        throw new RuntimeException();
    }

    @Inject(
            method = "isWearingGold", at = @At(
            value = "HEAD"
    ), cancellable = true
    )
    private static void isWearingGold(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> callback) {
        for (ItemStack itemStack : livingEntity.getArmorAndBodyArmorSlots()) {
            if (ModPiglinBruteAi.makesPiglinsNeutral(itemStack)) {
                callback.setReturnValue(true);
                break;
            }
        }
    }
}
