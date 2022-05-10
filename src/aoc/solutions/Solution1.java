package aoc.solutions;

import java.util.HashSet;
import java.util.List;

public class Solution1 implements PuzzleSolution {
    @Override
    public String solvePart1(List<String> input) {
        return input.stream().map(i -> i.replaceAll("\\+", ""))
                .map(i -> Integer.parseInt(i)).reduce(0, Integer::sum).toString();
    }

    @Override
    public String solvePart2(List<String> input) {
        var inputs = input.stream().map(i -> i.replaceAll("\\+", ""))
                .map(i -> Integer.parseInt(i)).toList();
        var frequencies = new HashSet<Integer>();
        Integer currentFrequency = 0;
        while (true) {
            for (var f : inputs) {
                currentFrequency += f;
                if (!frequencies.add(currentFrequency)) {
                    return currentFrequency.toString();
                }
            }
        }
    }
}
