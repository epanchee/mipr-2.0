package core;

import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * Created by Epanchee on 28.04.15.
 */
public class MiprMain {

    public static final String workspace = "/home/u1220/mipr-2.0";

    public static URI getOpenCVUri(){
        return new Path(workspace + "/lib/libopencv_java2411.so").toUri();
    }

}
