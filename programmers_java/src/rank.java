import java.util.ArrayList;

public class rank {
    class Solution{
        // 변수 클래스 내 공유는 이렇게
        // 이중배열 선언시 초기화 까먹지 않도록
        ArrayList<Integer>[] parent;
        ArrayList<Integer>[] child;
        int visitCount;
        Boolean[] visited;

        public int solution(int n, int[][] results) {
            int answer = 0;
            ArrayList<Integer>[] parent = new ArrayList[n+1];
            ArrayList<Integer>[] child = new ArrayList[n+1];
            for(int i=1;i<=n;i++){
                parent[i] = new ArrayList<Integer>();
                child[i] = new ArrayList<Integer>();
            }
            for(int i=0;i< results.length;i++){
                child[results[i][0]].add(results[i][1]);
                parent[results[i][1]].add(results[i][0]);
            }
            visited = new Boolean[n+1];
            for(int i = 1;i<=n;i++){
                for(int j=1;j<=n;j++) visited[j] = false;
                visitCount = -1;
                dfs(true,i);
                dfs(false,i);
                if(visitCount==n) answer++;
            }
            return answer;
        }
        public void dfs(Boolean isUp,int visiting){
            visitCount++;
            visited[visiting] = true;
            ArrayList<Integer> map;
            if(isUp) map = parent[visiting];
            else map = child[visiting];
            for(int i=0;i<map.size();i++){
                int next = map.get(i);
                if(!visited[next]) dfs(isUp,next);
            }
        }
    }
}