package fuzs.eternalnether.common.data.client;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.data.ModAdvancementProvider;
import fuzs.eternalnether.common.init.*;
import fuzs.puzzleslib.common.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.core.v1.ModContainer;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import net.minecraft.world.item.DyeColor;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.addCreativeModeTab(ModRegistry.CREATIVE_MODE_TAB, EternalNether.MOD_NAME);

        builder.addBlock(ModBlocks.COBBLED_BLACKSTONE, "Cobbled Blackstone");
        builder.add(ModBlocks.WITHERED_BLACKSTONE.value(), "Withered Blackstone");
        builder.add(ModBlocks.WARPED_NETHER_BRICKS.value(), "Warped Nether Bricks");
        this.generateFor(builder, ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY, "Withered Blackstone");
        this.generateFor(builder, ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY, "Cracked Withered Blackstone");
        this.generateFor(builder, ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY, "Warped Nether Brick");
        builder.add(ModBlocks.WITHERED_BASALT.value(), "Withered Basalt");
        builder.add(ModBlocks.WITHERED_COAL_BLOCK.value(), "Withered Coal Block");
        builder.add(ModBlocks.WITHERED_QUARTZ_BLOCK.value(), "Withered Quartz Block");
        builder.add(ModBlocks.WITHERED_DEBRIS.value(), "Withered Debris");
        builder.add(ModBlocks.SOUL_STONE.value(), "Soul Stone");
        builder.add(ModBlocks.WITHERED_BONE_BLOCK.value(), "Withered Bone Block");
        builder.add(ModBlocks.NETHERITE_BELL.value(), "Netherite Bell");

        builder.addSpawnEgg(ModItems.WEX_SPAWN_EGG.value(), "Wex");
        builder.addSpawnEgg(ModItems.WARPED_ENDERMAN_SPAWN_EGG.value(), "Warped Enderman");
        builder.addSpawnEgg(ModItems.PIGLIN_PRISONER_SPAWN_EGG.value(), "Piglin Prisoner");
        builder.addSpawnEgg(ModItems.PIGLIN_HUNTER_SPAWN_EGG.value(), "Piglin Hunter");
        builder.addSpawnEgg(ModItems.WRAITHER_SPAWN_EGG.value(), "Wraither");
        builder.addSpawnEgg(ModItems.WITHER_SKELETON_KNIGHT_SPAWN_EGG.value(), "Wither Skeleton Knight");
        builder.addSpawnEgg(ModItems.CORPOR_SPAWN_EGG.value(), "Corpor");
        builder.addSpawnEgg(ModItems.WITHER_SKELETON_HORSE_SPAWN_EGG.value(), "Withered Skeleton Horse");
        builder.add(ModItems.WITHER_WALTZ_JUKEBOX_SONG, "Izofar - Wither Waltz");
        builder.add(ModItems.WITHER_WALTZ_MUSIC_DISC.value(), "Music Disc");
        builder.add(ModItems.WARPED_ENDER_PEARL.value(), "Warped Ender Pearl");
        builder.add(ModItems.WITHERED_BONE.value(), "Withered Bone");
        builder.add(ModItems.WITHERED_BONE_MEAL.value(), "Withered Bone Meal");
        builder.add(ModItems.GILDED_NETHERITE_SHIELD.value(), "Gilded Netherite Shield");
        for (DyeColor dyeColor : DyeColor.values()) {
            builder.add(ModItems.GILDED_NETHERITE_SHIELD.value(),
                    dyeColor.getName(),
                    ModContainer.getCapitalizedString(dyeColor.getName()) + " Gilded Netherite Shield");
        }

        builder.add(ModItems.CUTLASS.value(), "Cutlass");

        builder.add(ModEntityTypes.WEX.value(), "Wex");
        builder.add(ModEntityTypes.WARPED_ENDERMAN.value(), "Warped Enderman");
        builder.add(ModEntityTypes.PIGLIN_PRISONER.value(), "Piglin Prisoner");
        builder.add(ModEntityTypes.PIGLIN_HUNTER.value(), "Piglin Hunter");
        builder.add(ModEntityTypes.WRAITHER.value(), "Wraither");
        builder.add(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(), "Wither Skeleton Knight");
        builder.add(ModEntityTypes.CORPOR.value(), "Corpor");
        builder.add(ModEntityTypes.WITHER_SKELETON_HORSE.value(), "Withered Skeleton Horse");
        builder.add(ModEntityTypes.WARPED_ENDER_PEARL.value(), "Warped Ender Pearl");

        builder.add(ModSoundEvents.ITEM_SWORD_BLOCK_SOUND_EVENT.value(), "Sword blocks");
        builder.add(ModSoundEvents.WEX_CHARGE.value(), "Wex shrieks");
        builder.add(ModSoundEvents.WEX_DEATH.value(), "Wex dies");
        builder.add(ModSoundEvents.WEX_HURT.value(), "Wex hurts");
        builder.add(ModSoundEvents.WEX_AMBIENT.value(), "Wex wexes");
        builder.add(ModSoundEvents.WARPED_ENDERMAN_DEATH.value(), "Warped Enderman dies");
        builder.add(ModSoundEvents.WARPED_ENDERMAN_HURT.value(), "Warped Enderman hurts");
        builder.add(ModSoundEvents.WARPED_ENDERMAN_AMBIENT.value(), "Warped Enderman vwoops");
        builder.add(ModSoundEvents.WARPED_ENDERMAN_TELEPORT.value(), "Warped Enderman teleports");
        builder.add(ModSoundEvents.WARPED_ENDERMAN_SCREAM.value(), "Warped Enderman screams");
        builder.add(ModSoundEvents.WARPED_ENDERMAN_STARE.value(), "Warped Enderman cries out");

        builder.add(ModAdvancementProvider.ROOT_ADVANCEMENT.title(), EternalNether.MOD_NAME);
        builder.add(ModAdvancementProvider.ROOT_ADVANCEMENT.description(), "Explore the Nether for new structures!");
        builder.add(ModAdvancementProvider.ACQUIRE_WITHER_WALTZ_ADVANCEMENT.title(), "Here I Waltz");
        builder.add(ModAdvancementProvider.ACQUIRE_WITHER_WALTZ_ADVANCEMENT.description(),
                "Acquire the Wither Waltz Music Disc");
        builder.add(ModAdvancementProvider.CATACOMB_ADVANCEMENT.title(), "To Wither Or Not To Wither");
        builder.add(ModAdvancementProvider.CATACOMB_ADVANCEMENT.description(), "Locate a Catacomb structure");
        builder.add(ModAdvancementProvider.CITADEL_ADVANCEMENT.title(), "The Warping Citadel");
        builder.add(ModAdvancementProvider.CITADEL_ADVANCEMENT.description(), "Locate a Citadel structure");
        builder.add(ModAdvancementProvider.EXPLORE_STRUCTURES_ADVANCEMENT.title(), "Hotter Tourist Destinations");
        builder.add(ModAdvancementProvider.EXPLORE_STRUCTURES_ADVANCEMENT.description(),
                "Locate all " + EternalNether.MOD_NAME + " structures");
        builder.add(ModAdvancementProvider.PIGLIN_MANOR_ADVANCEMENT.title(), "Mind Your Manors");
        builder.add(ModAdvancementProvider.PIGLIN_MANOR_ADVANCEMENT.description(), "Locate a Piglin Manor structure");
        builder.add(ModAdvancementProvider.RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT.title(), "Dark Horse");
        builder.add(ModAdvancementProvider.RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT.description(),
                "Ride a Wither Skeleton Horse");
        builder.add(ModAdvancementProvider.SUMMON_ENDERMAN_ADVANCEMENT.title(), "A Little Off The Top");
        builder.add(ModAdvancementProvider.SUMMON_ENDERMAN_ADVANCEMENT.description(),
                "Trim the Warp from a Warped Enderman");
        builder.add(ModAdvancementProvider.RESCUE_PIGLIN_PRISONER_ADVANCEMENT.title(), "Saving Private Swine");
        builder.add(ModAdvancementProvider.RESCUE_PIGLIN_PRISONER_ADVANCEMENT.description(),
                "Rescue a Piglin Prisoner");
    }
}
