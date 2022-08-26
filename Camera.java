
public class Camera {

	private float x, y;
	private Handler handler;
	private GameObject tempPlayer = null;
	
	public Camera(float x, float y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		
		findPlayer();
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
	
	public void tick() {
		if(tempPlayer != null) {
			float camX = ((tempPlayer.x - x) - Game.WIDTH/2 + tempPlayer.w/2);
			float camY = ((tempPlayer.y - y) - Game.HEIGHT/2 + tempPlayer.h/2);
			
			x += camX * 0.05f;
			y += camY * 0.05f;
			
			// Level Borders
			if(x <= 0) x = 0;
			if(x >= Game.WIDTH+12) x = Game.WIDTH+12;
			if(y <= 0) y = 0;
			if(y >= Game.HEIGHT-29) y = Game.HEIGHT-29;
			
		}else findPlayer();
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
