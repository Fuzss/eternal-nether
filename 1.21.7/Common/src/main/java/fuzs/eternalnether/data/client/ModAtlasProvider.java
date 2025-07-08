package fuzs.eternalnether.data.client;

import fuzs.eternalnether.client.renderer.ShieldItemRenderer;
import fuzs.eternalnether.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.puzzleslib.api.client.data.v2.AbstractAtlasProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModAtlasProvider extends AbstractAtlasProvider {

    public ModAtlasProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addAtlases() {
        this.addMaterial(NetheriteBellRenderer.NETHERITE_BELL_MATERIAL);
        this.addMaterial(ShieldItemRenderer.SHIELD_BASE_MATERIAL);
        this.addMaterial(ShieldItemRenderer.NO_PATTERN_SHIELD_MATERIAL);
    }
}
