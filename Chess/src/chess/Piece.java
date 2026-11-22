package chess;

public abstract class Piece {
    //Atributos:
    protected Color color;
    protected Position position;

    public Piece(Color color, Position position){
        this.color = color;
        this.position = position;
    }
    //M´todos
    public Color getColor(){
        return color;
    }
    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    //Métodos para usar con @Override (métodos abstractos)
    public abstract boolean isValidMove(Board board, Position start, Position end);
    public abstract char getSymbol();
}
