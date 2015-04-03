package experiments;

import core.formats.MatImage.MatImageInputFormat;
import core.formats.MatImage.MatImageOutputFormat;
import core.writables.MatImageWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

/**
 * Created by Epanchee on 24.02.15.
 */
public class Img2Gray_opencv {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String input = args[0];
        String output = args[1];

        Configuration conf = new Configuration();
        Job job = new Job(conf);
        job.setJarByClass(Img2Gray.class);
        job.setMapperClass(Img2Gray_opencvMapper.class);
        job.setNumReduceTasks(0);
        job.setInputFormatClass(MatImageInputFormat.class);
        job.setOutputFormatClass(MatImageOutputFormat.class);
        Path outputPath = new Path(output);
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, outputPath);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(MatImageWritable.class);
        outputPath.getFileSystem(conf).delete(outputPath, true); // delete folder if exists

        job.waitForCompletion(true);

    }

    public static class Img2Gray_opencvMapper extends Mapper<NullWritable, MatImageWritable, NullWritable, MatImageWritable>{

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            System.load("/usr/local/share/OpenCV/java/libopencv_java2411.so");
//            System.load("C:\\Program Files (x86)\\opencv\\build\\java\\x64\\opencv_java2410.dll");
//            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }

        @Override
        protected void map(NullWritable key, MatImageWritable value, Context context) throws IOException, InterruptedException {
            Mat image = value.getImage();
            Mat result = new Mat(image.height(), image.width(), CvType.CV_8UC3);

            if (image != null) {
                Imgproc.cvtColor(image, result, Imgproc.COLOR_RGB2GRAY);
//                image.convertTo(result, CvType.CV_8UC1);
            }

            context.write(NullWritable.get(), new MatImageWritable(result, value.getFileName(), value.getFormat()));
        }
    }
}
