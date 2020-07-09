package com.neolab.heroesGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Консольный многопользовательский чат.
 * Клиент
 */
public class TemplateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateClient.class);
    private static final SimpleDateFormat DATE_FORMAT = TemplateServer.DATE_FORMAT;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private Socket socket = null;
    private BufferedReader in = null; // поток чтения из сокета
    private BufferedWriter out = null; // поток записи в сокет
    public BufferedReader inputUser = null; // поток чтения с консоли
    private final String nickname = null; // имя клиента

    /**
     * для создания необходимо принять адрес и номер порта
     *
     * @param ip   ip адрес клиента
     * @param port порт соединения
     */
    public TemplateClient(final String ip, final int port) {
        LOGGER.debug("client constructor");
        this.ip = ip;
        this.port = port;
    }

    public void startClient() {
        LOGGER.debug("startClient");

        try {
            socket = new Socket(this.ip, this.port);
            LOGGER.info(String.format("Клиент получил сокет для связи с сервером ip: %s, port^ %d", ip, port));
        } catch (final IOException e) {
            System.err.println("Socket failed");
            LOGGER.error("Socket failed");
            return;
        }

        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (final IOException e) {
            downService();
            LOGGER.error("Streaming failed");
            return;
        }

        System.out.println(String.format("Client started, ip: %s, port: %d", ip, port));
        pressNickname(); // перед началом необходимо спросить имя
        new ReadMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
        new WriteMsg().start(); // нить пишущая сообщения в сокет приходящие с консоли в бесконечном цикле
    }

    /**
     * отсылка одного сообщения клиенту
     *
     * @param message сообщение
     */
    public void send(final String message) throws IOException {
        out.write(message + "\n");
        out.flush();
        LOGGER.debug("Пользователь " + nickname + " отправил сообщение: " + message);
    }

    private String formatMessage(final String message) {
        final Date date = new Date();
        final String strTime = DATE_FORMAT.format(date);
        return String.format("[%s] %s: %s", strTime, nickname, message);
    }

    private String formatCommandMessage(final String message) {
        return formatMessage(message) + " [command]";
    }

    /**
     * просьба ввести имя,
     * и отсылка эхо с приветствием на сервер
     */
    private void pressNickname() {
        System.out.print("Press your nick: ");
        try {
            //nickname = "ivan";  //inputUser.readLine();
            send("ivan");
        } catch (final IOException ignored) {
        }
    }

    /**
     * закрытие сокета
     */
    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                LOGGER.info("Соединение успешно завершено");
            }
        } catch (final IOException ignored) {
        }
    }

    // нить чтения сообщений с сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String message;
            try {
                while (true) {
                    message = in.readLine(); // ждем сообщения с сервера
                    if (TemplateServer.Command.STOP_CLIENT.equalCommand(message)) {
                        LOGGER.info("Получечн запрос сервера на отключение клиента");
                        downService();
                        break; // нить чтения данных из консоли по этой команде прекращает работу сама
                    } else if (TemplateServer.Command.STOP_CLIENT_FROM_SERVER.equalCommand(message)
                            || TemplateServer.Command.STOP_ALL_CLIENTS.equalCommand(message)
                            || TemplateServer.Command.STOP_SERVER.equalCommand(message)) {
                        LOGGER.info("Получечн запрос сервера на прекращение работы клиента");
                        downService();
                        LOGGER.info("Работа клиента успешно.");
                        System.exit(0);
                    }
                    System.out.println(message); // пишем сообщение с сервера на консоль
                }
            } catch (final IOException e) {
                LOGGER.error("Возникла ошибка при получении ответа с сервера. Соединение будет разорвано/n"
                        + e.getMessage());
                downService();
            }
        }
    }

    // нить отправляющая сообщения приходящие с консоли на сервер
    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                final String message;
                try {
                    message = inputUser.readLine();
                    if (TemplateServer.Command.isCommandMessage(message)) {
                        LOGGER.info("Поступила команда" + message);
                        send(formatCommandMessage(message));
                        send(message);
                        if (TemplateServer.Command.STOP_CLIENT.equalCommand(message)) {
                            downService();
                            break;
                        }
                    } else {
                        send(formatMessage(message));
                    }
                } catch (final IOException e) {
                    LOGGER.error("Возникла ошибка отправки сообщения. Соединение будет разорвано/n" + e.getMessage());
                    downService();
                }
            }
        }
    }
}
