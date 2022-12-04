package be.assembledbytes.aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class W2 {

    private enum Outcome {
        LOSS("X"),
        DRAW("Y"),
        WIN("Z");

        private String symbol;

        private static final Outcome forSymbol(final String symbol) {
            for (final Outcome o : Outcome.values()) {
                if (o.symbol.equals(symbol)) {
                    return o;
                }
            }

            return null;
        }

        private Outcome(final String symbol) {
            this.symbol = symbol;
        }
    }

    private enum Action {

        ROCK("A", "X", 1),
        PAPER("B", "Y", 2),
        SCISSORS("C", "Z", 3);

        private static final Action forRequest(final String request) {
            for (final Action a : Action.values()) {
                if (a.request.equals(request)) {
                    return a;
                }
            }

            return null;
        }

        private static final Action forResponse(final String response) {
            for (final Action a : Action.values()) {
                if (a.response.equals(response)) {
                    return a;
                }
            }

            return null;
        }

        private final String request;
        private final String response;
        private final int score;
        private Action beats;

        private Action(final String request,
                       final String response,
                       final int score) {
            this.request = request;
            this.response = response;
            this.score = score;
        }

        private void setBeats(final Action other) {
            this.beats = other;
        }

        private final boolean beats(final Action other) {
            return other == this.beats;
        }

        private final boolean draws(final Action other) {
            return this == other;
        }
    }

    private static record InputLine(Action requestAction,
                                    Action responseAction,
                                    Outcome outcome,
                                    Action actionForOutcome) {
        private static final InputLine fromString(final String line) {
            final String[] requestResponse = line.split(" ");

            final Action opponentAction = Action.forRequest(requestResponse[0]);
            final Action myAction = Action.forResponse(requestResponse[1]);
            final Outcome outcome = Outcome.forSymbol(requestResponse[1]);
            Action actionForOutcome = null;

            if (outcome == Outcome.WIN) {
                actionForOutcome = switch (opponentAction) {
                    case ROCK -> Action.PAPER;
                    case PAPER -> Action.SCISSORS;
                    case SCISSORS -> Action.ROCK;
                };
            } else if (outcome == Outcome.DRAW) {
                actionForOutcome = opponentAction;
            } else if (outcome == Outcome.LOSS) {
                actionForOutcome = switch (opponentAction) {
                    case ROCK -> Action.SCISSORS;
                    case SCISSORS -> Action.PAPER;
                    case PAPER -> Action.ROCK;
                };
            }

            return new InputLine(opponentAction,
                                 myAction,
                                 outcome,
                                 actionForOutcome);
        }

        private final boolean isWin() {
            return this.responseAction.beats(this.requestAction);
        }

        private final boolean isLose() {
            return !this.responseAction.beats(this.requestAction) && !this.requestAction.draws(this.responseAction);
        }

        private final int getScore() {
            final int choiceScore = this.responseAction.score;
            final int winLoseScore = this.isWin() ? 6 : this.isLose() ? 0 : 3;

            return choiceScore + winLoseScore;
        }

        private final int getScoreP2() {
            final int choiceScore = this.actionForOutcome.score;

            final int winLoseScore = switch (outcome) {
                case WIN -> 6;
                case LOSS -> 0;
                case DRAW -> 3;
            };

            return choiceScore + winLoseScore;
        }
    }

    private static List<InputLine> readInput() throws IOException {
        final List<InputLine> lines = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(W2.class.getResourceAsStream("/w2-p1.txt")))) {
            String line = reader.readLine();

            while (line != null) {
                lines.add(InputLine.fromString(line));

                line = reader.readLine();
            }
        }

        return lines;
    }

    public static void main(String[] args) throws IOException {
        Action.ROCK.setBeats(Action.SCISSORS);
        Action.PAPER.setBeats(Action.ROCK);
        Action.SCISSORS.setBeats(Action.PAPER);

        final List<InputLine> input = readInput();

        int score = 0;

        for (final InputLine line : input) {
            System.out.println(line + ", score : " + line.getScore());
            score += line.getScore();
        }

        System.out.println(score);

        score = 0;

        for (final InputLine line : input) {
            System.out.println(line + ", score : " + line.getScoreP2());
            score += line.getScoreP2();
        }

        System.out.println(score);
    }
}
