package core.formats.BufferedImage;

import main.java.core.formats.ImageInputFormat;
import main.java.core.recordReaders.BufferedImage.BufferedImageRecordReader;
import main.java.core.writables.BufferedImageWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * Created by Epanchee on 19.02.15.
 */
public class BufferedImageInputFormat extends ImageInputFormat<NullWritable, BufferedImageWritable> {
    @Override
    public RecordReader createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new BufferedImageRecordReader();
    }
}
