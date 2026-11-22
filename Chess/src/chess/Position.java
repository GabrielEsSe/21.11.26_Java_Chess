package chess;

// Usamos un "record" de Java: una estructura inmutable perfecta para almacenar datos simples como coordenadas (row, column)
public record Position(int row, int column) {
    
    // ==========================================
    // 1. VALIDADOR DE LÍMITES
    // ==========================================
    // Define método que valida coordenadas dentro del tablero 8x8
    public boolean isValid(){
        // Comprueba que la fila y la columna estén estrictamente entre 0 y 7 (inclusive)
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }

    // ==========================================
    // 2. TRADUCTOR ALGEBRAICO (ASCII & MATRICES)
    // ==========================================
    // Convierte texto de ajedrez real (ej: "e2") en índices numéricos de matriz (fila, columna)
    public static Position translate(String coords){
        // Seguridad: Si el texto es nulo o no mide exactamente 2 caracteres, devolvemos una posición inválida (-1, -1)
        if(coords == null || coords.length() != 2){
            return new Position(-1, -1); //Asignamos valores centinela. 
        }
        
        // Extraemos la letra (columna, ej: 'e') y la pasamos a minúscula por seguridad
        char letter = Character.toLowerCase(coords.charAt(0));
        // Extraemos el número (fila, ej: '2')
        char num = coords.charAt(1);

        // Restar la letra 'a' (valor 97) da la columna exacta (ej: 'e' [101] - 'a' [97] = 4)
        int column = letter - 'a';
        
        // INVERSIÓN DE FILAS: En el ajedrez el '1' está abajo y el '8' arriba, 
        // pero en la matriz de Java la fila '0' está arriba y la '7' abajo. Restamos a 8.
        int row = 8 - Character.getNumericValue(num);

        // Retornamos un nuevo objeto Position empaquetado con la fila y columna calculadas
        return new Position(row, column);
    }
}