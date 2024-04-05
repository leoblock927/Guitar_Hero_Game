
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import java.util.*;
import java.util.Map;

public class GamePanel extends JPanel implements Runnable{

	final int tileSize = 50;
	final int maxScreenCol = 9;
	final int maxScreenRow = 16;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	double FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	//--------------------------------------------------------
	
	Map<Integer, Double> tilesY = new HashMap<Integer, Double>();
	Map<Integer, Double> tilesX = new HashMap<Integer, Double>();
	Map<Integer, Color> tilesColors = new HashMap<Integer, Color>();
	
	boolean start = true;
	int num = 170;//170 for American Idiot
	Double nextTileY;
	Double nextTileX;
	int tileNum = 0;
	int timer = 60;
	Double speed = 5.2;
	boolean changeable = true;
	int column1 = 50; int column2 = 150; int column3 = 250; int column4 = 350;
	Color barDefColor = new Color(242, 231, 223);
	Color barCorrectColor = new Color(31, 194, 31);
	Color barWrongColor = new Color(181, 52, 20);
	Color c1 = barDefColor; Color c2 = barDefColor; Color c3 = barDefColor; Color c4 = barDefColor;
	Color tileColor = new Color(0, 68, 255);
	Color tileCorrectColor = new Color(54, 168, 117);
	Color tileWrongColor = new Color(150, 21, 56);
	Color fretColor = new Color(196, 198, 204);
	int score = 0;
	String phrase = " ";
	boolean gameOver;
	boolean startGame = false;
	String song;
	double startPos;
	
	public  GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run() {
		double drawInterval = 1000000000/FPS; //0.01666 sec
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			update();
			repaint();
	
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/10000000;
				
				if(remainingTime < 0) 
					remainingTime = 0;
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//===============================================================================================
	public boolean isStarted() {
		return startGame;
	}
	public String getSong() {
		return song;
	}
	
	public void startGame() {
		while(!(startGame)) {
			System.out.println("startGame :: " + startGame);
			if(keyH.sPressed == true) {
				startGame = true;
				song = "E:\\Eclipse\\Q3_JavaGame\\AmericanIdiot.wav";
				speed = 5.2;
				startPos = -600;
			} else if(keyH.dPressed == true) {
				startGame = true;
				song = "E:\\Eclipse\\Q3_JavaGame\\FlashingLights.wav";
				speed = 5.2;
				startPos = -500;
			} else if(keyH.kPressed == true) {
				startGame = true;
				song = "E:\\Eclipse\\Q3_JavaGame\\HeyYa!.wav";
				speed = 6.5;
				startPos = -315;
			}
		}
	}
	
	public void update() {	
		if(startGame) {
			System.out.println("startGame :: " + startGame);
			if(start) {
				Song();
				nextTileY = tilesY.get(0);
				start = false;
			}
			
			if(nextTileY >= 400 && nextTileY <= 500 && changeable) {
				if(keyH.sPressed == true)
					if(nextTileX != column1) {
						wrong();
						c1 = barWrongColor;
					}
					else {
						correct();
						c1 = barCorrectColor;
					}
				else if(keyH.dPressed == true)
					if(nextTileX != column2) {
						wrong();
						c2 = barWrongColor;
					}
					else {
						correct();
						c2 = barCorrectColor;
					}
				else if(keyH.kPressed == true)
					if(nextTileX != column3) {
						wrong();
						c3 = barWrongColor;
					}
					else {
						correct();
						c3 = barCorrectColor;
					}
				else if(keyH.lPressed == true)
					if(nextTileX != column4) {
						wrong();
						c4 = barWrongColor;
					}
					else {
						correct();
						c4 = barCorrectColor;
					}
		}
		
		for(int i = 0; i < num; i++) {
			tilesY.replace(i, tilesY.get(i) + speed);
			findNextTileY();
			nextTileY = tilesY.get(tileNum);
			nextTileX = tilesX.get(tileNum);
		}
		}
		
	}
	
	public void wrong() {
		tilesColors.replace(tileNum,tileWrongColor);
		phrase = "NICE TRY";
		changeable = false;
	}
	
	public void correct() {
		tilesColors.replace(tileNum,tileCorrectColor);
		phrase();
		changeable = false;
	}
	
	public void phrase() {
		if(nextTileY >= 400 && nextTileY < 425) {
			phrase = "EARLY";
			score += 50;
		}
		if(nextTileY >= 425 && nextTileY < 450) {
			phrase = "GREAT";
			score += 100;
		}
		if(nextTileY >=450 && nextTileY < 475) {
			phrase = "PERFECT";
			score += 250;
		}
		if(nextTileY >= 475 && nextTileY <= 500) {
			phrase = "LATE";
			score += 50;
		}
	}
	
	//-----------------------------------------------------------------------------------------------
	
	public void findNextTileY() {
		nextTileY = tilesY.get(tileNum);
	
		if(nextTileY >= 500) {
			
			if(tilesY.get(tileNum + 1) != null) {
				tileNum = tileNum + 1;
				c1 = barDefColor;
				c2 = barDefColor;
				c3 = barDefColor;
				c4 = barDefColor;
				phrase = " ";
				changeable = true;
			} else {
				nextTileY = 0.0;
				phrase = "SONG OVER";
				c1 = barDefColor;
				c2 = barDefColor;
				c3 = barDefColor;
				c4 = barDefColor;
				gameOver = true;
			}
		}
	}
	
	public void Song() {	
		for(int i = 0; i < num; i++) {
			tilesY.put(i, startPos + -100*i); tilesX.put(i, ((int)(Math.random() * 4))*100.0 + 50);
			tilesColors.put(i,tileColor);
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(!(startGame)) {
			g2.setColor(new Color(217, 222, 217));
			g2.fillRect(25,25,400,750);
			g2.setColor(new Color(81, 176, 74));
			g2.fillRect(35,35,380,100);
			g2.setColor(barDefColor);
			g2.setFont(new Font("font", Font.BOLD,50));
			g2.drawString("GUITAR HERO", 50, 100);
			
			g2.setColor(new Color(81, 176, 74));
			g2.fillRect(35,150,380,325);
			g2.setColor(barDefColor);
			g2.setFont(new Font("font", Font.BOLD,20));
			g2.drawString("American Idiot - Greenday [Press S]", 50, 175);
			g2.drawString("Flashing Lights - Kanye [Press D]", 50, 225);
			g2.drawString("Hey Ya! - Outkast [Press K]", 50, 275);
			g2.setColor(new Color(172, 176, 172));
			g2.fillOval(100,500,250,250);
			g2.setColor(new Color(217, 222, 217));
			g2.fillOval(175,575,100,100);
			
			g2.drawLine(100, 500, 350, 750);
			g2.drawLine(350, 500, 100, 750);
			
			g2.setColor(barDefColor);
			g2.setFont(new Font("font", Font.BOLD,30));
			g2.drawString("[S]", 207, 545);
			g2.drawString("[D]", 295, 635);
			g2.drawString("[K]", 205, 720);
			g2.drawString("[L]", 120, 640);
		}
		
		if(startGame) {
			//bar=====================
			g2.setColor(new Color(107, 85, 72));
			g2.fillRect(25,0,400,800);
			g2.setColor(fretColor);
			g2.fillRect(12,62,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,162,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,262,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,362,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,462,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,562,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,662,425,25);
			g2.setColor(fretColor);
			g2.fillRect(12,762,425,25);
			g2.setColor(fretColor);
			g2.fillRect(75,0,2,1000);
			g2.setColor(fretColor);
			g2.fillRect(175,0,2,1000);
			g2.setColor(fretColor);
			g2.fillRect(275,0,2,1000);
			g2.setColor(fretColor);
			g2.fillRect(375,0,2,1000);
			g2.setColor(c1);
			g2.fillRect(50,450,50,50);
			g2.setColor(c2);
			g2.fillRect(150,450,50,50);
			g2.setColor(c3);
			g2.fillRect(250,450,50,50);
			g2.setColor(c4);
			g2.fillRect(350,450,50,50);
			//========================
			//Text--------------------
			g2.setColor(new Color(255, 33, 33));
			g2.setFont(new Font("font", Font.BOLD,50));
			if(gameOver) {
				g2.setFont(new Font("font", Font.BOLD,50));
				g2.drawString("Score :: " + score, 35, 350);
				g2.drawString(phrase, 75, 150);
			} else {
				g2.drawString("Score :: " + score, 100, 50);
				g2.drawString(phrase, 125, 550);
				g2.drawString("S", 58,493);
				g2.drawString("D", 158,493);
				g2.drawString("K", 258,493);
				g2.drawString("L", 358,493);
			}
			//------------------------
			//tiles*******************
			for(int i = 0; i < num; i++) {
				g2.setColor(tilesColors.get(i));
				g2.fillRect(tilesX.get(i).intValue(),tilesY.get(i).intValue(),tileSize,tileSize);
			}
		}
		//************************
		g2.dispose();
	}
}
