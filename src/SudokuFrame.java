import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SudokuFrame extends JFrame {

    private JPanel buttonSelectionPanel;
    private SudokuPanel sPanel;

    public SudokuFrame() {                                                                       //Konstruktor
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(800,600));

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Game");
        JMenu newGame = new JMenu("New Game");
        JMenuItem sixBySixGame = new JMenuItem("6 By 6 Game");
        sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX,30));
        JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
        nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE,26));
        JMenuItem twelveByTwelveGame = new JMenuItem("12 By 12 Game");
        twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE,20));

        newGame.add(sixBySixGame);
        newGame.add(nineByNineGame);                                                             // Metody obiektu JMenu
        newGame.add(twelveByTwelveGame);

        file.add(newGame);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());                                                 //Deklaracji zmiennych i obiektów
        windowPanel.setPreferredSize(new Dimension(800,600));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

        sPanel = new SudokuPanel();

        windowPanel.add(sPanel);                                                                 //Metody obiektu Jpanel
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26);
    }
    public void rebuildInterface(SudokuPuzzleType puzzleType,int fontSize) {                     //Stwórz menu
        SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        buttonSelectionPanel.removeAll();
        for(String value : generatedPuzzle.getValidValues()) {                                   //Petla for each która generuje puzle
            JButton b = new JButton(value);
            b.setPreferredSize(new Dimension(45,45));
            b.addActionListener(sPanel.new NumActionListener());
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
    }

    private class NewGameListener implements ActionListener {                                    // Ta klasa odpowiada za input

        private SudokuPuzzleType puzzleType;
        private int fontSize;

        public NewGameListener(SudokuPuzzleType puzzleType,int fontSize) {                      //Ta metoda czyta puzzleType i fontSize
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
        }

        public void actionPerformed(ActionEvent e) {                                            // Metoda z intefejsu ActionListener
            rebuildInterface(puzzleType,fontSize);
        }
    }

    public static void main(String[] args) {                                                    // Metoda Runnable
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SudokuFrame frame = new SudokuFrame();
                frame.setVisible(true);
            }
        });
    }
}
