package com.dxp.HeThongChuoiCungUng.Utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringUtils {
    private static SimpleDateFormat DateFormating = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public static SimpleDateFormat getDateFormating() {
        return DateFormating;
    }
}
