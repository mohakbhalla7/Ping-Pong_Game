package pingPong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Paddle{

	private double xVel, yVel, x, y;
	
	public Ball() {
		x=350;
		y=250;
		xVel= getRandomSpeed() * getRandomDirection();
		yVel= getRandomSpeed() * getRandomDirection();
	}
	
	public double getRandomSpeed() {
		return (Math.random() *2 + 2);
	}
	
	public int getRandomDirection() {
		int rand = (int)(Math.random()*2);
		if(rand == 1)
			return 1;
		else
			return -1;
	}
	
	public  void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)x-10, (int)y-10, 20, 20);
	}
	
	public int checkPaddleCollision(Paddle p1, Paddle p2) {
		int player= 0;
		if(x >= 650 && x <= 656) {
			if(y >= p1.getY() && y <= p1.getY()+80) {
				xVel = -xVel;
				player= 1;
			}
		}
		else if(x <= 50 && x >= 44) {
			if(y >= p2.getY() && y <= p2.getY()+80) {
				xVel = -xVel;
				player= 2;
			}
		}
		return player;
	}
	
	public void move() {
		x+=xVel;
		y+=yVel;
		
		if(y<10)
			yVel = -yVel;
		if(y>490)
			yVel = -yVel;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}

}