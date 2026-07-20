package com.algo.day27;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
ABC
문제

정수 N과 K가 주어졌을 때, 다음 두 조건을 만족하는 문자열 S를 찾는 프로그램을 작성하시오.

문자열 S의 길이는 N이고, 'A', 'B', 'C'로 이루어져 있다.
문자열 S에는 0 ≤ i < j < N이면서 **S[i] < S[j]**를 만족하는 (i, j) 쌍이 K개가 있다.
입력

첫째 줄에 N과 K가 주어진다.

3 ≤ N ≤ 30
0 ≤ K ≤ N(N-1)/2
출력

첫째 줄에 문제의 조건을 만족하는 문자열 S를 출력한다. 가능한 S가 여러 가지라면, 아무거나 출력한다. 만약, 그러한 S가 존재하지 않는 경우에는 -1을 출력한다.
 */
public class Solution1 {

    static int N, K;

    // visited[idx][numA][numB][k]
    static boolean[][][][] visited;

    static char[] answer;

    static boolean dfs(int idx, int numA, int numB, int k) {
        // 목표 쌍의 개수를 초과한 경우
        if (k > K) {
            return false;
        }

        // 문자열 길이가 N이 된 경우
        if (idx == N) {
            return k == K;
        }

        // 이미 실패했던 상태라면 다시 탐색하지 않음
        if (visited[idx][numA][numB][k]) {
            return false;
        }

        visited[idx][numA][numB][k] = true;

        /*
         * A 추가
         *
         * 새로 생기는 쌍 없음
         */
        answer[idx] = 'A';

        if (dfs(idx + 1, numA + 1, numB, k)) {
            return true;
        }

        /*
         * B 추가
         *
         * 이전에 나온 A들과 쌍을 만듦
         * 증가량 = numA
         */
        answer[idx] = 'B';

        if (dfs(idx + 1, numA, numB + 1, k + numA)) {
            return true;
        }

        /*
         * C 추가
         *
         * 이전에 나온 A, B들과 쌍을 만듦
         * 증가량 = numA + numB
         */
        answer[idx] = 'C';

        if (dfs(idx + 1, numA, numB, k + numA + numB)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        /*
         * idx: 0 ~ N
         * numA: 0 ~ N
         * numB: 0 ~ N
         * k: 0 ~ K
         */
        visited = new boolean[N + 1][N + 1][N + 1][K + 1];
        answer = new char[N];

        if (dfs(0, 0, 0, 0)) {
            System.out.println(new String(answer));
        } else {
            System.out.println(-1);
        }
    }
}