package com.neolab.heroesGame.aditional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HeroConfigManager {

    private static final Properties heroConfig = new Properties();
    private static boolean isLoadHeroConfig = false;
    private static final String heroConfigFileName = "hero.config";

    public static Properties getHeroConfig() {
        if (!isLoadHeroConfig) {
            loadConfig();
        }
        return heroConfig;
    }

    private static void loadConfig() {
        try (final InputStream is = new FileInputStream(heroConfigFileName)) {
            heroConfig.load(is);
            isLoadHeroConfig = true;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
