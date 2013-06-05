
public class ColoredLine {
	
	public int xStart;
	public int xEnd;
	public int y;
	public int lineWidth;
	
	public ColoredLine(int xStart, int y){
		this.xStart = xStart;
		this.y = y;
		this.xEnd = -1;
	}
	
	public int getLineWidth(){
		return xEnd - xStart;
	}
}
