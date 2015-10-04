import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Helper extends JButton implements ActionListener{
	
	BigDecimal cost = new BigDecimal("0");
	BigDecimal income = new BigDecimal("0");
	BigDecimal clickIncome = new BigDecimal("0");
	String name;
	Control ctrl;
	JLabel nameLabel, incomeLabel;
	int count = 0;
	
	public Helper(Control ctrl, BigDecimal cost, BigDecimal income, BigDecimal clickIncome, String name, String tooltip){
		
		this.clickIncome = clickIncome;
		this.ctrl = ctrl;
		this.cost = cost;
		this.income = income;
		this.name = name;
		setPreferredSize(new Dimension(220,70));
		addActionListener(this);
		setToolTipText(tooltip);
		nameLabel = new JLabel(name + ": $" + ctrl.getNumbers(this.cost));
		incomeLabel = new JLabel("Income: $" + income + " | Click: $" + clickIncome);
		
		setLayout(new BorderLayout());
		setFont(new Font("Arial", Font.PLAIN, 2));
		add(BorderLayout.NORTH, nameLabel);
		add(BorderLayout.SOUTH, incomeLabel);
	}
	
	public void increaseCost(){
		
		cost = cost.add(cost.multiply(new BigDecimal("0.08")));
		
	}
	
	public BigDecimal getIncome(){
		
		return income;
		
	}
	
	public void enableButtons(){
		
		if(cost.compareTo(ctrl.cash) == 1){
			
			setEnabled(false);
			
		}
		else{
			
			setEnabled(true);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		addUpHelper();
		
	}

	private void addUpHelper() {
		count += 1;
		ctrl.increaseHelperIncome(getIncome(), clickIncome);
		ctrl.incrementCash(cost.negate());
		increaseCost();
		nameLabel.setText(name + ": $" + ctrl.getNumbers(cost));
		ctrl.updateCountLabel();
	}
}
