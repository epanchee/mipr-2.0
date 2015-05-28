package main.java.core.recordReaders.BufferedImage;

import main.java.core.recordReaders.ImageRecordWriter;
import main.java.core.writables.BufferedImageWritable;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Created by Epanchee on 22.02.15.
 */
public class BufferedImageRecordWriter extends ImageRecordWriter<BufferedImageWritable> {
    public BufferedImageRecordWriter(TaskAttemptContext taskAttemptContext) {
        super(taskAttemptContext);
    }

    @Override
    protected void writeImage(BufferedImageWritable image, FSDataOutputStream imageFileStream) throws IOException {
        ImageIO.write(image.getImage(), image.getFormat(), imageFileStream);
    }
}
