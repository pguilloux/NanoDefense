import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Map
{
	private int width;
	private int height;	
	private int[] table;
	
	public Map(int width, int height)
	{		
		this.width=width;
		this.height=height;
		table=new int[width*height];
	}
	
	public void build()
	{
		
	}
	
	 /* @Override
     public void paintComponent(Graphics g) 
	 {
         super.paintComponent(g);

		 Graphics2D g2d = (Graphics2D) g;
	
	     g2d.setColor(new Color(212, 212, 212));
	     g2d.drawRect(10, 15, 90, 60);	
	
	     g2d.setColor(new Color(125, 167, 116));
	     g2d.fillRect(10, 15, 90, 60);
	 }*/
	
	
}