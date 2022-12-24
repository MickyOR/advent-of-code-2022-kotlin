fun main() {
    class Blueprint() {
        // cost triple: {ore, clay, obsidian}
        var oreCost = Triple(0, 0, 0)
        var clayCost = Triple(0, 0, 0)
        var obsidianCost = Triple(0, 0, 0)
        var geodeCost = Triple(0, 0, 0)
        var maxOre = 0
        var maxClay = 0
        var maxObsidian = 0
        var maxGeode = 0

        fun parseCost(line: String) {
            var vs = line.split(": ")
            vs = vs[1].split(". ")
            var ore = vs[0].split(" ")
            var clay = vs[1].split(" ")
            var obsidian = vs[2].split(" ")
            var geode = vs[3].split(" ")
            oreCost = Triple(ore[4].toInt(), 0, 0)
            clayCost = Triple(clay[4].toInt(), 0, 0)
            obsidianCost = Triple(obsidian[4].toInt(), obsidian[7].toInt(), 0)
            geodeCost = Triple(geode[4].toInt(), 0, geode[7].toInt())
            maxOre = maxOf(oreCost.first, clayCost.first, obsidianCost.first, geodeCost.first)
            maxClay = maxOf(oreCost.second, clayCost.second, obsidianCost.second, geodeCost.second)
            maxObsidian = maxOf(oreCost.third, clayCost.third, obsidianCost.third, geodeCost.third)
        }
    }

    var currentBlueprint: Blueprint? = null

    fun f(time: Int): Int {
        var state = listOf(0, 1, 0, 0, 0, 0, 0, time)
        var q = mutableListOf(state)
        var visited = mutableSetOf<List<Int>>()
        var ans = 0
        visited.add(state)
        var cnt = 0
        while (q.isNotEmpty()) {
            state = q.removeAt(0)
            var ore = state[0]
            var oreR = state[1]
            var clay = state[2]
            var clayR = state[3]
            var obsidian = state[4]
            var obsidianR = state[5]
            var geode = state[6]
            var time = state[7]
            ans = maxOf(ans, geode)
            if (time == 0) {
                continue
            }
            ore = minOf(ore, currentBlueprint!!.maxOre * time)
            clay = minOf(clay, currentBlueprint!!.maxClay * time)
            obsidian = minOf(obsidian, currentBlueprint!!.maxObsidian * time)

            cnt++
            if (cnt%1000000 == 0) {
                println(cnt)
            }

            // do nothing
            var newState = listOf(ore+oreR, oreR, clay+clayR, clayR, obsidian+obsidianR, obsidianR, geode, time-1)
            if (!visited.contains(newState)) {
                q.add(newState)
                visited.add(newState)
            }
            // make ore robot if needed
            if (oreR < currentBlueprint!!.maxOre && ore >= currentBlueprint!!.oreCost.first) {
                newState = listOf(ore-currentBlueprint!!.oreCost.first+oreR, oreR+1, clay+clayR, clayR, obsidian+obsidianR, obsidianR, geode, time-1)
                if (!visited.contains(newState)) {
                    q.add(newState)
                    visited.add(newState)
                }
            }
            // make clay robot if needed
            if (clayR < currentBlueprint!!.maxClay && ore >= currentBlueprint!!.clayCost.first) {
                newState = listOf(ore-currentBlueprint!!.clayCost.first+oreR, oreR, clay+clayR, clayR+1, obsidian+obsidianR, obsidianR, geode, time-1)
                if (!visited.contains(newState)) {
                    q.add(newState)
                    visited.add(newState)
                }
            }
            // make obsidian robot if needed
            if (obsidianR < currentBlueprint!!.maxObsidian && ore >= currentBlueprint!!.obsidianCost.first && clay >= currentBlueprint!!.obsidianCost.second) {
                newState = listOf(ore-currentBlueprint!!.obsidianCost.first+oreR, oreR, clay-currentBlueprint!!.obsidianCost.second+clayR, clayR, obsidian+obsidianR, obsidianR+1, geode, time-1)
                if (!visited.contains(newState)) {
                    q.add(newState)
                    visited.add(newState)
                }
            }
            // make geode robot
            if (ore >= currentBlueprint!!.geodeCost.first && obsidian >= currentBlueprint!!.geodeCost.third) {
                newState = listOf(ore-currentBlueprint!!.geodeCost.first+oreR, oreR, clay+clayR, clayR, obsidian-currentBlueprint!!.geodeCost.third+obsidianR, obsidianR, geode+(time-1), time-1)
                if (!visited.contains(newState)) {
                    q.add(newState)
                    visited.add(newState)
                }
            }
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        var ans = 0
        for (id in 1 .. input.size) {
            var line = input[id-1]
            currentBlueprint = Blueprint()
            currentBlueprint!!.parseCost(line)
            var maxGeodes = f(24)
            ans += maxGeodes * id
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 1
        for (id in 1 .. 3) {
            var line = input[id-1]
            currentBlueprint = Blueprint()
            currentBlueprint!!.parseCost(line)
            var maxGeodes = f(32)
            println("id: $id, maxGeodes: $maxGeodes")
            ans *= maxGeodes
        }
        return ans
    }

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
