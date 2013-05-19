import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DrawPanel extends JPanel{
	private ArrayList<Zone> zones;
	private ArrayList<Agent> agents;
	private ArrayList<Tower> towers;
	private Map map;
	
	public DrawPanel( ArrayList<Zone> zones,ArrayList<Agent> agents,ArrayList<Tower> towers,Map map)
	{
		this.zones=zones;
        this.agents=agents;
        this.towers=towers;
        this.map=map;
	}
	public void drawMap(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		for(int i=0; i<map.getWidth()*map.getHeight();i++)
		{
			if(map.getTable(i)==0)	
				g2d.setColor(new Color(220, 220, 220));
			if(map.getTable(i)==1)	
				g2d.setColor(new Color(100, 100, 100));
       
			g2d.drawRect((i%map.getWidth())*10, (i/map.getWidth())*10, 10, 10);
			g2d.fillRect((i%map.getWidth())*10, (i/map.getWidth())*10, 10, 10);
		}

	}	
	

	
	
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);        
    }
}


