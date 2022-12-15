fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0;
        for (line in input) {
            var set = mutableSetOf<Char>();
            var s1 = line.substring(0, line.length/2);
            var s2 = line.substring(line.length/2);
            for (c in s1) {
                set.add(c);
            }
            for (c in s2) {
                if (set.contains(c)) {
                    sum += if (c in 'a'..'z') c.code - 'a'.code + 1;
                    else c.code - 'A'.code + 27;
                    break;
                }
            }
        }
        return sum;
    }

    fun part2(input: List<String>): Int {
        var sum = 0;
        for (i in 0..input.size-1 step 3) {
            var set1 = mutableSetOf<Char>();
            var set2 = mutableSetOf<Char>();
            var set3 = mutableSetOf<Char>();
            for (c in input[i]) {
                set1.add(c);
            }
            for (c in input[i+1]) {
                set2.add(c);
            }
            for (c in input[i+2]) {
                set3.add(c);
            }
            for (c in set1) {
                if (set2.contains(c) && set3.contains(c)) {
                    sum += if (c in 'a'..'z') c.code - 'a'.code + 1;
                    else c.code - 'A'.code + 27;
                    break;
                }
            }
        }
        return sum;
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
