import java.awt.Color;
import java.awt.Graphics;


public class Enemy {

	public double x,y; //tem IA então a velocidade pode mudar
	public int width, height;
	public Enemy(int x,int y) {
		this.x = x;
		this.y = y;
		this.width = 40;
		this.height = 5;
	}
	
	public void tick() {//toda a logica
		x+= (Game.ball.x - x - 6) * 0.2;//fazer o inimigo perder as vezes kkkk
		
	}
	
	public void render(Graphics g) {//toda a renderização
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, width, height);
		
	}
}
