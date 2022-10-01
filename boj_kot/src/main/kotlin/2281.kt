/*
2 * (n-1 줄 남은칸) * (n-1 줄 마지막 + 1)
- 2*(n 줄 남은칸) * (n-1 줄 마지막 + 1)
+ 2 * (n-1 줄 마지막 + 1)^2
 */
import java.io.BufferedReader
import java.io.InputStreamReader


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,lengthLimit) = readLine().split(" ").map{it.toInt()}
    val nameLength = IntArray(n){readLine().toInt()}
    var lastLeft = 0
    var lastNameLength = lengthLimit
    var curWaste = 0
    var curLeft = lengthLimit
    var lastUsed = -1
    var i = 0
    while(i in 0 until n){
        val curLength = nameLength[i]
        if(curLeft > curLength){
            i+=1
            //lastLeft = curLeft
            lastUsed = curLength
            curLeft -= if(curLeft == lengthLimit) curLength
            else (curLength+1)
        }else if(lastNameLength < curLeft){
            // 줄바꿈 필요, 저번 단어 끌어쓸 수 있을 때
            val differ = 2*(lastLeft*(lastNameLength+1) - curLeft*(lastNameLength+1)+(lastNameLength+1)*(lastNameLength+1))
            if(differ<0){
                lastLeft = curLeft - (lastNameLength+1)
                curWaste +=  curLeft*curLeft+differ
                lastNameLength = lastUsed
            }
            else{
                lastLeft = curLeft
                curWaste +=  curLeft*curLeft
                lastNameLength = lastUsed
            }
            curLeft = lengthLimit
        }else{
            curWaste +=  curLeft*curLeft
            lastLeft = curLeft
            curLeft = lengthLimit
            lastNameLength = lastUsed
        }
    }
    print(curWaste)
}