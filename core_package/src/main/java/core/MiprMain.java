package main.java.core;

import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * Created by Epanchee on 28.04.15.
 */
public class MiprMain {

    public static final String workspace = "";

    public static URI getOpenCVUri(){
        return new Path(workspace + "/usr/local/share/OpenCV/java/libopencv_java2411.so").toUri();
    }

    public static long getMaxSplitSize(){
        return 134217728;
    }
}
