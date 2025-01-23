package fuzs.eternalnether;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fuzs.eternalnether.init.ModEntityTypes;
import fuzs.eternalnether.init.ModFeatures;
import fuzs.eternalnether.init.ModItems;
import fuzs.eternalnether.init.ModRegistry;
import fuzs.eternalnether.util.CreativeModeTabHelper;
import fuzs.eternalnether.world.entity.animal.horse.WitherSkeletonHorse;
import fuzs.eternalnether.world.entity.monster.*;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinPrisoner;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinPrisonerAi;
import fuzs.eternalnether.world.entity.projectile.EnderPearlTeleportCallback;
import fuzs.eternalnether.world.entity.projectile.ThrownWarpedEnderpearl;
import fuzs.eternalnether.world.item.CutlassItem;
import fuzs.puzzleslib.api.biome.v1.BiomeLoadingContext;
import fuzs.puzzleslib.api.biome.v1.BiomeLoadingPhase;
import fuzs.puzzleslib.api.biome.v1.BiomeModificationContext;
import fuzs.puzzleslib.api.config.v3.json.GsonCodecHelper;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.BiomeModificationsContext;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.entity.living.UseItemEvents;
import fuzs.puzzleslib.api.event.v1.level.BlockEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EternalNether implements ModConstructor {
    public static final String MOD_ID = "eternalnether";
    public static final String MOD_NAME = "Eternal Nether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onConstructMod() {
        ModRegistry.boostrap();
        registerEventHandlers();
        if (true) return;
        Path outputPath = Path.of(".", "../Common/src/generated/resources/data").toAbsolutePath().normalize();
        Path inputPath = Path.of(".", "../Common/src/main/resources/data").toAbsolutePath().normalize();
        List<File> jsonFiles = new ArrayList<>();
        getAllFilesRecursive(inputPath.toFile(), jsonFiles::add, s -> s.endsWith(".json"));
        jsonFiles.forEach((File file) -> {
            Path path = outputPath.resolve(inputPath.relativize(file.toPath()));
            LOGGER.info("Putting {} to {}", file, path);
            path.toFile().getParentFile().mkdirs();
            try (FileReader fileReader = new FileReader(file); FileWriter fileWriter = new FileWriter(path.toFile())) {
                JsonElement jsonElement = GsonHelper.fromJson(GsonCodecHelper.GSON, fileReader, JsonElement.class);
                jsonElement = searchAndReplaceValue(jsonElement, "bygonenether", MOD_ID);
                GsonCodecHelper.GSON.toJson(jsonElement, fileWriter);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
        List<File> nbtFiles = new ArrayList<>();
        getAllFilesRecursive(inputPath.toFile(), nbtFiles::add, s -> s.endsWith(".nbt"));
        nbtFiles.forEach((File file) -> {
            Path path = outputPath.resolve(inputPath.relativize(file.toPath()));
            LOGGER.info("Putting {} to {}", file, path);
            path.toFile().getParentFile().mkdirs();
            try {
                CompoundTag tag = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());
                tag = searchAndReplaceValue(tag, "bygonenether", MOD_ID);
                NbtIo.writeCompressed(tag, path);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    @Nullable
    protected static <T> JsonElement searchAndReplaceValue(@Nullable JsonElement jsonElement, String searchFor, String replaceWith) {
        Objects.requireNonNull(searchFor, "search for is null");
        Objects.requireNonNull(replaceWith, "replace with is null");
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isString()) {
                    if (jsonPrimitive.getAsString().contains(searchFor)) {
                        return new JsonPrimitive(jsonPrimitive.getAsString().replace(searchFor, replaceWith));
                    }
                }
                return jsonElement;
            } else if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    jsonArray.set(i, searchAndReplaceValue(jsonArray.get(i), searchFor, replaceWith));
                }
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    entry.setValue(searchAndReplaceValue(entry.getValue(), searchFor, replaceWith));
                }
            }
        }
        return jsonElement;
    }

    @Nullable
    protected static <T extends Tag> T searchAndReplaceValue(@Nullable T tag, String searchFor, String replaceWith) {
        Objects.requireNonNull(searchFor, "search for is null");
        Objects.requireNonNull(replaceWith, "replace with is null");
        if (tag != null) {
            if (tag instanceof StringTag stringTag) {
                if (stringTag.getAsString().contains(searchFor)) {
                    return (T) StringTag.valueOf(stringTag.getAsString().replace(searchFor, replaceWith));
                }
            } else if (tag instanceof ListTag listTag) {
                for (int i = 0; i < listTag.size(); i++) {
                    listTag.set(i, searchAndReplaceValue(listTag.get(i), searchFor, replaceWith));
                }
            } else if (tag instanceof CompoundTag compoundTag) {
                for (String s : compoundTag.getAllKeys()) {
                    compoundTag.put(s, searchAndReplaceValue(compoundTag.get(s), searchFor, replaceWith));
                }
            }
        }
        return tag;
    }

    private static void getAllFilesRecursive(File directory, Consumer<File> fileOutput, Predicate<String> fileFilter) {
        File[] listedFiles = directory.listFiles();
        if (listedFiles != null) {
            for (File file : listedFiles) {
                if (file.isDirectory()) {
                    getAllFilesRecursive(file, fileOutput, fileFilter);
                } else if (fileFilter.test(file.getName())) {
                    fileOutput.accept(file);
                }
            }
        }
    }

    private static void registerEventHandlers() {
        BlockEvents.BREAK.register((ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, Player player, ItemStack itemInHand) -> {
            if (blockState.is(Blocks.IRON_BARS)) {
                PiglinPrisonerAi.exciteNearbyPiglins(player, false);
            }
            return EventResult.PASS;
        });
        UseItemEvents.START.register(CutlassItem::onUseItemStart);
        EnderPearlTeleportCallback.EVENT.register(ThrownWarpedEnderpearl::onEnderPearlTeleport);
    }

    @Override
    public void onCommonSetup() {
        ModFeatures.setBasaltFeatureRestrictions();
        ModEntityTypes.setPiglinBruteSensorsAndMemories();
    }

    @Override
    public void onRegisterBiomeModifications(BiomeModificationsContext context) {
        context.register(BiomeLoadingPhase.ADDITIONS,
                (BiomeLoadingContext biomeLoadingContext) -> biomeLoadingContext.is(Biomes.SOUL_SAND_VALLEY),
                (BiomeModificationContext biomeModificationContext) -> {
                    biomeModificationContext.generationSettings()
                            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION,
                                    ModRegistry.SOUL_STONE_BLOBS_PLACED_FEATURE);
                });
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
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModEntityTypes.PIGLIN_PRISONER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PiglinPrisoner::checkPiglinSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.PIGLIN_HUNTER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PiglinPrisoner::checkPiglinSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WEX.value(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WARPED_ENDERMAN.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WRAITHER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.CORPOR.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WITHER_SKELETON_HORSE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SkeletonHorse::checkSkeletonHorseSpawnRules);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID, ModItems.WITHERED_DEBRIS)
                .displayItems(CreativeModeTabHelper.getDisplayItems(MOD_ID)));
    }

    @Override
    public ContentRegistrationFlags[] getContentRegistrationFlags() {
        return new ContentRegistrationFlags[]{ContentRegistrationFlags.BIOME_MODIFICATIONS};
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
