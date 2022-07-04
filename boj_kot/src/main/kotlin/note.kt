fun main(){
    val br = System.`in`.bufferedReader()
    val bw = System.`out`.bufferedWriter()
    do{
        val n = br.readLine().toInt()
        val input = br.readLine().split(" ").map { it.toInt() } as ArrayList<Int>

        var x = n+1
        for (i in n-2 downTo 0){
            if(input[i]>input[i+1]){
                x = i
                break
            }
        }
        // for 문 다 돌았는데도  x가 n+1이면 주어진 순열은 첫 순열, 이전 순열 없으므로 -1 출력
        if(x == n+1) {
            bw.write("-1")
            bw.write("\n")
        }
        else{
            // 이제 x 오른쪽에서 자신보다 작은 수 중 가장 큰 수 모셔오기
            var successor = 0
            var y = 0
            for(i in x+1 until n){
                if(input[x] > input[i] && input[i] > successor ){
                    successor = input[i]
                    y = i
                }
            }
            // 오른쪽에 자신보다 작은 수를 찾으면 successor 는 0이 아님
            // 찾지 못했으면 그대로 x 오른쪽을 역순정렬
            if(successor != 0){
                input[y] = input[x]
                input[x] = successor
            }
            // x 오른쪽의 서브리스트를 역순 정렬하여 출력
            var ans = input.subList(0,x+1) + (input.subList(x+1,input.size)).sortedDescending()
            bw.write(ans.joinToString(" "))
            bw.write("\n")
        }
    }while(n!=-1)
    bw.close()
    br.close()
}