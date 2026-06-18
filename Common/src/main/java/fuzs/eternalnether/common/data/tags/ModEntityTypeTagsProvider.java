package fuzs.eternalnether.common.data.tags;

import fuzs.eternalnether.common.init.ModEntityTypes;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTagsProvider extends AbstractTagProvider<EntityType<?>> {

    public ModEntityTypeTagsProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        this.tag(EntityTypeTags.SKELETONS)
                .add(ModEntityTypes.CORPOR,
                        ModEntityTypes.WITHER_SKELETON_HORSE,
                        ModEntityTypes.WITHER_SKELETON_KNIGHT,
                        ModEntityTypes.WRAITHER);
        this.tag(EntityTypeTags.BURN_IN_DAYLIGHT).add(ModEntityTypes.CORPOR, ModEntityTypes.WRAITHER);
        this.tag(EntityTypeTags.CAN_EQUIP_SADDLE).add(ModEntityTypes.WITHER_SKELETON_HORSE);
        this.tag(EntityTypeTags.FOLLOWABLE_FRIENDLY_MOBS).add(ModEntityTypes.WITHER_SKELETON_HORSE);
        this.tag(EntityTypeTags.CANNOT_BE_AGE_LOCKED).add(ModEntityTypes.WITHER_SKELETON_HORSE);
        this.tag("enderzoology:concussion_immune").add(ModEntityTypes.WARPED_ENDERMAN);
    }
}
