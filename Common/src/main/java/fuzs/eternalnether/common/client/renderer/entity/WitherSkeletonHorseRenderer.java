package fuzs.eternalnether.common.client.renderer.entity;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.client.model.geom.ModModelLayers;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.animal.equine.EquineSaddleModel;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.equine.AbstractHorse;

import java.util.Set;

/**
 * TODO we can more easily extend the enum now in {@link net.minecraft.client.renderer.entity.UndeadHorseRenderer}, so use it instead of this
 */
public class WitherSkeletonHorseRenderer extends AbstractHorseRenderer<AbstractHorse, EquineRenderState, AbstractEquineModel<EquineRenderState>> {
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
    private static final Type WITHER_SKELETON_HORSE = new Type(EternalNether.id(
            "textures/entity/horse/wither_skeleton_horse.png"),
            ModModelLayers.WITHER_SKELETON_HORSE,
            ModModelLayers.WITHER_SKELETON_HORSE_BABY,
            EquipmentClientInfo.LayerType.SKELETON_HORSE_SADDLE,
            ModModelLayers.WITHER_SKELETON_HORSE_SADDLE);

    private final Identifier texture;

    public WitherSkeletonHorseRenderer(EntityRendererProvider.Context context) {
        this(context, WITHER_SKELETON_HORSE);
    }

    public WitherSkeletonHorseRenderer(EntityRendererProvider.Context context, Type type) {
        super(context,
                new HorseModel(context.bakeLayer(type.model)),
                new HorseModel(context.bakeLayer(type.babyModel)));
        this.texture = type.texture;
        this.addLayer(new SimpleEquipmentLayer<>(this,
                context.getEquipmentRenderer(),
                type.saddleLayer,
                (EquineRenderState state) -> {
                    return state.saddle;
                },
                new EquineSaddleModel(context.bakeLayer(type.saddleModel)),
                null));
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
    public Identifier getTextureLocation(EquineRenderState state) {
        return this.texture;
    }

    @Override
    public EquineRenderState createRenderState() {
        return new EquineRenderState();
    }

    public record Type(Identifier texture,
                       ModelLayerLocation model,
                       ModelLayerLocation babyModel,
                       EquipmentClientInfo.LayerType saddleLayer,
                       ModelLayerLocation saddleModel) {
    }
}
