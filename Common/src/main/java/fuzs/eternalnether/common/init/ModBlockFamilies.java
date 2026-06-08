package fuzs.eternalnether.common.init;

import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.data.BlockFamily;
import net.minecraft.util.Util;

import java.util.stream.Stream;

/**
 * TODO remove the manual calls to {@link BlockFamily.Builder#generateStonecutterRecipe()}, they are no longer necessary.
 * <p>
 * TODO Also, the chiseled variant now supports properly overriding the base name.
 */
public final class ModBlockFamilies {
    public static final BlockSetFamily WITHERED_BLACKSTONE_FAMILY = BlockSetFamily.any(ModRegistry.REGISTRIES,
                    ModBlocks.WITHERED_BLACKSTONE,
                    "withered_blackstone")
            .generateFor(BlockSetVariant.CHISELED)
            .generateFor(BlockSetVariant.CRACKED)
            .configureBlockFamily(BlockFamily.Builder::generateStonecutterRecipe);
    public static final BlockSetFamily CRACKED_WITHERED_BLACKSTONE_FAMILY = BlockSetFamily.any(ModRegistry.REGISTRIES,
            WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED),
            "cracked_withered_blackstone").configureBlockFamily(BlockFamily.Builder::generateStonecutterRecipe);
    public static final BlockSetFamily WARPED_NETHER_BRICKS_FAMILY = Util.make(BlockSetFamily.any(ModRegistry.REGISTRIES,
                    ModBlocks.WARPED_NETHER_BRICKS,
                    "warped_nether_brick").configureBlockFamily(BlockFamily.Builder::generateStonecutterRecipe),
            (BlockSetFamily.Writable family) -> {
                BlockSetFamily.Context context = (BlockSetFamily.Context) family;
                context.registerBlock(BlockSetVariant.CHISELED, ModBlocks.CHISELED_WARPED_NETHER_BRICKS);
                context.registerItem(BlockSetVariant.CHISELED, ModItems.CHISELED_WARPED_NETHER_BRICKS);
            });

    public static void bootstrap() {
        // NO-OP
    }

    public static Stream<BlockSetFamily> getAllBlockSetFamilies() {
        return Stream.of(WITHERED_BLACKSTONE_FAMILY, CRACKED_WITHERED_BLACKSTONE_FAMILY, WARPED_NETHER_BRICKS_FAMILY);
    }
}
