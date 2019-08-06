package com.lzy.imagepicker.server;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;

import fi.iki.elonen.NanoHTTPD;

/**
 * 本地 服务
 */
public class LocalServer extends NanoHTTPD {
    String TAG="LocalServer";
    private NanoHTTPD server;
    private static final int DEFAULT_PORT = 8686;
    // public String filesDir = null;
    private FileInputStream fis;
    public LocalServer() {
        super(DEFAULT_PORT);
    }


    /**
     * 启动服务
     */
    public void execute() {
        try {
            server = LocalServer.class.newInstance();
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);
        } catch (IOException ioe) {
            Log.e(TAG, "execute: " +ioe);
        } catch (Exception e) {
            Log.e(TAG, "execute: "+e );
        }

    }


    /**
     * 关闭服务
     */
    public void finish() {
        if(server != null){
            server.stop();
            server = null;
        }
    }


    @Override
    public Response serve(IHTTPSession session) {
        String url = String.valueOf(session.getUri());

        File file = new File(url);

        Response response = newFixedLengthResponse(Response.Status.NOT_FOUND, "text/html", "文件不存在：" + url);
        if(file.exists()){

            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/html", "文件不存在：" + url);
            }
            // ts文件
            //通过文件类型获取 mimetype
            String mimeType= URLConnection.guessContentTypeFromName(url);
            try {
                response = newFixedLengthResponse(Response.Status.OK, mimeType, fis, fis.available());
            } catch (IOException e) {

            }

        }

        return response;
    }


    public String createLocalHttpUrl(String filePath) throws UnsupportedEncodingException {
        Uri uri = Uri.parse(filePath);
        String scheme = uri.getScheme();
        if (null != scheme) {
            filePath = uri.toString();
        } else {
            filePath = uri.getPath();
        }
        if (filePath != null){
            //  filesDir = filePath.substring(0, filePath.lastIndexOf("/") + 1);

            return  URLEncoder.encode(String.format("http://192.168.1.112:%d%s", DEFAULT_PORT, filePath),"utf-8");
        }
        return null;
    }
}
