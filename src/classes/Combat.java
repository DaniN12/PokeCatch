package classes;
/**
 * This is the Class combat where the object combat's structure is defined.
 * Here we can find 3 different attributes:
 * trainer1 the trainerID of the trainer that asked for the match
 * trainer2 the trainerIDof the other trainer
 * winner the trainerID of the one that won
 * @author DaniD
 * @version 2
 *
 */
public class Combat {

	private int trainer1;
	private int trainer2;
	private int winnerTrainerID;

	public Combat(){
		super();
	}
	public Combat(int id1, int id2, int winner){
		super();
		this.trainer1 = id1;
		this.trainer2 = id2;
		this.winnerTrainerID = winner;
	}

	public int getTrainer1() {
		return trainer1;
	}

	public void setTrainer1(int trainer1) {
		this.trainer1 = trainer1;
	}

	public int getTrainer2() {
		return trainer2;
	}

	public void setTrainer2(int trainer2) {
		this.trainer2 = trainer2;
	}

	public int getWinnerTrainerID() {
		return winnerTrainerID;
	}

	public void setWinnerTrainerID(int winnerTrainerID) {
		this.winnerTrainerID = winnerTrainerID;
	}

	@Override
	public String toString() {
		return "Combat [trainer1=" + trainer1 + ", trainer2=" + trainer2 + ", winnerTrainerID=" + winnerTrainerID
				+ "]\n";
	}
}
