class TreeNode {
    var size: Int = 0;
    var idDir: Boolean = false;
    var parent = -1;
    var children = mutableMapOf<String, Int>();
}

var tree = mutableListOf<TreeNode>();

fun newNode(): Int {
    tree.add(TreeNode());
    return tree.size-1;
}

fun dfs(v: Int) {
    if (tree[v].idDir) {
        for (child in tree[v].children.values) {
            dfs(child);
            tree[v].size += tree[child].size;
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var root = newNode();
        tree[root].idDir = true;
        var curNode = root;
        for (line in input) {
            var vs = line.split(" ");
            if (vs[0] == "$") {
                if (vs[1] == "cd") {
                    curNode = if (vs[2] == "/") {
                        root;
                    }
                    else if (vs[2] == "..") {
                        tree[curNode].parent;
                    }
                    else {
                        tree[curNode].children[vs[2]]!!;
                    }
                }
            }
            else {
                if (!tree[curNode].children.contains(vs[1])) {
                    tree[curNode].children[vs[1]] = newNode();
                    tree[tree[curNode].children[vs[1]]!!].parent = curNode;
                }
                if (vs[0] == "dir") {
                    tree[tree[curNode].children[vs[1]]!!].idDir = true;
                }
                else {
                    tree[tree[curNode].children[vs[1]]!!].size = vs[0].toInt();
                }
            }
        }
        dfs(root);
        var ans = 0;
        for (node in tree) {
            if (node.idDir && node.size <= 100000) {
                ans += node.size;
            }
        }
        return ans;
    }

    fun part2(input: List<String>): Int {
        var root = newNode();
        tree[root].idDir = true;
        var curNode = root;
        for (line in input) {
            var vs = line.split(" ");
            if (vs[0] == "$") {
                if (vs[1] == "cd") {
                    curNode = if (vs[2] == "/") {
                        root;
                    }
                    else if (vs[2] == "..") {
                        tree[curNode].parent;
                    }
                    else {
                        tree[curNode].children[vs[2]]!!;
                    }
                }
            }
            else {
                if (!tree[curNode].children.contains(vs[1])) {
                    tree[curNode].children[vs[1]] = newNode();
                    tree[tree[curNode].children[vs[1]]!!].parent = curNode;
                }
                if (vs[0] == "dir") {
                    tree[tree[curNode].children[vs[1]]!!].idDir = true;
                }
                else {
                    tree[tree[curNode].children[vs[1]]!!].size = vs[0].toInt();
                }
            }
        }
        dfs(root);
        var totSize = 70000000;
        var needed = 30000000;
        var ans = totSize;
        for (node in tree) {
            if (node.idDir && totSize - (tree[root].size - node.size) >= needed) {
                ans = ans.coerceAtMost(node.size);
            }
        }
        return ans;
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
