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
        this.add(ItemTags.STONE_CRAFTING_MATERIALS).add(ModItems.COBBLED_BLACKSTONE);
        this.add(ItemTags.STONE_TOOL_MATERIALS).add(ModItems.COBBLED_BLACKSTONE);
    }
}
