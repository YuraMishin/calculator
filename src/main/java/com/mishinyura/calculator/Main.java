package com.mishinyura.calculator;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    enum Operation {

        PLUS("+") {
            int apply(int x, int y) {
                return x + y;
            }
        },
        MINUS("-") {
            int apply(int x, int y) {
                return x - y;
            }
        },
        TIMES("*") {
            int apply(int x, int y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            int apply(int x, int y) {
                return x / y;
            }
        };

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

        abstract int apply(int x, int y);
    }

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение: ");
            String input = userInput.nextLine();

            try {
                System.out.println(Main.calc(input));
            } catch (Exception e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }

    public static String calc(String input) throws Exception {
        validate(input);
        String[] parsedString = input.split(" ");
        validate(parsedString);
        return String.valueOf(getResult(parsedString));
    }

    public static void validate(String input) throws Exception {
        if (input == null || input.isEmpty()) {
            throw new Exception("Отсутсвует строка");
        }
    }

    public static void validate(String[] parsedString) throws Exception {
        validateStringFormat(parsedString);
        validateNumberFormat(parsedString);
        validateNumberRange(parsedString);
        validateMathOperation(parsedString);
    }

    public static void validateStringFormat(String[] parsedString) throws Exception {
        if (parsedString.length != 3) {
            throw new Exception("Невалидная строка");
        }
    }

    public static void validateNumberFormat(String[] parsedString) throws Exception {
        try {
            Integer.parseInt(parsedString[0]);
            Integer.parseInt(parsedString[2]);
        } catch (NumberFormatException e) {
            throw new Exception("Неверный формат чисел");
        }
    }

    public static void validateNumberRange(String[] parsedString) throws Exception {
        int value1 = Integer.parseInt(parsedString[0]);
        int value2 = Integer.parseInt(parsedString[2]);

        if (!(value1 >= 1 && value1 <= 10) || !(value2 >= 1 && value2 <= 10)) {
            throw new Exception("Числа выходят за пределы диапазона");
        }
    }

    public static void validateMathOperation(String[] parsedString) throws Exception {
        boolean isOperationExisted = Arrays.stream(
                        Operation.values())
                .anyMatch(operation -> operation.toString().equals(parsedString[1]));
        if (!isOperationExisted) {
            throw new Exception("Неверный формат мат.операции");
        }
    }

    public static int getResult(String[] parsedString) {
        int value1 = Integer.parseInt(parsedString[0]);
        int value2 = Integer.parseInt(parsedString[2]);

        return Arrays.stream(Operation.values())
                .filter(operation -> operation.toString().equals(parsedString[1]))
                .findFirst().get().apply(value1, value2);
    }
}
