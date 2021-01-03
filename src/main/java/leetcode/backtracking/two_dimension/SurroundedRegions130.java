package leetcode.backtracking.two_dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/11/10 11:26
 */
public class SurroundedRegions130 {
    int m = 0;
    int n = 0;

    int[][] direction = { {-1,0} , {0,1}, {1,0}, {0, -1}};

    public void solve(char[][] board) {
        if( board == null || board.length <= 2) {
            return;
        }

        m = board.length;
        n = board[0].length;


        for( int i = 0 ; i < m; i ++) {
            dfs(board,i,0);
            dfs(board,i,n-1);
        }
        for( int i = 0 ; i < n; i ++) {
            dfs(board,0,i);
            dfs(board,m-1,i);
        }
        
        for( int i = 0 ; i < m; i ++) {
            for(int j = 0; j< n; j++) {
                if(board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }

        for( int i = 0 ; i < m; i ++) {
            for(int j = 0; j< n; j++) {
                if(board[i][j] == 'P') {
                    board[i][j] = 'O';
                }
            }
        }


    }

    private void dfs(char[][] board,int startX,int startY) {
        if(!inArea(startX,startY)|| board[startX][startY] != 'O') {
            return;
        }

        board[startX][startY] = 'P';

        for( int i = 0; i < direction.length; i++) {
            int newx = startX + direction[i][0]; 
            int newy = startY + direction[i][1];
            dfs(board,newx,newy);
            
        }
    }

    private boolean inArea(int x , int y) {
        return x < m && x >=0 && y< n && y>=0;
    }

}
