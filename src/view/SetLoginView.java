package view;

import javax.swing.JButton;
import javax.swing.JDialog;

import classes.Login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;

/**
 * 
 * A dialog box that allows the user to set a login username and password.
 * 
 * It implements the ActionListener and FocusListener interfaces to handle
 * 
 * user input events.
 * 
 * @author Alexander Epelde
 */
public class SetLoginView extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L; // Sets the serialVersion of the view
	private JTextField setLoginUsernameField; // Text field to enter the username
	private JPasswordField setLoginPasswordField; // Password field to enter the password
	private JPasswordField setLoginConfirmPassword; // Password field to confirm the password
	private JButton setLoginBtn; // Button to confirm the login details
	private JLabel setLoginError; // Label to display an error message
	private ProfessorView prof; // Reference to the professor view

	/**
	 * 
	 * Creates a new SetLoginView dialog box with the given ProfessorView
	 * 
	 * reference and modal flag.
	 * 
	 * @param p     The reference to the ProfessorView.
	 * 
	 * @param modal A flag indicating whether the dialog should be modal or not.
	 */
	public SetLoginView(ProfessorView p, boolean modal) {
		p.transferFocus(); // Transfer focus to the ProfessorView
		prof = p; // Store the reference to the ProfessorView
		requestFocus(true); // Request focus for this dialog box
		setResizable(false); // Disable resizing of the dialog
		setBounds(100, 100, 450, 300); // Set the size and position of the dialog
		getContentPane().setLayout(null); // Disable layout management

		// Create the CONFIRM button
		setLoginBtn = new JButton("CONFIRM");
		setLoginBtn.addActionListener(this);
		setLoginBtn.setBounds(163, 168, 96, 25);
		getContentPane().add(setLoginBtn);
		setLoginBtn.setActionCommand("OK");
		setLoginBtn.requestFocus();
		getRootPane().setDefaultButton(setLoginBtn);

		// Create the USERNAME label and text field
		JLabel setUsernameLbl = new JLabel("USERNAME");
		setUsernameLbl.setBounds(55, 32, 96, 15);
		getContentPane().add(setUsernameLbl);
		setLoginUsernameField = new JTextField();
		setLoginUsernameField.setBounds(178, 30, 239, 19);
		getContentPane().add(setLoginUsernameField);
		setLoginUsernameField.setColumns(10);
		

		// Create the PASSWORD label and text field
		JLabel setPasswordlbl = new JLabel("PASSWORD");
		setPasswordlbl.setBounds(55, 69, 96, 15);
		getContentPane().add(setPasswordlbl);
		setLoginPasswordField = new JPasswordField();
		setLoginPasswordField.setBounds(178, 67, 239, 19);
		getContentPane().add(setLoginPasswordField);

		// Create the CONFIRM PASSWORD label and text field
		JLabel setConfirmPasswordLbl = new JLabel("CONFIRM PASSWORD");
		setConfirmPasswordLbl.setBounds(28, 111, 132, 15);
		getContentPane().add(setConfirmPasswordLbl);
		setLoginConfirmPassword = new JPasswordField();
		setLoginConfirmPassword.setBounds(178, 109, 239, 19);
		getContentPane().add(setLoginConfirmPassword);

		// Create the error label and set its initial visibility to false
		setLoginError = new JLabel("Passwords do not match");
		setLoginError.setForeground(new Color(224, 27, 36));
		setLoginError.setBounds(178, 140, 239, 15);
		getContentPane().add(setLoginError);
		setLoginError.setVisible(false);
	}

	/**
	 * 
	 * This method is called when the user clicks the "Save" button to set their
	 * login credentials.
	 * It checks that the two passwords entered in the fields match. If they do, it
	 * creates a new Login object with the username and password entered,
	 *  and sets it in the prof object. If they don't match, an error message is displayed.
	 * 
	 * @param arg0 The ActionEvent object representing the click event.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get the passwords entered in the two fields
		String passwd1 = new String(setLoginPasswordField.getPassword());
		String passwd2 = new String(setLoginConfirmPassword.getPassword());

		// Check if the passwords match
		if (!passwd1.equals(passwd2)) {
			setLoginError.setVisible(true); // Show an error message if they don't match
		} else {
			setLoginError.setVisible(false); // Hide the error message if they match

			// Create a new Login object with the username and password entered
			Login login = new Login();
			login.setUsername(setLoginUsernameField.getText());
			login.setPassword(new String(setLoginPasswordField.getPassword()));

			// Set the Login object in the prof object
			prof.setLogin(login);

			// Close the window
			this.dispose();

		}
	}
}
