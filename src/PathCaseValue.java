
public class PathCaseValue implements Comparable{
	
	private int distance; //le nombre correspondant à la distance à la zone
	private int position; // la position de la case dans le tableau de calcul du chemin d'une zone
	
	
	public PathCaseValue(int dist, int pos){
		distance = dist;
		position = pos;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
