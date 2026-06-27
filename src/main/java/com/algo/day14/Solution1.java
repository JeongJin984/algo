package com.algo.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
부분수열의 합 2
문제

N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.

입력

첫째 줄에 정수의 개수를 나타내는 N과 정수 S가 주어진다.

1≤N≤40
∣S∣≤1,000,000

둘째 줄에 N개의 정수가 빈칸을 사이에 두고 주어진다. 주어지는 정수의 절댓값은 100,000을 넘지 않는다.

출력

첫째 줄에 합이 S가 되는 부분수열의 개수를 출력한다.

예제 입력 1
5 0
-7 -3 -2 5 8
예제 출력 1
1
 */
public class Solution1 {
    static int N, S;
    static int[] A;
    static List<Long> leftSums = new ArrayList<>();
    static List<Long> rightSums = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        A = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int middle = N / 2;

        // 왼쪽 절반의 모든 부분수열 합 생성
        backtrack(0, middle, 0, leftSums);

        // 오른쪽 절반의 모든 부분수열 합 생성
        backtrack(middle, N, 0, rightSums);

        Collections.sort(leftSums);
        Collections.sort(rightSums);


        long answer = countTargetSums();

        // 문제에서는 크기가 양수인 부분수열만 인정한다.
        // target이 0이면 왼쪽과 오른쪽 모두 공집합인 경우가 포함되므로 제거한다.
        if (S == 0) {
            answer--;
        }

        System.out.println(answer);
    }

    private static void backtrack(
        int index,
        int end,
        long sum,
        List<Long> sums
    ) {
        if (index == end) {
            sums.add(sum);
            return;
        }

        // 현재 원소를 선택하지 않는다.
        backtrack(index + 1, end, sum, sums);

        // 현재 원소를 선택한다.
        backtrack(index + 1, end, sum + A[index], sums);
    }


    private static long countTargetSums() {
        int left = 0;
        int right = rightSums.size() - 1;

        long count = 0;

        while (left < leftSums.size() && right >= 0) {
            long leftValue = leftSums.get(left);
            long rightValue = rightSums.get(right);
            long sum = leftValue + rightValue;

            if (sum < S) {
                left++;
            } else if (sum > S) {
                right--;
            } else {
                long leftCount = 0;
                long rightCount = 0;

                while (
                    left < leftSums.size()
                        && leftSums.get(left) == leftValue
                ) {
                    leftCount++;
                    left++;
                }

                while (
                    right >= 0
                        && rightSums.get(right) == rightValue
                ) {
                    rightCount++;
                    right--;
                }

                count += leftCount * rightCount;
            }
        }

        return count;
    }
}
