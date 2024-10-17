package classes;
/**
 * This class set the structure for the Pokemon object and it his
 * worth mentioning that the team attribute is a boolean that tells if the 
 * Pokemon is on the team or if it is stored in the PC 
 * @author DaniD
 *@version 3
 */
public class Pokemon {

	private int pokedexID;
	private String region;
	private String name;
	private String nickname;
	private String type1;
	private String type2;
	private int level;
	private boolean team;

	public int getPokedexID() {
		return pokedexID;
	}

	public void setPokedexID(int pokedexID) {
		this.pokedexID = pokedexID;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isTeam() {
		return team;
	}

	public void setTeam(boolean team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "ID =" + pokedexID + ", region=" + region + ", name=" + name + ", nickname=" + nickname + "\n type1="
				+ type1 + ", type2=" + type2 + ", level=" + level + ", team=" + team + "\n";
	}
}
