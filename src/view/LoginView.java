package view;

import factories.LogeableFactory;
import interfaces.Logeable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import classes.MyException;
import classes.Professor;
import classes.Trainer;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class LoginView extends JFrame implements ActionListener, FocusListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton goBackButton;
	private JButton continueButton;
	private JLabel errorMessage;
	private Logeable logeable = LogeableFactory.getLogeable();
	private JButton btnShowPasswd;

	/*
	 * Here is the main View's structure.
	 * Because of that this View is a JFrame and the program always has to start on
	 * this page
	 */
	public LoginView() {
		setResizable(false);

		setTitle("Log in to PokeCatch");
		// This sets the little icon of the view
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginView.class.getResource("/resources/descarga.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 607);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel pokeCatch = new JLabel("");
		pokeCatch.setIcon(new ImageIcon(
				LoginView.class.getResource("/resources/PokeCatch-56d0c8a0589d01eea221bdf040d9a9cc.png")));
		pokeCatch.setBounds(143, 12, 744, 139);
		contentPane.add(pokeCatch);

		JLabel welcomeMenssage = new JLabel("Welcome back to PokeCatch !!");
		welcomeMenssage.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeMenssage.setFont(new Font("Noto Sans CJK HK", Font.PLAIN, 24));
		welcomeMenssage.setBounds(343, 154, 373, 115);
		contentPane.add(welcomeMenssage);

		// Here we have the two field where the user will write
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		usernameField.setFont(new Font("Noto Sans CJK HK", Font.PLAIN, 20));
		usernameField.setForeground(new Color(0, 0, 0));
		usernameField.setBackground(new Color(255, 255, 255));
		usernameField.setBounds(377, 281, 290, 42);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		usernameField.addFocusListener(this);
		usernameField.addKeyListener(this);

		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		passwordField.setFont(new Font("Noto Sans CJK HK", Font.BOLD, 20));
		passwordField.setBackground(new Color(255, 255, 255));
		passwordField.setBounds(377, 360, 290, 42);
		contentPane.add(passwordField);
		passwordField.setEchoChar('*');
		passwordField.addFocusListener(this);
		passwordField.addKeyListener(this);
		
		
		// This are the two buttons one that continues and the other that disposes the
		// view
		continueButton = new JButton("");
		continueButton.setEnabled(false);
		continueButton.setForeground(new Color(255, 255, 255));
		continueButton.setOpaque(false);
		continueButton.setBackground(new Color(255, 255, 255));
		continueButton.setBorder(null);
		continueButton.setIcon(new ImageIcon(LoginView.class.getResource("/resources/Continue.png")));
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		continueButton.setBounds(215, 427, 200, 100);
		continueButton.addActionListener(this);
		contentPane.add(continueButton);

		goBackButton = new JButton("");
		goBackButton.setOpaque(false);
		goBackButton.setBorder(null);
		goBackButton.setBackground(new Color(255, 255, 255));
		goBackButton.setIcon(new ImageIcon(LoginView.class.getResource("/resources/Exit.png")));
		goBackButton.setBounds(600, 427, 200, 100);
		contentPane.add(goBackButton);
		goBackButton.addActionListener(this);
		
		btnShowPasswd = new JButton("");
		btnShowPasswd.setIcon(new ImageIcon(LoginView.class.getResource("/resources/ojo-removebg-preview (1).png")));
		btnShowPasswd.setBounds(704, 375, 85, 21);
		contentPane.add(btnShowPasswd);
		btnShowPasswd.addActionListener(this);

		JLabel usernameLabel = new JLabel("USERNAME");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setFont(new Font("Noto Sans CJK HK", Font.BOLD, 20));
		usernameLabel.setBounds(417, 247, 206, 28);
		contentPane.add(usernameLabel);

		JLabel passwordLabel = new JLabel("PASSWORD");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setFont(new Font("Noto Sans CJK HK", Font.BOLD, 20));
		passwordLabel.setBounds(417, 325, 211, 28);
		contentPane.add(passwordLabel);

		// This text is hidden but it will appear if the parameters added are not
		// correct
		errorMessage = new JLabel("Username or password do not belong to a trainer");
		errorMessage.setForeground(new Color(255, 0, 0));
		errorMessage.setFont(new Font("Noto Sans CJK HK", Font.PLAIN, 15));
		errorMessage.setBounds(355, 410, 357, 35);
		contentPane.add(errorMessage);
		
		
		
		JLabel lbl_Background = new JLabel("");
		lbl_Background.setIcon(new ImageIcon(LoginView.class.getResource("/resources/logginBackground.jpg")));
		lbl_Background.setBounds(-27, -12, 1107, 604);
		contentPane.add(lbl_Background);
		errorMessage.setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Trainer t = null;

		if (e.getSource().equals(goBackButton)) {
			// Confirm option when the GO BACK button is pressed
			if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose();
			} else {
				
			}
		 
			}
		if (e.getSource().equals(continueButton)) {
			/*
			 * When the continue button is pressed it check if the
			 * Object is a trainer of a professor and shows a different view
			 */
			String password = new String(passwordField.getPassword());
			try {
				t = logeable.getPerson(usernameField.getText(), password);
				if (t instanceof Professor) {
					Professor prof = (Professor) t;
					ProfessorView vProfessor = new ProfessorView(LoginView.this, prof);
					vProfessor.setVisible(true);
				} else if (t == null) {
					errorMessage.setVisible(true);
					usernameField.setText("");
					passwordField.setText("");
				} else {
					TrainerView trainerView = new TrainerView(LoginView.this, t);
					trainerView.setVisible(true);

				}
			} catch (MyException e1) {

				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource().equals(usernameField)) {
			passwordField.setEnabled(true);
		}
	}

	public void focusLost(FocusEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// The content of a password field must me got like that cause getText() is
		// deprecated
		String password = new String(passwordField.getPassword());
		if (e.getSource().equals(passwordField) && !usernameField.getText().isBlank()
				|| e.getSource().equals(usernameField) && !password.equals("")) {
			continueButton.setEnabled(true);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Here me manage that the user is able to move over the view a little bit using
		// the enter button
		if (e.getSource().equals(usernameField)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				passwordField.requestFocus();
			}
		}
		if (e.getSource().equals(passwordField)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				continueButton.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
