package core.writables;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Epanchee on 24.02.15.
 */
public class MatImageWritable extends ImageWritable<Mat> {
    public MatImageWritable() {
        this.im = new Mat();
        this.format = "undef";
        this.fileName = "unnamed";
    }

    public MatImageWritable(Mat mat) {
        this();
        this.im = mat;
    }

    public MatImageWritable(Mat mat, String fileName, String format) {
        this(mat);
        setFileName(fileName);
        setFormat(format.toLowerCase());
    }

    public void write(DataOutput out) throws IOException {
        super.write(out);
        // Write image
        byte[] byteArray = new byte[(int) (im.total() * im.channels())];
        im.get(0, 0, byteArray);
        // Write Mat array size
        out.writeInt((int) (im.total() * im.channels()));
        // Write Mat image width
        out.writeInt(im.width());
        // Write Mat image height
        out.writeInt(im.height());
        // Write image bytes
        out.write(byteArray);
    }

    public void readFields(DataInput in) throws IOException {
        super.readFields(in);
        // Read Mat array size
        int arraySize = in.readInt();
        // Read Mat image width
        int mWidth = in.readInt();
        // Read Mat image height
        int mHeight = in.readInt();
        // Read image byte array
        byte[] bArray = new byte[(int) (arraySize)];
        in.readFully(bArray);
        this.im = new Mat(mHeight, mWidth, CvType.CV_8UC3);
        // Read image from byte array
        this.im.put(0, 0, bArray);
    }
}
