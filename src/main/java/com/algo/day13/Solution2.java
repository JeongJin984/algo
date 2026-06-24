package com.algo.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
계단 수
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	9980	5036	3823	50.065%
문제

45656이란 수를 보자.

이 수는 인접한 모든 자리의 차이가 1이다. 이런 수를 계단 수라고 한다.

N이 주어질 때, 길이가 N이면서 0부터 9까지 숫자가 모두 등장하는 계단 수가 총 몇 개 있는지 구하는 프로그램을 작성하시오. 0으로 시작하는 수는 계단수가 아니다.
 */
public class Solution2 {
    private static final int DIGIT_COUNT = 10;
    private static final int FULL_MASK = (1 << DIGIT_COUNT) - 1;
    private static final long MOD = 1_000_000_000L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        int n = Integer.parseInt(br.readLine());

        // dp[length][마지막 숫자][등장한 숫자 비트마스크]
        long[][][] dp = new long[n + 1][DIGIT_COUNT][1 << DIGIT_COUNT];

        // 0으로 시작할 수 없으므로 1부터 9까지만 초기화
        for (int digit = 1; digit <= 9; digit++) {
            dp[1][digit][1 << digit] = 1;
        }

        for (int length = 1; length < n; length++) {
            for (int lastDigit = 0; lastDigit <= 9; lastDigit++) {
                for (int mask = 0; mask <= FULL_MASK; mask++) {
                    long currentCount = dp[length][lastDigit][mask];

                    if (currentCount == 0) {
                        continue;
                    }

                    // 다음 숫자가 현재 숫자보다 1 작은 경우
                    if (lastDigit > 0) {
                        int nextDigit = lastDigit - 1;
                        int nextMask = mask | (1 << nextDigit);

                        dp[length + 1][nextDigit][nextMask] =
                            (dp[length + 1][nextDigit][nextMask]
                                + currentCount) % MOD;
                    }

                    // 다음 숫자가 현재 숫자보다 1 큰 경우
                    if (lastDigit < 9) {
                        int nextDigit = lastDigit + 1;
                        int nextMask = mask | (1 << nextDigit);

                        dp[length + 1][nextDigit][nextMask] =
                            (dp[length + 1][nextDigit][nextMask]
                                + currentCount) % MOD;
                    }
                }
            }
        }

        long answer = 0;

        for (int lastDigit = 0; lastDigit <= 9; lastDigit++) {
            answer = (answer + dp[n][lastDigit][FULL_MASK]) % MOD;
        }

        System.out.println(answer);
    }
}
