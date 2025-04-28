package fuzs.eternalnether.data.tags;

import fuzs.eternalnether.init.ModItems;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ItemTags.STONE_CRAFTING_MATERIALS).add(ModItems.COBBLED_BLACKSTONE.value());
        this.add(ItemTags.STONE_TOOL_MATERIALS).add(ModItems.COBBLED_BLACKSTONE.value());
        this.add("c:music_discs").add(ModItems.WITHER_WALTZ_MUSIC_DISC.value());
        this.add("c:bones").add(ModItems.WITHERED_BONE.value());
        this.add("c:fertilizers").add(ModItems.WITHERED_BONE_MEAL.value());
        this.add(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.GILDED_NETHERITE_SHIELD.value());
        this.add("c:tools/shield").add(ModItems.GILDED_NETHERITE_SHIELD.value());
        this.add(ItemTags.SWORDS).add(ModItems.CUTLASS.value());
        this.add("c:tools/melee_weapon").add(ModItems.CUTLASS.value());
    }
}
