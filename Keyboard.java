import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Keyboard extends JFrame implements ActionListener 
{
    private String[] secretWord = {"P","O","O","P"};
    private String[] guessedWord = {" "," "," "," "};
    private JLabel guessedWordLabel = new JLabel(" ");

    public Keyboard() 
    {
        setTitle("Guess the Secret Word");
        setSize(600, 300);
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        String[][] keys = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            {"Z", "X", "C", "V", "B", "N", "M"}
            };

        for (int i = 0; i < keys.length; i++) 
        {
            for (int j = 0; j < keys[i].length; j++) 
            {
                JButton button = new JButton(keys[i][j]);
                button.addActionListener(this);
                c.gridx = j;
                c.gridy = i;
                keyboardPanel.add(button, c);
            }
        }

        add(keyboardPanel, "Center");
        add(guessedWordLabel, "North");
        guessedWordLabel.setText("Guessed word: " + guessedWord);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // a close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(500, 250, 75, 25);
        keyboardPanel.add(closeButton);

        closeButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                setVisible(false); // hide the GUI
                dispose(); // release resources used by the GUI
            }
        });
    }

    public void actionPerformed(ActionEvent e) 
    {
        JButton button = (JButton) e.getSource();
        String pressedButton = button.getText();
        boolean isCorrect = false;

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
                button.setBackground(Color.GREEN);
                break;
            }
        }

        if (!isCorrect)
        {
            button.setEnabled(false);
            button.setBackground(Color.RED);
        }

        if (guessedWord.equals("POOP"))
        {
            System.out.println("Congratulations! You guessed the secret word: Poop");
        }
    }
    // public static void main (String [] args) 
	// {
	// 	new Keyboard();
	// }
}
