package chess;

public class Pawn extends Piece {
    
    public Pawn(Color color, Position position){
        super(color, position);
    }

    @Override 
    public char getSymbol(){
        return (color == Color.white) ? '♟' : '♙';
    }

    @Override 
    public boolean isValidMove(Board board, Position start, Position end){

        Piece targetPiece = board.getPosition(end);
        
        // 1. Validar que no captures a tus propias piezas
        if (targetPiece != null && targetPiece.getColor() == this.color) {
            return false; 
        }

        // =================================================================
        // MOVIMIENTOS PARA LAS PIEZAS BLANCAS (Suben filas, de la 6 hacia la 0)
        // =================================================================
        if (this.color == Color.white) {
            
            // --- CASO A: Avance simple de 1 sola casilla hacia adelante (Recto) ---
            if (end.column() == start.column() && end.row() == start.row() - 1) {
                return targetPiece == null; 
            }
            
            // --- CASO B: Avance doble de 2 casillas desde la fila de origen (6) ---
            if (end.column() == start.column() && start.row() == 6 && end.row() == start.row() - 2) {
                Position intermediate = new Position(5, start.column()); 
                return targetPiece == null && board.getPosition(intermediate) == null;
            }

            // --- CASO C: Captura en diagonal para blancas ---
            // Se mueve una columna a la izquierda o derecha (diferencia de 1) 
            // y sube exactamente una fila (start.row() - 1). 
            // OBLIGATORIO: La casilla de destino debe tener una pieza enemiga (targetPiece != null).
            if (Math.abs(end.column() - start.column()) == 1 && end.row() == start.row() - 1) {
                return targetPiece != null && targetPiece.getColor() != this.color;
            }
        }

        // =================================================================
        // MOVIMIENTOS PARA LAS PIEZAS NEGRAS (Bajan filas, de la 1 hacia la 7)
        // =================================================================
        if (this.color == Color.black) {
            
            // --- CASO A: Avance simple de 1 sola casilla hacia adelante (Recto) ---
            if (end.column() == start.column() && end.row() == start.row() + 1) {
                return targetPiece == null;
            }
            
            // --- CASO B: Avance doble de 2 casillas desde la fila de origen (1) ---
            if (end.column() == start.column() && start.row() == 1 && end.row() == start.row() + 2) {
                Position intermediate = new Position(2, start.column());
                return targetPiece == null && board.getPosition(intermediate) == null;
            }

            // --- CASO C: Captura en diagonal para negras ---
            // Se mueve una columna a los lados y baja una fila (start.row() + 1).
            // OBLIGATORIO: Debe haber una pieza enemiga en el destino.
            if (Math.abs(end.column() - start.column()) == 1 && end.row() == start.row() + 1) {
                return targetPiece != null && targetPiece.getColor() != this.color;
            }
        }

        // Si no cumple ninguna regla de movimiento válido para el peón, devolvemos falso.
        return false;
    }
}