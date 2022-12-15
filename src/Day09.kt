import kotlin.math.abs
import kotlin.math.max

data class MutablePair<T, U>(var first: T, var second: U);

fun main() {
    fun part1(input: List<String>): Int {
        val ropeSize = 2;
        var set = mutableSetOf<Pair<Int, Int>>();
        var dx = listOf<Int>(1, 0, -1, 0);
        var dy = listOf<Int>(0, -1, 0, 1);
        var dir = mutableMapOf<Char, Int>();
        dir['R'] = 0;
        dir['D'] = 1;
        dir['L'] = 2;
        dir['U'] = 3;
        var rope = mutableListOf<MutablePair<Int, Int>>();
        for (ite in 0 until ropeSize) rope.add(MutablePair(0, 0));
        set.add(Pair(0, 0));
        for (line in input) {
            var vs = line.split(" ");
            var d = vs[0][0];
            var steps = vs[1].toInt();
            for (ite in 0 until steps) {
                rope[0] = MutablePair(rope[0].first + dx[dir[d]!!], rope[0].second + dy[dir[d]!!]);
                for (i in 1 until ropeSize) {
                    while (max(abs(rope[i].first - rope[i - 1].first), abs(rope[i].second - rope[i - 1].second)) > 1) {
                        if (rope[i].first < rope[i-1].first) rope[i].first += 1;
                        else if (rope[i].first > rope[i-1].first) rope[i].first -= 1;

                        if (rope[i].second < rope[i-1].second) rope[i].second++;
                        else if (rope[i].second > rope[i-1].second) rope[i].second--;
                    }
                }
                set.add(Pair(rope[ropeSize-1].first, rope[ropeSize-1].second));
            }
        }
        return set.size;
    }

    fun part2(input: List<String>): Int {
        val ropeSize = 10;
        var set = mutableSetOf<Pair<Int, Int>>();
        var dx = listOf<Int>(1, 0, -1, 0);
        var dy = listOf<Int>(0, -1, 0, 1);
        var dir = mutableMapOf<Char, Int>();
        dir['R'] = 0;
        dir['D'] = 1;
        dir['L'] = 2;
        dir['U'] = 3;
        var rope = mutableListOf<MutablePair<Int, Int>>();
        for (ite in 0 until ropeSize) rope.add(MutablePair(0, 0));
        set.add(Pair(0, 0));
        for (line in input) {
            var vs = line.split(" ");
            var d = vs[0][0];
            var steps = vs[1].toInt();
            for (ite in 0 until steps) {
                rope[0] = MutablePair(rope[0].first + dx[dir[d]!!], rope[0].second + dy[dir[d]!!]);
                for (i in 1 until ropeSize) {
                    while (max(abs(rope[i].first - rope[i - 1].first), abs(rope[i].second - rope[i - 1].second)) > 1) {
                        if (rope[i].first < rope[i-1].first) rope[i].first += 1;
                        else if (rope[i].first > rope[i-1].first) rope[i].first -= 1;

                        if (rope[i].second < rope[i-1].second) rope[i].second++;
                        else if (rope[i].second > rope[i-1].second) rope[i].second--;
                    }
                }
                set.add(Pair(rope[ropeSize-1].first, rope[ropeSize-1].second));
            }
        }
        return set.size;
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
