package com.neolab.heroesGame.server.serverNetwork;

import com.neolab.heroesGame.aditional.StatisticWriter;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FactoryArmies;
import com.neolab.heroesGame.enumerations.GameEvent;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.samplesSockets.PlayerSocket;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.answers.AnswerProcessor;
import com.neolab.heroesGame.server.dto.ClientResponse;
import com.neolab.heroesGame.server.dto.ExtendedServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;


public class GameServer {
    public static final Integer MAX_ROUND = 15;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);
    private PlayerSocket currentPlayer;
    private PlayerSocket waitingPlayer;
    private final AnswerProcessor answerProcessor;
    private final BattleArena battleArena;
    private int counter;

    public GameServer(PlayerSocket playerOne, PlayerSocket playerTwo) throws Exception {
        currentPlayer = playerOne;
        waitingPlayer = playerTwo;
        battleArena = new BattleArena(FactoryArmies.generateArmies(currentPlayer.getPlayerId(), waitingPlayer.getPlayerId()));
        answerProcessor = new AnswerProcessor(currentPlayer.getPlayerId(), waitingPlayer.getPlayerId(), battleArena);
        counter = 0;
    }

    public void gameProcess() throws IOException, HeroExceptions, InterruptedException {
        LOGGER.info("-----------------Начинается великая битва---------------");

        while (true){
            final Optional<PlayerSocket> whoIsWin = someoneWhoWin();
            if (whoIsWin.isPresent()) {
                someoneWin(whoIsWin.get());
                waitingPlayer.send(ExtendedServerRequest.getRequestString(
                        GameEvent.WAIT_ITS_NOT_YOUR_TURN, battleArena, answerProcessor.getActionEffect()));
                currentPlayer.send(ExtendedServerRequest.getRequestString(
                        GameEvent.WAIT_ITS_NOT_YOUR_TURN, battleArena, answerProcessor.getActionEffect()));
                break;
            }

            if (!battleArena.canSomeoneAct()) {
                counter++;
                if (counter > MAX_ROUND) {
                    StatisticWriter.writePlayerDrawStatistic(currentPlayer.getPlayerName(),
                            waitingPlayer.getPlayerName());
                    LOGGER.info("Поединок закончился ничьей");
                    break;
                }
                LOGGER.info("-----------------Начинается раунд <{}>---------------", counter);
                battleArena.endRound();
            }

            if (checkCanMove(currentPlayer.getPlayerId())) {
                askPlayerProcess();
            }
            changeCurrentAndWaitingPlayers();
        }
    }



    private void changeCurrentAndWaitingPlayers() {
        final PlayerSocket temp = currentPlayer;
        currentPlayer = waitingPlayer;
        waitingPlayer = temp;
        setAnswerProcessorPlayerId(currentPlayer.getPlayerId(), waitingPlayer.getPlayerId());
    }

    private void setAnswerProcessorPlayerId(int currentPlayerId, int waitingPlayerId) {
        answerProcessor.setActivePlayerId(currentPlayerId);
        answerProcessor.setWaitingPlayerId(waitingPlayerId);
    }

    private void askPlayerProcess() throws HeroExceptions, IOException, InterruptedException {
        battleArena.toLog();
        waitingPlayer.send(ExtendedServerRequest.getRequestString(
                GameEvent.WAIT_ITS_NOT_YOUR_TURN, battleArena, answerProcessor.getActionEffect()));
        currentPlayer.send(ExtendedServerRequest.getRequestString(
                GameEvent.NOW_YOUR_TURN, battleArena, answerProcessor.getActionEffect()));
        final String response;
        if(checkInputStreamReady()){
            response = currentPlayer.getIn().readLine();
        }
        else {
            //если игрок не отвечает уничтожаем его армию
            battleArena.diedArmy(currentPlayer.getPlayerId());
            return;
        }
        final Answer answer = new ClientResponse(response).getAnswer();
        answer.toLog();
        answerProcessor.handleAnswer(answer);
        answerProcessor.getActionEffect().toLog();
    }

    private Optional<PlayerSocket> someoneWhoWin() {
        PlayerSocket isWinner = battleArena.isArmyDied(getCurrentPlayerId()) ? waitingPlayer : null;
        if (isWinner == null) {
            isWinner = battleArena.isArmyDied(getWaitingPlayerId()) ? currentPlayer : null;
        }
        return Optional.ofNullable(isWinner);
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

    private void someoneWin(PlayerSocket winner) throws IOException {
        PlayerSocket loser = getLoser(winner);
        StatisticWriter.writePlayerWinStatistic(winner.getPlayerName(), loser.getPlayerName());
        battleArena.toLog();
        LOGGER.info("Игрок<{}> выиграл это тяжкое сражение", winner.getPlayerId());
        winner.send(ExtendedServerRequest.getRequestString(
                GameEvent.YOU_WIN_GAME, battleArena, answerProcessor.getActionEffect()));
        loser.send(ExtendedServerRequest.getRequestString(
                GameEvent.YOU_LOSE_GAME, battleArena, answerProcessor.getActionEffect()));
    }

    private PlayerSocket getLoser(PlayerSocket winner) {
        return winner.equals(currentPlayer) ? waitingPlayer : currentPlayer;
    }

    /**
     * опрашиваем клиента по таймаюту до 3 раз, или до ответа
     * делаем небольшую задежку т.к. у клиентов пинг
     * @return флаг готовность входного потока от клиета
     */
    private boolean checkInputStreamReady() throws IOException, InterruptedException {
        for(int i = 0; i < 3; i++ ){
            Thread.sleep(50);
            if(currentPlayer.getIn().ready()){
                return true;
            }
            Thread.sleep(500);
        }
        return false;
    }
}


