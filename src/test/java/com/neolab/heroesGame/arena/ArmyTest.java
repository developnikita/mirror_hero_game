package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Footman;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordFootman;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ArmyTest {
    @Test
    public void ArmyMethodsTest() throws HeroExceptions {
        Hero warlord = WarlordFootman.createInstance();
        Hero footman = Footman.createInstance();
        int defaultHpWarlord = warlord.getHp();
        int defaultDamageWarlord = warlord.getDamage();
        int defaultHp = footman.getHp();
        int defaultDamage = footman.getDamage();

        Map<SquareCoordinate, Hero> armyMap = new HashMap<>();
        SquareCoordinate warlordCoord = new SquareCoordinate(0, 0);
        SquareCoordinate footmanCoord = new SquareCoordinate(1, 1);
        armyMap.put(warlordCoord, warlord);
        armyMap.put(footmanCoord, footman);
        Army army = new Army(armyMap);

        //improveTest
        assertNotEquals(defaultDamage, footman.getDamage());
        assertNotEquals(defaultHp, footman.getHp());
        assertNotEquals(defaultDamageWarlord, warlord.getDamage());
        assertNotEquals(defaultHpWarlord, warlord.getHp());

        //cancelImproveTest
        army.cancelImprove();
        assertEquals(defaultDamage, footman.getDamage());
        assertEquals(defaultHp, footman.getHp());
        assertEquals(defaultDamageWarlord, warlord.getDamage());
        assertEquals(defaultHpWarlord, warlord.getHp());

        //killHeroTest
        warlord.setHp(0);
        army.killHero(warlord.getUnitId());
        assertEquals(army.getHeroes().size(), 1);
        assertEquals(army.getAvailableHeroes().size(), 1);
        footman.setHp(0);
        army.killHero(footman.getUnitId());
        assertEquals(army.getHeroes().size(), 0);
        assertEquals(army.getAvailableHeroes().size(), 0);

    }
}
