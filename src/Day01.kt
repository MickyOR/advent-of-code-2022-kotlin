fun main() {
    fun part1(input: List<String>): Int {
        var maxElf = 0;
        var curElf = 0;
        var lastEmpty = false;
        for (line in input) {
            if (line.isEmpty()) {
                maxElf = maxElf.coerceAtLeast(curElf);
                curElf = 0;
                lastEmpty = true;
            }
            else {
                curElf += line.toInt();
                lastEmpty = false;
            }
        }
        assert(lastEmpty);
        return maxElf;
    }

    fun part2(input: List<String>): Int {
        var elves = mutableListOf<Int>();
        var curElf = 0;
        for (line in input) {
            if (line.isEmpty()) {
                elves.add(curElf);
                curElf = 0;
            }
            else {
                curElf += line.toInt();
            }
        }
        elves.sortDescending();
        return elves[0] + elves[1] + elves[2];
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
