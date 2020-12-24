package pingPong;

import java.awt.Color;
import java.awt.Graphics;

public class CompPaddle implements Paddle{

	private double y;
	private int x;
	
	private Ball b1;
	
	public CompPaddle(Ball b) {
		b1=b;
		y=210;
		x=20;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, (int)y, 20, 80);
		
	}

	public void move() {
		
		y = b1.getY() - 40;
		
		if(y<0)
			y=0;
		if(y>420)
			y=420;
	}
	
	public int getY() {
		return (int)y;
	}

}