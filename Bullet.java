import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Bullet extends GameObject {

	private Handler handler;
	
	private float h_dcc = 0f;
	private float v_dcc = 0f;
	private int frameCount;
	private boolean frame;
	
	public Bullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.w = 8;
		this.h = 8;
		this.handler = handler;
		this.frameCount = 0;
		this.frame = false;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		velX += h_dcc;
		velY += v_dcc;
		
		if((clamp(velX, 1, -1) == velX)) {
			h_dcc = 0f;
			velX = 0;
		}
		if((clamp(velY, 1, -1) == velY)) {
			v_dcc = 0f;
			velY = 0;
		}
		if(frame) frameCount++;
		
		Collision();
		Lifetime();
	}

	private float clamp(float value, float max, float min) {
		if(value >= max) value = max;
		else if(value <= min) value = min;
		
		return value;
	}
	
	private void Collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			
			if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Platform || tempObject.getId() == ID.Enemy) {
				int decelFactor = 60;
				
				if(getFutureBounds().intersects(tempObject.getBounds())) {
					if(tempObject.getId() == ID.Enemy) { // Remove bullet on contact of Enemy
						handler.removeObject(this);			
					}
					
					if(tempObject.getVelX() < 0) { // Block left
						if(x < tempObject.getX() + tempObject.getW()/2) x = tempObject.getX() - w;
					}else if(tempObject.getVelX() > 0) { // Block right
						if(x > tempObject.getX() + tempObject.getW()/2) x = tempObject.getX() + tempObject.getW();
					}
					
					if(velX > 0) { // Moving Right
						
						velX = velX * -1;
						h_dcc =(float)(-velX / decelFactor); // Decelerate
						if(velY > 0) v_dcc += -(velY / decelFactor);
						if(velY < 0) v_dcc += -(velY / decelFactor);
						x = tempObject.getX() - w;
						
					}else if(velX < 0) { // Moving Left
						
						velX = velX * -1;
						h_dcc = (float)(-velX / decelFactor); // Decelerate
						if(velY > 0) v_dcc += -(velY / decelFactor);
						if(velY < 0) v_dcc += -(velY / decelFactor);
						x = tempObject.getX() + tempObject.getW();
					}
					frame = true;
				}
				
				if(getFutureBounds2().intersects(tempObject.getBounds())) {
					if(tempObject.getId() == ID.Enemy) { // Remove bullet on contact of Enemy
						handler.removeObject(this);
					}
					
					if(tempObject.getVelY() < 0) { // Block up
						if(y < tempObject.getY() + tempObject.getH()/2) y = tempObject.getY() - h;
					}else if(tempObject.getVelY() > 0) { // Block down
						if(y > tempObject.getY() + tempObject.getH()/2) y = tempObject.getY() + tempObject.getH();
					}
					
					if(velY > 0) { // Moving Down
						
						velY = velY * -1;
						v_dcc = (float)(-velY / decelFactor); // Decelerate
						if(velX > 0) h_dcc += -(velX / decelFactor);
						if(velX < 0) h_dcc += -(velX / decelFactor);
						y = tempObject.getY() - h;
						
					}else if(velY < 0) { // Moving Up
						
						velY = velY * -1;
						v_dcc = (float)(-velY / decelFactor); // Decelerate
						if(velX > 0) h_dcc += -(velX / decelFactor);
						if(velX < 0) h_dcc += -(velX / decelFactor);
						y = tempObject.getY() + tempObject.getH();
					}
					frame = true;
				}
			}
		}
	}
	
	private void Lifetime() {
		if(frameCount >= 20) {
			handler.removeObject(this);
			frameCount = 0;
		}
	}
	// Future X Location
	public Rectangle getFutureBounds() {// Horizontal Collision
		
		float bx = x + velX;
		float by = y;
		float bw = w + velX/2;
		float bh = w;
		
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
	
	// Future Y Location
	public Rectangle getFutureBounds2() {// Vertical Collision
		
		float bx = x;
		float by = y + velY;
		float bw = w;
		float bh = w + velY/2;
		
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)w, (int)h);
	}
}
