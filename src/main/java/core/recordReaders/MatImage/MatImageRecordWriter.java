package core.recordReaders.MatImage;

import core.recordReaders.ImageRecordWriter;
import core.writables.MatImageWritable;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by Epanchee on 24.02.15.
 */
public class MatImageRecordWriter extends ImageRecordWriter<MatImageWritable> {
    public MatImageRecordWriter(TaskAttemptContext taskAttemptContext) {
        super(taskAttemptContext);
    }

    @Override
    protected void writeImage(MatImageWritable image, FSDataOutputStream imageFile) throws Exception {
        BufferedImage out;
        Mat in = image.getImage();
        byte[] data = new byte[in.width() * in.height() * (int)in.elemSize()];
        int type;
        in.get(0, 0, data);

        if(in.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(in.width(), in.height(), type);

        out.getRaster().setDataElements(0, 0, in.width(), in.height(), data);

        ImageIO.write(out, image.getFormat(), imageFile);
    }
}
