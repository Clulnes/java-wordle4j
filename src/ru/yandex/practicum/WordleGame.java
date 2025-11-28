package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> helpList;
    Random random = new Random();
    private PrintWriter log;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) throws DictionaryIsEmptyException {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
        this.helpList = dictionary.getWords();
        this.log = log;
        log.println("Игра инициализирована. Программа загадала слово: " + answer);
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public void inputCorrection(String answer) throws WordNotFoundInException {
        if (answer.length() != 5) {
            throw new IllegalArgumentException("Слово неверной размерности!");
        }

        if (!dictionary.isContains(answer)) {
            throw new WordNotFoundInException("Слова нет в словаре!");
        }
    }

    public String doGuess(String userWord) throws WordNotFoundInException {
        userWord = userWord.toLowerCase().replace("ё", "е");
        inputCorrection(userWord);
        steps--;
        String mask = createCorrectMask(answer, userWord);
        updateHelpList(userWord, mask);
        return mask;
    }

    public String createCorrectMask(String answer, String userWord) {
        StringBuilder output = new StringBuilder("-----");
        StringBuilder temp = new StringBuilder(answer);

        for (int i = 0; i < userWord.length(); i++) {
            if (userWord.charAt(i) == answer.charAt(i)) {
                output.setCharAt(i, '+');
                temp.setCharAt(i, '.');
            }
        }

        for (int j = 0; j < userWord.length(); j++) {
            if (output.charAt(j) != '+') {
                char userChar = userWord.charAt(j);
                String userString = String.valueOf(userChar);
                int indexInAnswer = temp.indexOf(userString);

                if (indexInAnswer != -1) {
                    output.setCharAt(j, '^');
                    temp.setCharAt(indexInAnswer, '.');
                }
            }
        }
        return output.toString();
    }

    public void updateHelpList(String userWord, String mask) {
        List<String> newHelpList = new ArrayList<>();

        for (String word : helpList) {
            String maskThatCanBe = createCorrectMask(word, userWord);

            if (maskThatCanBe.equals(mask)) {
                newHelpList.add(word);
            }
        }
        helpList = newHelpList;
    }

    public String getHint() {
        return helpList.get(random.nextInt(helpList.size()));
    }
}
