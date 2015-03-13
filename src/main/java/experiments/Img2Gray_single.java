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

//            Mat image = Highgui.imread(getClass().getResource("/best.jpg").getPath().substring(1));
//            System.out.println(getClass().getResource("/best.jpg").getPath().substring(1));
//            System.out.println(image.cols());

            Mat image = Highgui.imread("/root/mipr/hadoopimg/6-house-in-green-field.jpg");

            Mat result = new Mat(image.height(), image.width(), CvType.CV_8UC3);

            if (image != null) {
                Imgproc.cvtColor(image, result, Imgproc.COLOR_RGB2GRAY);
            }

            Highgui.imwrite("/root/mipr/out_images/_gray.jpg", result);
//            Highgui.imwrite("E:\\GitRepos\\mipr-2.0\\src\\main\\resources\\_gray.jpg", result);

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
