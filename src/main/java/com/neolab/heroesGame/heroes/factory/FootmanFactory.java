package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Footman;
import com.neolab.heroesGame.heroes.Hero;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FootmanFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop = new Properties();

    public FootmanFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("hero.footman.hp"));
        final int damage = Integer.parseInt(prop.getProperty("hero.footman.damage"));
        final float armor = Float.parseFloat(prop.getProperty("hero.footman.armor"));
        final float precision = Float.parseFloat(prop.getProperty("hero.footman.precision"));
        return new Footman(hp, damage, precision, armor);
    }
}
