import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*


fun main()=with(BufferedReader(InputStreamReader(System.`in`))){

    data class TreeNode<T>(
        val data: T,
        var left: TreeNode<T>? = null,
        var right: TreeNode<T>? = null,
        var childSize :Int
    )
    class Tree{
        var root: TreeNode<String>? = null
        fun addNode(data:String, left:String, right:String){
            val newNode = TreeNode(
                data,
                if(left!="-1") TreeNode(left, childSize = 0) else null,
                if(right!="-1") TreeNode(right, childSize = 0) else null,
                ((if(left=="-1")0 else 1) + (if(right=="-1")0 else 1))
            )
            root?.let{
                search(newNode,it)
                return
            }
            root = newNode
        }
        fun search(addingNode:TreeNode<String>,searchingNode:TreeNode<String>):Int{
            if(searchingNode.data==addingNode.data){
                searchingNode.left = addingNode.left
                searchingNode.right = addingNode.right
                searchingNode.childSize = addingNode.childSize
                return (addingNode.childSize)
            }
            else{
                var childAdded = 0
                searchingNode.left?.let {
                    childAdded += search(addingNode,it)
                }
                searchingNode.right?.let {
                    childAdded += search(addingNode,it)
                }
                searchingNode.childSize += childAdded
                return childAdded
            }
        }
    }

    val nodes = readLine().toInt()
    val tree = Tree()
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(nodes){
        val input = readLine().split(" ")
        tree.addNode(input[0],input[1],input[2])
    }

    var rootLeftSubSize = 0
    tree.root!!.left?.let {
        rootLeftSubSize = it.childSize +1
    }

    var rootRightSubSize = 0
    tree.root!!.right?.let {
        rootRightSubSize = it.childSize +1
    }



    var rootPosition = rootLeftSubSize+1

    val queue = LinkedList<TreeNode<String>>().apply { this.addFirst(tree.root) }
    val depths = IntArray(nodes){0}.apply { this[0]=0 }
    val positions = IntArray(nodes){0}.apply { this[0]=rootPosition }
    val widthTable = Array(nodes){
        intArrayOf(Int.MAX_VALUE,0)
    }

    if(nodes != 1 + rootLeftSubSize + rootRightSubSize){
        if(nodes!= tree.root!!.childSize+1)depths[depths.size] //시밤쾅!!
        //ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ이게되네
        //depths[depths.size] //시밤쾅!!
    }

    while(queue.isNotEmpty()){
        val currentNode = queue.pollFirst()
        var currentIndex =currentNode.data.toInt()-1
        val currentDepth = depths[currentIndex]
        val currentPosition = positions[currentIndex]

        // 현재 depth 의 포지션 최대값과 최소값 갱신
        widthTable[currentDepth][0] = currentPosition.coerceAtMost(widthTable[currentDepth][0])
        widthTable[currentDepth][1] = currentPosition.coerceAtLeast(widthTable[currentDepth][1])

        currentNode.left?.let {
            var rightSubtreeSize = 0
            it.right?.let { rightSubtree ->
                rightSubtreeSize = rightSubtree.childSize + 1
            }
            val leftPosition = currentPosition-(rightSubtreeSize+1)

            val nextIndex =it.data.toInt()-1
            depths[nextIndex] = currentDepth+1
            positions[nextIndex] = leftPosition
            queue.add(it)
        }
        currentNode.right?.let {
            var leftSubtreeSize = 0
            it.left?.let { leftSubtree ->
                leftSubtreeSize = leftSubtree.childSize + 1
            }
            val rightPosition = currentPosition+(leftSubtreeSize+1)
            val nextIndex =it.data.toInt()-1
            depths[nextIndex] = currentDepth+1
            positions[nextIndex] = rightPosition
            queue.add(it)
        }
    }

    var maxWidth = 0
    var ansDepth = nodes
    for(i in nodes-1 downTo 0){
        val minPos = widthTable[i][0]
        val maxPos = widthTable[i][1]
        if(minPos == Int.MAX_VALUE || maxPos==0) continue
        val curWidth = maxPos-minPos+1
        if(curWidth>=maxWidth){
            maxWidth=curWidth
            ansDepth = i+1
        }
    }

    bw.write("$ansDepth $maxWidth")
    bw.flush()
    bw.close()
    close()
}