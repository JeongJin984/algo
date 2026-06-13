package com.algo.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
문제: 회식 자리 배치

회사에서 회식을 한다.
사람들은 긴 테이블에 일렬로 앉아야 한다.

각 직원은 성격이 달라서, 어떤 직원 옆에 앉으면 분위기가 좋아지거나 나빠진다.
또한 일부 직원들은 서로 사이가 좋지 않아서 반드시 떨어져 앉아야 한다.

당신은 회식 분위기 점수를 최대로 만드는 자리 배치를 구해야 한다.

입력

첫째 줄에 직원 수 N이 주어진다.

둘째 줄부터 N개의 줄에 걸쳐 분위기 점수표가 주어진다.
i번 직원이 j번 직원의 바로 왼쪽에 앉았을 때 얻는 점수 A[i][j]가 주어진다.

그다음 줄에 사이가 좋지 않은 직원 쌍의 수 M이 주어진다.

다음 M개의 줄에는 서로 붙어 앉으면 안 되는 직원 쌍 u v가 주어진다.

출력

가능한 자리 배치 중 분위기 점수의 최댓값을 출력한다.
가능한 배치가 없다면 -1을 출력한다.

조건
2 ≤ N ≤ 18
-1000 ≤ A[i][j] ≤ 1000
A[i][i] = 0
0 ≤ M ≤ N(N-1)/2
1 ≤ u, v ≤ N
u ≠ v
예시 입력
4
0 5 -2 4
3 0 7 -1
6 -3 0 2
4 1 8 0
2
1 3
2 4
예시 출력
16
설명

예를 들어 다음과 같이 앉힐 수 있다.

3 2 1 4

점수는 다음과 같다.

A[3][2] + A[2][1] + A[1][4]
= -3 + 3 + 4
= 4

하지만 최적 배치는 더 높은 점수를 만들 수 있다.
 */
public class Solution2 {
    static final int NEG_INF = -1_000_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] A = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int M = Integer.parseInt(br.readLine());

        boolean[][] banned = new boolean[N][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;

            banned[u][v] = true;
            banned[v][u] = true;
        }

        int full = (1 << N) - 1;

        int[][] dp = new int[1 << N][N];

        for (int i = 0; i < (1 << N); i++) {
            Arrays.fill(dp[i], NEG_INF);
        }

        for (int i = 0; i < N; i++) {
            dp[1 << i][i] = 0;
        }

        for (int mask = 0; mask <= full; mask++) {
            for (int last = 0; last < N; last++) {
                if (dp[mask][last] == NEG_INF) continue;

                for (int next = 0; next < N; next++) {
                    if ((mask & (1 << next)) != 0) continue;
                    if (banned[last][next]) continue;

                    int nextMask = mask | (1 << next);

                    dp[nextMask][next] = Math.max(
                        dp[nextMask][next],
                        dp[mask][last] + A[last][next]
                    );
                }
            }
        }

        int answer = NEG_INF;

        for (int last = 0; last < N; last++) {
            answer = Math.max(answer, dp[full][last]);
        }

        System.out.println(answer == NEG_INF ? -1 : answer);
    }
}
