import java.lang.Math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var cycle = 0;
        var neededCycles = listOf<Int>(20, 60, 100, 140, 180, 220);
        var x = 1;
        var ans = 0;
        for (line in input) {
            var vs = line.split(" ");
            if (vs[0] == "noop") {
                cycle++;
                if (cycle in neededCycles) {
                    ans += x * cycle;
                }
                continue;
            }
            else {
                var delta = vs[1].toInt();
                cycle++;
                if (cycle in neededCycles) {
                    ans += x * cycle;
                }
                cycle++;
                if (cycle in neededCycles) {
                    ans += x * cycle;
                }
                x += delta;
            }
        }
        return ans;
    }

    fun part2(input: List<String>) {
        var cycle = 0;
        var x = 1;
        var row = 0;
        var ans: String = "";
        for (line in input) {
            var vs = line.split(" ");
            if (vs[0] == "noop") {
                cycle++;
                if (cycle == 41) {
                    cycle = 1;
                    row++;
                }
                if (abs(ans.length - row * 40 - x) <= 1) ans += '\u2588';
                else ans += ' ';
                continue;
            }
            var delta = vs[1].toInt();
            cycle++;
            if (cycle == 41) {
                cycle = 1;
                row++;
            }
            if (abs(ans.length - row * 40 - x) <= 1) ans += '\u2588';
            else ans += ' ';

            cycle++;
            if (cycle == 41) {
                cycle = 1;
                row++;
            }
            if (abs(ans.length - row * 40 - x) <= 1) ans += '\u2588';
            else ans += ' ';
            x += delta;
        }
        for (i in ans.indices step 40) {
            println(ans.substring(i, i + 40));
        }
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
