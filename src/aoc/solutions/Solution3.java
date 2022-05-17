package aoc.solutions;

import aoc.utils.Matrix;

import java.util.List;

record Claim(int id, int row, int column, int width, int height) {
    public static Claim parse(String raw) {
        var tokens = raw.split(" ");

        var id = Integer.parseInt(tokens[0].substring(1));

        var coords = tokens[2].split(",");
        var col = Integer.parseInt(coords[0]);
        var row = Integer.parseInt(coords[1].replaceAll(":", ""));

        var dimensions = tokens[3].split("x");
        var width = Integer.parseInt(dimensions[0]);
        var height = Integer.parseInt(dimensions[1]);

        return new Claim(id, row, col, width, height);
    }

    public void fillMatrix(Matrix<Integer> matrix) {
        matrix.transformRange(row, row + height, column, column + width, cell -> cell + 1);
    }

    public int size() {
        return width * height;
    }

    public int matrixSize(Matrix<Integer> matrix) {
        return matrix.queryRange(row, row + height, column, column + width)
                .stream().reduce(0, Integer::sum);
    }

    public boolean unique(Matrix<Integer> matrix) {
        return this.size() == this.matrixSize(matrix);
    }
}

public class Solution3 implements PuzzleSolution {
    @Override
    public String solvePart1(List<String> input) {
        var matrix = new Matrix<>(1000, 1000, 0);
        input.stream().map(Claim::parse).forEach(c -> c.fillMatrix(matrix));
        return String.valueOf(matrix.where(cell -> cell > 1).size());
    }

    @Override
    public String solvePart2(List<String> input) {
        var matrix = new Matrix<>(1000, 1000, 0);
        var claims = input.stream().map(Claim::parse).toList();
        claims.forEach(c -> c.fillMatrix(matrix));
        for (var claim : claims) {
            if (claim.unique(matrix)) {
                return String.valueOf(claim.id());
            }
        }
        return null;
    }
}
