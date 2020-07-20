package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordVampire;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WarlordVampireFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop = new Properties();

    public WarlordVampireFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("warlord.vampire.hp"));
        final int damage = Integer.parseInt(prop.getProperty("warlord.vampire.damage"));
        final float armor = Float.parseFloat(prop.getProperty("warlord.vampire.armor"));
        final float precision = Float.parseFloat(prop.getProperty("warlord.vampire.precision"));
        return new WarlordVampire(hp, damage, precision, armor);
    }
}
