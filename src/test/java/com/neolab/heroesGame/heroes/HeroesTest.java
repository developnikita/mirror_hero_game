package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HeroesTest {

    @Test
    public void heroesMethodsTest() throws HeroExceptions {
        final Hero targetHero = WarlordFootman.createInstance();
        final Hero activeHero = WarlordFootman.createInstance();
        final Map<SquareCoordinate, Hero> mapTarget = new HashMap<>();
        final SquareCoordinate targetCoord = new SquareCoordinate(0, 0);
        final SquareCoordinate activeCoord = new SquareCoordinate(1, 1);
        mapTarget.put(targetCoord, targetHero);
        final Map<SquareCoordinate, Hero> mapActive = new HashMap<>();
        mapActive.put(activeCoord, activeHero);
        final Army armyTarget = new Army(mapTarget);
        final Army armyActive = new Army(mapActive);

        //testEquals
        assertNotEquals(activeHero, targetHero);

        // removeTargetTest
        final int oldSize = armyTarget.getHeroes().size();
        targetHero.setHp(0);
        armyTarget.killHero(targetCoord);
        final int newSize = armyTarget.getHeroes().size();
        assertNotEquals(oldSize, newSize);

        //calculateDamage
        final int damageByWarlordFootman = 56;
        final int damageByFootman = 59;
        final int damageByArcher = 66;
        final Hero footman = Footman.createInstance();
        final Hero archer = Archer.createInstance();
        assertEquals(damageByWarlordFootman, activeHero.calculateDamage(targetHero));
        assertEquals(damageByFootman, activeHero.calculateDamage(footman));
        assertEquals(damageByArcher, activeHero.calculateDamage(archer));

        //isHitTest
        assertTrue(activeHero.isHit(1.0f));
        assertFalse(activeHero.isHit(0));

        //setDefenseTest
        final float armor = activeHero.getArmor();
        final float armorDef = activeHero.getArmor() + 0.5f;
        activeHero.setDefence();
        assertTrue(activeHero.isDefence());
        assertEquals(Math.round(armorDef), Math.round(activeHero.getArmor()));
        activeHero.cancelDefence();
        assertFalse(activeHero.isDefence());
        assertEquals(Math.round(armor), Math.round(activeHero.getArmor()));

    }
}
