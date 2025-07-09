package fuzs.eternalnether.client.model.geom;

import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(EternalNether.MOD_ID);
    public static final ModelLayerLocation NETHERITE_BELL = MODEL_LAYERS.registerModelLayer("netherite_bell");
    public static final ModelLayerLocation GILDED_NETHERITE_SHIELD = MODEL_LAYERS.registerModelLayer(
            "gilded_netherite_shield");
    public static final ModelLayerLocation WEX = MODEL_LAYERS.registerModelLayer("wex");
    public static final ModelLayerLocation WARPED_ENDERMAN = MODEL_LAYERS.registerModelLayer("warped_enderman");
    public static final ModelLayerLocation CORPOR = MODEL_LAYERS.registerModelLayer("corpor");
    public static final ModelLayerLocation CORPOR_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer("corpor");
    public static final ModelLayerLocation CORPOR_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer("corpor");
    public static final ModelLayerLocation WRAITHER = MODEL_LAYERS.registerModelLayer("wraither");
    public static final ModelLayerLocation WRAITHER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer("wraither");
    public static final ModelLayerLocation WRAITHER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer("wraither");
    public static final ModelLayerLocation WITHER_SKELETON_KNIGHT = MODEL_LAYERS.registerModelLayer(
            "wither_skeleton_knight");
    public static final ModelLayerLocation WITHER_SKELETON_KNIGHT_OUTER_LAYER = MODEL_LAYERS.registerModelLayer(
            "wither_skeleton_knight",
            "outer");
    public static final ModelLayerLocation WITHER_SKELETON_KNIGHT_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "wither_skeleton_knight");
    public static final ModelLayerLocation WITHER_SKELETON_KNIGHT_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "wither_skeleton_knight");
    public static final ModelLayerLocation PIGLIN_PRISONER = MODEL_LAYERS.registerModelLayer("piglin_prisoner");
    public static final ModelLayerLocation PIGLIN_PRISONER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "piglin_prisoner");
    public static final ModelLayerLocation PIGLIN_PRISONER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "piglin_prisoner");
    public static final ModelLayerLocation PIGLIN_PRISONER_BABY = MODEL_LAYERS.registerModelLayer("piglin_prisoner_baby");
    public static final ModelLayerLocation PIGLIN_PRISONER_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "piglin_prisoner_baby");
    public static final ModelLayerLocation PIGLIN_PRISONER_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "piglin_prisoner_baby");
    public static final ModelLayerLocation PIGLIN_HUNTER = MODEL_LAYERS.registerModelLayer("piglin_hunter");
    public static final ModelLayerLocation PIGLIN_HUNTER_SKULL = MODEL_LAYERS.registerModelLayer("piglin_hunter",
            "skull");
    public static final ModelLayerLocation PIGLIN_HUNTER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "piglin_hunter");
    public static final ModelLayerLocation PIGLIN_HUNTER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "piglin_hunter");
    public static final ModelLayerLocation PIGLIN_HUNTER_BABY = MODEL_LAYERS.registerModelLayer("piglin_hunter_baby");
    public static final ModelLayerLocation PIGLIN_HUNTER_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "piglin_hunter_baby");
    public static final ModelLayerLocation PIGLIN_HUNTER_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "piglin_hunter_baby");
    public static final ModelLayerLocation WITHER_SKELETON_HORSE = MODEL_LAYERS.registerModelLayer(
            "wither_skeleton_horse");
    public static final ModelLayerLocation WITHER_SKELETON_HORSE_BABY = MODEL_LAYERS.registerModelLayer(
            "wither_skeleton_horse_baby");
    public static final ModelLayerLocation WITHER_SKELETON_HORSE_SADDLE = MODEL_LAYERS.registerModelLayer(
            "wither_skeleton_horse",
            "saddle");
    public static final ModelLayerLocation WITHER_SKELETON_HORSE_BABY_SADDLE = MODEL_LAYERS.registerModelLayer(
            "wither_skeleton_horse_baby",
            "saddle");
}
