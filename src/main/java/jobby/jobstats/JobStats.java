package jobby.jobstats;

import jobby.jobstats.config.ColorConfig;
import jobby.jobstats.event.ModEvents;
import net.fabricmc.api.ModInitializer;

public class JobStats implements ModInitializer {
    @Override
    public void onInitialize() {
        // Load configuration file on startup
        ColorConfig.load();
        
        // Register standard events
        ModEvents.registerServerEvents();
    }
}