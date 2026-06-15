package com.algo.day6;

/*
슬라이딩 윈도우 문제

정수 배열 arr와 정수 k가 주어진다.
길이가 k인 연속 부분 배열 중에서 합이 가장 큰 값을 구하시오.

입력 예시
arr = [2, 1, 5, 1, 3, 2]
k = 3
출력 예시
9
설명

길이 3인 연속 부분 배열들은 다음과 같다.

[2, 1, 5] = 8
[1, 5, 1] = 7
[5, 1, 3] = 9
[1, 3, 2] = 6

따라서 최대 합은 9이다.

요구사항

다음 클래스를 완성하시오.
 */
public class Solution2 {
    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 1, 3, 2};
        int k = 3;

        int answer = maxSum(arr, k);

        System.out.println(answer); // 9
    }

    static int maxSum(int[] arr, int k) {
        int windowSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int maxSum = windowSum;

        for (int right = k; right < arr.length; right++) {
            windowSum += arr[right];
            windowSum -= arr[right - k];

            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }
}