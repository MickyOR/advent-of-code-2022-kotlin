import java.math.BigInteger
import kotlin.math.*

fun main() {
    fun getLong(): MutableList<MutablePair<Int, Int>> {
        var shape = mutableListOf<MutablePair<Int, Int>>()
        shape.add(MutablePair(0, 0))
        shape.add(MutablePair(0, 1))
        shape.add(MutablePair(0, 2))
        shape.add(MutablePair(0, 3))
        return shape
    }

    fun getCross(): MutableList<MutablePair<Int, Int>> {
        var shape = mutableListOf<MutablePair<Int, Int>>()
        shape.add(MutablePair(-1, 0))
        shape.add(MutablePair(-1, 1))
        shape.add(MutablePair(-1, 2))
        shape.add(MutablePair(-2, 1))
        shape.add(MutablePair(0, 1))
        return shape
    }

    fun getL(): MutableList<MutablePair<Int, Int>> {
        var shape = mutableListOf<MutablePair<Int, Int>>()
        shape.add(MutablePair(0, 0))
        shape.add(MutablePair(0, 1))
        shape.add(MutablePair(0, 2))
        shape.add(MutablePair(-1, 2))
        shape.add(MutablePair(-2, 2))
        return shape
    }

    fun getTall(): MutableList<MutablePair<Int, Int>> {
        var shape = mutableListOf<MutablePair<Int, Int>>()
        shape.add(MutablePair(0, 0))
        shape.add(MutablePair(-1, 0))
        shape.add(MutablePair(-2, 0))
        shape.add(MutablePair(-3, 0))
        return shape
    }

    fun getSquare(): MutableList<MutablePair<Int, Int>> {
        var shape = mutableListOf<MutablePair<Int, Int>>()
        shape.add(MutablePair(0, 0))
        shape.add(MutablePair(-1, 0))
        shape.add(MutablePair(0, 1))
        shape.add(MutablePair(-1, 1))
        return shape
    }

    fun getShape(inx: Int): MutableList<MutablePair<Int, Int>> {
        return when (inx%5) {
            0 -> getLong()
            1 -> getCross()
            2 -> getL()
            3 -> getTall()
            4 -> getSquare()
            else -> getLong()
        }
    }

    fun move(shape: MutableList<MutablePair<Int, Int>>, dx: Int, grid: MutableList<MutableList<Char>>) {
        var canMove = true
        for (i in 0 until shape.size) {
            var x = shape[i].second + dx
            if (x < 0 || x >= 7 || grid[shape[i].first][x] == '#') {
                canMove = false
                break
            }
        }
        if (canMove) {
            for (i in 0 until shape.size) {
                shape[i].second += dx
            }
        }
    }

    fun moveDown(shape: MutableList<MutablePair<Int, Int>>, grid: MutableList<MutableList<Char>>): Boolean {
        var canMove = true
        for (i in 0 until shape.size) {
            var y = shape[i].first + 1
            var x = shape[i].second
            if (y >= grid.size || grid[y][x] == '#') {
                canMove = false
                break
            }
        }
        if (canMove) {
            for (i in 0 until shape.size) {
                shape[i].first += 1
            }
        }
        return canMove
    }

    fun part1(input: List<String>): Int {
        val rows = 100000
        var mat = MutableList(rows) { MutableList<Char>(7) { '.' } }
        var tallest = rows
        var op = -1
        for (idx in 0 until 2022) {
            var shape = getShape(idx)
            for (i in shape.indices) {
                shape[i].second += 2
                shape[i].first += (tallest - 4)
            }
            while (true) {
                op++
                if (op >= input[0].length) op = 0
                var delta = if (input[0][op] == '<') -1 else 1
                move(shape, delta, mat)
                if (!moveDown(shape, mat)) {
                    for (i in shape.indices) {
                        mat[shape[i].first][shape[i].second] = '#'
                        tallest = min(tallest, shape[i].first)
                    }
                    break
                }
            }
        }
        return rows - tallest
    }

    // returns {{cycle height, pieces in cycle}, last piece}
    fun findCycleLength(input: List<String>): Pair<Pair<Int, Int>, Int> {
        for (len in 250 until 100000) {
            val rows = 10000000
            var mat = MutableList(rows) { MutableList<Char>(7) { '.' } }
            var tallest = rows
            var op = -1
            val testPieces = 50000
            var upperGrid = mutableMapOf<String, Pair<Int, Int>>() // {grid -> {height, pieces}}
            for (idx in 0 until testPieces) {
                var shape = getShape(idx)
                for (i in shape.indices) {
                    shape[i].second += 2
                    shape[i].first += (tallest - 4)
                }
                while (true) {
                    op++
                    if (op >= input[0].length) op = 0
                    var delta = if (input[0][op] == '<') -1 else 1
                    move(shape, delta, mat)
                    if (!moveDown(shape, mat)) {
                        for (i in shape.indices) {
                            mat[shape[i].first][shape[i].second] = '#'
                            tallest = min(tallest, shape[i].first)
                        }
                        break
                    }
                }
                if (rows - tallest < len) continue
                var gridString: String = ""
                for (i in 0 until len) {
                    if (i >= rows) break
                    for (j in 0 until 7) {
                        gridString += mat[tallest + i][j]
                    }
                }
                if (upperGrid.containsKey(gridString)) {
                    return Pair(Pair(rows - tallest - upperGrid[gridString]!!.first, (idx+1) - upperGrid[gridString]!!.second), idx+1)
                }
                upperGrid[gridString] = Pair(rows - tallest, idx+1)
            }
        }
        return Pair(Pair(0, 0), 0)
    }

    fun part2(input: List<String>): Long {
        val totalPieces = 1000000000000
        var cycle = findCycleLength(input)
        val rows = 10000000
        var mat = MutableList(rows) { MutableList<Char>(7) { '.' } }
        var tallest = rows
        var op = -1
        for (idx in 0 until cycle.second) {
            var shape = getShape(idx)
            for (i in shape.indices) {
                shape[i].second += 2
                shape[i].first += (tallest - 4)
            }
            while (true) {
                op++
                if (op >= input[0].length) op = 0
                var delta = if (input[0][op] == '<') -1 else 1
                move(shape, delta, mat)
                if (!moveDown(shape, mat)) {
                    for (i in shape.indices) {
                        mat[shape[i].first][shape[i].second] = '#'
                        tallest = min(tallest, shape[i].first)
                    }
                    break
                }
            }
        }
        var remainingPieces = totalPieces - cycle.second
        var lastPieces = remainingPieces % cycle.first.second
        var height = remainingPieces / cycle.first.second * cycle.first.first
        for (idx in cycle.second until cycle.second + lastPieces.toInt()) {
            var shape = getShape(idx)
            for (i in shape.indices) {
                shape[i].second += 2
                shape[i].first += (tallest - 4)
            }
            while (true) {
                op++
                if (op >= input[0].length) op = 0
                var delta = if (input[0][op] == '<') -1 else 1
                move(shape, delta, mat)
                if (!moveDown(shape, mat)) {
                    for (i in shape.indices) {
                        mat[shape[i].first][shape[i].second] = '#'
                        tallest = min(tallest, shape[i].first)
                    }
                    break
                }
            }
        }
        return rows - tallest + height
    }

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
