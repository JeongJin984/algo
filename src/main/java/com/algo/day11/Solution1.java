package com.algo.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
2098번 — 외판원 순회
문제

1번부터 N번까지 도시가 있으며, 도시 사이를 이동할 때 비용이 발생한다. 일부 도시 사이에는 이동 가능한 길이 없을 수도 있다.

한 도시에서 출발하여 다음 조건을 만족하는 순회 경로를 구한다.

모든 도시를 정확히 한 번씩 방문한다.
모든 도시를 방문한 후 출발한 도시로 돌아온다.
방문했던 도시는 다시 방문할 수 없다. 단, 마지막에 출발 도시로 돌아가는 것은 허용된다.

가능한 순회 경로 중 이동 비용의 합이 최소가 되는 값을 구해야 한다.

입력

첫째 줄에 도시의 수 N이 주어진다.

2 ≤ N ≤ 16

다음 N개의 줄에 비용 행렬 W가 주어진다.

W[i][j]: 도시 i에서 도시 j로 이동하는 비용
이동할 수 없는 경우 W[i][j] = 0
그 외의 비용은 1,000,000 이하의 양의 정수
항상 모든 도시를 순회할 수 있는 입력만 주어진다.
출력

외판원이 모든 도시를 순회하고 출발 도시로 돌아오는 데 필요한 최소 비용을 출력한다.

예제 입력
4
0 10 15 20
5 0 9 10
6 13 0 12
8 8 9 0
예제 출력
35
 */
public class Solution1 {
    static final int INF = 1_000_000_000;

    static int N;
    static int[][] W;
    static int[][] dp;
    static int allVisited;

    static int dfs(int current, int visited) {
        // 모든 도시를 방문했다면 출발 도시로 복귀
        if (visited == allVisited) {
            if (W[current][0] == 0) return INF;
            return W[current][0];
        }

        // 이미 계산한 상태
        if (dp[current][visited] != -1) return dp[current][visited];

        int minCost = INF;

        for (int next = 0; next < N; next++) {
            // 길이 없는 경우
            if (W[current][next] == 0) continue;

            // 이미 방문한 도시
            if ((visited & (1 << next)) != 0) continue;

            int nextCost = dfs(next, visited | (1 << next));

            if (nextCost == INF) continue;

            minCost = Math.min(minCost, W[current][next] + nextCost);
        }

        return dp[current][visited] = minCost;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        W = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N][(1 << N)];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(dfs(0, 1));
    }
}
