package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private List<String> words = new ArrayList<>();
    private PrintWriter log;

    public WordleDictionary(PrintWriter log) {
        this.log = log;
    }

    public void addWord(String word) {
        words.add(word);
    }

    public String getRandomWord() throws DictionaryIsEmptyException {
        if (words.isEmpty()) {
            log.println("Ошибка: попытка взятия слова из пустого файла.");
            throw new DictionaryIsEmptyException("Словарь пуст! Невозможно выбрать слово.");
        }

        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public int getCount() {
        return words.size();
    }

    public boolean isContains(String word) {
        return words.contains(word);
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }
}
