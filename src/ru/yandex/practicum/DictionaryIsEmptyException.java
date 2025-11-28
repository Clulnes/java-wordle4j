package ru.yandex.practicum;

public class DictionaryIsEmptyException extends Exception {
    public DictionaryIsEmptyException(String message) {
        super(message);
    }
}
