package menu;

import constant.Constants;
import game.DisplayGame;
import org.apache.commons.lang3.StringUtils;
import player.PlayerIdentification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.stream.Stream;

public class Login extends JFrame implements ActionListener{
    private JLabel titleLabel, nickLabel, collegeLabel, errorLabel;
    private JTextField nickTextField;
    private JButton nextButton, quitButton;
    private JComboBox collegesBox;

    public Login(){
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Login");

        titleLabel = new JLabel("Menu");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font(Constants.FONT, Font.BOLD, 20));

        nickLabel = new JLabel("Nick:");
        collegeLabel = new JLabel("College:");
        errorLabel = new JLabel("You must specify nickname!");

        nickTextField = new JTextField();

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

        collegesBox = new JComboBox<>(Constants.COLLEGES);
        collegesBox.addActionListener(this);

        titleLabel.setBounds(300, 30, 400, 30);
        nickLabel.setBounds(130, 70, 200, 30);
        collegeLabel.setBounds(130, 110, 200, 30);
        errorLabel.setBounds(200, 150, 300, 30);
        errorLabel.setForeground(Color.RED);
        setLabelVisible(errorLabel, false);

        nickTextField.setBounds(300, 70,200,30);

        collegesBox.setBounds(300, 110, 200, 30);

        nextButton.setBounds(300,200,100,30);
        quitButton.setBounds(300,250,100,30);


        add(titleLabel);
        add(nickLabel);
        add(collegeLabel);
        add(errorLabel);
        add(nickTextField);
        add(nextButton);
        add(quitButton);
        add(collegesBox);
        setVisible(true);
    }

    private void setLabelVisible(JLabel label, boolean visible) {
        label.setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == nextButton) {
            String nick = nickTextField.getText();
            String selectedItem = (String) collegesBox.getSelectedItem();
            College college = College.resolveFaculty(selectedItem);
            PlayerIdentification playerIdentification = new PlayerIdentification(nick, college);
            handleInput(playerIdentification);
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }

    private void handleInput(PlayerIdentification playerIdentification) {
        if (isIdentificationValid(playerIdentification)) {
            setLabelVisible(errorLabel, false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            DisplayGame.displayGame(playerIdentification);
        } else {
            setLabelVisible(errorLabel, true);
        }
    }

    private boolean isIdentificationValid(PlayerIdentification playerIdentification) {
        return Stream.of(playerIdentification.getNick(), playerIdentification.getCollege().name())
                .allMatch(StringUtils::isNotBlank);
    }
}
