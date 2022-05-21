package aoc.solutions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Solution5 implements PuzzleSolution {
    @Override
    public String solvePart1(List<String> input) {
        var polymer = new Polymer(input.get(0));
        return String.valueOf(polymer.size());
    }

    @Override
    public String solvePart2(List<String> input) {
        var unreacted = input.get(0);
        Set<Character> units = new HashSet<>();
        for (var c : unreacted.toCharArray()) {
            units.add(Character.toLowerCase(c));
        }

        int smallest = Integer.MAX_VALUE;
        for (var u : units) {
            var polymer = new Polymer(unreacted.replaceAll(String.format("[%c%c]", u, Character.toUpperCase(u)), ""));
            if (polymer.size() < smallest) {
                smallest = polymer.size();
            }
        }

        return String.valueOf(smallest);
    }

    class Polymer {
        Stack<Character> polymer = new Stack<>();

        Polymer(String polymer) {
            for (char c : polymer.toCharArray()) {
                if (!this.polymer.isEmpty() && this.polymer.peek() == swapCase(c)) {
                    this.polymer.pop();
                } else {
                    this.polymer.add(c);
                }
            }
        }

        static Character swapCase(Character c) {
            if (Character.isUpperCase(c)) {
                return Character.toLowerCase(c);
            }

            assert Character.isLowerCase(c);
            return Character.toUpperCase(c);
        }

        public int size() {
            return this.polymer.size();
        }
    }
}
