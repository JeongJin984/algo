package com.algo.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
문제: 비밀번호 복구

서버 비밀번호는 길이 N의 문자열이다. 각 문자는 A, B, C 중 하나다.

단, 보안 규칙 때문에 비밀번호에는 다음 패턴이 연속 부분 문자열로 등장하면 안 된다.

AAA
BBB
CCC
ABAB
BCBC
CACA

길이 N이 주어졌을 때, 가능한 비밀번호를 사전순으로 가장 앞서는 것부터 최대 1개 출력하라.
불가능하면 -1을 출력하라.
*/

public class Solution1 {

    static String[] banned = {"AAA", "BBB", "CCC", "ABAB", "BCBC", "CACA"};
    static int N;
    static String answer = null;


    public static void backtracking(String current) {
        if (answer != null) return;

        if (current.length() == N) {
            answer = current;
            return;
        }

        for (char ch = 'A'; ch <= 'C'; ch++) {
            String next = current + ch;

            if (isValid(next)) {
                backtracking(next);
            }
        }
    }

    public static boolean isValid(String str) {
        for (String ban : banned) {
            if (str.endsWith(ban)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
    }
}
