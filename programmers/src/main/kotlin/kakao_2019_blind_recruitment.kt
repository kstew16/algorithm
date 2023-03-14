import java.util.PriorityQueue

enum class Order{PRE,POST}
fun main(){
    class TreeNode(val num:Int,val x:Int, var left:TreeNode?, var right:TreeNode?, val depth:Int)
    class Tree(var root:TreeNode?){
        var size:Int = 0
        fun addOrderedNode(node:TreeNode){
            fun addDFS(visiting:TreeNode, addingNode:TreeNode):Boolean{
                var added = false
                if(visiting.depth==addingNode.depth-1){
                    if(visiting.left==null && visiting.x>addingNode.x){
                        visiting.left = addingNode
                        added = true
                    }else if(visiting.right==null && visiting.x<addingNode.x){
                        visiting.right = addingNode
                        added = true
                    }
                }
                else if(addingNode.depth>visiting.depth){
                    visiting.left?.let {
                        if(addingNode.x<visiting.x)added = addDFS(it,addingNode)
                    }
                    if(!added) visiting.right?.let {
                        if(addingNode.x>visiting.x) added = addDFS(it,addingNode)
                    }
                }
                return added
            }
            if(this.root==null) this.root = node
            else addDFS(this.root!!,node)
            this.size+=1
        }
        fun getOrderedArr(order: Order):IntArray{
            var visitCount = 0
            val arr = IntArray(this.size)
            fun visitPreorder(visiting:TreeNode){
                arr[visitCount++] = visiting.num
                visiting.left?.let {visitPreorder(it)}
                visiting.right?.let {visitPreorder(it)}
            }
            fun visitPostorder(visiting:TreeNode){
                visiting.left?.let {visitPostorder(it)}
                visiting.right?.let {visitPostorder(it)}
                arr[visitCount++] = visiting.num
            }
            if(order==Order.POST) visitPostorder(this.root!!)
            if(order==Order.PRE) visitPreorder(this.root!!)
            return arr
        }
    }
    class Solution {
        fun solution(nodeInfo: Array<IntArray>): Array<IntArray> {
            val pq = PriorityQueue<IntArray>{a,b->
                when{
                    // y 좌표가 클 수록
                    a[1]>b[1] -> -1
                    a[1]<b[1] -> 1
                    else -> {
                        // x 좌표가 작을 수록 먼저 나옴
                        if(a[0]<b[0]) -1 else 1
                    }
                }
            }
            nodeInfo.forEachIndexed { index, ints ->
                pq.add(intArrayOf(ints[0],ints[1],index))
            }
            val t = Tree(null)
            var curDepth = -1
            var lastY = Int.MAX_VALUE

            while(pq.isNotEmpty()){
                val (x,y,nodeNum) = pq.poll()
                if(y<lastY){
                    lastY = y
                    curDepth++
                }
                t.addOrderedNode(TreeNode(nodeNum+1,x,null,null,curDepth))
            }
            return arrayOf(t.getOrderedArr(Order.PRE),t.getOrderedArr(Order.POST))
        }
    }
    val nodeInfo = arrayOf(
        intArrayOf(5,3),
        intArrayOf(11,5),
        intArrayOf(13,3),
        intArrayOf(3,5),
        intArrayOf(6,1),
        intArrayOf(1,3),
        intArrayOf(8,6),
        intArrayOf(7,2),
        intArrayOf(2,2)
    )
    Solution().solution(nodeInfo).forEach {
        println(it.joinToString(","))
    }
}