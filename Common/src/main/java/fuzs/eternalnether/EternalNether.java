package fuzs.eternalnether;

import com.izofar.bygonenether.handler.BlockInteractionHandler;
import com.izofar.bygonenether.init.ModEntityTypes;
import com.izofar.bygonenether.init.ModItems;
import com.izofar.bygonenether.init.ModRegistry;
import com.izofar.bygonenether.util.CreativeModeTabHelper;
import com.izofar.bygonenether.util.ModStructureUtils;
import com.izofar.bygonenether.world.entity.animal.horse.WitherSkeletonHorse;
import com.izofar.bygonenether.world.entity.monster.*;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.puzzleslib.api.event.v1.level.BlockEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EternalNether implements ModConstructor {
    public static final String MOD_ID = "eternalnether";
    public static final String MOD_NAME = "Eternal Nether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onConstructMod() {
        ModRegistry.boostrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        PlayerInteractEvents.ATTACK_BLOCK.register(BlockInteractionHandler::onAttackBlock);
        BlockEvents.BREAK.register(BlockInteractionHandler::onBreakBlock);
    }

    @Override
    public void onCommonSetup() {
        ModStructureUtils.addBasaltRestrictions();
        ModEntityTypes.modifyPiglinMemoryAndSensors();
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModEntityTypes.PIGLIN_PRISONER.value(), PiglinPrisoner.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.PIGLIN_HUNTER.value(), PiglinBrute.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WEX.value(), Wex.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WARPED_ENDERMAN.value(), WarpedEnderMan.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WRAITHER.value(), Wraither.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(),
                WitherSkeletonKnight.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.CORPOR.value(), Corpor.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WITHER_SKELETON_HORSE.value(),
                WitherSkeletonHorse.createAttributes());
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID, ModItems.WITHERED_DEBRIS)
                .displayItems(CreativeModeTabHelper.getDisplayItems(MOD_ID)));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
