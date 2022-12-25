import com.sun.source.tree.LiteralTree

fun main() {
    var dRow = listOf<Int>(0, 1, 0, -1)
    var dCol = listOf<Int>(1, 0, -1, 0)

    fun parseInstructions(line: String): MutableList<String> {
        var instructions = mutableListOf<String>()
        var lst = ""
        for (c in line) {
            if (c == 'L' || c == 'R') {
                if (lst.isNotBlank()) instructions.add(lst)
                instructions.add(c.toString())
                lst = ""
            }
            else lst += c
        }
        if (lst.isNotBlank()) instructions.add(lst)
        return instructions
    }

    fun part1(input: List<String>): Int {
        while (input.last().isEmpty()) input.dropLast(1)
        var r = 0
        var c = 0
        for (i in input[0].indices) {
            if (input[0][i] == '.') {
                c = i
                break
            }
        }
        var dir = 0
        var instructions = parseInstructions(input.last())
        input.dropLast(2)
        for (inst in instructions) {
            if (inst == "L") dir = (dir + 3) % 4
            else if (inst == "R") dir = (dir + 1) % 4
            else for (st in 1 .. inst.toInt()) {
                var a = r + dRow[dir]
                var b = c + dCol[dir]
                if (a < 0 || a >= input.size || b < 0 || b >= input[a].length || input[a][b] == ' ') {
                    var wr = r
                    var wc = c
                    var wdir = (dir + 2) % 4
                    while (wr >= 0 && wr < input.size && wc >= 0 && wc < input[wr].length && input[wr][wc] != ' ') {
                        wr += dRow[wdir]
                        wc += dCol[wdir]
                    }
                    wr += dRow[dir]
                    wc += dCol[dir]
                    assert(wr >= 0 && wr < input.size && wc >= 0 && wc < input[wr].length)
                    if (input[wr][wc] == '#') break
                    a = wr
                    b = wc
                }
                else if (input[a][b] == '#') break
                r = a
                c = b
            }
        }
        return 1000 * (r+1) + 4 * (c+1) + dir
    }

    fun part2(input: List<String>): Int {
        val faceSize = 50
        var face = mutableListOf<MutableList<Char>>()
        for (line in input) face.add(line.toMutableList())
        face = face.dropLast(2).toMutableList()

        var corners = mutableMapOf<Int, List<Pair<Int, Int>>>() // face -> list of corners {UL, UR, DR, DL}

        var cnt = 0
        for (i in face.indices) {
            for (j in face[i].indices) {
                if (face[i][j] == '.' || face[i][j] == '#') {
                    cnt++
                    corners[cnt] = listOf(Pair(i, j), Pair(i, j+faceSize-1), Pair(i+faceSize-1, j+faceSize-1), Pair(i+faceSize-1, j))
                    for (r in i until i + faceSize) {
                        for (c in j until j + faceSize) {
                            face[r][c] = (cnt + '0'.code).toChar()
                        }
                    }
                }
            }
        }

        class Wrap {
            var oriFace = 0
            var corner = 0
            var destFace = 0
            var destDir = 0
            var destCorner = 0
            var destCornerDir = 0

            fun wrappedPosition(r: Int, c: Int): Pair<Pair<Int, Int>, Int> {
                var distFromCorner = kotlin.math.abs(r - corners[oriFace]!![corner].first) + kotlin.math.abs(c - corners[oriFace]!![corner].second)
                var newR = corners[destFace]!![destCorner].first + dRow[destCornerDir] * distFromCorner
                var newC = corners[destFace]!![destCorner].second + dCol[destCornerDir] * distFromCorner
                var newDir = destDir
                return Pair(Pair(newR, newC), newDir)
            }
        }

        var wrapFunction = mutableMapOf< Pair<Int, Int>, Wrap > () // face, dir -> wrap function

        // face 1, wrap left
        wrapFunction[Pair(1, 2)] = Wrap().apply {
            oriFace = 1
            corner = 0
            destFace = 4
            destDir = 0
            destCorner = 3
            destCornerDir = 3
        }
        // face 1, wrap up
        wrapFunction[Pair(1, 3)] = Wrap().apply {
            oriFace = 1
            corner = 0
            destFace = 6
            destDir = 0
            destCorner = 0
            destCornerDir = 1
        }
        // face 2, wrap up
        wrapFunction[Pair(2, 3)] = Wrap().apply {
            oriFace = 2
            corner = 0
            destFace = 6
            destDir = 3
            destCorner = 3
            destCornerDir = 0
        }
        // face 2, wrap right
        wrapFunction[Pair(2, 0)] = Wrap().apply {
            oriFace = 2
            corner = 1
            destFace = 5
            destDir = 2
            destCorner = 2
            destCornerDir = 3
        }
        // face 2, wrap down
        wrapFunction[Pair(2, 1)] = Wrap().apply {
            oriFace = 2
            corner = 3
            destFace = 3
            destDir = 2
            destCorner = 1
            destCornerDir = 1
        }
        // face 3, wrap right
        wrapFunction[Pair(3, 0)] = Wrap().apply {
            oriFace = 3
            corner = 1
            destFace = 2
            destDir = 3
            destCorner = 3
            destCornerDir = 0
        }
        // face 5, wrap right
        wrapFunction[Pair(5, 0)] = Wrap().apply {
            oriFace = 5
            corner = 1
            destFace = 2
            destDir = 2
            destCorner = 2
            destCornerDir = 3
        }
        // face 5, wrap down
        wrapFunction[Pair(5, 1)] = Wrap().apply {
            oriFace = 5
            corner = 3
            destFace = 6
            destDir = 2
            destCorner = 1
            destCornerDir = 1
        }
        // face 6, wrap right
        wrapFunction[Pair(6, 0)] = Wrap().apply {
            oriFace = 6
            corner = 1
            destFace = 5
            destDir = 3
            destCorner = 3
            destCornerDir = 0
        }
        // face 6, wrap down
        wrapFunction[Pair(6, 1)] = Wrap().apply {
            oriFace = 6
            corner = 3
            destFace = 2
            destDir = 1
            destCorner = 0
            destCornerDir = 0
        }
        // face 6, wrap left
        wrapFunction[Pair(6, 2)] = Wrap().apply {
            oriFace = 6
            corner = 0
            destFace = 1
            destDir = 1
            destCorner = 0
            destCornerDir = 0
        }
        // face 4, wrap left
        wrapFunction[Pair(4, 2)] = Wrap().apply {
            oriFace = 4
            corner = 0
            destFace = 1
            destDir = 0
            destCorner = 3
            destCornerDir = 3
        }
        // face 4, wrap up
        wrapFunction[Pair(4, 3)] = Wrap().apply {
            oriFace = 4
            corner = 0
            destFace = 3
            destDir = 0
            destCorner = 0
            destCornerDir = 1
        }
        // face 3, wrap left
        wrapFunction[Pair(3, 2)] = Wrap().apply {
            oriFace = 3
            corner = 0
            destFace = 4
            destDir = 1
            destCorner = 0
            destCornerDir = 0
        }

        var r = 0
        var c = 0
        for (i in input[0].indices) {
            if (input[0][i] == '.') {
                c = i
                break
            }
        }
        var dir = 0
        var instructions = parseInstructions(input.last())
        input.dropLast(2)
        var visitedPositions = mutableMapOf<Pair<Int, Int>, Char>()
        fun printBoard(r: Int, c: Int, dir: Int) {
            println("=======================================================================\n")
            println("=======================================================================\n")
            println("=======================================================================\n")
            println("Board with r = $r, c = $c, dir = $dir")
            for (i in 0 until input.size - 2) {
                for (j in input[i].indices) {
                    print(
                        if (i == r && j == c) {
                            'O'
                        }
                        else if (visitedPositions.containsKey(Pair(i, j))) {
                            visitedPositions[Pair(i, j)]
                        } else {
                            input[i][j]
                        }
                    )
                }
                println()
            }
        }
        var wrapCount = 0
        for (inst in instructions) {
            if (inst == "L") dir = (dir + 3) % 4
            else if (inst == "R") dir = (dir + 1) % 4
            else for (st in 1 .. inst.toInt()) {
                visitedPositions[Pair(r, c)] = when (dir) {
                    0 -> '>'
                    1 -> 'v'
                    2 -> '<'
                    else -> '^'
                }
//                if (st > 1) printBoard(r, c, dir)
                var a = r + dRow[dir]
                var b = c + dCol[dir]
                if (a < 0 || a >= input.size || b < 0 || b >= input[a].length || input[a][b] == ' ') {
                    var wrap = wrapFunction[Pair((face[r][c] - '0').toInt(), dir)]?.wrappedPosition(r, c)
//                    println("wrap count: ${++wrapCount}")
                    var newR = wrap?.first?.first ?: 0
                    var newC = wrap?.first?.second ?: 0
                    var newDir = wrap?.second ?: 0
//                    println("wraps to $newR, $newC with dir = $newDir")
                    if (input[newR][newC] == '#') {
                        break
                    }
                    a = newR
                    b = newC
                    dir = newDir
                }
                else if (input[a][b] == '#') break
                r = a
                c = b
            }
//            printBoard(r, c, dir)
            visitedPositions[Pair(r, c)] = when (dir) {
                0 -> '>'
                1 -> 'v'
                2 -> '<'
                else -> '^'
            }
        }
        return 1000 * (r+1) + 4 * (c+1) + dir
    }

    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
