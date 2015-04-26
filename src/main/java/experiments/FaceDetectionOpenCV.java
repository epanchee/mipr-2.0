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
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOException;

/**
 * Created by Epanchee on 09.04.15.
 */
public class FaceDetectionOpenCV {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String input = args[0];
        String output = args[1];

        Configuration conf = new Configuration();
        Job job = new Job(conf);
        job.setJarByClass(FaceDetectionOpenCV.class);
        job.setMapperClass(FaceDetectorMapper.class);
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

    public static class FaceDetectorMapper extends Mapper<NullWritable, MatImageWritable, NullWritable, MatImageWritable>{

        @Override
        protected void map(NullWritable key, MatImageWritable value, Context context) throws IOException, InterruptedException {
            Mat image = value.getImage();

            if (image != null) {
                Mat result_image = new Mat(image.height(), image.width(), CvType.CV_8UC3);
                CascadeClassifier faceDetector = new CascadeClassifier("lbpcascade_frontalface.xml");
                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(image, faceDetections);

                for (Rect rect : faceDetections.toArray()) {
                    Core.rectangle(result_image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 3);
                }

                MatImageWritable matiw = new MatImageWritable(result_image);

                matiw.setFormat("jpg");
                matiw.setFileName(value.getFileName() + "_result");
                context.write(NullWritable.get(), matiw);
            }
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            System.load("/usr/local/share/OpenCV/java/libopencv_java2411.so");
        }
    }
}
