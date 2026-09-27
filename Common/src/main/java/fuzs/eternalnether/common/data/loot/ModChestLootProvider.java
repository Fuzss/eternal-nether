package fuzs.eternalnether.common.data.loot;

import fuzs.eternalnether.common.init.ModItems;
import fuzs.eternalnether.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v3.loot.AbstractLootSubProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ModChestLootProvider extends AbstractLootSubProvider {

    public ModChestLootProvider(LootTableSubProvider.Context output) {
        super(output);
    }

    @Override
    public void generate() {
        HolderGetter<Enchantment> enchantments = this.output.lookup(Registries.ENCHANTMENT);
        this.output.accept(ModRegistry.CITADEL_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.WARPED_ENDER_PEARL.value())
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                        .setWeight(12)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(10))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(8))
                                .add(LootItem.lootTableItem(Items.TWISTING_VINES)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 8))))
                                .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE)
                                        .setWeight(6)
                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1.0F)))
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_HELMET)
                                        .setWeight(6)
                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1.0F)))
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS)
                                        .setWeight(6)
                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1.0F)))
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS)
                                        .setWeight(6)
                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1.0F)))
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(6))
                                .add(LootItem.lootTableItem(Items.NETHERITE_INGOT).setWeight(4))
                                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(4)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(2))
                                .add(LootItem.lootTableItem(Items.GOLD_BLOCK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                .add(LootItem.lootTableItem(Items.IRON_BLOCK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                .add(LootItem.lootTableItem(ModItems.WARPED_ENDER_PEARL.value())
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.TWISTING_VINES)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 8))))
                                .add(LootItem.lootTableItem(Items.WARPED_FUNGUS_ON_A_STICK)
                                        .setWeight(6)
                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1.0F)))
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.between(2, 3))
                                .add(LootItem.lootTableItem(Items.WARPED_ROOTS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(12, 24))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 9))))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 9))))
                                .add(LootItem.lootTableItem(Items.CRYING_OBSIDIAN)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 5))))
                                .add(LootItem.lootTableItem(Items.QUARTZ)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 24))))
                                .add(LootItem.lootTableItem(Items.WARPED_STEM)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                .add(LootItem.lootTableItem(Items.MAGMA_CREAM)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8))))
                                .add(LootItem.lootTableItem(Items.NETHER_BRICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(6, 16))))));
        this.output.accept(ModRegistry.CATACOMB_TREASURE_RIB_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.between(3, 5))
                                .add(LootItem.lootTableItem(Items.BONE)
                                        .setWeight(12)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 10))))
                                .add(LootItem.lootTableItem(Items.SOUL_SAND)
                                        .setWeight(12)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 12))))
                                .add(LootItem.lootTableItem(Items.SOUL_SOIL)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_STONE.value())
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 10))))
                                .add(LootItem.lootTableItem(Items.COAL)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8))))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_SWORD)
                                        .setWeight(8)
                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1.0F)))
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                .add(LootItem.lootTableItem(Items.CRYING_OBSIDIAN)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                                .add(LootItem.lootTableItem(Items.COAL_BLOCK)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(7))
                                .add(LootItem.lootTableItem(ModItems.WITHER_WALTZ_MUSIC_DISC.value()).setWeight(6))
                                .add(LootItem.lootTableItem(Items.SADDLE).setWeight(5))
                                .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(4))
                                .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.WITHER_SKELETON_SKULL).setWeight(2))
                                .add(LootItem.lootTableItem(Items.NETHERITE_INGOT).setWeight(1))));
    }
}
