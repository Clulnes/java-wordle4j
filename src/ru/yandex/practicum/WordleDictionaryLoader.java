package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private PrintWriter logWriter;

    public WordleDictionaryLoader(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public WordleDictionary load(String fileName) throws DictionaryIsEmptyException, WordleFileNotFoundException {
        logWriter.println("Загрузка!");

        WordleDictionary dictionary = new WordleDictionary(logWriter);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String word = line;

                if (word.length() == 5) {
                    word = word.toLowerCase().replace("ё", "е");
                    dictionary.addWord(word);
                }
            }
        } catch (IOException e) {
            throw new WordleFileNotFoundException("Файл не найден!");
        }

        if (dictionary.getCount() == 0) {
            throw new DictionaryIsEmptyException("Файл не содержит слов из 5 букв!");
        }
        return dictionary;
    }
}
