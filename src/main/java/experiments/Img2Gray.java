package experiments;

import core.formats.BufferedImage.BufferedImageInputFormat;
import core.formats.BufferedImage.BufferedImageOutputFormat;
import core.writables.BufferedImageWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Epanchee on 17.02.15.
 */
public class Img2Gray {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        String input = args[0];
        String output = args[1];

        Configuration conf = new Configuration();
        Job job = new Job(conf);
        job.setJarByClass(Img2Gray.class);
        job.setMapperClass(Img2GrayMapper.class);
        job.setNumReduceTasks(0);
        job.setInputFormatClass(BufferedImageInputFormat.class);
        job.setOutputFormatClass(BufferedImageOutputFormat.class);
        Path outputPath = new Path(output);
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, outputPath);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(BufferedImageWritable.class);
        outputPath.getFileSystem(conf).delete(outputPath, true); // delete folder if exists

        job.waitForCompletion(true);
    }

    public static class Img2GrayMapper extends Mapper<NullWritable, BufferedImageWritable, NullWritable, BufferedImageWritable> {

        private BufferedImage grayImage;
        private Graphics g;

        @Override
        protected void map(NullWritable key, BufferedImageWritable value, Context context) throws IOException, InterruptedException {

            BufferedImage image = value.getImage();

            if (image != null) {
                grayImage = new BufferedImage(image.getWidth(), image.getHeight(),
                        BufferedImage.TYPE_BYTE_GRAY);
                g = grayImage.getGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
                BufferedImageWritable biw = new BufferedImageWritable(grayImage, value.getFileName(), value.getFormat());
                context.write(NullWritable.get(), biw);
            }

        }

    }
}
