package pro.sky.coursework2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.sky.coursework2.exception.MethodNotAllowedException;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

@RestControllerAdvice

public class QuestionControllerAdvice {

    @ExceptionHandler(QuestionAddedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(QuestionAddedException exception) {
        return String.format("HTTP статус -  Внутренняя ошибка сервера (КОД 500):\n <b>%s</b>\n",exception.getMessage());
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(QuestionNotFoundException exception) {
        return String.format("HTTP статус -  Не корректный запрос (КОД 400):\n <b>%s</b>\n",exception.getMessage());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleException(MethodNotAllowedException exception) {
        return String.format("HTTP статус -  Метод недоступен (КОД 405):\n <b>%s</b>\n",exception.getMessage());
    }
}
