package chess;

public class Rook extends Piece {
    
    // Constructor de la Torre: recibe su color y su posición inicial, 
    // y se los manda a la clase padre (Piece) usando "super()".
    public Rook(Color color, Position position){
        super(color, position);
    }

    // ==========================================
    // 1. SÍMBOLO VISUAL DE LA TORRE
    // ==========================================
    // Devuelve el carácter Unicode que se imprimirá en la terminal para representar a la torre.
    @Override 
    public char getSymbol(){
        return (color == Color.white) ? '♜' : '♖';
    }

    // ==========================================
    // 2. VALIDACIÓN DE MOVIMIENTOS VÁLIDOS
    // ==========================================
    // Este método verifica si el movimiento que el usuario intenta hacer con la torre es geométricamente legal.
    @Override 
    public boolean isValidMove(Board board, Position start, Position end){
        
        // Buscamos si hay alguna pieza ocupando la casilla de destino (end).
        Piece targetPiece = board.getPosition(end);

        // REGLA 1: 
        // Si la casilla de destino tiene una pieza y es de nuestro mismo color, el movimiento está prohibido.
        if (targetPiece != null && targetPiece.getColor() == this.color) {
            return false; 
        }

        // ==========================================
        // REGLA 2: MOVIMIENTO VERTICAL (Misma columna, cambia de fila)
        // ==========================================
        if (end.column() == start.column()) {
            
            // --- CASO A: La torre se mueve hacia arriba (las filas disminuyen en la matriz) ---
            if (end.row() < start.row()) {
                // Recorremos desde una casilla arriba de nuestro origen hasta una casilla antes del destino.
                for (int i = start.row() - 1; i > end.row(); i--) {
                    Position auxPosition = new Position(i, start.column());
                    // Si encontramos CUALQUIER pieza en medio del camino, la torre está siendo bloqueada.
                    if (board.getPosition(auxPosition) != null) {
                        return false; 
                    }
                }
            } 
            // --- CASO B: La torre se mueve hacia abajo (las filas aumentan en la matriz) ---
            else {
                // Recorremos desde una casilla abajo de nuestro origen hasta una casilla antes del destino.
                for (int i = start.row() + 1; i < end.row(); i++) {
                    Position auxPosition = new Position(i, start.column());
                    // Verificamos que no haya piezas estorbando en medio.
                    if (board.getPosition(auxPosition) != null) {
                        return false; 
                    }
                }
            }
            
            // Si el bucle terminó sin encontrar obstáculos, el camino vertical está libre. 
            // El movimiento es válido (ya sea para avanzar a una casilla vacía o capturar un enemigo al final).
            return true;
        }

        // ==========================================
        // REGLA 3: MOVIMIENTO HORIZONTAL (Misma fila, cambia de columna)
        // ==========================================
        if (end.row() == start.row()) {
            
            // --- CASO A: La torre se mueve hacia la izquierda (las columnas disminuyen) ---
            if (end.column() < start.column()) {
                // Recorremos desde una columna a la izquierda del origen hasta una antes del destino.
                for (int i = start.column() - 1; i > end.column(); i--) {
                    Position auxPosition = new Position(start.row(), i);
                    if (board.getPosition(auxPosition) != null) {
                        return false; // Hay una pieza bloqueando el paso intermedio
                    }
                }
            } 
            // --- CASO B: La torre se mueve hacia la derecha (las columnas aumentan) ---
            else {
                // Recorremos desde una columna a la derecha del origen hasta una antes del destino.
                for (int i = start.column() + 1; i < end.column(); i++) {
                    Position auxPosition = new Position(start.row(), i);
                    if (board.getPosition(auxPosition) != null) {
                        return false; // Hay una pieza bloqueando el paso intermedio
                    }
                }
            }
            
            // Si el camino horizontal está completamente libre, el movimiento es válido.
            return true;
        }

        // REGLA 4: Si la torre intentó moverse en diagonal o hacer un salto que no es ni recto vertical 
        // ni recto horizontal, el movimiento no es de Torre y se rechaza.
        return false;
    }
}