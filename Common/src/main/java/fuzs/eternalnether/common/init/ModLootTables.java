package fuzs.eternalnether.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public final class ModLootTables {
    public static final ResourceKey<LootTable> CITADEL_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/citadel");
    public static final ResourceKey<LootTable> CATACOMB_TREASURE_RIB_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/catacomb/treasure_rib");
    public static final ResourceKey<LootTable> SHEARING_WARPED_ENDER_MAN_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "shearing/warped_ender_man");
}
