package com.algo.day20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
문제

가로와 세로의 길이가 같은 평지에서 벌목을 한다. 그 지형은 0과 1로 나타나 있다. 1은 아직 잘리지 않은 나무를 나타내고 0은 아무 것도 없음을 나타낸다. 다음 지형을 보자.

B0011
B0000
B0000
11000
EEE00

위의 지형에서 길이 3의 통나무 BBB를 밀거나 회전시켜 EEE의 위치로 옮기는 작업을 하는 문제를 생각해 보자. BBB와 EEE의 위치는 임의로 주어진다. 단 문제에서 통나무의 길이는 항상 3이며 B의 개수와 E의 개수는 같다.

통나무를 움직이는 방법은 아래와 같이 상하좌우(Up, Down, Left, Right)와 회전(Turn)이다.

코드	의미
U	통나무를 위로 한 칸 옮긴다.
D	통나무를 아래로 한 칸 옮긴다.
L	통나무를 왼쪽으로 한 칸 옮긴다.
R	통나무를 오른쪽으로 한 칸 옮긴다.
T	중심점을 중심으로 90도 회전시킨다.

예를 들면 다음과 같다. (초기상태로부터의 이동)

상(U) : 통나무를 위로 한 칸 이동
하(D) : 통나무를 아래로 한 칸 이동
좌(L) : 통나무를 왼쪽으로 한 칸 이동
우(R) : 통나무를 오른쪽으로 한 칸 이동
회전(T) : 중심점을 기준으로 90도 회전

이와 같은 방식으로 이동시킬 때 그 움직일 위치에 다른 나무, 즉 1이 없어야 움직일 수 있다. 그리고 움직임은 위의 그림과 같이 한 번에 한 칸씩만 움직인다. 단 움직이는 통나무는 어떤 경우이든 중간칸에서 한 행이나 한 열에 놓일 수 있다. 예를 들면 위 그림에서 a와 같은 단계는 불가능하다.

그리고 회전의 경우에는 반드시 중심점을 중심으로 90도 회전해야 한다. (항상 통나무의 길이가 3이므로 중심점이 있음)

회전(Turn)이 가능하기 위해서는 통나무를 둘러싸고 있는 3×3 정사각형의 구역에 다른 나무도 없어야 한다. 즉, 아래 그림의 b, d와 같이 ?로 표시된 지역에 다른 나무 즉 1이 없어야만 회전시킬 수 있다. 따라서 c와 같은 경우에, 통나무는 왼쪽 하단에 벌채되지 않은 나무 때문에 회전시킬 수 없다.

문제는 통나무를 5개의 기본동작(U, D, L, R, T)만을 사용하여 처음 위치(BBB)에서 최종 위치(EEE)로 옮기는 프로그램을 작성하는 것이다. 최소 횟수의 동작을 사용해야 한다.

입력

첫째 줄에 주어진 평지의 한 변의 길이 N이 주어진다. (4 ≤ N ≤ 50)

이어서 N개의 줄에 지형의 정보가 0, 1, B, E로 이루어진 문자열로 주어진다.

한 줄에 입력되는 문자열의 길이는 N이며 입력 문자 사이에는 빈칸이 없다.

통나무와 최종 위치의 개수는 각각 3개이며, 최종 위치의 개수는 1개이다.

출력

첫째 줄에 최소 동작 횟수를 출력한다.

이동이 불가능하면 0만을 출력한다.

예제 입력 1
5
B0011
B0000
B0000
11000
EEE00
예제 출력 1
9
 */
public class Solution2 {
    static int N;
    static char[][] map;

    static int sr, sc, sDir;
    static int er, ec, eDir;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class State {
        int r, c, dir, dist;

        State(int r, int c, int dir, int dist) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new char[N][N];

        List<int[]> bList = new ArrayList<>();
        List<int[]> eList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                char ch = line.charAt(j);

                if (ch == 'B') {
                    bList.add(new int[]{i, j});
                    map[i][j] = '0';
                } else if (ch == 'E') {
                    eList.add(new int[]{i, j});
                    map[i][j] = '0';
                } else {
                    map[i][j] = ch;
                }
            }
        }

        initLog(bList, true);
        initLog(eList, false);

        System.out.println(bfs());
    }

    static void initLog(List<int[]> list, boolean isStart) {
        list.sort((a, b) -> {
            if (a[0] == b[0]) return a[1] - b[1];
            return a[0] - b[0];
        });

        int r = list.get(1)[0];
        int c = list.get(1)[1];

        int dir = list.get(0)[0] == list.get(1)[0] ? 0 : 1;

        if (isStart) {
            sr = r;
            sc = c;
            sDir = dir;
        } else {
            er = r;
            ec = c;
            eDir = dir;
        }
    }

    static int bfs() {
        Queue<State> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[N][N][2];

        q.offer(new State(sr, sc, sDir, 0));
        visited[sr][sc][sDir] = true;

        while (!q.isEmpty()) {
            State cur = q.poll();

            if (cur.r == er && cur.c == ec && cur.dir == eDir) {
                return cur.dist;
            }

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (canMove(nr, nc, cur.dir) && !visited[nr][nc][cur.dir]) {
                    visited[nr][nc][cur.dir] = true;
                    q.offer(new State(nr, nc, cur.dir, cur.dist + 1));
                }
            }

            int ndir = 1 - cur.dir;

            if (canTurn(cur.r, cur.c) && !visited[cur.r][cur.c][ndir]) {
                visited[cur.r][cur.c][ndir] = true;
                q.offer(new State(cur.r, cur.c, ndir, cur.dist + 1));
            }
        }

        return 0;
    }

    static boolean canMove(int r, int c, int dir) {
        if (dir == 0) {
            return isEmpty(r, c - 1)
                && isEmpty(r, c)
                && isEmpty(r, c + 1);
        }

        return isEmpty(r - 1, c)
            && isEmpty(r, c)
            && isEmpty(r + 1, c);
    }

    static boolean canTurn(int r, int c) {
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (!isEmpty(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean isEmpty(int r, int c) {
        return r >= 0 && r < N
            && c >= 0 && c < N
            && map[r][c] != '1';
    }
}
