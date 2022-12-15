fun main() {
    fun part1(input: List<String>): Int {
        var map = mutableMapOf<Char, Int>();
        map['A'] = 0;
        map['B'] = 1;
        map['C'] = 2;
        map['X'] = 0;
        map['Y'] = 1;
        map['Z'] = 2;
        var score: Int = 0;
        for (line in input) {
            var p1: Char = line[0];
            var p2: Char = line[2];
            score += map[p2]!! + 1;
            if (map[p1] == map[p2]) score += 3;
            else {
                var v1: Int = map[p1]!!;
                var v2: Int = map[p2]!!;
                if (v2 == (v1 + 1) % 3) score += 6;
            }
        }
        return score;
    }

    fun part2(input: List<String>): Int {
        var map = mutableMapOf<Char, Int>();
        map['A'] = 0;
        map['B'] = 1;
        map['C'] = 2;
        map['X'] = 0;
        map['Y'] = 1;
        map['Z'] = 2;
        var score: Int = 0;
        for (line in input) {
            var p1: Char = line[0];
            var p2: Char = line[2];
            var x = map[p1]!!;
            var y = map[p2]!!;
            when (y) {
                0 -> {
                    score += (x-1+3)%3 + 1;
                }
                1 -> {
                    score += 3;
                    score += x+1;
                }
                else -> {
                    score += 6;
                    score += (x+1)%3 + 1;
                }
            }
        }
        return score;
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
