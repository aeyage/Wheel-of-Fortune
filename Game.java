import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame implements ActionListener 
{
    private String[] secretWord = {"T","H","E","  ",
                                    "S","E","C","R","E","T","  ",
                                    "L","I","F","E","  ",
                                    "O","F","  ",
                                    "B","E","E","S"};
    private String[] guessedWord = {"_","_","_","  ",
                                    "_","_","_","_","_","_","  ",
                                    "_","_","_","_","  ",
                                    "_","_","  ",
                                    "_","_","_","_"};

    private JLabel guessedWordLabel = new JLabel(" ");

    public Game() 
    {
        setTitle("Guess the Secret Word");
        setSize(650, 300);
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        String[][] keys = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {" ","A", "S", "D", "F", "G", "H", "J", "K", "L"," "},
            {" ", " ", "Z", "X", "C", "V", "B", "N", "M"}
            };
        
        JButton[] buttons = new JButton[keys.length*keys[0].length];

        int count = 0;
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[i].length; j++) {
                buttons[count] = new JButton(keys[i][j]);
                if(keys[i][j] == " "){
                   buttons[count].setVisible(false);
                }
                buttons[count].addActionListener(this);
                buttons[count].setEnabled(false);
                c.gridx = j;
                c.gridy = i;
                keyboardPanel.add(buttons[count], c);
                count++;
            }
        }

        add(keyboardPanel, "Center");
        add(guessedWordLabel, "North");
        guessedWordLabel.setText("Guessed word: " + String.join(" ", guessedWord));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JButton spinButton = new JButton("SPIN");
        spinButton.setBounds(500, 250, 75, 25);
        keyboardPanel.add(spinButton);
        
        List<String> pressedAlphabets = new ArrayList<>();

        spinButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                // for (int j = 0; j < secretWord.length; j++) 
                // {
                //     if (!guessedWord[j].equals("  ") || !guessedWord[j].equals("_"))
                //     {
                //         pressedAlphabets.add(guessedWord[j]);
                //     }
                // }

                for (int j = 0; j < buttons.length; j++) 
                {
                    boolean isDisabled = false;
                    for(int i = 0; i < pressedAlphabets.size(); i++)
                    {
                        if(buttons[j].getText().equals(pressedAlphabets.get(i)))
                        {
                            isDisabled = true;
                            break;
                        }
                    }
                    buttons[j].setEnabled(!isDisabled);
                }
                spinButton.setEnabled(false);
            }     
        });

        for (int i = 0; i < buttons.length; i++) 
        {
            buttons[i].addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    JButton button = (JButton) e.getSource();
                    String pressedButton = button.getText();
                    boolean isCorrect = false;
                    
                    pressedAlphabets.add(pressedButton);

                    for (int i = 0; i < secretWord.length; i++)
                    {
                        if (pressedButton == secretWord[i])
                        {
                            isCorrect = true;
                            for (int j = 0; j < secretWord.length; j++)
                            {
                                if (pressedButton == secretWord[j])
                                {
                                    guessedWord[j] = pressedButton;
                                } 
                            }
                            guessedWordLabel.setText("Guessed word: " + String.join(" ", guessedWord));
                            button.setEnabled(false);
                            break;
                        }  
                    }
        
                    if (!isCorrect)
                    {
                        button.setEnabled(false);
                    }
                
                    if (Arrays.equals(guessedWord, secretWord)) {
                        JOptionPane.showMessageDialog(null, "Congratulations!", "You win!", JOptionPane.PLAIN_MESSAGE);
                    }
                    
                    for (int j = 0; j < buttons.length; j++)
                    {
                        buttons[j].setEnabled(false);  
                    }
                    spinButton.setEnabled(true); 
                } 
            });
        }
    }

    public void actionPerformed(ActionEvent e) 
    {

    }

    public static void main (String [] args) 
	{
		new Game();
	}
    
}


   // a close button
        // JButton closeButton = new JButton("Close");
        // closeButton.setBounds(500, 250, 75, 25);
        // keyboardPanel.add(closeButton);

        // closeButton.addActionListener(new ActionListener() 
        // {
        //     public void actionPerformed(ActionEvent e) 
        //     {
        //         setVisible(false); // hide the GUI
        //         dispose(); // release resources used by the GUI
        //     }
        // });
