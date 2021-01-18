package base.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;


@SuppressLint("ParcelCreator")
public class ServiceReceiver extends ResultReceiver{

    private Receiver receiver;
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public ServiceReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null)
            receiver.onReceiveResult(resultCode, resultData);
    }

    public void setReceiver(Receiver listener) {
        this.receiver = listener;
    }

    public static interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

}
