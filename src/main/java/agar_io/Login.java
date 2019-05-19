package agar_io;

import constant.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Login extends JFrame implements ActionListener{
    private JLabel titleLabel, nickLabel, facultyLabel;
    private JTextField nickTextField, facultyTextField;
    private JButton nextButton, quitButton;

    public Login(){
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Login");

        titleLabel = new JLabel("Menu");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font(Constants.FONT, Font.BOLD, 20));

        nickLabel = new JLabel("Nick:");
        facultyLabel = new JLabel("Faculty:");

        nickTextField = new JTextField();
        facultyTextField = new JTextField();

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

        titleLabel.setBounds(300, 30, 400, 30);
        nickLabel.setBounds(130, 70, 200, 30);
        facultyLabel.setBounds(130, 110, 200, 30);

        nickTextField.setBounds(300, 70,200,30);
        facultyTextField.setBounds(300, 110, 200, 30);

        nextButton.setBounds(300,200,100,30);
        quitButton.setBounds(300,250,100,30);


        add(titleLabel);
        add(nickLabel);
        add(facultyLabel);
        add(nickTextField);
        add(facultyTextField);
        add(nextButton);
        add(quitButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == nextButton) {
            String nick = nickTextField.getText();
            String faculty = facultyTextField.getText();
            PlayerIdentification playerIdentification = new PlayerIdentification(nick, faculty);
            if (StringUtils.isNotBlank(nick) && StringUtils.isNoneBlank(faculty)) {
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                DisplayGame.displayGame();
            }
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}
