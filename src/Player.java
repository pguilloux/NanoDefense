import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Player implements Runnable, ActionListener
{

	protected int money;
	protected ArrayList<Zone> zones;
	protected ArrayList<Tower> towers;
	protected ArrayList<Agent> agents;
	protected Map map;
	protected RoundedCornerButton money_print;
	protected int id;
	protected boolean isAlive = true;
	int pas=0;
	
	/* ----------------------------- */
	/*   Déclaration des méthodes    */
	/* ----------------------------- */
	
	public Player() {
		this.money = 0;
		this.money_print=new RoundedCornerButton();
		this.zones = new ArrayList<Zone>();
		this.towers = new ArrayList<Tower>();
		this.agents= new ArrayList<Agent>();
		
	}
	
	public Player(int id,int MONEY, ArrayList<Zone> ZONELIST, ArrayList<Tower> TOWERLIST, ArrayList<Agent> AGENTLIST, Map MAP) {
		this.id=id;
		this.money = MONEY;
		this.zones = ZONELIST;
		this.towers = TOWERLIST;
		this.agents = AGENTLIST;
		this.map=MAP;
		this.money_print=new RoundedCornerButton();
		for(int i=0; i<zones.size(); i++)
		{
			zones.get(i).addActionListener(this);
			for(int k=0; k<4; k++)
			{				
				zones.get(i).getGetMod(k).addActionListener(this);				
			}
		}
		for(int i=0; i<towers.size(); i++)
		{
			towers.get(i).getUpLevel().addActionListener(this);
			for(int k=0; k<5; k++)
			{				
				towers.get(i).getActiveType(k).addActionListener(this);				
			}
		}
		
	}
	
	/* ----------------------------- */
	/*    Déclaration des GETERS     */
	/* ----------------------------- */
	
	public int getMoney() {
		return this.money;
	}
	public void pay(int nb)
	{
		money-=nb;
	}
	public void win(int nb)
	{
		money+=nb;
	}
	
	public ArrayList<Zone> getZoneList() {
		return this.zones;
	}
	
	public ArrayList<Tower> getTowerList() {
		return this.towers;
	}
	public RoundedCornerButton getPrintMoney() {
		return this.money_print;
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
	public void printMoney()
	{	
		money_print.setText(String.valueOf(money));
		money_print.setBounds(0,0, 100, 20);			
	}
	public void run()
	{
		for(;;)
		{

			int gain=0;
			for(int i=0; i<zones.size();i++)
			{
				if(zones.get(i).getProprio()==id)
					gain+=zones.get(i).getTaille()/100;
			}
			
			if(pas>100000000/gain)
			{
				money+=gain;
				pas=0;
			}
			else 
				pas++;
		}
	}
	public void kill()
	{
		
			for(int i=0; i<zones.size(); i++)
			{
				if(zones.get(i).getProprio() != id && zones.get(i).getActive() ){
	
					zones.get(i).setActive(false);
				}
			}
		
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		for(int i=0; i<towers.size(); i++)
		{	
			if(source==towers.get(i).getUpLevel())
			{
				if(money>40*towers.get(i).getLevel())
				{
					pay(40*towers.get(i).getLevel());
					towers.get(i).upLevel();
					towers.get(i).set();
				
				}
			}
			for(int k=0; k<5; k++)
			{
				if(source==towers.get(i).getActiveType(k))
				{
					if(money>20*k)
					{
						pay(20*k);
						towers.get(i).setType(k+1);
						towers.get(i).upLevel();
						towers.get(i).set();
					//System.out.println(zones.get(i).getMod(k));
					}
				}
			}
		}
		for(int i=0; i<zones.size(); i++)
		{
			
			if(source == zones.get(i))
			{
				if(zones.get(i).getActive())
				{
					zones.get(i).setActive(false);
					System.out.println("1"+zones.get(0).getActive());
					break;
					
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
									agents.add(new Agent(zones.get(j).getProprio(),this,2,zones.get(j),zones.get(i), map));
								}
							
							}
						
						}
					}
					if(zap>=0 && zae==0 && zones.get(i).getProprio()==id)
					{
						zones.get(i).setActive(true);
					}
					
				}
			}
			else
				for(int k=0; k<4; k++)
				{
					int nb=1;
				
					for(int j=0; j<4; j++)
					{
						if(zones.get(i).getMod(j))
							nb++;
					}
					
					if(source==zones.get(i).getGetMod(k))
					{
						
						if(money>20*nb)
						{
						zones.get(i).setMod(k, true);
						pay(20*nb);
						}
						//System.out.println(zones.get(i).getMod(k));
					}
				}
		}
		System.out.println("2"+zones.get(0).getActive());
	}
	
	
}



