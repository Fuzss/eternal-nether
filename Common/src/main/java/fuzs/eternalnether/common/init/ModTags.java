package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.EternalNether;
import fuzs.puzzleslib.common.api.init.v3.tags.TagFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    static final TagFactory TAGS = TagFactory.make(EternalNether.MOD_ID);

    public static class Blocks {
        public static final TagKey<Block> WITHERED = register("withered");
        public static final TagKey<Block> BASALT_DELTA_CANNOT_REPLACE = register("basalt_delta_cannot_replace");

        private static TagKey<Block> register(String name) {
            return TAGS.registerBlockTag(name);
        }
    }

    public static class Items {
        public static final TagKey<Item> SHEAR_TOOLS = TagFactory.COMMON.registerItemTag("tools/shear");
        /**
         * @see net.minecraft.tags.ItemTags#PIGLIN_SAFE_ARMOR
         */
        public static final TagKey<Item> PIGLIN_BRUTE_SAFE_ARMOR = register("piglin_brute_safe_armor");

        private static TagKey<Item> register(String name) {
            return TAGS.registerItemTag(name);
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> BYPASSES_CUTLASS = register("bypasses_cutlass");

        private static TagKey<DamageType> register(String name) {
            return TAGS.registerDamageTypeTag(name);
        }
    }

    public static class TrimMaterials {
        public static final TagKey<TrimMaterial> PIGLIN_SAFE = register("piglin_safe");

        private static TagKey<TrimMaterial> register(String name) {
            return TAGS.registerTagKey(Registries.TRIM_MATERIAL, name);
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_PIGLIN_MANOR = register("has_structure/piglin_manor");
        public static final TagKey<Biome> HAS_CITADEL = register("has_structure/citadel");
        public static final TagKey<Biome> HAS_CATACOMB = register("has_structure/catacomb");

        private static TagKey<Biome> register(String name) {
            return TAGS.registerBiomeTag(name);
        }
    }
}
