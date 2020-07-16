package com.neolab.heroesGame;

import com.neolab.heroesGame.aditional.StatisticWriter;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FactoryArmies;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.answers.AnswerProcessor;
import com.neolab.heroesGame.server.dto.ClientResponse;
import com.neolab.heroesGame.server.dto.ServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerImitation {
    public static final Integer MAX_ROUND = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(GamingProcess.class);
    private ClientPlayerImitation currentPlayer;
    private ClientPlayerImitation waitingPlayer;
    private final AnswerProcessor answerProcessor;
    private final BattleArena battleArena;
    private int counter;

    public ServerImitation() throws HeroExceptions {
        currentPlayer = new ClientPlayerImitation(1, "Bot1");
        waitingPlayer = new ClientPlayerImitation(2, "Bot2");
        battleArena = new BattleArena(FactoryArmies.generateArmies(1, 2));
        answerProcessor = new AnswerProcessor(1, 2, battleArena);
        counter = 0;
    }

    private void changeCurrentAndWaitingPlayers() {
        final ClientPlayerImitation temp = currentPlayer;
        currentPlayer = waitingPlayer;
        waitingPlayer = temp;
        setAnswerProcessorPlayerId(currentPlayer.getPlayer(), waitingPlayer.getPlayer());
    }

    private void setAnswerProcessorPlayerId(final Player currentPlayer, final Player waitingPlayer) {
        answerProcessor.setActivePlayerId(currentPlayer.getId());
        answerProcessor.setWaitingPlayerId(waitingPlayer.getId());
    }

    public static void main(final String[] args) {
        try {
            final ServerImitation serverImitation = new ServerImitation();
            LOGGER.info("-----------------Начинается великая битва---------------");
            while (true) {
                serverImitation.battleArena.toLog();

                final ClientPlayerImitation whoIsWin = serverImitation.someoneWhoWin();
                if (whoIsWin != null) {
                    StatisticWriter.writePlayerStatistic(whoIsWin.getPlayer().getName(), whoIsWin.getPlayer().equals(serverImitation.currentPlayer)?serverImitation.waitingPlayer.getPlayer().getName():serverImitation.currentPlayer.getPlayer().getName());
                    LOGGER.info("Игрок<{}> выиграл это тяжкое сражение", whoIsWin.getPlayer().getId());
                    break;
                }

                if (!serverImitation.battleArena.canSomeoneAct()) {
                    serverImitation.counter++;
                    if (serverImitation.counter > MAX_ROUND) {
                        LOGGER.info("Поединок закончился ничьей");
                        break;
                    }
                    LOGGER.info("-----------------Начинается раунд <{}>---------------", serverImitation.counter);
                    serverImitation.battleArena.endRound();
                }

                if (serverImitation.checkCanMove(serverImitation.currentPlayer.getPlayer().getId())) {
                    serverImitation.askPlayerProcess();
                }
                serverImitation.changeCurrentAndWaitingPlayers();
            }

        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void askPlayerProcess() throws Exception {
        final ServerRequest request = new ServerRequest(battleArena, answerProcessor.getActionEffect());
        final String response = currentPlayer.getAnswer(request.boardJson, request.actionEffect);
        final Answer answer = new ClientResponse(response).getAnswer();
        answer.toLog();
        answerProcessor.handleAnswer(answer);
        answerProcessor.getActionEffect().toLog();
    }

    private ClientPlayerImitation someoneWhoWin() {
        ClientPlayerImitation isWinner = battleArena.isArmyDied(getCurrentPlayerId()) ? waitingPlayer : null;
        if (isWinner == null) {
            isWinner = battleArena.isArmyDied(getWaitingPlayerId()) ? currentPlayer : null;
        }
        return isWinner;
    }

    private boolean checkCanMove(final Integer id) {
        return !battleArena.haveAvailableHeroByArmyId(id);
    }

    private int getCurrentPlayerId() {
        return currentPlayer.getPlayerId();
    }

    private int getWaitingPlayerId() {
        return waitingPlayer.getPlayerId();
    }
}


