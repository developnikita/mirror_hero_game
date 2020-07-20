package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FactoryArmies;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CloneTest {

    @Test
    public void cloneArcherTest() {
        Hero hero = Archer.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof Archer);
    }

    @Test
    public void cloneFootmanTest() {
        Hero hero = Footman.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof Footman);
    }

    @Test
    public void cloneHealerTest() {
        Hero hero = Healer.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof Healer);
    }

    @Test
    public void cloneMagicianTest() {
        Hero hero = Magician.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof Magician);
    }

    @Test
    public void cloneWarlordFootmanTest() {
        Hero hero = WarlordFootman.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof WarlordFootman);
    }

    @Test
    public void cloneWarlordMagicianTest() {
        Hero hero = WarlordMagician.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof WarlordMagician);
    }

    @Test
    public void cloneWarlordVampireTest() {
        Hero hero = WarlordVampire.createInstance();
        Hero clone = hero.clone();
        assertEquals(hero, clone);
        assertNotSame(hero, clone);
        assertTrue(clone instanceof WarlordVampire);
    }

    @Test
    public void cloneArmyTest() throws HeroExceptions {
        Army army = FactoryArmies.createRandomArmy();
        Army clone = FactoryArmies.cloneArmy(army);
        assertEquals(army, clone);
        assertNotSame(army, clone);


        assertEquals(army.getHeroes(), clone.getHeroes());
        assertNotSame(army.getHeroes(), clone.getHeroes());
        assertEquals(army.getWarlord(), clone.getWarlord());
        assertNotSame(army.getWarlord(), clone.getWarlord());
        assertEquals(army.getAvailableHeroes(), clone.getAvailableHeroes());
        assertNotSame(army.getAvailableHeroes(), clone.getAvailableHeroes());

        army.getHeroes().keySet().forEach((key -> {
            assertEquals(army.getHero(key), clone.getHero(key));
            if (army.getHero(key).isPresent() && clone.getHero(key).isPresent()) {
                assertNotSame(army.getHero(key).get(), clone.getHero(key).get());
            } else if (army.getHero(key).isPresent() || clone.getHero(key).isPresent()) {
                fail();
            }
        }));

        army.getAvailableHeroes().keySet().forEach((key) -> {
            assertEquals(army.getAvailableHeroes().get(key), clone.getAvailableHeroes().get(key));
            assertNotSame(army.getAvailableHeroes().get(key), clone.getAvailableHeroes().get(key));
        });
    }

    @Test
    public void cloneArenaTest() throws HeroExceptions {
        BattleArena arena = new BattleArena(FactoryArmies.generateArmies(1, 2));
        BattleArena clone = BattleArena.getCloneBattleArena(arena);

        assertEquals(arena, clone);
        assertNotSame(arena, clone);

        arena.getArmies().keySet().forEach((key) -> {
            assertEquals(arena.getArmy(key), clone.getArmy(key));
            assertNotSame(arena.getArmy(key), clone.getArmy(key));
        });
    }
}
