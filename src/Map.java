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
		case_cote=10;
		try 
		{ 
			InputStream ips = new FileInputStream(fichier); // mon fichier texte pour tester 
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
			InputStream ips = new FileInputStream(fichier); // mon fichier texte pour tester 
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
				/*newZone.buildPathMap(width, height);
				buildZonePathMap(newZone);
				for(int i=0; i<height; i++){
					for(int k=0; k<width; k++){
						System.out.print(newZone.getPathMap()[i*width+k]+" ");
					}
					System.out.println(" ");
				}*/
				zones.add(newZone);
			}
			br.close(); 
		} 
		catch (Exception e) { 
			System.out.println(e.toString()); 
		} 		
	}
	
	public void buildZonePathMap(Zone zone){
		//initialisation de la map chemin de la zone avec des -2 et -1
		pathTableQueue = new LinkedList<PathCaseValue>();
		int id = -1;
		
		for(int i=0; i<width*height; i++){
			if(table[i]==1)
				zone.setPathMap(i, -2);
			else
				zone.setPathMap(i, -1);
		}
		
		int posZone = (int)((zone.getx()/case_cote+(zone.getTaille()/case_cote)/2)+width*(zone.gety()/case_cote));
		
		//construction de la map chemin de la zone en partant de sa position centrale
		pathTableQueue.add(new PathCaseValue(id, posZone));
		SetCaseDistanceToZone(zone, posZone, id);
		int id2 = 0;
		while(pathTableQueue.size() != 0){
	    	PathCaseValue values = pathTableQueue.pollFirst();
	    	
	    	int pos = values.getPosition();
	    	id = values.getDistance()+1;
	    	
	    	zone.setPathMap(pos, id);
	    	//SetCaseDistanceToZone(zone, values.getPosition(), values.getDistance()+1);
	    	
         // Haut-Gauche
    	    if((pos-1-width >= 0) && (pos%width!=0) &&(zone.getPathMap()[pos-1-width] == -1) && (zone.getPathMap()[pos-1-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-1-width));
    	    }
    	    
    	    // Haut
    	    if((pos-width >= 0) && (zone.getPathMap()[pos-width] == -1) && (zone.getPathMap()[pos-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-width));
    	    }
    	    
    	    // Haut-droite
    	    if((pos+1-width > 0) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1-width] == -1) && (zone.getPathMap()[pos+1-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+1-width));
    	    }
    	    
    	    // Gauche
    	    if((pos-1 >= 0) && (pos%width!=0) && (zone.getPathMap()[pos-1] == -1) && (zone.getPathMap()[pos-1] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-1));
    	    }
    	    
    	    // Droite
    	    if((pos+1 < width*height) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1] == -1) && (zone.getPathMap()[pos+1] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+1));
    	    }
    	    
    	    // Bas-Gauche
    	    if((pos-1+width < width*height) && (pos%width!=0) && (zone.getPathMap()[pos-1+width] == -1) && (zone.getPathMap()[pos-1+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos-1+width));
    	    }
    	    
    	    // Bas
    	    if((pos+width < width*height) && (zone.getPathMap()[pos+width] == -1) && (zone.getPathMap()[pos+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+width));	
    	    }
    	    
    	    // Bas-droite
    	    if((pos+1+width < width*height) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1+width] == -1) && (zone.getPathMap()[pos+1+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(id, pos+1+width));
    	    }
    	    if(id > id2){
    	    	//System.out.println(id);
    	    	id2++;
    	    }
        }
	}
		
	public void SetCaseDistanceToZone(Zone zone, int pos, int id){

		// Haut-Gauche
	    if((pos-1-width >= 0) && (pos%width!=0) &&(zone.getPathMap()[pos-1-width] == -1) && (zone.getPathMap()[pos-1-width] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos-1-width));
	    }
	    
	    // Haut
	    if((pos-width >= 0) && (zone.getPathMap()[pos-width] == -1) && (zone.getPathMap()[pos-width] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos-width));
	    }
	    
	    // Haut-droite
	    if((pos+1-width > 0) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1-width] == -1) && (zone.getPathMap()[pos+1-width] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos+1-width));
	    }
	    
	    // Gauche
	    if((pos-1 >= 0) && (pos%width!=0) && (zone.getPathMap()[pos-1] == -1) && (zone.getPathMap()[pos-1] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos-1));
	    }
	    
	    // Droite
	    if((pos+1 < width*height) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1] == -1) && (zone.getPathMap()[pos+1] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos+1));
	    }
	    
	    // Bas-Gauche
	    if((pos-1+width < width*height) && (pos%width!=0) && (zone.getPathMap()[pos-1+width] == -1) && (zone.getPathMap()[pos-1+width] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos-1+width));
	    }
	    
	    // Bas
	    if((pos+width < width*height) && (zone.getPathMap()[pos+width] == -1) && (zone.getPathMap()[pos+width] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos+width));	
	    }
	    
	    // Bas-droite
	    if((pos+1+width < width*height) && (pos%width !=(width-1)) && (zone.getPathMap()[pos+1+width] == -1) && (zone.getPathMap()[pos+1+width] != -2)){
	    	pathTableQueue.add(new PathCaseValue(id, pos+1+width));
	    }
	    
	    
	    
	}
	
	
	public void save(String fichier)
	{
		try {
			FileWriter fw = new FileWriter (fichier);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println (width);
			fichierSortie.println (height);
			for(int i=0;i<width*height;i++)
				fichierSortie.println (table[i]); 
			fichierSortie.close();
			System.out.println("Le fichier " + fichier + " a été créé!"); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
}