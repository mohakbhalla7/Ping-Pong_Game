package pingPong;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainLogic extends Applet implements Runnable, KeyListener, MouseListener{

	private final int WIDTH=700, HEIGHT=500;
	private Thread thread;
	private HumanPaddle p1, p2;
	private CompPaddle c1;
	private Ball b1;
	private boolean gameStarted, gameRunning, singleP, doubleP, menuRead;
	private Graphics gfx;
	private Image img;
	private Integer p1Score, p2Score;
	
	public void init() {
		this.resize(WIDTH, HEIGHT);
		gameStarted= false;
		gameRunning= false;
		singleP= false;
		doubleP= false;
		menuRead= false;
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		p1 = new HumanPaddle(1);
		p2 = new HumanPaddle(2);
		b1 = new Ball();
		c1 = new CompPaddle(b1);
		
		p1Score = new Integer(0);
		p2Score = new Integer(0);
		
		img = createImage(WIDTH, HEIGHT);
		gfx = img.getGraphics();
		
		thread = new Thread(this);
		thread.start();
	}
	
	
	public void paint(Graphics g) {
		gfx.setColor(Color.white);
		gfx.fillRect(0, 0, WIDTH, HEIGHT);
		if(singleP) {
			gfx.setColor(Color.red);
			gfx.setFont(new Font("Arial", Font.BOLD, 19));
			gfx.drawString(p1Score.toString(), 345, 20);
		}
		else if(doubleP) {
			gfx.setColor(Color.red);
			gfx.setFont(new Font("Arial", Font.BOLD, 19));
			gfx.drawString(p1Score.toString(), 620, 20);
			gfx.drawString(p2Score.toString(), 80, 20);
		}
				
		if(!gameStarted) {
			gfx.setColor(Color.black);
			gfx.setFont(new Font("Arial", Font.PLAIN, 13));
			if(!gameRunning) {
				gfx.drawString("Ping-Pong", 325, 85);
			}
			
			if(!menuRead) {	
				gfx.drawString("Single Player", 300, 120);
				gfx.drawString("Double Player", 300, 140);
			}
			else {
				if(singleP) {
					gfx.drawString("Controls: Use Arrow keys", 287, 135);
				}
				else if(doubleP && !gameRunning) {
					gfx.drawString("Controls:", 330, 120);
					gfx.drawString("Player 1: Use Arrow keys", 290, 140);
					gfx.drawString("Player 2: Up - W    Down - S", 290, 160);
				}
				
				gfx.setColor(Color.black);
				gfx.setFont(new Font("Arial", Font.PLAIN, 13));
				gfx.drawString("Press Enter to Begin..", 305, 185);
			}
		}
		
		if((b1.getX() > 710 || b1.getX() <-10 ) && doubleP) {
			int s1= Integer.parseInt(p1Score.toString());
			int s2= Integer.parseInt(p2Score.toString());	
			if(s1>=10 || s2>=10) {
				gfx.setColor(Color.red);
				gfx.setFont(new Font("Times New Roman", Font.BOLD, 19));
				gfx.drawString("Game Over", 317, 250);
				if(s1>=10)
					gfx.drawString("Player 1 wins!", 305, 300);
				else
					gfx.drawString("Player 2 wins!", 305, 300);
				
				stop();
				destroy();
				thread.stop();
			}
			else {
				b1= new Ball();
				gameRunning= true;
				gameStarted=false;
			}
		}
		else if(b1.getX() > 710 && singleP) {
			gfx.setColor(Color.red);
			gfx.setFont(new Font("Times New Roman", Font.BOLD, 19));
			gfx.drawString("Game Over", 317, 250);
			gfx.drawString("Your Score is: " + p1Score.toString(), 297, 300);
			
			stop();
			destroy();
		}
		else {
			p1.draw(gfx);
			b1.draw(gfx);
			if(singleP) {
				c1.draw(gfx);
			}
			else {
				p2.draw(gfx);
			}
		}
		
		g.drawImage(img, 0, 0, this);
	}
	
	
	public void update(Graphics g) {
		paint(g);
	}

	
	public void run() {
		for(;;) {
			if(gameStarted) {
				if(singleP) {
					p1.move();
					c1.move();
					b1.move();
					if(b1.checkPaddleCollision(p1, c1)==1)
						p1Score += 1;
				}
				else if(doubleP) {
					p1.move();
					p2.move();
					b1.move();
					if(b1.getX() < -10)
						p1Score += 1;
					else if(b1.getX() > 710)
						p2Score += 1;
					b1.checkPaddleCollision(p1, p2);
				}
				
			}
			repaint();
			
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			p1.setUpAccel(true);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			p1.setDownAccel(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			p2.setUpAccel(true);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			p2.setDownAccel(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			gameStarted= true;
		}
	}

	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			p1.setUpAccel(false);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			p1.setDownAccel(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			p2.setUpAccel(false);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			p2.setDownAccel(false);
		}
	}

	
	public void keyTyped(KeyEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==1) {
			int x= e.getX();
			int y= e.getY();
			
			if(x>=300 && x<=375 && y>=110 && y<=120) {
				singleP= true;
				menuRead= true;
			}
			if(x>=300 && x<=380 && y>=130 && y<=140) {
				doubleP= true;
				menuRead= true;
			}
		}
	}

	
	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
}