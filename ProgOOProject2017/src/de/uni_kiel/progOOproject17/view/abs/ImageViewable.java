package de.uni_kiel.progOOproject17.view.abs;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class ImageViewable implements Viewable{

	private Image imgHI;
	private Image imgLOW;
	private Rectangle rect;
	
	public ImageViewable(Image hi, Image low, Rectangle rect) {
		this.rect = rect;
		this.imgHI = hi;
		this.imgLOW = low;
		
	}

	@Override
	public void render(Graphics gr) {
		gr.drawImage(imgHI, rect.x, rect.y, rect.width, rect.height, null);
		
	}

	@Override
	public void renderLOW(Graphics gr) {
		gr.drawImage(imgLOW, rect.x, rect.y, rect.width, rect.height, null);
		
	}

}
