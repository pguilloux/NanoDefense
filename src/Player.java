import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Player implements Runnable, ActionListener
{
	private int money;
	private ArrayList<Zone> zones;
	private ArrayList<Tower> towers;
	private ArrayList<Agent> agents;
	private Map map;
	
	/* ----------------------------- */
	/*   Déclaration des méthodes    */
	/* ----------------------------- */
	
	public Player() {
		this.money = 0;
		this.zones = new ArrayList<Zone>();
		this.towers = new ArrayList<Tower>();
		this.agents= new ArrayList<Agent>();
		
	}
	
	public Player(int MONEY, ArrayList<Zone> ZONELIST, ArrayList<Tower> TOWERLIST, ArrayList<Agent> AGENTLIST, Map MAP) {
		this.money = MONEY;
		this.zones = ZONELIST;
		this.towers = TOWERLIST;
		this.agents = AGENTLIST;
		this.map=MAP;
		for(int i=0; i<zones.size(); i++)
			zones.get(i).addActionListener(this);
		
	}
	
	/* ----------------------------- */
	/*    Déclaration des GETERS     */
	/* ----------------------------- */
	
	public int getMoney() {
		return this.money;
	}
	
	public ArrayList<Zone> getZoneList() {
		return this.zones;
	}
	
	public ArrayList<Tower> getTowerList() {
		return this.towers;
	}
	
	/* ----------------------------- */
	/*    Déclaration des SETERS     */
	/* ----------------------------- */
	
	public void setMoney(int MONEY) {
		this.money = MONEY;
	}
	
	public void setZoneList(ArrayList<Zone> ZONELIST) {
		this.zones = ZONELIST;
	}
	
	public void setTowerList(ArrayList<Tower> TOWERLIST) {
		this.towers = TOWERLIST;
	}
	
	/*******FUNCTIONS*********/
	
	public void run()
	{
		
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
 
		for(int i=0; i<zones.size(); i++)
		{
			if(source == zones.get(i))
			{
				if(zones.get(i).getActive())
				{
					zones.get(i).setActive(false);
					//zones.get(i).setBackground(Color.WHITE);
				}
				else 
				{	
					int zap=0;//zones actives en possession
					int zae=0;//zones actives ennemies
					for(int j=0; j<zones.size(); j++)
					{
						if(j!=i)
						{
							if(zones.get(j).getActive())
							{
								if(zones.get(i).getProprio()!=zones.get(j).getProprio())
								{	
									zae++;
								}
								else if(zones.get(i).getProprio()==zones.get(j).getProprio())
								{
									zap++;
								}
								if(zones.get(j).getNbAgents()>1 && zones.get(i).getProprio()!=zones.get(j).getProprio())
								{	
									agents.add(new Agent(zones.get(j).getProprio(),zones.get(j),zones.get(i), map));
								}
							
							}
						
						}
					}
					if(zap>=0 && zae==0 && zones.get(i).getProprio()!=0)
					{
						zones.get(i).setActive(true);
					}
				}
			}			
		}
	}	
	
}



