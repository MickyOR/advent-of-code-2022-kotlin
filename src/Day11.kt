class Monke {
    var items = MutableList(0) { mutableListOf<Pair<Int, Int>>() }
    var operation: (Int) -> Int = { a -> a }
    var trueMonke: Int = 0
    var falseMonke: Int = 0
    var inspectCount: Long = 0
}

fun main() {
    fun part1(input: List<String>): Long {
        var monkes = mutableListOf<Monke>()
        var rules = mutableListOf<Int>()
        for (i in 0 until input.size step 7) {
            val vs = input[i+3].split(" ")
            val modulo = vs[5].toInt()
            rules.add(modulo)
        }
        for (i in 0 until input.size step 7) {
            var monke = Monke()
            var vs = input[i+1].split(" ").map { if (it.isNotEmpty() && it[it.length-1] == ',') it.substring(0, it.length-1) else it }
            for (j in 4 until vs.size) {
                var v = vs[j].toInt()
                var itemList = mutableListOf<Pair<Int, Int>>()
                for (k in 0 until rules.size) {
                    itemList.add(Pair(v, rules[k]))
                }
                monke.items.add(itemList)
            }
            vs = input[i+2].split(" ")
            if (vs[vs.size-1] == "old") {
                monke.operation = if (vs[vs.size-2] == "+") { a -> a + a } else { a -> a * a }
            }
            else {
                var opValue = vs[vs.size - 1].toInt()
                monke.operation = if (vs[vs.size - 2] == "+") { a -> a + opValue } else { a -> a * opValue }
            }
            vs = input[i+4].split(" ")
            monke.trueMonke = vs[vs.size-1].toInt()
            vs = input[i+5].split(" ")
            monke.falseMonke = vs[vs.size-1].toInt()
            monkes.add(monke)
        }
        val rounds = 20
        for (ite in 1 .. rounds) {
            for (idx in 0 until monkes.size) {
                var monke = monkes[idx]
                var trueItems = MutableList(0) { mutableListOf<Pair<Int, Int>>() }
                var falseItems = MutableList(0) { mutableListOf<Pair<Int, Int>>() }
                monke.inspectCount += monke.items.size
                for (item in monke.items) {
                    var itemWithRules = mutableListOf<Pair<Int, Int>>()
                    for (pair in item) {
                        var newPair: Pair<Int, Int> = Pair(monke.operation(pair.first) / 3, pair.second)
                        itemWithRules.add(newPair)
                    }
                    if (itemWithRules[0].first % rules[idx] == 0) {
                        trueItems.add(itemWithRules)
                    } else {
                        falseItems.add(itemWithRules)
                    }
                }
                monke.items.clear()
                monkes[monke.trueMonke].items.addAll(trueItems)
                monkes[monke.falseMonke].items.addAll(falseItems)
            }
        }
        var auxVec = mutableListOf<Long>()
        for (monke in monkes) {
            auxVec.add(monke.inspectCount)
        }
        auxVec.sortDescending()
        return auxVec[0] * auxVec[1]
    }

    fun part2(input: List<String>): Long {
        var monkes = mutableListOf<Monke>()
        var rules = mutableListOf<Int>()
        for (i in 0 until input.size step 7) {
            val vs = input[i+3].split(" ")
            val modulo = vs[5].toInt()
            rules.add(modulo)
        }
        for (i in 0 until input.size step 7) {
            var monke = Monke()
            var vs = input[i + 1].split(" ")
                .map { if (it.isNotEmpty() && it[it.length - 1] == ',') it.substring(0, it.length - 1) else it }
            for (j in 4 until vs.size) {
                var v = vs[j].toInt()
                var itemList = mutableListOf<Pair<Int, Int>>()
                for (k in 0 until rules.size) {
                    itemList.add(Pair(v % rules[k], rules[k]))
                }
                monke.items.add(itemList)
            }
            vs = input[i + 2].split(" ")
            if (vs[vs.size - 1] == "old") {
                monke.operation = if (vs[vs.size - 2] == "+") { a -> a + a } else { a -> a * a }
            } else {
                var opValue = vs[vs.size - 1].toInt()
                monke.operation = if (vs[vs.size - 2] == "+") { a -> a + opValue } else { a -> a * opValue }
            }
            vs = input[i + 4].split(" ")
            monke.trueMonke = vs[vs.size - 1].toInt()
            vs = input[i + 5].split(" ")
            monke.falseMonke = vs[vs.size - 1].toInt()
            monkes.add(monke)
        }
        val rounds = 10000
        for (ite in 1 .. rounds) {
            for (idx in 0 until monkes.size) {
                var monke = monkes[idx]
                var trueItems = MutableList(0) { mutableListOf<Pair<Int, Int>>() }
                var falseItems = MutableList(0) { mutableListOf<Pair<Int, Int>>() }
                monke.inspectCount += monke.items.size
                for (item in monke.items) {
                    var itemWithRules = mutableListOf<Pair<Int, Int>>()
                    for (pair in item) {
                        var newPair: Pair<Int, Int> = Pair(monke.operation(pair.first) % pair.second, pair.second)
                        itemWithRules.add(newPair)
                    }
                    if (itemWithRules[idx].first == 0) {
                        trueItems.add(itemWithRules)
                    } else {
                        falseItems.add(itemWithRules)
                    }
                }
                monke.items.clear()
                monkes[monke.trueMonke].items.addAll(trueItems)
                monkes[monke.falseMonke].items.addAll(falseItems)
            }
        }
        var auxVec = mutableListOf<Long>()
        for (monke in monkes) {
            auxVec.add(monke.inspectCount)
        }
        auxVec.sortDescending()
        return auxVec[0] * auxVec[1]
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
