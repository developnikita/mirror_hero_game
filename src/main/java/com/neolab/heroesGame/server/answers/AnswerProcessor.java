package com.neolab.heroesGame.server.answers;

import ch.qos.logback.classic.net.SocketNode;
import com.neolab.heroesGame.aditional.SomeFunction;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Healer;
import com.neolab.heroesGame.heroes.Hero;

public final class AnswerProcessor {

    private static Player serverBot;
    private static Player player;
    private static Player activePlayer;
    private static BattleArena board;

    //todo askNewPlayer перенести в сервер
    public static void handleAnswer(Answer answer) throws HeroExceptions {
        if(AnswerValidator.validateAnswer(answer, board)){
            if(answer.getAction() == HeroActions.ATTACK){

                //получаем атакующего героя
                Hero activeHero =  board.getArmy(activePlayer.getId())
                        .getHero(answer.getActiveHero())
                        .orElseThrow(new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT));

                //если маг или арчер то первый аргумент не используется
                activeHero.toAttack(answer.getTargetUnit(), board.getArmy(player.getId()));
            }
            else if(answer.getAction() == HeroActions.HEAL){
                //получаем healer'a
                Healer activeHero = (Healer) board.getArmy(activePlayer.getId())
                        .getHero(answer.getActiveHero())
                        .orElseThrow(new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT));

                //лечим цель
                activeHero.toHeal(answer.getTargetUnit(), board.getArmy(activePlayer.getId()));
            }
            else {
                //получаем героя который хочет встать в оборону
                Hero activeHero =  board.getArmy(activePlayer.getId())
                        .getHero(answer.getActiveHero())
                        .orElseThrow(new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT));

                activeHero.setArmor(activeHero.getArmor() * (float) 1.5);
                activeHero.setDefence(true);
            }
        }
        else throw new HeroExceptions(HeroErrorCode.ERROR_ANSWER);
    }


}
