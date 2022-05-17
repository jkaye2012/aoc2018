package aoc.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

/**
 * A two-dimensional matrix.
 *
 * @param <T> The numeric type of each cell within the matrix.
 */
public class Matrix<T extends Number> {
    private final T[][] matrix;
    @Getter
    private final int rows;
    @Getter
    private final int cols;
    @Getter
    private final int size;

    /**
     * Creates a new matrix with the specified dimensions and starting value for all cells.
     *
     * @param rows         The number of rows in the matrix.
     * @param cols         The number of columns in the matrix.
     * @param defaultValue The initial value for all cells in the matrix.
     */
    public Matrix(int rows, int cols, T defaultValue) {
        this.rows = rows;
        this.cols = cols;
        this.size = rows * cols;
        this.matrix = (T[][]) new Number[rows][cols];
        IntStream.range(0, this.getRows()).forEach(row ->
                IntStream.range(0, this.getCols()).forEach(col ->
                        this.matrix[row][col] = defaultValue));
    }

    /**
     * Retrieve the value from a single cell.
     *
     * @param row The row to retrieve.
     * @param col The column to retrieve.
     * @return The value of the cell.
     * @throws ArrayIndexOutOfBoundsException if either {@code row} or {@code col} exceed the matrix dimensions.
     */
    public T get(int row, int col) {
        return this.matrix[row][col];
    }

    /**
     * Applies a unary operator to a contiguous range within the matrix.
     *
     * @param rowStart The inclusive starting row for the operation.
     * @param rowEnd   The exclusive ending row the operation.
     * @param colStart The inclusive starting column for the operation.
     * @param colEnd   The exclusive ending column for the operation.
     * @param op       The operation to apply.
     * @throws ArrayIndexOutOfBoundsException if the specified range exceeds the matrix dimensions.
     */
    public void transformRange(int rowStart, int rowEnd, int colStart, int colEnd, UnaryOperator<T> op) {
        IntStream.range(rowStart, rowEnd).forEach(row ->
                IntStream.range(colStart, colEnd).forEach(col ->
                        this.matrix[row][col] = op.apply(this.get(row, col))));
    }

    /**
     * Applies a unary operator to all cells within the matrix.
     *
     * @param op The operation to apply.
     */
    public void transformAll(UnaryOperator<T> op) {
        this.transformRange(0, this.getRows(), 0, this.getCols(), op);
    }

    /**
     * Runs a {@link Consumer} on a subrange of the matrix.
     *
     * @param rowStart The inclusive starting row for the operation.
     * @param rowEnd   The exclusive ending row the operation.
     * @param colStart The inclusive starting column for the operation.
     * @param colEnd   The exclusive ending column for the operation.
     */
    public List<T> queryRange(int rowStart, int rowEnd, int colStart, int colEnd) {
        var range = new ArrayList<T>();
        IntStream.range(rowStart, rowEnd).forEach(row ->
                IntStream.range(colStart, colEnd).forEach(col ->
                        range.add(this.get(row, col))));
        return range;
    }

    /**
     * Retrieves the cells in the matrix that satisfy a condition.
     *
     * @param condition The predicate to test.
     * @return A list of {@link Coordinate}s that satisfy {@code condition}.
     */
    public List<Coordinate> where(Predicate<T> condition) {
        var coordinates = new ArrayList<Coordinate>();
        IntStream.range(0, this.getRows()).forEach(row ->
                IntStream.range(0, this.getCols()).forEach(col -> {
                    if (condition.test(this.get(row, col))) {
                        coordinates.add(new Coordinate(col, row));
                    }
                }));
        return coordinates;
    }
}
