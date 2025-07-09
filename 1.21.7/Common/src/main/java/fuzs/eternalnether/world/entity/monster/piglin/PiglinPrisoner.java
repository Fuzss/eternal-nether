package fuzs.eternalnether.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import fuzs.eternalnether.init.ModFeatures;
import fuzs.eternalnether.init.ModItems;
import fuzs.eternalnether.init.ModSensorTypes;
import fuzs.puzzleslib.api.util.v1.EntityHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;

/**
 * TODO this is mostly a copy of {@link net.minecraft.world.entity.monster.piglin.Piglin}, instead extend that class
 */
public class PiglinPrisoner extends AbstractPiglin implements CrossbowAttackMob, InventoryCarrier {
    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(
            PiglinPrisoner.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_DANCING = SynchedEntityData.defineId(PiglinPrisoner.class,
            EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(
            PiglinPrisoner.class,
            EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinPrisoner>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.HURT_BY,
            ModSensorTypes.PIGLIN_PRISONER_SPECIFIC_SENSOR_TYPE.value());
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.DOORS_TO_CLOSE,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
            MemoryModuleType.NEARBY_ADULT_PIGLINS,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.ADMIRING_ITEM,
            MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
            MemoryModuleType.ADMIRING_DISABLED,
            MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
            MemoryModuleType.CELEBRATE_LOCATION,
            MemoryModuleType.DANCING,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED,
            MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
            MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.IS_TEMPTED);
    protected static final int RESCUE_TIME = 75;

    private final SimpleContainer inventory = new SimpleContainer(8);
    protected int timeBeingRescued;
    protected boolean isBeingRescued;

    public PiglinPrisoner(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public static boolean checkPiglinSpawnRules(EntityType<? extends AbstractPiglin> piglin, LevelAccessor level, EntitySpawnReason entitySpawnReason, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_CHARGING_CROSSBOW, false);
        builder.define(DATA_IS_DANCING, false);
        builder.define(DATA_OWNERUUID_ID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putInt("TimeBeingRescued", this.timeBeingRescued);
        valueOutput.putBoolean("IsBeingRescued", this.isBeingRescued);
        EntityReference.store(this.getTempterReference(), valueOutput, "Tempter");
        this.writeInventoryToTag(valueOutput);
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.timeBeingRescued = valueInput.getIntOr("TimeBeingRescued", 0);
        this.isBeingRescued = valueInput.getBooleanOr("IsBeingRescued", false);
        EntityReference<LivingEntity> entityReference = EntityReference.read(valueInput, "Tempter");
        if (entityReference != null) {
            this.entityData.set(DATA_OWNERUUID_ID, Optional.of(entityReference));
            PiglinPrisonerAi.reloadAllegiance(this, this.getTempter());
        }
        this.readInventoryFromTag(valueInput);
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        super.customServerAiStep(serverLevel);
        Profiler.get().push("piglinBrain");
        this.getBrain().tick(serverLevel, this);
        Profiler.get().pop();
        PiglinPrisonerAi.updateActivity(this);
        if (this.isBeingRescued) {
            this.timeBeingRescued++;
        } else {
            this.timeBeingRescued = 0;
        }

        if (this.timeBeingRescued > RESCUE_TIME) {
            this.playConvertedSound();
            this.finishRescue(serverLevel);
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        this.inventory.removeAllItems().forEach(itemStackx -> this.spawnAtLocation(level, itemStackx));
    }

    public void addToInventory(ItemStack stack) {
        this.inventory.addItem(stack);
    }

    public boolean canAddToInventory(ItemStack stack) {
        return this.inventory.canAddItem(stack);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToPlayer) {
        return false;
    }

    @Override
    protected Brain.Provider<PiglinPrisoner> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return PiglinPrisonerAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<PiglinPrisoner> getBrain() {
        return (Brain<PiglinPrisoner>) super.getBrain();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        InteractionResult interactionresult = super.mobInteract(player, interactionHand);
        if (interactionresult.consumesAction()) {
            return interactionresult;
        } else if (this.level() instanceof ServerLevel serverLevel) {
            return PiglinPrisonerAi.mobInteract(serverLevel, this, player, interactionHand);
        } else {
            boolean flag = PiglinPrisonerAi.canAdmire(this, player.getItemInHand(interactionHand))
                    && this.getArmPose() != PiglinArmPose.ADMIRING_ITEM;
            return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
    }

    @Override
    public int getBaseExperienceReward(ServerLevel serverLevel) {
        return this.xpReward;
    }

    @Override
    protected void finishConversion(ServerLevel serverLevel) {
        PiglinPrisonerAi.cancelAdmiring(serverLevel, this);
        this.inventory.removeAllItems().forEach(itemStack -> this.spawnAtLocation(serverLevel, itemStack));
        super.finishConversion(serverLevel);
    }

    public boolean isChargingCrossbow() {
        return this.entityData.get(DATA_IS_CHARGING_CROSSBOW);
    }

    @Override
    public void setChargingCrossbow(boolean bool) {
        this.entityData.set(DATA_IS_CHARGING_CROSSBOW, bool);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Override
    public void performRangedAttack(LivingEntity entity, float vel) {
        this.performCrossbowAttack(this, 1.6F);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem item) {
        return item == Items.CROSSBOW;
    }

    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    protected boolean canHunt() {
        return false;
    }

    @Override
    public PiglinArmPose getArmPose() {
        if (this.isDancing()) {
            return PiglinArmPose.DANCING;
        } else if (PiglinPrisonerAi.isLovedItem(this.getOffhandItem())) {
            return PiglinArmPose.ADMIRING_ITEM;
        } else if (this.isAggressive() && this.isHoldingMeleeWeapon()) {
            return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON;
        } else if (this.isChargingCrossbow()) {
            return PiglinArmPose.CROSSBOW_CHARGE;
        } else {
            return this.isAggressive() && this.isHolding(is -> is.getItem() instanceof CrossbowItem) ?
                    PiglinArmPose.CROSSBOW_HOLD : PiglinArmPose.DEFAULT;
        }
    }

    public boolean isDancing() {
        return this.entityData.get(DATA_IS_DANCING);
    }

    public void setDancing(boolean isDancing) {
        this.entityData.set(DATA_IS_DANCING, isDancing);
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float damageAmount) {
        if (super.hurtServer(serverLevel, damageSource, damageAmount)) {
            if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
                PiglinPrisonerAi.wasHurtBy(serverLevel, this, livingEntity);
            }
            return true;
        } else {
            return false;
        }
    }

    public void holdInOffHand(ItemStack itemStack) {
        if (EntityHelper.isPiglinCurrency(itemStack)) {
            this.setItemSlot(EquipmentSlot.OFFHAND, itemStack);
            this.setGuaranteedDrop(EquipmentSlot.OFFHAND);
        } else {
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.OFFHAND, itemStack);
        }
    }

    @Override
    public boolean wantsToPickUp(ServerLevel serverLevel, ItemStack itemstack) {
        return EntityHelper.isMobGriefingAllowed(serverLevel, this) && this.canPickUpLoot()
                && PiglinPrisonerAi.wantsToPickup(this, itemstack);
    }

    protected boolean canReplaceCurrentItem(ItemStack candidate) {
        EquipmentSlot equipmentSlot = this.getEquipmentSlotForItem(candidate);
        ItemStack itemStack = this.getItemBySlot(equipmentSlot);
        return this.canReplaceCurrentItem(candidate, itemStack, equipmentSlot);
    }

    @Override
    protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack currentItem, EquipmentSlot slot) {
        if (EnchantmentHelper.has(currentItem, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
            return false;
        } else {
            TagKey<Item> tagKey = this.getPreferredWeaponType();
            boolean bl = PiglinPrisonerAi.isLovedItem(newItem) || tagKey != null && newItem.is(tagKey);
            boolean bl2 = PiglinPrisonerAi.isLovedItem(currentItem) || tagKey != null && currentItem.is(tagKey);
            if (bl && !bl2) {
                return true;
            } else {
                return (bl || !bl2) && super.canReplaceCurrentItem(newItem, currentItem, slot);
            }
        }
    }

    @Override
    protected void pickUpItem(ServerLevel serverLevel, ItemEntity itemEntity) {
        this.onItemPickup(itemEntity);
        PiglinPrisonerAi.pickUpItem(serverLevel, this, itemEntity);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.level().isClientSide ? null : PiglinPrisonerAi.getSoundForCurrentActivity(this).orElse(null);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PIGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIGLIN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockpos, BlockState blockstate) {
        this.playSound(SoundEvents.PIGLIN_STEP, 0.15F, 1.0F);
    }

    @Override
    public void playSound(SoundEvent sound) {
        this.playSound(sound, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    protected void playConvertedSound() {
        this.playSound(SoundEvents.PIGLIN_CONVERTED_TO_ZOMBIFIED);
    }

    @Nullable
    public Player getTempter() {
        return (Player) EntityReference.get(this.getTempterReference(), this.level(), LivingEntity.class);
    }

    @Nullable
    public EntityReference<LivingEntity> getTempterReference() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setTempter(@Nullable LivingEntity owner) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(owner).map(EntityReference::new));
    }

    public void setTempterReference(@Nullable EntityReference<LivingEntity> owner) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(owner));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 18) {
            for (int i = 0; i < 7; i++) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.level()
                        .addParticle(ParticleTypes.HEART,
                                this.getRandomX(1.0),
                                this.getRandomY() + 0.5,
                                this.getRandomZ(1.0),
                                d,
                                e,
                                f);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    public void rescue() {
        PiglinPrisonerAi.startDancing(this);
        PiglinPrisonerAi.broadcastBeingRescued(this);
        CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayer) this.getTempter(), this);
        this.isBeingRescued = true;
    }

    protected void finishRescue(ServerLevel serverLevel) {
        PiglinPrisonerAi.throwItems(this, Collections.singletonList(new ItemStack(ModItems.GILDED_NETHERITE_SHIELD)));
        Optional<Holder<? extends EntityType<? extends Mob>>> optional = ModFeatures.PIGLIN_PRISONER_CONVERSIONS.getRandom(
                this.random);
        if (optional.isPresent()) {
            this.convertTo(optional.get().value(), ConversionParams.single(this, false, false), mob -> {
                mob.finalizeSpawn(serverLevel,
                        serverLevel.getCurrentDifficultyAt(mob.blockPosition()),
                        EntitySpawnReason.CONVERSION,
                        null);
                mob.setPersistenceRequired();
                mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200));
            });
        }
    }
}