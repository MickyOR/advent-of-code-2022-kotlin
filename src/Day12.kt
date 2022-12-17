import java.util.Queue

fun main() {
    fun getElevation(c: Char): Int {
        if (c == 'S') return 0
        if (c == 'E') return 'z' - 'a'
        return c - 'a'
    }

    fun part1(input: List<String>): Int {
        var n = input.size-1
        var m = input[0].length
        var r = 0
        var c = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (input[i][j] == 'S') {
                    r += i
                    c += j
                }
            }
        }
        var q = mutableListOf<Pair<Int, Int>>()
        var dist = MutableList(n) { MutableList(m) { -1 } }
        q.add(Pair(r, c))
        dist[r][c] = 0
        var dr = listOf(0, 0, 1, -1)
        var dc = listOf(1, -1, 0, 0)
        while (q.size > 0) {
            r = q.first().first
            c = q.first().second
            q.removeAt(0)
            for (i in 0 until 4) {
                var nr = r + dr[i]
                var nc = c + dc[i]
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && dist[nr][nc] == -1 && getElevation(input[nr][nc]) <= getElevation(input[r][c])+1) {
                    dist[nr][nc] = dist[r][c] + 1
                    q.add(Pair(nr, nc))
                }
            }
        }
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (input[i][j] == 'E') {
                    return dist[i][j]
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        var n = input.size-1
        var m = input[0].length
        var r = 0
        var c = 0
        var q = mutableListOf<Pair<Int, Int>>()
        var dist = MutableList(n) { MutableList(m) { -1 } }
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (getElevation(input[i][j]) == 0) {
                    q.add(Pair(i, j))
                    dist[i][j] = 0
                }
            }
        }
        var dr = listOf(0, 0, 1, -1)
        var dc = listOf(1, -1, 0, 0)
        while (q.size > 0) {
            r = q.first().first
            c = q.first().second
            q.removeAt(0)
            for (i in 0 until 4) {
                var nr = r + dr[i]
                var nc = c + dc[i]
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && dist[nr][nc] == -1 && getElevation(input[nr][nc]) <= getElevation(input[r][c])+1) {
                    dist[nr][nc] = dist[r][c] + 1
                    q.add(Pair(nr, nc))
                }
            }
        }
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (input[i][j] == 'E') {
                    return dist[i][j]
                }
            }
        }
        return 0
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
