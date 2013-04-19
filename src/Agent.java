import javax.swing.JButton;
public class Agent extends JButton{
	/*****VARIABLES*****/
	private float x;
	private float y;
	private int nb_agents;//pour les groupes (puisque les agents se déplacent rarement seuls)
	private int proprio;
	private Zone zone_start;
	private Zone zone_stop;
	private boolean move;
	
	/*****GET&SET******/
	public float getx()
	{ 
		return this.x; 
	}
	public float gety()
	{ 
		return this.y; 
	}
	
	/******CONSTRUCTOR*******/
	public Agent(int nb_agents, int proprio, Zone zone_start, Zone zone_stop)
	{
		zone_start.setNbAgents(-nb_agents);
		this.zone_start=zone_start;		
		this.zone_stop=zone_stop;
		this.x=zone_start.getx();
		this.y=zone_start.gety();
		this.nb_agents=nb_agents;
		this.proprio=proprio;
		this.move=true;
	}
	public void move()
	{
		if(move)
		{
			float dx=zone_stop.getx()-this.x;
			float dy=zone_stop.gety()-this.y;
			float absx=(dx<0)?-dx:dx;
			float absy=(dy<0)?-dy:dy;
			if(absx<1 && absy<=1)
			{
				
				zone_stop.setNbAgents(nb_agents);
				move=false;
				try {
					finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				
	
				float vx=dx/(absx+absy);
				float vy=dy/(absx+absy);
				
				this.x+=vx;
				this.y+=vy;
			}
					
			this.setBounds((int)this.getx(),(int)this.gety(), 10, 10);	
		}
		
		//void active(Tower tower);
	
	}
}

