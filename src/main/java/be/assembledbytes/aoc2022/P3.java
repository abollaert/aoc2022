package be.assembledbytes.aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class P3 {

    private static int getPriority(final char c) {
        if (c >= 97 && c <= 122) {
            return c - 96;
        } else {
            return (c - 64) + 26;
        }
    }

    private static final List<Set<Character>> parse(final String input) {
        final String firstCompartmentInput = input.substring(0, (input.length() / 2));
        final String secondCompartmentInput = input.substring((input.length() / 2), input.length());

        final Set<Character> firstCompartment = firstCompartmentInput.chars()
                                                                     .mapToObj(i -> (char)i)
                                                                     .collect(Collectors.toSet());

        final Set<Character> secondCompartment = secondCompartmentInput.chars()
                                                                      .mapToObj(i -> (char)i)
                                                                      .collect(Collectors.toSet());

        return List.of(firstCompartment, secondCompartment);
    }

    private static final List<List<Set<Character>>> readInput() throws IOException {
        final List<List<Set<Character>>> rucksacks = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(P3.class.getResourceAsStream("/p3.txt")))) {
            String line = reader.readLine();

            while (line != null) {
                rucksacks.add(parse(line));

                line = reader.readLine();
            }
        }

        return rucksacks;
    }

    private static Set<Character> findCommonItems(final List<Set<Character>> rucksack) {
        final Set<Character> common = new HashSet<>(rucksack.get(0));

        common.retainAll(rucksack.get(1));

        return common;
    }

    private static Set<Character> findCommonItemsP2(final List<List<Set<Character>>> rucksacks) {
        Set commonItems = new HashSet(rucksacks.get(0).get(0));
        commonItems.addAll(rucksacks.get(0).get(1));

        for (int i = 1; i < 3; i++) {
            final Set combined = new HashSet(rucksacks.get(i).get(0));
            combined.addAll(rucksacks.get(i).get(1));

            commonItems.retainAll(combined);
        }

        return commonItems;
    }

    public static void main(String[] args) throws IOException {
        final List<List<Set<Character>>> rucksacks = readInput();
        long sum = 0;

        for (final List<Set<Character>> rucksack : rucksacks) {
            final Set<Character> commonItems = findCommonItems(rucksack);

            for (char c : commonItems) {
                System.out.println(c + " : prio " + getPriority(c));
                sum += getPriority(c);
            }
        }

        System.out.println(sum);

        sum = 0;

        for (int i = 0; i < rucksacks.size(); i += 3) {
            final Set<Character> badges = findCommonItemsP2(List.of(rucksacks.get(i), rucksacks.get(i + 1), rucksacks.get(i + 2)));

            for (char c : badges) {
                System.out.println(c + " : prio " + getPriority(c));
                sum += getPriority(c);
            }
        }

        System.out.println(sum);
    }
}
