
public class Tick extends Thread{
	
	Control ctrl;
	private boolean keepFiring;
	
	public Tick(Control ctrl){
		
		this.ctrl = ctrl;
		
	}
	
	@Override
	public void run(){
		
		while(true){
			if (keepFiring) {
				ctrl.tick();
				
				try {
					Thread.sleep(ctrl.interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		
		}
		
	}
	
	@Override
	public synchronized void start() {
		keepFiring = true;
		super.start();
	}

	public void stopFiring() {
		keepFiring = false;
	}

}
