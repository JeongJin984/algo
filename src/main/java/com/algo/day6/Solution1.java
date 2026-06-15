package com.algo.day6;

import java.util.Arrays;

/*
문제

한 외판원이 도시 A에서 출발하여 모든 도시를 정확히 한 번씩 방문한 뒤 다시 A로 돌아오려고 한다.
도시 간 이동 비용은 아래 표와 같다.

	A	B	C	D	E
A	0	10	8	9	7
B	10	0	10	5	6
C	8	10	0	8	9
D	9	5	8	0	6
E	7	6	9	6	0
조건
출발 도시는 A이다.
모든 도시를 정확히 한 번씩 방문해야 한다.
마지막에는 다시 A로 돌아와야 한다.
총 이동 비용이 최소가 되는 경로를 구하시오.
예시(올바른 경로 형식)
A → B → C → D → E → A

단, 위 예시는 형식만 보여주는 것이며 최적 경로는 아니다.

문제:
최소 비용과 그에 해당하는 순회 경로를 구하시오.
 */
public class Solution1 {
    static final int INF = 1_000_000_000;
    static int n = 4;

    static int[][] cost = {
        {0, 10, 15, 20},
        {5, 0, 9, 10},
        {6, 13, 0, 12},
        {8, 8, 9, 0}
    };

    static int[][] dp;

    public static void main(String[] args) {
        dp = new int[n][1 << n];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        int answer = tsp(0, 1);

        System.out.println(answer); // 35
    }

    static int tsp(int city, int visited) {
        if (visited == (1 << n) - 1) {
            return cost[city][0];
        }

        if (dp[city][visited] != -1) {
            return dp[city][visited];
        }

        dp[city][visited] = INF;

        for (int next = 0; next < n; next++) {
            if ((visited & (1 << next)) != 0) {
                continue;
            }

            int nextVisited = visited | (1 << next);
            int totalCost = cost[city][next] + tsp(next, nextVisited);

            dp[city][visited] = Math.min(dp[city][visited], totalCost);
        }

        return dp[city][visited];
    }
}