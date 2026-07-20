package com.algo.day27;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
성냥개비
문제

성냥개비는 숫자를 나타내기에 아주 이상적인 도구이다. 보통 실간수를 성냥개비로 표현하는 방법은 다음과 같다.

0 : 6개
1 : 2개
2 : 5개
3 : 5개
4 : 4개
5 : 5개
6 : 6개
7 : 3개
8 : 7개
9 : 6개

성냥개비의 개수가 주어졌을 때, 성냥개비를 모두 사용해서 만들 수 있는 가장 작은 수와 큰 수를 찾는 프로그램을 작성하시오.

입력

첫째 줄에 테스트 케이스의 개수가 주어진다. 테스트 케이스는 최대 100개이다.

각 테스트 케이스는 한 줄로 이루어져 있고, 성냥개비의 개수 n이 주어진다. (2≤n≤100)

출력

각 테스트 케이스에 대해서 입력으로 주어진 성냥개비를 모두 사용해서 만들 수 있는 가장 작은 수와 가장 큰 수를 출력한다.

두 숫자는 모두 양수이어야 하고, 숫자는 0으로 시작할 수 없다.

예제 입력 1
4
3
6
7
15
예제 출력 1
7 7
6 111
8 711
108 7111111
 */

public class Solution2 {

    // 숫자 0~9를 만드는 데 필요한 성냥개비 개수
    static final int[] COST = {
        6, // 0
        2, // 1
        5, // 2
        5, // 3
        4, // 4
        5, // 5
        6, // 6
        3, // 7
        7, // 8
        6  // 9
    };

    static final long INF = Long.MAX_VALUE / 10;
    static final long[] minDp = new long[101];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 모든 n에 대한 최솟값을 한 번만 계산
        makeMinDp();

        int testCase = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();

        for (int tc = 0; tc < testCase; tc++) {
            int n = Integer.parseInt(br.readLine());

            String minValue = String.valueOf(minDp[n]);
            String maxValue = makeMaxValue(n);

            answer.append(minValue)
                .append(' ')
                .append(maxValue)
                .append('\n');
        }

        System.out.print(answer);
    }

    /**
     * 성냥개비 i개로 만들 수 있는 최솟값 계산
     */
    static void makeMinDp() {
        Arrays.fill(minDp, INF);

        // 성냥개비를 하나도 쓰지 않은 상태
        minDp[0] = 0;

        for (int matches = 2; matches <= 100; matches++) {
            for (int digit = 0; digit <= 9; digit++) {
                int digitCost = COST[digit];
                int previousMatches = matches - digitCost;

                if (previousMatches < 0) {
                    continue;
                }

                if (minDp[previousMatches] == INF) {
                    continue;
                }

                // 숫자는 0으로 시작할 수 없음
                if (previousMatches == 0 && digit == 0) {
                    continue;
                }

                long candidate = minDp[previousMatches] * 10 + digit;

                minDp[matches] = Math.min(
                    minDp[matches],
                    candidate
                );
            }
        }
    }

    /**
     * 성냥개비 n개로 만들 수 있는 최댓값 생성
     */
    static String makeMaxValue(int n) {
        StringBuilder result = new StringBuilder();

        if (n % 2 == 0) {
            // 짝수면 성냥 2개짜리 숫자 1을 최대한 많이 사용
            for (int i = 0; i < n / 2; i++) {
                result.append('1');
            }
        } else {
            // 홀수면 맨 앞에 성냥 3개짜리 숫자 7을 배치
            result.append('7');

            for (int i = 0; i < (n - 3) / 2; i++) {
                result.append('1');
            }
        }

        return result.toString();
    }
}