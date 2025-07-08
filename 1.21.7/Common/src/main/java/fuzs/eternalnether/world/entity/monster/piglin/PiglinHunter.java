package fuzs.eternalnether.world.entity.monster.piglin;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.init.ModItems;
import fuzs.eternalnether.world.entity.ai.goal.UseShieldGoal;
import fuzs.eternalnether.world.entity.monster.ShieldedMob;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PiglinHunter extends Piglin implements ShieldedMob {
    private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(PiglinHunter.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_SHIELD_COOLDOWN = SynchedEntityData.defineId(PiglinHunter.class,
            EntityDataSerializers.INT);
    private static final ResourceLocation SPEED_MODIFIER_ATTACKING_ID = EternalNether.id("shielded_speed_penalty");
    private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_ID,
            -0.1,
            AttributeModifier.Operation.ADD_VALUE);
    private static final float SHIELD_BASE_PROBABILITY = 0.35F;
    private static final float GILDED_SHIELD_PROBABILITY = 0.05F;

    public PiglinHunter(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_SHIELDED, false);
        builder.define(DATA_SHIELD_COOLDOWN, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new UseShieldGoal<>(this, Player.class));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level() instanceof ServerLevel && this.getShieldCooldown() > 0) {
            this.setShieldCooldown(this.getShieldCooldown() - 1);
        }
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        if (this.getMainHandItem().has(DataComponents.WEAPON) && this.random.nextFloat()
                < SHIELD_BASE_PROBABILITY + SHIELD_BASE_PROBABILITY * difficulty.getSpecialMultiplier()) {
            this.setItemSlot(EquipmentSlot.OFFHAND, this.createShieldWeapon(random));
            this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        }
    }

    private ItemStack createShieldWeapon(RandomSource random) {
        return random.nextFloat() < GILDED_SHIELD_PROBABILITY ? new ItemStack(ModItems.GILDED_NETHERITE_SHIELD) :
                new ItemStack(Items.GOLDEN_SWORD);
    }

    @Override
    public void knockback(double strength, double x, double z) {
        if (!this.isUsingShield()) {
            super.knockback(strength, x, z);
        } else {
            this.playSound(SoundEvents.SHIELD_BLOCK.value(), 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
        }
    }

    @Override
    public boolean isShieldDisabled() {
        return false;
    }

    @Override
    public void startUsingShield() {
        if (!this.isUsingShield() && !this.isShieldDisabled()) {
            for (InteractionHand interactionHand : InteractionHand.values()) {
                if (this.getItemInHand(interactionHand).is(ModItems.GILDED_NETHERITE_SHIELD.value())) {
                    this.startUsingItem(interactionHand);
                    this.setUsingShield(true);
                    AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance != null && !attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING_ID)) {
                        attributeinstance.addTransientModifier(SPEED_MODIFIER_BLOCKING);
                    }
                }
            }
        }
    }

    @Override
    public void stopUsingShield() {
        if (this.isUsingShield()) {
            for (InteractionHand interactionhand : InteractionHand.values()) {
                if (this.getItemInHand(interactionhand).is(ModItems.GILDED_NETHERITE_SHIELD.value())) {
                    this.stopUsingItem();
                    this.setUsingShield(false);
                    AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance != null) {
                        attributeinstance.removeModifier(SPEED_MODIFIER_BLOCKING);
                    }
                }
            }
        }
    }

    @Override
    public boolean isUsingShield() {
        return this.entityData.get(DATA_IS_SHIELDED);
    }

    @Override
    public void setUsingShield(boolean isShielded) {
        this.entityData.set(DATA_IS_SHIELDED, isShielded);
    }

    private int getShieldCooldown() {
        return this.entityData.get(DATA_SHIELD_COOLDOWN);
    }

    private void setShieldCooldown(int shieldCooldown) {
        this.entityData.set(DATA_SHIELD_COOLDOWN, Math.max(0, shieldCooldown));
    }

    @Nullable
    public InteractionHand getShieldHoldingHand() {
        return this.getShieldHoldingHand(this.getMainHandItem(), this.getOffhandItem());
    }
}
