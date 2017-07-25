package net.bruce.zzim.push;

import android.os.Environment;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import net.bruce.zzim.common.app.Activity;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();

        String path = Environment.getExternalStorageDirectory() + File.separator + "sdg.gif";
        String url = "https://github.com/lingochamp/FileDownloader/raw/master/art/serial_tasks_demo.gif";
        String subPath = path.substring(path.lastIndexOf('/') + 1);
        FileDownloader.getImpl().create(url).setPath(path).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.i("yjn_test_filedown","pending");
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.i("yjn_test_filedown","progress"+" soFarBytes:"+soFarBytes+" totalBytes:"+totalBytes);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.i("yjn_test_filedown","completed");
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.i("yjn_test_filedown","paused");
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.i("yjn_test_filedown","error");
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.i("yjn_test_filedown","warn");
            }
        }).start();
    }

}
