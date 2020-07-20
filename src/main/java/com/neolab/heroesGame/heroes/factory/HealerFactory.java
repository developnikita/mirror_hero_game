package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Healer;
import com.neolab.heroesGame.heroes.Hero;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HealerFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop = new Properties();

    public HealerFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("hero.healer.hp"));
        final int heal = Integer.parseInt(prop.getProperty("hero.healer.heal"));
        final float armor = Float.parseFloat(prop.getProperty("hero.healer.armor"));
        final float precision = Float.parseFloat(prop.getProperty("hero.healer.precision"));
        return new Healer(hp, heal, precision, armor);
    }
}
