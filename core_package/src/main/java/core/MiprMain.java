package core;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Epanchee on 28.04.15.
 */
public class MiprMain {

    public static final String workspace = "/root";

    public static URI getOpenCVUri(){
        return new Path(workspace + "/libopencv_java2411.so").toUri();
    }

    public static long getMaxSplitSize(){
        return 134217728;
    }

    public static Job getOpenCVJobTemplate() throws IOException {
        Configuration conf = new Configuration();
        return getOpenCVJobTemplate(conf);
    }

    public static Job getOpenCVJobTemplate(Configuration conf) throws IOException {
        DistributedCache.addCacheFile(MiprMain.getOpenCVUri(), conf);
        Job job = new Job(conf);
        job.setNumReduceTasks(0);

        return job;
    }
}
