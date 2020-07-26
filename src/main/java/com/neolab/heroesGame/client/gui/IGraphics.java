package com.neolab.heroesGame.client.gui;

import com.neolab.heroesGame.client.dto.ExtendedServerResponse;

import java.io.IOException;
import java.util.List;

public interface IGraphics {

    /**
     * Отображаем текущую позицию на арене и последнее сделанное действие.
     *
     * @param response   - Используется для отображения арены, эффекта прошлого действия.
     * @param isYourTurn - В зависимости от значения этой переменной будет отображено чей сейчас ход
     */
    void showPosition(final ExtendedServerResponse response, boolean isYourTurn) throws IOException;

    void endGame(final ExtendedServerResponse response) throws IOException;

    /**
     * Используется для выбора действий пользователем.
     * Отображает принятый список строк.
     *
     * @param strings - списко доступных пользователю действий
     * @return -  номер выбранной строки
     */
    int getChoose(final List<String> strings) throws IOException;
}
