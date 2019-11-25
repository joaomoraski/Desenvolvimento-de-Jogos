package com.moraski.main;



import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.moraski.entities.Entity;
import com.moraski.entities.Player;
import com.moraski.graficos.Spritesheet;
import com.moraski.world.World;

public class Game extends Canvas implements Runnable, KeyListener{
	// criação de variaveis, instanciação de classes ou pacotes e etc
	//private, apenas esse emtodo acessa
	//public todos acessam
	//static nao inicializa toda hora que iniciar o metodo, so inicia 1 vez
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
    private Thread thread;
    private boolean isRunning;
    private final int WIDTH = 240;
    private final int HEIGHT = 160;
    private final int SCALE = 3;
    
    private BufferedImage image;
   
    public static List<Entity> entities;
    public static Spritesheet spritesheet;
    
    public static World world;
    
    public static Player player;
    
    public Game(){
    	//inicializa metodos e coisas do tipo para o jogo
    	addKeyListener(this);//tem que colocar aq por causa do canvas, this é pq esta no proprio codigo
        this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        initFrame();
        //inicializando objetos. 
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();
        spritesheet = new Spritesheet("/spritesheet.png");//world dps pq o sprite ta na sprite sheet, tem que estar tudo na ordem
        player = new Player(0,0,16,16,spritesheet.getSprite(32, 0, 16, 16));//player antes do world
        entities.add(player);
        world = new World("/mapa.png");
        
        
    }
    
    public void initFrame(){
        frame = new JFrame("Game #1");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    
    public synchronized void stop(){
        isRunning = false;
        try {
        	thread.join();
        } catch (InterruptedException e){
        	e.printStackTrace();
        }
    }
    
    public static void main(String args[]){
        Game game = new Game();
        game.start();
    }

    public void tick(){ //logica
    	for(int i = 0; i < entities.size(); i++) {
    		Entity e = entities.get(i);
    		//if (e instanceof Player) {
    			//estou dando tick no player
    		//}
    		e.tick();
    	}
    }
    
    public void render(){ //graficos
    	BufferStrategy bs = this.getBufferStrategy();
    	if(bs == null) {
    		this.createBufferStrategy(3);
    		return;
    	}
    	Graphics g = image.getGraphics();
    	g.setColor(new Color(0,0,0));
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
    	
    	//renderização do jogo
    	world.render(g);
    	for(int i = 0; i < entities.size(); i++) {
    		Entity e = entities.get(i);
    		e.render(g);
    	}
    	
    	g.dispose();
    	g = bs.getDrawGraphics();
    	g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
    	bs.show();
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();//pega a hora do pc em nano segundos, mais precisão
        double amountOfticks = 60.0;
        double ns = 1000000000 / amountOfticks;
        int frames = 0;
        double timer = System.currentTimeMillis();
        double delta = 0;
        while (isRunning) {            
            long now = System.nanoTime();
            delta+= (now-lastTime)/ns;
            lastTime = now;
            if (delta>=1) {
                tick();
                render();
                frames++;
                delta--;
            }
            if (System.currentTimeMillis() - timer >=1000) {
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer+=1000;
            }
        }

    }

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {//segue o padrão, VK_Tecla
				player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				player.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ASTERISK) {
				Game game = new Game();
				game.start();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {//segue o padrão, VK_Tecla
				player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.left = false;
		}
	
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				player.down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    
}
