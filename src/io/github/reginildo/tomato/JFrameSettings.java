package io.github.reginildo.tomato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.ResourceBundle;

class JFrameSettings extends JFrame {
    private static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("io.github.reginildo.tomato/Labels", Locales.localeDefault);
    private JPanel jPanelSettings;
    private JButton jButtonSairSettings = new JButton(resourceBundle.getString("buttonExit"));
    private Tomato tomato = new Tomato(25, 5, 15);
    private String stringIntervaloCurto =
            resourceBundle.getString("intervaloCurto");
    private String stringIntervaloLongo = resourceBundle.getString("intervaloLongo");
    private String stringValorShortBreak;
    private JLabel jLabelInfo = new JLabel("Ajuste os tempos:");
    private JLabel jLabelSettingsTomato, jLabelSettingsLongBreak, jLabelSettingsShortBreak;
    private static JSlider jSliderTomato, jSliderLongBreak, jSliderShortBreak;
    private Font fontInfoSettings = new Font("Arial",
            Font.BOLD, 26);

    JFrameSettings() {
        createLabelsForJFrameSettings();
        createJPanelSettings();
        setJSliders();
        setJLabelInfo();
        setJButtonSairSetting();
        setJPanelSettings();
        setActionListenerTojButtonSairSettings();
        setMouseMotionListenerTojButtonSairSettings();
        addComponentsToJPanelSettings();
        setJFrameSettings();
    }

    private void setJFrameSettings() {
        setContentPane(jPanelSettings);
        setSize(300, 300);
        setLocation(600, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(JFrameGui.resourceBundle
                .getString("stringTomatoSettingTitle"));
        setVisible(true);
    }

    private void setJLabelInfo() {
        jLabelInfo.setFont(fontInfoSettings);
    }

    private void setMouseMotionListenerTojButtonSairSettings() {
        jButtonSairSettings.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonSairSettings.setToolTipText(
                        "Desejar sair das configurações?");
            }
        });
    }

    private void setActionListenerTojButtonSairSettings() {
        jButtonSairSettings.addActionListener(actionEvent -> {
            setTomatoTimeValues();
            setVisible(false);
            setTimeStartValue();
            JFrameGui.timerPause = null;
            if (JFrameGui.timer != null) {
                JFrameGui.timer.cancel();
            }
            setButtonsOnExit();
            JFrameGui.jLabelTimeCounter.setText(JFrameGui.format.format(JFrameGui.timerStart.getTime()));
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        });
    }

    private void setButtonsOnExit() {
        JFrameGui.jButtonStart.setText(JFrameGui.resourceBundle.getString("buttonStart"));
        JFrameGui.jButtonPause.setEnabled(false);
        JFrameGui.jButtonStart.setEnabled(true);
        JFrameGui.jButtonReset.setEnabled(false);
    }

    private void setTimeStartValue() {
        JFrameGui.timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
        JFrameGui.timerStart.set(Calendar.SECOND, 0);
    }

    private void setTomatoTimeValues() {
        Tomato.setPomodoroTime(jSliderTomato.getValue());
        Tomato.setShortBreakTime(jSliderShortBreak.getValue());
        Tomato.setLongBreakTime(jSliderLongBreak.getValue());
    }

    private void setJButtonSairSetting() {
        jButtonSairSettings.setMnemonic(KeyEvent.VK_S);
    }

    private void createJPanelSettings() {
        jPanelSettings = new JPanel();
    }

    private void setJPanelSettings() {
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings,
                BoxLayout.Y_AXIS));
    }

    private void addComponentsToJPanelSettings() {
        jPanelSettings.add(jLabelInfo);
        jPanelSettings.add(jLabelSettingsTomato);
        jPanelSettings.add(jSliderTomato);
        jPanelSettings.add(jLabelSettingsShortBreak);
        jPanelSettings.add(jSliderShortBreak);
        jPanelSettings.add(jLabelSettingsLongBreak);
        jPanelSettings.add(jSliderLongBreak);
        jPanelSettings.add(jButtonSairSettings);
    }

    private void setJSliders() {
        createJSlidesToJFrameSettings();
        setJSliderTomato();
        setJSliderShortBreak();
        setJSliderLongBreak();
        setListenersToJSliders();
    }

    private void setJSliderLongBreak() {
        jSliderLongBreak.setMajorTickSpacing(10);
        jSliderLongBreak.setMinorTickSpacing(1);
        jSliderLongBreak.setPaintTicks(true);
        jSliderLongBreak.setPaintLabels(true);
        jSliderLongBreak.setValue(Tomato.getLongBreakTime());
    }

    private void setJSliderShortBreak() {
        jSliderShortBreak.setMajorTickSpacing(10);
        jSliderShortBreak.setMinorTickSpacing(1);
        jSliderShortBreak.setPaintLabels(true);
        jSliderShortBreak.setPaintTicks(true);
        jSliderShortBreak.setValue(Tomato.getShortBreakTime());
    }

    private void setJSliderTomato() {
        jSliderTomato.setMajorTickSpacing(10);
        jSliderTomato.setMinorTickSpacing(1);
        jSliderTomato.setPaintTicks(true);
        jSliderTomato.setPaintLabels(true);
        jSliderTomato.setValue(Tomato.getPomodoroTime());
        jSliderTomato.setValueIsAdjusting(true);
    }

    private void createJSlidesToJFrameSettings() {
        jSliderTomato = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 25);
        jSliderShortBreak = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 5);
        jSliderLongBreak = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 15);
    }

    private void setListenersToJSliders() {
        setChangeListenersToJSliders();
        setMouseMotionListenerToJSliders();
    }

    private void setMouseMotionListenerToJSliders() {
        setJSliderTomatoMouseMotionListener();
        setJSliderShortBreakMouseMotionListener();
        setJSliderLongBreakMouseMotionListener();
    }

    private void setJSliderLongBreakMouseMotionListener() {
        jSliderLongBreak.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderLongBreak.setToolTipText(
                        String.valueOf(jSliderLongBreak.getValue()
                                + " " + resourceBundle.getString("minutos")));
            }
        });
    }

    private void setJSliderShortBreakMouseMotionListener() {
        jSliderShortBreak.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderShortBreak.setToolTipText(
                        String.valueOf(jSliderShortBreak.getValue() + " " +
                                resourceBundle.getString("minutos")));
            }
        });
    }

    private void setJSliderTomatoMouseMotionListener() {
        jSliderTomato.addMouseMotionListener(
                new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent mouseEvent) {
                    }

                    @Override
                    public void mouseMoved(MouseEvent mouseEvent) {
                        jSliderTomato.setToolTipText(String.valueOf(
                                jSliderTomato.getValue() + " " +
                                        resourceBundle.getString("minutos")));
                    }
                });
    }

    private void setChangeListenersToJSliders() {
        setJSliderTomatoChangeListener();
        setJSliderShortBreakChangeListener();
        setJSliderLongBreakChangeListener();
    }

    private void setJSliderLongBreakChangeListener() {
        jSliderLongBreak.addChangeListener(changeEvent -> {
            JFrameGui.stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue()
                    + " " + resourceBundle.getString("minutos"));
            Tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jLabelSettingsLongBreak.setText(JFrameGui.stringValorLongBreak);
        });
    }

    private void setJSliderShortBreakChangeListener() {
        jSliderShortBreak.addChangeListener(changeEvent -> {
            stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(jSliderShortBreak.getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setShortBreakTime(jSliderShortBreak.getValue());
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        });
    }

    private void setJSliderTomatoChangeListener() {
        jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(jSliderTomato.getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setPomodoroTime(jSliderTomato.getValue());
            jLabelSettingsTomato.setText(valorTomato);
        });
    }

    private void createLabelsForJFrameSettings() {
        jLabelSettingsTomato = new JLabel(resourceBundle.getString("stringTempoPomodoro"));
        jLabelSettingsLongBreak = new JLabel(
                resourceBundle.getString("intervaloLongo"));
        jLabelSettingsShortBreak = new JLabel(
                resourceBundle.getString("intervaloCurto"));
    }
}
