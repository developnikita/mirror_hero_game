package com.neolab.heroesGame.arena;

import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FabricArmiesTest {

    @Test
    public void testCreateBattaleArena() {
        Map<Integer, Army> armies = FabricArmies.generateArmyes(1, 2);
        assertEquals(6, armies.get(1).getHeroes().size());
        assertEquals(6, armies.get(2).getHeroes().size());
        assertNotNull(armies.get(1).getWarlord().get());
        assertNotNull(armies.get(2).getWarlord().get());
    }
}