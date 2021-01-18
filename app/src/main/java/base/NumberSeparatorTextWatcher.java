package base;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;

public class NumberSeparatorTextWatcher implements TextWatcher {

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean isSeparatorExist;
    private int numberOfDecimal;

    private EditText editText;

    public NumberSeparatorTextWatcher(EditText editText) {
        df = new DecimalFormat("#,###,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        this.editText = editText;
        isSeparatorExist = false;
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        try {
            int inilen, endlen;
            inilen = editText.getText().length();

            String v = (s.toString()).replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number n = df.parse(v);
            int cp = editText.getSelectionStart();
            if (isSeparatorExist) {
                StringBuilder builder = new StringBuilder();
                while (numberOfDecimal-- > 0)
                    builder.append('0');
                editText.setText(df.format(n) + builder.toString());
                Log.e("GOGOOOO"," : " + df.format(n));
            } else {
                editText.setText(dfnd.format(n));
            }
            endlen = editText.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= editText.getText().length()) {
                editText.setSelection(sel);
            } else {
                editText.setSelection(editText.getText().length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int index = s.toString().indexOf(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
        numberOfDecimal = 0;
        if (index > -1) {
            for (index++; index < s.length(); index++) {
                if (s.charAt(index) == '0')
                    numberOfDecimal++;
                else
                    numberOfDecimal = 0;
            }
            isSeparatorExist = true;

            Log.e("GGIIIIGIII"," : " + numberOfDecimal);
        } else
            isSeparatorExist = false;
    }
}