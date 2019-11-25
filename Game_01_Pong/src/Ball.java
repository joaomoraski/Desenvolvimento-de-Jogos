import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	public double x,y; //tem IA então a velocidade pode mudar
	public int width, height;

	public double dx,dy;
	public double speed = 1.7;
	
	public Ball(int x,int y) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		
		
		int angle = new Random().nextInt(120-45)+45;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() {//toda a logica, colisão
		
		if(x+(dx*speed) + width >= Game.WIDTH) {
			dx*= -1;
			
		}else if (x+(dx*speed)<0) {
			dx*= -1;
		}
		
		if(y >= Game.HEIGHT) {
			//ponto do inimigo
			System.out.println("Ponto do inimigo!!");
			new Game();
			return;
		}else if (y<0) {
			//ponto do jogador, game over
			System.out.println("Ponto nosso!! YAYYY!");
			new Game();
			return;
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)), (int)(y+(dx*speed)),width,height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsEnemy= new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);		//dados tem que ser em int
		
		if(bounds.intersects(boundsPlayer)) {//colisão
			int angle = new Random().nextInt(120-45)+45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy> 0) {
				dy*=-1;
			}

		} else if(bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120-45)+45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy < 0) 
				dy*=-1;
			

		}
		
		x+= dx*speed;
		y+= dy*speed;
		
		
	}
	
	public void render(Graphics g) {//toda a renderização
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, width, height);
		
	}
}


