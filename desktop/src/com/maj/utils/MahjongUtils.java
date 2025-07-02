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
    public static class MahjongPosition {
        public int layer;
        public int row;
        public int col;

        public MahjongPosition(int layer, int row, int col) {
            this.layer = layer;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Layer: " + layer + ", Row: " + row + ", Col: " + col;
        }
    }

    /**
     * 获取可点击（可以出的）麻将列表
     *
     * @param board 三维麻将数组
     * @return 可点击麻将位置列表
     */
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
                        if (isCovered(board, x, y, z)) {
                            mahJItem.setColor(Color.BLACK);
                            continue;
                        }

                        // 2. 检查是否左右有空间滑出
                        if (canSlideOut(board, x, y, z)) {
                            mahJItem.setColor(Color.WHITE);
                        }else {
                            mahJItem.setColor(Color.BLACK);

                        }
                    }
                }
            }
        }


    }

    /**
     * 判断当前麻将是否被上方盖住
     */
    private static boolean isCovered(MahJItem[][][] board, int x, int y, int z) {
        if (z + 1 >= board.length) {
            return false;
        }
        MahJItem[][] upperLayer = board[z + 1];

        // 偏移为正负 1 都会遮挡当前麻将
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < board[0][0].length && ny >= 0 && ny < board[0].length) {
                    if (upperLayer[ny][nx] != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前麻将是否左右至少有一侧可以滑出
     */
    private static boolean canSlideOut(MahJItem[][][] board, int x, int y, int z) {
        int layers = board.length;
        int rows = board[0].length;
        int cols = board[0][0].length;

        boolean leftFree = false;
        boolean rightFree = false;



        // 检查左侧
        if (x - 2 < 0 || board[z][y][x - 2] == null) {
            leftFree = true;
        }

        // 检查右侧
        if (x + 2 >= cols || board[z][y][x + 2] == null) {
            rightFree = true;
        }

        return leftFree || rightFree;
    }
}
