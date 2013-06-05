import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Map
{
	/*****VARIABLES****/
	
	private int width;
	private int height;	
	private int case_cote;
	private int nb_zones;
	private int nb_towers;
	private int[] table;
	private int[] zonesInfluenceMap;
	private ArrayList<Zone>zones;
	private ArrayList<Tower>towers;
	private ArrayList<Agent>agents;
	private ArrayList<Bullet>bullets;
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

	
	public Map(ArrayList<Zone>zones,ArrayList<Tower>towers, ArrayList<Agent> agents, ArrayList<Bullet> bullets)
	{		
		this.zones=zones;
		this.towers=towers;
		this.agents=agents;
		this.bullets=bullets;
		this.case_cote=10;
		table=new int[width*height];
		zonesInfluenceMap=new int[width*height];
		java.util.Random rand = new java.util.Random();
		for(int j=0;j<height*width;j++)
			table[j] = rand.nextInt(2);
		
	}
	public Map(String fichier)
	{		
		zones=new ArrayList<Zone>();
		towers=new ArrayList<Tower>();
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
			zonesInfluenceMap=new int[width*height];
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
				Zone newZone = new Zone(j+1, x,y,taille,proprio,nb);
				newZone.buildPathMap(width, height);
				buildZonePathMap(newZone);
				/*if(j == 2){
				//boucle d'affichage de la map de PathFining dans la console
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
				}*/
				
				zones.add(newZone);
			}
			ligne = br.readLine();
			val = new StringTokenizer(ligne," ");
			nb_towers = Integer.parseInt(val.nextToken());
			for(int j=0;j<nb_towers;j++)	
			{
				ligne = br.readLine();
				val = new StringTokenizer(ligne," ");
				int x = Integer.parseInt(val.nextToken());
				int y = Integer.parseInt(val.nextToken());
				int influence = Integer.parseInt(val.nextToken());
				int taille = Integer.parseInt(val.nextToken());
				Tower newTower = new Tower(x,y,influence ,agents, bullets);				
				towers.add(newTower);
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
		zonesInfluenceMap=new int[width*height];
		java.util.Random rand = new java.util.Random();
		for(int j=0;j<height*width;j++)	
		table[j] = rand.nextInt(2);
	}
	
	
	public ArrayList<Zone> getZonesFromImage(int[][] pixelData){
		
		//l'image ne peut comporter qu'une base par joueur (pour l'instant) et de couleurs rouge, verte et bleue
		
		ArrayList<Zone> zonesInImage = new ArrayList<Zone>();
		
		ColoredLine blueLine = null;
		ColoredLine blueLineWide = null;
		ColoredLine redLine = null;
		ColoredLine redLineWide = null;
		ColoredLine greenLine =null;
		ColoredLine greenLineWide = null;
		
		boolean isBlue = false;
		boolean isRed = false;
		boolean isGreen = false;
		
		for(int j=0;j<height;j++)	
		{

			for(int i=0;i<width;i++)	
			{
				int r = pixelData[j*width+i][0];
				int g = pixelData[j*width+i][1];
				int b = pixelData[j*width+i][2];
				int absRG = Math.abs(r-g);
				int absRB = Math.abs(r-b);
				int absGB = Math.abs(g-b);
				
				//red zone detected
				if(r > 100 && absRB > 50 && absRG > 50){
					if(!isRed){
						if(redLine == null){
							redLine = new ColoredLine(i, j);
							redLineWide = new ColoredLine(redLine.xStart, redLine.y);
						}
						else{
							redLine.xStart = i;
							redLine.y = j;
						}
						isRed = true;
					}
				}else{
					if(isRed){
						redLine.xEnd = i;
						if(redLineWide.xEnd == -1){
							System.out.println("toto");
							redLineWide.xEnd = i;
							redLineWide.xEnd = j;
						}
						
						if(redLineWide.getLineWidth() < redLine.getLineWidth()){
							System.out.println("red");
							System.out.println(i+" "+j);
							redLineWide.xStart = redLine.xStart;
							redLineWide.xEnd = redLine.xEnd;
						}
						isRed = false;
					}
				}
				
				//blue zone detected
				if(b > 100 && absRB > 50 && absGB > 50){
					if(!isBlue){
						if(blueLine == null){
							blueLine = new ColoredLine(i, j);
							blueLineWide = new ColoredLine(blueLine.xStart, blueLine.y);
						}
						else{
							blueLine.xStart = i;
							blueLine.y = j;
						}
						isBlue = true;
					}
				}else{
					if(isBlue){
						blueLine.xEnd = i;
						if(blueLineWide.xEnd == -1){
							System.out.println("toto");
							blueLineWide.xEnd = i;
							blueLineWide.xEnd = j;
						}
						
						if(blueLineWide.getLineWidth() < blueLine.getLineWidth()){
							System.out.println("blue");
							blueLineWide.xStart = blueLine.xStart;
							blueLineWide.xEnd = blueLine.xEnd;
						}
						isBlue = false;
					}
				}
				//green zone detected
				if(g > 100 && absRG > 50 && absGB > 50){
					if(!isGreen){
						if(greenLine == null){
							greenLine = new ColoredLine(i, j);
							greenLineWide = new ColoredLine(greenLine.xStart, greenLine.y);
						}
						else{
							greenLine.xStart = i;
							greenLine.y = j;
						}
						isGreen = true;
					}
				}else{
					if(isGreen){
						greenLine.xEnd = i;
						if(greenLineWide.xEnd == -1){
							System.out.println("toto");
							greenLineWide.xEnd = i;
							greenLineWide.xEnd = j;
						}
						
						if(greenLineWide.getLineWidth() < greenLine.getLineWidth()){
							System.out.println("green");
							greenLineWide.xStart = greenLine.xStart;
							greenLineWide.xEnd = greenLine.xEnd;
						}
						isGreen = false;
					}
				}	
			}	
		}	
		if(redLineWide != null)
			zonesInImage.add(new Zone(1, redLineWide.xStart*case_cote, redLineWide.y*case_cote, redLineWide.getLineWidth()*case_cote, 1, redLineWide.getLineWidth()));
		if(blueLineWide != null)
			zonesInImage.add(new Zone(2, blueLineWide.xStart*case_cote, blueLineWide.y*case_cote, blueLineWide.getLineWidth()*case_cote, 2, blueLineWide.getLineWidth()));
		if(greenLineWide != null)
			zonesInImage.add(new Zone(3, greenLineWide.xStart*case_cote, greenLineWide.y*case_cote, greenLineWide.getLineWidth()*case_cote, 3, greenLineWide.getLineWidth()));
		
		return zonesInImage;
	}
	
	
	public void build(String fichier)
	{		
		case_cote=10;

		try 
		{ 
			if(fichier.contains(".jpg")){
				
				width=80; 
				height=80;
				table=new int[width*height];
				zonesInfluenceMap=new int[width*height];
				

				int[][] pixelData = buildFromImage(fichier);
				for(int j=0;j<height;j++)	
				{
					
					for(int i=0;i<width;i++)	
					{
						int r = pixelData[j*width+i][0];
						int g = pixelData[j*width+i][1];
						int b = pixelData[j*width+i][2];
						int absRG = Math.abs(r-g);
						int absRB = Math.abs(r-b);
						int absGB = Math.abs(g-b);

						
						if(r > 50 && (g == b) && (g == r)){
							table[i+j*width] = 0;
						}
						/*
						if((r > g) && (r > b)){
							table[i+j*width] = 0;
						}*/
						
						//case noire (ou grise)
						if(r < 51 && g < 51 && b < 51){
							table[i+j*width] = 1;
						}
						
						
					}	
				}
				ArrayList<Zone> zones = getZonesFromImage(pixelData);
				for(int j=0;j<zones.size();j++)	
				{
					zones.get(j).buildPathMap(width, height);
					buildZonePathMap(zones.get(j));
	
					this.zones.add(zones.get(j));
					System.out.println(zones.size());
				}
					
					setZonesInfluence();
					
					for(int j=0;j<nb_towers;j++)	
					{
						int x = j*100;
						int y = j*100;
						int influence = 20;
						int taille = 70;
						Tower newTower = new Tower(x,y,influence ,agents, bullets);	
						towers.add(newTower);
					}
					setTowerZone();
				}

			else{
				
				InputStream ips = new FileInputStream(fichier); 
				InputStreamReader ipsr = new InputStreamReader(ips); 
				BufferedReader br = new BufferedReader(ipsr); 
				String ligne;
				ligne = br.readLine();
				
				StringTokenizer val = new StringTokenizer(ligne," "); // ici point important: ," " indique qu'on utilise le séparateur de mots (de valeurs) espace. 
		
				width=Integer.parseInt(val.nextToken()); 
				height=Integer.parseInt(val.nextToken());
				table=new int[width*height];
				zonesInfluenceMap=new int[width*height];
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
					Zone newZone = new Zone(j+1, x,y,taille,proprio,nb);
					newZone.buildPathMap(width, height);
					buildZonePathMap(newZone);
					/*if(j == 2){
					//boucle d'affichage de la map de PathFining dans la console
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
							System.out.println(" ");
						}
						System.out.println(" ");
						}
						*/
						zones.add(newZone);
						System.out.println("zone créée ");
				}
				
				setZonesInfluence();
				ligne = br.readLine();
				val = new StringTokenizer(ligne," ");
				nb_towers = Integer.parseInt(val.nextToken());
				for(int j=0;j<nb_towers;j++)	
				{
					ligne = br.readLine();
					val = new StringTokenizer(ligne," ");
					int x = Integer.parseInt(val.nextToken());
					int y = Integer.parseInt(val.nextToken());
					int influence = Integer.parseInt(val.nextToken());
					//int taille = Integer.parseInt(val.nextToken());
					Tower newTower = new Tower(x,y,influence ,agents, bullets);	
					towers.add(newTower);
				}
				setTowerZone();

				br.close(); 
				
			}
		}
		catch (Exception e) {
		System.out.println(e.toString());
		}
	}
	
	
	public int[][] buildFromImage(String imgFile){
		BufferedImage img;
		int[][] pixelData = new int[80*80][3];
	    try {
	        img = ImageIO.read(new File(imgFile));

	        pixelData = new int[img.getHeight() * img.getWidth()][3];
	        int[] rgb;// = new int[img.getHeight() * img.getWidth()];

	        int counter = 0;
	        for(int i = 0; i < img.getHeight(); i++){
	            for(int j = 0; j < img.getWidth(); j++){
	                rgb = getPixelData(img, j, i);

	                for(int k = 0; k < rgb.length; k++){
	                    pixelData[counter][k] = rgb[k];
	                }

	                counter++;
	            }
	        }
	  
	        for(int i=0; i<img.getHeight(); i++){
				for(int k=0; k<img.getWidth(); k++){
					
					int r = pixelData[i*width+k][0];
					int g = pixelData[i*width+k][1];
					int b = pixelData[i*width+k][2];
					int absRG = Math.abs(r-g);
					int absRB = Math.abs(r-b);
					int absGB = Math.abs(g-b);
					
					
					if((r > 70 && absRG > 40 && absRB > 40) || (r < 51 && g < 51 && b < 51)){
						System.out.print(" ");
					}
					
					if(r > 50 && (g == b) && (g == r)){
						System.out.print("B");
					}
					
					/*if((r > g) && (r > b)){
						System.out.print("B");
					}*/
					
					/*if(pixelData[i*img.getWidth()+k][0] == 255 && pixelData[i*img.getWidth()+k][1] == 255 && pixelData[i*img.getWidth()+k][2] == 255){
						System.out.print("B");
					}
					if(pixelData[i*img.getWidth()+k][0] == 0 && pixelData[i*img.getWidth()+k][1] == 0 && pixelData[i*img.getWidth()+k][2] == 0){
						System.out.print(" ");
					}*/
				}
			}

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return pixelData;
	}
	
	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};

		//System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
		return rgb;

			
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
		   // System.out.println(dist);

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
	
	public void setZonesInfluence(){
		
		for(int k=0; k<width*height; k++){
			if(table[k]==1)
				zonesInfluenceMap[k] = -2;
			else
				zonesInfluenceMap[k] = -1;
		}
		
		
		//on utilise une Liste FIFO pour traiter dans l'ordre les différentes cases à remplir avec leur id de zone
		pathTableQueue = new LinkedList<PathCaseValue>();
		
		//construction de la map d'influence des zones en partant de leurs positions centrales
		for(int l = 0; l < zones.size(); l++){
			pathTableQueue.add(new PathCaseValue(l+1, zones.get(l).getIndexInPath()));
		}
		
		/*boucle qui défile la liste et traite toutes les cases autour de la case défliée. Si ces cases remplissent les conditions, 
		alors on les ajoute à la file et on règle la case de le tableau de la map à -3 pour ne pas la retraiter dans les tours de boucle suivants*/
		while(pathTableQueue.size() != 0){
	    	PathCaseValue values = pathTableQueue.poll();
	    	
	    	int pos = values.getPosition();
	    	
	    	//on assigne la valeur la case de la map de PathFinding avec l'id de la zone qui prend cette case dans son rayon d'influence
	    	zonesInfluenceMap[pos] = values.getDistance();

	    	
	    	// Haut-Gauche
    	    if((pos-1-width >= 0) && (pos%width!=0) && (zonesInfluenceMap[pos-1-width] == -1) && (zonesInfluenceMap[pos-1-width] != -3) && (zonesInfluenceMap[pos-1-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos-1-width));
    	    	zonesInfluenceMap[pos-1-width] = -3;
    	    }
    	    
    	    // Haut
    	    if((pos-width >= 0) && (zonesInfluenceMap[pos-width] == -1) && (zonesInfluenceMap[pos-width] != -3) && (zonesInfluenceMap[pos-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos-width));
    	    	zonesInfluenceMap[pos-width] = -3;
    	    }
    	    
    	    // Haut-droite
    	    if((pos+1-width > 0) && (pos%width !=(width-1)) && (zonesInfluenceMap[pos+1-width] == -1) && (zonesInfluenceMap[pos+1-width] != -3) && (zonesInfluenceMap[pos+1-width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos+1-width));
    	    	zonesInfluenceMap[pos+1-width] = -3;
    	    }
    	    
    	    // Gauche
    	    if((pos-1 >= 0) && (pos%width!=0) && (zonesInfluenceMap[pos-1] == -1) && (zonesInfluenceMap[pos-1] != -3) && (zonesInfluenceMap[pos-1] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos-1));
    	    	zonesInfluenceMap[pos-1] = -3;
    	    }
    	    
    	    // Droite
    	    if((pos+1 < width*height) && (pos%width !=(width-1)) && (zonesInfluenceMap[pos+1] == -1) && (zonesInfluenceMap[pos+1] != -3) && (zonesInfluenceMap[pos+1] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos+1));
    	    	zonesInfluenceMap[pos+1] = -3;
    	    }
    	    
    	    // Bas-Gauche
    	    if((pos-1+width < width*height) && (pos%width!=0) && (zonesInfluenceMap[pos-1+width] == -1) && (zonesInfluenceMap[pos-1+width] != -3) && (zonesInfluenceMap[pos-1+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos-1+width));
    	    	zonesInfluenceMap[pos-1+width] = -3;
    	    }
    	    
    	    // Bas
    	    if((pos+width < width*height) && (zonesInfluenceMap[pos+width] == -1) && (zonesInfluenceMap[pos+width] != -3) && (zonesInfluenceMap[pos+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos+width));	
    	    	zonesInfluenceMap[pos+width] = -3;
    	    }
    	    
    	    // Bas-droite
    	    if((pos+1+width < width*height) && (pos%width !=(width-1)) && (zonesInfluenceMap[pos+1+width] == -1) && (zonesInfluenceMap[pos+1+width] != -3) && (zonesInfluenceMap[pos+1+width] != -2)){
    	    	pathTableQueue.add(new PathCaseValue(zonesInfluenceMap[pos], pos+1+width));
    	    	zonesInfluenceMap[pos+1+width] = -3;
    	    }
    	  }
		
		/*System.out.println("done man !");
		
		//boucle d'affichage en console de la map d'influence des zones
		/*for(int i=0; i<height; i++){
			for(int k=0; k<width; k++){
				if(zonesInfluenceMap[i*width+k] < 0 || (zonesInfluenceMap[i*width+k] > 9 && zonesInfluenceMap[i*width+k] < 100)){
					System.out.print(zonesInfluenceMap[i*width+k]+"  ");
				}
				if(zonesInfluenceMap[i*width+k] > 99){
					System.out.print(zonesInfluenceMap[i*width+k]+" ");
				}
				if(zonesInfluenceMap[i*width+k] < 10 && zonesInfluenceMap[i*width+k] > -1){
					System.out.print(zonesInfluenceMap[i*width+k]+"   ");
				}
			}
			System.out.println(" ");
		}
		System.out.println(" ");*/
		
	}
	public void setTowerZone()
	{
		if(!towers.isEmpty())
		for(int i=0; i<towers.size(); i++)
		{
			towers.get(i).setZone(zonesInfluenceMap[(int)(towers.get(i).getx()+towers.get(i).gety()*width)/10]);
			//System.out.println(towers.get(i).getx()+" "+towers.get(i).gety()+" "+towers.get(i).getZone());
		}
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
			fichierSortie.print(nb_towers);
			fichierSortie.print("\r");
			
			
			for(int k=0; k<nb_towers;k++)
			{
				fichierSortie.print((int)towers.get(k).getx());
				fichierSortie.print(" "); 
				fichierSortie.print((int)towers.get(k).gety());
				fichierSortie.print(" "); 
				fichierSortie.print(towers.get(k).getInfluence());	
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