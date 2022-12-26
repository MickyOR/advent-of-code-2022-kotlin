fun main() {
    fun toDec(s: String): Long {
        var ans: Long = 0
        var pot: Long = 1
        for (i in s.length - 1 downTo 0) {
            if (s[i] == '=') {
                ans -= 2*pot
            }
            else if (s[i] == '-') {
                ans -= pot
            }
            else {
                ans += (s[i] - '0')*pot
            }
            pot *= 5
        }
        return ans
    }

    fun toSNAFU(num: Long): String {
        var n = num
        var digs = mutableListOf<Int>()
        while (n > 0) {
            digs.add((n % 5).toInt())
            n /= 5
        }
        val aux = digs.size
        for (i in 0 until aux) digs.add(0)
        for (i in 0 until digs.size) {
            while (digs[i] >= 5) {
                digs[i] -= 5
                digs[i+1] += 1
            }
            if (digs[i] == 3) {
                digs[i] = -2
                digs[i+1] += 1
            }
            else if (digs[i] == 4) {
                digs[i] = -1
                digs[i+1] += 1
            }
        }
        var ans = ""
        for (i in digs.size - 1 downTo 0) {
            if (digs[i] == -2) ans += "="
            else if (digs[i] == -1) ans += "-"
            else if (ans.length > 0 || digs[i] > 0) ans += digs[i].toString()
        }
        return ans
    }

    fun part1(input: List<String>): String {
        var sum: Long = 0
        for (line in input) {
            sum += toDec(line)
        }
        return toSNAFU(sum)
    }

    fun part2(input: List<String>): String {
        return "x"
    }

    val input = readInput("Day25")
    part1(input).println()
    part2(input).println()
}
