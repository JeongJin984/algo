package com.algo.day9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
문제: 연속 선택 제한 최대 점수

길이가 N인 수열이 있다. 각 위치 i에는 점수 A[i]가 있다.

왼쪽부터 오른쪽으로 진행하면서 각 위치를 선택하거나 건너뛸 수 있다.

단, 다음 조건이 있다:

연속으로 3개 이상 선택할 수 없다.

얻을 수 있는 점수의 최댓값을 구하라.

입력
N
A1 A2 A3 ... AN
제한
1 ≤ N ≤ 100,000
1 ≤ Ai ≤ 10,000
출력

최대 점수 출력

예제 입력 1
6
6 10 13 9 8 1
예제 출력 1
33
설명

가능한 선택:

6 + 10 + (skip 13) + 9 + 8 + (skip 1) = 33
 */
public class Solution1 {
    static int N;
    static int[] A;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        long[] score = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            score[i] = Long.parseLong(st.nextToken());
        }

        if (n == 1) {
            System.out.println(score[1]);
            return;
        }

        long[] dp = new long[n + 1];

        dp[0] = 0;
        dp[1] = score[1];
        dp[2] = score[1] + score[2];

        for (int i = 3; i <= n; i++) {
            long skipCurrent = dp[i - 1];

            long selectCurrentOnly =
                dp[i - 2] + score[i];

            long selectPreviousAndCurrent =
                dp[i - 3] + score[i - 1] + score[i];

            dp[i] = Math.max(
                skipCurrent,
                Math.max(selectCurrentOnly, selectPreviousAndCurrent)
            );
        }

        System.out.println(dp[n]);
    }
}
