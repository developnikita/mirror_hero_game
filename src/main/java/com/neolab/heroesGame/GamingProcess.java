package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FabricArmies;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.answers.AnswerProcessor;
import com.neolab.heroesGame.server.networkServiceServer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GamingProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static Player currentPlayer;
    private static Player waitingPlayer;

    public GamingProcess() {
        currentPlayer = new PlayerBot(1);
        waitingPlayer = new PlayerBot(2);
        setAnswerProcessorPlayerId(currentPlayer, waitingPlayer);
        AnswerProcessor.setBoard(new BattleArena(FabricArmies.generateArmies(1, 2)));
    }

    private static void changeCurrentAndWaitingPlayers() {
        Player temp = currentPlayer;
        currentPlayer = waitingPlayer;
        waitingPlayer = temp;
        setAnswerProcessorPlayerId(currentPlayer, waitingPlayer);
    }

    private static void setAnswerProcessorPlayerId(Player currentPlayer, Player waitingPlayer) {
        AnswerProcessor.setActivePlayerId(currentPlayer.getId());
        AnswerProcessor.setPlayerId(waitingPlayer.getId());
    }

    public static void main(String[] args) {
        try {
            GamingProcess gamingProcess = new GamingProcess();
            while (true) {
                AnswerProcessor.getBoard().toLog();

                Player whoIsWin = isSomeOneWin();
                if (whoIsWin != null) {
                    LOGGER.info(String.format("Игрок<%d> выиграл это тяжкое сражение", whoIsWin.getId()));
                    break;
                }

                if (isRoundEnd()) {
                    LOGGER.info("-----------------Начинается новый раунд---------------");
                    AnswerProcessor.getBoard().getArmies().values().forEach(Army::setAvailableHeroes);
                }

                if (checkCanMove(gamingProcess.currentPlayer.getId())) {
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
            Answer answer = currentPlayer.getAnswer(AnswerProcessor.getBoard());
            answer.toLog();
            AnswerProcessor.handleAnswer(answer);
            AnswerProcessor.getActionEffect().toLog();
        } catch (HeroExceptions ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private static Player isSomeOneWin() {
        Player isWinner = AnswerProcessor.getBoard().isArmyDied(currentPlayer.getId()) ? waitingPlayer : null;
        if (isWinner == null) {
            isWinner = AnswerProcessor.getBoard().isArmyDied(waitingPlayer.getId()) ? currentPlayer : null;
        }
        return isWinner;
    }

    private static boolean checkCanMove(Integer id) {
        return !AnswerProcessor.getBoard().getArmy(id).getAvailableHero().isEmpty();
    }
}

