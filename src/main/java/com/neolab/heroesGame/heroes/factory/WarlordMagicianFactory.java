package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordMagician;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WarlordMagicianFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop = new Properties();

    public WarlordMagicianFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("warlord.magician.hp"));
        final int damage = Integer.parseInt(prop.getProperty("warlord.magician.damage"));
        final float armor = Float.parseFloat(prop.getProperty("warlord.magician.armor"));
        final float precision = Float.parseFloat(prop.getProperty("warlord.magician.precision"));
        return new WarlordMagician(hp, damage, precision, armor);
    }
}
