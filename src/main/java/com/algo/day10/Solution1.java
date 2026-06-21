package com.algo.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
문제: 냉장 배송 상자 포장

온라인 마트에서는 주문받은 식품을 컨베이어 벨트에 놓인 순서대로 냉장 상자에 포장합니다.

각 식품에는 다음 두 가지 정보가 있습니다.

식품의 무게
식품을 안전하게 배송하기 위해 필요한 냉장 비용

하나의 상자에는 컨베이어 벨트에서 연속해서 놓여 있는 식품들만 담을 수 있습니다. 식품의 순서를 바꾸거나, 중간에 있는 식품을 건너뛸 수는 없습니다.

각 상자가 담을 수 있는 식품의 총무게는 W 이하입니다.

상자 하나의 배송 비용은 다음과 같이 계산합니다.

기본 배송비 B + 상자에 담긴 식품 중 가장 큰 냉장 비용

모든 식품을 빠짐없이 포장할 때 필요한 최소 배송 비용을 구하세요.

입력

첫 번째 줄에 식품의 개수 N, 상자의 최대 허용 무게 W, 기본 배송비 B가 주어집니다.

N W B

다음 N개의 줄에는 컨베이어 벨트 순서대로 각 식품의 무게 weight와 냉장 비용 cost가 주어집니다.

weight cost
제한 사항
1 ≤ N ≤ 2,000
1 ≤ W ≤ 10,000
1 ≤ B ≤ 100,000
1 ≤ weight ≤ W
1 ≤ cost ≤ 100,000

정답은 32비트 정수 범위를 넘을 수 있습니다.

출력

모든 식품을 포장하는 데 필요한 최소 배송 비용을 출력합니다.

예제 입력
5 10 100
4 30
2 80
5 20
3 50
2 40
예제 출력
250
예제 설명

다음과 같이 포장할 수 있습니다.

첫 번째 상자: 1번, 2번 식품
총무게: 4 + 2 = 6
배송비: 100 + max(30, 80) = 180
두 번째 상자: 3번, 4번, 5번 식품
총무게: 5 + 3 + 2 = 10
배송비: 100 + max(20, 50, 40) = 150

이 경우 총비용은 330입니다.

하지만 다음과 같이 포장하면 더 저렴합니다.

첫 번째 상자: 1번 식품
배송비: 100 + 30 = 130
두 번째 상자: 2번, 3번, 4번 식품
총무게: 2 + 5 + 3 = 10
배송비: 100 + 80 = 180
세 번째 상자: 5번 식품
배송비: 100 + 40 = 140

이 값은 450이므로 최소가 아닙니다.

따라서 예제 출력 250과 설명이 일치하지 않습니다. 정확한 최소 비용은 330입니다.
 */
public class Solution1 {
    static int N, W, B;
    static Food[] foods;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        foods = new Food[N+1];

        for(int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            foods[i] = new Food(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        int[] weightSum = new int[N+1];
        int[][] costMax = new int[N+1][N+1];

        for(int i = 1; i <= N; i++) {
            weightSum[i] = weightSum[i-1] + foods[i].weight;
        }

        for(int i = 1; i <= N; i++) {
            int maxCost = Integer.MIN_VALUE;
            for(int j = i; j <= N; j++) {
                if(maxCost < foods[j].cost) {
                    maxCost = foods[j].cost;
                    costMax[i][j] = maxCost;
                } else {
                    costMax[i][j] = maxCost;
                }
            }

        }

        // dp[i] = min(dp[j-1] + cost[j][i]) => i번째 식품까지 포장한다고 할 때, 마지막 박스가 j번부터 i번까지 담는다고 가정
        int[] dp = new int[N+1];
        for(int i=1; i<=N; i++) {
            dp[i] = Integer.MAX_VALUE;
            for(int j=1; j<=i; j++) {
                if(weightSum[i] - weightSum[j-1] <= W) {
                    dp[i] = Math.min(dp[i], dp[j-1] + B + costMax[j][i]);
                }
            }
        }

        System.out.println(dp[N]);
    }

    static class Food {
        int weight;
        int cost;

        public Food(int weight, int cost) {
            this.weight = weight;
            this.cost = cost;
        }
    }
}
