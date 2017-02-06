package de.uni_kiel.progOOproject17.mvctest;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.uni_kiel.progOOproject17.mvctest.mvc.AbstractDataModel;
import de.uni_kiel.progOOproject17.mvctest.mvc.OutputView;

public class OView extends JFrame implements OutputView{


    private JPanel contentPane;

    public OView(String title) {
	this.setTitle(title);
	contentPane = new JPanel();

	contentPane.setBackground(Color.WHITE);

	this.setContentPane(contentPane);
	this.setSize(400, 300);
	this.setVisible(true);
	this.setLocationRelativeTo(null);
    }

    @Override
    public void updateView(AbstractDataModel model) {
	contentPane.setBackground(((Model) model).getBGColor());
	
    }

    

}
