package com.algo.day23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
합이 0인 네 정수
문제

정수로 이루어진 크기가 같은 배열 A, B, C, D가 있다.

다음 조건을 만족하는 정수 쌍 (a, b, c, d)의 개수를 구하는 프로그램을 작성하시오.

a, b, c, d는 0 ≤ a, b, c, d < n을 만족한다.
A[a] + B[b] + C[c] + D[d] = 0이다.
입력

첫째 줄에 배열의 크기 n이 주어진다.

1 ≤ n ≤ 4,000

다음 n개의 줄에는 각각 네 개의 정수 A[i], B[i], C[i], D[i]가 공백으로 구분되어 주어진다.

각 배열에 들어 있는 정수의 절댓값은 최대 2²⁸이다.

출력

합이 0이 되는 쌍 (a, b, c, d)의 개수를 출력한다.

예제 입력 1
6
-45 22 42 -16
-41 -27 56 30
-36 53 -37 77
-36 30 -75 -46
26 -38 -10 62
-32 -54 -6 45
예제 출력 1
5
예제 입력 2
2
1 -1 0 0
2 -2 0 0
예제 출력 2
8
예제 2 설명

A[a] + B[b]가 항상 0이고, C[c] + D[d]도 항상 0이다.

각 인덱스는 두 가지씩 선택할 수 있으므로 가능한 조합은 다음과 같다.

2 × 2 × 2 × 2 = 16

다만 위 입력에서는 A[a] + B[b] = 0인 경우가 (0, 0), (1, 1) 두 개이므로, 전체 경우의 수는 다음과 같다.

2 × 2 × 2 = 8
 */
public class Solution1 {

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();

        int[] A = new int[n];
        int[] B = new int[n];
        int[] C = new int[n];
        int[] D = new int[n];

        for (int i = 0; i < n; i++) {
            A[i] = fs.nextInt();
            B[i] = fs.nextInt();
            C[i] = fs.nextInt();
            D[i] = fs.nextInt();
        }

        int size = n * n;

        int[] AB = new int[size];
        int[] CD = new int[size];

        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                AB[index] = A[i] + B[j];
                CD[index] = C[i] + D[j];
                index++;
            }
        }

        Arrays.sort(AB);
        Arrays.sort(CD);

        int left = 0;
        int right = size - 1;

        long answer = 0;

        while (left < size && right >= 0) {
            long sum = (long) AB[left] + CD[right];

            if (sum < 0) {
                left++;
            } else if (sum > 0) {
                right--;
            } else {
                int leftValue = AB[left];
                int rightValue = CD[right];

                long leftCount = 0;
                long rightCount = 0;

                while (left < size && AB[left] == leftValue) {
                    left++;
                    leftCount++;
                }

                while (right >= 0 && CD[right] == rightValue) {
                    right--;
                    rightCount++;
                }

                answer += leftCount * rightCount;
            }
        }

        System.out.println(answer);
    }

    private static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int pointer = 0;
        private int length = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (pointer >= length) {
                length = in.read(buffer);
                pointer = 0;

                if (length <= 0) {
                    return -1;
                }
            }

            return buffer[pointer++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;

            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }

            return value * sign;
        }
    }
}
