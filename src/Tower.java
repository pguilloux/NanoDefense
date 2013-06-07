import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

public class Tower extends RoundedCornerButton
{
	private int type;
	private JButton up_level;
	private JButton[] active_type;
	private int level;
	private int hit;
	private float x;
	private float y;
	private int price;
	private int taille;
	private int influence;
	private int pas;
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
	public JButton getActiveType(int k)
	{ 
		return active_type[k]; 
	}
	public JButton getUpLevel()
	{ 
		return up_level; 
	}
	public int getLevel()
	{ 
		return level; 
	}
	public int getInfluence()
	{ 
		return influence; 
	}
	public void upLevel()
	{ 
		if(level<5)
		++level; 
	}
	public void setType(int k)
	{ 
		type=k; 
	}
	
	public Tower(float x, float y, ArrayList<Agent> agents, ArrayList<Bullet> bullets)
	{
		this.x=x;
		this.y=y;
		
		this.agents=agents;
		this.bullets=bullets;
		level=0;
		this.taille=20;
		cadence=0;
		pas=10;
		this.influence=50;
		haveCible=false;
		type=0;
		up_level=new JButton();
		active_type= new JButton[5];
		for(int i=0;i<5;i++)
		{
			active_type[i]=new JButton();
		}
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
		up_level.setBounds((int)this.getx()+taille/2-10,(int)this.gety()-20, 20, 20);
		for(int i=0;i<5;i++)
		{
			active_type[i].setBounds((int)this.getx()+10*i,(int)this.gety()-10, 10, 10);
		}
	}
	public void set()
	{		
		taille=(level+1)*(15);
		influence=50*level;
		pas=30/level;
		hit=level;
		this.setBounds((int)this.getx(),(int)this.gety(), this.taille, this.taille);
		if(level<5)
		up_level.setBounds((int)this.getx()+taille/2-10,(int)this.gety()-20, 20, 20);
		else 
			up_level.setBounds((int)this.getx()+taille/2-10,(int)this.gety()-20, 0, 0);
		for(int i=0;i<5;i++)
		{
			active_type[i].setBounds((int)this.getx()+10*i,(int)this.gety()-10, 10, 10);
		}
	}


	public void shoot()
	{
		if(level!=0)
		if(haveCible && cible.getMove())
			if(cible.getx()>x-influence && cible.getx()<x+influence && cible.gety()>y-influence && cible.gety()<y+influence)
			if(cadence>pas)
			{
				if(type==4)
					hit++;
				bullets.add(new Bullet(this,cible,type,hit));
				cadence=0;
			}
			else
				cadence++;
			else
				haveCible=false;
		else
		for(int i=0; i<agents.size(); i++)
		{
			if(agents.get(i).getProprio()!=proprio && type!=3)
			if(agents.get(i).getx()>x-influence && agents.get(i).getx()<x+influence && agents.get(i).gety()>y-influence && agents.get(i).gety()<y+influence)
			{
				
				cible=agents.get(i);
				haveCible=true;				
			}
			if(agents.get(i).getProprio()==proprio && type==3)
				if(agents.get(i).getx()>x-influence && agents.get(i).getx()<x+influence && agents.get(i).gety()>y-influence && agents.get(i).gety()<y+influence)
				{
					
					cible=agents.get(i);
					haveCible=true;	
					hit=1;
				}
			
		}
			
	}
}