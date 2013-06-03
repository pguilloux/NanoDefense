import java.awt.Color;
import java.util.ArrayList;

public class Tower extends RoundedCornerButton
{
	private int type;
	private int level;
	private float x;
	private float y;
	private int price;
	private int taille;
	private float influence;
	private int zone;
	private boolean active;
	private int proprio;
	private int cadence;
	private Agent cible;
	private boolean haveCible;
	private ArrayList<Agent> agents;
	private ArrayList<Bullet> bullets;
	
	public float getx()
	{ 
		return this.x; 
	}
	public float gety()
	{ 
		return this.y; 
	}
	public int getProprio()
	{ 
		return this.proprio; 
	}
	
	public void setProprio(int proprio)
	{ 
		this.proprio=proprio; 
	}
	public void setZone(int zone)
	{ 
		this.zone=zone; 
	}
	public int getZone()
	{ 
		return zone; 
	}
	public void setActive(boolean a)
	{ 
		this.active=a; 
	}
	public boolean getActive()
	{ 
		return this.active; 
	}
	
	public Tower(float x, float y, float influence, ArrayList<Agent> agents, ArrayList<Bullet> bullets, int taille)
	{
		this.x=x;
		this.y=y;
		this.influence=influence;
		this.agents=agents;
		this.bullets=bullets;
		this.taille=taille;
		cadence=0;
		haveCible=false;
	}
	
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
	}
	public void setProprio()
	{
		
	}
	public void shoot()
	{
		if(haveCible && cible.getMove())
			if(cible.getx()>x-influence && cible.getx()<x+influence && cible.gety()>y-influence && cible.gety()<y+influence)
			if(cadence>20)
			{
				bullets.add(new Bullet(this,cible));
				cadence=0;
			}
			else
				cadence++;
			else
				haveCible=false;
		else
		for(int i=0; i<agents.size(); i++)
		{
			if(agents.get(i).getProprio()!=proprio)
			if(agents.get(i).getx()>x-influence && agents.get(i).getx()<x+influence && agents.get(i).gety()>y-influence && agents.get(i).gety()<y+influence)
			{
				
				cible=agents.get(i);
				haveCible=true;				
			}
		}
	}
}