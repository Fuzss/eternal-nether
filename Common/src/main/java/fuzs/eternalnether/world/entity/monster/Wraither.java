package fuzs.eternalnether.world.entity.monster;

import fuzs.eternalnether.init.ModEntityTypes;
import fuzs.eternalnether.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Wraither extends WitherSkeleton {
    private static final EntityDataAccessor<Boolean> DATA_IS_POSSESSED = SynchedEntityData.defineId(Wraither.class,
            EntityDataSerializers.BOOLEAN);

    public Wraither(EntityType<? extends WitherSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, 25.0D);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Possessed", this.isPossessed());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setPossessed(tag.getBoolean("Possessed"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_POSSESSED, true);
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.CUTLASS));
    }

    public boolean isPossessed() {
        return this.entityData.get(DATA_IS_POSSESSED);
    }

    private void setPossessed(boolean possessed) {
        this.entityData.set(DATA_IS_POSSESSED, possessed);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean isHurt = super.hurt(source, amount);
        if (this.isPossessed() && this.getHealth() < 10.0F) {
            this.dispossess();
        }
        return isHurt;
    }

    private void dispossess() {
        this.setPossessed(false);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        if (this.level() instanceof ServerLevel serverLevel) {
            Wex wex = ModEntityTypes.WEX.value().create(serverLevel);
            if (wex != null) {
                wex.moveTo(this.blockPosition().above(), this.yBodyRot, this.xRotO);
                wex.finalizeSpawn(serverLevel,
                        serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                        MobSpawnType.CONVERSION,
                        null);
                wex.setPersistenceRequired();
                serverLevel.addFreshEntity(wex);
            }
        }
    }
}
