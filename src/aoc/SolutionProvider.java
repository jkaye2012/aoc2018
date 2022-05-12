package aoc;

import aoc.solutions.PuzzleSolution;
import aoc.solutions.Solution1;
import aoc.solutions.Solution2;

import java.util.List;

class NoSuchSolution implements PuzzleSolution {
    @Override
    public String solvePart1(List<String> input) {
        return "No such solution";
    }

    @Override
    public String solvePart2(List<String> input) {
        return "No such solution";
    }
}

public class SolutionProvider {
    public static PuzzleSolution forPuzzle(int puzzleNumber) {
        switch (puzzleNumber) {
            case 1:
                return new Solution1();
            case 2:
                return new Solution2();
            default:
                return new NoSuchSolution();
        }
    }

}
