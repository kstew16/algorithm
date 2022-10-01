import java.io.BufferedReader
import java.io.InputStreamReader


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (all,a,b,c) = readLine().split(" ").map{it.toInt()}
    var count = 0
    fun makeComb(ia:Int, ib:Int, ic:Int, made:Int){
        val left = all - made
        if(left<ia || left<ib || left<ic) return
        if(ia==0 && ib == 0 && ic == 0 && made == all){
            count += 1
            return
        }
        // ia+ib+ic 가 all - made 보다 커야만 가능
        // 또 all-made 보다 ia, ib, ic 가 모두 같거나 작아야 함
        val sum = ia+ib+ic
        val reduceA = (ia - 1 >= 0)
        val reduceB = (ib - 1 >= 0)
        val reduceC = (ic - 1 >= 0)
        if(sum>=left){
            if(reduceA) makeComb(ia-1,ib,ic,made+1)
            if(reduceB) makeComb(ia,ib-1,ic,made+1)
            if(reduceC) makeComb(ia,ib,ic-1,made+1)
        }
        if(sum-1>=left){
            if(reduceA && reduceB) makeComb(ia-1,ib-1,ic,made+1)
            if(reduceA && reduceC) makeComb(ia-1,ib,ic-1,made+1)
            if(reduceB && reduceC) makeComb(ia,ib-1,ic-1,made+1)
        }
        if(sum-2>=left && reduceA && reduceB && reduceC) makeComb(ia-1,ib-1,ic-1,made+1)
    }
    makeComb(a,b,c,0)
    print(count)
}