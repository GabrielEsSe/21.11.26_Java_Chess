package chess;

public class Bishop extends Piece {
    
    public Bishop(Color color, Position position){
        super(color, position);
    }   

    @Override 
    public char getSymbol(){
        return (color == Color.white) ? '♝' : '♗';
    }

    @Override 
    public boolean isValidMove(Board board, Position start, Position end){
        
        Piece targetPiece = board.getPosition(end);

        // No puedes capturar a tus propias piezas
        if (targetPiece != null && targetPiece.getColor() == this.color) {
            return false; 
        }

        // REGLA GEOMÉTRICA: El alfil solo se mueve en diagonal.
        // La distancia horizontal (columnas) debe ser igual a la distancia vertical (filas).
        int rowDiff = Math.abs(end.row() - start.row());
        int colDiff = Math.abs(end.column() - start.column());

        if (rowDiff != colDiff) {
            return false; // Si no es una diagonal exacta, el movimiento es inválido.
        }

        // ==========================================
        // ESCANEO DE CASILLAS INTERMEDIAS EN DIAGONAL
        // ==========================================
        // Determinamos la dirección del paso (-1 o +1) tanto para filas como para columnas
        int rowStep = (end.row() > start.row()) ? 1 : -1;
        int colStep = (end.column() > start.column()) ? 1 : -1;

        int currentRow = start.row() + rowStep;
        int currentCol = start.column() + colStep;

        // Recorremos casilla por casilla el camino intermedio hasta llegar justo antes de la casilla 'end'
        while (currentRow != end.row() && currentCol != end.column()) {
            Position auxPosition = new Position(currentRow, currentCol);
            
            // Si hay alguna pieza estorbando en medio del camino diagonal, bloqueamos el movimiento
            if (board.getPosition(auxPosition) != null) {
                return false;
            }

            // Avanzamos al siguiente paso en la dirección diagonal
            currentRow += rowStep;
            currentCol += colStep;
        }

        // Si el camino diagonal está despejado, el movimiento es válido (vacío o captura enemiga)
        return true;
    }
}