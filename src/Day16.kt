import kotlin.math.*

fun main() {
    var nodes: Int = 16
    var dp = Array(1 shl nodes) { Array(nodes) { IntArray(31) { -1 } } }
    var dist = Array(60) { IntArray(60) { 10000000 } }
    var flow = IntArray(60) { 0 }
    var g = Array(60) { mutableListOf<Int>() }
    var id = mutableMapOf<String, Int>()

    fun f(mask: Int, v: Int, t: Int) : Int {
        if (t < 0) return -10000000
        if (mask == 0) return 0
        if (dp[mask][v][t] != -1) return dp[mask][v][t]
        var res = 0
        for (i in 0 until nodes) {
            if (mask and (1 shl i) != 0) {
                res = max(res, f(mask xor (1 shl i), i, t-1-dist[v][i]) + (t-1-dist[v][i])*flow[i])
            }
        }
        dp[mask][v][t] = res
        return res
    }

    fun part1(input: List<String>): Int {
        id["AA"] = 15
        for (line in input) {
            if (line.isEmpty()) continue
            var vs = line.split(" ").map {
                if (it.last() == ';' || it.last() == ',') it.dropLast(1) else it
            }
            vs = vs.map {
                if (it.length >= 5 && it.substring(0, 5) == "rate=") it.drop(5) else it
            }
            if (vs[4].toInt() == 0) continue
            if (!id.containsKey(vs[1])) id[vs[1]] = id.size-1
        }
        for (line in input) {
            if (line.isEmpty()) continue
            var vs = line.split(" ").map {
                if (it.last() == ';' || it.last() == ',') it.dropLast(1) else it
            }
            vs = vs.map {
                if (it.length >= 5 && it.substring(0, 5) == "rate=") it.drop(5) else it
            }
            if (!id.containsKey(vs[1])) id[vs[1]] = id.size
            var v = id[vs[1]]!!
            dist[v][v] = 0
            g[v].add(v)
            flow[v] = vs[4].toInt()
            for (i in 9 until vs.size) {
                if (!id.containsKey(vs[i])) id[vs[i]] = id.size
                var u = id[vs[i]]!!
                dist[v][u] = 1
                g[v].add(u)
            }
        }
        for (k in 0 until 60) {
            for (i in 0 until 60) {
                for (j in 0 until 60) {
                    dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
                }
            }
        }
        return f((1 shl nodes)-1, 15, 30)
    }

    fun part2(input: List<String>): Int {
        // flow and dist are already calculated
        var ans = 0
        for (mask in 0 until (1 shl (nodes-1))) {
            var mask1 = mask or (1 shl 15)
            var mask2 = ((1 shl (nodes))-1) xor mask
            ans = max(ans, f(mask1, 15, 26) + f(mask2, 15, 26))
        }
        return ans
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
