package fuzs.eternalnether.common.data.tags;

import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModItems;
import fuzs.eternalnether.common.init.ModTags;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagsProvider extends AbstractTagProvider<Item> {

    public ModItemTagsProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        ModBlockFamilies.getAllBlockSetFamilies().forEach((BlockSetFamily blockSetFamily) -> {
            this.generateFor(blockSetFamily.getItemVariants(), VARIANT_ITEM_TAGS);
        });
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(ModItems.COBBLED_BLACKSTONE);
        this.tag(ItemTags.STONE_TOOL_MATERIALS).add(ModItems.COBBLED_BLACKSTONE);
        this.tag("c:music_discs").add(ModItems.WITHER_WALTZ_MUSIC_DISC);
        this.tag("c:bones").add(ModItems.WITHERED_BONE);
        this.tag("c:fertilizers").add(ModItems.WITHERED_BONE_MEAL);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.GILDED_NETHERITE_SHIELD);
        this.tag("c:tools/shield").add(ModItems.GILDED_NETHERITE_SHIELD);
        this.tag(ItemTags.SWORDS).add(ModItems.CUTLASS);
        this.tag("c:tools/melee_weapon").add(ModItems.CUTLASS);
        this.tag(ModTags.PIGLIN_BRUTE_SAFE_ARMOR_ITEM_TAG_KEY)
                .add(ItemIds.NETHERITE_HELMET,
                        ItemIds.NETHERITE_CHESTPLATE,
                        ItemIds.NETHERITE_LEGGINGS,
                        ItemIds.NETHERITE_BOOTS);
    }
}
