
import java.awt.Color;
import java.util.LinkedList;
import java.util.Vector;
public class Bullet extends RoundButton{
	/*****VARIABLES*****/
	
	private float x;
	private float y;
	private int proprio;
	private Tower tower_start;
	private Agent agent_stop;
	private boolean move;
	private float speed;
	private int type;
	private int hit;

	
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
	public Bullet(Tower tower_start, Agent agent_stop, int type, int hit)
	{
		this.tower_start=tower_start;		
		this.agent_stop=agent_stop;
		this.x=tower_start.getx();
		this.y=tower_start.gety();
		this.type=type;
		this.move=true;
		this.speed = 20;
		this.hit=hit;

	}
	
	
	/********FUNCTIONS*******/ 
	 
	 public void move()
		{
			if(move && agent_stop.getMove())
			{
				float dx=agent_stop.getx()+5-this.x;
				float dy=agent_stop.gety()+5-this.y;
				float absx=(dx<0)?-dx:dx;
				float absy=(dy<0)?-dy:dy;
				if(absx<10 && absy<=10)
				{
					switch(type)
					{
						case 1:
							agent_stop.looseLife(hit);
						break;
						
						case 2:
							agent_stop.looseSpeed(hit);
							if(agent_stop.getSpeed()<=0)
								agent_stop.setSpeed(1);
						break;
						
						case 3:
							agent_stop.giveLife(hit);
							
						break;
						case 4:
							agent_stop.looseLife(hit);
							
					}
					//agent_stop.looseLife(1);
					move=false;

				}
				else
				{
					
		
					float vx=dx/(absx+absy);
					float vy=dy/(absx+absy);
					
					this.x+=vx*speed;
					this.y+=vy*speed;
				}
						
				this.setBounds((int)this.getx(),(int)this.gety(), 3, 3);	
			}
			else
				move =false;
			
			//void active(Tower tower);
		
		}
}


