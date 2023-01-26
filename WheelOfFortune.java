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

public class WheelOfFortune extends JFrame implements ActionListener 
{
    // Array containing characters of the secret word in order
    private String[] secretWord = {"T","H","E","  ",
                                    "S","E","C","R","E","T","  ",
                                    "L","I","F","E","  ",
                                    "O","F","  ",
                                    "B","E","E","S"};
    // Array containing empty spaces for users to guess the secret word
    private String[] guessedWord = {"_","_","_","  ",
                                    "_","_","_","_","_","_","  ",
                                    "_","_","_","_","  ",
                                    "_","_","  ",
                                    "_","_","_","_"};

    // Create labels                                
    private JLabel player1Label = new JLabel(" ");
    private JLabel player2Label = new JLabel(" ");
    private JLabel guessedWordLabel = new JLabel(" ");
    private JLabel turnLabel = new JLabel("");


    private boolean player1turn = true;  // Keep track of player's turn
    private int spinValue;               // Store the value from the wheel spin

    // Create objects
    Player player1 = new Player();
    Player player2 = new Player();
    Wheel fortuneWheel = new Wheel();

    public WheelOfFortune() 
    {
        setTitle("Wheel Of Fortune");
        setSize(650, 300);
        setLayout(new BorderLayout());

        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        // Array of keys to be added to keyboard GUI
        String[][] keys = 
        {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {" ","A", "S", "D", "F", "G", "H", "J", "K", "L"," "},
            {" ", " ", "Z", "X", "C", "V", "B", "N", "M"}
        };
                
        // Create an array of JButtons with the same number of elements as the total number of keys
        JButton[] buttons = new JButton[keys.length*keys[0].length];
        
        int count = 0;
        // Iterate through each row of keys
        for (int i = 0; i < keys.length; i++) 
        { 
            // Iterate through each key in the current row
            for (int j = 0; j < keys[i].length; j++) 
            {
                // Create a new JButton with the label of the current key
                buttons[count] = new JButton(keys[i][j]);
                // If the key is a space, set the button to not be visible
                if(keys[i][j] == " ")
                {
                    buttons[count].setVisible(false);
                }
                // Add an ActionListener to the button
                buttons[count].addActionListener(this);
                // Disable the button
                buttons[count].setEnabled(false);
                // Set the button's position in the keyboard panel using a GridBagConstraints object
                c.gridx = j;
                c.gridy = i;
                keyboardPanel.add(buttons[count], c);

                count++;
            }
        }
        
        add(keyboardPanel, BorderLayout.SOUTH);
        
        guessedWordLabel.setHorizontalAlignment(JLabel.CENTER);
        guessedWordLabel.setText(String.join(" ", guessedWord));
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

        // Display Player 1 and Player 2 with their total amount of money
        player1Label.setText("Player 1 :   " + player1.getTotal() + "        ");
        player2Label.setText("Player 2 :   " + player2.getTotal() + "        ");

        // Display to indicate which player's turn 
        turnLabel.setText("Player 1's Turn");
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Create a spin button
        JButton spinButton = new JButton("SPIN");
        spinButton.setBounds(500, 250, 75, 25);
        keyboardPanel.add(spinButton);
        
        // Dynamic array to keep track of pressed keys
        List<String> pressedAlphabets = new ArrayList<>();

        // Add actionListener to spin button
        spinButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {                   
                // Spin the wheel to get a random value
                fortuneWheel.spin();
                spinValue = fortuneWheel.getWheelValue();

                // If it is player 1's turn
                if(player1turn)
                {   
                    // Player 1 lose a turn and cannot guess
                    if(spinValue == 0)
                    {
                        // Display lose turn message
                        JOptionPane.showMessageDialog(null, "Player 1 lose a turn!","Uh Oh!", 
                        JOptionPane.PLAIN_MESSAGE);
                        // Change turn
                        player1turn = false;
                        turnLabel.setText("Player 2's Turn");

                    } 
                    else if(spinValue == -100) // Player 1 bankrupts, lose all money
                    {
                        // Set player 1's total money to zero
                        player1.setTotal(-100);
                        // Display bankruptcy message
                        JOptionPane.showMessageDialog(null, "Player 1 hit bankruptcy!","Oh No!", 
                        JOptionPane.PLAIN_MESSAGE);
                        // Change turn
                        player1turn = false;
                        player1Label.setText("Player 1 : " + player1.getTotal() + "   ");
                        turnLabel.setText("Player 2's Turn");

                    } 
                    else // Player 1 gets an amount of money
                    {
                        // Set player 1 total money
                        player1.setTotal(spinValue);
                        player1Label.setText("Player 1 : " + player1.getTotal() + "   ");
                        //change turn
                        player1turn = false;

                        // Loop to make all the buttons that have been pressed disabled and vice versa
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

                        // Make spin button disabled once pressed
                        spinButton.setEnabled(false);
                    }
                } 
                else // If it is player 2's turn
                {
                    // Player 2 lose a turn and cannot guess
                    if(spinValue == 0)
                    {
                        // Display lose turn message
                        JOptionPane.showMessageDialog(null, "Player 2 lose a turn!","OH NO!", 
                        JOptionPane.PLAIN_MESSAGE);
                        // Change turn
                        player1turn = true;
                        turnLabel.setText("Player 1's Turn");

                    } 
                    else if(spinValue == -100) // Player 2 bankrupts, lose all money
                    {
                        // Set player 1's total money to zero
                        player2.setTotal(-100);
                        // Display bankrupt message
                        JOptionPane.showMessageDialog(null, "Player 2 hit bankruptcy!","Oh No!", 
                        JOptionPane.PLAIN_MESSAGE);
                        // Change turn
                        player1turn = true;
                        player2Label.setText("Player 2 : " + player2.getTotal() + "   ");
                        turnLabel.setText("Player 1's Turn");

                    } 
                    else // Player 2 gets an amount of money
                    {
                        // Set player 2 total money
                        player2.setTotal(spinValue);
                        player2Label.setText("Player 2 : " + player2.getTotal() + "   ");
                        // Change turn
                        player1turn = true;

                        // Loop to make all the buttons that have been pressed disabled and vice versa
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
                        // Disable spin button once pressed
                        spinButton.setEnabled(false);
                    }
                }

            }     
        });

        // Loop to add actionListener to each buttons
        for (int i = 0; i < buttons.length; i++) 
        {
            buttons[i].addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    // Set the turnLabel text according to current player's turn
                    if (player1turn) 
                    {
                        turnLabel.setText("Player 1's Turn");
                    } 
                    else 
                    {
                        turnLabel.setText("Player 2's Turn");
                    } 

                    JButton button = (JButton) e.getSource();  // Determine which button is pressed
                    String pressedButton = button.getText();   // Store the character held by the pressed button
                    boolean isCorrect = false;                 // Indicate if guess is correct
                    
                    pressedAlphabets.add(pressedButton);       // Add the character to pressedButton array

                    // Loop to check whether the secret word contains the guessed character
                    for (int i = 0; i < secretWord.length; i++)
                    {
                        // If the guess is correct
                        if (pressedButton == secretWord[i])
                        {
                            isCorrect = true;

                            // Add the correct guessed character to guessedWord array at it's true position
                            for (int j = 0; j < secretWord.length; j++)
                            {
                                if (pressedButton == secretWord[j])
                                {
                                    guessedWord[j] = pressedButton;
                                } 
                            }
                            // Reset the guessedWordLabel to include correct guesses
                            guessedWordLabel.setText(String.join(" ", guessedWord));

                            // Disable the buttons once one of them is pressed
                            button.setEnabled(false);
                            break;
                        }  
                    }
                    
                    // Player only get to keep money from spin from current turn if correct guess 
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
                    
                    // Determine whether all characters has been guessed correctly
                    if (Arrays.equals(guessedWord, secretWord)) 
                    {
                        // Check the winner, last player to complete the word wins
                        if(player1turn)
                        {
                            JOptionPane.showMessageDialog(null, "Player 1 wins!", "Congratulations!", 
                            JOptionPane.PLAIN_MESSAGE);
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Player 2 wins!", "Congratulations!", 
                            JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    
                    // Loop to set disable all buttons once one of them is pressed
                    for (int j = 0; j < buttons.length; j++)
                    {
                        buttons[j].setEnabled(false);  
                    }
                    // Enable spin button once guess has been made
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
		new WheelOfFortune();
	}
    
}
