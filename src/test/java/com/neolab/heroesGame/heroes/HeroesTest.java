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
        Hero targetHero = WarlordFootman.createInstance();
        Hero activeHero = WarlordFootman.createInstance();
        Map<SquareCoordinate, Hero> mapTarget = new HashMap<>();
        SquareCoordinate targetCoord = new SquareCoordinate(0, 0);
        SquareCoordinate activeCoord = new SquareCoordinate(1, 1);
        mapTarget.put(targetCoord, targetHero);
        Map<SquareCoordinate, Hero> mapActive = new HashMap<>();
        mapActive.put(activeCoord, activeHero);
        Army armyTarget = new Army(mapTarget);
        Army armyActive = new Army(mapActive);

        //testEquals
        assertNotEquals(activeHero, targetHero);

        // removeTargetTest
        int oldSize = armyTarget.getHeroes().size();
        targetHero.setHp(0);
        armyTarget.killHero(targetHero.getUnitId());
        int newSize = armyTarget.getHeroes().size();
        assertNotEquals(oldSize, newSize);

        //calculateDamage
        int damageByWarlordFootman = 56;
        int damageByFootman = 59;
        int damageByArcher = 66;
        Hero footman = Footman.createInstance();
        Hero archer = Archer.createInstance();
        assertEquals(damageByWarlordFootman, activeHero.calculateDamage(targetHero));
        assertEquals(damageByFootman, activeHero.calculateDamage(footman));
        assertEquals(damageByArcher, activeHero.calculateDamage(archer));

        //isHitTest
        assertTrue(activeHero.isHit(1.0f));
        assertFalse(activeHero.isHit(0));

        //setDefenseTest
        float armor = activeHero.getArmor();
        float armorDef = activeHero.getArmor() + 0.5f;
        activeHero.setDefence();
        assertTrue(activeHero.isDefence());
        assertEquals(Math.round(armorDef), Math.round(activeHero.getArmor()));
        activeHero.cancelDefence();
        assertFalse(activeHero.isDefence());
        assertEquals(Math.round(armor), Math.round(activeHero.getArmor()));

    }
}
