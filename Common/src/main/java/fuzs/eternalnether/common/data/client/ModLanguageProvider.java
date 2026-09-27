package fuzs.eternalnether.common.data.client;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.data.advancements.ModAdvancementProvider;
import fuzs.eternalnether.common.init.*;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.core.v1.ModContainer;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import net.minecraft.world.item.DyeColor;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.addCreativeModeTab(ModRegistry.CREATIVE_MODE_TAB, EternalNether.MOD_NAME);

        this.addBlock(ModBlocks.COBBLED_BLACKSTONE, "Cobbled Blackstone");
        this.add(ModBlocks.WITHERED_BLACKSTONE.value(), "Withered Blackstone");
        this.add(ModBlocks.WARPED_NETHER_BRICKS.value(), "Warped Nether Bricks");
        this.generateFor(this, ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY, "Withered Blackstone");
        this.generateFor(this, ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY, "Cracked Withered Blackstone");
        this.generateFor(this, ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY, "Warped Nether Brick");
        this.add(ModBlocks.WITHERED_BASALT.value(), "Withered Basalt");
        this.add(ModBlocks.WITHERED_COAL_BLOCK.value(), "Withered Coal Block");
        this.add(ModBlocks.WITHERED_QUARTZ_BLOCK.value(), "Withered Quartz Block");
        this.add(ModBlocks.WITHERED_DEBRIS.value(), "Withered Debris");
        this.add(ModBlocks.SOUL_STONE.value(), "Soul Stone");
        this.add(ModBlocks.WITHERED_BONE_BLOCK.value(), "Withered Bone Block");
        this.add(ModBlocks.NETHERITE_BELL.value(), "Netherite Bell");

        this.addSpawnEgg(ModItems.WEX_SPAWN_EGG.value(), "Wex");
        this.addSpawnEgg(ModItems.WARPED_ENDERMAN_SPAWN_EGG.value(), "Warped Enderman");
        this.addSpawnEgg(ModItems.PIGLIN_PRISONER_SPAWN_EGG.value(), "Piglin Prisoner");
        this.addSpawnEgg(ModItems.PIGLIN_HUNTER_SPAWN_EGG.value(), "Piglin Hunter");
        this.addSpawnEgg(ModItems.WRAITHER_SPAWN_EGG.value(), "Wraither");
        this.addSpawnEgg(ModItems.WITHER_SKELETON_KNIGHT_SPAWN_EGG.value(), "Wither Skeleton Knight");
        this.addSpawnEgg(ModItems.CORPOR_SPAWN_EGG.value(), "Corpor");
        this.addSpawnEgg(ModItems.WITHER_SKELETON_HORSE_SPAWN_EGG.value(), "Withered Skeleton Horse");
        this.add(ModItems.WITHER_WALTZ_JUKEBOX_SONG, "Izofar - Wither Waltz");
        this.add(ModItems.WITHER_WALTZ_MUSIC_DISC.value(), "Music Disc");
        this.add(ModItems.WARPED_ENDER_PEARL.value(), "Warped Ender Pearl");
        this.add(ModItems.WITHERED_BONE.value(), "Withered Bone");
        this.add(ModItems.WITHERED_BONE_MEAL.value(), "Withered Bone Meal");
        this.add(ModItems.GILDED_NETHERITE_SHIELD.value(), "Gilded Netherite Shield");
        for (DyeColor dyeColor : DyeColor.values()) {
            this.add(ModItems.GILDED_NETHERITE_SHIELD.value(),
                    dyeColor.getName(),
                    ModContainer.getCapitalizedString(dyeColor.getName()) + " Gilded Netherite Shield");
        }

        this.add(ModItems.CUTLASS.value(), "Cutlass");

        this.add(ModEntityTypes.WEX.value(), "Wex");
        this.add(ModEntityTypes.WARPED_ENDERMAN.value(), "Warped Enderman");
        this.add(ModEntityTypes.PIGLIN_PRISONER.value(), "Piglin Prisoner");
        this.add(ModEntityTypes.PIGLIN_HUNTER.value(), "Piglin Hunter");
        this.add(ModEntityTypes.WRAITHER.value(), "Wraither");
        this.add(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(), "Wither Skeleton Knight");
        this.add(ModEntityTypes.CORPOR.value(), "Corpor");
        this.add(ModEntityTypes.WITHER_SKELETON_HORSE.value(), "Withered Skeleton Horse");
        this.add(ModEntityTypes.WARPED_ENDER_PEARL.value(), "Warped Ender Pearl");

        this.add(ModSoundEvents.ITEM_SWORD_BLOCK_SOUND_EVENT.value(), "Sword blocks");
        this.add(ModSoundEvents.WEX_CHARGE.value(), "Wex shrieks");
        this.add(ModSoundEvents.WEX_DEATH.value(), "Wex dies");
        this.add(ModSoundEvents.WEX_HURT.value(), "Wex hurts");
        this.add(ModSoundEvents.WEX_AMBIENT.value(), "Wex wexes");
        this.add(ModSoundEvents.WARPED_ENDERMAN_DEATH.value(), "Warped Enderman dies");
        this.add(ModSoundEvents.WARPED_ENDERMAN_HURT.value(), "Warped Enderman hurts");
        this.add(ModSoundEvents.WARPED_ENDERMAN_AMBIENT.value(), "Warped Enderman vwoops");
        this.add(ModSoundEvents.WARPED_ENDERMAN_TELEPORT.value(), "Warped Enderman teleports");
        this.add(ModSoundEvents.WARPED_ENDERMAN_SCREAM.value(), "Warped Enderman screams");
        this.add(ModSoundEvents.WARPED_ENDERMAN_STARE.value(), "Warped Enderman cries out");

        this.add(ModAdvancementProvider.ROOT_ADVANCEMENT.title(), EternalNether.MOD_NAME);
        this.add(ModAdvancementProvider.ROOT_ADVANCEMENT.description(), "Explore the Nether for new structures!");
        this.add(ModAdvancementProvider.ACQUIRE_WITHER_WALTZ_ADVANCEMENT.title(), "Here I Waltz");
        this.add(ModAdvancementProvider.ACQUIRE_WITHER_WALTZ_ADVANCEMENT.description(),
                "Acquire the Wither Waltz Music Disc");
        this.add(ModAdvancementProvider.CATACOMB_ADVANCEMENT.title(), "To Wither Or Not To Wither");
        this.add(ModAdvancementProvider.CATACOMB_ADVANCEMENT.description(), "Locate a Catacomb structure");
        this.add(ModAdvancementProvider.CITADEL_ADVANCEMENT.title(), "The Warping Citadel");
        this.add(ModAdvancementProvider.CITADEL_ADVANCEMENT.description(), "Locate a Citadel structure");
        this.add(ModAdvancementProvider.EXPLORE_STRUCTURES_ADVANCEMENT.title(), "Hotter Tourist Destinations");
        this.add(ModAdvancementProvider.EXPLORE_STRUCTURES_ADVANCEMENT.description(),
                "Locate all " + EternalNether.MOD_NAME + " structures");
        this.add(ModAdvancementProvider.PIGLIN_MANOR_ADVANCEMENT.title(), "Mind Your Manors");
        this.add(ModAdvancementProvider.PIGLIN_MANOR_ADVANCEMENT.description(), "Locate a Piglin Manor structure");
        this.add(ModAdvancementProvider.RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT.title(), "Dark Horse");
        this.add(ModAdvancementProvider.RIDE_WITHER_SKELETON_HORSE_ADVANCEMENT.description(),
                "Ride a Wither Skeleton Horse");
        this.add(ModAdvancementProvider.SUMMON_ENDERMAN_ADVANCEMENT.title(), "A Little Off The Top");
        this.add(ModAdvancementProvider.SUMMON_ENDERMAN_ADVANCEMENT.description(),
                "Trim the Warp from a Warped Enderman");
        this.add(ModAdvancementProvider.RESCUE_PIGLIN_PRISONER_ADVANCEMENT.title(), "Saving Private Swine");
        this.add(ModAdvancementProvider.RESCUE_PIGLIN_PRISONER_ADVANCEMENT.description(),
                "Rescue a Piglin Prisoner");
    }
}
