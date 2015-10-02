import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Frames implements ActionListener{
	
	JFrame mainFrame, achievementFrame;
	JPanel clickPanel, upgradePanel, statPanel, castPanel, lastAchievementPanel;
	JLabel headerLabel, cashLabel, incomeLabel, clickIncomeLabel, tooltipLabel, lastAchievementLabel, countLabel;
	JButton clickButton, upgradeButton1, upgradeButton2, achievements;
	Helper[] helper;
	Spells[] spell;
	Achievements[] achievs;
	Control ctrl;
	JProgressBar manaBar;
	
	public Frames(Control ctrl, Helper[] helper, Spells[] spell, Achievements[] achievs){
		
		this.achievs = achievs;
		this.helper = helper;
		this.spell = spell;
		this.ctrl = ctrl;
		
		//Frames
		mainFrame = new JFrame();
		achievementFrame = new JFrame();
		
		achievementFrame.setLayout(new FlowLayout());
		achievementFrame.setSize(600, 400);
		
		//Panels
		clickPanel = new JPanel();
		upgradePanel = new JPanel();
		statPanel = new JPanel();
		castPanel = new JPanel();
		lastAchievementPanel = new JPanel();
		upgradePanel.setLayout(new FlowLayout());
		upgradePanel.setPreferredSize(new Dimension(250, 400));
		
		//Labels
		cashLabel = new JLabel("Cash: " + ctrl.getNumbers(ctrl.cash));
		incomeLabel = new JLabel("Income: " + ctrl.getNumbers(ctrl.income));
		//clickIncomeLabel = new JLabel("ClickIncome: " + ctrl.clickIncome);
		tooltipLabel = new JLabel("Hover to show tooltip");
		lastAchievementLabel = new JLabel("Last achievement earned: ");
		headerLabel = new JLabel("Statistics!");
		headerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		countLabel = new JLabel();
		
		//Buttons
		achievements = new JButton("Achievements");
		clickButton = new JButton("CashButton");
		clickButton.setPreferredSize(new Dimension(200,80));
		clickButton.addActionListener(this);
		achievements.addActionListener(this);
		clickPanel.setLayout(new GridLayout(5,1));
		
		//Progressbar
		manaBar = new JProgressBar(0, ctrl.maxMana);
		manaBar.setStringPainted(true);
		
		castPanel.add(manaBar);
		statPanel.setLayout(new GridLayout(3,1));
		statPanel.add(headerLabel);
		statPanel.add(countLabel);
		
		for(int i = 0; i < helper.length; i++){
			
			upgradePanel.add(helper[i]);
			
		}
		upgradePanel.add(tooltipLabel);
		upgradePanel.add(achievements);
		
		for(int i = 0; i < spell.length; i++){
			
			castPanel.add(spell[i]);
			
		}
		
		for(int i = 0; i < achievs.length; i++){
			
			achievementFrame.add(achievs[i]);
			
		}
		
		mainFrame.setTitle("IncGame v0.1");
		
		mainFrame.setLayout(new BorderLayout());
		clickPanel.add(cashLabel);
		clickPanel.add(incomeLabel);
		//clickPanel.add(clickIncomeLabel);
		clickPanel.add(clickButton);
		clickPanel.add(lastAchievementLabel);
		clickPanel.add(lastAchievementPanel);
		mainFrame.add(clickPanel, BorderLayout.WEST);
		mainFrame.add(upgradePanel, BorderLayout.EAST);
		mainFrame.add(castPanel, BorderLayout.SOUTH);
		mainFrame.add(statPanel, BorderLayout.CENTER);
		
		mainFrame.setSize(800, 600);
		mainFrame.setVisible(true);
		
		
	}
	
	public void changeLastAchievement(){
		
		if(ctrl.lastAchievement != null){
			
			lastAchievementLabel.setText("Last Achievement earned: " + ctrl.lastAchievement.name);
			
		}
	}
	

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(clickButton)){
			
			ctrl.incrementCash(ctrl.clickIncome.multiply(ctrl.multiplier));
			cashLabel.setText("Cash: " + ctrl.getNumbers(ctrl.cash));
			ctrl.increaseClicks();
			ctrl.updateCountLabel();
			
		}
		
		if(e.getSource().equals(achievements)){
			
			achievementFrame.setVisible(true);
			
		}
	}
}
