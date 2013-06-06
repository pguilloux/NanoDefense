import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

public class Zone extends RoundButton{
	/*******VARIABLES********/
	private float zoneId;
	private float x; 
	private float y;
	private int taille;
	private int nb_max_agents;
	private int progress;
	private int proprio; //(0 si neutre)
	private int nb_agents;
	private boolean active;
	private boolean[] mod;
	private JButton[] get_mod;
	private RoundButton[] active_mod;
	private int[] pathMap;
	private int indexInPath;
	private ArrayList<Tower> tours;
	
	/******CONSTRUCTORS*****/
	public Zone(int id, float x, float y, int taille, int proprio, int nb_agents)//zone occupée
	{
		this.zoneId = id;
		this.x=x;
		this.y=y;
		this.taille=taille;
		this.progress=100/taille;
		this.nb_max_agents=taille/10;
		this.proprio=proprio;	
		this.nb_agents=nb_agents;
		mod=new boolean[4];
		get_mod=new JButton[4];
		active_mod=new RoundButton[4];	
		for(int i=0; i<4; i++)
		{
			get_mod[i]=new JButton();
			active_mod[i]=new RoundButton();
		}
	}
	
	public Zone(float x, float y, int taille)//zone neutre
	{
		this.x=x;
		this.y=y;
		this.taille=taille;
		this.proprio=0;	
		this.nb_agents=-taille;
		this.nb_max_agents=taille/10;
		this.progress=100/taille;
		mod=new boolean[4];
		get_mod=new JButton[4];
		active_mod=new RoundButton[4];
		for(int i=0; i<4; i++)
		{
			get_mod[i]=new JButton();
			active_mod[i]=new RoundButton();
		}
	}
	
	/********GET&SET*********/
	public float getZoneId()
	{ 
		return this.zoneId; 
	}
	
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
	public void setx(int x)
	{ 
		this.x=x; 
	}
	public void sety(int y)
	{ 
		this.y=y; 
	}
	public void setTaille(int taille)
	{ 
		this.taille=taille; 
	}
	public int getNbAgents()
	{ 
		return this.nb_agents; 
	}
	public int getProprio()
	{ 
		return this.proprio; 
	}
	public void nbAgents(int nb)
	{ 
		this.nb_agents=nb; 
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
	public int[] getPathMap() {
		return pathMap;
	}
	public void setPathMap(int position, int distance) {
		this.pathMap[position] = distance;
	}
	public int getIndexInPath() {
		return indexInPath;
	}
	public void setIndexInPath(int indexInPath) {
		this.indexInPath = indexInPath;
	}
	public boolean getMod(int k)
	{
		return mod[k];
	}
	public void setMod(int k, boolean a)
	{
		mod[k]=a;
	}
	public JButton getGetMod(int k)
	{
		return get_mod[k];
	}
	public RoundButton getActiveMod(int k)
	{
		return active_mod[k];
	}
	/********FUNCTIONS********/
    public void setColor()
    {
    	if(getActive())
		switch(this.getProprio())
    	{    		
    		case 1:
    			this.setBackground(new Color(255, 0, 0));
    		break;
    	
    		case 2:
    			this.setBackground(new Color(0, 0, 255));
    		break;
    		
    		default:
    			this.setBackground(new Color(0, 0, 0));
    		break;
    	}
    	else
    	switch(this.getProprio())
    	{
    		
    		case 1:
    			this.setBackground(new Color(180, 0, 0));
    		break;
    	
    		case 2:
    			this.setBackground(new Color(0, 0, 180));
    		break;
    		
    		default:
    			this.setBackground(new Color(255, 255, 255));
    		break;
    	}
    }
    
	public void place()
	{		
		this.setBounds((int)this.getx(),(int)this.gety(), this.taille, this.taille);	
		for(int i=0; i<4; i++)
		{
			get_mod[i].setBounds((int)this.getx()+taille/4*i,(int)this.gety()-10, 10, 10);
			active_mod[i].setBounds((int)this.getx()+taille/4*i,(int)this.gety()+5+taille, 10, 10);
		}
		get_mod[0].setBackground(Color.WHITE);
		get_mod[1].setBackground(Color.RED);
		get_mod[2].setBackground(Color.GREEN);
		get_mod[3].setBackground(Color.BLUE);
		active_mod[0].setBackground(Color.WHITE);
		active_mod[1].setBackground(Color.RED);
		active_mod[2].setBackground(Color.GREEN);
		active_mod[3].setBackground(Color.BLUE);
	}
	
	public void buildPathMap(int width, int height){
		pathMap = new int[width*height];
	}
	
	public void set()
	{
		if(getNbAgents()>0 && getNbAgents()<nb_max_agents)
		{
			setProgress(getTaille()/50);
			if(getProgress()>=100)
			{
				setNbAgents(1);
				resetProgress();				
			}
		}
		this.setColor();
		this.setText(String.valueOf(nb_agents));
	}
	
	

	public void sendAgents(int nb, Zone destination){}
	//private void activeTower(int nb_agents, Tour tour);
}