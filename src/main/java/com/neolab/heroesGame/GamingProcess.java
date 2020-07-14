package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FactoryArmies;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.answers.AnswerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GamingProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(GamingProcess.class);
    private Player currentPlayer;
    private Player waitingPlayer;
    private final AnswerProcessor answerProcessor;
    private final BattleArena battleArena;

    public GamingProcess() throws HeroExceptions {
        currentPlayer = new PlayerBot(1);
        waitingPlayer = new PlayerBot(2);
        battleArena = new BattleArena(FactoryArmies.generateArmies(1, 2));
        answerProcessor = new AnswerProcessor(1, 2, battleArena);
    }

    private void changeCurrentAndWaitingPlayers() {
        final Player temp = currentPlayer;
        currentPlayer = waitingPlayer;
        waitingPlayer = temp;
        setAnswerProcessorPlayerId(currentPlayer, waitingPlayer);
    }

    private void setAnswerProcessorPlayerId(final Player currentPlayer, final Player waitingPlayer) {
        answerProcessor.setActivePlayerId(currentPlayer.getId());
        answerProcessor.setWaitingPlayerId(waitingPlayer.getId());
    }

    public static void main(final String[] args) {
        try {
            final GamingProcess gamingProcess = new GamingProcess();
            LOGGER.info("-----------------Начинается великая битва---------------");
            while (true) {
                gamingProcess.battleArena.toLog();

                final Player whoIsWin = gamingProcess.someoneWhoWin();
                if (whoIsWin != null) {
                    LOGGER.info("Игрок<{}> выиграл это тяжкое сражение", whoIsWin.getId());
                    break;
                }

                if (gamingProcess.isRoundEnd()) {
                    LOGGER.info("-----------------Начинается новый раунд---------------");
                    gamingProcess.battleArena.endRound();
                }

                if (gamingProcess.checkCanMove(gamingProcess.currentPlayer.getId())) {
                    gamingProcess.askPlayerProcess();
                }
                gamingProcess.changeCurrentAndWaitingPlayers();
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private boolean isRoundEnd() {
        return !(checkCanMove(currentPlayer.getId()) || checkCanMove(waitingPlayer.getId()));
    }

    private void askPlayerProcess() throws HeroExceptions {
        final Answer answer = currentPlayer.getAnswer(battleArena);
        answer.toString();
        answerProcessor.handleAnswer(answer);
        answerProcessor.getActionEffect().toLog();
    }

    private Player someoneWhoWin() {
        Player isWinner = battleArena.isArmyDied(getCurrentPlayerId()) ? waitingPlayer : null;
        if (isWinner == null) {
            isWinner = battleArena.isArmyDied(getWaitingPlayerId()) ? currentPlayer : null;
        }
        return isWinner;
    }

    private boolean checkCanMove(final Integer id) {
        return !battleArena.haveAvailableHeroByArmyId(id);
    }

    private int getCurrentPlayerId(){
        return currentPlayer.getId();
    }

    private int getWaitingPlayerId(){
        return waitingPlayer.getId();
    }
}

