package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.FabricArmies;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Footman;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.IWarlord;
import com.neolab.heroesGame.heroes.WarlordFootman;
import org.junit.Test;

import java.util.Map;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

public class SomeFunctionTest {

    @Test
    public void testGetCorrectTargetForFootman1() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 1), FabricArmies.createDefaultFootman());
        heroes.put(new SquareCoordinate(1, 1), FabricArmies.createDefaultFootman());
        heroes.put(new SquareCoordinate(2, 1), FabricArmies.createDefaultWarlordFootman());
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

        heroes.put(new SquareCoordinate(1, 1), getFootman());
        heroes.put(new SquareCoordinate(2, 1), FabricArmies.createDefaultWarlordFootman());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 1)));
        assertEquals(1, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman3() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(2, 1), FabricArmies.createDefaultWarlordFootman());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(2, 1)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman4() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 1), FabricArmies.createDefaultWarlordFootman());
        heroes.put(new SquareCoordinate(2, 1), getFootman());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 1)));
        assertEquals(1, legalTarget.size());
    }

    @Test
    public void testGetCorrectTargetForFootman5() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 0), FabricArmies.createDefaultWarlordFootman());
        heroes.put(new SquareCoordinate(1, 0), getFootman());
        heroes.put(new SquareCoordinate(2, 0), getFootman());
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

        heroes.put(new SquareCoordinate(1, 0), FabricArmies.createDefaultWarlordFootman());
        heroes.put(new SquareCoordinate(2, 0), getFootman());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(1, 0)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman7() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(2, 0), FabricArmies.createDefaultWarlordFootman());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(2, 0)));
        assertEquals(1, legalTarget.size());
    }


    @Test
    public void testGetCorrectTargetForFootman8() throws HeroExceptions {
        SquareCoordinate activeUnit = new SquareCoordinate(0, 1);
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();

        heroes.put(new SquareCoordinate(0, 0), FabricArmies.createDefaultWarlordFootman());
        heroes.put(new SquareCoordinate(2, 0), getFootman());
        Army enemy = new Army(heroes);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertTrue(legalTarget.contains(new SquareCoordinate(0, 0)));
        assertEquals(1, legalTarget.size());
    }

    public static Footman getFootman() {
        return new Footman(170, 50, (float) 0.8, (float) 0.1);
    }
}