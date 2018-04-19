import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener,ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer time;
    private int delay=5;
    private int playerX = 310;
    private int level=1;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        //draw borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        //draw map
        map.draw((Graphics2D) g);
        //draw scores
        g.setColor(Color.CYAN);
        g.setFont(new Font("ariel",Font.BOLD,25));
        g.drawString("Scores:"+score,550,30);
        if(ballPosY>570){
            play=false;
            ballYDir=0;
            ballXDir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("ariel",Font.BOLD,50));
            g.drawString("Game Over!",220,200);
            g.drawString("Press Enter to restart",100,300);

        }
        if(totalBricks<1){
            play=false;
            ballYDir=0;
            ballXDir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("ariel",Font.BOLD,50));
            g.drawString("You reached level"+level,150,200);
            g.drawString("Press N to next level",100,300);
        }
        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        ballPosX += ballXDir;
        ballPosY += ballYDir;

        if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
            ballYDir = -ballYDir;
        }
        A:
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if (map.map[i][j] > 0) {
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickWidth = map.brickWidth;
                    int brichHeight = map.brickHeight;
                    Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brichHeight);
                    Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                    Rectangle brickRect = rectangle;

                    if (ballRect.intersects(brickRect)) {
                        map.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 5;
                        if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                            ballXDir = -ballXDir;
                        } else {
                            ballYDir = -ballYDir;
                        }
                        break A;
                    }
                }

            }

        }
        if (ballPosX <= 0) {
            ballXDir = -ballXDir;
        }
        if (ballPosY <= 0) {
            ballYDir = -ballYDir;
        }
        if (ballPosX >= 670) {
            ballXDir = -ballXDir;
        }
        if (ballPosY >= 670) {
            ballYDir = -ballYDir;
        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_N){
            if(!play){
                play=true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                totalBricks=21;
                map=new MapGenerator(3,7);
                repaint();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                delay=3;
                level=1;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                score=0;
                totalBricks=21;
                map=new MapGenerator(3,7);
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 575) {
                playerX = 575;
            } else {
                moveRight();
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
                repaint();
            }
        }
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
