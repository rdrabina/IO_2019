package menu;

import constant.Constants;
import game.DisplayGame;
import menu.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameOver extends JFrame implements ActionListener {
    private JLabel titleLabel, scoreLabel;
    private JButton nextButton, quitButton;

    public GameOver(int score, boolean isSuccess){
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Game over");

        if (isSuccess == false ) {
            titleLabel = new JLabel("You Lost");
            titleLabel.setForeground(Color.red);
            titleLabel.setFont(new Font(Constants.FONT, Font.BOLD, 40));
        } else {
            titleLabel = new JLabel("You win!");
            titleLabel.setForeground(Color.green);
            titleLabel.setFont(new Font(Constants.FONT, Font.BOLD, 40));
        }

        scoreLabel = new JLabel("Your score is " + score);
        scoreLabel.setForeground(Color.blue);
        scoreLabel.setFont(new Font(Constants.FONT, Font.BOLD, 20));

        nextButton = new JButton("Continue");
        nextButton.addActionListener(this);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

        titleLabel.setBounds(170, 100, 200, 30);
        scoreLabel.setBounds(175, 150, 200, 30);

        nextButton.setBounds(200,350,100,30);
        quitButton.setBounds(200,400,100,30);

        add(titleLabel);
        add(scoreLabel);
        add(nextButton);
        add(quitButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == nextButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            new Login();
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}
