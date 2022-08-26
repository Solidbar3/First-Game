import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	public boolean keys[] = new boolean[4];
	//keys 0 = true right
	//keys 1 = true left
	//keys 2 = true up
	//keys 3 = true down
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_D: keys[0] = true; break;
			case KeyEvent.VK_A: keys[1] = true; break;
			case KeyEvent.VK_W: keys[2] = true; break;
			case KeyEvent.VK_S: keys[3] = true; break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_D: keys[0] = false; break;
			case KeyEvent.VK_A: keys[1] = false; break;
			case KeyEvent.VK_W: keys[2] = false; break;
			case KeyEvent.VK_S: keys[3] = false; break;
			case KeyEvent.VK_ESCAPE:
				System.out.println("Exiting now...");
				System.exit(0);
				break;
		}
	}
	
}
