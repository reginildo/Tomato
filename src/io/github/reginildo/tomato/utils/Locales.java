package io.github.reginildo.tomato.utils;

import java.util.Locale;

public class Locales {
    private static final Locale localeDefault = Locale.getDefault();
    private static final Locale locale_pt_BR = new Locale("pt", "BR");
    private static final Locale locale_en_US = new Locale("en", "US");
    private static final Locale locale_tlh = new Locale("tlh");

    public static Locale getLocaleDefault() {
        return localeDefault;
    }

    public static Locale getLocale_pt_BR() {
        return locale_pt_BR;
    }

    public static Locale getLocale_en_US() {
        return locale_en_US;
    }

    public static Locale getLocale_tlh() {
        return locale_tlh;
    }
}
