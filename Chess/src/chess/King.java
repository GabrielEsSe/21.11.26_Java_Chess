package chess;

public class King extends Piece {
    
    public King(Color color, Position position){
        super(color, position);
    }   

    @Override 
    public char getSymbol(){
        return (color == Color.white) ? '♚' : '♔';
    }

    @Override 
    public boolean isValidMove(Board board, Position start, Position end){
        
        Piece targetPiece = board.getPosition(end);

        // No puedes capturar a tus propias piezas
        if (targetPiece != null && targetPiece.getColor() == this.color) {
            return false; 
        }

        // Calculamos las distancias recorridas
        int rowDiff = Math.abs(end.row() - start.row());
        int colDiff = Math.abs(end.column() - start.column());

        // El rey se mueve exactamente a cualquiera de sus 8 casillas circundantes (máximo 1 paso)
        boolean isValidStep = (rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff > 0);

        if (!isValidStep) {
            return false; // Se intentó mover más de una casilla o no se movió
        }

        // Como el rey solo se mueve de 1 en 1
        return true;
    }
}