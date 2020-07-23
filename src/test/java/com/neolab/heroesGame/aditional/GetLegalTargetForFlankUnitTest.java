package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Footman;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordFootman;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetLegalTargetForFlankUnitTest {

    @Test
    public void testGetCorrectTargetForFootman1() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 1), Footman.createInstance());
        heroes.put(new SquareCoordinate(1, 1), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 1), Footman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 1)));
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 1)));
        assertEquals(2, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman2() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(1, 1), Footman.createInstance());
        heroes.put(new SquareCoordinate(2, 1), WarlordFootman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 1)));
        assertEquals(1, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman3() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(2, 1), WarlordFootman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(2, 1)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman4() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 1), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 1), Footman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 1)));
        assertEquals(1, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman5() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 0), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(1, 0), Footman.createInstance());
        heroes.put(new SquareCoordinate(2, 0), Footman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 0)));
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 0)));
        assertEquals(2, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman6() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(1, 0), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 0), Footman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 0)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman7() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(2, 0), WarlordFootman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(2, 0)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman8() throws HeroExceptions {
        final SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 0), WarlordFootman.createInstance());
        heroes.put(new SquareCoordinate(2, 0), Footman.createInstance());
        final Army enemy = new Army(heroes);
        final Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 0)));
        assertEquals(1, legalTarget.size());
    }
}
