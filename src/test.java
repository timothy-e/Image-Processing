import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test extends JPanel{

    BufferedImage image;
    int width;
    int height;
    ArrayList<int[]> verticalLines = new ArrayList<int[]>();
    ArrayList<int[]> horizontalLines = new ArrayList<int[]>();
    Color black = new Color(0, 0, 0);
    int scale = 9;


    public test() {
        try {
            File input = new File("bw.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            //vertical
            for(int i = 0; i < width; i ++){
                int j = 0;
                while (j < height) {
                    Color c = new Color(image.getRGB(i, j));
                    int L = 0;
                    while (c.getRed() <= 5 && c.getBlue() <= 5 && c.getGreen() <= 5) {
                        L ++;
                        j ++;
                        c = new Color(image.getRGB(i, j));
                    }
                    if (L != 0) {
                        int line[] = {i, j - L, L};
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
                    int L = 0;
                    while (c.getRed() < 5 && c.getBlue() <= 5 && c.getGreen() <= 5) {
                        L ++;
                        j ++;
                        c = new Color (image.getRGB(j, i));
                    }
                    if (L != 0) {
                        int line[] = {j - L, i, L};
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(490, 490);
        frame.setVisible(true);

    }



    public void paint(Graphics g) {
        Image img = createImageWithText();
        g.drawImage(img, 20, 20, this);
    }

    private Image createImageWithText(){
        BufferedImage bufferedImage = new BufferedImage(450, 450, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        for(int i = 0; i < verticalLines.size(); i++){
            int[] v = verticalLines.get(i);
            g.drawLine(v[0]*scale, v[1]*scale, v[0]*scale, (v[1] + v[2])*scale);
        }
        for(int i = 0; i < horizontalLines.size(); i ++){
            int[] v = horizontalLines.get(i);
            g.drawLine(v[0]*scale, v[1] * scale, (v[0]+v[2])*scale, v[1]*scale);
        }

        return bufferedImage;
    }
}
