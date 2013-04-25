import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPanel pan;
	

	
	/******CONSTRUCTOR******/

	
	 
	 public GameEngine()
	 {  
		zones=new ArrayList<Zone>();
		agents=new ArrayList<Agent>();
		this.setTitle("Animation");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	    //this.setContentPane(buildContentPane());
		this.setVisible(true);
	    //go();
	}
	  public GameEngine(ArrayList<Zone> zones, ArrayList<Agent> agents, JPanel pan)
	  {
		  
        this.zones=zones;
        this.agents=agents;
        this.pan=pan;
	    this.setTitle("Animation");
	    this.setSize(800, 800);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setContentPane(pan);
	    this.setVisible(true);
	    //go();
	  }
	  /******FUNCTIONS******/
	  public void set(ArrayList<Zone> zones, ArrayList<Agent> agents, JPanel pan)
	  {
		  
        this.zones=zones;
        this.agents=agents;
        this.pan=pan;
        this.setContentPane(pan);

	  }
	 
	  public void run()
	  {
	    for(;;){
	    	for(int k=0; k<zones.size(); k++)
				zones.get(k).set(); 
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
	    	repaint();
	      try {
	        Thread.sleep(25);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	  }  
	  



}