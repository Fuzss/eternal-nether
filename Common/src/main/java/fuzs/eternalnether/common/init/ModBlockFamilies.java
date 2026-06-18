package fuzs.eternalnether.common.init;

import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.util.Util;

import java.util.stream.Stream;

public final class ModBlockFamilies {
    public static final BlockSetFamily WITHERED_BLACKSTONE_FAMILY = BlockSetFamily.any(ModRegistry.REGISTRIES,
            ModBlocks.WITHERED_BLACKSTONE,
            "withered_blackstone").generateFor(BlockSetVariant.CHISELED).generateFor(BlockSetVariant.CRACKED);
    public static final BlockSetFamily CRACKED_WITHERED_BLACKSTONE_FAMILY = BlockSetFamily.any(ModRegistry.REGISTRIES,
            WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED),
            "cracked_withered_blackstone");
    public static final BlockSetFamily WARPED_NETHER_BRICKS_FAMILY = Util.make(BlockSetFamily.any(ModRegistry.REGISTRIES,
            ModBlocks.WARPED_NETHER_BRICKS,
            "warped_nether_brick"), (BlockSetFamily.Writable context) -> {
        // TODO call this directly on the builder without accessing internals
        BlockSetVariant.CHISELED.generateFor((BlockSetFamily.Context) context, "chiseled_warped_nether_bricks");
    });

    public static void bootstrap() {
        // NO-OP
    }

    public static Stream<BlockSetFamily> getAllBlockSetFamilies() {
        return Stream.of(WITHERED_BLACKSTONE_FAMILY, CRACKED_WITHERED_BLACKSTONE_FAMILY, WARPED_NETHER_BRICKS_FAMILY);
    }
}
