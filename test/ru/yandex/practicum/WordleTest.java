package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private PrintWriter writer;
    private WordleDictionary dictionary;

    @BeforeEach
    void setUp() {
        writer = new PrintWriter(System.out);
        dictionary = new WordleDictionary(writer);
    }

    @Test
    void testExactWord() throws DictionaryIsEmptyException, WordNotFoundInException {
        dictionary.addWord("арбуз");
        WordleGame game = new WordleGame(dictionary, writer);
        String result  = game.doGuess("арбуз");
        Assertions.assertEquals("+++++", result);
    }

    @Test
    void testNotExactWord() throws DictionaryIsEmptyException, WordNotFoundInException {
        dictionary.addWord("мякиш");
        WordleGame game = new WordleGame(dictionary, writer);
        dictionary.addWord("булка");
        String result = game.doGuess("булка");
        Assertions.assertEquals("---^-", result);
    }

    @Test
    void testGameEndedAfterSixWrongAnswers() throws DictionaryIsEmptyException, WordNotFoundInException {
        dictionary.addWord("кулак");
        WordleGame game = new WordleGame(dictionary, writer);
        dictionary.addWord("палец");
        int attemptsAtStart = game.getSteps();
        Assertions.assertEquals(6, attemptsAtStart);

        for (int i = 0; i < 6; i++) {
            game.doGuess("палец");
        }

        Assertions.assertEquals(0, game.getSteps());
    }

    @Test
    void testAddAndFindWord() {
        dictionary.addWord("напор");
        boolean exists = dictionary.isContains("напор");

        Assertions.assertTrue(exists);
    }

    @Test
    void testDictionarySize() {
        dictionary.addWord("пакет");
        dictionary.addWord("радар");

        Assertions.assertEquals(2, dictionary.getCount());
    }
}
