package com.neolab.heroesGame;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(GamingProcess.class);
    private ClientPlayerImitation currentPlayer;
    private ClientPlayerImitation waitingPlayer;
    private final AnswerProcessor answerProcessor;
    private final BattleArena battleArena;

    public ServerImitation() throws HeroExceptions {
        currentPlayer = new ClientPlayerImitation(1);
        waitingPlayer = new ClientPlayerImitation(2);
        battleArena = new BattleArena(FactoryArmies.generateArmies(1, 2));
        answerProcessor = new AnswerProcessor(1, 2, battleArena);
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
            final ServerImitation gamingProcess = new ServerImitation();
            LOGGER.info("-----------------Начинается великая битва---------------");
            while (true) {
                gamingProcess.battleArena.toLog();

                final ClientPlayerImitation whoIsWin = gamingProcess.someoneWhoWin();
                if (whoIsWin != null) {
                    LOGGER.info("Игрок<{}> выиграл это тяжкое сражение", whoIsWin.getPlayer().getId());
                    break;
                }

                if (!gamingProcess.battleArena.canSomeoneAct()) {
                    LOGGER.info("-----------------Начинается новый раунд---------------");
                    gamingProcess.battleArena.endRound();
                }

                if (gamingProcess.checkCanMove(gamingProcess.currentPlayer.getPlayer().getId())) {
                    gamingProcess.askPlayerProcess();
                }
                gamingProcess.changeCurrentAndWaitingPlayers();
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void askPlayerProcess() throws Exception {
        final String request = new ServerRequest(battleArena).boardJson;
        final String response = currentPlayer.getAnswer(request);
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
        return currentPlayer.getPlayer().getId();
    }

    private int getWaitingPlayerId() {
        return waitingPlayer.getPlayer().getId();
    }
}


