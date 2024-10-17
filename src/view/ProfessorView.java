package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import classes.Combat;
import classes.Login;
import classes.MyException;
import classes.Pokemon;
import classes.PokemonExtra;
import classes.Professor;
import classes.Trainer;
import factories.AccountManageableFactory;
import factories.DataBattleFactory;
import interfaces.AccountManageable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import interfaces.DataBattleShowable;

public class ProfessorView extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField addCheckField;
	private JTextField addGenderField;
	private JTextField addCityField;
	private JTextField addBadgesField;
	private JTextField addPokeballsField;
	private JTextField pokeballAdderField;
	private JTextField upgradeRegionField;
	private JTextArea infotoaddField;
	private JTextArea resulttextField;
	private JTextArea deleteTrainerTextArea;
	private JButton teamButton;
	private JButton combatHistoryButton;
	private JButton informationButton;
	private JButton checkButton;
	private JButton deleteButton;
	private JButton addCheckBtn;
	private JButton pokeballAdderBtn;
	private JButton addTrainerButton;
	private JButton addValidateBtn;
	private JButton upgradeButton;
	private JDateChooser addCalender;
	private JComboBox<String> trainersComboBox;
	private JComboBox<String> addInitalsComboBox;
	private JComboBox<String> ascentTrainerCB;
	private JComboBox<String> waterComboBox;
	private JComboBox<String> fireComboBox;
	private JComboBox<String> grassComboBox;
	private JComboBox<String> trainerDelete;
	private JTable table;
	private JTable table2;
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModel2;
	private JPanel panelBattle;
	private JPanel paneDelete;
	private JPanel panelModify;
	private JPanel panelAscend;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private AccountManageable manageable = AccountManageableFactory.getAccountManageable();
	private DataBattleShowable showable = DataBattleFactory.getDataBattleShowable();
	private Trainer t;
	private Professor prof;
	private LinkedHashSet<Trainer> trainers;
	private LinkedHashSet<PokemonExtra> pokemons;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblFondoLabo;

	/**
	 * @param loginView
	 * @param loggedProf
	 */
	public ProfessorView(LoginView loginView, Professor loggedProf) {
		super(loginView);
		requestFocus();
		setResizable(false);
		prof = loggedProf;
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProfessorView.class.getResource("/resources/descarga.png")));
		setTitle("Professor View");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		loginView.dispose();

		try {
			pokemons = showable.getPokemons();
			trainers = manageable.getTrainers();
		} catch (MyException er) {
			JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
		}

		panelBattle = new JPanel();
		paneDelete = new JPanel();
		paneDelete.setBackground(new Color(63, 204, 220));
		panelModify = new JPanel();
		panelModify.setBackground(new Color(63, 204, 220));
		panelAscend = new JPanel();
		panelAscend.setBackground(new Color(63, 204, 220));
		JTabbedPane pestanas = new JTabbedPane();

		trainersComboBox = new JComboBox<String>();
		trainersComboBox.setMaximumRowCount(20);
		trainersComboBox.setForeground(new Color(0, 0, 0));
		trainersComboBox.setBackground(SystemColor.control);
		trainersComboBox.setBounds(175, 61, 237, 53);
		panelBattle.add(trainersComboBox);
		trainersComboBox.addActionListener(this);

		trainerDelete = new JComboBox<String>();
		trainerDelete.setBounds(37, 63, 222, 47);
		paneDelete.add(trainerDelete);
		trainerDelete.addActionListener(this);

		ascentTrainerCB = new JComboBox<String>();
		ascentTrainerCB.setBounds(404, 91, 222, 31);
		ascentTrainerCB.addActionListener(this);
		panelAscend.add(ascentTrainerCB);

		// 1.Battles View
		panelBattle.setBackground(new Color(192, 192, 192));
		pestanas.addTab("BATTLES", panelBattle);

		panelBattle.setLayout(null);

		teamButton = new JButton("TEAM");
		teamButton.setBounds(10, 204, 142, 49);
		panelBattle.add(teamButton);
		teamButton.addActionListener(this);

		informationButton = new JButton("INFORMATION");
		informationButton.setBounds(10, 138, 142, 55);
		panelBattle.add(informationButton);
		informationButton.addActionListener(this);

		combatHistoryButton = new JButton("COMBAT HISTORY");
		combatHistoryButton.setBounds(10, 264, 142, 49);
		panelBattle.add(combatHistoryButton);
		combatHistoryButton.addActionListener(this);

		JLabel selectTrainerLabel = new JLabel("Select Trainner:");
		selectTrainerLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		selectTrainerLabel.setBounds(23, 73, 142, 53);
		panelBattle.add(selectTrainerLabel);

		/*
		 * This one is the TextArean where the trainers info is going to be shown it is
		 * an area and not a normal text field because it has to show more than one line
		 * and a label can not handle it
		 */
		resulttextField = new JTextArea(2, 30);
		resulttextField.setBackground(new Color(200, 243, 249));
		resulttextField.setEditable(false);
		resulttextField.setBounds(162, 138, 280, 175);
		panelBattle.add(resulttextField);

		informationButton.setEnabled(false);
		teamButton.setEnabled(false);
		combatHistoryButton.setEnabled(false);

		// The JLabel under this comment has no text because the picture of Oak is on
		// this Label
		JLabel professorImageLabel = new JLabel("");
		professorImageLabel.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/oak.png")));
		professorImageLabel.setBounds(667, 178, 188, 341);
		panelBattle.add(professorImageLabel);

		JLabel welcomeProfessorLabel = new JLabel("Welcome Professor !!");
		welcomeProfessorLabel.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 17));
		welcomeProfessorLabel.setBounds(24, 30, 248, 28);
		panelBattle.add(welcomeProfessorLabel);

		/*
		 * Here we define two different tables to show the information of the Pokemon
		 * team and the combat History The one Responsible for the Pokemon team is the
		 * scrollPane, tableModel and table. the same unes but with the number 2 are the
		 * ones fore Combat history
		 */
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 350, 432, 169);
		panelBattle.add(scrollPane);

		tableModel = new DefaultTableModel();
		String[] tableHeaders = { "PokedexID", "Name", "Nickname", "Region", "type1", "type2", "level" };
		tableModel.setColumnIdentifiers(tableHeaders);
		table = new JTable(tableModel);
		table.setEnabled(false);

		table.setBackground(new Color(239, 205, 124));
		scrollPane.setViewportView(table);
		table.setVisible(false);
		scrollPane.setVisible(false);

		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 350, 432, 118);
		panelBattle.add(scrollPane2);

		tableModel2 = new DefaultTableModel();
		String[] tableHeaders2 = { "TrainerID1", "TrainerID2", "WinnerID" };
		tableModel2.setColumnIdentifiers(tableHeaders2);
		table2 = new JTable(tableModel2);
		table2.setEnabled(false);

		/*
		 * As you can tell both tables have the same position and thats because they are
		 * shown in the same places and the ActionListener is the one responsible of
		 * managing if one of the is visible or not not
		 */
		table2.setBackground(new Color(239, 205, 124));
		scrollPane2.setViewportView(table2);

		lblFondoLabo = new JLabel("");
		lblFondoLabo.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/labo.png")));
		lblFondoLabo.setBounds(0, 0, 1061, 543);
		panelBattle.add(lblFondoLabo);
		table2.setVisible(false);
		scrollPane2.setVisible(false);

		// 2.Delete View

		pestanas.addTab("DELETE", paneDelete);
		paneDelete.setLayout(null);

		JLabel deleteMessage = new JLabel("Which trainer do you want to delete?");
		deleteMessage.setFont(new Font("Yu Gothic UI Light", Font.BOLD | Font.ITALIC, 18));
		deleteMessage.setBounds(27, 26, 337, 17);
		paneDelete.add(deleteMessage);

		// And here are the two different buttons used in the second view
		checkButton = new JButton("CHECK");
		checkButton.setBounds(37, 136, 173, 47);
		paneDelete.add(checkButton);
		checkButton.addActionListener(this);

		deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(this);

		checkButton.setEnabled(false);
		deleteButton.setEnabled(false);

		deleteButton.setBounds(37, 201, 173, 47);
		paneDelete.add(deleteButton);

		// As the previous page here we have the TextArea where the trainer info is
		// shown
		deleteTrainerTextArea = new JTextArea();
		deleteTrainerTextArea.setBackground(SystemColor.inactiveCaption);
		deleteTrainerTextArea.setBounds(79, 270, 375, 172);
		paneDelete.add(deleteTrainerTextArea);
		deleteTrainerTextArea.setColumns(10);

		// Pestaña3
		JLabel deletePIcture = new JLabel("");
		deletePIcture.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/brocks-removebg-preview.png")));
		deletePIcture.setBounds(313, 11, 505, 532);
		paneDelete.add(deletePIcture);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/fondo2.png")));
		lblNewLabel_1.setBounds(0, 0, 1061, 543);
		paneDelete.add(lblNewLabel_1);
		pestanas.addTab("ADD/MODIFY", panelModify);
		panelModify.setLayout(null);

		JLabel intitalsLbl = new JLabel("SET INITIAL");
		intitalsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		intitalsLbl.setBounds(151, 263, 86, 14);
		panelModify.add(intitalsLbl);

		addCheckBtn = new JButton("CHECK");
		addCheckBtn.setBounds(293, 54, 89, 23);
		panelModify.add(addCheckBtn);

		addCheckField = new JTextField();
		addCheckField.setHorizontalAlignment(SwingConstants.CENTER);
		addCheckField.setBounds(40, 55, 193, 20);
		panelModify.add(addCheckField);
		addCheckField.setColumns(10);

		addCalender = new JDateChooser();
		addCalender.setDateFormatString("yyyy-MM-dd");
		addCalender.setBounds(40, 137, 101, 20);
		panelModify.add(addCalender);

		addGenderField = new JTextField();
		addGenderField.setHorizontalAlignment(SwingConstants.CENTER);
		addGenderField.setBounds(40, 166, 101, 20);
		panelModify.add(addGenderField);
		addGenderField.setColumns(10);

		addCityField = new JTextField();
		addCityField.setHorizontalAlignment(SwingConstants.CENTER);
		addCityField.setColumns(10);
		addCityField.setBounds(40, 197, 101, 20);
		panelModify.add(addCityField);

		addBadgesField = new JTextField();
		addBadgesField.setHorizontalAlignment(SwingConstants.CENTER);
		addBadgesField.setColumns(10);
		addBadgesField.setBounds(40, 228, 101, 20);
		panelModify.add(addBadgesField);

		addInitalsComboBox = new JComboBox<String>();
		addInitalsComboBox.setBounds(40, 259, 101, 22);
		panelModify.add(addInitalsComboBox);

		addPokeballsField = new JTextField();
		addPokeballsField.setEditable(false);
		addPokeballsField.setHorizontalAlignment(SwingConstants.CENTER);
		addPokeballsField.setColumns(10);
		addPokeballsField.setBounds(40, 290, 101, 20);
		panelModify.add(addPokeballsField);

		pokeballAdderField = new JTextField();
		pokeballAdderField.setHorizontalAlignment(SwingConstants.CENTER);
		pokeballAdderField.setBounds(255, 290, 52, 20);
		panelModify.add(pokeballAdderField);
		pokeballAdderField.setColumns(10);

		pokeballAdderBtn = new JButton("ADD");
		pokeballAdderBtn.setBounds(336, 288, 74, 23);
		panelModify.add(pokeballAdderBtn);

		pokeballAdderBtn.addActionListener(this);

		addValidateBtn = new JButton("VALIDATE");
		addValidateBtn.addActionListener(this);
		addValidateBtn.setBounds(151, 362, 101, 23);
		panelModify.add(addValidateBtn);

		JLabel ageLbl = new JLabel("AGE");
		ageLbl.setHorizontalAlignment(SwingConstants.LEFT);
		ageLbl.setBounds(151, 137, 86, 14);
		panelModify.add(ageLbl);

		JLabel genderLbl = new JLabel("GENDER");
		genderLbl.setHorizontalAlignment(SwingConstants.LEFT);
		genderLbl.setBounds(151, 168, 86, 14);
		panelModify.add(genderLbl);

		JLabel cityLbl = new JLabel("ORIGIN CITY");
		cityLbl.setHorizontalAlignment(SwingConstants.LEFT);
		cityLbl.setBounds(151, 199, 86, 14);
		panelModify.add(cityLbl);

		JLabel badgesLbl = new JLabel("BADGES");
		badgesLbl.setHorizontalAlignment(SwingConstants.LEFT);
		badgesLbl.setBounds(151, 230, 86, 14);
		panelModify.add(badgesLbl);

		JLabel pokeballLbl = new JLabel("POKEBALLS");
		pokeballLbl.setHorizontalAlignment(SwingConstants.LEFT);
		pokeballLbl.setBounds(151, 292, 86, 14);
		panelModify.add(pokeballLbl);

		// Desactivar los componentes relevantes
		addCalender.setEnabled(false);
		addGenderField.setEnabled(false);
		addCityField.setEnabled(false);
		addBadgesField.setEnabled(false);
		addInitalsComboBox.setEnabled(false);
		addPokeballsField.setEnabled(false);
		pokeballAdderField.setEnabled(false);
		pokeballAdderBtn.setEnabled(false);

		infotoaddField = new JTextArea();
		infotoaddField.setLineWrap(true);
		infotoaddField.setEditable(false);
		infotoaddField.setColumns(10);
		infotoaddField.setBackground(Color.GRAY);
		infotoaddField.setBounds(422, 107, 250, 220);
		infotoaddField.setVisible(false);
		panelModify.add(infotoaddField);

		addTrainerButton = new JButton("ADD");
		addTrainerButton.addActionListener(this);
		addTrainerButton.setBounds(504, 362, 101, 23);
		addTrainerButton.setVisible(false);
		panelModify.add(addTrainerButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/joven.png")));
		lblNewLabel.setBounds(728, 125, 290, 346);
		panelModify.add(lblNewLabel);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/fondo3.png")));
		lblNewLabel_2.setBounds(0, 0, 1061, 543);
		panelModify.add(lblNewLabel_2);
		// Agregar ActionListener al botón btnNewButton_3
		addCheckBtn.addActionListener(this);

		// Pestaña4
		pestanas.addTab("ASCEND", panelAscend);
		panelAscend.setLayout(null);

		JLabel leafLbl = new JLabel("GRASS INITIAL");
		leafLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		leafLbl.setHorizontalAlignment(SwingConstants.CENTER);
		leafLbl.setBounds(317, 244, 139, 14);
		panelAscend.add(leafLbl);

		JLabel fireLbl = new JLabel("FIRE INITIAL");
		fireLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		fireLbl.setHorizontalAlignment(SwingConstants.CENTER);
		fireLbl.setBounds(454, 206, 139, 14);
		panelAscend.add(fireLbl);

		JLabel waterLbl = new JLabel("WATER INITIAL");
		waterLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		waterLbl.setHorizontalAlignment(SwingConstants.CENTER);
		waterLbl.setBounds(610, 244, 139, 14);
		panelAscend.add(waterLbl);

		JLabel lblNewLabel_4 = new JLabel("Select Trainer");
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setBackground(new Color(255, 255, 255));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(446, 56, 147, 25);
		panelAscend.add(lblNewLabel_4);

		upgradeRegionField = new JTextField();
		upgradeRegionField.setHorizontalAlignment(SwingConstants.CENTER);
		upgradeRegionField.setText("REGION");
		upgradeRegionField.setBounds(454, 166, 139, 20);
		panelAscend.add(upgradeRegionField);
		upgradeRegionField.setColumns(10);
		upgradeRegionField.setEnabled(false);

		waterComboBox = new JComboBox<String>();
		waterComboBox.setBounds(600, 268, 139, 22);
		panelAscend.add(waterComboBox);
		for (Pokemon p : getPokemonsForType("Water")) {
			waterComboBox.addItem(p.getName());
		}
		waterComboBox.setEnabled(false);

		fireComboBox = new JComboBox<String>();
		fireComboBox.setBounds(454, 230, 139, 22);
		panelAscend.add(fireComboBox);
		for (Pokemon p : getPokemonsForType("Fire")) {
			fireComboBox.addItem(p.getName());
		}
		fireComboBox.setEnabled(false);

		grassComboBox = new JComboBox<String>();
		grassComboBox.setBounds(317, 268, 139, 22);
		panelAscend.add(grassComboBox);
		for (Pokemon p : getPokemonsForType("Grass")) {
			grassComboBox.addItem(p.getName());
		}
		grassComboBox.setEnabled(false);

		upgradeButton = new JButton("UPGRADE");
		upgradeButton.addActionListener(this);
		upgradeButton.setBounds(687, 393, 122, 69);
		panelAscend.add(upgradeButton);
		upgradeButton.setEnabled(false);

		// Deshabilitar elementos al principio
		upgradeRegionField.setEnabled(false);
		waterComboBox.setEnabled(false);
		fireComboBox.setEnabled(false);
		grassComboBox.setEnabled(false);
		upgradeButton.setEnabled(false);

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(ProfessorView.class.getResource("/resources/ashfin.png")));
		lblNewLabel_3.setBounds(0, 0, 1061, 543);
		panelAscend.add(lblNewLabel_3);

		getContentPane().add(pestanas);
		// Using @param name = null because I want all
		// trainers to be added and none to be removed
		setComboBoxes();
		// Configuramos la ventana

		setSize(1080, 607);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * This first if control if something has been chosen on the trainers conboBox
		 * and by using the name identifies the trainer and sets it to the t variable
		 */
		if (e.getSource().equals(trainersComboBox)) {
			resulttextField.setText("");
			informationButton.setEnabled(true);
			teamButton.setEnabled(true);
			combatHistoryButton.setEnabled(true);
			String selectedTrainer = (String) trainersComboBox.getSelectedItem();
			try {
				for (Trainer element : manageable.getTrainers()) {
					if (element.getName().equals(selectedTrainer))
						t = element;
				}
			} catch (MyException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			}
		}

		if (e.getSource().equals(informationButton)) {
			if (t.getName().equals(trainersComboBox.getSelectedItem())) {
				resulttextField.setText(null);
				resulttextField.setText(t.getTrainerInfo());

			}
		}
		// Here are the two methods to show and hide the tables stated before
		if (e.getSource().equals(teamButton)) {
			if (t.getName().equals(trainersComboBox.getSelectedItem())) {
				scrollPane.setVisible(true);
				scrollPane2.setVisible(false);
				table.setVisible(true);
				table2.setVisible(false);
				tableModel.setRowCount(0);
				for (Trainer element : trainers) {
					if (element.getName().equals(trainersComboBox.getSelectedItem()))
						t = element;
				}
				for (Pokemon element : t.getTeam()) {
					Object[] row = new Object[7];
					row[0] = element.getPokedexID();
					row[1] = element.getName();
					row[2] = element.getNickname();
					row[3] = element.getRegion();
					row[4] = element.getType1();
					row[5] = element.getType2();
					row[6] = element.getLevel();
					tableModel.addRow(row);
				}
				table.setVisible(true);
			}
		}
		if (e.getSource().equals(combatHistoryButton)) {
			if (t.getName().equals(trainersComboBox.getSelectedItem())) {
				scrollPane.setVisible(false);
				scrollPane2.setVisible(true);
				table.setVisible(false);
				table2.setVisible(true);
				tableModel2.setRowCount(0);

				for (Trainer element : trainers) {
					if (element.getName().equals(trainersComboBox.getSelectedItem())) {
						t = element;
					}
				}

				for (Combat element : t.getCombatHistory()) {
					Object[] row = new Object[3];
					row[0] = element.getTrainer1();
					row[1] = element.getTrainer2();
					row[2] = element.getWinnerTrainerID();

					tableModel2.addRow(row);
				}

			}
		}

		if (e.getSource().equals(trainerDelete)) {
			deleteButton.setEnabled(true);
			checkButton.setEnabled(true);
			String selectedTrainer = (String) trainerDelete.getSelectedItem();
			for (Trainer element : trainers) {
				if (element.getName().equals(selectedTrainer))
					t = element;
			}

		}

		if (e.getSource().equals(checkButton)) {
			try {
				if (t.getName().equals(trainerDelete.getSelectedItem())) {
					deleteTrainerTextArea.setText(null);
					deleteTrainerTextArea.setText(t.getTrainerInfo());
				}
			} catch (NullPointerException er) {

				JOptionPane.showMessageDialog(null, "No trainer selected", "WARNING", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource().equals(deleteButton)) {
			if (JOptionPane.showOptionDialog(panelBattle, "Do you want to delete this trainner?", "Confirm delete",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "YES", "NO" },
					JOptionPane.YES_OPTION) == 0) {
				int id;
				id = t.getTrainerID();
				try {
					manageable.deleteTrainer(id);
					JOptionPane.showMessageDialog(null, "The trainer has been sucssesfully deleted");
				} catch (MyException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} finally {
					deleteTrainer(t.getName());
					setComboBoxes();
				}
			}
		}

		if (e.getSource().equals(addCheckBtn)) {
			ArrayList<Pokemon> initialSelection = prof.getInitialSelection();
			Trainer aux = new Trainer();
			aux = trainerExists(addCheckField.getText());
			if (aux != null) {
				addCalender.setDate(aux.getBirthdate());
				addGenderField.setText(aux.getGender());
				addCityField.setText(aux.getOriginCity());
				addBadgesField.setText(String.valueOf(aux.getBadges()));
				addPokeballsField.setText(String.valueOf(aux.getPokeballs()));
				addTrainerButton.setText("MODIFY");
			} else {
				setAddEnabled();
				addInitalsComboBox.setEnabled(true);
				addInitalsComboBox.removeAllItems();
				addInitalsComboBox.addItem("");
				for (Pokemon p : initialSelection) {
					addInitalsComboBox.addItem(p.getName());
				}
			}
		}

		if (e.getSource().equals(pokeballAdderBtn)) {
			// Obtener valores de txtPokeballs y txtAddPokeballs
			int pokeballs;
			try {
				pokeballs = Integer.parseInt(addPokeballsField.getText());
				try {
					int addPokeballs = Integer.parseInt(pokeballAdderField.getText());
					addPokeballsField.setText(Integer.toString(pokeballs + addPokeballs));
				} catch (NumberFormatException er) {
					JOptionPane.showMessageDialog(null, "Only integers allowed", "WARNING",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NumberFormatException e1) {
				addPokeballsField.setText(Integer.toString(0));
			}
			// Sumar valores y establecer resultado en txtPokeballs
		}

		if (e.getSource().equals(addValidateBtn)) {
			try {
					Trainer aux = buildTrainer();
					showTrainerInfo(aux);
			} catch (NumberFormatException er) {
				JOptionPane.showMessageDialog(null, "Badges field cannot contain characters, only numbers", "WARNING",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource().equals(addTrainerButton)) {
			t = buildTrainer();
			try {
				if (addTrainerButton.getText().equals("ADD")) {
					SetLoginView setlogin = new SetLoginView(ProfessorView.this, true);
					setlogin.setVisible(true);
					setlogin.requestFocus();
				} else {
					manageable.modifyTrainer(t);
				}
			} catch (MyException er) {
				JOptionPane.showMessageDialog(null, er.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE);
			} finally {
				trainers.add(t);
				setComboBoxes();
			}
		}

		if (e.getSource().equals(ascentTrainerCB)) {

			if (ascentTrainerCB.getSelectedIndex() != -1) {
				upgradeRegionField.setEnabled(true);
				waterComboBox.setEnabled(true);
				fireComboBox.setEnabled(true);
				grassComboBox.setEnabled(true);
				upgradeButton.setEnabled(true);
			}
		}

		if (e.getSource().equals(upgradeButton)) {
			Professor prof = new Professor();
			ArrayList<Pokemon> poks = new ArrayList<>();

			for (Trainer t : trainers) {
				if (t.getName().equals(ascentTrainerCB.getSelectedItem())) {
					prof.completeProf(t, upgradeRegionField.getText());
				}
			}
			for (PokemonExtra p : pokemons) {
				if (p.getName().equals(fireComboBox.getSelectedItem()))
					poks.add(p);
				if (p.getName().equals(waterComboBox.getSelectedItem()))
					poks.add(p);
				if (p.getName().equals(grassComboBox.getSelectedItem()))
					poks.add(p);
			}
			prof.setInitialSelection(poks);

			try {
				manageable.upgradeToProfessor(prof);
			} catch (MyException er) {
				JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void setComboBoxes() {
		Iterator<Trainer> it = trainers.iterator();

		// Delete everything from the combo boxes
		trainersComboBox.removeAllItems();
		trainerDelete.removeAllItems();
		ascentTrainerCB.removeAllItems();

		// Set the box to a default position
		trainersComboBox.setSelectedIndex(-1);
		trainerDelete.setSelectedIndex(-1);
		ascentTrainerCB.setSelectedIndex(-1);

		while (it.hasNext()) {
			Trainer t = it.next();
			trainerDelete.addItem(t.getName());
			trainersComboBox.addItem(t.getName());
			ascentTrainerCB.addItem(t.getName());
		}

	}

	private Trainer trainerExists(String name) {
		Iterator<Trainer> it = trainers.iterator();
		boolean found = false;
		Trainer toReturn = null;

		while (it.hasNext() && !found) {
			Trainer aux = it.next();
			if (name.equalsIgnoreCase(aux.getName())) {
				setAddEnabled();
				toReturn = aux;
				found = true;
			}
		}
		if (!found) {
			JOptionPane.showMessageDialog(null, "Trainer not found, you may now add a new one", "WARNING",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return toReturn;
	}

	private void setAddEnabled() {
		addCalender.setEnabled(true);
		addGenderField.setEnabled(true);
		addCityField.setEnabled(true);
		addBadgesField.setEnabled(true);
		addPokeballsField.setEnabled(true);
		pokeballAdderField.setEnabled(true);
		pokeballAdderBtn.setEnabled(true);
	}

	private void showTrainerInfo(Trainer aux) {
		infotoaddField.setVisible(true);
		addTrainerButton.setVisible(true);

		infotoaddField.setText(aux.getTrainerInfo());
	}

	private Trainer buildTrainer() throws NumberFormatException {
		Trainer aux = new Trainer();
		java.sql.Date date = new java.sql.Date(addCalender.getDate().toInstant().toEpochMilli());
		aux.setName(addCheckField.getText());

		for (Trainer a : trainers) {
			if (a.getName().equalsIgnoreCase(aux.getName()))
				aux.setTrainerID(a.getTrainerID());
		}

		aux.setName(addCheckField.getText());
		aux.setBirthdate(date);
		aux.setGender(addGenderField.getText());
		aux.setOriginCity(addCityField.getText());
		aux.setBadges(Integer.parseInt(addBadgesField.getText()));
		aux.setPokeballs(Integer.parseInt(addPokeballsField.getText()));
		aux.setInitial(prof.getInitial((String) addInitalsComboBox.getSelectedItem()));

		return aux;
	}

	public void setLogin(Login log) {
		try {
			Trainer trainer = buildTrainer();
			manageable.addTrainer(trainer);
			manageable.setLogin(log);
		} catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "Badges field cannot contain characters, only numbers", "WARNING",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (MyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "WARNING", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private List<PokemonExtra> getPokemonsForType(String type) {
		List<PokemonExtra> aux = new ArrayList<>();
		Iterator<PokemonExtra> it = pokemons.iterator();

		while (it.hasNext()) {
			PokemonExtra p = it.next();
			if (p.getType1().equals(type)) {
				aux.add(p);
			}
		}
		return aux;
	}

	private void deleteTrainer(String name) {
		Iterator<Trainer> it = trainers.iterator();
		boolean flag = false;

		while (it.hasNext() && !flag) {
			Trainer aux = it.next();
			if (aux.getName().equals(name)) {
				it.remove();
				flag = true;
			}
		}
	}

}
