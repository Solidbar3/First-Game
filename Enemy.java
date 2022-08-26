import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Enemy extends GameObject {
	
	private Handler handler;
	private int frameCount;
	private boolean frame;
	
	public Enemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.w = 32;
		this.h = 32;
		this.handler = handler;
		this.frameCount = 0;
		this.frame = false;
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if(frame) frameCount++;
		
		Collision();
		Lifetime();
	}
	
	private void Collision() {
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Platform || tempObject.getId() == ID.Player || tempObject.getId() == ID.Bullet) {
				
				if(getFutureBounds().intersects(tempObject.getBounds())) {
					
					if(tempObject.getVelX() < 0) { // Object Moving left
						if(x < tempObject.getX() + tempObject.getW()/2) x = tempObject.getX() - w;
						
						if(tempObject.getId() == ID.Bullet) {
							frame = true;
						}
					}else if(tempObject.getVelX() > 0) { // Object Moving right
						if(x > tempObject.getX() + tempObject.getW()/2) x = tempObject.getX() + tempObject.getW();
						
						if(tempObject.getId() == ID.Bullet) {
							frame = true;
						}
					}
					
					if(velX > 0) { // Enemy Moving Right
						
						velX = 0;
						x = tempObject.getX() - w;
						
					}else if(velX < 0) { // Enemy Moving Left
						
						velX = 0;
						x = tempObject.getX() + tempObject.getW();
					}
				}
				
				if(getFutureBounds2().intersects(tempObject.getBounds())) {
					
					if(tempObject.getVelY() < 0) { // Object Moving up
						if(y < tempObject.getY() + tempObject.getH()/2) y = tempObject.getY() - h;
						
						if(tempObject.getId() == ID.Bullet) {
							frame = true;
						}
					}else if(tempObject.getVelY() > 0) { // Object Moving down
						if(y > tempObject.getY() + tempObject.getH()/2) y = tempObject.getY() + tempObject.getH();
						
						if(tempObject.getId() == ID.Bullet) {
							frame = true;
						}
					}
					
					if(velY > 0) { // Enemy Moving Right
						
						velY = 0;
						y = tempObject.getY() - h;
						
					}else if(velY < 0) { // Enemy Moving Left
						
						velY = 0;
						y = tempObject.getY() + tempObject.getH();
					}
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
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)w, (int)h);
	}

}
