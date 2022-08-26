import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Block extends GameObject {

	private float _acc = 0.2f;
	private float _dcc = 0.1f;
	private boolean upDecel = true;
	private boolean downDecel = false;
	
	public Block(float x, float y, ID id) {
		super(x, y, id);
		this.w = 32;
		this.h = 32;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if(id == ID.Platform) {
			
			if(x > 295 && x < 300 && upDecel) {
				velX -= _dcc;
				velY += _dcc;
				if(velX < 0 || velY < 0) {
					velX = -1;
					velY = 1;
					upDecel = false;
					downDecel = true;
				}
			}
			if(x < 105 && x > 100 && downDecel) {
				velX += _dcc;
				velY -= _dcc;
				if(velX < 0 || velY < 0) {
					velX = 1;
					velY = -1;
					downDecel = false;
					upDecel = true;
				}
			}
			
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)w, (int)h);
	}

}
