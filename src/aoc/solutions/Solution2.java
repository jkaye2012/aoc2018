package aoc.solutions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Solution2 implements PuzzleSolution {
    @Override
    public String solvePart1(List<String> input) {
        Integer num2 = 0;
        Integer num3 = 0;
        for (var i : input) {
            var letterCount = new HashMap<Character, Integer>();
            for (var c : i.toCharArray()) {
                letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
            }

            if (letterCount.containsValue(2)) {
                num2++;
            }
            if (letterCount.containsValue(3)) {
                num3++;
            }
        }

        return String.valueOf(num2 * num3);
    }

    @Override
    public String solvePart2(List<String> input) {
        int index = -1;
        while (++index < input.get(0).length()) {
            var strings = new HashSet<String>();
            int finalIndex = index;
            Optional<String> init = Optional.empty();
            var opt = input.stream().map(i -> i.substring(0, finalIndex) + i.substring(finalIndex + 1))
                    .map(Optional::of)
                    .reduce(init, (result, i) -> {
                        if (result.isPresent()) {
                            return result;
                        }

                        if (!strings.add(i.get())) {
                            return i;
                        }
                        return Optional.empty();
                    });
            if (opt.isPresent()) {
                return opt.get();
            }
        }

        return "failed";
    }
}
