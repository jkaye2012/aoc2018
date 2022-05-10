package aoc;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class InputProvider {
    public static List<String> forPuzzle(int puzzleNumber) throws IOException {
        var inputFileName = String.format("%s.txt", puzzleNumber);
        var inputFilePath = FileSystems.getDefault().getPath("inputs", inputFileName);
        var reader = Files.newBufferedReader(inputFilePath);
        return reader.lines().toList();
    }
}
