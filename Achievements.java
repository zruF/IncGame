import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Achievements extends JButton{
	
	Control ctrl;
	String name = "";
	String requirement = "";
	int reqValue;
	String reward;
	int rewardValue;
	JLabel nameLabel, reqLabel, rewLabel;
	boolean achieved = false;
	
	public Achievements(Control ctrl, String name, String requirement, int reqValue, String reward, int rewardValue){
		
		this.ctrl = ctrl;
		this.name = name;
		this.requirement = requirement;
		this.reqValue = reqValue;
		this.reward = reward;
		this.rewardValue = rewardValue;
		nameLabel = new JLabel(name);
		reqLabel = new JLabel(requirement);
		rewLabel = new JLabel(reward);
		reqLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		rewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		setLayout(new BorderLayout());
		
		add(nameLabel, BorderLayout.NORTH);
		add(rewLabel, BorderLayout.CENTER);
		add(reqLabel, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(250,50));
		setBackground(Color.RED);
		setEnabled(false);
		
	}
	
	
	public void checkAchievement(){
		
		if(!achieved){
			if(requirement.contains("Click")){
				
				if(ctrl.clicks >= reqValue){
					
					getAchievement();
					
				}
				
			}
		}
	}
	
	public void getAchievement(){
		
		if(reward.contains("ClickIncome")){
			
			achieved = true;
			ctrl.increaseIncome(new BigDecimal("0"), new BigDecimal(rewardValue));
			
		}
		else if(reward.contains("Income")){
			
			achieved = true;
			ctrl.increaseIncome(new BigDecimal(rewardValue), new BigDecimal("0"));
			
		}
		setBackground(Color.GREEN);
		ctrl.setLastAchievement(this);
	}


	public void setAchieved() {
		achieved = true;
		setBackground(Color.GREEN);
	}
}
