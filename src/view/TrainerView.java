package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import classes.Combat;
import classes.MyException;
import classes.Pokemon;
import classes.PokemonExtra;
import classes.Trainer;
import factories.AccountManageableFactory;
import factories.DataBattleFactory;
import factories.SimulableFactory;
import interfaces.AccountManageable;
import interfaces.DataBattleShowable;
import interfaces.Simulable;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class TrainerView extends JDialog implements ActionListener, FocusListener {
	private JLabel lblPokePC;
	private JButton btnSave;
	private JButton btnPok1;
	private JButton btnPok2;
	private JButton btnPok3;
	private JButton btnPok4;
	private JButton btnPok5;
	private JButton btnPok6;
	private JButton btnShow;
	private JButton btnUpdate;
	private JButton btnFight;
	private JTextField textUserID;
	private JTextField textName;
	private JTextField textOrigin;
	private JTextField textGender;
	private JTextField textBadges;
	private JTextArea textAreaTrainInfo;
	private JDateChooser ageCalender;
	private JComboBox<String> comboBoxPC;
	private JComboBox<String> teamPokemonToSwitch;
	private JComboBox<String> pcPokemonToSwitch;
	private JPanel panelPC;
	private JButton btnSwitch;
	private Trainer trainer;
	private AccountManageable manageable = AccountManageableFactory.getAccountManageable();
	private DataBattleShowable showable = DataBattleFactory.getDataBattleShowable();
	private LinkedHashMap<String, Pokemon> pc;
	private LinkedHashSet<Pokemon> team;
	private JButton backButton;
	private JPanel panelInfo;
	private Simulable simulable = SimulableFactory.getSimulable();
	private DataBattleShowable dbs = DataBattleFactory.getDataBattleShowable();
	private JComboBox<String> comboPokemon;
	private JLabel lbl_PokemonName;
	private JButton btn_Escape;
	private JLabel lblRandomLvl;
	private JButton btn_ThrowPokeball;
	private JLabel lbl_PokeballNumber;
	private LinkedHashSet<PokemonExtra> dbPokemons;
	private LinkedHashSet<Trainer> trainers;
	private JLabel lblOpponentSprite_1;
	private JLabel lblBattle;
	private DefaultTableModel tableModel2;
	private JScrollPane scrollPane;
	private JTable table;

	TrainerView(LoginView loginView, Trainer t) {
		super(loginView, true);
		setResizable(false);
		trainer = t;
		setTitle("Trainer View");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		loginView.dispose();

		try {
			dbPokemons = dbs.getPokemons();
		} catch (MyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);

		}

		panelInfo = new JPanel();
		panelInfo.setBackground(new Color(192, 192, 192));
		panelPC = new JPanel();
		panelPC.setBorder(null);
		JPanel panelCapture = new JPanel();
		JPanel panelSimulation = new JPanel();

		// Creamos el objeto JTabbedPane y le agregamos las pestañas
		JTabbedPane pestanas = new JTabbedPane();

		// Pestaña1
		// Creamos un panel y lo agregamos a la pestaña "BATTLES"
		pestanas.addTab("INFO", panelInfo);
		panelInfo.setLayout(null);

		textUserID = new JTextField();
		textUserID.setEditable(false);
		textUserID.setBounds(255, 135, 96, 19);
		panelInfo.add(textUserID);
		textUserID.setColumns(10);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(255, 195, 96, 19);
		panelInfo.add(textName);

		textOrigin = new JTextField();
		textOrigin.setBounds(255, 250, 96, 19);
		panelInfo.add(textOrigin);
		textOrigin.setColumns(10);

		textGender = new JTextField();
		textGender.setColumns(10);
		textGender.setBounds(536, 195, 96, 19);
		panelInfo.add(textGender);

		textBadges = new JTextField();
		textBadges.setEditable(false);
		textBadges.setColumns(10);
		textBadges.setBounds(536, 250, 96, 19);
		panelInfo.add(textBadges);

		JLabel lblUserID = new JLabel("USER ID");
		lblUserID.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserID.setBounds(255, 100, 96, 25);
		panelInfo.add(lblUserID);

		ageCalender = new JDateChooser();
		ageCalender.getCalendarButton();
		ageCalender.setBounds(536, 135, 96, 19);
		panelInfo.add(ageCalender);

		JLabel lblName = new JLabel("NAME");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(255, 160, 96, 25);
		panelInfo.add(lblName);

		JLabel lblOriginCity = new JLabel("ORIGIN CITY");
		lblOriginCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblOriginCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOriginCity.setBounds(255, 224, 110, 25);
		panelInfo.add(lblOriginCity);

		JLabel lblGender = new JLabel("GENDER");
		lblGender.setHorizontalAlignment(SwingConstants.LEFT);
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGender.setBounds(536, 160, 76, 25);
		panelInfo.add(lblGender);

		JLabel lblBadges = new JLabel("BADGES");
		lblBadges.setHorizontalAlignment(SwingConstants.LEFT);
		lblBadges.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBadges.setBounds(535, 224, 110, 25);
		panelInfo.add(lblBadges);

		btnSave = new JButton("SAVE");
		btnSave.addActionListener(this);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(402, 306, 85, 21);
		panelInfo.add(btnSave);

		textAreaTrainInfo = new JTextArea();
		textAreaTrainInfo.setForeground(new Color(0, 0, 0));
		textAreaTrainInfo.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 14));
		textAreaTrainInfo.setLineWrap(true);
		textAreaTrainInfo.setColumns(10);
		textAreaTrainInfo.setEditable(false);
		textAreaTrainInfo.setBounds(132, 379, 353, 88);
		panelInfo.add(textAreaTrainInfo);

		JLabel lblAge = new JLabel("AGE");
		lblAge.setHorizontalAlignment(SwingConstants.LEFT);
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAge.setBounds(536, 100, 76, 25);
		panelInfo.add(lblAge);

		btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(this);
		btnUpdate.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnUpdate.setBounds(545, 409, 148, 21);
		btnUpdate.setVisible(false);
		panelInfo.add(btnUpdate);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/Ash_Ketchum_Journeys.png")));
		lblNewLabel_1.setBounds(769, 33, 282, 481);
		panelInfo.add(lblNewLabel_1);

		JLabel lblFondTarde = new JLabel("");
		lblFondTarde.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/fondo2.png")));
		lblFondTarde.setBounds(0, 0, 1061, 543);
		panelInfo.add(lblFondTarde);

		// Pestaña2
		pestanas.addTab("PC", panelPC);
		panelPC.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 12, 563, 402);
		panelPC.add(scrollPane);
		
		tableModel2 = new DefaultTableModel();
		String[] tableHeaders = { "PokedexID", "Name", "Nickname", "Region", "type1", "type2", "level" };
		tableModel2.setColumnIdentifiers(tableHeaders);
		table = new JTable(tableModel2);
		table.setEnabled(true);
		
		scrollPane.setViewportView(table);
		table.setVisible(true);
		scrollPane.setVisible(true);

		lblPokePC = new JLabel("POKÉMON IN THE PC");
		lblPokePC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPokePC.setBounds(676, 23, 153, 21);
		panelPC.add(lblPokePC);

		comboBoxPC = new JComboBox<String>();
		comboBoxPC.setFocusTraversalPolicyProvider(true);
		comboBoxPC.setBounds(676, 56, 153, 31);
		panelPC.add(comboBoxPC);

		btnPok1 = new JButton("POK1");
		btnPok1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPok1.setBounds(639, 300, 114, 71);
		btnPok1.addActionListener(this);
		panelPC.add(btnPok1);

		btnPok2 = new JButton("POK2");
		btnPok2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPok2.setBounds(639, 418, 114, 71);
		btnPok2.addActionListener(this);
		panelPC.add(btnPok2);

		btnPok3 = new JButton("POK3");
		btnPok3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPok3.setBounds(775, 300, 114, 71);
		btnPok3.addActionListener(this);
		panelPC.add(btnPok3);

		btnPok4 = new JButton("POK4");
		btnPok4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPok4.setBounds(775, 418, 114, 71);
		btnPok4.addActionListener(this);
		panelPC.add(btnPok4);

		btnPok5 = new JButton("POK5");
		btnPok5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPok5.setBounds(916, 300, 114, 71);
		btnPok5.addActionListener(this);
		panelPC.add(btnPok5);

		btnPok6 = new JButton("POK6");
		btnPok6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPok6.setBounds(916, 418, 114, 71);
		btnPok6.addActionListener(this);
		panelPC.add(btnPok6);

		btnShow = new JButton("SHOW");
		btnShow.addActionListener(this);
		btnShow.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnShow.setBounds(874, 61, 99, 21);
		panelPC.add(btnShow);

		teamPokemonToSwitch = new JComboBox<String>();
		teamPokemonToSwitch.setSelectedIndex(-1);
		teamPokemonToSwitch.setBounds(665, 158, 153, 31);
		teamPokemonToSwitch.setVisible(false);
		panelPC.add(teamPokemonToSwitch);

		pcPokemonToSwitch = new JComboBox<String>();
		pcPokemonToSwitch.setSelectedIndex(-1);
		pcPokemonToSwitch.setBounds(845, 158, 153, 31);
		pcPokemonToSwitch.setVisible(false);
		panelPC.add(pcPokemonToSwitch);

		btnSwitch = new JButton("SWITCH MENU");
		btnSwitch.addActionListener(this);
		btnSwitch.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnSwitch.setBounds(687, 234, 153, 21);
		panelPC.add(btnSwitch);

		backButton = new JButton("BACK");
		backButton.setVerticalAlignment(SwingConstants.BOTTOM);
		backButton.setFont(new Font("Dialog", Font.PLAIN, 14));
		backButton.setBounds(875, 237, 114, 21);
		backButton.addActionListener(this);
		backButton.setVisible(false);
		panelPC.add(backButton);

		JLabel pcBackground = new JLabel("");
		pcBackground.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/pcphotoshop.png")));
		pcBackground.setBounds(-21, -24, 1073, 560);
		panelPC.add(pcBackground);

		// Agregar ActionListener a la JComboBox
		comboBoxPC.addActionListener(this);

		// Pestaña3
		pestanas.addTab("CAPTURE", panelCapture);
		panelCapture.setLayout(null);

		JLabel lb_Lvl = new JLabel("Lvl.");
		lb_Lvl.setForeground(new Color(255, 0, 0));
		lb_Lvl.setFont(new Font("Pokemon Fire Red", Font.BOLD, 25));
		lb_Lvl.setBounds(522, 123, 45, 23);
		panelCapture.add(lb_Lvl);

		lbl_PokeballNumber = new JLabel();
		lbl_PokeballNumber.setFont(new Font("Dialog", Font.BOLD, 18));
		lbl_PokeballNumber.setText(Integer.toString(trainer.getPokeballs()));
		lbl_PokeballNumber.setBounds(571, 417, 45, 32);
		panelCapture.add(lbl_PokeballNumber);

		lblRandomLvl = new JLabel(randomLvl());
		lblRandomLvl.setForeground(new Color(255, 0, 0));
		lblRandomLvl.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 30));
		lblRandomLvl.setBounds(577, 121, 51, 23);
		panelCapture.add(lblRandomLvl);

		lbl_PokemonName = new JLabel("");
		lbl_PokemonName.setForeground(new Color(0, 0, 0));
		lbl_PokemonName.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 30));
		lbl_PokemonName.setText(randomPokemon());
		lbl_PokemonName.setBounds(386, 103, 163, 58);
		panelCapture.add(lbl_PokemonName);

		JLabel lblNewLabel_6 = new JLabel("X");
		lblNewLabel_6.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 25));
		lblNewLabel_6.setBounds(534, 400, 33, 70);
		panelCapture.add(lblNewLabel_6);

		JLabel lbl_Sprite = new JLabel("");
		lbl_Sprite.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/bulbasur.gif")));
		lbl_Sprite.setBounds(452, 188, 152, 165);
		panelCapture.add(lbl_Sprite);

		JLabel lbl_WildPokémonEncounter = new JLabel("Wild Pokémon encounter!");
		lbl_WildPokémonEncounter.setForeground(new Color(0, 0, 0));
		lbl_WildPokémonEncounter.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 40));
		lbl_WildPokémonEncounter.setBounds(355, 47, 310, 63);
		panelCapture.add(lbl_WildPokémonEncounter);

		btn_ThrowPokeball = new JButton("THROW POKEBALL");
		btn_ThrowPokeball.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 15));
		btn_ThrowPokeball.addActionListener(this);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(
				TrainerView.class.getResource("/resources/584-5843757_pokemon-dialog-box-pokemon-text-box-png.png")));
		lblNewLabel_3.setBounds(329, 12, 362, 200);
		panelCapture.add(lblNewLabel_3);
		btn_ThrowPokeball.setBounds(452, 482, 152, 23);
		panelCapture.add(btn_ThrowPokeball);

		btn_Escape = new JButton("Run away!");
		btn_Escape.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 15));
		btn_Escape.addActionListener(this);
		btn_Escape.setBounds(761, 482, 137, 23);
		panelCapture.add(btn_Escape);

		JLabel lbl_Pokeball = new JLabel("");
		lbl_Pokeball.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/Poké_Ball_icon.svg (1).png")));
		lbl_Pokeball.setBounds(452, 391, 70, 70);
		panelCapture.add(lbl_Pokeball);

		JLabel lbl_Background_1 = new JLabel("");
		lbl_Background_1.setIcon(new ImageIcon(
				TrainerView.class.getResource("/resources/DVMT-6OXcAE2rZY.jpg.afab972f972bd7fbd4253bc7aa1cf27f.jpg")));
		lbl_Background_1.setBounds(0, -45, 1063, 611);
		panelCapture.add(lbl_Background_1);

		// Pestaña4
		pestanas.addTab("SIMULATION", panelSimulation);
		panelSimulation.setLayout(null);

		btnFight = new JButton("FIGHT!");
		btnFight.setBackground(new Color(255, 255, 255));
		btnFight.setFont(new Font("Pokemon Fire Red", Font.BOLD, 20));
		btnFight.setBounds(463, 422, 152, 81);
		btnFight.setVisible(false);
		btnFight.addActionListener(this);

		comboPokemon = new JComboBox<String>();
		comboPokemon.setOpaque(false);
		comboPokemon.setBorder(null);
		comboPokemon.setForeground(new Color(237, 51, 59));

		comboPokemon.setBackground(new Color(255, 255, 255));
		comboPokemon.setBounds(83, 278, 235, 32);
		comboPokemon.setSelectedIndex(-1);
		comboPokemon.addActionListener(this);
		lblOpponentSprite_1 = new JLabel("");
		lblOpponentSprite_1.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/8K8b (1).gif")));
		lblOpponentSprite_1.setBounds(624, -17, 257, 273);
		panelSimulation.add(lblOpponentSprite_1);

		lblBattle = new JLabel("Battle!");
		lblBattle.setForeground(new Color(255, 255, 255));
		lblBattle.setFont(new Font("Pokemon Fire Red", Font.BOLD, 70));
		lblBattle.setBounds(105, 62, 240, 56);
		panelSimulation.add(lblBattle);
		panelSimulation.add(comboPokemon);

		JLabel lblSelecPKSim = new JLabel("Choose a trainer to battle against");
		lblSelecPKSim.setForeground(new Color(237, 51, 59));
		lblSelecPKSim.setFont(new Font("Pokemon Fire Red", Font.PLAIN, 30));
		lblSelecPKSim.setBounds(61, 222, 351, 32);
		panelSimulation.add(lblSelecPKSim);

		JLabel lblTextBox = new JLabel("");
		lblTextBox.setIcon(new ImageIcon(
				TrainerView.class.getResource("/resources/584-5843757_pokemon-dialog-box-pokemon-text-box-png.png")));
		lblTextBox.setBounds(29, 185, 480, 164);
		panelSimulation.add(lblTextBox);
		panelSimulation.add(btnFight);

		JLabel lblTrainer = new JLabel("");
		lblTrainer.setIcon(new ImageIcon(TrainerView.class.getResource("/resources/goldTrainer.png")));
		lblTrainer.setBounds(463, 221, 152, 128);
		panelSimulation.add(lblTrainer);

		JLabel lbl_Setting = new JLabel("");
		lbl_Setting.setIcon(new ImageIcon(TrainerView.class
				.getResource("/resources/d6rqxyw-baf81cc2-f50f-4ab4-8265-c2831654c3f2-2124573794 (1).png")));
		lbl_Setting.setBounds(420, 12, 740, 457);
		panelSimulation.add(lbl_Setting);

		JLabel lbl_Background = new JLabel("");
		lbl_Background.setIcon(new ImageIcon(
				TrainerView.class.getResource("/resources/DVMT-6OXcAE2rZY.jpg.afab972f972bd7fbd4253bc7aa1cf27f.jpg")));
		lbl_Background.setBounds(0, -50, 1160, 613);
		panelSimulation.add(lbl_Background);
		// Pestaña4
		// Pestaña4
		pestanas.addTab("SIMULATION", panelSimulation);
		panelSimulation.setLayout(null);

		// Agregamos el objeto JTabbedPane a la ventana
		getContentPane().add(pestanas);
		setPokemonButtonsAndComboBox();
		setPersonalInfo();

		// Configuramos la ventana
		setSize(1080, 607);
		


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn_ThrowPokeball) {
			int randomNumber = (int) (Math.random() * (100 - 1) + 1);
			int lvlNumber = Integer.parseInt(lblRandomLvl.getText());
			int pokeballNo = trainer.getPokeballs();
			// Captures pokemon
			if (randomNumber >= lvlNumber) {
				JOptionPane.showMessageDialog(null, "Pokemon captured! You may now look at its stats in the PC slide",
						"CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE);
				Pokemon p = buildPokemon(lvlNumber);
				if ((team.size() < 6)) {
					addToTeam(p);
				} else {
					addToPc(p);
				}
				setPokemonButtonsAndComboBox();
				setRandomPokemon();
			} else {
				if (trainer.getPokeballs() >= 0) {
					trainer.setPokeballs(pokeballNo - 1);
					lbl_PokeballNumber.setText(Integer.toString(pokeballNo - 1));
					try {
						manageable.givePokeballs(trainer);
					} catch (MyException er) {
						JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"You have ran out of pokeballs, ask your local Professor to give you more",
							"CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		if (e.getSource() == btn_Escape) {
			setRandomPokemon();
		}

		if (e.getSource().equals(btnPok1) || e.getSource().equals(btnPok2) || e.getSource().equals(btnPok3)
				|| e.getSource().equals(btnPok4) || e.getSource().equals(btnPok5) || e.getSource().equals(btnPok6)) {

			JButton aux = (JButton) e.getSource();

			for (Pokemon element : trainer.getTeam()) {
				
				if(element.getName().equalsIgnoreCase(aux.getText())) {
					Object[] row = new Object[7];
					row[0] = element.getPokedexID();
					row[1] = element.getName();
					row[2] = element.getNickname();
					row[3] = element.getRegion();
					row[4] = element.getType1();
					row[5] = element.getType2();
					row[6] = element.getLevel();
					tableModel2.addRow(row);
					
				}
				
				aux.setEnabled(false);
			}
		}

		if (e.getSource().equals(btnShow)) {
			
			Pokemon element = pc.get(comboBoxPC.getSelectedItem());
			
			Object[] row = new Object[7];
			row[0] = element.getPokedexID();
			row[1] = element.getName();
			row[2] = element.getNickname();
			row[3] = element.getRegion();
			row[4] = element.getType1();
			row[5] = element.getType2();
			row[6] = element.getLevel();
			tableModel2.addRow(row);
			
			comboBoxPC.removeItem(comboBoxPC.getSelectedItem());
		}

		if (e.getSource().equals(btnSwitch)) {
			if (btnSwitch.getText().equalsIgnoreCase("SWITCH MENU")) {
				toggleSwitchMode(true);

				teamPokemonToSwitch.removeAllItems();
				pcPokemonToSwitch.removeAllItems();

				for (Pokemon p : team) {
					teamPokemonToSwitch.addItem(p.getName());
				}
				for (String name : pc.keySet()) {
					pcPokemonToSwitch.addItem(name);
				}

				teamPokemonToSwitch.setSelectedIndex(-1);
				pcPokemonToSwitch.setSelectedIndex(-1);

			} else if (btnSwitch.getText().equalsIgnoreCase("SWITCH")) {
				if (teamPokemonToSwitch.getSelectedIndex() != -1 && pcPokemonToSwitch.getSelectedIndex() != -1) {
					if (JOptionPane.showOptionDialog(panelPC, "Are you sure you want to switch these pokemon?",
							"Confirm switch", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new Object[] { "YES", "NO" }, JOptionPane.YES_OPTION) == 0) {
						Iterator<Pokemon> it = team.iterator();
						boolean found = false;
						while (it.hasNext() && !found) {
							Pokemon p = it.next();
							if (teamPokemonToSwitch.getSelectedItem().equals(p.getName())) {
								try {
									simulable.switchPosition(trainer, p, pc.get(pcPokemonToSwitch.getSelectedItem()));
								} catch (Exception er) {
									JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING",
											JOptionPane.INFORMATION_MESSAGE);
								} finally {
									switchInternally(p, pc.get(pcPokemonToSwitch.getSelectedItem()));
								}
								found = true;
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Select 2 pokemons first", "WARNING",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			pcRefresh();
			
		}
		if (e.getSource().equals(backButton)) {
			toggleSwitchMode(false);
			trainer.setTeam(team);
			setPokemonButtonsAndComboBox();
		}

		if (e.getSource().equals(btnSave)) {
			updateTrainer();
			textAreaTrainInfo.setText(trainer.getTrainerInfo());
			btnUpdate.setVisible(true);
		}
		if (e.getSource().equals(btnUpdate)) {
			try {
				manageable.modifyTrainer(trainer);
			} catch (MyException er) {
				JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.INFORMATION_MESSAGE);
			} finally {
				btnUpdate.setVisible(false);
			}
		}

		if (e.getSource().equals(comboPokemon)) {
			if (comboPokemon.getSelectedIndex() != -1) {
				btnFight.setVisible(true);
			}
		}

		if (e.getSource().equals(btnFight)) {
			Iterator<Trainer> it = trainers.iterator();
			boolean flag = false;
			while (it.hasNext() && !flag) {
				Trainer t = it.next();
				if (comboPokemon.getSelectedItem().equals(t.getName())) {
					LinkedHashSet<Pokemon> auxTeam = t.getTeam();
					if (auxTeam.size() < 6) {
						int i = JOptionPane.showOptionDialog(panelPC,
								"This trainer has an incomplete team, we do not guarantee "
										+ "that the outcome of the fight will be correct. Do you still want to continue?",
								"Confirm switch", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								new Object[] { "YES", "NO" }, JOptionPane.YES_OPTION);
						if (i == 0) {
							battle(t);
						}
					} else {
						battle(t);
					}
					flag = true;
				}
			}
		}

	}

	private void pcRefresh() {
		tableModel2.setRowCount(0);
		btnPok1.setEnabled(true);
		btnPok2.setEnabled(true);
		btnPok3.setEnabled(true);
		btnPok4.setEnabled(true);
		btnPok5.setEnabled(true);
		btnPok6.setEnabled(true);
		
	}

	private void battle(Trainer t) {
		int[] pokemonArr = new int[12];
		LinkedHashSet<Pokemon> auxTeam = t.getTeam();
		Iterator<Pokemon> it = team.iterator();
		Iterator<Pokemon> iter = auxTeam.iterator();
		Combat combat = null;
		int winner;

		int j = 0;
		while (it.hasNext()) {
			pokemonArr[j] = it.next().getPokedexID();
			if (iter.hasNext()) {
				pokemonArr[j + 6] = iter.next().getPokedexID();
			}
			j++;
		}
		try {
			winner = simulable.getWinner(pokemonArr);
			if (winner == 1) {
				JOptionPane.showMessageDialog(null, "You won! ", "CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE);
				combat = new Combat(trainer.getTrainerID(), t.getTrainerID(), trainer.getTrainerID());
			} else if (winner == 0) {
				// ties will not be added to the database, the chance that you tie is so low it
				// makes no sense
				JOptionPane.showMessageDialog(null, "You tied ", "That was close!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "You lost ", "You will get them next time :)",
						JOptionPane.INFORMATION_MESSAGE);
				combat = new Combat(trainer.getTrainerID(), t.getTrainerID(), t.getTrainerID());
			}
			simulable.updateCombatHistory(combat);
		} catch (MyException er) {
			JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void setRandomPokemon() {
		lblRandomLvl.setText(randomLvl());
		lbl_PokemonName.setText(randomPokemon());
	}

	private void addToPc(Pokemon p) {
		p.setTeam(false);
		pc.put(p.getName(), p);
		try {
			simulable.addCaughtPokemon(p, trainer.getTrainerID());
		} catch (MyException er) {
			JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void addToTeam(Pokemon p) {
		p.setTeam(true);
		trainer.addToTeam(p);
		try {
			simulable.addCaughtPokemon(p, trainer.getTrainerID());
		} catch (MyException er) {
			JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private Pokemon buildPokemon(int lvlNumber) {
		Pokemon pokemon = new Pokemon();
		for (PokemonExtra p : dbPokemons) {
			if (lbl_PokemonName.getText().equals(p.getName())) {
				pokemon = p;
				pokemon.setLevel(lvlNumber);
			}
		}
		return pokemon;
	}

	public void getPokemonObj(String pokemonName, boolean b, int lvl) {
		PokemonExtra add = new PokemonExtra();

		for (PokemonExtra p : dbPokemons) {
			if (p.getName().equals(pokemonName)) {
				add.setLevel(lvl);
				add.setName(pokemonName);
				add.setNickname(pokemonName);
				add.setPokedexID(p.getPokedexID());
				add.setRegion(p.getRegion());
				add.setType1(p.getType1());
				add.setType2(p.getType2());
			}
		}
		try {
			simulable.addCaughtPokemon(add, trainer.getTrainerID());
		} catch (MyException er) {
			JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		throw new UnsupportedOperationException("Unimplemented method 'focusGained'");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		throw new UnsupportedOperationException("Unimplemented method 'focusLost'");
	}

	private void setPokemonButtonsAndComboBox() {
		team = trainer.getTeam();
		try {
			trainers = manageable.getTrainers();
			pc = showable.getPcPokemons(trainer.getTrainerID());
		} catch (MyException er) {
			JOptionPane.showMessageDialog(null, er.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
		}

		Pokemon teamStatic[] = new Pokemon[6];
		team.toArray(teamStatic);

		btnPok1.setText(teamStatic[0] == null ? "" : teamStatic[0].getName());
		btnPok2.setText(teamStatic[1] == null ? "" : teamStatic[1].getName());
		btnPok3.setText(teamStatic[2] == null ? "" : teamStatic[2].getName());
		btnPok4.setText(teamStatic[3] == null ? "" : teamStatic[3].getName());
		btnPok5.setText(teamStatic[4] == null ? "" : teamStatic[4].getName());
		btnPok6.setText(teamStatic[5] == null ? "" : teamStatic[5].getName());

		comboBoxPC.removeAllItems();

		for (String name : pc.keySet()) {
			comboBoxPC.addItem(name);
		}
		for (Trainer t : trainers) {
			comboPokemon.addItem(t.getName());
		}
		comboPokemon.setSelectedIndex(-1);
		comboBoxPC.setSelectedIndex(-1);
	}

	private void toggleSwitchMode(Boolean flag) {
		if (flag) {
			btnSwitch.setBounds(713, 250, 153, 21);
			btnSwitch.setText("SWITCH");
		} else {
			btnSwitch.setBounds(796, 250, 153, 21);
			btnSwitch.setText("SWITCH MENU");
		}

		teamPokemonToSwitch.setVisible(flag);
		pcPokemonToSwitch.setVisible(flag);
		backButton.setVisible(flag);

		lblPokePC.setVisible(!flag);
		comboBoxPC.setVisible(!flag);
		btnShow.setVisible(!flag);
		//textAreaPKInfo.setText("");
		btnPok1.setVisible(!flag);
		btnPok2.setVisible(!flag);
		btnPok3.setVisible(!flag);
		btnPok4.setVisible(!flag);
		btnPok5.setVisible(!flag);
		btnPok6.setVisible(!flag);
	}

	private void switchInternally(Pokemon teamPokemon, Pokemon pcPokemon) {
		team.remove(teamPokemon);
		team.add(pcPokemon);

		pc.remove(pcPokemon.getName());
		pc.put(teamPokemon.getName(), teamPokemon);
	}

	private void setPersonalInfo() {
		textUserID.setText(String.valueOf(trainer.getTrainerID()));
		textName.setText(trainer.getName());
		textOrigin.setText(trainer.getOriginCity());
		ageCalender.setDate(trainer.getBirthdate());
		textGender.setText(trainer.getGender());
		textBadges.setText(String.valueOf(trainer.getBadges()));
	}

	private void updateTrainer() {
		java.sql.Date date = new java.sql.Date(ageCalender.getDate().toInstant().toEpochMilli());

		trainer.setName(textName.getText());
		trainer.setBirthdate(date);
		trainer.setGender(textGender.getText());
		trainer.setOriginCity(textOrigin.getText());
		trainer.setBadges(Integer.parseInt(textBadges.getText()));

	}

	private String randomLvl() {
		int lvl = (int) (Math.random() * (100 - 1) + 1);

		return Integer.toString(lvl);
	}

	public String randomPokemon() {
		String pokemonName = null;
		try {
			LinkedHashSet<PokemonExtra> pokemons = dbs.getPokemons();
			int rdm = (int) (Math.random() * (pokemons.size() - 1) + 1);
			int i = 1;

			for (PokemonExtra pokemonExtra : pokemons) {
				if (i == rdm) {
					pokemonName = pokemonExtra.getName();
				}

				i++;
			}
		} catch (MyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
		}
		return pokemonName;
	}

}