package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.heroes.Hero;

import java.util.Map;

public class AnswerValidator {

    public static boolean validateAnswer(Answer answer, Map<SquareCoordinate, Hero> heroes){
        return false;
    }

    private static boolean checkActiveHero(SquareCoordinate heroPos, Map<SquareCoordinate, Hero> heroes){
        return false;
    }

    private static boolean checkAction(HeroActions action, Map<SquareCoordinate, Hero> heroes){
        return false;
    }

    private static boolean checkTargetUnit(SquareCoordinate heroPos, Map<SquareCoordinate, Hero> heroes){
        return false;
    }
}
