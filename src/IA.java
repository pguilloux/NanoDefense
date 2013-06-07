import java.util.ArrayList;


public class IA implements Runnable{
	
	protected int money;
	protected ArrayList<Zone> zones;
	protected ArrayList<Tower> towers;
	protected ArrayList<Agent> agents;
	protected Map map;

	protected int id;
	protected boolean isAlive = true;
	private int activeZone;
	private int enemyZone;
	
	public IA(int id, int MONEY, ArrayList<Zone> ZONELIST, ArrayList<Tower> TOWERLIST, ArrayList<Agent> AGENTLIST, Map MAP) {
		this.id=id;
		this.money = MONEY;
		this.zones = ZONELIST;
		this.towers = TOWERLIST;
		this.agents = AGENTLIST;
		this.map=MAP;

		
		for(int i=0; i<zones.size(); i++)
		{
			if(zones.get(i).getProprio() == id)
				activeZone = i;
			else
				enemyZone = i;
		}
	}
	
	public void run(){
		
		int nbCoups = 0;
		
		int minCoups = 2;
		int maxCoups = 8;

		int random = (int)(Math.random() * (maxCoups-minCoups)) + minCoups;
		
		while(isAlive){
			System.out.println("IA");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("random : "+random);
			if(nbCoups > random){
				int ancientActiveZone = activeZone;
				int ancientEnemyZone = enemyZone;
				boolean noMoreZone = true;
				for(int i=0; i<zones.size(); i++)
				{
					if(zones.get(i).getProprio() == id && ancientActiveZone != i){
						activeZone = i;
						noMoreZone = false;
					}
					if(zones.get(i).getProprio() != id && ancientEnemyZone != i){
						enemyZone = i;
					}
				}
				if(noMoreZone){
					activeZone = -1;
				}
				nbCoups = 0;
				random = (int)(Math.random() * (maxCoups-minCoups)) + minCoups;
			}
			
			if(activeZone != -1){
				if(zones.get(activeZone).getNbAgents()>3)
				{
					if(zones.get(activeZone).getProprio()==id)
						for(int k=0; k<3;k++)
						{
							agents.add(new Agent(id,2, zones.get(activeZone), zones.get(enemyZone), map));
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				}
			}
				
			nbCoups++;
				
		}
	}
	
}
