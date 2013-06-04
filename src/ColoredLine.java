
public class ColoredLine {
	
	public int xStart;
	public int yStart;
	public int xEnd;
	public int yEnd;
	public int lineWidth;
	
	public ColoredLine(int xStart, int yStart){
		this.xStart = xStart;
		this.yStart = yStart;
	}
	
	public int getLineWidth(){
		return xEnd - xStart;
	}
}
