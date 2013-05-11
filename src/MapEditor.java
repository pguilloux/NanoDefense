import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class MapEditor extends JFrame implements ActionListener
{
	private Map map;
	private JButton[] cases=new JButton[80*80];
	private JPanel pan;

	public MapEditor(String fichier)
	{
		this.map= new Map(fichier);
		map.build(fichier);
		pan=new JPanel();
		this.setTitle("Animation");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		System.out.println(map.getWidth()); 
		System.out.println(map.getHeight()); 
		pan.setLayout(null);
		//cases=new JButton[map.getWidth()*map.getHeight()];
		
		this.setContentPane(build());
		this.setVisible(true);
	}
	public JPanel build()
	{
		for(int i=0; i<map.getWidth()*map.getHeight();i++)
		{
			System.out.println(i); 
			cases[i].addActionListener(this);
			cases[i].setBounds((i%map.getWidth())*10, (i/map.getWidth())*10, 10, 10);
			if(map.getTable(i)==0)	
				this.cases[i].setBackground(new Color(220, 220, 220));
			if(map.getTable(i)==1)	
				this.cases[i].setBackground(new Color(100, 100, 100));			
			
			this.pan.add(cases[i]);
		}
		return pan;
	}
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();		
 
		for(int i=0; i<map.getWidth()*map.getHeight(); i++)
		{
			if(source == cases[i])
			{
				if(map.getTable(i)==0)
				{
					map.setTable(i,1);
					
					cases[i].setBackground(new Color(100, 100, 100));
				}
				else if(map.getTable(i)==1)
				{
					map.setTable(i,0);
					
					cases[i].setBackground(new Color(220, 220, 220));
				}
			}
		}
		repaint();
	}
}