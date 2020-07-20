package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Archer;
import com.neolab.heroesGame.heroes.Hero;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ArcherFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop = new Properties();

    public ArcherFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("hero.archer.hp"));
        final int damage = Integer.parseInt(prop.getProperty("hero.archer.damage"));
        final float armor = Float.parseFloat(prop.getProperty("hero.archer.armor"));
        final float precision = Float.parseFloat(prop.getProperty("hero.archer.precision"));
        return new Archer(hp, damage, precision, armor);
    }
}
