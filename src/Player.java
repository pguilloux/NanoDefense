import java.util.ArrayList;

public class Player
{
	private int money;
	private ArrayList<Zone> zoneList;
	private ArrayList<Tower> towerList;
	
	/* ----------------------------- */
	/*   Déclaration des méthodes    */
	/* ----------------------------- */
	
	public Player() {
		this.money = 0;
		this.zoneList = new ArrayList<Zone>();
		this.towerList = new ArrayList<Tower>();
	}
	
	public Player(int MONEY, ArrayList<Zone> ZONELIST, ArrayList<Tower> TOWERLIST) {
		this.money = MONEY;
		this.zoneList = ZONELIST;
		this.towerList = TOWERLIST;
	}
	
	/* ----------------------------- */
	/*    Déclaration des GETERS     */
	/* ----------------------------- */
	
	public int getMoney() {
		return this.money;
	}
	
	public ArrayList<Zone> getZoneList() {
		return this.zoneList;
	}
	
	public ArrayList<Tower> getTowerList() {
		return this.towerList;
	}
	
	/* ----------------------------- */
	/*    Déclaration des SETERS     */
	/* ----------------------------- */
	
	public void setMoney(int MONEY) {
		this.money = MONEY;
	}
	
	public void setZoneList(ArrayList<Zone> ZONELIST) {
		this.zoneList = ZONELIST;
	}
	
	public void setTowerList(ArrayList<Tower> TOWERLIST) {
		this.towerList = TOWERLIST;
	}
	
}



