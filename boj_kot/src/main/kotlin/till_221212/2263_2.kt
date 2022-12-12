import java.util.StringTokenizer
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
// 맞왜틀 했으나 틀린건 너임
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val inOrder = IntArray(n){st.getInt()}
    st = StringTokenizer(readLine())
    val postOrder = IntArray(n){st.getInt()}
    val indexInOrder = IntArray(n+1)
    for(i in 0 until n) indexInOrder[inOrder[i]] = i

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    fun makeTree(inS: Int, inE: Int, postS:Int, postE: Int) {
        if(postS>postE) return
        val inBoundary = indexInOrder[postOrder[postE]]
        val leftSize = inBoundary - inS
        bw.write("${postOrder[postE]} ")
        if(postS<=postS+leftSize-1){
            if(inS==inBoundary-1) bw.write("${inOrder[inS]} ")
            else makeTree(inS, inBoundary - 1,postS, postS+leftSize-1)
        }
        if(postS+leftSize<=postE-1){
            if(inBoundary+1==inE) bw.write("${inOrder[inE]} ")
            else makeTree(inBoundary + 1, inE,postS+leftSize, postE - 1)
        }
    }
    makeTree(0,n-1,0, n-1)
    bw.close()
}