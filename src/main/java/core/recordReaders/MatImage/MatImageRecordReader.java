package core.recordReaders.MatImage;

import core.recordReaders.ImageRecordReader;
import core.writables.MatImageWritable;
import org.apache.hadoop.fs.FSDataInputStream;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

/**
 * Created by Epanchee on 24.02.15.
 */
public class MatImageRecordReader extends ImageRecordReader<MatImageWritable> {
    @Override
    protected MatImageWritable readImage(FSDataInputStream fileStream) throws IOException {
        Mat mat;

        BufferedImage bi = ImageIO.read(fileStream);
        byte[] imageBytes = ((DataBufferByte) bi.getData().getDataBuffer()).getData();
        mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, imageBytes);

        return new MatImageWritable(mat);
    }
}
