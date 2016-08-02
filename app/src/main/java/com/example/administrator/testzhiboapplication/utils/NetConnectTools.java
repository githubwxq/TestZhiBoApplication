package com.example.administrator.testzhiboapplication.utils;


import android.support.v4.util.ArrayMap;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lijia on 2016/6/29 0029.
 * <p/>
 * xutils二次封裝
 */
public class NetConnectTools {
    private static NetConnectTools network;
    private Callback.Cancelable cancelable;
    public static final int PUT = 0;
    public static final int DELETE = 1;
    public static final int MOVE = 2;
    public static final int POST = 3;

    public static String urlHost = "api.exiaoxin.com";
    public static String uploadHost = "api.juziwl.cn";

    private NetConnectTools() {
    }

    public static NetConnectTools getInstance() {
        if (network == null) {
            synchronized (NetConnectTools.class) {
                if (network == null) {
                    network = new NetConnectTools();
                }
            }
        }
        return network;
    }

    //Get请求调用
    public void getData(String uri, ArrayMap<String, String> header, final CallBackListen listen) {
        RequestParams params = new RequestParams(uri);
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                params.addHeader(entry.getKey(), entry.getValue());
            }

        }
        params.addHeader("host", urlHost);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listen.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listen.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                listen.onFinished();
            }
        });
    }
    //Post请求调用
    public void postData(String uri, ArrayMap<String, String> header, ArrayMap<String, String> body, String jsonObject, final CallBackListen listen) {
        RequestParams params = new RequestParams(uri);
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                params.addHeader(entry.getKey(), entry.getValue());
            }

        }
        params.addHeader("host", urlHost);
        if (body != null) {
            for (Map.Entry<String, String> entry : body.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }

        params.setAsJsonContent(true);
        params.setBodyContent(jsonObject);
        params.setCharset("UTF-8");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listen.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listen.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                listen.onFinished();
            }
        });
    }

    public void upFile(String uri, ArrayMap<String, File> map, boolean flag, final CallBackListen listen) {
        RequestParams params = new RequestParams(uri);
        params.addHeader("host", uploadHost);
        if (flag) {
            params.addBodyParameter("myFiletype", "png");
        } else {
            params.addBodyParameter("myFiletype", "amr");
        }
        if (map != null) {
            for (Map.Entry<String, File> entry : map.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }

        }
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                listen.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listen.onError(ex, isOnCallback);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                listen.onFinished();

            }
        });

    }

    //Request请求调用
    public void requestData(String uri, ArrayMap<String, String> map, String jsonObject, int method, final CallBackListen listen) {
        HttpMethod httpMethod = null;
        RequestParams params = new RequestParams(uri);
        params.addHeader("host", urlHost);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        params.setAsJsonContent(true);
        params.setBodyContent(jsonObject);
        if (method == 0) {
            httpMethod = HttpMethod.PUT;
        } else if (method == 1) {
            httpMethod = HttpMethod.DELETE;
        } else if (method == 2) {
            httpMethod = HttpMethod.MOVE;
        } else if (method == 3) {
            httpMethod = HttpMethod.POST;
        }
        x.http().request(httpMethod, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listen.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listen.onError(ex, isOnCallback);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                listen.onFinished();

            }
        });
    }

    //文件下载调用
    public Callback.Cancelable loadFile(String uri, boolean autoRename, String saveFilepath, final ProgressCallBackListen listen) {
        RequestParams params = new RequestParams(uri);
        try {
            URL url = new URL(uri);
            params.addHeader("host", url.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        params.setAutoResume(autoRename);
        params.setSaveFilePath(saveFilepath);
        cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                listen.onStarted();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                listen.onLoading(total, current, isDownloading);
            }

            @Override
            public void onSuccess(File result) {
                listen.onSuccess(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listen.onError(ex, isOnCallback);

            }

            @Override
            public void onCancelled(CancelledException cex) {
                listen.onCancelled(cex);

            }

            @Override
            public void onFinished() {

            }
        });

        return cancelable;
    }

    //多图片上传调用
    public void upLoadPicture(String uri, ArrayMap<String, String> map, final ArrayList<String> list, final CallBackListen listen) {
        RequestParams params = new RequestParams(uri);
        params.addHeader("host", urlHost);
        params.setMultipart(true);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        params.addBodyParameter("myFiletype", "png");
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(list.size() - 1 - i));
            if (file != null) {
                params.addBodyParameter("File" + i, file);
            }
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listen.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listen.onError(ex, isOnCallback);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                listen.onFinished();

            }
        });

    }

    public interface CallBackListen {
        void onSuccess(String result);

        void onError(Throwable ex, boolean isOnCallback);

        void onFinished();
    }

    public interface ProgressCallBackListen {

        void onError(Throwable ex, boolean isOnCallback);

        void onCancelled(Callback.CancelledException cex);

        void onStarted();

        void onLoading(long total, long current, boolean isDownloading);

        void onSuccess(File result);
    }

}
