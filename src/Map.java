import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.JPanel;

public class Map
{
	/*****VARIABLES****/
	
	private int width;
	private int height;	
	private int case_cote;
	private int[] table;
	
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
	public Map(int width, int height)
	{		
		this.width=width;
		this.height=height;
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
			br.close(); 
		} 
		catch (Exception e) { 
			System.out.println(e.toString()); 
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