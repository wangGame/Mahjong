package com.maj.utils;

import com.badlogic.gdx.graphics.Color;
import com.maj.view.MahJItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by tony
 * Date on 2025/7/2.
 */
public class MahjongUtils {
    public static void getClickableTiles(MahJItem[][][] board) {
        int layers = board.length;
        int rows = board[0].length;
        int cols = board[0][0].length;

        for (int z = 0; z < layers; z++) {
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    MahJItem mahJItem = board[z][y][x];
                    if (mahJItem != null) {
                        // 1. 检查是否被上方盖住
                        if (isCovered(board, z, y, x)) {
                            mahJItem.setCannotMove();
                            continue;
                        }

//                        // 2. 检查是否左右有空间滑出
//                        if (canSlideOut(board, x, y, z)) {
//                            mahJItem.setCanMove();
//                        } else {
//                            mahJItem.setCannotMove();
//                        }

//                        // 3. 检查该麻将是否可滑动
//                        if (canMove(board, x, y, z)) {
//                            mahJItem.setCanMove(); // 假设我们为 MahjongItem 设置一个可以滑动的状态
//                        } else {
//                            mahJItem.setCannotMove(); // 如果不能滑动
//                        }
                    }
                }
            }
        }
    }

    private static boolean isCovered(MahJItem[][][] board, int x, int y, int z) {
        if(x + 1 <= board.length-1){
            if (board[x+1][y][z] !=null) {
                return true;
            }

            if (z-1>=0) {
                if (board[x+1][y][z-1] !=null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canMove(MahJItem[][][] board, int x, int y, int z) {
        // 获取board的尺寸
        int layers = board.length;
        int rows = board[0].length;
        int cols = board[0][0].length;

        // 检查麻将是否有效（不为空）
        if (board[z][y][x] == null) {
            return false; // 当前麻将无效
        }

        // 检查向四个方向（上下左右）是否有空位可以滑动
        boolean canMoveUp = (z > 0 && board[z - 1][y][x] == null);  // 向上滑动
        boolean canMoveDown = (z + 1 < layers && board[z + 1][y][x] == null);  // 向下滑动
        boolean canMoveLeft = (x - 1 >= 0 && board[z][y][x - 1] == null);  // 向左滑动
        boolean canMoveRight = (x + 1 < cols && board[z][y][x + 1] == null);  // 向右滑动

        // 判断是否有方向可以滑动
        return canMoveUp || canMoveDown || canMoveLeft || canMoveRight;
    }
}