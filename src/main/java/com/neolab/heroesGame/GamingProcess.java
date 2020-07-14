package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.Army;
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
    private static Player currentPlayer;
    private static Player waitingPlayer;

    public GamingProcess() throws HeroExceptions {
        currentPlayer = new PlayerBot(1);
        waitingPlayer = new PlayerBot(2);
        setAnswerProcessorPlayerId(currentPlayer, waitingPlayer);
        AnswerProcessor.setBoard(new BattleArena(FactoryArmies.generateArmies(1, 2)));
    }

    private static void changeCurrentAndWaitingPlayers() {
        final Player temp = currentPlayer;
        currentPlayer = waitingPlayer;
        waitingPlayer = temp;
        setAnswerProcessorPlayerId(currentPlayer, waitingPlayer);
    }

    private static void setAnswerProcessorPlayerId(final Player currentPlayer, final Player waitingPlayer) {
        AnswerProcessor.setActivePlayerId(currentPlayer.getId());
        AnswerProcessor.setPlayerId(waitingPlayer.getId());
    }

    public static void main(final String[] args) {
        try {
            final GamingProcess gamingProcess = new GamingProcess();
            while (true) {
                AnswerProcessor.getBoard().toLog();

                final Player whoIsWin = someoneWhoWin();
                if (whoIsWin != null) {
                    LOGGER.info("Игрок<{}> выиграл это тяжкое сражение", whoIsWin.getId());
                    break;
                }

                if (isRoundEnd()) {
                    LOGGER.info("-----------------Начинается новый раунд---------------");
                    AnswerProcessor.getBoard().getArmies().values().forEach(Army::roundIsOver);
                }

                if (checkCanMove(currentPlayer.getId())) {
                    askPlayerProcess();
                }
                changeCurrentAndWaitingPlayers();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error(ex.getMessage());
        }
    }

    private static boolean isRoundEnd() {
        return !(checkCanMove(currentPlayer.getId()) || checkCanMove(waitingPlayer.getId()));
    }

    private static void askPlayerProcess() {
        try {
            final Answer answer = currentPlayer.getAnswer(AnswerProcessor.getBoard());
            answer.toLog();
            AnswerProcessor.handleAnswer(answer);
            AnswerProcessor.getActionEffect().toLog();
        } catch (final HeroExceptions ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private static Player someoneWhoWin() {
        Player isWinner = AnswerProcessor.getBoard().isArmyDied(currentPlayer.getId()) ? waitingPlayer : null;
        if (isWinner == null) {
            isWinner = AnswerProcessor.getBoard().isArmyDied(waitingPlayer.getId()) ? currentPlayer : null;
        }
        return isWinner;
    }

    private static boolean checkCanMove(final Integer id) {
        return !AnswerProcessor.getBoard().getArmy(id).getAvailableHero().isEmpty();
    }
}

