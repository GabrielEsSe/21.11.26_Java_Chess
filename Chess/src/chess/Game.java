package chess;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    
    // ==========================================
    // 1. ATRIBUTOS PRINCIPALES DEL JUEGO
    // ==========================================
    // Cargamos lo esencial para que la partida exista en memoria:
    private Board board;                        // El tablero físico (la matriz de 8x8 con las piezas)
    private Color currentTurn;                  // De quién es el turno actual (WHITE o BLACK)
    public  ArrayList<String> move; // Guardaremos las Jugadas.

    // ==========================================
    // 2. CONSTRUCTOR
    // ==========================================
    // Se ejecuta al hacer "new Game()". Inicializa el estado inicial de una partida nueva.
    public Game(){
        this.move = new ArrayList<>();
        this.board = new Board();             // Creamos un tablero nuevo y ordenamos las piezas en sus casillas base, escio en RAM en el HEAP (this.board: referencia en memoria)
        this.currentTurn = Color.white;       // Por regla, las blancas siempre mueven primero
    }
    
    // ==========================================
    // 3. EL BUCLE PRINCIPAL
    // ==========================================
    // Este método mantiene la partida viva turno tras turno hasta que el usuario decide salir.
    public void start(){
        // Creamos el Scanner para leer lo que el usuario escribe en la terminal
        Scanner sc = new Scanner(System.in);
        boolean playing = true; // Bandera booleana para controlar cuándo se acaba el juego

        System.out.println("<<Welcome>>");
        
        // Iniciamos el bucle: se repite indefinidamente mientras "playing" sea verdadero
        while(playing){
            
            // Paso A: Dibujar el estado actual del tablero en la consola
            board.printBoard(); 
            
            // Mostramos de qué equipo es el turno actual
            System.out.println(">> Turno: " + currentTurn);
            System.out.println(">> Jugada (e.g. e2 e4) ");
            System.out.println(">> Escribe '...' para salir");
            System.out.println("o escribe '--help' para consultar el historial de jugadas. ");

            // Paso B: Leer la casilla de origen que ingresa el usuario (ej: "e2")
            String fisrtInput = sc.next();
            
            // Verificamos si el usuario quiso salir escribiendo "-1"
            if(fisrtInput.equals("...")){
                playing = false; // Cambiamos la bandera a falso para romper el while
                break;           // Rompemos el ciclo inmediatamente
            }

            if (fisrtInput.equals("--help")) { 
                System.out.println("\n--- HISTORIAL DE JUGADAS (CIFRADO XOR) ---");
                
                if (move.isEmpty()) {
                    System.out.println("Aún no hay jugadas registradas.");
                } else {
                    char secretKey = 'K'; // Clave secreta para descifrar el historial al consultarlo
                    
                    for (int i = 0; i < move.size(); i += 2) {
                        int numeroTurno = (i / 2) + 1;
                        
                        // Desciframos la jugada de las blancas para poder leerla en pantalla
                        String decryptedWhite = xorCipher(move.get(i), secretKey);
                        System.out.print("Turno " + numeroTurno + " - Blancas: " + decryptedWhite);
                        
                        if (i + 1 < move.size()) {
                            // Desciframos la jugada de las negras para poder leerla en pantalla
                            String decryptedBlack = xorCipher(move.get(i + 1), secretKey);
                            System.out.println(" | Negras: " + decryptedBlack);
                        } else {
                            System.out.println(" | Negras: (Esperando movimiento...)");
                        }
                    }
                }
                System.out.println("-------------------------------------------\n");
                continue; // Volvemos al inicio del while para seguir jugando
                }

            // Paso C: Leer la casilla de destino (ej: "e4")
            String secondInput = sc.next();
            
            // Paso D: Traducir las coordenadas algebraicas ("e2", "e4") a coordenadas de matriz (filas y columnas 0-7)
            Position start = Position.translate(fisrtInput);
            System.out.println(start); //[PRUEBA]
            Position end = Position.translate(secondInput);
            System.out.println(end);//[PRUEBA]

            // Paso E: Validar que las coordenadas estén dentro del tablero (0 a 7)
            if(!start.isValid() || !end.isValid()){
                System.out.println("\n[ERROR] Coordenadas fuera del tablero. Inténtalo de nuevo.");
                continue; // Salta al siguiente ciclo del while sin procesar nada más
            }

            // Paso F: Obtener la pieza que está en la casilla de origen
            Piece target = board.getPosition(start);
            
            // Paso G: Validar que la casilla no esté vacía (null) y que la pieza pertenezca al jugador en turno
            if(target != null && target.getColor() == currentTurn){
                
                // Paso H: Consultar si el movimiento es geométricamente válido para esa pieza específica
                if(target.isValidMove(board, start, end)){

                    // Si es válido, ejecutamos el movimiento en el tablero:
                    board.setPosition(end, target); // Colocamos la pieza en la casilla de destino
                    board.setPosition(start, null); // Vaciamos la casilla de origen dejándola en null

                    // Cambiamos de turno usando un operador ternario: si era blancas pasa a negras, y viceversa
                    currentTurn = (currentTurn == Color.white) ? Color.black : Color.white;
                    
                    // Guardamos la jugada cifrada con XOR en nuestro arrayList para protegerla en memoria
                    char secretKey = 'K';
                    String rawMove = fisrtInput + " " + secondInput;
                    String encryptedMove = xorCipher(rawMove, secretKey);
                    move.add(encryptedMove);

                    System.out.println("\n¡Movimiento realizado!");  
                } else {
                    // La pieza existe y es nuestra, pero su regla de movimiento no permite este salto
                    System.out.println("\n[ERROR] Movimiento inválido para esta pieza.");
                } 
            } else {
                // Intentó mover una casilla vacía o una pieza del contrincante
                System.out.println("\n[ERROR] No hay una pieza tuya en esa posición de origen.");
            }
        } // Fin del bucle while

        // Paso I: Cerramos el Scanner FUERA del bucle, justo cuando el juego termina por completo
        sc.close();
        System.out.println("\n[END] ¡Juego terminado!");
    }

    // ==========================================
    // 4. CIFRADO POR XOR
    // ==========================================
    private String xorCipher(String text, char key) {
        StringBuilder cipherText = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char encryptedChar = (char) (text.charAt(i) ^ key);
            cipherText.append(encryptedChar);
        }
        
        return cipherText.toString();
    }

    // ==========================================
    // 5. PUNTO DE ENTRADA (MAIN)
    // ==========================================
    public static void main(String[] args) {
        Game chessGame = new Game(); // Instanciamos el objeto del juego
        chessGame.start();           // Arrancamos el ciclo de juego
    }
}