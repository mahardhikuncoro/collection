package base.screen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import base.data.UserData;
import id.collection.mobile.application.R;


public class BasePinActivity extends BaseDialogActivity {

    private static final String PIN_PREF = "PIN";
    private boolean shown = false;

    protected void setRequiredPin(Activity activity, boolean pin) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PIN_PREF, pin);
        editor.apply();
    }

    protected boolean isNeedPin(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(PIN_PREF, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shown)
            setRequiredPin(this, false);
        else
            setRequiredPin(this, true);
        shown = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!shown && isNeedPin(this)) {
            dialogAskPin();
            shown = true;
            setRequiredPin(this, false);
        }

    }


    protected void dialogAskPin() {
        new MaterialDialog.Builder(this)
                .title(R.string.askPinInfo)
                .content(R.string.askPinInfo2)
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .inputMaxLengthRes(4, R.color.stylePrimary)
                .alwaysCallInputCallback()
                .positiveText(R.string.buttonContinue)
                .input(R.string.askPinPin, 0, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.length() != 4) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        } else if (input.length() == 4) {
                            String _pin = input.toString();
                            _pin = _pin.replaceAll(" ", "");
                            UserData userData = new UserData(getApplicationContext());
                            if (userData.askPin(_pin)) {
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                                dialogWrongPin();
                            }
                        }
                    }
                })
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                    }
                })
                .cancelable(false)
                .show();
    }

    protected void dialogWrongPin() {
        new MaterialDialog.Builder(this)
                .content(R.string.askPinUnauthorized)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        dialogAskPin();
                    }
                })
                .cancelable(false)
                .show();
    }




}