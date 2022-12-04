import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    class Block(var merged:Boolean, var weight:Int){
        fun merged(target:Block):Block {
           return Block(true,this.weight+target.weight)
        }
    }
    val emptyBlock = Block(false,0)
    val n = readLine().toInt()
    var st:StringTokenizer
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var maxRecord = 0
    val originalField = Array(n){
        st = StringTokenizer(readLine())
        Array(n){
            Block(false,st.getInt().also { maxRecord = maxRecord.coerceAtLeast(it) })
        }
    }
    // direction : 0 up 1 right 2 down 3 left
    fun applyGravity(field:Array<Array<Block>>,direction:Int){
        val ground = Array(n){LinkedList<Block>()}
        when(direction){
            0->{
                for(j in 0 until n){
                    for(i in 0 until n){
                        val cur = field[j][i]
                        if(cur != emptyBlock){
                            if(ground[i].isEmpty()) ground[i].add(cur)
                            //else ground[i].add(ground[i].pollLast().merged(cur).also { maxRecord = it.weight.coerceAtLeast(maxRecord) })
                            else{
                                val lastBlock = ground[i].pollLast()
                                if(lastBlock.merged || lastBlock.weight!=cur.weight) ground[i].add(cur)
                                else {
                                    val mergedBlock = cur.merged(lastBlock)
                                    maxRecord = maxRecord.coerceAtLeast(mergedBlock.weight)
                                    ground[i].add(mergedBlock)
                                }
                            }
                        }
                    }
                }
                for(i in 0 until n){
                    val size = ground[i].size
                    repeat(n-size){
                        field[it+size][i] = emptyBlock
                    }
                    repeat(size){
                        field[it][i] = ground[i].pollFirst().apply { this.merged = false }
                    }
                }
            }
            1->{
                for(j in 0 until n){
                    for(i in n-1 downTo 0){
                        val cur = field[j][i]
                        if(cur != emptyBlock){
                            if(ground[j].isEmpty()) ground[j].add(cur)
                            else{
                                val lastBlock = ground[j].pollLast()
                                if(lastBlock.merged || lastBlock.weight!=cur.weight) ground[j].add(cur)
                                else {
                                    val mergedBlock = cur.merged(lastBlock)
                                    maxRecord = maxRecord.coerceAtLeast(mergedBlock.weight)
                                    ground[j].add(mergedBlock)
                                }
                            }
                        }
                    }
                }
                for(j in 0 until n){
                    val size = ground[j].size
                    repeat(n-size){
                        field[j][it] = emptyBlock
                    }
                    repeat(size){
                        field[j][n-it-1] = ground[j].pollFirst().apply { this.merged = false }
                    }
                }
            }
            2->{
                for(j in n-1 downTo 0){
                    for(i in 0 until n){
                        val cur = field[j][i]
                        if(cur != emptyBlock){
                            if(ground[i].isEmpty()) ground[i].add(cur)
                            else{
                                val lastBlock = ground[i].pollLast()
                                if(lastBlock.merged || lastBlock.weight!=cur.weight) ground[i].add(cur)
                                else {
                                    val mergedBlock = cur.merged(lastBlock)
                                    maxRecord = maxRecord.coerceAtLeast(mergedBlock.weight)
                                    ground[i].add(mergedBlock)
                                }
                            }
                        }
                    }
                }
                for(i in 0 until n){
                    val size = ground[i].size
                    repeat(n-size){
                        field[it][i] = emptyBlock
                    }
                    repeat(size){
                        field[n-it-1][i] = ground[i].pollFirst().apply { this.merged = false }
                    }
                }
            }
            else->{
                for(j in 0 until n){
                    for(i in 0 until n){
                        val cur = field[j][i]
                        if(cur != emptyBlock){
                            if(ground[j].isEmpty()) ground[j].add(cur)
                            else{
                                val lastBlock = ground[j].pollLast()
                                if(lastBlock.merged || lastBlock.weight!=cur.weight) ground[j].add(cur)
                                else {
                                    val mergedBlock = cur.merged(lastBlock)
                                    maxRecord = maxRecord.coerceAtLeast(mergedBlock.weight)
                                    ground[j].add(mergedBlock)
                                }
                            }
                        }
                    }
                }
                for(j in 0 until n){
                    val size = ground[j].size
                    repeat(n-size){
                        field[j][size+it] = emptyBlock
                    }
                    repeat(size){
                        field[j][it] = ground[j].pollFirst().apply { this.merged = false }
                    }
                }
            }
        }
    }
    val command = IntArray(5)
    fun dfs(depth:Int){
        if(depth==5){
            val field = Array(n){y-> Array(n){x-> originalField[y][x]} }
            for(i in 0..4) applyGravity(field,command[i])
        }else {
            for (i in 0..3) {
                command[depth] = i
                dfs(depth+1)
            }
        }
    }
    dfs(0)
    print(maxRecord)
}
