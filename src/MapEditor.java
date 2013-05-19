import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class MapEditor extends JFrame implements ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4108996025929159043L;
	
	private Map map;
	private ArrayList<JButton> cases=new ArrayList<JButton>();
	private JPanel pan;

	public MapEditor(String fichier)
	{
		this.map= new Map(fichier);
		//map.build(fichier);
		pan=new JPanel();
		this.setTitle("Animation");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		//System.out.println(map.getWidth()); 
		//System.out.println(map.getHeight()); 
		pan.setLayout(null);
		//cases=new JButton[map.getWidth()*map.getHeight()];
		
		this.setContentPane(build());
		this.setVisible(true);
		
	}
	public JPanel build()
	{
		for(int i=0; i<map.getWidth()*map.getHeight();i++)
		{
			cases.add(new JButton());
			//System.out.println(i); 
			cases.get(i).addActionListener(this);
			cases.get(i).setBounds((i%map.getWidth())*10, (i/map.getWidth())*10, 10, 10);
			if(map.getTable(i)==0)	
				this.cases.get(i).setBackground(new Color(220, 220, 220));
			if(map.getTable(i)==1)	
				this.cases.get(i).setBackground(new Color(100, 100, 100));			
			
			this.pan.add(cases.get(i));
		}
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
		return pan;
	}
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();		
 
		for(int i=0; i<map.getWidth()*map.getHeight(); i++)
		{
			if(source ==cases.get(i))
			{
				if(map.getTable(i)==0)
				{
					map.setTable(i,1);
					
					cases.get(i).setBackground(new Color(100, 100, 100));
				}
				else if(map.getTable(i)==1)
				{
					map.setTable(i,0);
					
					cases.get(i).setBackground(new Color(220, 220, 220));
				}
			}
		}
		repaint();
		this.setFocusable(true);
		this.requestFocus();
	}
		public void keyPressed(KeyEvent event) {
	      System.out.println("Code touche pressée : " + event.getKeyCode() + " - caractère touche pressée : " + event.getKeyChar());
	      if(event.getKeyCode()==83)
	    	 map.save("save.txt");
	    }
	 
	    public void keyReleased(KeyEvent event) {
	      System.out.println("Code touche relâchée : " + event.getKeyCode() + " - caractère touche relâchée : " + event.getKeyChar());                      
	    }
	 
	    public void keyTyped(KeyEvent event) {
	      System.out.println("Code touche tapée : " + event.getKeyCode() + " - caractère touche tapée : " + event.getKeyChar());
	    }   
}