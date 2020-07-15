package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.aditional.GetLegalTargetTest;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.spy;

public class HeroToActTest {

    @Test
    public void healerToActTest() throws HeroExceptions {
        Hero healer = Healer.createInstance();
        Map<SquareCoordinate, Integer> effect;
        SquareCoordinate firstPosition = new SquareCoordinate(0, 0);
        SquareCoordinate secondPosition = new SquareCoordinate(1, 1);
        SquareCoordinate thirdPosition = new SquareCoordinate(2, 0);

        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(firstPosition);
        coordHeroes1.add(secondPosition);
        coordHeroes1.add(thirdPosition);
        Army army = GetLegalTargetTest.getArmyByCoords(coordHeroes1);
        assertTrue(army.getHero(firstPosition).isPresent());
        assertTrue(army.getHero(secondPosition).isPresent());

        effect = healer.toAct(firstPosition, army);
        assertEquals(1, effect.size());
        assertNotNull(effect.get(firstPosition));
        assertEquals(army.getHero(firstPosition).get().getHpMax(), army.getHero(firstPosition).get().getHp());

        Hero hero = army.getHero(secondPosition).get();
        int damageForTest = 10;
        hero.setHp(hero.getHpMax() - healer.getDamage() - damageForTest);
        effect = healer.toAct(secondPosition, army);
        assertEquals(1, effect.size());
        assertNotNull(effect.get(secondPosition));
        assertEquals(healer.getDamage(), effect.get(secondPosition).intValue());
        assertEquals(hero.getHpMax() - damageForTest, hero.getHp());

        try {
            healer.toAct(new SquareCoordinate(2, 2), army);
            fail();
        } catch (HeroExceptions ex) {
            assertEquals(HeroErrorCode.ERROR_TARGET_ATTACK, ex.getHeroErrorCode());
        }
    }

    @Test
    public void soloTargetAttackToActTest() throws HeroExceptions {
        Hero archer = spy(Archer.createInstance());
        Map<SquareCoordinate, Integer> effect;
        SquareCoordinate firstPosition = new SquareCoordinate(0, 0);
        SquareCoordinate secondPosition = new SquareCoordinate(1, 1);
        SquareCoordinate thirdPosition = new SquareCoordinate(2, 0);
        Mockito.when(archer.isHit(anyFloat())).thenReturn(true);

        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(firstPosition);
        coordHeroes1.add(secondPosition);
        coordHeroes1.add(thirdPosition);
        Army army = GetLegalTargetTest.getArmyByCoords(coordHeroes1);
        assertTrue(army.getHero(firstPosition).isPresent());
        assertTrue(army.getHero(secondPosition).isPresent());

        Hero hero = army.getHero(firstPosition).get();
        effect = archer.toAct(firstPosition, army);
        assertEquals(1, effect.size());
        assertNotNull(effect.get(firstPosition));
        int expectedDamage = Math.round(archer.getDamage() - archer.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(firstPosition).intValue());

        hero = army.getHero(secondPosition).get();
        effect = archer.toAct(secondPosition, army);
        assertEquals(1, effect.size());
        assertNotNull(effect.get(secondPosition));
        expectedDamage = Math.round(archer.getDamage() - archer.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(secondPosition).intValue());

        try {
            archer.toAct(new SquareCoordinate(2, 2), army);
            fail();
        } catch (HeroExceptions ex) {
            assertEquals(HeroErrorCode.ERROR_TARGET_ATTACK, ex.getHeroErrorCode());
        }
    }

    @Test
    public void magicianTargetAttackToActTest() throws HeroExceptions {
        Hero magician = spy(Magician.createInstance());
        Map<SquareCoordinate, Integer> effect;
        SquareCoordinate firstPosition = new SquareCoordinate(0, 0);
        SquareCoordinate secondPosition = new SquareCoordinate(1, 1);
        SquareCoordinate thirdPosition = new SquareCoordinate(2, 0);
        Mockito.when(magician.isHit(anyFloat())).thenReturn(true);

        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(firstPosition);
        coordHeroes1.add(secondPosition);
        coordHeroes1.add(thirdPosition);
        Army army = GetLegalTargetTest.getArmyByCoords(coordHeroes1);
        assertTrue(army.getHero(firstPosition).isPresent());
        assertTrue(army.getHero(secondPosition).isPresent());

        effect = magician.toAct(firstPosition, army);
        assertEquals(3, effect.size());

        Hero hero = army.getHero(firstPosition).get();
        assertNotNull(effect.get(firstPosition));
        int expectedDamage = Math.round(magician.getDamage() - magician.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(firstPosition).intValue());

        hero = army.getHero(secondPosition).get();
        assertNotNull(effect.get(firstPosition));
        expectedDamage = Math.round(magician.getDamage() - magician.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(secondPosition).intValue());

        hero = army.getHero(thirdPosition).get();
        assertNotNull(effect.get(firstPosition));
        expectedDamage = Math.round(magician.getDamage() - magician.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(thirdPosition).intValue());
    }

    @Test
    public void vampireTargetAttackToActTest() throws HeroExceptions {
        Hero vampire = spy(WarlordVampire.createInstance());
        Map<SquareCoordinate, Integer> effect;
        SquareCoordinate firstPosition = new SquareCoordinate(0, 0);
        SquareCoordinate secondPosition = new SquareCoordinate(1, 1);
        SquareCoordinate thirdPosition = new SquareCoordinate(2, 0);
        Mockito.when(vampire.isHit(anyFloat())).thenReturn(true);
        vampire.setHp(1);

        Set<SquareCoordinate> coordHeroes1 = new HashSet<>();
        coordHeroes1.add(firstPosition);
        coordHeroes1.add(secondPosition);
        coordHeroes1.add(thirdPosition);
        Army army = GetLegalTargetTest.getArmyByCoords(coordHeroes1);
        assertTrue(army.getHero(firstPosition).isPresent());
        assertTrue(army.getHero(secondPosition).isPresent());

        effect = vampire.toAct(firstPosition, army);
        int health = 1;
        for (Integer value : effect.values()) {
            health += value;
        }
        assertEquals(3, effect.size());
        assertEquals(health, vampire.getHp());

        Hero hero = army.getHero(firstPosition).get();
        assertNotNull(effect.get(firstPosition));
        int expectedDamage = Math.round(vampire.getDamage() - vampire.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(firstPosition).intValue());

        hero = army.getHero(secondPosition).get();
        assertNotNull(effect.get(firstPosition));
        expectedDamage = Math.round(vampire.getDamage() - vampire.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(secondPosition).intValue());

        hero = army.getHero(thirdPosition).get();
        assertNotNull(effect.get(firstPosition));
        expectedDamage = Math.round(vampire.getDamage() - vampire.getDamage() * hero.getArmor());
        assertEquals(expectedDamage, effect.get(thirdPosition).intValue());
    }
}
