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
 * <p>
 * 音频焦点是为了解决同时播放音频而声音混乱的问题。
 * 1.音频焦点是互斥的，同一时刻只允许有一个获取音频焦点。（注意，失去焦点不代表不能播放音频）
 * 2.requestAudioFocus获取焦点成功不会回调OnAudioFocusChangeListener 只有当下一个获取音频焦点时 上一个OnAudioFocusChangeListener才会回调失去焦点
 * 3.同理，上一个requestAudioFocus 下一个则回调OnAudioFocusChangeListener失去焦点
 * 4.下一个abandonAudioFocus 上一个上一个OnAudioFocusChangeListener才会回调获取焦点
 * <p>
 * <p>
 * <p>
 * AUDIOFOCUS_GAIN：获得了Audio Focus；
 * AUDIOFOCUS_LOSS：失去了Audio Focus，并将会持续很长的时间。这里因为可能会停掉很长时间，
 * 所以不仅仅要停止Audio的播放，最好直接释放掉Media资源。而因为停止播放Audio的时间会很长，
 * 如果程序因为这个原因而失去AudioFocus，最好不要让它再次自动获得AudioFocus而继续播放，
 * 不然突然冒出来的声音会让用户感觉莫名其妙，感受很不好。这里直接放弃AudioFocus，
 * 当然也不用再侦听远程播放控制【如下面代码的处理】。要再次播放，除非用户再在界面上点击开始播放，
 * 才重新初始化Media，进行播放。
 * AUDIOFOCUS_LOSS_TRANSIENT：暂时失去Audio Focus，并会很快再次获得。必须停止Audio的播放，
 * 但是因为可能会很快再次获得AudioFocus，这里可以不释放Media资源；
 * AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK：暂时失去AudioFocus，但是可以继续播放，不过要在降低音量。
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
