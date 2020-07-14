package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Footman;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordFootman;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetLegalTargetForFlankUnitTest {

    @Test
    public void testGetCorrectTargetForFootman1() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 1), Footman.createInstance());
        heroes.put(new SquareCoordinate(1, 1), Footman.createInstance());
        heroes.put(new SquareCoordinate(2, 1), Footman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 1)));
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 1)));
        assertEquals(2, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman2() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(1, 1), Footman.createInstance());
        heroes.put(new SquareCoordinate(2, 1), WarlordFootman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 1)));
        assertEquals(1, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman3() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(2, 1), WarlordFootman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(2, 1)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman4() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 1), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 1), Footman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 1)));
        assertEquals(1, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman5() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 0), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(1, 0), Footman.createInstance());
        heroes.put(new SquareCoordinate(2, 0), Footman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 0)));
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 0)));
        assertEquals(2, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman6() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(1, 0), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 0), Footman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 0)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman7() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(2, 0), WarlordFootman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(2, 0)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman8() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 0), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 0), Footman.createInstance());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 0)));
        assertEquals(1, legalTarget.size());
    }

    private Army getArmyByCoords(Set<SquareCoordinate> coords) throws HeroExceptions {
        boolean isWarlordNotExist = true;
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();
        for (SquareCoordinate coord : coords) {
            if (isWarlordNotExist) {
                heroes.put(coord, WarlordFootman.createInstance());
                isWarlordNotExist = false;
            }
            heroes.put(coord, Footman.createInstance());
        }
        return new Army(heroes);
    }

    private void dataSetSquareCoordinate() {
        SquareCoordinate activeUnit1 = new SquareCoordinate(0, 1);
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(0, 1));
        coordHeroes1.add(new SquareCoordinate(1, 1));
        coordHeroes1.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget1 = new HashSet<>();
        legalTarget1.add(new SquareCoordinate(0, 1));
        legalTarget1.add(new SquareCoordinate(1, 1));
        legalTarget1.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes2 = new HashSet<>();
        coordHeroes2.add(new SquareCoordinate(0, 1));
        coordHeroes2.add(new SquareCoordinate(1, 1));
        coordHeroes2.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget2 = new HashSet<>();
        legalTarget2.add(new SquareCoordinate(0, 1));
        legalTarget2.add(new SquareCoordinate(1, 1));
        legalTarget2.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes3 = new HashSet<>();
        coordHeroes3.add(new SquareCoordinate(0, 1));
        coordHeroes3.add(new SquareCoordinate(1, 1));
        coordHeroes3.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget3 = new HashSet<>();
        legalTarget3.add(new SquareCoordinate(0, 1));
        legalTarget3.add(new SquareCoordinate(1, 1));
        legalTarget3.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes4 = new HashSet<>();
        coordHeroes4.add(new SquareCoordinate(0, 1));
        coordHeroes4.add(new SquareCoordinate(1, 1));
        coordHeroes4.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget4 = new HashSet<>();
        legalTarget4.add(new SquareCoordinate(0, 1));
        legalTarget4.add(new SquareCoordinate(1, 1));
        legalTarget4.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes5 = new HashSet<>();
        coordHeroes5.add(new SquareCoordinate(0, 1));
        coordHeroes5.add(new SquareCoordinate(1, 1));
        coordHeroes5.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget5 = new HashSet<>();
        legalTarget5.add(new SquareCoordinate(0, 1));
        legalTarget5.add(new SquareCoordinate(1, 1));
        legalTarget5.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes6 = new HashSet<>();
        coordHeroes6.add(new SquareCoordinate(0, 1));
        coordHeroes6.add(new SquareCoordinate(1, 1));
        coordHeroes6.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget6 = new HashSet<>();
        legalTarget6.add(new SquareCoordinate(0, 1));
        legalTarget6.add(new SquareCoordinate(1, 1));
        legalTarget6.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes7 = new HashSet<>();
        coordHeroes7.add(new SquareCoordinate(0, 1));
        coordHeroes7.add(new SquareCoordinate(1, 1));
        coordHeroes7.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget7 = new HashSet<>();
        legalTarget7.add(new SquareCoordinate(0, 1));
        legalTarget7.add(new SquareCoordinate(1, 1));
        legalTarget7.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> coordHeroes8 = new HashSet<>();
        coordHeroes8.add(new SquareCoordinate(0, 1));
        coordHeroes8.add(new SquareCoordinate(1, 1));
        coordHeroes8.add(new SquareCoordinate(2, 1));

        Set<SquareCoordinate> legalTarget8 = new HashSet<>();
        legalTarget8.add(new SquareCoordinate(0, 1));
        legalTarget8.add(new SquareCoordinate(1, 1));
        legalTarget8.add(new SquareCoordinate(2, 1));
    }
}
