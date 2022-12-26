fun main() {
    var move = Array(4) { listOf<Pair<Int, Int>>() }

    move[0] = listOf( Pair(-1, -1), Pair(-1, 0), Pair(-1, 1)) // up
    move[1] = listOf( Pair(1, 1), Pair(1, 0), Pair(1, -1) ) // down
    move[2] = listOf( Pair(1, -1), Pair(0, -1), Pair(-1, -1) ) // left
    move[3] = listOf( Pair(-1, 1), Pair(0, 1), Pair(1, 1) ) // right

    var dR = listOf<Int>(-1, -1, -1, 0, 0, 1, 1, 1)
    var dC = listOf<Int>(-1, 0, 1, -1, 1, -1, 0, 1)

    fun part1(input: List<String>): Int {
        var elves = mutableSetOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') {
                    elves.add(Pair(i, j))
                }
            }
        }
        var moveChoice = 0
        for (rounds in 1 .. 10) {
            var newElves = mutableSetOf<Pair<Int, Int>>()
            var positionCount = mutableMapOf<Pair<Int, Int>, Int>()
            // get moves
            for (elf in elves) {
                var cantMove = true
                for (k in 0 until 8) {
                    var next = Pair(elf.first + dR[k], elf.second + dC[k])
                    if (elves.contains(next)) {
                        cantMove = false
                        break
                    }
                }
                if (cantMove) continue
                for (moveIdx in 0 until 4) {
                    val i = (moveChoice + moveIdx) % 4
                    var count = 0
                    for (j in 0 until 3) {
                        var pos = Pair(elf.first + move[i][j].first, elf.second + move[i][j].second)
                        if (elves.contains(pos)) {
                            count++
                        }
                    }
                    if (count > 0) continue
                    positionCount[Pair(elf.first + move[i][1].first, elf.second + move[i][1].second)] = positionCount.getOrDefault(Pair(elf.first + move[i][1].first, elf.second + move[i][1].second), 0) + 1
                    break
                }
            }
            // apply moves
            for (elf in elves) {
                var moved = false
                var cantMove = true
                for (k in 0 until 8) {
                    var next = Pair(elf.first + dR[k], elf.second + dC[k])
                    if (elves.contains(next)) {
                        cantMove = false
                        break
                    }
                }
                if (!cantMove) {
                    for (moveIdx in 0 until 4) {
                        val i = (moveChoice + moveIdx) % 4
                        var count = 0
                        for (j in 0 until 3) {
                            var pos = Pair(elf.first + move[i][j].first, elf.second + move[i][j].second)
                            if (elves.contains(pos)) {
                                count++
                            }
                        }
                        if (count > 0) continue
                        if (positionCount.getOrDefault(Pair(elf.first + move[i][1].first, elf.second + move[i][1].second), 0) == 1) {
                            newElves.add(Pair(elf.first + move[i][1].first, elf.second + move[i][1].second))
                            moved = true
                        }
                        break
                    }
                }
                if (!moved) {
                    newElves.add(elf)
                }
            }
            elves = newElves
            moveChoice = (moveChoice + 1) % 4
        }
        var minRow = elves.minBy { it.first }!!.first
        var maxRow = elves.maxBy { it.first }!!.first
        var minCol = elves.minBy { it.second }!!.second
        var maxCol = elves.maxBy { it.second }!!.second
        return (maxRow - minRow + 1) * (maxCol - minCol + 1) - elves.size
    }

    fun part2(input: List<String>): Int {
        var elves = mutableSetOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') {
                    elves.add(Pair(i, j))
                }
            }
        }
        var moveChoice = 0
        var round = 0
        var moved = true
        while (moved) {
            round++
            moved = false
            var newElves = mutableSetOf<Pair<Int, Int>>()
            var positionCount = mutableMapOf<Pair<Int, Int>, Int>()
            // get moves
            for (elf in elves) {
                var cantMove = true
                for (k in 0 until 8) {
                    var next = Pair(elf.first + dR[k], elf.second + dC[k])
                    if (elves.contains(next)) {
                        cantMove = false
                        break
                    }
                }
                if (cantMove) continue
                else moved = true
                for (moveIdx in 0 until 4) {
                    val i = (moveChoice + moveIdx) % 4
                    var count = 0
                    for (j in 0 until 3) {
                        var pos = Pair(elf.first + move[i][j].first, elf.second + move[i][j].second)
                        if (elves.contains(pos)) {
                            count++
                        }
                    }
                    if (count > 0) continue
                    positionCount[Pair(elf.first + move[i][1].first, elf.second + move[i][1].second)] = positionCount.getOrDefault(Pair(elf.first + move[i][1].first, elf.second + move[i][1].second), 0) + 1
                    break
                }
            }
            // apply moves
            for (elf in elves) {
                var moved = false
                var cantMove = true
                for (k in 0 until 8) {
                    var next = Pair(elf.first + dR[k], elf.second + dC[k])
                    if (elves.contains(next)) {
                        cantMove = false
                        break
                    }
                }
                if (!cantMove) {
                    for (moveIdx in 0 until 4) {
                        val i = (moveChoice + moveIdx) % 4
                        var count = 0
                        for (j in 0 until 3) {
                            var pos = Pair(elf.first + move[i][j].first, elf.second + move[i][j].second)
                            if (elves.contains(pos)) {
                                count++
                            }
                        }
                        if (count > 0) continue
                        if (positionCount.getOrDefault(Pair(elf.first + move[i][1].first, elf.second + move[i][1].second), 0) == 1) {
                            newElves.add(Pair(elf.first + move[i][1].first, elf.second + move[i][1].second))
                            moved = true
                        }
                        break
                    }
                }
                if (!moved) {
                    newElves.add(elf)
                }
            }
            elves = newElves
            moveChoice = (moveChoice + 1) % 4
        }
        return round
    }

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
