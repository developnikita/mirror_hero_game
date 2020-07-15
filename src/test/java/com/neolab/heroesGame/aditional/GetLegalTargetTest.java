package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Footman;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordFootman;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class GetLegalTargetTest {

    @Parameterized.Parameters()
    public static Iterable<Object[]> data() throws HeroExceptions {
        return Arrays.asList(new Object[][]{
                {set1(), army1()}, {set2(), army2()}, {set3(), army3()}, {set4(), army4()},
                {set5(), army5()}, {set6(), army6()}, {set7(), army7()}, {set8(), army8()},
        });
    }

    private Set<SquareCoordinate> legalCoordinates;
    private Army enemy;

    public GetLegalTargetTest(Set<SquareCoordinate> legalCoordinates, Army enemy) {
        this.legalCoordinates = legalCoordinates;
        this.enemy = enemy;
    }

    @Test
    public void getTargetTest() {
        SquareCoordinate activeUnit = new SquareCoordinate(1, 1);
        Set<SquareCoordinate> legalTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemy);
        assertEquals(legalCoordinates.size(), legalTarget.size());
        assertTrue(legalCoordinates.containsAll(legalTarget));
    }

    private static Set<SquareCoordinate> set1() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(0, 1));
        legalTarget.add(new SquareCoordinate(1, 1));
        legalTarget.add(new SquareCoordinate(2, 1));
        return legalTarget;
    }

    private static Army army1() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(0, 1));
        coordHeroes1.add(new SquareCoordinate(1, 1));
        coordHeroes1.add(new SquareCoordinate(2, 1));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set2() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(0, 1));
        legalTarget.add(new SquareCoordinate(2, 1));
        return legalTarget;
    }

    private static Army army2() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(0, 1));
        coordHeroes1.add(new SquareCoordinate(2, 1));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set3() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(1, 1));
        legalTarget.add(new SquareCoordinate(2, 1));
        return legalTarget;
    }

    private static Army army3() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(1, 1));
        coordHeroes1.add(new SquareCoordinate(2, 1));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set4() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(2, 1));
        return legalTarget;
    }

    private static Army army4() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(2, 1));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set5() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(0, 0));
        legalTarget.add(new SquareCoordinate(1, 0));
        legalTarget.add(new SquareCoordinate(2, 0));
        return legalTarget;
    }

    private static Army army5() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(0, 0));
        coordHeroes1.add(new SquareCoordinate(1, 0));
        coordHeroes1.add(new SquareCoordinate(2, 0));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set6() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(0, 0));
        return legalTarget;
    }

    private static Army army6() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(0, 0));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set7() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(2, 0));
        return legalTarget;
    }

    private static Army army7() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(2, 0));
        return getArmyByCoords(coordHeroes1);
    }

    private static Set<SquareCoordinate> set8() {
        Set<SquareCoordinate> legalTarget = new HashSet<>();
        legalTarget.add(new SquareCoordinate(1, 0));
        return legalTarget;
    }

    private static Army army8() throws HeroExceptions {
        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(new SquareCoordinate(1, 0));
        return getArmyByCoords(coordHeroes1);
    }

    /**
     * Создает армию из футфайтеров с варлордом для того, чтобы не генерировать армию в каждом тесте
     */
    public static Army getArmyByCoords(Set<SquareCoordinate> coords) throws HeroExceptions {
        boolean isWarlordNotExist = true;
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();
        for (SquareCoordinate coord : coords) {
            if (isWarlordNotExist) {
                heroes.put(coord, WarlordFootman.createInstance());
                isWarlordNotExist = false;
                continue;
            }
            heroes.put(coord, Footman.createInstance());
        }
        return new Army(heroes);
    }
}
