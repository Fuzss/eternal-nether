package fuzs.eternalnether.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import fuzs.eternalnether.common.init.ModTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DeltaFeature.class)
abstract class DeltaFeatureMixin {

    @ModifyExpressionValue(method = "isClear",
                           at = @At(value = "INVOKE",
                                    target = "Lcom/google/common/collect/ImmutableList;contains(Ljava/lang/Object;)Z"))
    private boolean isClear(boolean contains, @Local BlockState state) {
        return contains || state.is(ModTags.Blocks.BASALT_DELTA_CANNOT_REPLACE);
    }
}
