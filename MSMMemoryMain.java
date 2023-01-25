import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MSMMemoryMain
{
    static Robot r;

    public static void main(String[] args)
    {
        boolean test = false;
        {
            try {
                r = new Robot();
                Thread.sleep(5000);
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        BufferedImage referenceImg = null;
        BufferedImage image = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

        try {
            image = resizeImage(image, 1920, 1080);

            referenceImg = ImageIO.read(new File("C:\\Users\\someo\\IdeaProjects\\MSM macro\\src\\msm_menu_background.png"));
            referenceImg = resizeImage(referenceImg,1920, 1080);
        } catch (IOException e) {}

        Point pStart, pEnd;
        int cNum;

        pStart = getFirstChange(referenceImg, image);
        pEnd   = getFirstChange(referenceImg, image, pStart);

        if(pStart != null)
        {

            System.out.println("Start pixel coords: x: " + pStart.x + " y: " + pStart.y);
            System.out.println("End pixel coords: x: " + pEnd.x + " y: " + pEnd.y);
        }
        else
            System.out.println("p is null");

    }

    /* Searches row by row from left to right
     * searching for the first instance of
     * the inputted color
     *
     * Param: img1, img2 - 2 images of equal dimensions being compared
     * Return: Coordinates of pixel (null if not found)
     */
    private static Point getFirstChange(BufferedImage img1, BufferedImage img2)
    {
        Dimension d = new Dimension();
        d.setSize(img1.getWidth(), img1.getHeight());

        // Searching left to right, top to bottom for the first instance of Color c
        // Special beginning and ending values to ignore the border and speed up the pixel search
        for(int row = 172; row < d.height - 70; row++)
        {
            for(int col = 110; col < d.width - 170; col++)
            {
                //System.out.println("Col: " + col);
                if(img1.getRGB(col, row) != img2.getRGB(col, row))
                {
                    return new Point(col, row);
                }
            }
        }

        return null;
    }

    private static Point getFirstChange(BufferedImage img1, BufferedImage img2, Point start)
    {
        Dimension d = new Dimension();
        d.setSize(img1.getWidth(), img1.getHeight());

        int row, col;
        // Searching left to right, top to bottom for the first instance of Color c
        // Special beginning and ending values to ignore the border and speed up the pixel search
        for(row = start.y; row < d.height - 70; row++)
        {
            if(row == start.y)
                col = start.x + 1;
            else
                col = 110;
            for (; col < d.width - 170; col++)
            {
                if (img1.getRGB(col, row) != img2.getRGB(col, row))
                {
                    return new Point(col, row);
                }
            }
        }

        return null;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}