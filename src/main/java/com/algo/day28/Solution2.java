package com.algo.day28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
11066번 - 파일 합치기
문제

소설가인 김대전은 소설을 여러 장(chapter)으로 나누어 쓰는데, 각 장은 각각 다른 파일에 저장하고 한다. 소설의 모든 장을 쓰고 나서는 각 장이 쓰여진 파일을 합쳐서 최종적으로 소설의 완성본이 들어있는 한 개의 파일을 만든다.

이 과정에서 두 개의 파일을 합쳐서 하나의 임시파일을 만들고, 이 임시파일이나 원래의 파일을 계속 두 개씩 합쳐서 소설의 여러 장들이 연속이 되도록 파일을 합쳐나가고, 최종적으로는 하나의 파일로 합친다.

두 개의 파일을 합칠 때 필요한 비용(시간 등)이 두 파일 크기의 합이라고 가정할 때, 최종적인 한 개의 파일을 완성하는데 필요한 비용의 총합을 계산하시오.

예를 들어, C1, C2, C3, C4가 연속적인 네 개의 장을 수록하고 있는 파일이고, 파일 크기가 각각 40, 30, 30, 50이라고 하자.

이 파일들을 합치는 과정에서, 먼저 C2와 C3를 합쳐서 임시파일 X1을 만든다. 이때 비용 60이 필요하다. 그 다음으로 C1과 X1을 합쳐 임시파일 X2를 만들면 비용 100이 필요하다. 최종적으로 X2와 C4를 합쳐 최종파일을 만들면 비용 150이 필요하다. 따라서 최종의 한 파일을 만드는데 필요한 비용의 합은 60 + 100 + 150 = 310이다.

다른 방법으로 파일들을 합치면 비용을 줄일 수 있다. 먼저 C1과 C2를 합쳐 임시파일 Y1을 만들고, C3와 C4를 합쳐 임시파일 Y2를 만든 뒤, 최종적으로 Y1과 Y2를 합쳐 최종파일을 만들 수 있다. 이때 필요한 총 비용은 70 + 80 + 150 = 300이다.

소설의 각 장들이 수록되어 있는 파일의 크기가 주어질 때, 이 파일들을 하나의 파일로 합칠 때 필요한 최소 비용을 계산하는 프로그램을 작성하시오.

입력

프로그램은 표준 입력에서 입력 데이터를 받는다.

프로그램의 입력은 T개의 테스트 데이터로 이루어져 있는데, T는 입력의 맨 첫 줄에 주어진다.

각 테스트 데이터는 두 개의 행으로 주어진다.

첫 번째 행에는 소설을 구성하는 장의 수를 나타내는 양의 정수 K(3 ≤ K ≤ 500)가 주어진다.
두 번째 행에는 1장부터 K장까지 수록한 파일의 크기를 나타내는 양의 정수 K개가 주어진다.
파일의 크기는 10,000을 초과하지 않는다.
출력

프로그램은 표준 출력에 출력한다.

각 테스트 데이터마다 정확히 한 행에 출력하는데, 모든 장을 합치는 데 필요한 최소 비용을 출력한다.

예제 입력 1
2
4
40 30 30 50
15
1 21 3 4 5 35 5 4 3 5 98 21 14 17 32
예제 출력 1
300
864
 */
public class Solution2 {
    static int T;
    static int[] A;
    static int[][] dp;
    static int[] sumA;

    static int foo(int i, int j) {
        if(i == j) return 0;

        if(dp[i][j] != -1) return dp[i][j];

        int result = Integer.MAX_VALUE;
        int cost = sumA[j+1] - sumA[i];
        for(int m=i; m<j; m++) {
            result = Math.min(result, foo(i, m) + foo(m+1, j) + cost) ;
        }

        return dp[i][j] = result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());

        for(int t=0; t<T; t++) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            A = new int[K];
            for(int i=0; i<K; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }
            sumA = new int[K+1];
            sumA[0] = 0;
            for(int i=1; i<=K; i++) {
                sumA[i] = sumA[i-1] + A[i-1];
            }

            dp = new int[K][K];
            for(int i=0; i<K; i++) {
                for(int j=0; j<K; j++) {
                    dp[i][j] = -1;
                }
            }

            System.out.println(foo(0, K - 1));
        }
    }
}
