package com.cesar.ajedrez;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tablero implements Serializable {
    protected int size;
    protected Pieza[][] tablero2d;
    public boolean jugadorActivo = true;
    public boolean fin = false;

    public Tablero() {
        this.size = 8;
        tablero2d = new Pieza[size][size];
    }

    public Tablero(int size) {
        this.size = size;
        tablero2d = new Pieza[size][size];
    }

    public Pieza[][] getTablero2d() {
        return tablero2d;
    }

    public void reset(){
        for (int i = 0; i < tablero2d.length; i++) {
            for (int j = 0; j < tablero2d[i].length; j++) {
                switch (i){
                    case 0 -> {
                        switch (j){
                            case 0,7 -> tablero2d[i][j] = new Pieza(PiezaID.Torre, false);
                            case 1,6 -> tablero2d[i][j] = new Pieza(PiezaID.Caballo, false);
                            case 2,5 -> tablero2d[i][j] = new Pieza(PiezaID.Alfil, false);
                            case 3 -> tablero2d[i][j] = new Pieza(PiezaID.Rey, false);
                            case 4 -> tablero2d[i][j] = new Pieza(PiezaID.Reina, false);
                        }
                    }
                    case 1 -> tablero2d[i][j] = new Pieza(PiezaID.Peon, false);

                    case 7 -> {
                        switch (j){
                            case 0,7 -> tablero2d[i][j] = new Pieza(PiezaID.Torre, true);
                            case 1,6 -> tablero2d[i][j] = new Pieza(PiezaID.Caballo, true);
                            case 2,5 -> tablero2d[i][j] = new Pieza(PiezaID.Alfil, true);
                            case 3 -> tablero2d[i][j] = new Pieza(PiezaID.Reina, true);
                            case 4 -> tablero2d[i][j] = new Pieza(PiezaID.Rey, true);
                        }
                    }
                    case 6 -> tablero2d[i][j] = new Pieza(PiezaID.Peon, true);
                }
                if (i > 1 && i < 6){
                    tablero2d[i][j] = null;
                }
            }
        }
    }

    public void move(int Ofila, int Ocolumna, int Dfila, int Dcolumna, boolean onlyShow){
        int[] coordOrigen = {Ofila, Ocolumna};
        int[] coordDestino = {Dfila, Dcolumna};

        Pieza piezaOrigen = null;
        try {
            piezaOrigen = tablero2d[coordOrigen[0]][coordOrigen[1]];
        }catch (NullPointerException _){
            System.out.println("No se ha encontrado ninguna pieza en la casilla seleccionada");
        }
        Pieza piezaDestino = null;
        if (tablero2d[coordDestino[0]][coordDestino[1]] != null){
            piezaDestino = tablero2d[coordDestino[0]][coordDestino[1]];
        }

        List<int[]> posLegal = new ArrayList<>();

        if (piezaOrigen != null){
            switch (piezaOrigen.getId().id()){
                case "torre" -> {
                    List<int[]> legalMovesVERyHOR = Logica.legalMoveVERyHOR(coordOrigen, coordDestino, tablero2d);

                    posLegal.addAll(legalMovesVERyHOR);
                }
                case "alfil" -> {
                    List<int[]> legalMovesDIAGONAL = Logica.legalMoveDIAGONAL(coordOrigen, coordDestino, tablero2d);

                    posLegal.addAll(legalMovesDIAGONAL);
                }
                case "reina" -> {
                    List<int[]> legalMovesVERyHOR = Logica.legalMoveVERyHOR(coordOrigen, coordDestino, tablero2d);
                    List<int[]> legalMovesDIAGONAL = Logica.legalMoveDIAGONAL(coordOrigen, coordDestino, tablero2d);

                    posLegal.addAll(legalMovesDIAGONAL);
                    posLegal.addAll(legalMovesVERyHOR);
                }
                case "rey" -> {
                    List<int[]> legalMovesRey = Logica.legalMoveRey(coordOrigen, coordDestino, tablero2d);

                    posLegal.addAll(legalMovesRey);
                }
                case "peon" -> {
                    List<int[]> posLegalPeon = Logica.legalMovePeon(coordOrigen, tablero2d);

                    posLegal.addAll(posLegalPeon);
                }
                case "caballo" -> {
                    List<int[]> posLegalCaballo = Logica.legalMoveCaballo(coordOrigen, tablero2d);

                    posLegal.addAll(posLegalCaballo);
                }
            }
        }

        if (!onlyShow){
            boolean moveLegal = false;
            for (int[] ints : posLegal){
                if (ints[0] == coordDestino[0] && ints[1] == coordDestino[1]) {
                    moveLegal = true;
                    break;
                }
            }

            if (moveLegal){
                tablero2d[coordDestino[0]][coordDestino[1]] = piezaOrigen;
                tablero2d[coordOrigen[0]][coordOrigen[1]] = null;

                for (int i = 0; i < tablero2d.length; i++) {
                    for (int j = 0; j < tablero2d[i].length; j++) {
                        if (tablero2d[i][j] != null){
                            if (tablero2d[i][j].getId() == PiezaID.PeonPassant){
                                tablero2d[i][j] = null;
                            }
                        }
                    }
                }

                if (piezaOrigen.getId() == PiezaID.Peon && piezaOrigen.isPrimerMove()){
                    if ((Math.max(coordOrigen[0],coordDestino[0]) - Math.min(coordOrigen[0],coordDestino[0])) == 2){
                        if (piezaOrigen.isBando()){
                            tablero2d[coordOrigen[0]-1][coordOrigen[1]] = new Pieza(PiezaID.PeonPassant, piezaOrigen.isBando());
                        }else {
                            tablero2d[coordOrigen[0]+1][coordOrigen[1]] = new Pieza(PiezaID.PeonPassant, piezaOrigen.isBando());
                        }
                    }
                }
                if (piezaOrigen.getId() == PiezaID.Peon) {
                    if (piezaDestino != null){
                        if (piezaDestino.getId() == PiezaID.PeonPassant) {
                            if (coordDestino[1] < coordOrigen[1]) {
                                tablero2d[coordOrigen[0]][coordOrigen[1]-1] = null;
                            }else tablero2d[coordOrigen[0]][coordOrigen[1]+1] = null;
                        }
                    }
                }
                if (piezaOrigen.getId() == PiezaID.Peon && (coordDestino[0] == 0 || coordDestino[0] == 7)){
                    promocion(coordDestino[0], coordDestino[1], piezaOrigen.isBando());
                }

                tablero2d[coordDestino[0]][coordDestino[1]].setPrimerMove(false);

                if (piezaDestino != null){
                    if (piezaDestino.getId().id().equals("rey")){
                        System.out.println(piezaOrigen.isBando() ? "El bando Blanco gana!" : "El bando Negro gana!");
                        fin = true;
                    }
                }

                jugadorActivo = !jugadorActivo;
            }

            show();
        }else {
            for (int[] ints : posLegal){
                System.out.println(Arrays.toString(ints));
            }
        }
    }

    public void promocion(int fila, int columna, boolean bando){
        Scanner scanner = new Scanner(System.in);

        System.out.println("A qué pieza va a ser promociado este peón?");
        System.out.println("1 = Reina\n2 = Torre\n3 = Caballo\n4 = Alfil\nCualquier otro valor será considerado Reina");

        switch (scanner.nextLine()){
            case "2" -> tablero2d[fila][columna] = new Pieza(PiezaID.Torre, bando);
            case "3" -> tablero2d[fila][columna] = new Pieza(PiezaID.Caballo, bando);
            case "4" -> tablero2d[fila][columna] = new Pieza(PiezaID.Alfil, bando);
            default -> tablero2d[fila][columna] = new Pieza(PiezaID.Reina, bando);
        }
    }

    public void remove(int fila, int columna){
        tablero2d[fila][columna] = null;
    }

    public void add(int fila, int columna, Pieza pieza){
        tablero2d[fila][columna] = pieza;
    }

    public void show(){
        for (Pieza[] piezas : tablero2d) {
            StringBuilder msg = new StringBuilder();
            for (int j = 0; j < tablero2d.length; j++) {
                if (piezas[j] == null) {
                    msg.append("[] ");
                } else msg.append(piezas[j]).append(" ");
            }
            System.out.println(msg);
        }
    }
}
