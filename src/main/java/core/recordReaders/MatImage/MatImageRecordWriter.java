package core.recordReaders.MatImage;

import core.recordReaders.ImageRecordWriter;
import core.writables.MatImageWritable;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Epanchee on 24.02.15.
 */
public class MatImageRecordWriter extends ImageRecordWriter<MatImageWritable> {
    public MatImageRecordWriter(TaskAttemptContext taskAttemptContext) {
        super(taskAttemptContext);
    }

    @Override
    protected void writeImage(MatImageWritable image, FSDataOutputStream imageFile) throws IOException {
        byte[] buf = new byte[(int) (image.getImage().channels() * image.getImage().total())];
        image.getImage().get(0, 0, buf);
        ImageIO.write(ImageIO.read(new ByteArrayInputStream(buf)), image.getFormat(), imageFile);
    }
}
