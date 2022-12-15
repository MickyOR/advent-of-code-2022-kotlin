fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0;
        for (line in input) {
            var (s1, s2) = line.split(",");
            var (l1, r1) = s1.split("-").map { it.toInt() };
            var (l2, r2) = s2.split("-").map { it.toInt() };
            if (l1 <= l2 && r2 <= r1 || l2 <= l1 && r1 <= r2) {
                sum += 1;
            }
        }
        return sum;
    }

    fun part2(input: List<String>): Int {
        var sum = 0;
        for (line in input) {
            var (s1, s2) = line.split(",");
            var (l1, r1) = s1.split("-").map { it.toInt() };
            var (l2, r2) = s2.split("-").map { it.toInt() };
            if (l2 in l1..r1 || r2 in l1..r1 || l1 in l2..r2 || r1 in l2..r2) {
                sum += 1;
            }
        }
        return sum;
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
