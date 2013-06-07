import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GameEngine extends JFrame implements Runnable
{
	/*****VARIABLES*******/
	
	//ArrayList<Player> players;//contenant également les IA
	private ArrayList<Zone> zones;//toutes les zones de la map
	ArrayList<Tower> towers;
	private ArrayList<Agent> agents;
	private ArrayList<Bullet> bullets;
	private ArrayList<Player> players;
	private DrawPanel pan;
	private Map map;
	
	/******CONSTRUCTOR******/
	
	 public GameEngine()
	 {  
		zones=new ArrayList<Zone>();
		agents=new ArrayList<Agent>();
		towers=new ArrayList<Tower>();
		bullets=new ArrayList<Bullet>();
		this.setTitle("Animation");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public GameEngine(ArrayList<Zone> zones, ArrayList<Agent> agents, ArrayList<Tower> towers,ArrayList<Player> players, ArrayList<Bullet> bullets,DrawPanel pan, Map map)
	{		  
        this.zones=zones;
        this.agents=agents;
        this.towers=towers;
        this.players=players;
        this.bullets=bullets;
        this.pan=pan;
        this.map=map;
	    this.setTitle("Animation");
	    this.setSize(800, 800);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setContentPane(pan);
	    this.setVisible(true);
	}
	 
	  /******FUNCTIONS******/
	  
	  public void set(ArrayList<Zone> zones, ArrayList<Agent> agents, DrawPanel pan)
	  {		  
        this.zones=zones;
        this.agents=agents;
        this.pan=pan;
        this.setContentPane(pan);
	  }
	  public Zone getZone(int id)
	  {
		  if(!zones.isEmpty())	  
			for(int i=0; i<zones.size(); i++)
			{				
				if(zones.get(i).getZoneId()==id)
					return zones.get(i);				
			}
		return null;
	  }
	  public void setTowerProprio()
	  {
		  if(!towers.isEmpty())
			for(int i=0; i<towers.size(); i++)
			{
				towers.get(i).setProprio(getZone(towers.get(i).getZone()).getProprio());
			}
	  }
	  public void setTowerActive()
	  {
		  if(!towers.isEmpty())
			for(int i=0; i<towers.size(); i++)
			{
				towers.get(i).setActive(getZone(towers.get(i).getZone()).getActive());
			}
	  }
	  public void setZoneIHM()
	  {
		  if(!zones.isEmpty())
				for(int i=0; i<zones.size(); i++)
				{
					for(int k=0; k<4; k++)
					{
						pan.remove(zones.get(i).getGetMod(k));
						pan.remove(zones.get(i).getActiveMod(k));
					}
					if(zones.get(i).getActive())
					{
						for(int k=0; k<4; k++)
						{
							if(zones.get(i).getMod(k)==false)
								pan.add(zones.get(i).getGetMod(k));
							else
								pan.add(zones.get(i).getActiveMod(k));
						}
							
					}
				}
	  }
	  public void setTowerIHM()
	  {
		  if(!towers.isEmpty())
				for(int i=0; i<towers.size(); i++)
				{
					for(int k=0; k<5; k++)
					{
						pan.remove(towers.get(i).getActiveType(k));
						pan.remove(towers.get(i).getUpLevel());
					}
					if(towers.get(i).getActive())
					{
						if(towers.get(i).getLevel()==0)
						for(int k=0; k<5; k++)
						{
							pan.add(towers.get(i).getActiveType(k));
						}
						else
							pan.add(towers.get(i).getUpLevel());	
					}
				}
	  }
	 
	  public void run()
	  {
	    for(;;){
	    	
	    	if(!agents.isEmpty())
				for(int i=0; i<agents.size(); i++)
				{
					pan.remove(agents.get(i));
					//agents.get(i).setText(String.valueOf(nb_agents));
					if(agents.get(i).getLife()<=0 && agents.get(i).getMove())
					{
						if(agents.get(i).getMod(2))
							for(int k=0; k<4; k++)
								agents.add(new Agent(agents.get(i).getProprio(),1,agents.get(i).getx(),agents.get(i).gety()+k,agents.get(i).getPath(),agents.get(i).getZoneStart(),agents.get(i).getZoneStop(),map));
						agents.get(i).setMove(false);
						//agents.remove(agents.get(i));
					}
					if(!agents.get(i).getMove())
						agents.remove(agents.get(i));
					else
					{
						agents.get(i).move();							
						pan.add(agents.get(i));
					}
				}
	    	if(!bullets.isEmpty())
				for(int i=0; i<bullets.size(); i++)
				{
					pan.remove(bullets.get(i));
					//agents.get(i).setText(String.valueOf(nb_agents));
					if(!bullets.get(i).getMove())
						bullets.remove(bullets.get(i));
					
					else
					{
						bullets.get(i).move();							
						pan.add(bullets.get(i));
					}
				}
	    	
	    	setTowerProprio();
	    	setTowerActive();
	    	setZoneIHM();
	    	setTowerIHM();
	    	
	    	for(int k=0; k<zones.size(); k++)
				zones.get(k).set(); 
	    	if(!towers.isEmpty())
	    	for(int k=0; k<towers.size(); k++)
	    	{
	    		towers.get(k).setColor();
				towers.get(k).shoot(); 
	    	}
	    	if(!players.isEmpty())
	    	for(int k=0; k<players.size(); k++)	
	    	{
	    		players.get(k).printMoney();
	    		players.get(k).kill();
	    	}
	    	repaint();
	    	
	      try {
	        Thread.sleep(25);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	  }  
}