package chess;

class Board {
    //Arreglo bidimencional de objetos (Piezas de ajedrez).
    private Piece[][] grid = new Piece[8][8]; //Arreglo de 8x8

    //Constructor para ejecutar en el Heap reservado.  
    public Board(){
        renderBoard();
    }

    public void renderBoard(){
        //Rederizamos piezas negras.
        grid[0][0] = new Rook(Color.black, new Position(0, 0));
        grid[0][1] = new Knight(Color.black, new Position(0, 1));
        grid[0][2] = new Bishop(Color.black, new Position(0, 2));
        grid[0][3] = new Queen(Color.black, new Position(0, 3));
        grid[0][4] = new King(Color.black, new Position(0, 4));
        grid[0][5] = new Bishop(Color.black, new Position(0, 5));
        grid[0][6] = new Knight(Color.black, new Position(0, 6));
        grid[0][7] = new Rook(Color.black, new Position(0, 7));
        //Faltan los peones 
        for(int j = 0; j < 8; j++){
            grid[1][j] = new Pawn(Color.black, new Position(1, j));
        }
        //Y cargamos la parte vacia entre black y white
        for (int i = 2; i <= 5; i++){
            for(int j = 0; j < 8; j++){
                grid[i][j] = null; 
            }
        }
        //Ahora las blancas 
        for(int j = 0; j < 8; j++){
            grid[6][j] = new Pawn(Color.white, new Position(1, j));
        }
        //Rederizamos piezas blancas.
        grid[7][0] = new Rook(Color.white, new Position(7, 0));
        grid[7][1] = new Knight(Color.white, new Position(7, 1));
        grid[7][2] = new Bishop(Color.white, new Position(7, 2));
        grid[7][3] = new Queen(Color.white, new Position(7, 3));
        grid[7][4] = new King(Color.white, new Position(7, 4));
        grid[7][5] = new Bishop(Color.white, new Position(7, 5));
        grid[7][6] = new Knight(Color.white, new Position(7, 6));
        grid[7][7] = new Rook(Color.white, new Position(7, 7));
    }
    //get Positon 
    public Piece getPosition(Position position){
        if(!position.isValid()){ return null; }
        return grid[position.row()][position.column()];
    }
    //set Position
    public void setPosition(Position position, Piece piece){
        if(position.isValid()){
            grid[position.row()][position.column()] = piece;
            if(piece != null){
                piece.setPosition(position);
            }
        }
    }

    //Imprimimos gradilla
    public void printBoard(){
        System.out.println("   a b c d e f g h");
        System.out.println("   ________________");
        for(int j = 0; j < 8; j++){
            System.out.print((8 - j) + " |");
            for(int i = 0; i < 8; i++){
                if(grid[j][i] == null){
                    System.out.print(". ");
                } else { System.out.print(grid[j][i].getSymbol() + " "); }
            }
            System.out.println("| " + (8 - j));
        }
        System.out.println("   ________________");
        System.out.println("   a b c d e f g h");
    }

}