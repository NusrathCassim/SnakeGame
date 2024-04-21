/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{
    static final int Screen_width = 600;
    static final int Screen_height = 600;
    static final int unit_size = 25;
    static final int Game_units = (Screen_width*Screen_height)/unit_size;
    static final int Delay =75;
    
    final int x[]= new int[Game_units];
    final int y[]= new int[Game_units];
    int body_parts = 6;
    int dotsEaten = 0;
    int dotX;
    int dotY;
    char Direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    
    
    
    public GamePanel() {
        random = new Random ();
        this.setPreferredSize(new Dimension(Screen_width,Screen_height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        Start();
    }
    public void Start(){
        //Everthing starts from here
        newDot();
        running = true;
        timer = new Timer(Delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        //for the grid
        for(int i=0; i < Screen_height/unit_size; i++){
            g.drawLine(i*unit_size ,0 ,i*unit_size , Screen_height );
            g.drawLine(0,i*unit_size ,Screen_width , i*unit_size );
        }
        //dot
        g.setColor(Color.ORANGE);
        g.fillOval(dotX, dotY, unit_size, unit_size);
        
        //for the snake
        for(int i=0; i<body_parts; i++){
            if(i ==0 ){
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], unit_size, unit_size);
            }else{
                g.setColor(new Color(50,205,50));
                g.fillRect(x[i], y[i], unit_size, unit_size);
            }
        }
        
        
    }
    
    public void newDot(){
        dotX = random.nextInt((int)(Screen_width/unit_size))*unit_size;
        dotY = random.nextInt((int)(Screen_height/unit_size))*unit_size;
    }
    public void move(){
        for(int i = body_parts ; i>0 ; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        
        switch(Direction){
            case 'U':
                y[0] = y[0]-unit_size;
                break;
            case 'D':
                y[0] = y[0]+unit_size;
                break;
            case 'L':
                x[0] = x[0]-unit_size;
                break;    
            case 'R':
                x[0] = x[0]+unit_size;
                break; 
        }
    }
    
    public void checkScore(){
        if((x[0] == dotX)&& (y[0] == dotY)){
            body_parts++;
            dotsEaten++;
            newDot();
            
            
        }
    }
    public void collisionchecker(){
        //head collision
        for(int i =body_parts ;i>0 ; i--){
            if((x[0]== x[i]&& y[0]==y[i])){
                running= false;
            }
        }
        //collide with left border
        if(x[0]<0){
            running = false;
        }
        //right
        if(x[0]>= Screen_width ){
            running = false;
        }
        //top
         if(y[0]< 0 ){
            running = false;
        }
        //bottom
        if(y[0]>= Screen_height ){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void GameOver(Graphics g){
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if(running){
          move();
          checkScore();
          collisionchecker();
      }
      repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent key){
            switch(key.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(Direction != 'R'){
                        Direction = 'L';
                    }
                    break;
                 case KeyEvent.VK_RIGHT:
                    if(Direction != 'L'){
                        Direction = 'R';
                    }
                    break;
                    case KeyEvent.VK_UP:
                    if(Direction != 'D'){
                        Direction = 'U';
                    }
                    break;
                    case KeyEvent.VK_DOWN:
                    if(Direction != 'U'){
                        Direction = 'D';
                    }
                    break;
                 
            }
        }
    }
    
}
