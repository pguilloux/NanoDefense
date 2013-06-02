import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Dispatcher
{
	/*****VARIABLES******/
	ArrayList<Player> players;//contenant également les IA
	ArrayList<Zone> zones;//toutes les zones de la map
	ArrayList<Tower> towers;
	ArrayList<Agent> agents;
	ArrayList<Bullet> bullets;
	ArrayList<Thread> threads;
	GameEngine engine;
	private DrawPanel pan;
	private Map map;

	/*****CONSTRUCTOR******/
    public Dispatcher()
    {
    	
    	towers= new ArrayList<Tower>();
    	zones= new ArrayList<Zone>();
    	agents= new ArrayList<Agent>();
    	bullets= new ArrayList<Bullet>();
    	map=new Map(zones,towers);
    	map.build("map.txt");
    	pan=new DrawPanel(zones,agents,towers,map);
    	players=new ArrayList<Player>();
    	players.add(new Player(100,zones,towers,agents,map));

    	this.pan=buildContentPane();
    	Thread t = new Thread(new GameEngine(zones,agents,towers, players,bullets,pan, map));
    	threads= new ArrayList<Thread>();
		
		for(int i=0; i<players.size();i++)
		{
			threads.add(new Thread(players.get(i)));
		}
    	
		t.start(); 
		
		
		for(int i=0; i<players.size();i++)
		{
			threads.get(i).start();
		}
			
    	
    }

	/*******FUNCTIONS******/

	private DrawPanel buildContentPane()
	{	
		
		pan.setLayout(null);
		
		towers.add(new Tower(200, 400, 150, agents, bullets, 2, 40));
		towers.add(new Tower(300, 200, 80, agents, bullets, 2, 30));
		
		
		
		for(int i=0; i<zones.size(); i++)
		{
			//zones.get(i).addActionListener(this);	
			
			
			zones.get(i).place();	
	
			zones.get(i).set();
			
			zones.get(i).setColor();
			
			//zones.get(i).setBackground(Color.WHITE);
			
			pan.add(zones.get(i));
		}
		
		for(int i=0; i<towers.size(); i++)
		{
			//zones.get(i).addActionListener(this);	
			
			
			towers.get(i).place();	
	
			//zones.get(i).set();
			
			towers.get(i).setColor();
			
			//zones.get(i).setBackground(Color.WHITE);
			
			pan.add(towers.get(i));
		}
		for(int i=0; i<players.size(); i++)
		{
			//zones.get(i).addActionListener(this);	
			
			
			players.get(i).printMoney();	
	
			//zones.get(i).set();
			
			//towers.get(i).setColor();
			
			players.get(i).getPrintMoney().setBackground(Color.WHITE);
			
			pan.add(players.get(i).getPrintMoney());
		}	
		return pan;
	}
	
}