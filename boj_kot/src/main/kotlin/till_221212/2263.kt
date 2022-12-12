import java.util.StringTokenizer
import java.io.BufferedReader
import java.io.InputStreamReader
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val inOrder = IntArray(n){st.getInt()}
    st = StringTokenizer(readLine())
    val postOrder = IntArray(n){st.getInt()}
    class TreeNode(val index:Int, var left:TreeNode?, var right:TreeNode?)

    fun makeTree(inS: Int, inE: Int, postE: Int): TreeNode? {
        val inBoundary = inOrder.indexOf(postOrder[postE])
        return TreeNode(postOrder[postE],
            if(inBoundary-1<0||inS>inBoundary-1) null else if(inS==inBoundary-1) TreeNode(inOrder[inS], null, null)
            else makeTree(inS, inBoundary - 1, inBoundary - 1),
            if(postE-1<0 || inBoundary+1>inE) null else if(inBoundary+1==inE) TreeNode(inOrder[inE],null,null)
            else makeTree(inBoundary + 1, inE, postE - 1)
        )
    }
    fun printPreOrder(sb:StringBuilder,node:TreeNode):StringBuilder{
        sb.append("${node.index} ")
        node.left?.let{printPreOrder(sb,it)}
        node.right?.let{printPreOrder(sb,it)}
        return sb
    }

    val rootNode = makeTree(0,n-1,n-1)
    val sb = StringBuilder("")
    print(printPreOrder(sb,rootNode!!).trim())

}