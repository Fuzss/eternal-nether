package fuzs.eternalnether.common.data.client;

import fuzs.eternalnether.common.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.eternalnether.common.client.renderer.special.GildedNetheriteShieldSpecialRenderer;
import fuzs.puzzleslib.common.api.client.data.v3.atlas.AbstractAtlasProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModAtlasProvider extends AbstractAtlasProvider {

    public ModAtlasProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addAtlases() {
        this.addMaterial(NetheriteBellRenderer.NETHERITE_BELL_TEXTURE);
        this.addMaterial(GildedNetheriteShieldSpecialRenderer.SHIELD_BASE);
        this.addMaterial(GildedNetheriteShieldSpecialRenderer.SHIELD_BASE_NO_PATTERN);
    }
}
