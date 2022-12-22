import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
// 스택을 이용한 풀이는 상상도 못 했음.
// 숫자를 크게 하려면 앞자리부터 크게 해야 함. 따라서 앞자리를 작게 만드는 숫자들부터 지워 간다!
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var (_,haveToRemove) = readLine().split(" ").map{it.toInt()}
    val input = readLine().toString()
    // sb 를 스택으로 사용해보자
    val sb = StringBuilder("")
    for(i in input.indices){
        // stack 상단의 현재 문자보다 작은 문자들 다 삭제
        while(sb.isNotEmpty() && haveToRemove>0 && sb.last()<input[i]){
            sb.setLength(sb.length-1)
            haveToRemove--
        }
        // 더이상 삭제할 필요 없다면 나머지 문자를 다 출력해주면 됨
        if(haveToRemove==0){
            sb.append(input.substring(i))
            break
        }
        // stack 이 비어있다면 일단 넣어줌
        sb.append(input[i])
    }
    // 다 돌았는데 아직 삭제할 게 남은 경우 다 스택 뒤에 몰려있음
    print(sb.toString().substring(0,sb.length-haveToRemove))
}