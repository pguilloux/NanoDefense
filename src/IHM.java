import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IHM implements ActionListener{	


	private int tower_mod;//
	private int nb_players;
	public IHM(int nb)
	{
		tower_mod=0;//1=tour associées aux bases, 2=destruction des tours quand on prend la base, 3=les bases restent à l'assaillant 
		nb_players=0;		
	}

	public void towerMod()
	{
		RoundedCornerButton towermod[]=new RoundedCornerButton[3];
		for(int i=0; i<3; i++)
			towermod[i].addActionListener(this);		
	}

	public void actionPerformed(ActionEvent arg0)
	{
		
		
	}
	
}
	