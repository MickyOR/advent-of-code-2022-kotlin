fun main() {
    var dx: MutableList<Int> = mutableListOf(0, 1, 0, -1, 0, 0)
    var dy: MutableList<Int> = mutableListOf(-1, 0, 1, 0, 0, 0)
    var dz: MutableList<Int> = mutableListOf(0, 0, 0, 0, -1, 1)

    fun part1(input: List<String>): Int {
        val tam = 20
        var grid = Array(tam) { Array(tam) { BooleanArray(tam) { false } } }
        var minVal = tam
        var maxVal = 0
        for (line in input) {
            var (x, y, z) = line.split(",").map { it.toInt() }
            grid[x][y][z] = true
            minVal = minOf(minVal, x, y, z)
            maxVal = maxOf(maxVal, x, y, z)
        }
        var ans = 0
        for (x in 0 until tam) {
            for (y in 0 until tam) {
                for (z in 0 until tam) {
                    if (!grid[x][y][z]) continue
                    for (k in 0 until dx.size) {
                        var nx = x + dx[k]
                        var ny = y + dy[k]
                        var nz = z + dz[k]
                        if (nx < 0 || nx >= tam || ny < 0 || ny >= tam || nz < 0 || nz >= tam) {
                            ans++
                        }
                        else if (!grid[nx][ny][nz]) {
                            ans++
                        }
                    }
                }
            }
        }
        return ans
    }

    fun dfs(x: Int, y: Int, z: Int, grid: Array<Array<BooleanArray>>, vis: Array<Array<BooleanArray>>, tam: Int) {
        var stack = mutableListOf<Triple<Int, Int, Int>>()
        stack.add(Triple(x, y, z))
        while (stack.isNotEmpty()) {
            var (x, y, z) = stack.removeAt(stack.size - 1)
            if (vis[x][y][z]) continue
            vis[x][y][z] = true
            for (k in 0 until dx.size) {
                var nx = x + dx[k]
                var ny = y + dy[k]
                var nz = z + dz[k]
                if (nx < 0 || nx >= tam || ny < 0 || ny >= tam || nz < 0 || nz >= tam) continue
                if (!grid[nx][ny][nz] && !vis[nx][ny][nz]) {
                    stack.add(Triple(nx, ny, nz))
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val tam = 22
        var grid = Array(tam) { Array(tam) { BooleanArray(tam) { false } } }
        var vis = Array(tam) { Array(tam) { BooleanArray(tam) { false } } }
        for (line in input) {
            var (x, y, z) = line.split(",").map { it.toInt()+1 }
            grid[x][y][z] = true
        }
        dfs(0, 0, 0, grid, vis, tam)
        var ans = 0
        for (x in 0 until tam) {
            for (y in 0 until tam) {
                for (z in 0 until tam) {
                    if (!grid[x][y][z]) continue
                    for (k in 0 until dx.size) {
                        var nx = x + dx[k]
                        var ny = y + dy[k]
                        var nz = z + dz[k]
                        if (nx < 0 || nx >= tam || ny < 0 || ny >= tam || nz < 0 || nz >= tam) {
                            ans++
                        }
                        else if (vis[nx][ny][nz]) {
                            ans++
                        }
                    }
                }
            }
        }
        return ans
    }

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
