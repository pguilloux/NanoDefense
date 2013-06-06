import java.util.ArrayList;


public class IA extends Player implements Runnable{
	
	
	private boolean isAlive = true;
	
	public IA(int MONEY, ArrayList<Zone> ZONELIST, ArrayList<Tower> TOWERLIST, ArrayList<Agent> AGENTLIST, Map MAP) {
		super(MONEY, ZONELIST, TOWERLIST, AGENTLIST, MAP);
	}
	
	public void run(){
		while(isAlive){
			System.out.println("IA");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
