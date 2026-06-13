package com.algo.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
문제: 냉장고 털이 루트

당신은 퇴근 후 집에 왔다. 냉장고 안에는 여러 재료가 있고, 각 재료는 다른 재료와 조합해서 요리할 수 있다.

요리는 다음 규칙을 따른다.

재료는 1번부터 N번까지 있다.
어떤 재료 A를 사용하면, 그 다음에는 정해진 재료들 중 하나로만 이어서 요리할 수 있다.
즉, 재료 관계는 방향 그래프다.
같은 재료를 여러 번 써도 되지만, 요리 순서 안에서 같은 재료가 다시 등장하면 실패한다.
시작 재료는 아무거나 고를 수 있다.
만들 수 있는 가장 긴 요리 순서의 길이를 구하라.

단, 재료 관계에 사이클이 있을 수 있다.
사이클에 들어가면 같은 재료를 다시 쓰게 되므로 그 경로는 실패 처리해야 한다.

입력
A1 B1
A2 B2
...
AM BM
1 ≤ N ≤ 100,000
0 ≤ M ≤ 200,000
N M
Ai Bi는 Ai 다음에 Bi를 사용할 수 있다는 뜻이다.
출력
가장 긴 성공 요리 순서의 길이
예시
입력
6 7
1 2
2 3
3 4
4 2
2 5
5 6
1 6
출력
4

가능한 순서:

1 → 2 → 5 → 6

2 → 3 → 4 → 2는 같은 재료 2가 다시 나오므로 실패 경로다.
 */
public class Solution1 {
    static int N, M;
    static int[] state;
    static int[] dp;
    static Map<Integer, List<Integer>> graph;

    static int dfs(int cur) {
        if (state[cur] == 2) return dp[cur];

        state[cur] = 1;
        int length = 1;

        for(int next : graph.get(cur)) {
            if(state[next] == 1) continue;
            length = Math.max(length, dfs(next));
        }

        state[cur] = 2;
        return dp[cur] = length;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        state = new int[N + 1];
        dp = new int[N + 1];

        graph = new HashMap<>();

        for(int i = 1; i <= N; i++) {
            graph.put(i, new ArrayList<>());
        }

        for(int i = 0; i < M; i++) {
            input = br.readLine().split(" ");
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            graph.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        }

        int maxLength = 0;
        for(int i = 1; i <= N; i++) {
            maxLength = Math.max(maxLength, 1 + dfs(i));
        }

        System.out.println(maxLength);
    }
}
