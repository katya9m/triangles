import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.min;

class Render {

    public static void render(BufferedImage img, float xa, float ya, float xb, float yb, float xc, float yc, int color) {
//        img.setRGB(500, 300, new Color(255, 0, 200).getRGB());
        if((xa==xb&xb==xc)||(ya==yb&yb==yc)){
            System.out.println("Вырожденный треугольник");
        }else{
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    float u=((i-xa)*(yc-ya)-(xc-xa)*(j-ya))/((xb-xa)*(yc-ya)-(yb-ya)*(xc-xa));
                    float v=((xb-xa)*(j-ya)-(i-xa)*(yb-ya))/((xb-xa)*(yc-ya)-(yb-ya)*(xc-xa));
                    if(u+v<=1&&u>=0&&v>=0){img.setRGB(i,j,color);
                    }
                }
            }
        }
    }
}



public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 768;
    static final float x1 = 100;
    static final float y1 = 100;
    static final Color color1=new Color(0,0,0);
    static final Color color2=new Color(255, 100, 100);

    public static void draw(Graphics2D g) {
        //Создаем буффер в который рисуем кадр.
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        //Рисуем кадр.
        Render.render(img, x1, y1, x1, y1-15, x1+15, y1-30, color1.getRGB());
        Render.render(img, x1, y1, x1+15, y1-30, x1+15, y1-15, color2.getRGB());
        Render.render(img, x1, y1, x1+15, y1-15, x1+30, y1-15, color1.getRGB());
        Render.render(img, x1, y1, x1+30, y1-15, x1+15, y1, color2.getRGB());
        Render.render(img, x1, y1, x1+15, y1, x1+30, y1+15, color1.getRGB());
        Render.render(img, x1, y1, x1+30, y1+15, x1+15, y1+15, color2.getRGB());
        Render.render(img, x1, y1, x1+15, y1+15, x1+15, y1+30, color1.getRGB());
        Render.render(img, x1, y1, x1+15, y1+30, x1, y1+15, color2.getRGB());
        Render.render(img, x1, y1, x1, y1-15, x1-15, y1-30, color2.getRGB());
        Render.render(img, x1, y1, x1-15, y1-30, x1-15, y1-15, color1.getRGB());
        Render.render(img, x1, y1, x1-15, y1-15, x1-30, y1-15, color2.getRGB());
        Render.render(img, x1, y1, x1-30, y1-15, x1-15, y1, color1.getRGB());
        Render.render(img, x1, y1, x1-15, y1, x1-30, y1+15, color2.getRGB());
        Render.render(img, x1, y1, x1-30, y1+15, x1-15, y1+15, color1.getRGB());
        Render.render(img, x1, y1, x1-15, y1+15, x1-15, y1+30, color2.getRGB());
        Render.render(img, x1, y1, x1-15, y1+30, x1, y1+15, color1.getRGB());

        g.drawImage(img, 0, 0, null);
    }


    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        Main jf = new Main();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        //в бесконечном цикле рисуем новый кадр
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета  60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}