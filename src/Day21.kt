fun main() {
    class Monke {
        var leaf = false
        var leftChild: Monke? = null
        var rightChild: Monke? = null
        var op: String = ""
        var value: Long = 0
        var parent: Monke? = null
        var toMove = false
    }

    fun getValue(node: Monke?) : Long {
        if (node == null) return 0
        if (node.leaf) {
            return node.value
        }
        return when (node.op) {
            "+" -> getValue(node.leftChild!!) + getValue(node.rightChild!!)
            "-" -> getValue(node.leftChild!!) - getValue(node.rightChild!!)
            "*" -> getValue(node.leftChild!!) * getValue(node.rightChild!!)
            else -> {
                assert(getValue(node.leftChild!!) % getValue(node.rightChild!!) == 0L)
                getValue(node.leftChild!!) / getValue(node.rightChild!!)
            }
        }
    }

    var root: Monke? = null

    fun part1(input: List<String>): Long {
        var nodes = mutableMapOf<String, Monke>()
        for (line in input) {
            if (line.isEmpty()) continue
            val parts = line.split(" ").map { if (it.last() == ':') it.dropLast(1) else it }
            val node = nodes.getOrPut(parts[0]) { Monke() }
        }
        root = nodes["root"]
        for (line in input) {
            if (line.isEmpty()) continue
            val parts = line.split(" ").map { if (it.last() == ':') it.dropLast(1) else it }
            val node = nodes[parts[0]]!!
            if (parts.size == 2) {
                node.leaf = true
                node.value = parts[1].toLong()
            } else {
                node.leftChild = nodes[parts[1]]
                node.rightChild = nodes[parts[3]]
                nodes[parts[1]]?.parent = node
                nodes[parts[3]]?.parent = node
                node.op = parts[2]
            }
        }
        return getValue(root)
    }

    fun part2(input: List<String>): Long {
        var nodes = mutableMapOf<String, Monke>()
        for (line in input) {
            if (line.isEmpty()) continue
            val parts = line.split(" ").map { if (it.last() == ':') it.dropLast(1) else it }
            val node = nodes.getOrPut(parts[0]) { Monke() }
        }
        root = nodes["root"]
        for (line in input) {
            if (line.isEmpty()) continue
            val parts = line.split(" ").map { if (it.last() == ':') it.dropLast(1) else it }
            val node = nodes[parts[0]]!!
            if (parts.size == 2) {
                node.leaf = true
                node.value = parts[1].toLong()
            } else {
                node.leftChild = nodes[parts[1]]
                node.rightChild = nodes[parts[3]]
                nodes[parts[1]]?.parent = node
                nodes[parts[3]]?.parent = node
                node.op = parts[2]
            }
        }
        var v = nodes["humn"]!!
        while (v.parent != null) {
            v.toMove = true
            v = v.parent!!
        }
        while (true) {
            if (root?.leftChild == nodes["humn"]) break
            if (root?.rightChild == nodes["humn"]) break
            if (root?.leftChild?.toMove == true) {
                val l = root?.leftChild
                val r = root?.rightChild
                var newNode = Monke()
                if (l?.leftChild?.toMove == true) {
                    when (l?.op) {
                        "+" -> {
                            newNode.op = "-"
                            newNode.leftChild = r
                            newNode.rightChild = l?.rightChild
                            root?.leftChild = l?.leftChild
                            root?.rightChild = newNode
                        }
                        "-" -> {
                            newNode.op = "+"
                            newNode.leftChild = r
                            newNode.rightChild = l?.rightChild
                            root?.leftChild = l?.leftChild
                            root?.rightChild = newNode
                        }
                        "*" -> {
                            newNode.op = "/"
                            newNode.leftChild = r
                            newNode.rightChild = l?.rightChild
                            root?.leftChild = l?.leftChild
                            root?.rightChild = newNode
                        }
                        else -> {
                            newNode.op = "*"
                            newNode.leftChild = r
                            newNode.rightChild = l?.rightChild
                            root?.leftChild = l?.leftChild
                            root?.rightChild = newNode
                        }
                    }
                }
                else {
                    when (l?.op) {
                        "+" -> {
                            newNode.op = "-"
                            newNode.leftChild = r
                            newNode.rightChild = l?.leftChild
                            root?.leftChild = l?.rightChild
                            root?.rightChild = newNode
                        }
                        "-" -> {
                            newNode.op = "+"
                            newNode.leftChild = r
                            newNode.rightChild = l?.rightChild
                            newNode.toMove = true
                            root?.leftChild = l?.leftChild
                            root?.rightChild = newNode
                        }
                        "*" -> {
                            newNode.op = "/"
                            newNode.leftChild = r
                            newNode.rightChild = l?.leftChild
                            root?.leftChild = l?.rightChild
                            root?.rightChild = newNode
                        }
                        else -> {
                            newNode.op = "*"
                            newNode.leftChild = r
                            newNode.rightChild = l?.rightChild
                            newNode.toMove = true
                            root?.leftChild = l?.leftChild
                            root?.rightChild = newNode
                        }
                    }
                }
            }
            else {
                val l = root?.leftChild
                val r = root?.rightChild
                var newNode = Monke()
                if (r?.leftChild?.toMove == true) {
                    when (r?.op) {
                        "+" -> {
                            newNode.op = "-"
                            newNode.leftChild = l
                            newNode.rightChild = r?.rightChild
                            root?.leftChild = newNode
                            root?.rightChild = r?.leftChild
                        }
                        "-" -> {
                            newNode.op = "+"
                            newNode.leftChild = l
                            newNode.rightChild = r?.rightChild
                            root?.leftChild = newNode
                            root?.rightChild = r?.leftChild
                        }
                        "*" -> {
                            newNode.op = "/"
                            newNode.leftChild = l
                            newNode.rightChild = r?.rightChild
                            root?.leftChild = newNode
                            root?.rightChild = r?.leftChild
                        }
                        else -> {
                            newNode.op = "*"
                            newNode.leftChild = l
                            newNode.rightChild = r?.rightChild
                            root?.leftChild = newNode
                            root?.rightChild = r?.leftChild
                        }
                    }
                }
                else {
                    when (r?.op) {
                        "+" -> {
                            newNode.op = "-"
                            newNode.leftChild = l
                            newNode.rightChild = r?.leftChild
                            root?.leftChild = newNode
                            root?.rightChild = r?.rightChild
                        }
                        "-" -> {
                            newNode.op = "+"
                            newNode.leftChild = l
                            newNode.rightChild = r?.rightChild
                            newNode.toMove = true
                            root?.leftChild = newNode
                            root?.rightChild = r?.leftChild
                        }
                        "*" -> {
                            newNode.op = "/"
                            newNode.leftChild = l
                            newNode.rightChild = r?.leftChild
                            root?.leftChild = newNode
                            root?.rightChild = r?.rightChild
                        }
                        else -> {
                            newNode.op = "*"
                            newNode.leftChild = l
                            newNode.rightChild = r?.rightChild
                            newNode.toMove = true
                            root?.leftChild = newNode
                            root?.rightChild = r?.leftChild
                        }
                    }
                }
            }
        }
        return getValue(if (root?.leftChild == nodes["humn"]) root?.rightChild else root?.leftChild)
    }

    val input = readInput("Day21")
    part1(input).println()
    part2(input).println()
}
