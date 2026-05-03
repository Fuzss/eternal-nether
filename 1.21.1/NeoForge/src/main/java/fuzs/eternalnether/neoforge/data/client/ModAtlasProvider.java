package fuzs.eternalnether.neoforge.data.client;

import fuzs.eternalnether.client.renderer.ShieldItemRenderer;
import fuzs.eternalnether.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractSpriteSourceProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ModAtlasProvider extends AbstractSpriteSourceProvider {

    public ModAtlasProvider(NeoForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addSpriteSources() {
        this.addMaterial(NetheriteBellRenderer.NETHERITE_BELL_MATERIAL);
        this.addMaterial(ShieldItemRenderer.SHIELD_BASE_MATERIAL);
        this.addMaterial(ShieldItemRenderer.NO_PATTERN_SHIELD_MATERIAL);
    }

    public void addMaterial(Material material) {
        ResourceLocation resourceLocation = material.atlasLocation().withPath((String s) -> {
            return s.replace("textures/atlas/", "").replace(".png", "");
        });
        this.atlas(resourceLocation).addSource(new SingleFile(material.texture(), Optional.empty()));
    }
}
