import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer
import kotlin.math.pow
// 한번에 맞긴 했는데 빠른 게 있더라
// 매번 계산하지 말고 보드를 들고다니자~~
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    class Block(var merged:Boolean, var weight:Int){
        fun merged(other:Block):Block{
            return Block(true,this.weight+other.weight)
        }
    }
    val emptyBlock = Block(false,0)
    var maxWeight = 0
    val originalField = Array(n){
        val st = StringTokenizer(readLine())
        Array(n){
            Block(false, st.nextToken().toInt()).also { maxWeight = it.weight.coerceAtLeast(maxWeight) }
        }
    }


    fun setGravity(field:Array<Array<Block>>,direction:Int,maxBeforeMerge:Int):Int{
        val ground = Array(n){LinkedList<Block>()}
        var mergedMax = 0
        when(direction){
            // up
            0->{
                for(y in 0 until n){
                    for(x in 0 until n){
                        val cur = field[y][x]
                        if(cur.weight == 0) continue
                        if(ground[x].isEmpty()) ground[x].add(cur)
                        else{
                            val top = ground[x].peekLast()
                            if(!top.merged && top.weight == cur.weight){
                                ground[x].add(cur.merged(ground[x].pollLast()).also { mergedMax = it.weight.coerceAtLeast(mergedMax) })
                            } else ground[x].add(cur)
                        }
                    }
                }
                // 위에서부터 다시 배치
                for(y in 0 until n){
                    for(x in 0 until n){
                        field[y][x] = if(ground[x].isNotEmpty()) ground[x].pollFirst().also { it.merged = false } else emptyBlock
                    }
                }
            }
            // down
            1->{
                for(y in n-1 downTo 0){
                    for(x in 0 until n){
                        val cur = field[y][x]
                        if(cur.weight == 0) continue
                        if(ground[x].isEmpty()) ground[x].add(cur)
                        else{
                            val top = ground[x].peekLast()
                            if(!top.merged && top.weight == cur.weight){
                                ground[x].add(cur.merged(ground[x].pollLast()).also { mergedMax = it.weight.coerceAtLeast(mergedMax) })
                            }else ground[x].add(cur)
                        }
                    }
                }
                // 아래서부터 배치
                for(y in n-1 downTo 0){
                    for(x in 0 until n){
                        field[y][x] = if(ground[x].isNotEmpty()) ground[x].pollFirst().also { it.merged = false } else emptyBlock
                    }
                }
            }
            // right
            2->{
                for(y in 0 until n){
                    for(x in n-1 downTo 0){
                        val cur = field[y][x]
                        if(cur.weight == 0) continue
                        if(ground[y].isEmpty()) ground[y].add(cur)
                        else{
                            val top = ground[y].peekLast()
                            if(!top.merged && top.weight == cur.weight){
                                ground[y].add(cur.merged(ground[y].pollLast()).also { mergedMax = it.weight.coerceAtLeast(mergedMax) })
                            }else ground[y].add(cur)
                        }
                    }
                }
                // 오른쪽부터 배치
                for(y in 0 until n){
                    for(x in n-1 downTo 0){
                        field[y][x] = if(ground[y].isNotEmpty()) ground[y].pollFirst().also { it.merged = false } else emptyBlock
                    }
                }
            }
            // left
            else->{
                for(y in 0 until n){
                    for(x in 0 until n){
                        val cur = field[y][x]
                        if(cur.weight == 0) continue
                        if(ground[y].isEmpty()) ground[y].add(cur)
                        else{
                            val top = ground[y].peekLast()
                            if(!top.merged && top.weight == cur.weight){
                                ground[y].add(cur.merged(ground[y].pollLast()).also { mergedMax = it.weight.coerceAtLeast(mergedMax) })
                            }else ground[y].add(cur)
                        }
                    }
                }
                // 왼쪽부터 배치
                for(y in 0 until n){
                    for(x in 0 until n){
                        field[y][x] = if(ground[y].isNotEmpty()) ground[y].pollFirst().also { it.merged = false } else emptyBlock
                    }
                }
            }
        }

        return kotlin.math.max(mergedMax,maxBeforeMerge)
    }

    fun dfs(depth:Int,field:Array<Array<Block>>){
        if(depth==5) return
        else{
            for(i in 0..3){
                var currentMax = 0
                val clone = Array(n){y->
                    Array(n){x->
                        field[y][x].also { currentMax = it.weight.coerceAtLeast(currentMax) }
                    }
                }
                // 가능성이 있을 시에만 추가조작
                if(currentMax.toDouble()*2.0.pow(5-depth)>maxWeight) {
                    currentMax = setGravity(clone,i,currentMax)
                    maxWeight = currentMax.coerceAtLeast(maxWeight)
                    dfs(depth+1,clone)
                }
            }
        }
    }
    dfs(0,originalField)
    print(maxWeight)
}