import java.awt.BorderLayout;
import java.awt.Frame;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Control {
	
	BigDecimal cash = new BigDecimal("1000");
	BigDecimal income = new BigDecimal("0");
	int interval = 100;
	Frames frame;
	int clicks = 0;
	
	Helper[] helper = new Helper[6];
	Spells[] spell = new Spells[2];
	Achievements[] achievs = new Achievements[4];
	Achievements lastAchievement = null;
	
	int maxMana = 1000;
	double currentMana = 0;
	double manaIncome = 2;
	BigDecimal multiplier = new BigDecimal("1");
	BigDecimal clickIncome = new BigDecimal("1");
	double durationTicker = 1;
	
	public Control(){
		
		//Create Helper
		helper[0] = new Helper(this, new BigDecimal("20"), new BigDecimal("0.1"), new BigDecimal("0.1"), "Oddy", "Oddy ischt sie alle weg. +0.1 Income | +0.1 Clickincome");
		helper[1] = new Helper(this, new BigDecimal("80"), new BigDecimal("0.5"), new BigDecimal("0"), "Steffi", "Stefan ist mit seinem Mofa unterwegs. +1 Income");
		helper[2] = new Helper(this, new BigDecimal("350"), new BigDecimal("2.5"), new BigDecimal("0"), "Urban", "Urban macht eine fette Strandparty in Malle. +2.5 Income");
		helper[3] = new Helper(this, new BigDecimal("1200"), new BigDecimal("10"), new BigDecimal("2"), "Lukas", "Lukas ext eine Flasche tiefgefrorenen Obstler. +10 Income | +2 Clickincome");
		helper[4] = new Helper(this, new BigDecimal("9000"), new BigDecimal("60"), new BigDecimal("3"), "Tschulion", "Der Enboss is back in Game...");
		helper[5] = new Helper(this, new BigDecimal("120000"), new BigDecimal("100"), new BigDecimal("40"), "Jelly Bean", "Rotten Egg haut alle um...");
		
		//Create Spells
		spell[0] = new Spells(this, "LAN", 5, 200, 3, "Die Jungs veranstalten eine fette Lanparty. 500% Cash auf alles!");
		spell[1] = new Spells(this, "Oddys Köpfer", 8, 600, 4, "Oddy macht ein Köpfer und splattert sich den Kopf auf. Der Unterhaltungswert steigert die Produktivität um 800%");
		
		//Create Achievements
		achievs[0] = new Achievements(this, "ClickStarter", "Do " + getNumbers(new BigDecimal("10")) + " Clicks on the Cashbutton.", 10, "+1% Income", 1);
		achievs[1] = new Achievements(this, "ClickNewbie", "Do " + getNumbers(new BigDecimal("50")) + " Clicks on the Cashbutton.", 50, "+2% Income", 2);
		achievs[2] = new Achievements(this, "ClickRookie", "Do " + getNumbers(new BigDecimal("250")) + " Clicks on the Cashbutton.", 250, "+5% ClickIncome", 5);
		achievs[3] = new Achievements(this, "ClickProfi", "Do " + getNumbers(new BigDecimal("1000")) + " Clicks on the Cashbutton", 1000, "+10% ClickIncome", 10);
		
		frame = new Frames(this, helper, spell, achievs);
		Tick fire = new Tick(this);
		fire.start();
		
	}
	
	public String getNumbers(BigDecimal number){
		
		String showString = "";
		
		if(number.compareTo(new BigDecimal(Math.pow(10, 33))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 33))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 33)))).setScale(2, RoundingMode.HALF_UP)) + "Decillion";
					
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 30))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 30))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 30)))).setScale(2, RoundingMode.HALF_UP)) + "Nonillion";
					
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 27))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 27))) == 0){
					
			showString = ((number.divide(new BigDecimal(Math.pow(10, 27)))).setScale(2, RoundingMode.HALF_UP)) + "Octillion";
					
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 24))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 24))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 24)))).setScale(2, RoundingMode.HALF_UP)) + "Septillion";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 21))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 21))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 21)))).setScale(2, RoundingMode.HALF_UP)) + "Sextillion";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 18))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 18))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 18)))).setScale(2, RoundingMode.HALF_UP)) + "Quintillion";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 15))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 15))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 15)))).setScale(2, RoundingMode.HALF_UP)) + "Quadrillion";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 12))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 12))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 12)))).setScale(2, RoundingMode.HALF_UP)) + "Trillion";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 9))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 9))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 9)))).setScale(2, RoundingMode.HALF_UP)) + "Billion";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 6))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 6))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 6)))).setScale(2, RoundingMode.HALF_UP)) + "Million";
			
		}
		else if(number.compareTo(new BigDecimal(Math.pow(10, 3))) == 1 || number.compareTo(new BigDecimal(Math.pow(10, 3))) == 0){
			
			showString = ((number.divide(new BigDecimal(Math.pow(10, 3)))).setScale(2, RoundingMode.HALF_UP)) + "K";
			
		}
		else {
			
			showString = number.setScale(2, RoundingMode.HALF_UP).toString();
			
		}
		
		return showString;
	}
	
	public void setLastAchievement(Achievements ach){
		
		lastAchievement = ach;
		
	}
	
	public BigDecimal incrementCash(BigDecimal value){
		
		cash = cash.add(value).setScale(2, RoundingMode.HALF_UP);
		return cash;
		
	}
	
	public void increaseHelperIncome(BigDecimal val, BigDecimal clickVal){
		
		income = income.add(val);
		clickIncome = clickIncome.add(clickVal);
		
	}
	
	public void increaseIncome(BigDecimal val, BigDecimal clickVal){
		
		income = income.add(val.divide(new BigDecimal("100")));
		clickIncome = clickIncome.add(clickVal.divide(new BigDecimal("100")));
		
	}
	
	public void updateCountLabel(){
		
		String prep = "<html>";
		
		for(int i = 0; i < helper.length; i++){
			
			prep += helper[i].name + "'s got: " + helper[i].count + "<br>";
			
		}
		prep += "<br>" + "Click Counter: " + clicks;
		prep += "</html>";
		frame.countLabel.setText(prep);
		
	}
	
	
	public void increaseClicks(){
		
		clicks += 1;
		
	}
	
	
	public void increaseMultiplier(double multi){
		
		multiplier = multiplier.multiply(new BigDecimal(multi)).setScale(2, RoundingMode.HALF_UP);
		
	}
	
	public void decreaseMultiplier(int multi){
		
		multiplier = multiplier.divide(new BigDecimal(multi));
		
	}
	
	public void tick(){
		
		if(currentMana < maxMana){
			currentMana += manaIncome/(1000/interval);
		}
		else if(currentMana >= maxMana){
			
			currentMana = maxMana;
			
		}
		cash = cash.add(income.divide(new BigDecimal(1000/interval)).multiply(multiplier)).setScale(2, RoundingMode.CEILING);
		frame.cashLabel.setText("Cash: " + getNumbers(cash) + " $");
		frame.incomeLabel.setText("<html>" + "Income: " + getNumbers(income.multiply(multiplier)) + "$ /second" + "<br>" + "Cash per Click: " + getNumbers(clickIncome.multiply(multiplier)) + "$ /click" + "</html>");
		frame.manaBar.setValue((int)currentMana);
		frame.manaBar.setString((int)currentMana + "/" + maxMana + " Mana");
		frame.manaBar.setToolTipText(manaIncome + " mana/s");
		for(int i = 0; i < helper.length; i++){
			
			helper[i].enableButtons();
			
		}
		for(int i = 0; i < spell.length; i++){
			
			spell[i].enableButtons();
			spell[i].durationManagement(durationTicker/(1000/interval));
			
		}
		for(int i = 0; i < achievs.length; i++){
			
			achievs[i].checkAchievement();
			
		}

	}
}