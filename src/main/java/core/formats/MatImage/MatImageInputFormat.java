package core.formats.MatImage;

import core.formats.ImageInputFormat;
import core.recordReaders.MatImage.MatImageRecordReader;
import core.writables.MatImageWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * Created by Epanchee on 24.02.15.
 */
public class MatImageInputFormat extends ImageInputFormat<NullWritable, MatImageWritable> {
    @Override
    public RecordReader createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new MatImageRecordReader();
    }
}
