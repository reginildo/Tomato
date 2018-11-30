package io.github.reginildo.tomato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.ResourceBundle;

class JFrameSettings extends JFrame {
    static ResourceBundle resourceBundle = ResourceBundle
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
    static JSlider jSliderTomato, jSliderLongBreak, jSliderShortBreak;
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
            Tomato.setPomodoroTime(jSliderTomato.getValue());
            Tomato.setShortBreakTime(jSliderShortBreak.getValue());
            Tomato.setLongBreakTime(jSliderLongBreak.getValue());
            setVisible(false);
            JFrameGui.timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
            JFrameGui.timerStart.set(Calendar.SECOND, 0);
            JFrameGui.timerPause = null;
            if(JFrameGui.timer != null){
                JFrameGui.timer.cancel();
            }
            JFrameGui.jButtonStart.setText(JFrameGui.resourceBundle.getString("buttonStart"));
            JFrameGui.jButtonPause.setEnabled(false);
            JFrameGui.jButtonStart.setEnabled(true);
            JFrameGui.jButtonReset.setEnabled(false);
            JFrameGui.jLabelTimeCounter.setText(JFrameGui.format.format(JFrameGui.timerStart.getTime()));
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        });
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

    private void setChangeListenersToJSliders() {
        jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(jSliderTomato.getValue() + " " +
                    resourceBundle.getString("minutos"));
            tomato.setPomodoroTime(jSliderTomato.getValue());
            jLabelSettingsTomato.setText(valorTomato);
        });
        jSliderShortBreak.addChangeListener(changeEvent -> {
            stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(jSliderShortBreak.getValue() + " " +
                    resourceBundle.getString("minutos"));
            tomato.setShortBreakTime(jSliderShortBreak.getValue());
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        });
        jSliderLongBreak.addChangeListener(changeEvent -> {
            JFrameGui.stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue()
                    + " " + resourceBundle.getString("minutos"));
            tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jLabelSettingsLongBreak.setText(JFrameGui.stringValorLongBreak);
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
