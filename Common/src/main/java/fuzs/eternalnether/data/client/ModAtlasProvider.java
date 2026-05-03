package fuzs.eternalnether.data.client;

import fuzs.eternalnether.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.eternalnether.client.renderer.special.GildedNetheriteShieldSpecialRenderer;
import fuzs.puzzleslib.common.api.client.data.v2.AbstractAtlasProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;

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
