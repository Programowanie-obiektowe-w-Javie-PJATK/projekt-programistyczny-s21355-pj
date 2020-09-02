public enum SudokuPuzzleType {
    SIXBYSIX(6,6,3,2,new String[] {"1","2","3","4","5","6"},"6 By 6 Game"),
    NINEBYNINE(9,9,3,3,new String[] {"1","2","3","4","5","6","7","8","9"},"9 By 9 Game"),
    TWELVEBYTWELVE(12,12,4,3,new String[] {"1","2","3","4","5","6","7","8","9","A","B","C"},"12 By 12 Game"),
    SIXTEENBYSIXTEEN(16,16,4,4,new String[] {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"},"16 By 16 Game");

    private final int rows;
    private final int columns;
    private final int boxWidth;
    private final int boxHeight;
    private final String [] validValues;
    private final String desc;

    private SudokuPuzzleType(int rows,int columns,int boxWidth,int boxHeight,String [] validValues,String desc) {
        this.rows = rows;
        this.columns = columns;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.validValues = validValues;
        this.desc = desc;
    }

    public int getRows() {                  // Metoda zwraca rzędy
        return rows;
    }

    public int getColumns() {               // Metoda zwraca kolumny
        return columns;
    }

    public int getBoxWidth() {              // Metoda zwraca szerokość planszy
        return boxWidth;
    }

    public int getBoxHeight() {             // Metoda zwraca wysokość planszy
        return boxHeight;
    }

    public String [] getValidValues() {     // Metoda zwraca pasujące wartości
        return validValues;
    }

    public String toString() {              // Metoda zwraca stringa desc
        return desc;
    }
}