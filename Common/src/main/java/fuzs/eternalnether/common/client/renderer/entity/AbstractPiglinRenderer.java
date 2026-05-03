package fuzs.eternalnether.common.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.piglin.AdultPiglinModel;
import net.minecraft.client.model.monster.piglin.PiglinModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

/**
 * Allows for using the down-sized adult model instead of a fully separate baby model.
 */
public abstract class AbstractPiglinRenderer extends PiglinRenderer {

    public AbstractPiglinRenderer(EntityRendererProvider.Context context, ModelLayerLocation body, ModelLayerLocation babyBody, ArmorModelSet<ModelLayerLocation> armorSet, ArmorModelSet<ModelLayerLocation> babyArmorSet) {
        super(context, body, babyBody, armorSet, babyArmorSet);
        this.babyModel = new AdultPiglinModel(context.bakeLayer(babyBody));
        this.layers.removeIf((RenderLayer<PiglinRenderState, PiglinModel> layer) -> {
            return layer instanceof HumanoidArmorLayer<?, ?, ?>
                    || layer instanceof ItemInHandLayer<PiglinRenderState, PiglinModel>;
        });
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public boolean useBabyOffset(PiglinRenderState state) {
                return false;
            }
        });
        this.addLayer(new HumanoidArmorLayer<>(this,
                ArmorModelSet.bake(armorSet, context.getModelSet(), AdultPiglinModel::new),
                ArmorModelSet.bake(babyArmorSet, context.getModelSet(), AdultPiglinModel::new),
                context.getEquipmentRenderer()) {
            @Override
            public void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot slot, int lightCoords, PiglinRenderState state) {
                Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
                if (equippable != null && shouldRender(equippable, slot)) {
                    AdultPiglinModel model = this.getArmorModel(state, slot);
                    EquipmentClientInfo.LayerType layerType = (this.usesInnerModel(slot) ?
                            EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS : EquipmentClientInfo.LayerType.HUMANOID);
                    this.equipmentRenderer.renderLayers(layerType,
                            equippable.assetId().orElseThrow(),
                            model,
                            state,
                            itemStack,
                            poseStack,
                            submitNodeCollector,
                            lightCoords,
                            state.outlineColor);
                }
            }
        });
    }

    @Override
    public abstract Identifier getTextureLocation(PiglinRenderState state);
}
