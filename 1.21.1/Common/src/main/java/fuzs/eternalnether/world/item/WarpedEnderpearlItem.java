package fuzs.eternalnether.world.item;

import fuzs.eternalnether.world.entity.projectile.ThrownWarpedEnderpearl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WarpedEnderpearlItem extends Item {

    public WarpedEnderpearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        // copied from super, but replaces thrown pearl entity
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        level.playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENDER_PEARL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide) {
            ThrownWarpedEnderpearl thrownWarpedEnderpearl = new ThrownWarpedEnderpearl(level, player);
            thrownWarpedEnderpearl.setItem(itemInHand);
            // increase velocity
            thrownWarpedEnderpearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            level.addFreshEntity(thrownWarpedEnderpearl);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemInHand.consume(1, player);

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }
}
