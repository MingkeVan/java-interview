package leetcode.backtracking.two_dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription 
 * @date 2020/11/10 11:26
 * 
 * 
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:

The order of returned grid coordinates does not matter.
Both m and n are less than 150.
 

Example:

Given the following 5x5 matrix:

  Pacific ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Return:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 */
// todo:
public class PacificAtlanticWaterFlow417 {
    int m = 0;
    int n = 0;

    int[][] direction = { {-1,0} , {0,1}, {1,0}, {0, -1}};
    boolean[][] visitedA = null;
    boolean[][] visitedP = null;


    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        
        if( matrix == null || matrix.length == 0) {
            return res;
        }

        m = matrix.length;
        n = matrix[0].length;

        visitedA = new boolean[m][n];
        visitedP = new boolean[m][n];

        for( int i = 0 ; i < m; i ++) {
            dfs(visitedP, matrix,i,0);
            dfs(visitedA, matrix,i,n-1);
               
        }

        for( int i = 0 ; i < n; i ++) {
            
            dfs(visitedP, matrix,0,i);
            dfs(visitedA, matrix,m-1,i);
           
        }

        for( int i = 0 ; i < m; i ++) {
            for( int j = 0; j < n; j++) {
                if(visitedP[i][j] && visitedA[i][j]) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    tmp.add(j);

                    res.add(tmp);
                }
            }   
        }

        return res;
    }

    private void dfs(boolean[][] visited,int[][] matrix,int startX,int startY) {
       
        visited[startX][startY] = true;

        for( int i = 0; i < direction.length; i++) {

            int newx = startX + direction[i][0]; 
            int newy = startY + direction[i][1];
            if(inArea(newx,newy) && matrix[newx][newy] >= matrix[startX][startY] && !visited[newx][newy]) {
                dfs( visited,matrix,newx,newy);
            }
        }
    }

    private boolean inArea(int x , int y) {
        return x < m && x >=0 && y< n && y>=0;
    }

}
