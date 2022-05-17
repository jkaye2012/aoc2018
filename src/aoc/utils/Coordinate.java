package aoc.utils;

/**
 * Represents a single point in Cartesian space.
 * <p>
 * When used with a matrix, {@code x} represents the column, while {@code y} represents the row.
 *
 * @param x
 * @param y
 */
public record Coordinate(int x, int y) {
}
