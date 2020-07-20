package com.neolab.heroesGame.heroes.factory;

import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.Magician;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MagicianFactory implements HeroFactory {

    private final String propertyFileName = "hero.config";
    private final Properties prop =new Properties();

    public MagicianFactory() throws IOException {
        try (InputStream is = new FileInputStream(propertyFileName)) {
            prop.load(is);
        }
    }

    @Override
    public Hero create() {
        final int hp = Integer.parseInt(prop.getProperty("hero.magician.hp"));
        final int damage = Integer.parseInt(prop.getProperty("hero.magician.damage"));
        final float armor = Float.parseFloat(prop.getProperty("hero.magician.armor"));
        final float precision = Float.parseFloat(prop.getProperty("hero.magician.precision"));
        return new Magician(hp, damage, precision, armor);
    }
}
