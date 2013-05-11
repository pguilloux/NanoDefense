import java.awt.Color;

import javax.swing.JButton;
public class Agent extends RoundButton{
	/*****VARIABLES*****/
	
	private float x;
	private float y;
	private int proprio;
	private Zone zone_start;
	private Zone zone_stop;
	private boolean move;
	private int speed;
	
	/*****GET&SET******/
	
	public float getx()
	{ 
		return this.x; 
	}
	public float gety()
	{ 
		return this.y; 
	}
	public boolean getMove()
	{ 
		return this.move; 
	}
	public int getProprio()
	{ 
		return this.proprio; 
	}
	
	/******CONSTRUCTOR*******/
	
	public Agent(int proprio, Zone zone_start, Zone zone_stop)
	{
		zone_start.setNbAgents(-1);
		this.zone_start=zone_start;		
		this.zone_stop=zone_stop;
		this.x=zone_start.getx()+zone_start.getTaille()/2;
		this.y=zone_start.gety()+zone_start.getTaille()/2;
		this.proprio=proprio;
		this.move=true;
		this.speed=5;
	}
	
	/********FUNCTIONS*******/
	
	 public void setColor()
    {
    	switch(this.getProprio())
    	{    		
    		case 1:
    			this.setBackground(Color.RED);
    		break;
    	
    		case 2:
    			this.setBackground(Color.BLUE);
    		break;
    		
    		default:
    			this.setBackground(Color.WHITE);
    		break;
    	}
    }
	public void move()
	{
		if(move)
		{
			float dx=zone_stop.getx()+zone_stop.getTaille()/2-this.x;
			float dy=zone_stop.gety()+zone_stop.getTaille()/2-this.y;
			float absx=(dx<0)?-dx:dx;
			float absy=(dy<0)?-dy:dy;
			if(absx<1 && absy<=1)
			{		
				if(zone_stop.getProprio()==this.getProprio())
				{
					zone_stop.setNbAgents(1);
				}
				else if(zone_stop.getProprio()==0)
				{
					zone_stop.setNbAgents(1);
					if(zone_stop.getNbAgents()>0)
						zone_stop.setProprio(this.proprio);
				}
				else if(zone_stop.getProprio()!=this.getProprio())
				{
					zone_stop.setNbAgents(-1);
					if(zone_stop.getNbAgents()==0)
						zone_stop.setProprio(0);
					
				}
				move=false;
			}
			else
			{
				
	
				float vx=dx/(absx+absy);
				float vy=dy/(absx+absy);
				
				this.x+=vx*speed;
				this.y+=vy*speed;
			}
					
			this.setBounds((int)this.getx(),(int)this.gety(), 10, 10);	
			this.setColor();
		}
		
		//void active(Tower tower);
	
	}
}

