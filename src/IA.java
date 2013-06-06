import java.util.ArrayList;


public class IA extends Player implements Runnable{
	
	
	private boolean isAlive = true;
	private int activeZone;
	private int enemyZone;
	
	public IA(int id, int MONEY, ArrayList<Zone> ZONELIST, ArrayList<Tower> TOWERLIST, ArrayList<Agent> AGENTLIST, Map MAP) {
		super(id, MONEY, ZONELIST, TOWERLIST, AGENTLIST, MAP);
		
		for(int i=0; i<zones.size(); i++)
		{
			if(zones.get(i).getProprio() == 2)
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
				Thread.sleep(3000);
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
					if(zones.get(i).getProprio() == 2 && ancientActiveZone != i){
						activeZone = i;
						noMoreZone = false;
					}
					if(zones.get(i).getProprio() != 2 && ancientEnemyZone != i){
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
				if(zones.get(activeZone).getNbAgents()>1)
				{
					agents.add(new Agent(2, zones.get(activeZone), zones.get(enemyZone), map));
				}
			}
				
			nbCoups++;
				
		}
	}
	
}
