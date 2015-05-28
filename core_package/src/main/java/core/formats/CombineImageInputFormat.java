package core.formats;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;

/**
 * Created by Epanchee on 26.05.2015.
 */
public abstract class CombineImageInputFormat<K,V> extends CombineFileInputFormat {
    @Override
    protected void setMaxSplitSize(long maxSplitSize) {
        super.setMaxSplitSize(134217728);
    }

    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }
}
