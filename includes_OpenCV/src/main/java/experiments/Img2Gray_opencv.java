package experiments;

import core.MiprMain;
import opencv.MatImageInputFormat;
import opencv.MatImageOutputFormat;
import opencv.MatImageWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
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
        DistributedCache.addCacheFile(MiprMain.getOpenCVUri(), conf);
        Job job = new Job(conf);
        job.setJarByClass(Img2Gray_opencv.class);
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
            Path[] myCacheFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
            System.load(myCacheFiles[0].toUri().getPath());
        }

        @Override
        protected void map(NullWritable key, MatImageWritable value, Context context) throws IOException, InterruptedException {
            Mat image = value.getImage();
            Mat result = new Mat(image.height(), image.width(), CvType.CV_8UC3);

            if (image.type() == CvType.CV_8UC3) {
                Imgproc.cvtColor(image, result, Imgproc.COLOR_RGB2GRAY);
            } else result = image;

            context.write(NullWritable.get(), new MatImageWritable(result, value.getFileName(), value.getFormat()));
        }
    }
}
