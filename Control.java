import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class Control {
	
	BigDecimal cash;
	BigDecimal income;
	int interval = 100;
	Frames frame;
	int clicks;
	
	Helper[] helper = new Helper[6];
	Spells[] spell = new Spells[2];
	Achievements[] achievs = new Achievements[4];
	Achievements lastAchievement = null;
	
	int maxMana = 1000;
	double currentMana = 0;
	double manaIncome = 2;
	BigDecimal multiplier = new BigDecimal("1");
	BigDecimal clickIncome;
	double durationTicker = 1;
	
	PropertiesHandler propertiesHandler;
	private Tick fire;
	private BigDecimal earnedCashOverTime;
	private Timer timer;
	
	String[] numberNames = { "K", "Million", "Billion", "Trillion", "Quadrillion", "Quintillion", "Sextillion", "Septillion", "Octillion", "Nonillion", "Decillion" };
	
	public Control(){
		
		//Save game every minute
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				saveGame();
			}
		}, 60 * 1000, 60 * 1000);
		
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
		
		//Create PropertiesHandler and load the old stage of the game
		propertiesHandler = new PropertiesHandler();
		loadGame();
		
		frame = new Frames(this, helper, spell, achievs);
		frame.changeLastAchievement();
		
		updateCountLabel();
		
		fire = new Tick(this);
		fire.start();
		
		Seconds hDif = Seconds.secondsBetween(new DateTime(getTimeStamp()), DateTime.now());
		
		int pDays = ((hDif.getSeconds()/24)/60)/60;
		int pHours = ((hDif.getSeconds() - (pDays*24*60*60))/60)/60;
		int pMinutes = ((hDif.getSeconds() - ((pDays*24*60*60) + (pHours*60*60)))/60);
		int pSeconds = (hDif.getSeconds() - ((pDays*24*60*60) + (pHours*60*60) + (pMinutes*60)));
		String differenz = pDays + " days and " + pHours + " hours and " + pMinutes + " minutes and " + pSeconds + " seconds";
		
		if (earnedCashOverTime != null) { // if cash earned over time show dialog
			JOptionPane.showMessageDialog(new JFrame(), "<html> You were offline for " + differenz + "<br>"
					+ "You earned " + getNumbers(earnedCashOverTime) + "$ over time!");
		}
		
	}
	
	private void loadGame() {
		try {
			if (propertiesHandler.getProperty("cash") != null) { // testing whether data was saved already
				income = new BigDecimal(propertiesHandler.getProperty("income"));
				multiplier = new BigDecimal(propertiesHandler.getProperty("multiplier"));
				long timestamp = Long.valueOf(propertiesHandler.getProperty("timestamp"));
				BigDecimal oldCash = new BigDecimal(propertiesHandler.getProperty("cash"));
				earnedCashOverTime = new BigDecimal((System.currentTimeMillis() - timestamp)/1000.0).multiply(income).multiply(multiplier);
				cash = oldCash.add(earnedCashOverTime);
				clickIncome = new BigDecimal(propertiesHandler.getProperty("incomeperclick"));
				clicks = Integer.valueOf(propertiesHandler.getProperty("clicks"));
				String lastAchievName = propertiesHandler.getProperty("lastachievement");
				if (!lastAchievName.equals("none")) {
					for (Achievements achievement : achievs) {
						if(achievement.name.equals(lastAchievName)) {
							setLastAchievement(achievement);
						}
					}
				}
				for (int i = 0; i < helper.length; i++) {
					helper[i].count = Integer.valueOf(propertiesHandler.getProperty("helper" + i + "count"));
					helper[i].cost = new BigDecimal(propertiesHandler.getProperty("helper" + i + "cost"));
				}
				
				for (int i = 0; i < achievs.length; i++) {
					if (Boolean.valueOf(propertiesHandler.getProperty("achiev" + i + "achieved"))) {
						achievs[i].setAchieved();
					}
				}
				
			} else { // no data was saved before -> load defaults
				cash = new BigDecimal("1000");
				income = new BigDecimal("0");
				clickIncome = new BigDecimal("1");
				clicks = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private long getTimeStamp(){
		
		try {
			return Long.valueOf(propertiesHandler.getProperty("timestamp"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	public String getNumbers(BigDecimal number){
		
		String str = number.toPlainString();
		if (str.contains(".")) {
			str = str.substring(0, str.indexOf("."));
		}
		int digits = str.length();
		if (digits > 3) {
			if ((digits - 1) / 3 - 1 <= numberNames.length - 1) {
				return str.substring(0, digits - ((digits - 1) / 3) * 3) + "."
						+ str.substring(digits - ((digits - 1) / 3) * 3, digits - ((digits - 1) / 3) * 3 + 2)
						+ numberNames[(digits - 1) / 3 - 1];
			} else {
				DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
				otherSymbols.setDecimalSeparator('.');
				NumberFormat formatter = new DecimalFormat("0.00E0", otherSymbols);
				formatter.setRoundingMode(RoundingMode.HALF_UP);
				return formatter.format(number);
				
			} 
		} else return str;
	}
	
	public void setLastAchievement(Achievements ach){
		
		lastAchievement = ach;
		if (frame != null) {
			frame.changeLastAchievement();
		}
		
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

	public void saveGame() {
		propertiesHandler.setProperty("cash", cash.toString());
		propertiesHandler.setProperty("income", income.toString());
		propertiesHandler.setProperty("incomeperclick", clickIncome.toString());
		propertiesHandler.setProperty("clicks", String.valueOf(clicks));
		propertiesHandler.setProperty("timestamp", String.valueOf(System.currentTimeMillis()));
		propertiesHandler.setProperty("multiplier", multiplier.toString());
		if (lastAchievement != null) {
			propertiesHandler.setProperty("lastachievement", lastAchievement.name);
		}
		else {
			propertiesHandler.setProperty("lastachievement", "none");
		}
		for (int i = 0; i < helper.length; i++) {
			propertiesHandler.setProperty("helper" + i + "count", String.valueOf(helper[i].count));
			propertiesHandler.setProperty("helper" + i + "cost", helper[i].cost.toString());
		}
		for (int i = 0; i < achievs.length; i++) {
			propertiesHandler.setProperty("achiev" + i + "achieved", String.valueOf(achievs[i].achieved));
		}
		propertiesHandler.saveProperties();
	}

	public void resetGame() {
		propertiesHandler.deleteProps();
		Starter.restart();
	}

	public void exitGame() {
		fire.stopFiring();
		System.exit(0);
	}
}
