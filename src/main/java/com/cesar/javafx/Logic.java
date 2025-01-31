package com.cesar.javafx;

import java.util.HashMap;
import java.util.Map;

public class Logic {
    static Map<Coord, Coord> plano2dST = new HashMap<>();
    static Map<Coord, Coord> plano2dTS = new HashMap<>();

    public static Coord getTableroCoord(Coord coord){
        return plano2dST.get(coord);
    }

    public static Coord getScreenCoord(Coord coord){
        return plano2dTS.get(coord);
    }
}
