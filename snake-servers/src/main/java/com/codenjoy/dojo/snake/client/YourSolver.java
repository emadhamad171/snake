package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.snake.model.Elements;

import java.util.List;
import java.util.Optional;

///**
// * User: your name
// */
//public class YourSolver implements Solver<Board> {
//
//    private Dice dice;
//    private Board board;
//
//
//    public YourSolver(Dice dice) {
//        this.dice = dice;
//    }
//    @Override
//    public String get(Board board) {
//        this.board = board;
//        System.out.println(board.toString());
//       List apples = board.getApples();
//
//
//
//        return (
//         Direction.STOP.toString()
//
//
//        );
//    }

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

//import com.codenjoy.dojo.client.Solver;
//import com.codenjoy.dojo.client.WebSocketRunner;
//import com.codenjoy.dojo.services.Dice;
//import com.codenjoy.dojo.services.Direction;
//import com.codenjoy.dojo.services.Point;
//import com.codenjoy.dojo.services.RandomDice;
//import client.BoardLee;
//
//import java.util.List;
//import java.util.Optional;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    public int invertVervical(int val, int dimY) {
        return dimY - val - 1;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        if (board.isGameOver()) return "";
        char[][] field = board.getField();
        int sizeX = field.length;
        int sizeY = field[0].length;
        BoardLee boardLee = new BoardLee(sizeX, sizeY);
        List<Point> barriers = board.getBarriers();
        barriers.forEach(p -> boardLee.setObstacle(p.getX(), invertVervical(p.getY(), sizeY)));




        Point me = board.getHead();
        List<Point> apples = board.getApples();
        System.out.println(apples);
        PointLee src = new PointLee(me.getX(), invertVervical(me.getY(), sizeY));
        PointLee dst = new PointLee(apples);
        Optional<List<PointLee>> solution = boardLee.trace(src, dst);
        if (solution.isPresent()) {
            List<PointLee> path = solution.get();
            PointLee p = path.stream().skip(1).findFirst().get();
//            System.out.printf("ME: x:%2d, y:%2d\n", me.getX(), me.getY());
            int to_x = p.x();
            int to_y = invertVervical(p.y(), sizeY);
            System.out.printf("TO: x:%2d, y:%2d\n", to_x, to_y);
            if (to_y < me.getY()) return Direction.DOWN.toString();
            if (to_y > me.getY()) return Direction.UP.toString();
            if (to_x < me.getX()) return Direction.LEFT.toString();
            if (to_x > me.getX()) return Direction.RIGHT.toString();
        }
        return Direction.ACT.toString();
    }


    public static void main(String[] args) {
        WebSocketRunner.runClient(
                "http://206.81.16.237/codenjoy-contest/board/player/i0olzn9tthxvhk8ykitd?code=2585802424666404743",


                new YourSolver(new RandomDice()),
                new Board());
    }

}
