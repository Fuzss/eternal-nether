package fuzs.eternalnether.common.data.tags;

import fuzs.eternalnether.common.init.ModTags;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v3.tags.AbstractTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;

public class ModTrimMaterialTagsProvider extends AbstractTagsProvider<TrimMaterial> {

    public ModTrimMaterialTagsProvider(DataProviderContext context) {
        super(Registries.TRIM_MATERIAL, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        this.tag(ModTags.TrimMaterials.PIGLIN_SAFE_TRIM_MATERIAL_TAG_KEY).add(TrimMaterials.GOLD);
    }
}
