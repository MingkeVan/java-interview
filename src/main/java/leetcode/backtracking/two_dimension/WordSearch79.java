package leetcode.backtracking.two_dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/11/10 11:26
 */
public class WorkSearch79 {
    int m = 0;
    int n = 0;

    int[][] direction = { {-1,0} , {0,1}, {1,0}, {0, -1}};
    boolean[][] visited = null;

    public boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0) {
            return false;
        }
        

        //剪枝
        if(! checkCharFrequency(board,word)) {
            return false;
        }
        
        m = board.length;
        n = board[0].length;
        visited = new boolean[m][n];
        
        for(int i = 0;i < m; i ++) {
            for(int j = 0 ; j< n; j++) {
                if(searchWord(board,word,0,i,j)) {
                    return true;
                }
            }
        }

        return false;

    }

    private boolean searchWord(char[][] board,String word,int index,int startX,int startY) {
       if( index == word.length() - 1) {
            return word.charAt(index) == board[startX][startY];
        }

        if(word.charAt(index) == board[startX][startY]) {
            visited[startX][startY] = true;
            for( int i = 0; i < direction.length; i++) {

                int newx = startX + direction[i][0]; 
                int newy = startY + direction[i][1];
                if(inArea(newx,newy) && !visited[newx][newy] && searchWord(board, word, index + 1, newx, newy)) {
                    return true;
                }
            }
            visited[startX][startY] = false;
        }

        return false;
    }

    private boolean inArea(int x , int y) {
        return x < m && x >=0 && y< n && y>=0;
    }

    private boolean checkCharFrequency(char[][] board,String word) {
        int [] wordAlpha = new int[128];
        int [] boardAlpha = new int[128];
        
        for (char [] row : board){
            for (char c: row){
                ++boardAlpha[c];
            }
        }
        
        for (char c : word.toCharArray() ){
            ++wordAlpha[c];
        }
        
        // Check that board has enough character frequencies for word before searching
        for (int i = 0; i < 128; ++i){
            if (wordAlpha[i] > boardAlpha[i])
                return false;
        }

        return true;
    }
}
