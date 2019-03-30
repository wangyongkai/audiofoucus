package af.test.testaudiofoucus;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


/**
 * 结论：
 *
 * 音频焦点是为了解决同时播放音频而声音混乱的问题。
 * 1.音频焦点是互斥的，同一时刻只允许有一个获取音频焦点。（注意，失去焦点不代表不能播放音频）
 * 2.requestAudioFocus获取焦点成功不会回调OnAudioFocusChangeListener 只有当下一个获取音频焦点时 上一个OnAudioFocusChangeListener才会回调失去焦点
 * 3.同理，上一个requestAudioFocus 下一个则回调OnAudioFocusChangeListener失去焦点
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    class AFChangeListener implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            // Log.e("qazx", "AFChangeListener===" + this);
            TextView txtView = (TextView) findViewById(R.id.textView);
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                Log.e("qazx", "AFChangeListener===AUDIOFOCUS_LOSS_TRANSIENT");
                // txtView.append("AUDIOFOCUS_LOSS_TRANSIENT\n");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // txtView.append("AUDIOFOCUS_GAIN\n");
                Log.e("qazx", "AFChangeListener===AUDIOFOCUS_GAIN");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // txtView.append("AUDIOFOCUS_LOSS\n");
                Log.e("qazx", "AFChangeListener===AUDIOFOCUS_LOSS");
            }
        }
    }

    class AFChangeListener1 implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            // Log.e("qazx", "AFChangeListener===" + this);
            TextView txtView = (TextView) findViewById(R.id.textView1);
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                Log.e("qazx", "AFChangeListener1===AUDIOFOCUS_LOSS_TRANSIENT");
                // txtView.append("AUDIOFOCUS_LOSS_TRANSIENT\n");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                Log.e("qazx", "AFChangeListener1===AUDIOFOCUS_GAIN");
                // txtView.append("AUDIOFOCUS_GAIN\n");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                Log.e("qazx", "AFChangeListener1===AUDIOFOCUS_LOSS");
                // txtView.append("AUDIOFOCUS_LOSS\n");
            }
        }
    }


    class AFChangeListener2 implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            // Log.e("qazx", "AFChangeListener===" + this);
            TextView txtView = (TextView) findViewById(R.id.textView1);
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                Log.e("qazx", "AFChangeListener2===AUDIOFOCUS_LOSS_TRANSIENT");
                // txtView.append("AUDIOFOCUS_LOSS_TRANSIENT\n");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                Log.e("qazx", "AFChangeListener2===AUDIOFOCUS_GAIN");
                // txtView.append("AUDIOFOCUS_GAIN\n");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                Log.e("qazx", "AFChangeListener2===AUDIOFOCUS_LOSS");
                // txtView.append("AUDIOFOCUS_LOSS\n");
            }
        }
    }


    AFChangeListener afChangeListener = new AFChangeListener();
    AFChangeListener1 afChangeListener1 = new AFChangeListener1();
    AFChangeListener2 afChangeListener2 = new AFChangeListener2();

    public void requestAudioFocus(View view) {
        // afChangeListener = new AFChangeListener();
        // Log.e("qazx", "requestAudioFocus===" + afChangeListener);
        TextView txtView = (TextView) findViewById(R.id.textView);
        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int result = am.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);
        Log.e("qazx", "requestAudioFocus===result==" + result);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //  txtView.append("AUDIOFOCUS_REQUEST_GRANTED\n");
        } else if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            // txtView.append("AUDIOFOCUS_REQUEST_FAILED\n");
        } else {
            // txtView.append("Unknown response from AudioManager\n");
        }
    }


    public void abandonAudioFocus(View view) {

        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(afChangeListener);
    }


    public void requestAudioFocus1(View view) {
        // afChangeListener1 = new AFChangeListener1();
        // Log.e("qazx", "requestAudioFocus===" + afChangeListener1);
        TextView txtView = (TextView) findViewById(R.id.textView1);
        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int result = am.requestAudioFocus(afChangeListener1,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);
        Log.e("qazx", "requestAudioFocus1===result==" + result);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // txtView.append("AUDIOFOCUS_REQUEST_GRANTED\n");
        } else if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            // txtView.append("AUDIOFOCUS_REQUEST_FAILED\n");
        } else {
            //txtView.append("Unknown response from AudioManager\n");
        }
    }


    public void abandonAudioFocus1(View view) {

        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(afChangeListener1);
    }


    public void requestAudioFocus2(View view) {
        // afChangeListener1 = new AFChangeListener1();
        // Log.e("qazx", "requestAudioFocus===" + afChangeListener1);
        TextView txtView = (TextView) findViewById(R.id.textView1);
        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int result = am.requestAudioFocus(afChangeListener2,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);
        Log.e("qazx", "requestAudioFocus2===result==" + result);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // txtView.append("AUDIOFOCUS_REQUEST_GRANTED\n");
        } else if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            // txtView.append("AUDIOFOCUS_REQUEST_FAILED\n");
        } else {
            //txtView.append("Unknown response from AudioManager\n");
        }
    }


    public void abandonAudioFocus2(View view) {

        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(afChangeListener2);
    }


}
