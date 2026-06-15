package com.algo.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
문제: 구독 서비스 정산하기
문제 설명

당신은 여러 구독 서비스를 한 번에 관리하는 앱을 만들고 있다.

사용자는 하루에 한 번씩 구독료 정산 내역을 기록한다.
각 날짜의 정산 금액은 정수 A[i]로 주어진다.

양수: 그날 추가로 결제한 금액
음수: 그날 환불받은 금액
0: 아무 일도 없음

앱은 사용자가 특정 기간을 조회했을 때, 그 기간의 총 정산 금액이 정확히 X원이 되는 경우를 찾으려고 한다.

단, 너무 짧은 기간은 의미가 없으므로 조회 기간의 길이는 최소 L일 이상이어야 한다.

전체 기록에서 다음 조건을 만족하는 연속 기간의 개수를 구하여라.

기간의 총 정산 금액이 정확히 X
기간의 길이가 L 이상
입력
N X L
A1 A2 A3 ... AN
출력

조건을 만족하는 연속 기간의 개수를 출력한다.

제한
1 ≤ N ≤ 500,000
-10^9 ≤ A[i] ≤ 10^9
-10^14 ≤ X ≤ 10^14
1 ≤ L ≤ N

정답은 64비트 정수 범위를 사용할 수 있다.

예제 입력
8 3 3
1 2 -1 2 1 -2 3 0
예제 출력
4
 */
public class Solution1 {
    static int N, L;
    static long X;
    static long[] A;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        X = Long.parseLong(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        A = new long[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }

        long[] sumA = new long[N+1];
        sumA[0] = 0;
        for(int i = 1; i <= N; i++) {
            sumA[i] = sumA[i - 1] + A[i];
        }


        // X = S[l,r] = S(r) - S(l-1)
        // S(l-1) = S(r) - X, l-1은 sumA의 인덱스
        // 1 <= l <= r - L + 1 (null,1,2,3,4 -> 0,1,3,6,10 / 길이2, r=3, 최대 l=2 S(3)-S(2),)
        // 이러면 Map에 S(i)->i 로 카운팅 맵을 만들고 S(l-1)의 개수 출력
        int answer = 0;
        Map<Long, Long> countMap = new HashMap<>();
        for(int r=1; r<=N; r++) {
            int maxSumALeft = r - L;
            if(maxSumALeft >= 0) {
                long sumA_maxLeft = sumA[maxSumALeft]; // 현재 r에서 새롭게 허용되는 가장 오른쪽 시작점(l)에 대응하는 sumA 값
                countMap.put(sumA_maxLeft, countMap.getOrDefault(sumA_maxLeft, 0L) + 1); // 위 값의 개수(최단구간 합)
            }

            long target = sumA[r] - X;
            answer += countMap.getOrDefault(target, 0L); // S[r]-X = S(l-1) 값을 가지는 S[0...r-L]가 몇 개 있는가?
        }

        System.out.println(answer);
    }
}
