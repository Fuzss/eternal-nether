package fuzs.eternalnether.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.StopHoldingItemIfNoLongerAdmiring;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StopHoldingItemIfNoLongerAdmiring.class)
abstract class StopHoldingItemIfNoLongerAdmiringFabricMixin {

    @ModifyExpressionValue(
            method = "lambda$create$0(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/piglin/Piglin;J)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private static boolean create(boolean isShield, ServerLevel serverLevel, Piglin piglin, long gameTime) {
        return isShield || piglin.getOffhandItem().getItem() instanceof ShieldItem;
    }
}
