import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

/* 오래 걸렸음. 트리 루트부터 안 주는거 설마설마했는데 진짜 그짓을 하네
 * 루트 히스토그램으로 찾아서 루트 좌우부터 차곡차곡 넣고 돌리니까 잘 되더라 (로직 자체는 오래 안 걸렸음 1시간?)
 * 그리고 긴가민가했는데 visited 용도로 사용된 depths 랑 positions 는 노드의 데이터를 인덱스로 삼아서 통통 뛰어다니는데
 * 트리 구조 서칭할 때 트리랑 독립적으로 visited 운용하고 싶으면 이 방식 채택해도 괜찮을 것 같다는 생각
 * 그와 별개로 수행시간이랑 메모리랑 다 터져서 피드백 한 번 해야 할 듯
 * 그리고 childSize 근본없으니까 nodeSize 구현하든지 하셈 ㅋㅋㅋ
*/

fun main()=with(BufferedReader(InputStreamReader(System.`in`))){

    data class TreeNode<T>(
        val data: T,
        var left: TreeNode<T>? = null,
        var right: TreeNode<T>? = null,
        var childSize :Int
    )
    class Tree{
        var root: TreeNode<Int>? = null
        fun addNode(data:Int, left:Int, right:Int){
            val newNode = TreeNode(
                data,
                if(left!=-1) TreeNode(left, childSize = 0) else null,
                if(right!=-1) TreeNode(right, childSize = 0) else null,
                ((if(left==-1)0 else 1) + (if(right==-1)0 else 1))
            )
            root?.let{
                search(newNode,it)
                return
            }
            root = newNode
        }
        fun search(addingNode:TreeNode<Int>,searchingNode:TreeNode<Int>):Int{
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
    val inputHistogram = IntArray(nodes+1){0}.apply { this[0] = Int.MAX_VALUE }
    val inputs = Array(nodes+1){
        // i번 인덱스를 데이터로 하는 입력이 inputs[i]에 들어있음
        intArrayOf(0,0,0)
    }
    repeat(nodes){
        val input = readLine().split(" ").map{it.toInt()}
        inputs[input[0]] = input.toIntArray()
        if(input[1]!=-1)inputHistogram[input[1]]++
        if(input[2]!=-1)inputHistogram[input[2]]++
    }

    // 0 번 자식으로 지목된 애가 루트이므로, 루트부터 트리를 확장해감
    val rootNodeInput = inputs[inputHistogram.indexOf(0)]
    val inputQueue = LinkedList<IntArray>().apply { add(rootNodeInput) }
    while(inputQueue.isNotEmpty()){
        val (inputData,left,right) = inputQueue.pollFirst()

        tree.addNode(inputData,left,right)
        if(left!=-1) inputQueue.add(inputs[left])
        if(right!=-1) inputQueue.add(inputs[right])
    }

    //tree.addNode(input[0],input[1],input[2])
    var rootLeftSubSize = 0
    tree.root!!.left?.let {
        rootLeftSubSize = it.childSize +1
    }

    var rootRightSubSize = 0
    tree.root!!.right?.let {
        rootRightSubSize = it.childSize +1
    }



    var rootPosition = rootLeftSubSize+1
    val rootIndex = rootNodeInput[0]-1

    val queue = LinkedList<TreeNode<Int>>().apply { this.addFirst(tree.root) }
    val depths = IntArray(nodes){0}
    val positions = IntArray(nodes){0}.apply { this[rootIndex]=rootPosition }
    val widthTable = Array(nodes){
        intArrayOf(Int.MAX_VALUE,0)
    }

    if(nodes != 1 + rootLeftSubSize + rootRightSubSize){
        depths[depths.size] //시밤쾅!!
    }

    while(queue.isNotEmpty()){
        val currentNode = queue.pollFirst()
        var currentIndex =currentNode.data-1
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

            val nextIndex =it.data-1
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
            val nextIndex =it.data-1
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