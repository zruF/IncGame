import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Spells extends JButton implements ActionListener{
	
	int manaCost = 0;
	int wirkung = 0;
	String name;
	Control ctrl;
	double duration = 0;
	double actualDuration = 0;
	boolean active = false;
	
	public Spells(Control ctrl, String name, int wirkung, int manaCost, double duration, String tooltip){
		
		this.duration = duration;
		this.actualDuration = duration;
		this.name = name;
		this.manaCost = manaCost;
		this.wirkung = wirkung;
		this.ctrl = ctrl;
		setText(name + ": " + manaCost + " Mana");
		setPreferredSize(new Dimension(190,30));
		addActionListener(this);
		setToolTipText(tooltip);
		
	}
	
	public void enableButtons(){
		
		if(manaCost > ctrl.currentMana){
			
			setEnabled(false);
			
		}
		else{
			
			setEnabled(true);
			
		}
	}
	
	public void setMana(int value){
		
		ctrl.currentMana -= value;
		
	}
	
	public void durationManagement(double val){
		
		if(active){
			
			setEnabled(false);
			actualDuration -= val;
			if(actualDuration <= 0){
				
				active = false;
				actualDuration = duration;
				ctrl.decreaseMultiplier(wirkung);
				if(ctrl.currentMana > manaCost){
					setEnabled(true);
				}
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		
		setMana(manaCost);
		ctrl.increaseMultiplier(wirkung);
		active = true;
		
	}
}
