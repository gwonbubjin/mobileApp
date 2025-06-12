package com.example.pilly;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private final int min;
    private final int max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        try {
            // 입력 후의 결과값 예측
            String newVal = dest.subSequence(0, dstart)
                    + source.toString()
                    + dest.subSequence(dend, dest.length());
            if (newVal.isEmpty()) return null; // 빈칸 허용(삭제 등)
            int input = Integer.parseInt(newVal);
            if (input < min || input > max) return "";
        } catch (Exception e) {
            return "";
        }
        return null; // 입력 허용
    }
}
