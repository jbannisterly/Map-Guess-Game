package com.example.jb2570.mapguessgame;

import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class URLReader {
    public static int count = 0;
    public static String GetStringData(String urlData){
        StringBuilder builder = new StringBuilder();
        try{
            URL url = new URL(urlData);
            URLConnection con = url.openConnection();
            InputStream data = con.getInputStream();
            count++;
            int nextChar;
            while((nextChar = data.read()) != -1) {
                builder.append((char)nextChar);
            }
        }
        catch(Exception e){
            Log.e("EXCEPTION (probably network)", e.getMessage());
            return null;
        }
        return builder.toString();
    }

    public static byte[] GetByteData(String urlData){
        List<Byte> builder = new ArrayList<>();
        try{
            URL url = new URL(urlData);
            URLConnection con = url.openConnection();
            InputStream data = con.getInputStream();
            count++;
            int nextChar;
            while((nextChar = data.read()) != -1) {
                builder.add((byte)nextChar);
            }
        }
        catch(Exception e){
            return null;
        }
        byte[] data = new byte[builder.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = builder.get(i);
        }
        return data;
    }


}
