import java.awt.Color;
import java.util.LinkedList;
import java.util.Vector;
public class Agent extends RoundButton{
	/*****VARIABLES*****/
	
	private float x;
	private float y;
	private float nextX;
	private float nextY;
	private int proprio;
	private Zone zone_start;
	private Zone zone_stop;
	private boolean move;
	private float speed;
	private Map map;
	private LinkedList<Vector<Integer>> path;
	
	private double angle;
	private double distance;
	private float dx;
	private float dy;
	private float mvtLength;
	
	private int life;
	
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
	public void looseLife(int nb)
	{
		life-=nb;
	}
	/******CONSTRUCTOR*******/
	public Agent(int proprio, Zone zone_start, Zone zone_stop, Map map)
	{
		zone_start.setNbAgents(-1);
		this.zone_start=zone_start;		
		this.zone_stop=zone_stop;
		this.x=zone_start.getx()+zone_start.getTaille()/2;
		this.y=zone_start.gety()+zone_start.getTaille()/2;
		this.proprio=proprio;
		this.map = map;
		this.move=true;
		this.speed = 3;
		this.life=3;
		
		path = map.convertPosToCoord(map.getPathTableToZone(zone_start, zone_stop));
		Vector<Integer> vect = path.pollFirst();

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
			if(life<=0)
				move=false;
			float absx1, absy1;
			
			nextX = path.getFirst().elementAt(0);
			nextY = path.getFirst().elementAt(1);
			distance= Math.hypot(nextX-x, nextY-y);
			mvtLength = (int)(distance/speed);
					
			if(!path.isEmpty()){
				dx = path.getFirst().elementAt(0)-this.x;
				dy = path.getFirst().elementAt(1)-this.y;
			
				absx1 = (dx<0)?-dx:dx;
				absy1 = (dy<0)?-dy:dy;
			
			//System.out.println("absx1: "+absx1+" absy1: "+absy1+" case:"+path.getFirst());
			//if(this.x != path.getFirst().elementAt(0) && this.y != path.getFirst().elementAt(1)){
			//if(Math.abs(path.getFirst().elementAt(0) - this.x) < 1 && Math.abs(path.getFirst().elementAt(1)) -this.y < 1){
			if(absx1>1.7 && absy1>=1.7){
				dx = path.getFirst().elementAt(0)-this.x;
				dy = path.getFirst().elementAt(1)-this.y;
				System.out.println(distance);
				
			}
			else if(!path.isEmpty()){
				Vector<Integer> vect = path.pollFirst();
				dx = vect.elementAt(0)-this.x;
				dy = vect.elementAt(1)-this.y;	
				System.out.println("toto");
			}
			if(!path.isEmpty()){
			/*float dx=zone_stop.getx()+zone_stop.getTaille()/2-this.x;
			float dy=zone_stop.gety()+zone_stop.getTaille()/2-this.y;*/
			float absx=(dx<0)?-dx:dx;
			float absy=(dy<0)?-dy:dy;
			
			System.out.println(path.getFirst());
			
			if(absx<3 && absy<=3 && path.getFirst().elementAt(0) == path.getLast().elementAt(0) && path.getFirst().elementAt(1) == path.getLast().elementAt(1))
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
			
			float vx=(float)(dx/distance);
			float vy=(float)(dy/distance);
			
			/*this.x+=vx*speed;
			this.y+=vy*speed;*/
			this.x += vx*speed;
			this.y += vy*speed;
			
					
			this.setBounds((int)this.getx(),(int)this.gety(), 10, 10);	
			this.setColor();
			}}
		}
		
		//void active(Tower tower);
	}
}

