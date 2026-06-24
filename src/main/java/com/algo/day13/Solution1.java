package com.algo.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
1m 29s 동안 생각함
팰린드롬 분할
시간 제한: 2초
메모리 제한: 128 MB
문제

세준이는 어떤 문자열을 팰린드롬으로 분할하려고 한다. 예를 들어, ABACABA를 팰린드롬으로 분할하면, {A, B, A, C, A, B, A}, {A, BACAB, A}, {ABA, C, ABA}, {ABACABA} 등이 있다.

분할의 개수의 최솟값을 출력하는 프로그램을 작성하시오.

입력

첫째 줄에 문자열이 주어진다. 이 문자열은 알파벳 대문자로만 이루어져 있고, 최대 길이는 2,500이다.

출력

첫째 줄에 팰린드롬 분할의 개수의 최솟값을 출력한다.

예제 입력 1
BBCDDECAECBDABADDCEBACCCBDCAABDBADD
예제 출력 1
22
예제 입력 2
AAAA
예제 출력 2
1
예제 입력 3
ABCDEFGH
예제 출력 3
8
예제 입력 4
QWERTYTREWQWERT
예제 출력 4
5
 */
public class Solution1 {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();
        int n = str.length();

        // palindrome[start][end]:
        // str[start..end]가 팰린드롬인지 저장
        boolean[][] palindrome = new boolean[n][n];

        // 길이가 1인 문자열은 모두 팰린드롬
        for (int i = 0; i < n; i++) {
            palindrome[i][i] = true;
        }

        // 길이가 2인 문자열
        for (int i = 0; i < n - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                palindrome[i][i + 1] = true;
            }
        }

        // 길이가 3 이상인 문자열
        for (int length = 3; length <= n; length++) {
            for (int start = 0; start + length <= n; start++) {
                int end = start + length - 1;

                palindrome[start][end] =
                    str.charAt(start) == str.charAt(end)
                        && palindrome[start + 1][end - 1];
            }
        }

        // dp[i]: 앞에서부터 i개의 문자를 팰린드롬으로 분할하는 최소 개수
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;

        for (int end = 1; end <= n; end++) {
            for (int start = 0; start < end; start++) {
                // str[start..end-1]이 팰린드롬인 경우
                if (palindrome[start][end - 1]) {
                    dp[end] = Math.min(dp[end], dp[start] + 1);
                }
            }
        }

        System.out.println(dp[n]);
    }
}
