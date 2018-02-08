import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class test extends JPanel{

    private BufferedImage image;
    private int width;
    private int height;
    private ArrayList<int[]> verticalLines = new ArrayList<>();
    private ArrayList<int[]> horizontalLines = new ArrayList<>();
    private int scale = 9;
    private int block = 40;
    private int tolerance = 10;


    private test() {
        try {
            File input = new File("monalisa.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            //vertical
            for(int i = 0; i < width; i ++){
                int j = 0;
                while (j < height) {
                    Color c = new Color(image.getRGB(i, j));
                    Color d = c;
                    int L = 0;
                    while (abs(d.getRed() - c.getRed()) <= tolerance &&
                            abs(d.getBlue() - c.getBlue()) <= tolerance &&
                            abs(d.getGreen() - c.getBlue()) <= tolerance &&
                            j < height - 1) {
                        L ++;
                        j ++;
                        d = new Color(image.getRGB(i, j));
                    }
                    if (L != 0) {
                        int line[] = {i, j - L, L, round((255 - c.getRed()) / block)};
                        verticalLines.add(line);
                    }
                    j ++;
                }
            }
            //horizontal
            for(int i = 0; i < height; i++){
                int j = 0;
                while (j < width) {
                    Color c = new Color(image.getRGB(j, i));
                    Color d = c;
                    int L = 0;
                    while (abs(d.getRed() - c.getRed()) <= tolerance &&
                            abs(d.getBlue() - c.getBlue()) <= tolerance &&
                            abs(d.getGreen() - c.getBlue()) <= tolerance &&
                            j < width - 1) {
                        L ++;
                        j ++;
                        d = new Color (image.getRGB(j, i));
                    }
                    if (L != 0) {
                        int line[] = {j - L, i, L, round((255 - c.getRed()) / block)};
                        horizontalLines.add(line);
                    }
                    j ++;
                }
            }
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    static public void main(String args[]) throws Exception {
        //System.out.println(new Color(0, 0, 0));
        test obj = new test();

        JFrame frame = new JFrame();

        frame.getContentPane().add(new test());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(920, 920);
        frame.setVisible(true);

    }



    public void paint(Graphics g) {
        Image img = createImageWithText();
        g.drawImage(img, 0, 0, this);
    }

    private Image createImageWithText(){
        BufferedImage bufferedImage = new BufferedImage(900, 900, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, 900, 900);

        g.setColor(Color.black);
        for (int[] v : verticalLines) {
            for (int j = 0; j < v[3]; j++) {
                g.drawLine(v[0] * scale - round(j * scale / (v[3] + 1)), v[1] * scale, v[0] * scale - round(j * scale / (v[3] + 1)), (v[1] + v[2]) * scale);
            }

            //g.drawLine(v[0]*scale, v[1]*scale, v[0]*scale, (v[1] + v[2])*scale);
        }
        g.setColor(Color.blue);
        for (int[] v : horizontalLines) {
            for (int j = 0; j < v[3]; j++) {
                g.drawLine(v[0] * scale, v[1] * scale - round((j - 1) * scale / (v[3])), (v[0] + v[2]) * scale, v[1] * scale - round((j - 1) * scale / (v[3])));
            }
            //g.drawLine(v[0]*scale, v[1] * scale, (v[0]+v[2])*scale, v[1]*scale);
        }

        return bufferedImage;
    }
}
