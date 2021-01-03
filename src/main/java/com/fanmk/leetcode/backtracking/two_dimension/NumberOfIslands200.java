package com.fanmk.leetcode.backtracking.two_dimension;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/12/20 11:26
 */
public class NumberOfIslands200 {
    int m = 0;
    int n = 0;

    int[][] direction = { {-1,0} , {0,1}, {1,0}, {0, -1}};
    boolean[][] visited = null;
    int num = 0;

    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        
        m = grid.length;
        n = grid[0].length;
        visited = new boolean[m][n];
        
        for(int i = 0;i < m; i ++) {
            for(int j = 0 ; j< n; j++) {
                if(!visited[i][j] && grid[i][j] =='1') {
                    dfs(grid,i,j);
                    num+=1;
                }
            }
        }

        return num;

    }

    private void dfs(char[][] grid,int startX,int startY) {
        

        visited[startX][startY] = true;
        for( int i = 0; i < direction.length; i++) {

            int newx = startX + direction[i][0]; 
            int newy = startY + direction[i][1];
            if(inArea(newx,newy) && !visited[newx][newy] && grid[newx][newy] == '1') {
                dfs(grid,newx,newy);
            }
        }
        
    }

    private boolean inArea(int x , int y) {
        return x < m && x >=0 && y< n && y>=0;
    }

}
