public class SudokuPuzzle {

    protected String [][] board;
    protected boolean [][] mutable;
    private final int ROWS;
    private final int COLUMNS;                                                                          // Deklaracja zmiennych i obiektów
    private final int BOXWIDTH;
    private final int BOXHEIGHT;
    private final String [] VALIDVALUES;

    public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {       //Konstruktor który przypisuje wartości zmiennym
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOXWIDTH = boxWidth;
        this.BOXHEIGHT = boxHeight;
        this.VALIDVALUES = validValues;
        this.board = new String[ROWS][COLUMNS];
        this.mutable = new boolean[ROWS][COLUMNS];
        initializeBoard();
        initializeMutableSlots();
    }

    public SudokuPuzzle(SudokuPuzzle puzzle) {                                                          //Konstruktor uzupełnia tablice mutable
        this.ROWS = puzzle.ROWS;
        this.COLUMNS = puzzle.COLUMNS;
        this.BOXWIDTH = puzzle.BOXWIDTH;
        this.BOXHEIGHT = puzzle.BOXHEIGHT;
        this.VALIDVALUES = puzzle.VALIDVALUES;
        this.board = new String[ROWS][COLUMNS];
        for(int r = 0;r < ROWS;r++) {
            for(int c = 0;c < COLUMNS;c++) {
                board[r][c] = puzzle.board[r][c];
            }
        }
        this.mutable = new boolean[ROWS][COLUMNS];
        for(int r = 0;r < ROWS;r++) {
            for(int c = 0;c < COLUMNS;c++) {
                this.mutable[r][c] = puzzle.mutable[r][c];
            }
        }
    }

    public int getNumRows() {                                                                           // Metoda zwraca rzędy
        return this.ROWS;
    }

    public int getNumColumns() {                                                                        // Metoda zwraca kolumny
        return this.COLUMNS;
    }

    public int getBoxWidth() {                                                                          // Metoda zwraca szerokość ramki
        return this.BOXWIDTH;
    }

    public int getBoxHeight() {                                                                         // Metoda zwraca wysokość ramki
        return this.BOXHEIGHT;
    }

    public String [] getValidValues() {                                                                 // Metoda zwraca string VALIDVALUES
        return this.VALIDVALUES;
    }

    public void makeMove(int row,int col,String value,boolean isMutable) {                              // Metoda do poruszania się
        if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
            this.board[row][col] = value;
            this.mutable[row][col] = isMutable;
        }
    }

    public boolean isValidMove(int row,int col,String value) {                                          // Metoda sprawdza czy ruch jest możliwy
        if(this.inRange(row,col)) {
            if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
                return true;
            }
        }
        return false;
    }

    public boolean numInCol(int col,String value) {                                                     //Metoda sprawdzająca czy jest liczba w kolumnie
        if(col <= this.COLUMNS) {
            for(int row=0;row < this.ROWS;row++) {
                if(this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInRow(int row,String value) {                                                      //Metoda sprawdzająca czy jest liczba w rzędzie
        if(row <= this.ROWS) {
            for(int col=0;col < this.COLUMNS;col++) {
                if(this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInBox(int row,int col,String value) {                                             // Sprawdza cyfre w konkretnym kwardraciku
        if(this.inRange(row, col)) {
            int boxRow = row / this.BOXHEIGHT;
            int boxCol = col / this.BOXWIDTH;

            int startingRow = (boxRow*this.BOXHEIGHT);
            int startingCol = (boxCol*this.BOXWIDTH);

            for(int r = startingRow;r <= (startingRow+this.BOXHEIGHT)-1;r++) {
                for(int c = startingCol;c <= (startingCol+this.BOXWIDTH)-1;c++) {
                    if(this.board[r][c].equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSlotAvailable(int row,int col) {                                                     // Metoda sprawdza czy jest możliwe wypełnienie kwadraciku
        return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
    }

    public boolean isSlotMutable(int row,int col) {                                                       // Metoda sprawdzajaca czy ejst wiecej możliwości
        return this.mutable[row][col];                                                                    // na jeden kwadracik
    }

    public String getValue(int row,int col) {                                                             //Metoda zwraca aktualną wartość
        if(this.inRange(row,col)) {
            return this.board[row][col];
        }
        return "";
    }

    public String [][] getBoard() {                                                                        // Metoda zwraca z planszy
        return this.board;
    }

    private boolean isValidValue(String value) {                                                            // Metoda sprawdza czy wartość jest możliwa
        for(String str : this.VALIDVALUES) {
            if(str.equals(value)) return true;
        }
        return false;
    }

    public boolean inRange(int row,int col) {                                                              // Metoda zwraca zasięg
        return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
    }

    public boolean boardFull() {                                                                           // Metoda zwraca True jeśli skończyłeś lub False jeśli
        for(int r = 0;r < this.ROWS;r++) {                                                                 //można coś jeszcze wypełnić
            for(int c = 0;c < this.COLUMNS;c++) {
                if(this.board[r][c].equals("")) return false;
            }
        }
        return true;
    }

    public void makeSlotEmpty(int row,int col) {                                                            //Czyszczenie kwadraciku
        this.board[row][col] = "";
    }

    @Override
    public String toString() {                                                                              // Metoda wbudowana tuString
        String str = "Game Board:\n";
        for(int row=0;row < this.ROWS;row++) {
            for(int col=0;col < this.COLUMNS;col++) {
                str += this.board[row][col] + " ";
            }
            str += "\n";
        }
        return str+"\n";
    }

    private void initializeBoard() {                                                                         // Metoda inicjuje plansze
        for(int row = 0;row < this.ROWS;row++) {
            for(int col = 0;col < this.COLUMNS;col++) {
                this.board[row][col] = "";
            }
        }
    }

    private void initializeMutableSlots() {                                                                  // Metoda inicjuje zmienne sloty
        for(int row = 0;row < this.ROWS;row++) {
            for(int col = 0;col < this.COLUMNS;col++) {
                this.mutable[row][col] = true;
            }
        }
    }
}