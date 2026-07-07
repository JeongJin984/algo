package com.algo.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    static int N;
    static int[][] W;
    static final int INF = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        W = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] dp = new int[1 << N][N];

        for (int i = 0; i < (1 << N); i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[1][0] = 0; // 0번 도시에서 시작, 0번만 방문

        for (int mask = 0; mask < (1 << N); mask++) {
            for (int cur = 0; cur < N; cur++) {
                if (dp[mask][cur] == INF) continue;

                for (int next = 0; next < N; next++) {
                    if ((mask & (1 << next)) != 0) continue; // 이미 방문
                    if (W[cur][next] == 0) continue;         // 길 없음

                    int nextMask = mask | (1 << next);
                    dp[nextMask][next] = Math.min(
                        dp[nextMask][next],
                        dp[mask][cur] + W[cur][next]
                    );
                }
            }
        }

        int fullMask = (1 << N) - 1;
        int answer = INF;

        for (int last = 1; last < N; last++) {
            if (W[last][0] == 0) continue; // 시작 도시로 복귀 불가

            answer = Math.min(answer, dp[fullMask][last] + W[last][0]);
        }

        System.out.println(answer);
    }
}