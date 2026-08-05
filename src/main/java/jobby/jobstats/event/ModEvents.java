package jobby.jobstats.event;

public class ModEvents {
    public static void registerServerEvents() {
        BlockEventHandler.register();
        CombatEventHandler.register();
        InteractionEventHandler.register();
        AdditionalEvents.register();
    }
}