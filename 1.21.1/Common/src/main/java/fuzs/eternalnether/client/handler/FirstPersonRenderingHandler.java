package fuzs.eternalnether.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.eternalnether.init.ModItems;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderHandEvents;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FirstPersonRenderingHandler {

    public static RenderHandEvents.MainHand renderMainHand(InteractionHand interactionHand) {
        return (ItemInHandRenderer itemInHandRenderer, AbstractClientPlayer player, HumanoidArm humanoidArm, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress) -> {
            return onRenderBothHands(itemInHandRenderer,
                    interactionHand,
                    player,
                    humanoidArm,
                    itemStack,
                    poseStack,
                    multiBufferSource,
                    combinedLight,
                    partialTick,
                    interpolatedPitch,
                    swingProgress,
                    equipProgress);
        };
    }

    public static EventResult onRenderBothHands(ItemInHandRenderer itemInHandRenderer, InteractionHand interactionHand, AbstractClientPlayer player, HumanoidArm humanoidArm, ItemStack itemStack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress) {
        if (player.getUsedItemHand() == interactionHand && player.isBlocking() &&
                player.getUseItem().is(ModItems.CUTLASS)) {
            poseStack.pushPose();
            boolean mainHand = interactionHand == InteractionHand.MAIN_HAND;
            HumanoidArm handSide = mainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            boolean isHandSideRight = handSide == HumanoidArm.RIGHT;
            itemInHandRenderer.applyItemArmTransform(poseStack, handSide, equipProgress);
            transformBlockFirstPerson(poseStack, handSide);
            itemInHandRenderer.renderItem(player,
                    itemStack,
                    isHandSideRight ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND :
                            ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    !isHandSideRight,
                    poseStack,
                    bufferSource,
                    combinedLight);
            poseStack.popPose();
            return EventResult.INTERRUPT;
        } else {
            return EventResult.PASS;
        }
    }

    private static void transformBlockFirstPerson(PoseStack matrixStack, HumanoidArm hand) {
        int direction = hand == HumanoidArm.RIGHT ? 1 : -1;
        // values taken from Minecraft snapshot 15w33b
        matrixStack.translate(direction * -0.14142136F, 0.08F, 0.14142136F);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-102.25F));
        matrixStack.mulPose(Axis.YP.rotationDegrees(direction * 13.365F));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(direction * 78.05F));
    }
}
