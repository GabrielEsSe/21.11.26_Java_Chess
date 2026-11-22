package chess;

// La clase Knight hereda de Piece, compartiendo color y posición.
public class Knight extends Piece {
    
    // Constructor de la clase Knight
    public Knight(Color color, Position position){
        super(color, position);
    }   

    // ==========================================
    // 1. SÍMBOLO VISUAL DEL CABALLO
    // ==========================================
    @Override 
    public char getSymbol(){
        return (color == Color.white) ? '♞' : '♘';
    }

    // ==========================================
    // 2. VALIDACIÓN DE MOVIMIENTOS VÁLIDOS
    // ==========================================
    @Override 
    public boolean isValidMove(Board board, Position start, Position end){
        
        // Buscamos si hay una pieza en la casilla de destino
        Piece targetPiece = board.getPosition(end);

        // REGLA 1: Fuego amigo (Evitar capturar tus propias piezas)
        if (targetPiece != null && targetPiece.getColor() == this.color) {
            return false; 
        }

        // REGLA 2: La geometría del salto en "L"
        // Calculamos las distancias absolutas recorridas en filas y columnas
        int rowDiff = Math.abs(end.row() - start.row());
        int colDiff = Math.abs(end.column() - start.column());

        // El movimiento del caballo es válido SÓLO si hace una "L" perfecta:
        // O bien 2 filas y 1 columna, o bien 1 fila y 2 columnas.
        boolean isAValidLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);

        if (!isAValidLShape) {
            return false; // Si no tiene forma de L, el movimiento no es de caballo
        }

        // Nota: Como el caballo salta, ¡no necesitamos recorrer casillas intermedias!
        // Si llegó hasta aquí, el destino está vacío o tiene un enemigo, y la L es correcta.
        return true;
    }
}