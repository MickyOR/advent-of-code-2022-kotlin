fun main() {
    fun part1(input: List<String>): Int {
        var v = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            var line = input[i]
            v.add(Pair(line.toInt(), i))
        }
        var n = v.size
        for (ite in 0 until v.size) {
            var ini = 0
            for (i in v.indices) {
                if (v[i].second == ite) {
                    ini = i
                    break
                }
            }
            val steps = v[ini].first
            for (st in 0 until kotlin.math.abs(steps)) {
                var nxt = (ini + 1) % n
                if (steps < 0) nxt = (ini - 1 + n) % n
                var tmp = Pair(v[ini].first, v[ini].second)
                v[ini] = Pair(v[nxt].first, v[nxt].second)
                v[nxt] = tmp
                ini = nxt
            }
        }
        var zeroPos = 0
        for (i in v.indices) {
            if (v[i].first == 0) {
                zeroPos = i
                break
            }
        }
        return v[(zeroPos+1000)%n].first + v[(zeroPos+2000)%n].first + v[(zeroPos+3000)%n].first
    }

    fun part2(input: List<String>): Long {
        val key: Long = 811589153
        var v = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            var line = input[i]
            if (line.isEmpty()) continue
            v.add(Pair(line.toInt(), i))
        }
        var n = v.size
        for (mixing in 1 .. 10) {
            for (ite in 0 until v.size) {
                var ini = 0
                for (i in v.indices) {
                    if (v[i].second == ite) {
                        ini = i
                        break
                    }
                }
                var steps = (kotlin.math.abs(v[ini].first.toLong()) * key) % (n-1)
                if (v[ini].first < 0) steps *= -1
                for (st in 0 until kotlin.math.abs(steps)) {
                    var nxt = (ini + 1) % n
                    if (steps < 0) nxt = (ini - 1 + n) % n
                    var tmp = Pair(v[ini].first, v[ini].second)
                    v[ini] = Pair(v[nxt].first, v[nxt].second)
                    v[nxt] = tmp
                    ini = nxt
                }
            }
        }
        var zeroPos = 0
        for (i in v.indices) {
            if (v[i].first == 0) {
                zeroPos = i
                break
            }
        }
        return (v[(zeroPos+1000)%n].first + v[(zeroPos+2000)%n].first + v[(zeroPos+3000)%n].first) * key
    }

    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
