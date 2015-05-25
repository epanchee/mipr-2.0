package experiments;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Epanchee on 26.02.15.
 */
public class Img2Gray_single {

    public static class Img2GrayClass {

        public void run(){

/*//            Mat image = Highgui.imread(getClass().getResource("/best.jpg").getPath().substring(1));
//            System.out.println(getClass().getResource("/best.jpg").getPath().substring(1));
//            System.out.println(image.cols());

            Mat image = Highgui.imread("/root/mipr/best.jpg");

            Mat result = new Mat(image.height(), image.width(), CvType.CV_8UC3);

            if (image != null) {
                Imgproc.cvtColor(image, result, Imgproc.COLOR_RGB2GRAY);
            }

            Highgui.imwrite("/root/grayImg/_gray.jpg", result);
//            Highgui.imwrite("E:\\GitRepos\\mipr-2.0\\src\\main\\resources\\_gray.jpg", result);*/

/*            try {
                File input = new File("/root/mipr/best.jpg");
                BufferedImage image = ImageIO.read(input);

                byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
                mat.put(0, 0, data);

                Mat mat1 = new Mat(image.getHeight(),image.getWidth(), CvType.CV_8UC1);
                Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

                byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
                mat1.get(0, 0, data1);
                BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
                image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

                File ouptut = new File("/root/grayImg/_gray.jpg");
                ImageIO.write(image1, "jpg", ouptut);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }*/

            Mat mat = Highgui.imread("/root/mipr/best.jpg");

            Mat mat1 = new Mat(mat.height(),mat.width(), CvType.CV_8UC1);
            Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

            Highgui.imwrite("/root/grayImg/_gray.jpg", mat1);

        }

    }

    public static void main(String[] args) {
        System.out.println("OpenCV started");
        System.out.println(System.getProperty("java.library.path"));
        System.load("/usr/local/share/OpenCV/java/libopencv_java2411.so");
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.load("C:\\Program Files (x86)\\opencv\\build\\java\\x64\\opencv_java2410.dll");
        new Img2GrayClass().run();
        System.out.println("OpenCV finished");
    }
}
