package fuzs.eternalnether.mixin;

import fuzs.eternalnether.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "disableShield", at = @At(value = "HEAD"), cancellable = true
    )
    public void disableShield(CallbackInfo callback) {
        if (this.getUseItem().is(ModItems.GILDED_NETHERITE_SHIELD)) {
            callback.cancel();
        }
    }
}
