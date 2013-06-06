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
	private boolean[] mod;
	
	private int life;
	private int maxlife;
	private int loadlife;
	
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
	public void setMove(boolean a)
	{ 
		this.move=a; 
	}
	public Zone getZoneStop()
	{ 
		return this.zone_stop; 
	}
	public Zone getZoneStart()
	{ 
		return this.zone_start; 
	}
	public int getProprio()
	{ 
		return this.proprio; 
	}
	public int getLife()
	{
		return life;
	}
	public void looseLife(int nb)
	{
		life-=nb;
	}
	public void looseSpeed(int nb)
	{
		speed-=nb;
	}
	public void giveLife(int nb)
	{
		life+=nb;
	}
	public boolean getMod(int k)
	{
		return mod[k];
	}
	public LinkedList<Vector<Integer>> getPath()
	{
		return path;
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
		this.speed = 2;
		this.life=2;
		this.maxlife=life;
		loadlife=0;
		this.mod=new boolean[4];
		path = map.convertPosToCoord(map.getPathTableToZone(zone_start, zone_stop));
		Vector<Integer> vect = path.pollFirst();
		
		for(int k=0; k<4; k++)
		{
			mod[k]=false;
			if(zone_start.getMod(k))			
				mod[k]=true;
			
		}
			
		if(mod[0])
			speed=4;
		if(mod[1])
			life=20;
	}
	public Agent(int proprio,float x,float y, LinkedList<Vector<Integer>> path, Zone zone_start, Zone zone_stop, Map map)
	{
		//zone_start.setNbAgents(-1);
		this.path=path;		
		this.zone_stop=zone_stop;
		this.zone_start=zone_start;
		this.x=x;
		this.y=y;
		this.proprio=proprio;
		this.map = map;
		this.move=true;
		this.speed = 2;
		this.life=2;
		this.mod=new boolean[4];
		path = map.convertPosToCoord(map.getPathTableToZone(zone_start, zone_stop));
		Vector<Integer> vect = path.pollFirst();
		
		for(int k=0; k<4; k++)
		{
			mod[k]=false;
			if(zone_start.getMod(k))			
				mod[k]=true;
			
		}
			
		if(mod[0])
			speed=4;
		if(mod[1])
			life=20;
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
			if(mod[3] && life<maxlife)
			{
				if(loadlife>20)
				{
					life++;
					loadlife=0;
				}
				else loadlife++;
			}
			
			float absx1, absy1;
			
			if(!path.isEmpty())
			{
				nextX = path.getFirst().elementAt(0);
				nextY = path.getFirst().elementAt(1);
			}
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
				
			}
			else if(!path.isEmpty()){
				Vector<Integer> vect = path.pollFirst();
				dx = vect.elementAt(0)-this.x;
				dy = vect.elementAt(1)-this.y;	
			}
			if(!path.isEmpty()){
			float diffx=zone_stop.getx()+zone_stop.getTaille()/2-this.x;
			float diffy=zone_stop.gety()+zone_stop.getTaille()/2-this.y;
			float absx=(diffx<0)?-diffx:diffx;
			float absy=(diffy<0)?-diffy:diffy;
			
			float vx=(float)(dx/distance);
			float vy=(float)(dy/distance);
			
			/*this.x+=vx*speed;
			this.y+=vy*speed;*/
			this.x += vx*speed;
			this.y += vy*speed;
			if(absx<=zone_stop.getTaille()/2+10 && absy<=zone_stop.getTaille()/2+10)
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
			
			
			
					
			this.setBounds((int)this.getx(),(int)this.gety(), 10, 10);	
			this.setColor();
			}}
		}
		
		//void active(Tower tower);
	}
}

