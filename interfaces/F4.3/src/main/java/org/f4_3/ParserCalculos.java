package org.f4_3;

public class ParserCalculos {
    private final String input;
    private int position;

    public ParserCalculos(String input) {
        this.input = input.replace(',', '.');
        this.position = 0;
    }

    public static double calculate(String input) {
        ParserCalculos parser = new ParserCalculos(input);

        double result = parser.expression();

        parser.skipSpaces();

        if (parser.position != parser.input.length()) {
            throw new IllegalArgumentException("Expresión incorrecta");
        }

        return result;
    }

    // Sumas y restas
    private double expression() {
        double result = term();

        while (true) {
            skipSpaces();

            if (consume('+')) {
                result += term();
            } else if (consume('-')) {
                result -= term();
            } else {
                return result;
            }
        }
    }

    // Multiplicaciones y divisiones
    private double term() {
        double result = factor();

        while (true) {
            skipSpaces();

            if (consume('*')) {
                result *= factor();
            } else if (consume('/')) {
                double divisor = factor();

                if (divisor == 0) {
                    throw new ArithmeticException("División entre cero");
                }

                result /= divisor;
            } else {
                return result;
            }
        }
    }

    // Números, signos y paréntesis
    private double factor() {
        skipSpaces();

        if (consume('+')) {
            return factor();
        }

        if (consume('-')) {
            return -factor();
        }

        if (consume('(')) {
            double result = expression();

            skipSpaces();

            if (!consume(')')) {
                throw new IllegalArgumentException("Falta un paréntesis");
            }

            return result;
        }

        return number();
    }

    // Lectura de números decimales
    private double number() {
        skipSpaces();

        int start = position;
        boolean hasDigit = false;

        while (position < input.length()
                && Character.isDigit(input.charAt(position))) {
            position++;
            hasDigit = true;
        }

        if (position < input.length() && input.charAt(position) == '.') {
            position++;

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {
                position++;
                hasDigit = true;
            }
        }

        if (!hasDigit) {
            throw new IllegalArgumentException("Número incorrecto");
        }

        return Double.parseDouble(input.substring(start, position));
    }

    private boolean consume(char expected) {
        if (position < input.length()
                && input.charAt(position) == expected) {
            position++;
            return true;
        }

        return false;
    }

    private void skipSpaces() {
        while (position < input.length()
                && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }
}

