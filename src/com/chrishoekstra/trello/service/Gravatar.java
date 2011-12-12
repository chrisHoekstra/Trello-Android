package com.chrishoekstra.trello.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Gravatar {
   private static final String URL = "http://www.gravatar.com/avatar/";

    public static String getImageFromEmail(String email) {
        if (TextUtils.isEmpty(email)) return null;
        String emailHash = Gravatar.md5Hex(email.toLowerCase().trim());
        return URL + emailHash;
    }

    public static String getImage(String hash) {
        return URL + hash;
    }

    public static Bitmap downloadGravatar(final String hash) throws IOException {

            URL aURL = new URL( getImage( hash));
            final HttpURLConnection conn = (HttpURLConnection) aURL.openConnection();
            conn.setDoInput(true);
            conn.connect();
            final Bitmap bm;
            if (conn.getResponseCode() != 404) {
                final InputStream is = conn.getInputStream();
                bm = BitmapFactory.decodeStream(is);
                is.close();
            } else{
                // TODO return default gravatar image
                return null;
            }
            return bm;
        }

    private static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    private static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
