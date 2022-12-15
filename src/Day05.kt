import java.util.*

fun main() {
    fun part1(input: List<String>): String {
        var stacks = Array(9) { ArrayDeque<Char>() }
        var stacksDescription = true;
        for (line in input) {
            if (line.isEmpty()) {
                stacksDescription = false;
                continue;
            }
            if (stacksDescription) {
                if (line[1] in '1'..'9') {
                    continue;
                }
                var idx = -1;
                for (i in 1 until line.length step 4) {
                    idx++;
                    if (line[i] == ' ') {
                        continue;
                    }
                    stacks[idx].addFirst(line[i]);
                }
                continue;
            }
            val vs = line.split(" ");
            var from = vs[3].toInt()-1;
            var to = vs[5].toInt()-1;
            var amount = vs[1].toInt();
            for (ite in 1..amount) {
                stacks[to].addLast(stacks[from].removeLast());
            }
        }
        var ans: String = "";
        for (i in 0..8) {
            ans += stacks[i].last;
        }
        return ans;
    }

    fun part2(input: List<String>): String {
        var stacks = Array(9) { ArrayDeque<Char>() }
        var stacksDescription = true;
        for (line in input) {
            if (line.isEmpty()) {
                stacksDescription = false;
                continue;
            }
            if (stacksDescription) {
                if (line[1] in '1'..'9') {
                    continue;
                }
                var idx = -1;
                for (i in 1 until line.length step 4) {
                    idx++;
                    if (line[i] == ' ') {
                        continue;
                    }
                    stacks[idx].addFirst(line[i]);
                }
                continue;
            }
            val vs = line.split(" ");
            var from = vs[3].toInt()-1;
            var to = vs[5].toInt()-1;
            var amount = vs[1].toInt();
            var v = mutableListOf<Char>();
            for (ite in 1..amount) {
                v.add(stacks[from].removeLast());
            }
            for (ite in 1..amount) {
                stacks[to].addLast(v[amount-ite]);
            }
        }
        var ans: String = "";
        for (i in 0..8) {
            ans += stacks[i].last;
        }
        return ans;
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
