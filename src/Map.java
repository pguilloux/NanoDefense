import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

public class Map
{
	/*****VARIABLES****/
	
	private int width;
	private int height;	
	private int case_cote;
	private int nb_zones;
	private int[] table;
	private ArrayList<Zone>zones;
	private ArrayList<Tower>towers;
	private LinkedList<PathCaseValue> pathTableQueue;
	
	/******GET&SET******/
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public int getTable(int i)
	{
		return table[i];
	}
	public void setTable(int i, int value)
	{
		table[i]=value;
	}
	public int getNb_zones() 
	{
		return nb_zones;
	}

	
	public Map(ArrayList<Zone>zones,ArrayList<Tower>towers)
	{		
		this.zones=zones;
		this.case_cote=10;
		table=new int[width*height];
		java.util.Random rand = new java.util.Random();
		for(int j=0;j<height*width;j++)	
		table[j] = rand.nextInt(2);
	}
	public Map(String fichier)
	{		
		zones=new ArrayList<Zone>();
		case_cote=10;
		try 
		{ 
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips); 
			BufferedReader br = new BufferedReader(ipsr); 
			String ligne;
			ligne = br.readLine();
			
			StringTokenizer val = new StringTokenizer(ligne," "); // ici point important: ," " indique qu'on utilise le séparateur de mots (de valeurs) espace. 
	
			width=Integer.parseInt(val.nextToken()); 
			height=Integer.parseInt(val.nextToken());
			table=new int[width*height];
			for(int j=0;j<height;j++)	
			{
				ligne = br.readLine();
				val = new StringTokenizer(ligne," ");
				for(int i=0;i<width;i++)	
				{
					table[i+j*width] = Integer.parseInt(val.nextToken());
				}
			}	
			ligne = br.readLine();
			val = new StringTokenizer(ligne," ");
			nb_zones = Integer.parseInt(val.nextToken());
			for(int j=0;j<nb_zones;j++)	
			{
				ligne = br.readLine();
				val = new StringTokenizer(ligne," ");
				int x = Integer.parseInt(val.nextToken());
				int y = Integer.parseInt(val.nextToken());
				int taille = Integer.parseInt(val.nextToken());
				int proprio = Integer.parseInt(val.nextToken());
				int nb = Integer.parseInt(val.nextToken());	
				Zone newZone = new Zone(x,y,taille,proprio,nb);
				newZone.buildPathMap(width, height);
				buildZonePathMap(newZone);
				if(j == 2){
				/*boucle d'affichage de la map de PathFining dans la console*/
				for(int i=0; i<height; i++){
					for(int k=0; k<width; k++){
						if(newZone.getPathMap()[i*width+k] < 0 || (newZone.getPathMap()[i*width+k] > 9 && newZone.getPathMap()[i*width+k] < 100)){
							System.out.print(newZone.getPathMap()[i*width+k]+"  ");
						}
						if(newZone.getPathMap()[i*width+k] > 99){
							System.out.print(newZone.getPathMap()[i*width+k]+" ");
						}
						if(newZone.getPathMap()[i*width+k] < 10 && newZone.getPathMap()[i*width+k] > -1){
							System.out.print(newZone.getPathMap()[i*width+k]+"   ");
						}
					}
					System.out.println(" ");
				}
				System.out.println(" ");
				}
				
				zones.add(newZone);
			}
			br.close(); 
		} 
		catch (Exception e) { 
			System.out.println(e.toString()); 
		} 		
	}
	public void build(int width, int height)
	{		
		this.width=width;
		this.height=height;
		this.case_cote=10;
		table=new int[width*height];
		java.util.Random rand = new java.util.Random();
		for(int j=0;j<height*width;j++)	
		table[j] = rand.nextInt(2);
	}
	public void build(String fichier)
	{		
		case_cote=10;
		try 
		{ 
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips); 
			BufferedReader br = new BufferedReader(ipsr); 
			String ligne;
			ligne = br.readLine();
			
			StringTokenizer val = new StringTokenizer(ligne," "); // ici point important: ," " indique qu'on utilise le séparateur de mots (de valeurs) espace. 
	
			width=Integer.parseInt(val.nextToken()); 
			height=Integer.parseInt(val.nextToken());
			table=new int[width*height];
			for(int j=0;j<height;j++)	
			{
				ligne = br.readLine();
				val = new StringTokenizer(ligne," ");
				for(int i=0;i<width;i++)	
				{
					table[i+j*width] = Integer.parseInt(val.nextToken());
				}
			}	
			ligne = br.readLine();
			val = new StringTokenizer(ligne," ");
			nb_zones = Integer.parseInt(val.nextToken());
			for(int j=0;j<nb_zones;j++)	
			{
				ligne = br.readLine();
				val = new StringTokenizer(ligne," ");
				int x = Integer.parseInt(val.nextToken());
				int y = Integer.parseInt(val.nextToken());
				int taille = Integer.parseInt(val.nextToken());
				int proprio = Integer.parseInt(val.nextToken());
				int nb = Integer.parseInt(val.nextToken());	
				Zone newZone = new Zone(x,y,taille,proprio,nb);
				newZone.buildPathMap(width, height);
				buildZonePathMap(newZone);
				if(j == 2){
				/*boucle d'affichage de la map de PathFining dans la console*/
				for(int i=0; i<height; i++){
					for(int k=0; k<width; k++){
						if(newZone.getPathMap()[i*width+k] < 0 || (newZone.getPathMap()[i*width+k] > 9 && newZone.getPathMap()[i*width+k] < 100)){
							System.out.print(newZone.getPathMap()[i*width+k]+"  ");
						}
						if(newZone.getPathMap()[i*width+k] > 99){
							System.out.print(newZone.getPathMap()[i*width+k]+" ");
						}
						if(newZone.getPathMap()[i*width+k] < 10 && newZone.getPathMap()[i*width+k] > -1){
							System.out.print(newZone.getPathMap()[i*width+k]+"   ");
						}
					}
					System.out.println(" ");
				}
				System.out.println(" ");
				}
				
				zones.add(newZone);
				System.out.println("zone créée ");
			}
			br.close(); 
		} 
		catch (Exception e) { 
			System.out.println(e.toString()); 
		} 		
	}
	
	public void buildZonePathMap(Zone zone){
		
		//on utilise une Liste FIFO pour traiter dans l'ordre les différentes cases à remplir avec leur distance à la zone
		pathTableQueue = new LinkedList<PathCaseValue>();
		int id = -1;
		
		//initialisation de la map de PathFinding de la zone avec des -2 et -1
		for(int i=0; i<width*height; i++){
			if(table[i]==1)
				zone.setPathMap(i, -2);
			else
				zone.setPathMap(i, -1);
		}
		//on calcule la position de départ c'est-à-dire celle de la base passée en paramètre
		int posZone = (int)((zone.getx()/case_cote+(zone.getTaille()/case_cote)/2)+width*(zone.gety()/case_cote));
		
		//construction de la map chemin de la zone en partant de sa position centrale
		pathTableQueue.add(new PathCaseValue(id, posZone));
		
		/*boucle qui défile la liste et traite toutes les cases autour de la case défliée. Si ces cases remplissent les conditions, 
		alors on les ajoute à la file et on règle la case de le tableau de la map à -3 pour ne pas la retraiter dans les tours de boucle suivants*/
		while(pathTableQueue.size() != 0){
	    	PathCaseValue values = pathTableQueue.poll();
	    	
	    	int pos = values.getPosition();
	    	id = values.getDistance()+1;
	    	
	    	//on assigne la valeur la case de la map de PathFinding avec la distance calculée par rapport à la zone
	    	zone.setPathMap(pos, id);
	    	if(id ==0){
	    		zone.setIndexInPath(pos);
	    	}
	    	
         // Haut-Gauche
    	    if((pos-1-width >= 0) && (pos%width!=0) &&(zone.getPathMap()[pos-1-width] == -1) && (zone.getPathMap()[pos-1-width] != -3) && (zone.getPathMap()[pos-1-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-1-width));
    	    	zone.setPathMap(pos-1-width, -3);
    	    }
    	    
    	    // Haut
    	    if((pos-width >= 0) && (zone.getPathMap()[pos-width] == -1) && (zone.getPathMap()[pos-width] != -3) && (zone.getPathMap()[pos-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-width));
    	    	zone.setPathMap(pos-width, -3);
    	    }
    	    
    	    // Haut-droite
    	    if((pos+1-width > 0) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1-width] == -1) && (zone.getPathMap()[pos+1-width] != -3) && (zone.getPathMap()[pos+1-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+1-width));
    	    	zone.setPathMap(pos+1-width, -3);
    	    }
    	    
    	    // Gauche
    	    if((pos-1 >= 0) && (pos%width!=0) && (zone.getPathMap()[pos-1] == -1) && (zone.getPathMap()[pos-1] != -3) && (zone.getPathMap()[pos-1] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-1));
    	    	zone.setPathMap(pos-1, -3);
    	    }
    	    
    	    // Droite
    	    if((pos+1 < width*height) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1] == -1) && (zone.getPathMap()[pos+1] != -3) && (zone.getPathMap()[pos+1] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+1));
    	    	zone.setPathMap(pos+1, -3);
    	    }
    	    
    	    // Bas-Gauche
    	    if((pos-1+width < width*height) && (pos%width!=0) && (zone.getPathMap()[pos-1+width] == -1) && (zone.getPathMap()[pos-1+width] != -3) && (zone.getPathMap()[pos-1+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-1+width));
    	    	zone.setPathMap(pos-1+width, -3);
    	    }
    	    
    	    // Bas
    	    if((pos+width < width*height) && (zone.getPathMap()[pos+width] == -1) && (zone.getPathMap()[pos+width] != -3) && (zone.getPathMap()[pos+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+width));	
    	    	zone.setPathMap(pos+width, -3);
    	    }
    	    
    	    // Bas-droite
    	    if((pos+1+width < width*height) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1+width] == -1) && (zone.getPathMap()[pos+1+width] != -3) && (zone.getPathMap()[pos+1+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+1+width));
    	    	zone.setPathMap(pos+1+width, -3);
    	    }
    	    }
        }
	
	
	public ArrayList<Integer> getPathTableToZone(Zone start, Zone end){
		
		ArrayList<Integer> pathTable = new ArrayList<Integer>();
		
		int pos = start.getIndexInPath();
		int currentPos = pos;
		System.out.println();
		pathTable.add(pos);
		
		int dist = end.getPathMap()[pos];

		while(pos != end.getIndexInPath()){

			// Haut-Gauche
		    if((pos-1-width >= 0) && (pos%width!=0) && (end.getPathMap()[pos-1-width] < dist) && (end.getPathMap()[pos-1-width] > -1)){
		    	currentPos = pos-1-width;
		    	dist = end.getPathMap()[pos-1-width];
		    }
		    
		    // Haut
		    if((pos-width >= 0) && (end.getPathMap()[pos-width] < dist) && (end.getPathMap()[pos-width] > -1)){
		    	currentPos = pos-width;
		    	dist = end.getPathMap()[pos-width];
		    }
		    
		    // Haut-droite
		    if((pos+1-width > 0) && (pos%width !=(width-1)) && (end.getPathMap()[pos+1-width] < dist) && (end.getPathMap()[pos+1-width] > -1)){
		    	currentPos = pos+1-width;
		    	dist = end.getPathMap()[pos+1-width];
		    }
		    
		    // Gauche
		    if((pos-1 >= 0) && (pos%width!=0) && (end.getPathMap()[pos-1] < dist) && (end.getPathMap()[pos-1] > -1)){
		    	currentPos = pos-1;
		    	dist = end.getPathMap()[pos-1];
		    }
		    
		    // Droite
		    if((pos+1 < width*height) && (pos%width !=(width-1)) && (end.getPathMap()[pos+1] < dist) && (end.getPathMap()[pos+1] > -1)){
		    	currentPos = pos+1;
		    	dist = end.getPathMap()[pos+1];
		    }
		    
		    // Bas-Gauche
		    if((pos-1+width < width*height) && (pos%width!=0) && (end.getPathMap()[pos-1+width] < dist) && (end.getPathMap()[pos-1+width] > -1)){
		    	currentPos = pos-1+width;
		    	dist = end.getPathMap()[pos-1+width];
		    }
		    
		    // Bas
		    if((pos+width < width*height) && (end.getPathMap()[pos+width] < dist) && (end.getPathMap()[pos+width] > -1)){
		    	currentPos = pos+width;
		    	dist = end.getPathMap()[pos+width];
		    }
		    
		    // Bas-droite
		    if((pos+1+width < width*height) && (pos%width !=(width-1)) && (end.getPathMap()[pos+1+width] < dist) && (end.getPathMap()[pos+1+width] > -1)){
		    	currentPos = pos+1+width;
		    	dist = end.getPathMap()[pos+1+width];
		    }
		    pathTable.add(currentPos);
		    pos = currentPos;
		    System.out.println(dist);

		}
		
		return pathTable;
	}
	
	public LinkedList<Vector<Integer>> convertPosToCoord(ArrayList<Integer> pathTable){
		LinkedList<Vector<Integer>> path = new LinkedList<Vector<Integer>>();
		int pos = 0;
		int x, y;
		
		for(int i = 0; i < pathTable.size(); i++){
			pos = pathTable.get(i);
			x = (pos%width)*case_cote;
			y = (pos/width)*case_cote;
			Vector<Integer> vector = new Vector<Integer>();
			vector.addElement(x);
			vector.addElement(y);
			path.add(vector);
		}
		
		return path;
	}
	
	public void save(String fichier)
	{
		try {
			FileWriter fw = new FileWriter (fichier);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.print (width);
			fichierSortie.print(" "); 
			fichierSortie.print (height);
			fichierSortie.print("\r"); 
			for(int i=0;i<height;i++)
			{
				for(int j=0;j<width;j++)
				{
					fichierSortie.print (table[i*width+j]);
					fichierSortie.print(" ");
				}
				fichierSortie.print("\r"); 
			}
			
			fichierSortie.print(nb_zones);
			fichierSortie.print("\r");
			
			
			for(int k=0; k<nb_zones;k++)
			{
				fichierSortie.print((int)zones.get(k).getx());
				fichierSortie.print(" "); 
				fichierSortie.print((int)zones.get(k).gety());
				fichierSortie.print(" "); 
				fichierSortie.print(zones.get(k).getTaille());
				fichierSortie.print(" "); 
				fichierSortie.print(zones.get(k).getProprio());
				fichierSortie.print(" "); 
				fichierSortie.print(zones.get(k).getNbAgents());	
				fichierSortie.print("\r\n"); 
			}
			System.out.println("zones save");
			fichierSortie.close();
			System.out.println("Le fichier " + fichier + " a été créé!"); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
}