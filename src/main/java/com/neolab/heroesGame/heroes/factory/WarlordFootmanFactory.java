package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordFootman;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WarlordFootmanFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop = new Properties();

    public WarlordFootmanFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("warlord.footman.hp"));
        final int damage = Integer.parseInt(prop.getProperty("warlord.footman.damage"));
        final float armor = Float.parseFloat(prop.getProperty("warlord.footman.armor"));
        final float precision = Float.parseFloat(prop.getProperty("warlord.footman.precision"));
        return new WarlordFootman(hp, damage, precision, armor);
    }
}
