package com.algo.day28;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/*
문제

총 25명의 여학생들로 이루어진 여학생반은 5×5의 정사각형 격자 형태로 자리가 배치되었고, 얼마 지나지 않아 이다솜과 임도연이라는 두 학생이 두각을 나타내며 다른 학생들을 휘어잡기 시작했다. 곧 모든 여학생이 '이다솜파'와 '임도연파'의 두 파로 갈라지게 되었으며, 얼마 지나지 않아 '임도연파'가 세력을 확장시키며 '이다솜파'를 위협하기 시작했다.

위기의식을 느낀 '이다솜파'의 학생들은 과감히 현재의 체제를 포기하고, '소문난 칠공주'를 결성하는 것이 유일한 생존 수단임을 깨달았다. '소문난 칠공주'는 다음과 같은 규칙을 만족해야 한다.

이름이 이름인 만큼, 7명의 여학생들로 구성되어야 한다.
강한 결속력을 위해, 7명의 자리는 서로 가로나 세로로 반드시 인접해 있어야 한다.
화합과 번영을 위해, 반드시 '이다솜파'의 학생들로만 구성될 필요는 없다.
그러나 생존을 위해, '이다솜파'가 반드시 우위를 점해야 한다. 따라서 7명의 학생 중 '이다솜파'의 학생이 적어도 4명 이상은 반드시 포함되어야 한다.

여학생반의 자리 배치가 주어졌을 때, '소문난 칠공주'를 결성할 수 있는 모든 경우의 수를 구하는 프로그램을 작성하시오.

입력

'S'(이다솜파의 학생을 나타냄) 또는 'Y'(임도연파의 학생을 나타냄)를 값으로 갖는 5×5 행렬이 공백 없이 첫째 줄부터 다섯 줄에 걸쳐 주어진다.

출력

첫째 줄에 '소문난 칠공주'를 결성할 수 있는 모든 경우의 수를 출력한다.
 */
public class Solution1 {
    static char[][] map = new char[5][5];
    static int[] selected = new int[7];

    static int combination(int depth, int cur, int numS) {
        int numY = depth - numS;

        if(depth == 7) {
            if(isConnected() && numS >= 4) {
                return 1;
            } else {
                return 0;
            }
        }

        if(numY > 3) return 0;

        int answer = 0;
        for(int next = cur+1; next<25; next++) {
            selected[depth] = next;

            int i = next / 5;
            int j = next % 5;

            answer += combination(depth + 1, next, numS + (map[i][j] == 'S' ? 1 : 0));
        }

        return answer;
    }

    static boolean isConnected() {
        int[] di = {-1, 1, 0, 0};
        int[] dj = {0, 0, -1, 1};

        int start = selected[0];

        Set<Integer> set = new HashSet<>();
        for(int i=1; i<7; i++) {
            set.add(selected[i]);
        }

        int count = 1;

        int startI = start / 5;
        int startJ = start % 5;

        boolean[][] visited = new boolean[5][5];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startI, startJ});
        visited[startI][startJ] = true;
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();

            for(int i=0; i<4; i++) {
                int[] next = new int[]{cur[0] + di[i], cur[1] + dj[i]};

                if(next[0] < 0 || next[0] >= 5 || next[1] < 0 || next[1] >= 5) continue;
                if(visited[next[0]][next[1]]) continue;

                int nextIdx = next[0] * 5 + next[1];

                if(set.contains(nextIdx)) {
                    visited[next[0]][next[1]] = true;
                    queue.offer(next);
                    count++;
                }
            }
        }

        return count == 7;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));

        for(int i = 0; i < 5; i++) {
            String s = br.readLine();
            for(int j = 0; j < 5; j++) {
                map[i][j] = s.charAt(j);
            }
        }

        System.out.println(combination(0, -1, 0));
    }
}
