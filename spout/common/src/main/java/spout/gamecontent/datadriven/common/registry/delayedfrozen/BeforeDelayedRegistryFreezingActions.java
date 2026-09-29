package spout.gamecontent.datadriven.common.registry.delayedfrozen;

import net.minecraft.server.packs.resources.CloseableResourceManager;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class BeforeDelayedRegistryFreezingActions {

    private BeforeDelayedRegistryFreezingActions() {
        throw new UnsupportedOperationException();
    }

    public interface Action {

        /**
         * Lowest priority runs first.
         */
        int getBeforeDelayedRegistryFreezingActionPriority();

        CompletableFuture<?> runBeforeDelayedRegistryFreezing(CloseableResourceManager resources, Executor mainThreadExecutor);

    }

    public static CompletableFuture<?> runAll(CloseableResourceManager resources, Executor mainThreadExecutor) {
        List<Action> actions = ServiceLoader.load(Action.class).stream().map(ServiceLoader.Provider::get)
            .sorted(Comparator.comparingInt(Action::getBeforeDelayedRegistryFreezingActionPriority)).toList();
        CompletableFuture<?> lastInstanceCompletableFuture = CompletableFuture.completedFuture(null);
        for (Action action : actions) {
            lastInstanceCompletableFuture = lastInstanceCompletableFuture.thenComposeAsync(_ ->
                action.runBeforeDelayedRegistryFreezing(resources, mainThreadExecutor)
            );
        }
        return lastInstanceCompletableFuture;
    }

}
