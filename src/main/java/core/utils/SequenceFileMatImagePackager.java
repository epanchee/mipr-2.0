package core.utils;

import core.MiprMain;
import core.formats.MatImage.CombineMatImageInputFormat;
import core.writables.MatImageWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * Created by Epanchee on 26.05.2015.
 */
public class SequenceFileMatImagePackager {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String input = args[0];
        String output = args[1];

        Configuration conf = new Configuration();
        DistributedCache.addCacheFile(MiprMain.getOpenCVUri(), conf);
        conf.set("mapreduce.map.memory.mb", "4096");
        conf.set("mapreduce.reduce.memory.mb", "4096");
        Job job = new Job(conf);
        job.setJarByClass(SequenceFileMatImagePackager.class);
        job.setMapperClass(SequenceFileMatImagePackagerMapper.class);
        job.setNumReduceTasks(3); // count of resulted seq files
        job.setInputFormatClass(CombineMatImageInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class); // that is all
        Path outputPath = new Path(output);
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, outputPath);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(MatImageWritable.class);
        outputPath.getFileSystem(conf).delete(outputPath, true); // delete folder if exists

        job.waitForCompletion(true);

    }

    static class SequenceFileMatImagePackagerMapper extends Mapper<NullWritable, MatImageWritable, NullWritable, MatImageWritable>{
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            Path[] myCacheFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
            System.load(myCacheFiles[0].toUri().getPath());
        }
    }
}
