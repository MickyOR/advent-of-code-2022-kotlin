import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = Array(1001) { CharArray(1001) }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = '.'
            }
        }
        for (line in input) {
            var lastRow = -1
            var lastCol = -1
            if (line.isEmpty()) continue;
            var points = line.split(" -> ")
            for (point in points) {
                var (col, row) = point.split(",").map { it.toInt() }
                if (lastCol == -1) {
                    lastRow = row
                    lastCol = col
                    continue
                }
                for (j in min(lastCol, col) .. max(lastCol, col)) {
                    for (i in min(lastRow, row) .. max(lastRow, row)) {
                        matrix[i][j] = '#'
                    }
                }
                lastRow = row
                lastCol = col
            }
        }
        var ans = 0
        while (true) {
            var row = 0
            var col = 500
            while (row < 1000) {
                if (matrix[row+1][col] == '.') {
                    row++
                    continue
                }
                if (matrix[row+1][col-1] == '.') {
                    row++
                    col--
                    continue
                }
                if (matrix[row+1][col+1] == '.') {
                    row++
                    col++
                    continue
                }
                break
            }
            if (row == 1000) break
            matrix[row][col] = 'o'
            ans++
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val matrix = Array(1001) { CharArray(3001) }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = '.'
            }
        }
        var maxRow = 0
        for (line in input) {
            var lastRow = -1
            var lastCol = -1
            if (line.isEmpty()) continue;
            var points = line.split(" -> ")
            for (point in points) {
                var (col, row) = point.split(",").map { it.toInt() }
                col += 1000
                maxRow = maxRow.coerceAtLeast(row)
                if (lastCol == -1) {
                    lastRow = row
                    lastCol = col
                    continue
                }
                for (j in min(lastCol, col) .. max(lastCol, col)) {
                    for (i in min(lastRow, row) .. max(lastRow, row)) {
                        matrix[i][j] = '#'
                    }
                }
                lastRow = row
                lastCol = col
            }
        }
        for (j in 0 .. 3000) {
            matrix[maxRow+2][j] = '#'
        }
        var ans = 0
        while (true) {
            var row = 0
            var col = 1500
            while (row < 1000) {
                if (matrix[row+1][col] == '.') {
                    row++
                    continue
                }
                if (matrix[row+1][col-1] == '.') {
                    row++
                    col--
                    continue
                }
                if (matrix[row+1][col+1] == '.') {
                    row++
                    col++
                    continue
                }
                break
            }
            if (row == 0) break
            matrix[row][col] = 'o'
            ans++
        }
        return ans+1
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
