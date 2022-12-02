import java.io.BufferedReader
import java.io.InputStreamReader
// 첫 행동그룹에 의해 (행동이 여러 가지일 수도 있음) 후에 해야 하는 작업이 정해지는 경우가 있음
// 첫 행동그룹이 따져볼만한 수라면 그리디를 사용해서 브루트포스를 진행할 수 있겠다!
// 이 문제의 경우 첫 줄의 전구를 누르는 경우가 나머지 줄들에서 전구를 눌러야하는 방법을 다 결정했음

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    infix fun Int.on(i:Int):Int = this or (1 shl i)
    infix fun Int.off(i:Int):Int = this and (1 shl i).inv()
    infix fun Int.chk(i:Int):Boolean = (this shr i) and 1 == 1
    infix fun Int.toggle(i:Int):Int = if(this chk i) this off i else this on i

    val originalField = IntArray(10){
        var line = 0
        readLine().forEachIndexed { i,char ->
            if(char=='O') line = line on i
        }
        line
    }

    fun IntArray.press(y:Int,x:Int){
        if(x-1>=0) this[y] = this[y] toggle (x-1)
        this[y] = this[y] toggle(x)
        if(x+1<10) this[y] = this[y] toggle (x+1)
        if(y-1>=0) this[y-1] = this[y-1] toggle x
        if(y+1<10) this[y+1] = this[y+1] toggle x
    }

    var minPressed = Int.MAX_VALUE
    l1@for(firstLineCommand in 0 until 1024){
        var pressCount = 0
        val field = IntArray(10){originalField[it]}
        // 아무것도 안 하는 것부터 모든 걸 다 건드려보는것까지
        for(i in 0 until 10) if(firstLineCommand chk(i)) {
            field.press(0,i)
            if(++pressCount>=minPressed) continue@l1
        }
        for(y in 1..9)for(x in 0..9)if(field[y-1] chk x) {
            field.press(y,x)
            if(++pressCount>=minPressed) continue@l1
        }
        var left = false
        var i = 0
        while(!left && i<10){
            left = field[9] chk i
            i++
        }
        if(!left) minPressed = pressCount.coerceAtMost(minPressed)
    }
    if(minPressed == Int.MAX_VALUE) print(-1) else print(minPressed)
}