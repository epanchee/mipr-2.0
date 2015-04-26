package core.formats.MBFImage;

import core.formats.ImageInputFormat;
import core.recordReaders.MBFImage.MBFImageRecordReader;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;

/**
 * Created by Epanchee on 26.04.15.
 */
public class MBFImageInputFormat extends ImageInputFormat {
    @Override
    public RecordReader createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new MBFImageRecordReader();
    }
}
