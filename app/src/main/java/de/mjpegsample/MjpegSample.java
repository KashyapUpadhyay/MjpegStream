package de.mjpegsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.mjpegsample.MjpegView.MjpegInputStream;
import de.mjpegsample.MjpegView.MjpegView;

import static android.R.attr.password;


public class MjpegSample extends Activity {
	private MjpegView mv;
	
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.d("hello1","amar1");
        String user = "admin";
        String password = "admin";
        //sample public cam
        //String URL = "http://202.122.18.242:/axis-cgi/mjpg/video.cgi?resolution=VGA";
        //String URL = "http://192.168.2.253:1080/cgi-bin/mjpg/video.cgi?channel=0&subtype=1";


        String urlstr = "http://192.168.5.10/axis-cgi/mjpg/video.cgi?subtype=1";
        try {
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if ((user != null) && (password != null)) {
                String userPass = user + ":" + password;
                String basicAuth = "Basic " + Base64.encodeToString(userPass.getBytes(), Base64.DEFAULT);
                conn.setRequestProperty("Authorization", basicAuth);
            }
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            mv = new MjpegView(this);
            setContentView(mv);

            mv.setSource(MjpegInputStream.read(url));
            Log.d("hello3","amar");
            mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
            //mv.showFps(true);
        }
        catch (MalformedURLException e)
        {}
        catch (IOException e1)
        {}



	}
	
	public void onPause() {
		super.onPause();
		mv.stopPlayback();
	}

}
