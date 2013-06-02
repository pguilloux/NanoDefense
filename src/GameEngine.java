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
	 
	  public void run()
	  {
	    for(;;){
	    	
	    	if(!agents.isEmpty())
				for(int i=0; i<agents.size(); i++)
				{
					pan.remove(agents.get(i));
					//agents.get(i).setText(String.valueOf(nb_agents));
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
	    		players.get(k).printMoney();
	    	repaint();
	    	
	      try {
	        Thread.sleep(25);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	  }  
}