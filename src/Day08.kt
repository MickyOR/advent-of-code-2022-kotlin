fun main() {
    fun part1(input: List<String>): Int {
        var n = input.size;
        var m = input[0].length;
        var set = mutableSetOf<Pair<Int, Int>>();
        for (i in 0 until n) {
            var lst = -1;
            for (j in 0 until m) {
                var h = input[i][j].code - '0'.code;
                if (lst < h) {
                    set.add(Pair(i, j));
                }
                lst = lst.coerceAtLeast(h);
            }
            lst = -1;
            for (j in m - 1 downTo 0) {
                var h = input[i][j].code - '0'.code;
                if (lst < h) {
                    set.add(Pair(i, j));
                }
                lst = lst.coerceAtLeast(h);
            }
        }
        for (j in 0 until m) {
            var lst = -1;
            for (i in 0 until n) {
                var h = input[i][j].code - '0'.code;
                if (lst < h) {
                    set.add(Pair(i, j));
                }
                lst = lst.coerceAtLeast(h);
            }
            lst = -1;
            for (i in n - 1 downTo 0) {
                var h = input[i][j].code - '0'.code;
                if (lst < h) {
                    set.add(Pair(i, j));
                }
                lst = lst.coerceAtLeast(h);
            }
        }
        return set.size;
    }

    fun part2(input: List<String>): Int {
        var n = input.size;
        var m = input[0].length;
        var maxScore = 0;
        var dy = listOf<Int>(1, 0, -1, 0);
        var dx = listOf<Int>(0, 1, 0, -1);
        for (i in 0 until n) {
            for (j in 0 until m) {
                var score = 1;
                var h = input[i][j].code - '0'.code;
                for (k in 0 until 4) {
                    var y = i + dy[k];
                    var x = j + dx[k];
                    var dist = 0;
                    while (y in 0 until n && x in 0 until m) {
                        dist++;
                        var h2 = input[y][x].code - '0'.code;
                        if (h2 >= h) break;
                        y += dy[k];
                        x += dx[k];
                    }
                    score *= dist;
                }
                maxScore = maxScore.coerceAtLeast(score);
            }
        }
        return maxScore;
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
