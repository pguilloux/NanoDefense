import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

class Dispatcher implements ActionListener
{
	/*****VARIABLES******/
	ArrayList<Player> players;//contenant également les IA
	ArrayList<Zone> zones;//toutes les zones de la map
	ArrayList<Tower> towers;
	ArrayList<Agent> agents;
	GameEngine engine;
	private JPanel pan;

	/*****CONSTRUCTOR******/
    public Dispatcher()
    {
    	pan=new JPanel();
    	zones= new ArrayList<Zone>();
    	agents= new ArrayList<Agent>();
    	this.pan=buildContentPane();
    	Thread t = new Thread(new GameEngine(zones,agents,pan));
		t.start(); 
    	
    }

	/*******FUNCTIONS******/

	private JPanel buildContentPane()
	{	
		//panel.setLayout(new FlowLayout());

		zones.add(new Zone(20,400,80));
		zones.add(new Zone(600,400,150,1,100));
		zones.add(new Zone(400,200,100,1,100));
		zones.add(new Zone(300,100,70,2,20));
		
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
									agents.add(new Agent(zones.get(j).getProprio(),zones.get(j),zones.get(i)));
								}
								/*if(zones.get(i).getNbAgents()>0 && zones.get(i).getProprio()==zones.get(j).getProprio())
								{
									zones.get(i).setActive(true);
									//zones.get(i).setBackground(Color.BLACK);
								}*/
							}
							/*else
							{
								if(zones.get(i).getProprio()!=0 && )
								{
									zones.get(i).setActive(true);
									//zones.get(i).setBackground(Color.BLACK);
								}
							}*/
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