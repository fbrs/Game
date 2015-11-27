/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;
import game.models.Player;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 *
 * @author Luboš
 */
public class GamePanel extends JPanel implements Runnable{
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    
    private Thread thread;
    private boolean running;
    
    private BufferedImage image;
    private Graphics2D g;
    
    private int FPS = 30;
    private int targetTime = 1000/FPS;
    
    private TileMap tileMap;
    private Player player;
    
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void run(){
        
        init();
        
        long startTime;
        long urdTime;
        long waitTime;
        
        while(running){
            
            startTime = System.nanoTime();
            
            update();
            render();
            draw();
            
            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            
            try{
                Thread.sleep(waitTime);
            }
            catch(Exception e){
            }
        }
        
    }
    
    public void init(){
        
        running = true;
        
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        tileMap = new TileMap("src/game/levels/testMap.txt", 40);
    }
    
    ///////////////////////////////////////////////////////
    
    private void update(){
        
        tileMap.update();
        player.update();
    }
    
    private void render(){
        
        tileMap.draw(g);
        player.draw(g);
    }
    
    private void draw(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        
    }
    
}
