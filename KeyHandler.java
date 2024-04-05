import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	public boolean sPressed, dPressed, kPressed, lPressed;
	
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_S) {
			sPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			dPressed = true;
		}
		if(code == KeyEvent.VK_K) {
			kPressed = true;
		}
		if(code == KeyEvent.VK_L) {
			lPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_S) {
			sPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			dPressed = false;
		}
		if(code == KeyEvent.VK_K) {
			kPressed = false;
		}
		if(code == KeyEvent.VK_L) {
			lPressed = false;
		}
	}
	
}
