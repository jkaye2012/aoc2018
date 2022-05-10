package main;

import aoc.InputProvider;
import aoc.SolutionProvider;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var puzzleNumber = Integer.parseInt(args[0]);
        var input = InputProvider.forPuzzle(puzzleNumber);
        var solution = SolutionProvider.forPuzzle(puzzleNumber);
        System.out.println("Part 1:");
        System.out.println(solution.solvePart1(input));
        System.out.println();
        System.out.println("Part 2:");
        System.out.println(solution.solvePart2(input));
    }
}
