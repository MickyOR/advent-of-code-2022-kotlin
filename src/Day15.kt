import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var sensors = mutableListOf<Pair<Pair<Int,Int>,Int>>()
        var beacons = mutableSetOf<Pair<Int,Int>>()
        for (line in input) {
            if (line.isEmpty()) continue
            var vs = line.split(" ").map { if (it.last() == ',' || it.last() == ':') it.dropLast(1) else it }
            vs = vs.map { if (it.length > 2 && it.substring(0, 2) == "x=" || it.substring(0, 2) == "y=") it.substring(2) else it }
            var x = vs[2].toInt()
            var y = vs[3].toInt()
            var bx = vs[8].toInt()
            var by = vs[9].toInt()
            sensors.add( Pair( Pair(x, y), abs(x - bx) + abs( y - by ) ) )
            beacons.add( Pair(bx, by) )
        }
        var y = 2000000
        var ans = 0
        for (x in -3000000 .. 7000000) {
            if (beacons.contains(Pair(x, y))) {
                continue
            }
            for (s in sensors) {
                var (sx, sy) = s.first
                var dist = s.second
                if (abs(sx - x) + abs(sy - y) <= dist) {
                    ans++
                    break
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        var sensors = mutableListOf<Pair<Pair<Int,Int>,Int>>()
        var beacons = mutableSetOf<Pair<Int,Int>>()
        for (line in input) {
            if (line.isEmpty()) continue
            var vs = line.split(" ").map { if (it.last() == ',' || it.last() == ':') it.dropLast(1) else it }
            vs = vs.map { if (it.length > 2 && it.substring(0, 2) == "x=" || it.substring(0, 2) == "y=") it.substring(2) else it }
            var x = vs[2].toInt()
            var y = vs[3].toInt()
            var bx = vs[8].toInt()
            var by = vs[9].toInt()
            sensors.add( Pair( Pair(x, y), abs(x - bx) + abs( y - by ) ) )
            beacons.add( Pair(bx, by) )
        }
        val limX = 4000000
        val limY = 4000000
        for (s in sensors) {
            var (sx, sy) = s.first
            var dist = s.second
            var x = sx - dist - 1
            var y = sy
            // left to top
            while (x <= sx) {
                if (x in 0 .. limX && y in 0 .. limY) {
                    var good = true
                    for (ss in sensors) {
                        var (ssx, ssy) = ss.first
                        var ddist = ss.second
                        if (abs(x - ssx) + abs(y - ssy) <= ddist) {
                            good = false
                            break
                        }
                    }
                    if (good) return (x.toLong() * limX + y).toLong()
                }
                x++
                y++
            }
            // top to right
            x = sx
            y = sx + dist + 1
            while (y >= sy) {
                if (x in 0 .. limX && y in 0 .. limY) {
                    var good = true
                    for (ss in sensors) {
                        var (ssx, ssy) = ss.first
                        var ddist = ss.second
                        if (abs(x - ssx) + abs(y - ssy) <= ddist) {
                            good = false
                            break
                        }
                    }
                    if (good) return (x.toLong() * limX + y).toLong()
                }
                x++
                y--
            }
            // right to bottom
            x = sx + dist + 1
            y = sy
            while (x >= sx) {
                if (x in 0 .. limX && y in 0 .. limY) {
                    var good = true
                    for (ss in sensors) {
                        var (ssx, ssy) = ss.first
                        var ddist = ss.second
                        if (abs(x - ssx) + abs(y - ssy) <= ddist) {
                            good = false
                            break
                        }
                    }
                    if (good) return (x.toLong() * limX + y).toLong()
                }
                x--
                y--
            }
            // bottom to left
            x = sx
            y = sy - dist - 1
            while (y <= sy) {
                if (x in 0 .. limX && y in 0 .. limY) {
                    var good = true
                    for (ss in sensors) {
                        var (ssx, ssy) = ss.first
                        var ddist = ss.second
                        if (abs(x - ssx) + abs(y - ssy) <= ddist) {
                            good = false
                            break
                        }
                    }
                    if (good) return (x.toLong() * limX + y).toLong()
                }
                x--
                y++
            }
        }
        return -1
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
