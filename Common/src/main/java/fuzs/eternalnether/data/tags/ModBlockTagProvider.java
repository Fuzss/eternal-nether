package fuzs.eternalnether.data.tags;

import com.izofar.bygonenether.init.ModBlocks;
import com.izofar.bygonenether.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ModRegistry.WITHERED_BLOCK_TAG_KEY)
                .add(ModBlocks.WITHERED_BLACKSTONE.value(),
                        ModBlocks.WITHERED_BLACKSTONE_STAIRS.value(),
                        ModBlocks.WITHERED_BLACKSTONE_SLAB.value(),
                        ModBlocks.CHISELED_WITHERED_BLACKSTONE.value(),
                        ModBlocks.CRACKED_WITHERED_BLACKSTONE.value(),
                        ModBlocks.CRACKED_WITHERED_BLACKSTONE_STAIRS.value(),
                        ModBlocks.CRACKED_WITHERED_BLACKSTONE_SLAB.value(),
                        ModBlocks.WITHERED_BASALT.value(),
                        ModBlocks.WITHERED_COAL_BLOCK.value(),
                        ModBlocks.WITHERED_QUARTZ_BLOCK.value(),
                        ModBlocks.WITHERED_DEBRIS.value());
    }
}
