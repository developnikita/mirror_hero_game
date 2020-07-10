package com.neolab.heroesGame.server.networkServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neolab.heroesGame.aditional.SomeFunction;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FabricArmies;
import com.neolab.heroesGame.client.networkService.Client;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.answers.AnswerProcessor;
import com.neolab.heroesGame.server.dto.ClientResponse;
import com.neolab.heroesGame.server.dto.ServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private final Map<Integer, Client> clients = new HashMap<>();

    public Server() {
        super();
        clients.put(1, new Client(1));
        clients.put(2, new Client(2));
        AnswerProcessor.setPlayerId(2);
        AnswerProcessor.setBoard(new BattleArena(FabricArmies.generateArmies(1, 2)));
    }

    /**
     * главный цикл игры
     * опрашиваем игроков по очереди
     * если у одного закончились доступные ходы
     * другой ходит подряд до конца текущего раунда
     */
    public void playingProcess() throws JsonProcessingException, HeroExceptions {
        LOGGER.info("|-------------------------------------- START GAME ---------------------------------------------------|");

        Client[] arrClients = clients.values().toArray(new Client[2]);
        int endRes;

        for (int i = 0; i < arrClients.length;) {
            if (i == 1) {
                i = 0;
                if (checkAvailableHero(arrClients[i + 1].getPlayer().getId())) {
                    askNextPlayer(i + 1, arrClients);
                    // перед тем как спросить другого игрока проверям жив ли его лорд
                    checkDeathWarlord(arrClients[i].getPlayer().getId());
                }
            }
            else {
                i = 1;
                if(checkAvailableHero(arrClients[i - 1].getPlayer().getId())) {
                    askNextPlayer(i - 1, arrClients);
                    checkDeathWarlord(arrClients[i].getPlayer().getId());
                }
            }
            //проверка завершения игры
            endRes = checkEndGame();
            if (endRes > 0) {
                LOGGER.info(String.format("Пользователь c id = [%d] победил", +endRes));
                return;
            }
            //в конце раунда доступны все живые герои
            if (checkEndRound()) {
                AnswerProcessor.getBoard().getArmies().values().forEach(Army::setAvailableHeroes);
            }
        }
    }

    /**
     * спрашиваем следующего клиента
     *
     * @param nextClient номер след. клиента
     * @param arrClients массив клиентов
     */
    private void askNextPlayer(int nextClient, Client[] arrClients) throws JsonProcessingException, HeroExceptions {
//        ServerRequest serverRequest = new ServerRequest(AnswerProcessor.getBoard(), AnswerProcessor.getActionEffect());
//        String answerJson = arrClients[nextClient].getClientAnswer(serverRequest.getBoardJson(), serverRequest.getActionEffectJson());
//        ClientResponse clientResponse = new ClientResponse(answerJson);
//        Answer answerClient = clientResponse.getAnswer();
//        AnswerProcessor.handleAnswer(answerClient);

        //todo пока отключил Dto
        Answer answerClient = arrClients[nextClient].getClientAnswer(AnswerProcessor.getBoard()); //AnswerProcessor.getActionEffect()
        AnswerProcessor.setActivePlayerId(answerClient.getPlayerId());
        answerClient.toLog();
        AnswerProcessor.handleAnswer(answerClient);
        AnswerProcessor.getActionEffect().toLog();
        AnswerProcessor.getBoard().toLog();
        AnswerProcessor.setPlayerId(answerClient.getPlayerId());
    }

    /**
     * @return id победившего игрока
     */
    private int checkEndGame() {
        int playerId = AnswerProcessor.getPlayerId();

        if (AnswerProcessor.getBoard().isArmyDied(playerId)) {
            return AnswerProcessor.getPlayerId();
        }
        return 0;
    }

    /**
     * проверка конца текущего раунда игры
     *
     * @return если у обоих игроков нет доступных героев значить уже все походили
     * p.s. или уже все на доске погибли...
     */
    private boolean checkEndRound() {
        int countAvailableHero = AnswerProcessor.getBoard().getArmies().values().stream().map(
                army -> army.getAvailableHero().size()).reduce(0, Integer::sum);
        return countAvailableHero == 0;
    }

    private boolean checkAvailableHero(int playerId) {
        return AnswerProcessor.getBoard().getArmy(playerId).getAvailableHero().size() != 0;
    }

    private void checkDeathWarlord(int playerId) {
        Army army = AnswerProcessor.getBoard().getArmy(playerId);
        if (army.isWarlordAlive()) {
            army.cancelImprove();
        }
    }
}