fun main() {
    var dR = listOf<Int>(-1, 0, 1, 0, 0)
    var dC = listOf<Int>(0, 1, 0, -1, 0)

    fun part1(input: List<String>): Int {
        var blizzards = arrayListOf<ArrayList<ArrayList<Boolean>>>()
        var oldPositions = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
        var blizzard = arrayListOf<ArrayList<Boolean>>()
        for (i in input.indices) {
            blizzard.add(arrayListOf())
            for (j in input[i].indices) {
                blizzard[i].add(input[i][j] != '.' && input[i][j] != '#')
                if (input[i][j] == '.' || input[i][j] == '#') continue
                var dir = when (input[i][j]) {
                    '^' -> 0
                    '>' -> 1
                    'v' -> 2
                    else -> 3
                }
                oldPositions.add(Pair(Pair(i, j), dir))
            }
        }
        blizzards.add(blizzard)
        // generate blizzards for each step
        fun genNewBlizzard() {
            var blizzard = arrayListOf<ArrayList<Boolean>>()
            for (i in input.indices) {
                blizzard.add(arrayListOf())
                for (j in input[i].indices) {
                    blizzard[i].add(false)
                }
            }
            var positions = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
            for (pos in oldPositions) {
                var (r, c) = pos.first
                var dir = pos.second
                r += dR[dir]
                c += dC[dir]
                if (input[r][c] == '#') {
                    when (dir) {
                        0 -> r = input.size - 2
                        1 -> c = 1
                        2 -> r = 1
                        3 -> c = input[0].length - 2
                    }
                }
                blizzard[r][c] = true
                positions.add(Pair(Pair(r, c), dir))
            }
            blizzards.add(blizzard)
            oldPositions = positions
        }

        val dist = mutableSetOf<Pair<Pair<Int, Int>, Int>>() // ((row, col), dist)
        val queue = mutableListOf<Pair<Pair<Int, Int>, Int>>()
        queue.add(Pair(Pair(0, 1), 0))
        dist.add(Pair(Pair(0, 1), 0))
        while (queue.isNotEmpty()) {
            while (queue[0].second+1 >= blizzards.size) {
                genNewBlizzard()
            }
            val blizzard = blizzards[queue[0].second + 1]
            val (r, c) = queue[0].first
            val d = queue[0].second
            if (r == input.size - 1 && c == input[0].length - 2) {
                return d
            }
            queue.removeAt(0)
            for (i in 0 until 5) {
                val nr = r + dR[i]
                val nc = c + dC[i]
                if (nr < 0 || nr >= blizzard.size || nc < 0 || nc >= blizzard[0].size || input[nr][nc] == '#') continue
                if (!blizzard[nr][nc] && !dist.contains(Pair(Pair(nr, nc), d + 1))) {
                    dist.add(Pair(Pair(nr, nc), d + 1))
                    queue.add(Pair(Pair(nr, nc), d + 1))
                }
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        var blizzards = arrayListOf<ArrayList<ArrayList<Boolean>>>()
        var oldPositions = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
        var blizzard = arrayListOf<ArrayList<Boolean>>()
        for (i in input.indices) {
            blizzard.add(arrayListOf())
            for (j in input[i].indices) {
                blizzard[i].add(input[i][j] != '.' && input[i][j] != '#')
                if (input[i][j] == '.' || input[i][j] == '#') continue
                var dir = when (input[i][j]) {
                    '^' -> 0
                    '>' -> 1
                    'v' -> 2
                    else -> 3
                }
                oldPositions.add(Pair(Pair(i, j), dir))
            }
        }
        blizzards.add(blizzard)
        // generate blizzards for each step
        fun genNewBlizzard() {
            var blizzard = arrayListOf<ArrayList<Boolean>>()
            for (i in input.indices) {
                blizzard.add(arrayListOf())
                for (j in input[i].indices) {
                    blizzard[i].add(false)
                }
            }
            var positions = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
            for (pos in oldPositions) {
                var (r, c) = pos.first
                var dir = pos.second
                r += dR[dir]
                c += dC[dir]
                if (input[r][c] == '#') {
                    when (dir) {
                        0 -> r = input.size - 2
                        1 -> c = 1
                        2 -> r = 1
                        3 -> c = input[0].length - 2
                    }
                }
                blizzard[r][c] = true
                positions.add(Pair(Pair(r, c), dir))
            }
            blizzards.add(blizzard)
            oldPositions = positions
        }

        val dist = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>() // ((row, col), (dist, currentTarget))
        val queue = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        queue.add(Pair(Pair(0, 1), Pair(0, 0)))
        dist.add(Pair(Pair(0, 1), Pair(0, 0)))
        var target = listOf<Pair<Int, Int>>(
            Pair(input.size-1, input[0].length-2),
            Pair(0, 1),
            Pair(input.size-1, input[0].length-2)
        )
        while (queue.isNotEmpty()) {
            val d = queue[0].second.first
            while (d+1 >= blizzards.size) {
                genNewBlizzard()
            }
            val blizzard = blizzards[d + 1]
            val (r, c) = queue[0].first
            var curTarget = queue[0].second.second
            if (Pair(r, c) == target[curTarget]) curTarget++
            if (curTarget == 3) return d
            queue.removeAt(0)
            for (i in 0 until 5) {
                val nr = r + dR[i]
                val nc = c + dC[i]
                if (nr < 0 || nr >= blizzard.size || nc < 0 || nc >= blizzard[0].size || input[nr][nc] == '#') continue
                if (!blizzard[nr][nc] && !dist.contains(Pair(Pair(nr, nc), Pair(d + 1, curTarget)))) {
                    dist.add(Pair(Pair(nr, nc), Pair(d + 1, curTarget)))
                    queue.add(Pair(Pair(nr, nc), Pair(d + 1, curTarget)))
                }
            }
        }
        return -1
    }

    val input = readInput("Day24")
    part1(input).println()
    part2(input).println()
}
