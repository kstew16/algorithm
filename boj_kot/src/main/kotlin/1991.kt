import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
fun main()=with(BufferedReader(InputStreamReader(System.`in`))){

    data class TreeNode<T>(
        val data: T,
        var left: TreeNode<T>? = null,
        var right: TreeNode<T>? = null
    )
    class Tree{
        var root: TreeNode<String>? = null
        fun addNode(data:String, left:String, right:String){
            val newNode = TreeNode(
                data,
                if(left!=".") TreeNode(left) else null,
                if(right!=".") TreeNode(right) else null
            )
            root?.let{
                search(newNode,it)
                return
            }
            root = newNode
        }
        fun search(addingNode:TreeNode<String>,searchingNode:TreeNode<String>){
            if(searchingNode.data==addingNode.data){
                searchingNode.left = addingNode.left
                searchingNode.right = addingNode.right
            }
            else{
                searchingNode.left?.let { search(addingNode,it) }
                searchingNode.right?.let { search(addingNode,it) }
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


    fun searchPre(treeNode: TreeNode<String>){
        bw.write(treeNode.data)
        treeNode.left?.let { searchPre(it) }
        treeNode.right?.let { searchPre(it) }
    }
    fun searchIn(treeNode: TreeNode<String>){
        treeNode.left?.let { searchIn(it) }
        bw.write(treeNode.data)
        treeNode.right?.let { searchIn(it) }
    }
    fun searchPost(treeNode: TreeNode<String>){
        treeNode.left?.let { searchPost(it) }
        treeNode.right?.let { searchPost(it) }
        bw.write(treeNode.data)
    }

    tree.root?.let {
        searchPre(it)
        bw.write("\n")
        searchIn(it)
        bw.write("\n")
        searchPost(it)
    }

    bw.flush()
    bw.close()
    close()
}