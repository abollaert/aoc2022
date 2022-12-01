package be.assembledbytes.aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class W1 {

    private static record Elf(List<Integer> calorieEntries) {

        private final int countCalories() {
            return this.calorieEntries.stream()
                                      .reduce(0, Integer::sum);
        }
    }

    private static List<Elf> readInput() throws IOException  {
        final List<Elf> elves = new ArrayList<>();
        List<Integer> currentCalories = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(W1.class.getResourceAsStream("/w1-p1.txt")))) {
            String line = reader.readLine();

            while (line != null) {
                if (line.isBlank()) {
                    elves.add(new Elf(currentCalories));
                    currentCalories = new ArrayList<>();
                } else {
                    currentCalories.add(Integer.valueOf(line));
                }

                line = reader.readLine();
            }
        }

        return elves;
    }

    public static void main(final String[] args) throws IOException {
        final List<Elf> elves = readInput();

        elves.sort(Collections.reverseOrder(Comparator.comparing(Elf::countCalories)));

        System.out.println(elves.get(0).countCalories() + elves.get(1).countCalories() + elves.get(2).countCalories());
    }
}
