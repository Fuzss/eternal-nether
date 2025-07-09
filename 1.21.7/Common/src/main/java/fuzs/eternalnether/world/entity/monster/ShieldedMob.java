package fuzs.eternalnether.world.entity.monster;

import fuzs.eternalnether.init.ModTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ShieldedMob {

    boolean isUsingShield();

    void setUsingShield(boolean isShielded);

    boolean isShieldDisabled();

    void startUsingShield();

    void stopUsingShield();

    @Deprecated
    @Nullable
    default InteractionHand getShieldHoldingHand(ItemStack mainHandItem, ItemStack offHandItem) {
        if (this.isUsingShield()) {
            if (mainHandItem.is(ModTags.SHIELD_TOOLS_ITEM_TAG_KEY)) {
                return InteractionHand.MAIN_HAND;
            } else if (offHandItem.is(ModTags.SHIELD_TOOLS_ITEM_TAG_KEY)) {
                return InteractionHand.OFF_HAND;
            }
        }

        return null;
    }
}
