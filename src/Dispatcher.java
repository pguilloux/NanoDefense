import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

class Dispatcher implements ActionListener
{
	/*****VARIABLES******/
	//ArrayList<Player> players;//contenant également les IA
	ArrayList<Zone> zones;//toutes les zones de la map
	//ArrayList<Tower> towers;
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
		
		pan.setLayout(null);
		
		for(int i=0; i<zones.size(); i++)
		{
			zones.get(i).addActionListener(this);
			
			zones.get(i).place();	
	
			zones.get(i).set();
			
			zones.get(i).setBackground(Color.WHITE);
			
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
				//System.out.println("Vous avez cliqué en 1.");
				if(zones.get(i).getActive())
				{
					zones.get(i).setActive(false);
					zones.get(i).setBackground(Color.WHITE);
					//System.out.println("zone1 desactive");
				}
				else 
				{	
					for(int j=0; j<zones.size(); j++)
					{
						if(j!=i)
						{
							if(zones.get(j).getActive())
							{
								//System.out.println("zone2 active");
								if(zones.get(j).getNbAgents()>1)
								{	
									agents.add(new Agent(1,zones.get(i).getProprio(),zones.get(j),zones.get(i)));
									//zones.get(i).setNbAgents(1);
									//zones.get(j).setNbAgents(-1);
								}
							}
							else
							{
								if(zones.get(i).getNbAgents()>0)
								{
									zones.get(i).setActive(true);
									zones.get(i).setBackground(Color.BLACK);
								}
							}
						}
					}
				}
			}			
		}
	}	
}