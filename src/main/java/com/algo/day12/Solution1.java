package com.algo.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
K번째 수
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	128 MB	9515	3344	2451	38.237%
문제

세준이는 크기가 N×N인 배열 A를 만들었다. 배열에 들어있는 수 A[i][j] = i×j 이다. 이 수를 일차원 배열 B에 넣으면 B의 크기는 N×N이 된다. B를 오름차순 정렬했을 때, B[k]를 구해보자.

배열 A와 B의 인덱스는 1부터 시작한다.

입력

첫째 줄에 배열의 크기 N이 주어진다. N은 10⁵보다 작거나 같은 자연수이다. 둘째 줄에 k가 주어진다. k는 min(10⁹, N²)보다 작거나 같은 자연수이다.

출력

B[k]를 출력한다.

예제 입력 1
3
7
예제 출력 1
6
 */
public class Solution1 {
    static int N;
    static long K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());

        long left = 1;
        long right = N*N;

        // 그리고 그 값이 count(x) >= K를 만족하는 최초의 값, 즉 B[K]입니다. 즉 lower_bound
        while (left < right) {
            long mid = left + (right - left) / 2;

            long count = getCount(mid); // mid 이하인 수들의 개수

            if (count < K) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        System.out.println(left);
    }

    // 배열에서 value 이하인 숫자의 개수
    private static long getCount(long value) {
        long count = 0;

        for (int i = 1; i <= N; i++) {
            count += Math.min(N, value / i);
        }

        return count;
    }
}
