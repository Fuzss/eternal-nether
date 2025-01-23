package fuzs.eternalnether.init;

import fuzs.eternalnether.world.item.CutlassItem;
import fuzs.eternalnether.world.item.UnrepairableShieldItem;
import fuzs.eternalnether.world.item.WarpedEnderpearlItem;
import fuzs.eternalnether.world.item.WitheredBoneMealItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

public final class ModItems {
    public static final Holder.Reference<Item> PIGLIN_PRISONER_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.PIGLIN_PRISONER,
            0XC79E88,
            0XF9F3A4);
    public static final Holder.Reference<Item> PIGLIN_HUNTER_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.PIGLIN_HUNTER,
            0XBA6645,
            0XF9F3A4);
    public static final Holder.Reference<Item> WEX_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.WEX,
            0X7198C8,
            0X2B4667);
    public static final Holder.Reference<Item> WARPED_ENDERMAN_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.WARPED_ENDERMAN,
            0X0E8281,
            0X000000);
    public static final Holder.Reference<Item> WRAITHER_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.WRAITHER,
            0X273333,
            0X474D4D);
    public static final Holder.Reference<Item> CORPOR_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.CORPOR,
            0X141414,
            0X4A5757);
    public static final Holder.Reference<Item> WITHER_SKELETON_KNIGHT_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.WITHER_SKELETON_KNIGHT,
            0X242424,
            0X4E5252);
    public static final Holder.Reference<Item> WITHER_SKELETON_HORSE_SPAWN_EGG = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.WITHER_SKELETON_HORSE,
            0X242424,
            0X4D4747);

    public static final Holder.Reference<Item> COBBLED_BLACKSTONE = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.COBBLED_BLACKSTONE);

    public static final Holder.Reference<Item> WITHERED_BLACKSTONE = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.WITHERED_BLACKSTONE);
    public static final Holder.Reference<Item> WITHERED_BLACKSTONE_STAIRS = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WITHERED_BLACKSTONE_STAIRS);
    public static final Holder.Reference<Item> WITHERED_BLACKSTONE_SLAB = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WITHERED_BLACKSTONE_SLAB);
    public static final Holder.Reference<Item> WITHERED_BLACKSTONE_WALL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WITHERED_BLACKSTONE_WALL);

    public static final Holder.Reference<Item> CRACKED_WITHERED_BLACKSTONE = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CRACKED_WITHERED_BLACKSTONE);
    public static final Holder.Reference<Item> CRACKED_WITHERED_BLACKSTONE_STAIRS = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CRACKED_WITHERED_BLACKSTONE_STAIRS);
    public static final Holder.Reference<Item> CRACKED_WITHERED_BLACKSTONE_SLAB = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CRACKED_WITHERED_BLACKSTONE_SLAB);
    public static final Holder.Reference<Item> CRACKED_WITHERED_BLACKSTONE_WALL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CRACKED_WITHERED_BLACKSTONE_WALL);
    public static final Holder.Reference<Item> CHISELED_WITHERED_BLACKSTONE = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CHISELED_WITHERED_BLACKSTONE);

    public static final Holder.Reference<Item> WITHERED_BASALT = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.WITHERED_BASALT);
    public static final Holder.Reference<Item> WITHERED_COAL_BLACK = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.WITHERED_COAL_BLOCK);
    public static final Holder.Reference<Item> WITHERED_QUARTZ_BLOCK = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WITHERED_QUARTZ_BLOCK);

    public static final Holder.Reference<Item> WITHERED_DEBRIS = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.WITHERED_DEBRIS);
    public static final Holder.Reference<Item> SOUL_STONE = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.SOUL_STONE);
    public static final Holder.Reference<Item> WITHERED_BONE_BLOCK = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.WITHERED_BONE_BLOCK);

    public static final Holder.Reference<Item> WARPED_NETHER_BRICKS = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.WARPED_NETHER_BRICKS);
    public static final Holder.Reference<Item> CHISELED_WARPED_NETHER_BRICKS = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CHISELED_WARPED_NETHER_BRICKS);
    public static final Holder.Reference<Item> WARPED_NETHER_BRICK_STAIRS = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WARPED_NETHER_BRICK_STAIRS);
    public static final Holder.Reference<Item> WARPED_NETHER_BRICK_SLAB = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WARPED_NETHER_BRICK_SLAB);
    public static final Holder.Reference<Item> WARPED_NETHER_BRICK_WALL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.WARPED_NETHER_BRICK_WALL);

    public static final ResourceKey<JukeboxSong> WITHER_WALTZ_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "wither_waltz");
    public static final Holder.Reference<Item> WITHER_WALTZ_MUSIC_DISC = ModRegistry.REGISTRIES.registerItem(
            "wither_waltz_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(WITHER_WALTZ_JUKEBOX_SONG)));
    public static final Holder.Reference<Item> WARPED_ENDER_PEARL = ModRegistry.REGISTRIES.registerItem(
            "warped_ender_pearl",
            () -> new WarpedEnderpearlItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
    public static final Holder.Reference<Item> WITHERED_BONE = ModRegistry.REGISTRIES.registerItem("withered_bone",
            () -> new Item(new Item.Properties()));
    public static final Holder.Reference<Item> WITHERED_BONE_MEAL = ModRegistry.REGISTRIES.registerItem(
            "withered_bone_meal",
            () -> new WitheredBoneMealItem(new Item.Properties()));

    public static final Holder.Reference<Item> NETHERITE_BELL = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.NETHERITE_BELL,
            () -> new Item.Properties().rarity(Rarity.EPIC).fireResistant());
    public static final Holder.Reference<Item> GILDED_NETHERITE_SHIELD = ModRegistry.REGISTRIES.registerItem(
            "gilded_netherite_shield",
            () -> new UnrepairableShieldItem(new Item.Properties().durability(1512)
                    .rarity(Rarity.RARE)
                    .fireResistant()));
    public static final Holder.Reference<Item> CUTLASS = ModRegistry.REGISTRIES.registerItem("cutlass",
            () -> new CutlassItem(new Item.Properties().durability(312)
                    .component(DataComponents.TOOL, CutlassItem.createToolProperties())
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 3, -1.6F))));

    public static void boostrap() {
        // NO-OP
    }
}
