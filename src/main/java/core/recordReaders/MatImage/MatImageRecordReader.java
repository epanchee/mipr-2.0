package core.recordReaders.MatImage;

import core.recordReaders.ImageRecordReader;
import core.writables.MatImageWritable;
import org.apache.hadoop.fs.FSDataInputStream;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Epanchee on 24.02.15.
 */
public class MatImageRecordReader extends ImageRecordReader<MatImageWritable> {
    @Override
    protected MatImageWritable readImage(FSDataInputStream fileStream) throws IOException {
        Mat out;
        byte[] data;
        BufferedImage in = ImageIO.read(fileStream);

        out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
        data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
        int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
        for (int i = 0; i < dataBuff.length; i++) {
            data[i * 3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
            data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
            data[i * 3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
        }
        out.put(0, 0, data);

        return new MatImageWritable(out);
    }
}
