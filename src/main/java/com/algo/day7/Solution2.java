package com.algo.day7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
문제: 합이 K 이하인 가장 긴 연속 부분 수열

정수 배열 arr와 정수 K가 주어진다.
배열에서 연속된 부분 수열 중 원소들의 합이 K 이하인 것들 중에서, 길이가 가장 긴 부분 수열의 길이를 구하라.

단, 배열의 모든 원소는 양의 정수이다.

입력 형식
N K
arr[0] arr[1] ... arr[N-1]
출력 형식
합이 K 이하인 가장 긴 연속 부분 수열의 길이
제한 조건
1 ≤ N ≤ 100,000
1 ≤ K ≤ 1,000,000,000
1 ≤ arr[i] ≤ 10,000
예제 입력 1
8 10
1 2 3 4 5 1 1 2
예제 출력 1
4
 */
public class Solution2 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int left = 0;
        long sum = 0;
        int answer = 0;

        for (int right = 0; right < N; right++) {
            sum += arr[right];

            while (sum > K) {
                sum -= arr[left];
                left++;
            }

            int length = right - left + 1;
            answer = Math.max(answer, length);
        }

        System.out.println(answer);
    }
}
