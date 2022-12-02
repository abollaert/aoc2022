package be.assembledbytes.aoc2022;

import java.util.Set;

public class W2 {

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

    private static class InputLine {
        private static final InputLine fromString(final String line) {
            final String[] requestResponse = line.split(" ");

            return new InputLine(Action.forRequest(requestResponse[0]), Action.forResponse(requestResponse[1]));
        }

        private Action requestAction;
        private Action responseAction;

        private InputLine(final Action requestAction,
                          final Action responseAction) {
            this.requestAction = requestAction;
            this.responseAction = responseAction;
        }

        private final boolean isWin() {
            return this.requestAction.beats(this.responseAction);
        }

        private final boolean isLose() {
            return !this.requestAction.beats(this.responseAction) && !this.requestAction.draws(this.responseAction);
        }
    }

    public static void main(String[] args) {
        Action.ROCK.setBeats(Action.SCISSORS);
        Action.PAPER.setBeats(Action.ROCK);
        Action.SCISSORS.setBeats(Action.PAPER);
    }
}
