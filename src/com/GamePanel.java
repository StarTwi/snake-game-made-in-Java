package com.glim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    public static final int scwdh = 600;
    public static final int scht = 600;
    public static final int unitsz = 25;
    public static final int gmut = (scwdh * scht) / unitsz;
    //suggest something for this one , I am confused Imao
    public static final int delay = 100;
    final int x[] = new int[gmut]; 
    final int y[] = new int[gmut];
    int bodypart = 3;
    int eatenfruit = 0;
    int posxfruit;
    int posyfruit;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(scwdh, scht));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();

    }

    public void StartGame() {
        newfruit();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        for (int i = 0; i < scht / unitsz; i++) {
            g.drawLine(i * unitsz, 0, i * unitsz, scht);
            g.drawLine(0, i * unitsz, scwdh, i * unitsz);
        }
        g.setColor(Color.PINK);
        g.fillOval(posxfruit, posyfruit, unitsz, unitsz);

        for (int i = 0; i < bodypart; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], unitsz, unitsz);
            } else {
                g.setColor(new Color(200, 162, 200));
            }
        }

    }

    public void newfruit() {
        posxfruit = random.nextInt((int) scwdh / unitsz) * unitsz;
        posyfruit = random.nextInt((int) scht / unitsz) * unitsz;
    }

    public void move() {
        for (int i = bodypart; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
            switch (direction) {
                case 'U':
                    //y[0] = y[0] - unitsz;
                    y[0] -= unitsz; 
                    break;
                case 'D':
                    //y[0] = y[0] + unitsz;
                    y[0] += unitsz;
                    break;
                case 'R':
                    //x[0] = x[0] + unitsz;
                    x[0] += unitsz; 
                    break;
                case 'L':
                    //x[0] = x[0] - unitsz;
                    x[0] -= unitsz; 
                    break;
            }

        }

    }

    public void checkfruit() {
        if((x[0]==posxfruit)&&(y[0]==posyfruit)){
            bodypart++;
            eatenfruit++;
            newfruit();
        }
    }

    public void onCollision() {


        //checks if head collides with the body
        for (int i = bodypart; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            //checks if head touches left border
            if (x[0] < 0) {
                running = false;
            }
            //checks if head touches right border
            if (x[0] > scwdh) {
                running = false;
            }
            //checks if head touches top border
            if(y[0]<0){
                running=false;
            }
            //checks if head touches bottom border
            if(y[0]>scht){
                running=false;
            }
            if(!running){
                timer.stop();
            }
        }
    }
        public void overgameImao (Graphics g){

        }
        @Override
        public void actionPerformed (ActionEvent e){
            if (running == true) {
                move();
                checkfruit();
                onCollision();
            }
            else return False; 
            // we don't want to repaint if the game isn't running
            repaint();

        }
        public class MyKeyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }

            }
        }
    }

