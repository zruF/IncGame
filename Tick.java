
public class Tick extends Thread{
	
	Control ctrl;
	
	public Tick(Control ctrl){
		
		this.ctrl = ctrl;
		
	}
	
	public void run(){
		
		while(true){
		
			ctrl.tick();
			
			try {
				Thread.sleep(ctrl.interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
		
	}

}
