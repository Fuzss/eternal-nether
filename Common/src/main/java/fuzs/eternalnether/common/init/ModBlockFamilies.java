package fuzs.eternalnether.common.init;

import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;

import java.util.stream.Stream;

public final class ModBlockFamilies {
    public static final BlockSetFamily WITHERED_BLACKSTONE_FAMILY = BlockSetFamily.stone(ModRegistry.REGISTRIES,
            ModBlocks.WITHERED_BLACKSTONE,
            "withered_blackstone").generateFor(BlockSetVariant.CHISELED).generateFor(BlockSetVariant.CRACKED);
    public static final BlockSetFamily CRACKED_WITHERED_BLACKSTONE_FAMILY = BlockSetFamily.stone(ModRegistry.REGISTRIES,
            WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED),
            "cracked_withered_blackstone");
    public static final BlockSetFamily WARPED_NETHER_BRICKS_FAMILY = BlockSetFamily.stone(ModRegistry.REGISTRIES,
            ModBlocks.WARPED_NETHER_BRICKS,
            "warped_nether_brick").generateFor(BlockSetVariant.CHISELED, "chiseled_warped_nether_bricks");

    public static void bootstrap() {
        // NO-OP
    }

    public static Stream<BlockSetFamily> getAllBlockSetFamilies() {
        return Stream.of(WITHERED_BLACKSTONE_FAMILY, CRACKED_WITHERED_BLACKSTONE_FAMILY, WARPED_NETHER_BRICKS_FAMILY);
    }
}
