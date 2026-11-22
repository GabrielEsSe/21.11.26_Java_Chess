package chess;

public class Queen extends Piece {
    
    public Queen(Color color, Position position){
        super(color, position);
    }   

    @Override 
    public char getSymbol(){
        return (color == Color.white) ? '♛' : '♕';
    }

    @Override 
    public boolean isValidMove(Board board, Position start, Position end){
        
        Piece targetPiece = board.getPosition(end);

        // No puedes capturar a tus propias piezas
        if (targetPiece != null && targetPiece.getColor() == this.color) {
            return false; 
        }

        int rowDiff = Math.abs(end.row() - start.row());
        int colDiff = Math.abs(end.column() - start.column());

        // La reina se mueve si es un movimiento recto (como Torre) O una diagonal perfecta (como Alfil)
        boolean isStraight = (end.row() == start.row() || end.column() == start.column());
        boolean isDiagonal = (rowDiff == colDiff);

        if (!isStraight && !isDiagonal) {
            return false; // Si no es ni recto ni diagonal, el movimiento es inválido
        }

        // ==========================================
        // ESCANEO DE CASILLAS INTERMEDIAS (Reutilizando la lógica de Torre/Alfil)
        // ==========================================
        int rowStep = (end.row() == start.row()) ? 0 : (end.row() > start.row() ? 1 : -1);
        int colStep = (end.column() == start.column()) ? 0 : (end.column() > start.column() ? 1 : -1);

        int currentRow = start.row() + rowStep;
        int currentCol = start.column() + colStep;

        while (currentRow != end.row() || currentCol != end.column()) {
            Position auxPosition = new Position(currentRow, currentCol);
            
            if (board.getPosition(auxPosition) != null) {
                return false; // Hay una pieza bloqueando el paso
            }

            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }
}