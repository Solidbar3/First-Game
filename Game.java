import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

	public static int WIDTH = 1024, HEIGHT = 608;
	public String title = "My Game";
	
	private Thread thread;
	private boolean isRunning;
	
	private Handler handler;
	private KeyInput input;
	private MouseInput minput;
	private Camera cam;
	
	private BufferedImage level = null;
	
	public Game() {
		// Construct
		new Window(WIDTH, HEIGHT, title, this);
		start();
		
		init();
		
	}
	
	public void init() {
		handler = new Handler();
		input = new KeyInput();
		cam = new Camera(0, 0, handler);
		minput = new MouseInput(handler, cam);
		this.addKeyListener(input);
		this.addMouseListener(minput);
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/Level1.png");
		
		
		loadLevel(level);
		
		//handler.addObject(new Enemy(100, 100, ID.Enemy));
		
//		handler.addObject(new Player(100, 100, ID.Player, handler, input));
//		GameObject b1 = new Block(100, 300, ID.Platform);
//		b1.velX = 1;
//		b1.velY = -1;
//		GameObject b2 = new Block(364, 100, ID.Block);
//		handler.addObject(b1);
//		handler.addObject(b2);
		
	}
	
	private synchronized void start() {
		if(isRunning) return;
		
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	// Gameloop
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		// Updates the Game
		handler.tick();
		cam.tick();
	}
	
	private void render() {
		// Renders the Game
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		// Meat and bones of rendering
		g.setColor(Color.magenta);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2d.translate(-cam.getX(), -cam.getY());
		
		handler.render(g);
		
		g2d.translate(cam.getX(), cam.getY());
		
		bs.show();
		g.dispose();
	}

	// Loading the level
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255)
					handler.addObject(new Block(xx*32, yy*32, ID.Block));
				
				if(blue == 255)
					handler.addObject(new Player(xx*32, yy*32, ID.Player, handler, input));
				
				if(green == 255)
					handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler));
			}
		}
	}
	
	private synchronized void stop() {
		if(!isRunning) return;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isRunning = false;
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
