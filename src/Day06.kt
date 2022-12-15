fun main() {
    fun part1(input: List<String>): Int {
        var line = input[0];
        for (i in 3 until line.length) {
            var set = mutableSetOf<Char>();
            for (j in i - 3..i) {
                set.add(line[j]);
            }
            if (set.size == 4) {
                return i+1;
            }
        }
        return 0;
    }

    fun part2(input: List<String>): Int {
        var line = input[0];
        for (i in 13 until line.length) {
            var set = mutableSetOf<Char>();
            for (j in i - 13..i) {
                set.add(line[j]);
            }
            if (set.size == 14) {
                return i+1;
            }
        }
        return 0;
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
