import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {                     // Metoda która generuje losowe sudoku
        SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
        SudokuPuzzle copy = new SudokuPuzzle(puzzle);

        Random randomGenerator = new Random();                                                   //Deklarowanie obiektów

        List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues()));
        for(int r = 0;r < copy.getNumRows();r++) {
            int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
            copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
            notUsedValidValues.remove(randomValue);
        }

        backtrackSudokuSolver(0, 0, copy);

        int numberOfValuesToKeep = (int)(0.22222*(copy.getNumRows()*copy.getNumRows()));

        for(int i = 0;i < numberOfValuesToKeep;) {
            int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
            int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());

            if(puzzle.isSlotAvailable(randomRow, randomColumn)) {
                puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
                i++;
            }
        }

        return puzzle;                                                                           //Zwraca puzzle który jest obiektem SudokuPuzzleType
    }

    private boolean backtrackSudokuSolver(int r,int c,SudokuPuzzle puzzle) {
        if(!puzzle.inRange(r,c)) {                                                               //Jeżeli ruch jest niepoprawny zwraca fałsz
            return false;
        }
        if(puzzle.isSlotAvailable(r, c)) {                                                       //Jeśli pole jest puste
            for(int i = 0;i < puzzle.getValidValues().length;i++) {                              //Pętla sprawdzająca poprawną wartość dla pola
                                                                                                 //Jeżeli można wprowadzić nuemer do pola
                if(!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c,puzzle.getValidValues()[i]) && !puzzle.numInBox(r,c,puzzle.getValidValues()[i])) {
                    puzzle.makeMove(r, c, puzzle.getValidValues()[i], true);             // Jeżeli jest poprawne wstaw
                    if(puzzle.boardFull()) {                                                     //Jeśli rozwiązane zwróć true
                        return true;
                    }
                    if(r == puzzle.getNumRows() - 1) {                                           //Idź do następnego ruchu
                        if(backtrackSudokuSolver(0,c + 1,puzzle)) return true;
                    } else {
                        if(backtrackSudokuSolver(r + 1,c,puzzle)) return true;
                    }
                }
            }
        }
        else {                                                                                    //Jeśli pole nie jest puste
            if(r == puzzle.getNumRows() - 1) {
                return backtrackSudokuSolver(0,c + 1,puzzle);
            } else {
                return backtrackSudokuSolver(r + 1,c,puzzle);
            }
        }
        puzzle.makeSlotEmpty(r, c);                                                               // Cofnij ruch
        return false;
    }
}