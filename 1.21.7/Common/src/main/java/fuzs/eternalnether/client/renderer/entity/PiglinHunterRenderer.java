package fuzs.eternalnether.client.renderer.entity;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.geom.ModModelLayers;
import fuzs.eternalnether.init.ModTags;
import fuzs.eternalnether.world.entity.monster.ShieldedMob;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.ItemStack;

public class PiglinHunterRenderer extends PiglinRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id("textures/entity/piglin/piglin_hunter.png");

    public PiglinHunterRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModModelLayers.PIGLIN_HUNTER,
                ModModelLayers.PIGLIN_HUNTER_BABY,
                ModModelLayers.PIGLIN_HUNTER_INNER_ARMOR,
                ModModelLayers.PIGLIN_HUNTER_OUTER_ARMOR,
                ModModelLayers.PIGLIN_HUNTER_BABY_INNER_ARMOR,
                ModModelLayers.PIGLIN_HUNTER_BABY_OUTER_ARMOR);
    }

    @Override
    public ResourceLocation getTextureLocation(PiglinRenderState renderState) {
        return TEXTURE_LOCATION;
    }

    @Override
    protected HumanoidModel.ArmPose getArmPose(AbstractPiglin abstractPiglin, HumanoidArm humanoidArm) {
        ItemStack itemStack = abstractPiglin.getItemHeldByArm(humanoidArm);
        return abstractPiglin.getMainArm() == humanoidArm && ((ShieldedMob) abstractPiglin).isUsingShield()
                && itemStack.is(ModTags.SHIELD_TOOLS_ITEM_TAG_KEY) ? HumanoidModel.ArmPose.BLOCK :
                HumanoidModel.ArmPose.EMPTY;
    }

    /**
     * TODO enable this again
     */
    public static LayerDefinition createSkullLayer() {
        MeshDefinition meshDefinition = PiglinModel.createMesh(CubeDeformation.NONE);
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("skull",
                CubeListBuilder.create()
                        .texOffs(61, 1)
                        .addBox(-7.0F, 16.5F, 0.0F, 14.0F, 6.0F, 19.0F, new CubeDeformation(-0.9F))
                        .texOffs(61, 3)
                        .addBox(5.0F, 11.5F, 0.0F, 2.0F, 11.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(70, 3)
                        .addBox(-7.0F, 11.5F, 0.0F, 2.0F, 11.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 15.5F, 25.0F, -1.6581F, 0.0F, 0.0F));


        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F),
                PartPose.offsetAndRotation(0.0F, 2.0F, -12.0F, 0.87266463F, 0.0F, 0.0F)
        );
        partDefinition3.addOrReplaceChild(
                "right_ear",
                CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-6.0F, -2.0F, -3.0F, 0.0F, 0.0F, (float) (-Math.PI * 2.0 / 9.0))
        );
        partDefinition3.addOrReplaceChild(
                "left_ear",
                CubeListBuilder.create().texOffs(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(6.0F, -2.0F, -3.0F, 0.0F, 0.0F, (float) (Math.PI * 2.0 / 9.0))
        );
        partDefinition3.addOrReplaceChild(
                "right_horn", CubeListBuilder.create().texOffs(10, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(-7.0F, 2.0F, -12.0F)
        );
        partDefinition3.addOrReplaceChild(
                "left_horn", CubeListBuilder.create().texOffs(1, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(7.0F, 2.0F, -12.0F)
        );

        return LayerDefinition.create(meshDefinition, 128, 64);
    }
}
