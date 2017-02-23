package de.uni_kiel.progOOproject17.tests.mvc.pong;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.ViewableData;

public class PongModel extends TickedDataModelContainer<TickedDataModel> {

    private PongField field;
    private Bar bar1;
    private Bar bar2;
    private Ball ball;
    private PongBarBot bot;
    
    public final AbstractAction moveUP;
    public final AbstractAction moveDOWN;
    public final AbstractAction moveNONE;
    

    public PongModel(int maxW, int maxH) {
	field = new PongField(maxW, maxH);
	
	bar1 = new Bar(0, maxH / 2, 10, 50, field);
	bar2 = new Bar(maxW - 10, maxH / 2, 10, 50, field);
	
	ball = new Ball(6, field, bar1, bar2);
	
	bot = new PongBarBot(ball, bar2);
	
	moveUP = bar1.moveUP;     
	moveDOWN = bar1.moveDOWN; 
	moveNONE = bar1.moveNONE; 

	subData.add(field);
	subData.add(bot);
	subData.add(ball);
	subData.add(bar1);
	subData.add(bar2);
	
    }

    @Override
    public Viewable getViewable() {
	
	ViewableData vD = new ViewableData();
	
	//layer1:
	vD.add(field.getViewable());
	//layer2 // but not realy cuz bot has no view ( == null)
	vD.add(bot.getViewable());
	
	
	//layer2:
	ViewableData layer2 = new ViewableData();
	layer2.add(ball.getViewable());
	layer2.add(bar1.getViewable());
	layer2.add(bar2.getViewable());
	vD.add(layer2);
	
	
	
	return vD;
    }

}
