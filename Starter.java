
public class Starter {

	private static boolean restart;

	public static void main(String[] args) {
		restart = true;
		while (true) {
			if (restart) {
				new Control();
				restart = false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public static void restart() {
		restart = true;
	}

}
