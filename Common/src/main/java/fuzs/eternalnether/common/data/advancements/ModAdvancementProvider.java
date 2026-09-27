package fuzs.eternalnether.common.data.advancements;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModEntityTypes;
import fuzs.eternalnether.common.init.ModItems;
import fuzs.eternalnether.common.init.ModStructures;
import fuzs.puzzleslib.common.api.data.v3.advancements.AbstractAdvancementProvider;
import fuzs.puzzleslib.common.api.data.v3.advancements.AdvancementToken;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.ChangeDimensionTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.advancements.triggers.StartRidingTrigger;
import net.minecraft.advancements.triggers.SummonedEntityTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModAdvancementProvider extends AbstractAdvancementProvider {
    public static final AdvancementToken ROOT_ADVANCEMENT = new AdvancementToken(EternalNether.id("root"));
    public static final AdvancementToken ACQUIRE_WITHER_WALTZ_ADVANCEMENT = new AdvancementToken(EternalNether.id(
            "acquire_wither_waltz"));
    public static final AdvancementToken CATACOMB_ADVANCEMENT = new AdvancementToken(EternalNether.id("catacomb"));
    public static final AdvancementToken CITADEL_ADVANCEMENT = new AdvancementToken(EternalNether.id("citadel"));
    public static final AdvancementToken EXPLORE_STRUCTURES_ADVANCEMENT = new AdvancementToken(EternalNether.id(
            "explore_structures"));
    public static final AdvancementToken PIGLIN_MANOR_ADVANCEMENT = new AdvancementToken(EternalNether.id("piglin_manor"));
    public static final AdvancementToken RESCUE_PIGLIN_PRISONER_ADVANCEMENT = new AdvancementToken(EternalNether.id(
            "rescue_piglin_prisoner"));
    public static final AdvancementToken RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT = new AdvancementToken(EternalNether.id(
            "ride_wither_skeleton_horse"));
    public static final AdvancementToken SUMMON_ENDERMAN_ADVANCEMENT = new AdvancementToken(EternalNether.id(
            "summon_enderman"));

    public ModAdvancementProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        HolderGetter<EntityType<?>> entityLookup = this.output.lookup(Registries.ENTITY_TYPE);
        HolderGetter<Structure> structureLookup = this.output.lookup(Registries.STRUCTURE);
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getItem(
                                        BlockSetVariant.CHISELED).value()),
                                ROOT_ADVANCEMENT.id())
                        .setBackground(EternalNether.id("block/soul_stone"))
                        .setType(AdvancementType.TASK)
                        .setHidden(false)
                        .build())
                .addCriterion("entered_nether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER))
                .save(this.output, ROOT_ADVANCEMENT.name());
        AdvancementHolder exploreStructures = Advancement.Builder.advancement()
                .display(display(getNetheriteBootsDisplayItem(this.output),
                                EXPLORE_STRUCTURES_ADVANCEMENT.id())
                        .setType(AdvancementType.CHALLENGE)
                        .build())
                .parent(root)
                .rewards(AdvancementRewards.Builder.experience(500))
                .addCriterion("in_catacomb",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structureLookup.getOrThrow(
                                ModStructures.CATACOMB_STRUCTURE))))
                .addCriterion("in_citadel",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structureLookup.getOrThrow(
                                ModStructures.CITADEL_STRUCTURE))))
                .addCriterion("in_piglin_manor",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structureLookup.getOrThrow(
                                ModStructures.PIGLIN_MANOR_STRUCTURE))))
                .save(this.output, EXPLORE_STRUCTURES_ADVANCEMENT.name());
        AdvancementHolder catacomb = Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(ModItems.WITHERED_DEBRIS.value()),
                        CATACOMB_ADVANCEMENT.id()).build())
                .parent(exploreStructures)
                .addCriterion("in_catacomb",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structureLookup.getOrThrow(
                                ModStructures.CATACOMB_STRUCTURE))))
                .save(this.output, CATACOMB_ADVANCEMENT.name());
        AdvancementHolder citadel = Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getItem(
                        BlockSetVariant.CHISELED).value()), CITADEL_ADVANCEMENT.id()).build())
                .parent(exploreStructures)
                .addCriterion("in_citadel",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structureLookup.getOrThrow(
                                ModStructures.CITADEL_STRUCTURE))))
                .save(this.output, CITADEL_ADVANCEMENT.name());
        AdvancementHolder piglinManor = Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(Items.CRIMSON_PLANKS), PIGLIN_MANOR_ADVANCEMENT.id()).build())
                .parent(exploreStructures)
                .addCriterion("in_piglin_manor",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(structureLookup.getOrThrow(
                                ModStructures.PIGLIN_MANOR_STRUCTURE))))
                .save(this.output, PIGLIN_MANOR_ADVANCEMENT.name());
        Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(ModItems.NETHERITE_BELL.value()),
                                RESCUE_PIGLIN_PRISONER_ADVANCEMENT.id())
                        .setType(AdvancementType.CHALLENGE)
                        .build())
                .parent(catacomb)
                .rewards(AdvancementRewards.Builder.experience(500))
                .addCriterion("rescue_prisoner",
                        SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity()
                                .of(entityLookup, ModEntityTypes.PIGLIN_PRISONER.value())))
                .save(this.output, RESCUE_PIGLIN_PRISONER_ADVANCEMENT.name());
        Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(Items.SADDLE),
                        RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT.id()).build())
                .parent(piglinManor)
                .addCriterion("ride_wither_skeleton_horse",
                        StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity()
                                .vehicle(EntityPredicate.Builder.entity()
                                        .of(entityLookup, ModEntityTypes.WITHER_SKELETON_HORSE.value())
                                        .passenger(EntityPredicate.Builder.entity()
                                                .of(entityLookup, EntityTypes.PLAYER)))))
                .save(this.output, RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT.name());
        Advancement.Builder.advancement()
                .display(display(new ItemStackTemplate(Items.ENDER_PEARL),
                        SUMMON_ENDERMAN_ADVANCEMENT.id()).build())
                .parent(citadel)
                .addCriterion("summon_enderman",
                        SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity()
                                .of(entityLookup, ModEntityTypes.WARPED_ENDERMAN.value())))
                .save(this.output, SUMMON_ENDERMAN_ADVANCEMENT.name());
    }

    private static ItemStackTemplate getNetheriteBootsDisplayItem(BootstrapContext<Advancement> output) {
        ArmorTrim armorTrim = new ArmorTrim(output.lookup(Registries.TRIM_MATERIAL).getOrThrow(TrimMaterials.GOLD),
                output.lookup(Registries.TRIM_PATTERN).getOrThrow(TrimPatterns.SENTRY));
        DataComponentPatch patch = DataComponentPatch.builder().set(DataComponents.TRIM, armorTrim).build();
        return new ItemStackTemplate(Items.NETHERITE_BOOTS, patch);
    }
}
