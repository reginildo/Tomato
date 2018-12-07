package io.github.reginildo.tomato;

import java.awt.*;
import java.text.DateFormat;
import java.util.Date;

public class HoraThread implements Runnable {

    private static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);

    @Override
    public void run() {
        while (JFrameTomatoMain.isShowTimeView()) {
            JFrameTomatoMain.jLabelHora.setLayout(new FlowLayout(FlowLayout.TRAILING));
            JFrameTomatoMain.jLabelHora.setText(dateFormat.format(new Date()));
            JFrameTomatoMain.jLabelHora.setForeground(Color.DARK_GRAY);
        }
    }
}
