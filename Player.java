import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player extends GameObject {
	
	private float _acc = 1f;
	private float _dcc = 0.5f;
	private KeyInput input;
	private Handler handler;

	public Player(float x, float y, ID id, Handler handler, KeyInput input) {
		super(x, y, id);
		this.input = input;
		this.handler = handler;
		this.w = 32;
		this.h = 32;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		//Horizontal Movement
		//keys 0 = true right
		//keys 1 = true left
		if(input.keys[0]) velX += _acc;
		else if(input.keys[1]) velX -= _acc;
		else if(!input.keys[0] && !input.keys[1]) {
			
			if(velX > 0) velX -= _dcc;
			else if(velX < 0) velX += _dcc;
		}
		
		//Vertical Movement
		//keys 2 = true up
		//keys 3 = true down
		if(input.keys[2]) velY -= _acc;
		else if(input.keys[3]) velY += _acc;
		else if(!input.keys[2] && !input.keys[3]) {

			if(velY > 0) velY -= _dcc;
			else if(velY < 0) velY += _dcc;
		}
		
		velX = clamp(velX, 5, -5);
		velY = clamp(velY, 5, -5);
		
		Collision();
	}
	
	private void Collision() {
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Platform || tempObject.getId() == ID.Enemy) {
				
				if(getFutureBounds().intersects(tempObject.getBounds())) {
					
					if(tempObject.getVelX() < 0) { // Block left
						if(x < tempObject.getX() + tempObject.getW()/2) x = tempObject.getX() - w;
					}else if(tempObject.getVelX() > 0) { // Block right
						if(x > tempObject.getX() + tempObject.getW()/2) x = tempObject.getX() + tempObject.getW();
					}
					
					if(velX > 0) { // Right
						
						velX = 0;
						x = tempObject.getX() - w;
						
					}else if(velX < 0) { //Left
						
						velX = 0;
						x = tempObject.getX() + tempObject.getW();
					}
				}
				
				if(getFutureBounds2().intersects(tempObject.getBounds())) {
					
					if(tempObject.getVelY() < 0) { // Block up
						if(y < tempObject.getY() + tempObject.getH()/2) y = tempObject.getY() - h;
					}else if(tempObject.getVelY() > 0) { // Block down
						if(y > tempObject.getY() + tempObject.getH()/2) y = tempObject.getY() + tempObject.getH();
					}
					
					if(velY > 0) { // Right
						
						velY = 0;
						y = tempObject.getY() - h;
						
					}else if(velY < 0) { //Left
						
						velY = 0;
						y = tempObject.getY() + tempObject.getH();
					}
				}
			}
		}
	}

	private float clamp(float value, float max, float min) {
		if(value >= max) value = max;
		else if(value <= min) value = min;
		
		return value;
	}
	
	// Future X Location
	public Rectangle getFutureBounds() {// Horizontal Collision
		int boundDim = 2;
		
		float bx = x + velX;
		float by = y + boundDim;
		float bw = w + velX/2;
		float bh = h - boundDim*2;
		
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
	
	// Future Y Location
	public Rectangle getFutureBounds2() {// Vertical Collision
		int boundDim = 2;
		
		float bx = x + boundDim;
		float by = y + velY;
		float bw = w - boundDim*2;
		float bh = h + velY/2;
		
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
		
		//-Bounding Boxes-//
		g2d.setColor(Color.red);
		g2d.fill(getFutureBounds());
		
		g2d.setColor(Color.blue);
		g2d.fill(getFutureBounds2());
		//----------------//
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)w, (int)h);
	}

	

}
