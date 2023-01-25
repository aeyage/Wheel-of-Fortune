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
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BoxLayout;

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

    private JLabel player1Label = new JLabel(" ");
    private JLabel player2Label = new JLabel(" ");
    private JLabel guessedWordLabel = new JLabel(" ");
    private JLabel turnLabel = new JLabel("");
    private boolean player1turn = true;
    private int spinValue;

    Player player1 = new Player(1);
    Player player2 = new Player(2);
    Wheel fortuneWheel = new Wheel();

    public Game() 
    {
        setTitle("Guess the Secret Word");
        setSize(650, 300);
        setLayout(new BorderLayout());

        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        String[][] keys = 
        {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {" ","A", "S", "D", "F", "G", "H", "J", "K", "L"," "},
            {" ", " ", "Z", "X", "C", "V", "B", "N", "M"}
        };
        
        JButton[] buttons = new JButton[keys.length*keys[0].length];

        int count = 0;
        for (int i = 0; i < keys.length; i++) 
        { 
            for (int j = 0; j < keys[i].length; j++) 
            {
                buttons[count] = new JButton(keys[i][j]);
                if(keys[i][j] == " ")
                {
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
        
        guessedWordLabel.setText(String.join(" ", guessedWord));
        guessedWordLabel.setHorizontalAlignment(JLabel.CENTER);
        add(guessedWordLabel, BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        player1Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        player2Label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        turnLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        topPanel.add(player1Label);
        topPanel.add(player2Label);
        topPanel.add(turnLabel);

        add(topPanel, BorderLayout.NORTH);
        player1Label.setText("Player 1 :   " + player1.getTotal() + "        ");
        player2Label.setText("Player 2 :   " + player2.getTotal() + "        ");
        turnLabel.setText("Player 1's Turn");
        
        add(keyboardPanel, BorderLayout.SOUTH);
        
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

                fortuneWheel.spin();
                spinValue = fortuneWheel.getWheelValue();

                if(player1turn)
                {   
                    if(spinValue == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Player 1 lose a turn!","Uh Oh!", 
                        JOptionPane.PLAIN_MESSAGE);
                        player1turn = false;
                        turnLabel.setText("Player 2's Turn");
                    } else if(spinValue == -100) 
                    {
                        player1.setTotal(-100);
                        JOptionPane.showMessageDialog(null, "Player 1 hit bankruptcy!","Oh No!", 
                        JOptionPane.PLAIN_MESSAGE);
                        player1turn = false;
                        player1Label.setText("Player 1 : " + player1.getTotal() + "   ");
                        turnLabel.setText("Player 2's Turn");
                    } else 
                    {
                        player1.setTotal(spinValue);
                        player1Label.setText("Player 1 : " + player1.getTotal() + "   ");
                        player1turn = false;

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
        
                } else 
                {
                    if(spinValue == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Player 2 lose a turn!","OH NO!", 
                        JOptionPane.PLAIN_MESSAGE);
                        player1turn = true;
                        turnLabel.setText("Player 1's Turn");
                    } else if(spinValue == -100) 
                    {
                        player2.setTotal(-100);
                        JOptionPane.showMessageDialog(null, "Player 2 hit bankruptcy!","Oh No!", 
                        JOptionPane.PLAIN_MESSAGE);
                        player1turn = true;
                        player2Label.setText("Player 2 : " + player2.getTotal() + "   ");
                        turnLabel.setText("Player 1's Turn");
                    } else 
                    {
                        player2.setTotal(spinValue);
                        player2Label.setText("Player 2 : " + player2.getTotal() + "   ");
                        player1turn = true;

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
                }

            }     
        });

        for (int i = 0; i < buttons.length; i++) 
        {
            buttons[i].addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    if (player1turn) {
                        turnLabel.setText("Player 1's Turn");
                    } else {
                        turnLabel.setText("Player 2's Turn");
                    } 

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
                            guessedWordLabel.setText(String.join(" ", guessedWord));
                            button.setEnabled(false);
                            break;
                        }  
                    }
        
                    if (!isCorrect)
                    {
                        if(!player1turn)
                        {
                            player1.revertTotal(spinValue);
                            player1Label.setText("Player 1 : " + player1.getTotal() + "   ");
                        } else
                        {
                            player2.revertTotal(spinValue);
                            player2Label.setText("Player 2 : " + player2.getTotal() + "   ");
                        }
                    }
                
                    if (Arrays.equals(guessedWord, secretWord)) 
                    {
                        if(player1turn)
                        {
                            JOptionPane.showMessageDialog(null, "Player 1 wins!", "Congratulations!", 
                            JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Player 2 wins!", "Congratulations!", 
                            JOptionPane.PLAIN_MESSAGE);
                        }
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
