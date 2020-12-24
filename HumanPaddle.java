package pingPong;

import java.awt.Color;
import java.awt.Graphics;

public class HumanPaddle implements Paddle{

	private double y, yVel;
	private boolean upAccel, downAccel;
	private int x;
	private final double GRAVITY=0.8;
	
	public HumanPaddle(int i) {
		upAccel=false;
		downAccel=false;
		y=210;
		yVel=0;
		if(i==1) {
			x=660;
		}
		else if(i==2) {
			x=20;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, (int)y, 20, 80);
		
	}

	public void move() {
		if(upAccel) {
			yVel-=2.5;
		}
		else if(downAccel) {
			yVel+=2.5;
		}
		else if(!upAccel && !downAccel) {
			yVel*=GRAVITY;
		}
		
		if(yVel >= 6)
			yVel=6;
		else if(yVel<=-6)
			yVel=-6;
		
		y+=yVel;
		
		if(y<0)
			y=0;
		if(y>420)
			y=420;
	}
	
	public void setUpAccel(boolean input) {
		upAccel=input;
	}
	
	public void setDownAccel(boolean input) {
		downAccel=input;
	}

	public int getY() {
		return (int)y;
	}

}