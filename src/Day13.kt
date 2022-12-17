class Item: Comparable<Item> {
    public var originalString: String = ""
    var isLeaf = false
    var value = 0
    var children = mutableListOf<Item>()

    override fun compareTo(other: Item): Int {
        var originalString: String = ""
        var root1 = parse(this.originalString)
        var root2 = parse(other.originalString)
        var stack1 = mutableListOf<Item>()
        var stack2 = mutableListOf<Item>()
        stack1.add(root1)
        stack2.add(root2)
        var ordered = true
        while (stack1.isNotEmpty() && stack2.isNotEmpty()) {
            var item1 = stack1.last()
            var item2 = stack2.last()
            if (item1.isLeaf && item2.isLeaf) {
                if (item1.value < item2.value) {
                    ordered = true
                    break
                }
                else if (item1.value > item2.value) {
                    ordered = false
                    break
                }
                else {
                    stack1.removeAt(stack1.size-1)
                    stack2.removeAt(stack2.size-1)
                    continue
                }
            }
            if (item1.isLeaf) {
                var newItem = Item()
                newItem.children.add(item1)
                stack1[stack1.size-1] = newItem
                continue
            }
            if (item2.isLeaf) {
                var newItem = Item()
                newItem.children.add(item2)
                stack2[stack2.size-1] = newItem
                continue
            }
            if (item1.children.size == 0 && item2.children.size == 0) {
                stack1.removeAt(stack1.size-1)
                stack2.removeAt(stack2.size-1)
                continue
            }
            if (item1.children.size == 0) {
                ordered = true
                break
            }
            if (item2.children.size == 0) {
                ordered = false
                break
            }
            stack1.add(item1.children[0])
            stack2.add(item2.children[0])
            item1.children.removeAt(0)
            item2.children.removeAt(0)
        }
        if (stack1.isEmpty() && stack2.isEmpty()) {
            return 0
        }
        if (ordered) {
            return -1
        }
        else {
            return 1
        }
    }
}

fun parse(line: String) : Item {
    var root = Item()
    root.originalString = line
    var stack = mutableListOf<Item>()
    stack.add(root)
    var i = 0
    while (i < line.length) {
        if (line[i] == '[') {
            var item = Item()
            stack.last().children.add(item)
            stack.add(item)
            i++
        } else if (line[i] == ']') {
            stack.removeAt(stack.size-1)
            i++
        } else if (line[i] == ',') {
            i++
        } else {
            var item = Item()
            item.isLeaf = true
            var valueString = ""
            while (i < line.length && line[i] != ',' && line[i] != ']') {
                valueString += line[i]
                i++
            }
            item.value = valueString.toInt()
            stack.last().children.add(item)
        }
    }
    return root
}

fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        var cnt = 0
        for (i in 0 until input.size step 3) {
            cnt++
            var root1 = parse(input[i])
            var root2 = parse(input[i+1])
            var ordered = root1 < root2
            if (ordered) {
                ans += cnt
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var itemList = mutableListOf<Item>()
        for (i in 0 until input.size step 3) {
            var root1 = parse(input[i])
            var root2 = parse(input[i+1])
            itemList.add(root1)
            itemList.add(root2)
        }
        var separator1 = parse("[[2]]")
        var separator2 = parse("[[6]]")
        itemList.add(separator1)
        itemList.add(separator2)
        itemList.sort()
        var ans = 1
        for (i in itemList.indices) {
            if (separator1.compareTo(itemList[i]) == 0) {
                ans *= (i+1)
            }
            if (separator2.compareTo(itemList[i]) == 0) {
                ans *= (i+1)
            }
        }
        return ans
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
