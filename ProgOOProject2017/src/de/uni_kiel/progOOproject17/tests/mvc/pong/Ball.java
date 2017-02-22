package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Color;
import java.awt.Graphics;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

public class Ball extends TickedDataModel {

    public int x;
    public int y;
    public int r;

    public int xSpeed = (int) Math.pow(-1, (int) (Math.random() * 2)) * 3;
    public int ySpeed = (int) (Math.random() * 7) - 3;

    private PongField f;
    public Bar bar1;
    public Bar bar2;
    
    private final Viewable view = new Viewable() {

	    @Override
	    public void render(Graphics gr) {
		gr.setColor(Color.BLUE);
		gr.fillOval(x - r, y - r, r * 2, r * 2);
	    }
	};

    public Ball(int r, PongField f, Bar bar1, Bar bar2) {
	this.f = f;
	this.r = r;
	this.bar1 = bar1;
	this.bar2 = bar2;
	
	resetBall();
    }

    public void tick(long timestamp) {

	if (x + r + xSpeed > f.w - bar2.w && y > bar2.y && y < bar2.y + bar2.h) { // treffer
										  // 2
	    Sound.ping.play();
	    xSpeed *= -1;
	    if (bar2.moving == Bar.UP)
		ySpeed -= 2;
	    if (bar2.moving == Bar.DOWN)
		ySpeed += 2;
	    // ySpeed += (Math.random() * 5.0) - 2;

	} else if ((x - r + xSpeed < 0 + bar1.w && y > bar1.y && y < bar1.y + bar1.h)) { // treffer1
	    Sound.ping.play();
	    xSpeed *= -1;
	    if (bar1.moving == Bar.UP)
		ySpeed -= 2;
	    if (bar1.moving == Bar.DOWN)
		ySpeed += 2;
	    // ySpeed += (Math.random() * 5.0) - 2;

	} else if (x + r > f.w) { // aus2 point 1
	    Sound.monsterHurt.play();
	    f.points1++;
	    resetBall();
	    xSpeed = -3;
	    ySpeed = (int) (Math.random() * 7) - 3;

	} else if (x - r < 0) { // aus1 point 2
	    Sound.playerHurt.play();
	    f.points2++;
	    resetBall();
	    xSpeed = 3;
	    ySpeed = (int) (Math.random() * 7) - 3;
	}

	// weiter
	x += xSpeed;

	if (y + r + ySpeed > f.h || y - r + ySpeed < 0) { // bande
	    ySpeed *= -1;
	    Sound.ping.play();

	}
	// weiter
	y += ySpeed;

    }

    private void resetBall() {
	x = f.w / 2;
	y = f.h / 2;
	// xSpeed = (int) Math.pow(-1, (int) (Math.random() * 2)) * 3;
	// ySpeed = (int) (Math.random() * 7) - 3;
    }

    @Override
    public Viewable getViewable() {
	return view;
    }

}
