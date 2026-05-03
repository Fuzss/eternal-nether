package fuzs.eternalnether.common.client.renderer.entity;

import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;

import java.util.Set;

/**
 * Allows for using the down-sized adult model instead of a fully separate baby model.
 * <p>
 * Additionally, does not rely on {@link UndeadHorseRenderer.Type}.
 */
public abstract class LegacyUndeadHorseRenderer extends UndeadHorseRenderer {
    /**
     * Copied from {@code AbstractEquineModel#BABY_TRANSFORMER} from Minecraft 1.21.11.
     */
    protected static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true,
            16.2F,
            1.36F,
            2.7272F,
            2.0F,
            20.0F,
            Set.of("head_parts"));

    public LegacyUndeadHorseRenderer(EntityRendererProvider.Context context, ModelLayerLocation adultModel, ModelLayerLocation babyModel, ModelLayerLocation saddleModel) {
        super(context,
                EquipmentClientInfo.LayerType.SKELETON_HORSE_SADDLE,
                saddleModel,
                Type.SKELETON,
                Type.SKELETON_BABY);
        this.adultModel = new HorseModel(context.bakeLayer(adultModel));
        this.babyModel = new HorseModel(context.bakeLayer(babyModel));
    }

    /**
     * Copied from {@code AbstractEquineModel#createBabyMesh} from Minecraft 1.21.11.
     */
    public static MeshDefinition createBabyMesh(CubeDeformation cubeDeformation) {
        return BABY_TRANSFORMER.apply(createFullScaleBabyMesh(cubeDeformation));
    }

    /**
     * Copied from {@code AbstractEquineModel#createFullScaleBabyMesh} from Minecraft 1.21.11.
     */
    protected static MeshDefinition createFullScaleBabyMesh(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = AbstractEquineModel.createBodyMesh(cubeDeformation);
        PartDefinition partDefinition = meshDefinition.getRoot();
        CubeDeformation cubeDeformation2 = cubeDeformation.extend(0.0F, 5.5F, 0.0F);
        partDefinition.addOrReplaceChild("left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .mirror()
                        .addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubeDeformation2),
                PartPose.offset(4.0F, 14.0F, 7.0F));
        partDefinition.addOrReplaceChild("right_hind_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubeDeformation2),
                PartPose.offset(-4.0F, 14.0F, 7.0F));
        partDefinition.addOrReplaceChild("left_front_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .mirror()
                        .addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubeDeformation2),
                PartPose.offset(4.0F, 14.0F, -10.0F));
        partDefinition.addOrReplaceChild("right_front_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubeDeformation2),
                PartPose.offset(-4.0F, 14.0F, -10.0F));
        return meshDefinition;
    }

    @Override
    public abstract Identifier getTextureLocation(EquineRenderState state);
}
