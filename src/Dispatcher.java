import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Dispatcher implements ActionListener
{
	/*****VARIABLES******/
	ArrayList<Player> players;//contenant également les IA
	ArrayList<Zone> zones;//toutes les zones de la map
	ArrayList<Tower> towers;
	ArrayList<Agent> agents;
	GameEngine engine;
	private DrawPanel pan;
	private Map map;

	/*****CONSTRUCTOR******/
    public Dispatcher()
    {
    	
    	towers= new ArrayList<Tower>();
    	zones= new ArrayList<Zone>();
    	agents= new ArrayList<Agent>();
    	map=new Map(zones,towers);
    	map.build("map2.jpg");
    	//map.buildFromImage("map2.jpg");
    	pan=new DrawPanel(zones,agents,towers,map);

    	this.pan=buildContentPane();
    	Thread t = new Thread(new GameEngine(zones,agents,pan, map));
		t.start(); 

    	
    }

	/*******FUNCTIONS******/

	private DrawPanel buildContentPane()
	{	
		
		pan.setLayout(null);
		
		for(int i=0; i<zones.size(); i++)
		{
			zones.get(i).addActionListener(this);			
			
			zones.get(i).place();	
	
			zones.get(i).set();
			
			zones.get(i).setColor();
			
			//zones.get(i).setBackground(Color.WHITE);
			
			pan.add(zones.get(i));
		}		
		return pan;
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