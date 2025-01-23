package fuzs.eternalnether.neoforge.data.client;

import fuzs.eternalnether.init.ModSoundEvents;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractSoundDefinitionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import net.neoforged.neoforge.common.data.SoundDefinition;

public class ModSoundProvider extends AbstractSoundDefinitionProvider {

    public ModSoundProvider(NeoForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addSoundDefinitions() {
        SoundDefinition soundDefinition = definition().with(sound(this.id("wither_waltz")).stream());
        this.add(ModSoundEvents.WITHER_WALTZ.value(), soundDefinition);
        soundDefinition.subtitle(null);
        this.add(ModSoundEvents.WARPED_ENDERMAN_AMBIENT.value(),
                this.id("entity/warped_enderman/idle1"),
                this.id("entity/warped_enderman/idle2"),
                this.id("entity/warped_enderman/idle3"),
                this.id("entity/warped_enderman/idle4"),
                this.id("entity/warped_enderman/idle5"));
        this.add(ModSoundEvents.WARPED_ENDERMAN_DEATH.value(), this.id("entity/warped_enderman/death"));
        this.add(ModSoundEvents.WARPED_ENDERMAN_HURT.value(),
                this.id("entity/warped_enderman/hit1"),
                this.id("entity/warped_enderman/hit2"),
                this.id("entity/warped_enderman/hit3"),
                this.id("entity/warped_enderman/hit4"));
        this.add(ModSoundEvents.WARPED_ENDERMAN_SCREAM.value(),
                this.id("entity/warped_enderman/scream1"),
                this.id("entity/warped_enderman/scream2"),
                this.id("entity/warped_enderman/scream3"),
                this.id("entity/warped_enderman/scream4"));
        this.add(ModSoundEvents.WARPED_ENDERMAN_STARE.value(), this.id("entity/warped_enderman/stare"));
        this.add(ModSoundEvents.WARPED_ENDERMAN_TELEPORT.value(),
                this.id("entity/warped_enderman/portal1"),
                this.id("entity/warped_enderman/portal2"));
        this.add(ModSoundEvents.WEX_AMBIENT.value(),
                this.id("entity/wex/idle1"),
                this.id("entity/wex/idle2"),
                this.id("entity/wex/idle3"),
                this.id("entity/wex/idle4"));
        this.add(ModSoundEvents.WEX_CHARGE.value(),
                this.id("entity/wex/charge1"),
                this.id("entity/wex/charge2"),
                this.id("entity/wex/charge3"));
        this.add(ModSoundEvents.WEX_DEATH.value(), this.id("entity/wex/death1"), this.id("entity/wex/death2"));
        this.add(ModSoundEvents.WEX_HURT.value(), this.id("entity/wex/hurt1"), this.id("entity/wex/hurt2"));
    }
}
