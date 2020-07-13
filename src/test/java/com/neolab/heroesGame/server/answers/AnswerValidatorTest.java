package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FactoryArmies;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Hero;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AnswerValidatorTest {

    @Test
    public void footmanTest() throws HeroExceptions {
        BattleArena arena = getBatleArena();
        SquareCoordinate activeHero = new SquareCoordinate(2, 1);
        int playerId = 1;
        boolean isValidate;

        SquareCoordinate targetHero = new SquareCoordinate(2, 1);
        Answer answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(1, 1);
        answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 1);
        answer = new Answer(activeHero, HeroActions.DEFENCE, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 1);
        answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            AnswerValidator.isAnswerValidate(answer, arena);
            fail();
        } catch (HeroExceptions ex) {
            assertEquals(HeroErrorCode.ERROR_TARGET_ATTACK, ex.getHeroErrorCode());
        }

        targetHero = new SquareCoordinate(0, 1);
        answer = new Answer(activeHero, HeroActions.HEAL, targetHero, playerId);
        try {
            AnswerValidator.isAnswerValidate(answer, arena);
            fail();
        } catch (HeroExceptions ex) {
            assertEquals(HeroErrorCode.ERROR_UNIT_HEAL, ex.getHeroErrorCode());
        }
    }

    @Test
    public void archerTest() throws HeroExceptions {
        BattleArena arena = getBatleArena();
        SquareCoordinate activeHero = new SquareCoordinate(2, 0);
        int playerId = 1;
        boolean isValidate;

        SquareCoordinate targetHero = new SquareCoordinate(2, 0);
        Answer answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(1, 1);
        answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 1);
        answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 0);
        answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(-1, -1);
        answer = new Answer(activeHero, HeroActions.DEFENCE, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 1);
        answer = new Answer(activeHero, HeroActions.HEAL, targetHero, playerId);
        try {
            AnswerValidator.isAnswerValidate(answer, arena);
            fail();
        } catch (HeroExceptions ex) {
            assertEquals(HeroErrorCode.ERROR_UNIT_HEAL, ex.getHeroErrorCode());
        }
    }

    @Test
    public void healerTest() throws HeroExceptions {
        BattleArena arena = getBatleArena();
        SquareCoordinate activeHero = new SquareCoordinate(0, 0);
        int playerId = 1;
        boolean isValidate;

        SquareCoordinate targetHero = new SquareCoordinate(2, 0);
        Answer answer = new Answer(activeHero, HeroActions.HEAL, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(1, 1);
        answer = new Answer(activeHero, HeroActions.HEAL, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 1);
        answer = new Answer(activeHero, HeroActions.HEAL, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(0, 0);
        answer = new Answer(activeHero, HeroActions.HEAL, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(-1, -1);
        answer = new Answer(activeHero, HeroActions.DEFENCE, targetHero, playerId);
        try {
            isValidate = AnswerValidator.isAnswerValidate(answer, arena);
            assertTrue(isValidate);
        } catch (HeroExceptions ex) {
            fail();
        }

        targetHero = new SquareCoordinate(-1, -1);
        answer = new Answer(activeHero, HeroActions.ATTACK, targetHero, playerId);
        try {
            AnswerValidator.isAnswerValidate(answer, arena);
            fail();
        } catch (HeroExceptions ex) {
            assertEquals(HeroErrorCode.ERROR_UNIT_ATTACK, ex.getHeroErrorCode());
        }
    }

    private BattleArena getBatleArena() throws HeroExceptions {
        Map<Integer, Army> armies = new HashMap<>();
        armies.put(1, getArmy());
        armies.put(2, getArmy());
        return new BattleArena(armies);
    }

    private Army getArmy() throws HeroExceptions {
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();
        heroes.put(new SquareCoordinate(0, 0), FactoryArmies.createDefaultHealer());
        heroes.put(new SquareCoordinate(1, 0), FactoryArmies.createDefaultMagician());
        heroes.put(new SquareCoordinate(2, 0), FactoryArmies.createDefaultArcher());
        heroes.put(new SquareCoordinate(0, 1), FactoryArmies.createDefaultWarlordFootman());
        heroes.put(new SquareCoordinate(1, 1), FactoryArmies.createDefaultFootman());
        heroes.put(new SquareCoordinate(2, 1), FactoryArmies.createDefaultFootman());
        return new Army(heroes);
    }

}