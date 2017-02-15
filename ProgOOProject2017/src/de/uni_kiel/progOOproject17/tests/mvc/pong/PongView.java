package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.uni_kiel.progOOproject17.tests.mvc.abst.AbstractDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.FramedIOView;

public class PongView extends FramedIOView {

    private BufferedImage img;

    public PongView(String title, int w, int h) {
	super(title, w, h, false);
	img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public void render(AbstractDataModel model) {

	/*
	 * TODO
	 * This redering part is not very beautiful...
	 * How to do u get the data in the model in a more elegant way?
	 * 
	*/
	PongModel m = (PongModel) model;

	Graphics gr = img.getGraphics();
	gr.setColor(Color.GREEN);
	gr.fillRect(0, 0, m.field.w, m.field.h);


	gr.setColor(Color.BLACK);
	gr.fillRect(m.field.bar1.x, m.field.bar1.y, m.field.bar1.w, m.field.bar1.h);
	gr.fillRect(m.field.bar2.x, m.field.bar2.y, m.field.bar2.w, m.field.bar2.h);
	
	gr.drawLine(m.field.w/2, 0, m.field.w/2, m.field.h);

	gr.setFont(new Font(null, Font.BOLD, 20));
	gr.drawString(" " + m.field.points1, m.field.w/2 - 70, 60);
	gr.drawString(" " + m.field.points2, m.field.w/2 + 45, 60);
	
	gr.setColor(Color.BLUE);
	gr.fillOval(m.field.ball.x - m.field.ball.r, m.field.ball.y - m.field.ball.r, m.field.ball.r*2, m.field.ball.r*2);

	gr.dispose();
	gr = centerPane.getGraphics();
	gr.drawImage(img, 0, 0, null);
	gr.dispose();

    }

}
