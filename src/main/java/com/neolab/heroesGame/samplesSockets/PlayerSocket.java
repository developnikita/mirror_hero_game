package com.neolab.heroesGame.samplesSockets;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerSocket {

    private final Socket socket;
    private final BufferedReader in; // поток чтения из сокета
    private final BufferedWriter out; // поток завписи в сокет
    private final int playerId;
    private final String playerName;
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    /**
     * Для общения с клиентом необходим сокет (адресные данные)
     *
     * @param socket сокет
     */
    PlayerSocket(final Socket socket, final int playerId, final String playerName) throws IOException {
        this.playerId = playerId;
        this.playerName = playerName;
        this.socket = socket;
        // если потоки ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Обработать сообщение
     *
     * @return {@code false} окончить работу после обработки сообщения, иначе {@code true}
     * @throws IOException ошибка ввода-вывода
     */
    private boolean processMessage() throws IOException {
        final String answer = in.readLine();
        send(answer);
        return true;
    }

//    private void sendMessage(final String message) throws IOException {
//        for (ServerThread socket : Server.serverList) {
//            socket.send(message);
//        }
//    }


    /**
     * отсылка одного сообщения клиенту
     *
     * @param msg сообщение
     */
    public void send(final String msg) throws IOException {
        out.write(msg + "\n");
        out.flush();
    }

    public void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (final IOException ignored) {
        }
    }
}