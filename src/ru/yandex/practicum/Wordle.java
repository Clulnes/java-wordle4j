package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter("log.txt", StandardCharsets.UTF_8)) {
            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader(writer);
                WordleDictionary dictionary = loader.load("words_ru.txt");
                Scanner scanner = new Scanner(System.in);
                WordleGame game = new WordleGame(dictionary, writer);

                System.out.println("Вас приветствует игра Wordle! Ваша задача - отгадать слово из 5 букв за 6 попыток." +
                        " Игра началась!");

                while (game.getSteps() > 0) {
                    System.out.println("Введите слово! У вас осталось попыток " + game.getSteps());
                    String input = scanner.nextLine();

                    if (input.isBlank()) {
                        System.out.println("Подсказка: слово - " + game.getHint().toUpperCase());
                    } else {
                        try {
                            String result = game.doGuess(input);

                            System.out.println(result);

                            if (result.equals("+++++")) {
                                System.out.println("Вы угадали! Победа!");
                                return;
                            }
                        } catch (WordNotFoundInException | IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    }
                }
                System.out.println("Вы проиграли! Загаданное слово: " + game.getAnswer() + ". Повезет в следующий " +
                        "раз!");
            } catch (Exception e) {
                System.out.println("Ошибка! Причина записана в файл log.txt.");
                e.printStackTrace(writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
