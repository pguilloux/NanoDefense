import java.util.ArrayList;

import javax.swing.JButton;

public class Zone extends JButton{
	/*******VARIABLES********/
	private float x; 
	private float y;
	private int taille; 
	private int progress;
	private int proprio; //(0 si neutre)
	private int nb_agents;
	private boolean active;
	//ArrayList<Tower> tours;
	
	/******CONSTRUCTORS*****/
	public Zone(float x, float y, int taille, int proprio, int nb_agents)//zone occupée
	{
		this.x=x;
		this.y=y;
		this.taille=taille;
		this.proprio=proprio;	
		this.nb_agents=nb_agents;
	}
	public Zone(float x, float y, int taille)//zone neutre
	{
		this.x=x;
		this.y=y;
		this.taille=taille;
		this.proprio=0;	
		this.nb_agents=-taille;
	}
	
	/********GET&SET*********/
	public float getx()
	{ 
		return this.x; 
	}
	public float gety()
	{ 
		return this.y; 
	}
	public int getTaille()
	{ 
		return this.taille; 
	}
	public int getNbAgents()
	{ 
		return this.nb_agents; 
	}
	public int getProprio()
	{ 
		return this.proprio; 
	}
	public void setNbAgents(int nb)
	{ 
		this.nb_agents+=nb; 
	}
	public void setProprio(int proprio)
	{ 
		this.proprio=proprio; 
	}
	public void setActive(boolean a)
	{ 
		this.active=a; 
	}
	public boolean getActive()
	{ 
		return this.active; 
	}
	public void setProgress(int p)
	{ 
		this.progress+=p; 
	}
	public void resetProgress()
	{ 
		this.progress=0; 
	}
	public int getProgress()
	{ 
		return this.progress; 
	}
	
	/********FUNCTIONS********/

	public void place()
	{		
		this.setBounds((int)this.getx(),(int)this.gety(), this.taille, this.taille);	
	}
	public void set()
	{
		if(getNbAgents()>0)
		{
			setProgress(getTaille()/10);
			if(getProgress()>=100)
			{
				setNbAgents(1);
				resetProgress();				
			}
		}
		this.setText(String.valueOf(nb_agents));
	}

	public void sendAgents(int nb, Zone destination){}
	//private void activeTower(int nb_agents, Tour tour);
}