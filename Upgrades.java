import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Upgrades extends JButton implements ActionListener{
	
	String name;
	Helper helper;
	String requirement;
	int reqValue;
	String reward;
	int rewardValue;
	private boolean bought = false;
	
	public Upgrades(String name, Helper helper, String requirement, String reward){
		
		this.name = name;
		this.helper = helper;
		this.requirement = requirement;
		this.reward = reward;
		this.rewardValue = rewardValue;
		
	}
	
	public void setUpgradeBuyable(){
		
		if(helper.count >= reqValue){
			
			setEnabled(true);
			
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
	
		upgradeBought();
		
	}
	
	private void upgradeBought(){
		
		
		setBackground(Color.GREEN);
		
		
	}

}
