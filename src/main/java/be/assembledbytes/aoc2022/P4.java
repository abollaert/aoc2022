package be.assembledbytes.aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P4 {

    private record Assignment(int start,
                              int end) {
        private static final List<Assignment> forLine(final String line) {
            final List<Assignment> assignments = new ArrayList<>();

            final String[] paired = line.split(",");

            for (final String assignmentString : paired) {
                final String[] indices = assignmentString.split("-");

                assignments.add(new Assignment(Integer.parseInt(indices[0]), Integer.parseInt(indices[1])));
            }

            return assignments;
        }

        private boolean contains(final Assignment other) {
            return this.start <= other.start &&
                   this.end >= other.end;
        }

        private boolean hasOverlap(final Assignment other) {
            return (this.start <= other.start && this.end >= other.start) || (this.start > other.start && this.start <= other.end);
        }
    }

    private static List<List<Assignment>> readInput() throws IOException {
        final List<List<Assignment>> assignmentPairs = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(P4.class.getResourceAsStream("/p4.txt")))) {
            String line = reader.readLine();

            while (line != null) {
                assignmentPairs.add(Assignment.forLine(line));

                line = reader.readLine();
            }
        }

        return assignmentPairs;
    }

    private static boolean hasRangeThatContainsOther(final List<Assignment> pair1, final List<Assignment> pair2) {
        for (Assignment assignment : pair1) {
            for (Assignment assignmentP2 : pair2) {
                if (assignment.contains(assignmentP2)) {
                    return true;
                }
            }
        }

        return false;
    }
    public static void main(String[] args) throws IOException {
        final List<List<Assignment>> pairs = readInput();

        long count = 0;

        for (final List<Assignment> assignments : pairs) {
            if (assignments.get(0).hasOverlap(assignments.get(1)) || assignments.get(1).hasOverlap(assignments.get(0))) {
                count++;
            }
        }

        System.out.println(count);
    }
}
