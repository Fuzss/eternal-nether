package fuzs.eternalnether.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinPrisoner;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinPrisonerAi;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ModStopHoldingItemIfNoLongerAdmiring<E extends PiglinPrisoner> extends Behavior<E> {

    public ModStopHoldingItemIfNoLongerAdmiring() {
        super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, E piglinPrisoner) {
        return !piglinPrisoner.getOffhandItem().isEmpty() &&
                !ToolTypeHelper.INSTANCE.isShield(piglinPrisoner.getOffhandItem());
    }

    @Override
    protected void start(ServerLevel serverLevel, E piglinPrisoner, long gameTime) {
        PiglinPrisonerAi.stopHoldingOffHandItem(serverLevel, piglinPrisoner, true);
    }
}