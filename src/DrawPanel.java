import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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

        g2d.setColor(new Color(212, 212, 212));
        g2d.drawRect(10, 15, 90, 60);
        g2d.drawRect(130, 15, 90, 60);
        g2d.drawRect(250, 15, 90, 60);
        g2d.drawRect(10, 105, 90, 60);
        g2d.drawRect(130, 105, 90, 60);
        g2d.drawRect(250, 105, 90, 60);
        g2d.drawRect(10, 195, 90, 60);
        g2d.drawRect(130, 195, 90, 60);
        g2d.drawRect(250, 195, 90, 60);

        g2d.setColor(new Color(125, 167, 116));
        g2d.fillRect(10, 15, 90, 60);

        g2d.setColor(new Color(42, 179, 231));
        g2d.fillRect(130, 15, 90, 60);

        g2d.setColor(new Color(70, 67, 123));
        g2d.fillRect(250, 15, 90, 60);

        g2d.setColor(new Color(130, 100, 84));
        g2d.fillRect(10, 105, 90, 60);

        g2d.setColor(new Color(252, 211, 61));
        g2d.fillRect(130, 105, 90, 60);

        g2d.setColor(new Color(241, 98, 69));
        g2d.fillRect(250, 105, 90, 60);

        g2d.setColor(new Color(217, 146, 54));
        g2d.fillRect(10, 195, 90, 60);

        g2d.setColor(new Color(63, 121, 186));
        g2d.fillRect(130, 195, 90, 60);

        g2d.setColor(new Color(31, 21, 1));
        g2d.fillRect(250, 195, 90, 60);
	}
	
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);

        
    }
}


