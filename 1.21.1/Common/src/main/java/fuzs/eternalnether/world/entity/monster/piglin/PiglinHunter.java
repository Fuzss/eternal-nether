package fuzs.eternalnether.world.entity.monster.piglin;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.init.ModItems;
import fuzs.eternalnether.world.entity.ai.goal.ShieldDefenseGoal;
import fuzs.eternalnether.world.entity.monster.ShieldedMob;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PiglinHunter extends Piglin implements ShieldedMob {
    private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(PiglinHunter.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHIELD_HAND = SynchedEntityData.defineId(PiglinHunter.class,
            EntityDataSerializers.BOOLEAN); // True for Main Hand, False for Offhand
    private static final EntityDataAccessor<Integer> DATA_SHIELD_COOLDOWN = SynchedEntityData.defineId(PiglinHunter.class,
            EntityDataSerializers.INT);
    private static final ResourceLocation SPEED_MODIFIER_ATTACKING_ID = EternalNether.id("shielded_speed_penalty");
    private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_ID,
            -0.1,
            AttributeModifier.Operation.ADD_VALUE);
    private static final float SHIELDED_BASE_PROBABILITY = 0.35F;
    private static final float GILDED_SHIELDED_BASE_PROBABILITY = 0.05F;
    private static final float CROSSBOW_PROBABILITY = 0.5F;

    public PiglinHunter(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ShieldDefenseGoal<>(this, Player.class));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.decrementShieldCooldown();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_SHIELDED, false);
        builder.define(DATA_SHIELD_HAND, false);
        builder.define(DATA_SHIELD_COOLDOWN, 0);
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        float f = SHIELDED_BASE_PROBABILITY + 0.4F * difficulty.getEffectiveDifficulty() / 2.25F / CROSSBOW_PROBABILITY;
        if (!this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.CROSSBOW) && this.random.nextFloat() < f) {
            Item shield = this.random.nextFloat() < GILDED_SHIELDED_BASE_PROBABILITY * f ?
                    ModItems.GILDED_NETHERITE_SHIELD.value() : Items.SHIELD;
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(shield));
        }
    }

    @Override
    protected float getEquipmentDropChance(EquipmentSlot slot) {
        if (slot == EquipmentSlot.OFFHAND) {
            return 0.0F;
        } else {
            return super.getEquipmentDropChance(slot);
        }
    }

    @Override
    public void knockback(double strength, double x, double z) {
        if (!this.isUsingShield()) {
            super.knockback(strength, x, z);
        } else {
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
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
                if (ToolTypeHelper.INSTANCE.isShield(this.getItemInHand(interactionHand))) {
                    this.startUsingItem(interactionHand);
                    this.setUsingShield(true);
                    this.setShieldMainHand(interactionHand == InteractionHand.MAIN_HAND);
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
            for (InteractionHand interactionHand : InteractionHand.values()) {
                if (ToolTypeHelper.INSTANCE.isShield(this.getItemInHand(interactionHand))) {
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

    public boolean isUsingShield() {
        return this.entityData.get(DATA_IS_SHIELDED);
    }

    public void setUsingShield(boolean isShielded) {
        this.entityData.set(DATA_IS_SHIELDED, isShielded);
    }

    private boolean isShieldMainHand() {
        return this.entityData.get(DATA_SHIELD_HAND);
    }

    private void setShieldMainHand(boolean isShieldedMainHand) {
        this.entityData.set(DATA_SHIELD_HAND, isShieldedMainHand);
    }

    private int getShieldCooldown() {
        return this.entityData.get(DATA_SHIELD_COOLDOWN);
    }

    private void setShieldCooldown(int newShieldCooldown) {
        this.entityData.set(DATA_SHIELD_COOLDOWN, newShieldCooldown);
    }

    private void decrementShieldCooldown() {
        this.setShieldCooldown(Math.max(this.getShieldCooldown() - 1, 0));
    }

    public InteractionHand getShieldHand() {
        return this.isUsingShield() ? (this.isShieldMainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND) :
                null;
    }
}
