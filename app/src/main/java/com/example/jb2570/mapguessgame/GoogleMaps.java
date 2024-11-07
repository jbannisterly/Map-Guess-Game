package com.example.jb2570.mapguessgame;

import android.util.Log;

public class GoogleMaps {
    private final static String PHOTO_INFO_PREFIX = "https://www.google.com/maps/rpc/photo/listentityphotos?authuser=0&hl=en&gl=uk&pb=!1e3!5m54!2m2!1i203!2i100!3m3!2i4!3sCAEIBAgFCAYgAQ!5b1!7m42!1m3!1e1!2b0!3e3!1m3!1e2!2b1!3e2!1m3!1e2!2b0!3e3!1m3!1e8!2b0!3e3!1m3!1e10!2b0!3e3!1m3!1e10!2b1!3e2!1m3!1e9!2b1!3e2!1m3!1e10!2b0!3e3!1m3!1e10!2b1!3e2!1m3!1e10!2b0!3e4!2b1!4b1!8m0!9b0!11m1!4b1!6m3!1sD93mZtfBOv6thbIPxdTt-QY!7e81!15i11021!9m2!2d";
    private final static String STREET_VIEW_PREFIX = "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=";
    private static String GetPhotoInfo(double longitude, double latitude, double range){
        return URLReader.GetStringData(PHOTO_INFO_PREFIX + longitude + "!3d" + latitude + "!10d" + range);
    }

    private static String GetPanoID(String infoJSON){
        int position = infoJSON.indexOf("panoid\\u003d");
        if(position == -1){
            return null;
        }
        position += 12;
        char nextChar;
        StringBuilder builder = new StringBuilder();
        while((nextChar = infoJSON.charAt(position++)) != '\\'){
            builder.append(nextChar);
        }
        return builder.toString();
    }

    private static byte[] GetStreetViewData(String panoID, int width, int height, int yaw, int pitch, int fov){
        String url = GetStreetViewURL(panoID, width, height, yaw, pitch, fov);
        return URLReader.GetByteData(url);
    }

    private static String GetStreetViewURL(String panoID, int width, int height, int yaw, int pitch, int fov){
        return STREET_VIEW_PREFIX + panoID + "&cb_client=maps_sv.tactile.gps&w=" + width + "&h=" + height + "&yaw=" + yaw + "&pitch=" + pitch + "&thumbfov=" + fov;
    }

    private static String GetClosestPhotoInfo(double longitude, double latitude){
        String photoInfo = null;
        String panoID = null;
        double range = 50000;

        photoInfo = GetPhotoInfo(longitude, latitude, range);
        panoID = GetPanoID(photoInfo);
        if(panoID != null) {
            while (panoID != null) {
                range /= 2;
                photoInfo = GetPhotoInfo(longitude, latitude, range);
                panoID = GetPanoID(photoInfo);
            }
            range *= 2;
            photoInfo = GetPhotoInfo(longitude, latitude, range);
            Log.d("Successful Range", String.valueOf(range));
            return photoInfo;
        }else{
            return null;
        }
    }

    private static double GetLongitude(String info){
        int offset;
        int end;
        offset = info.indexOf("panoid");
        offset += info.substring(offset).indexOf("[[");
        offset += info.substring(offset).indexOf(",") + 1;
        end = info.substring(offset).indexOf(",") + offset;
        Log.d("qw", info.substring(offset, end));
        return Double.parseDouble(info.substring(offset, end));
    }

    private static double GetLatitude(String info){
        int offset;
        int end;
        offset = info.indexOf("panoid");
        offset += info.substring(offset).indexOf("[[");
        offset += info.substring(offset).indexOf(",") + 1;
        offset += info.substring(offset).indexOf(",") + 1;
        end = info.substring(offset).indexOf("]") + offset;
        Log.d("qw", info.substring(offset, end));
        return Double.parseDouble(info.substring(offset, end));
    }

    public static GoogleMapsStreetImage GetStreetViewImage(double targetLongitude, double targetLatitude, int width, int height){
        String photoInfo = GetClosestPhotoInfo(targetLongitude, targetLatitude);
        if (photoInfo == null) return null;
        String panoID = GetPanoID(photoInfo);

        int yaw = (int)(Math.random() * 359);

        byte[] imageData = GetStreetViewData(panoID, width, height, yaw, 0, 100);

        GoogleMapsStreetImage image = new GoogleMapsStreetImage();

        image.data = imageData;
        image.longitude = GetLongitude(photoInfo);
        image.latitude = GetLatitude(photoInfo);
        image.yaw = yaw;

        return image;
    }

}
