package io.github.reginildo.tomato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private String stringValorShortBreak, stringValorCiclos;
    private JLabel jLabelInfo = new JLabel("Ajuste os tempos:");
    private JLabel jLabelSettingsCiclos, jLabelSettingsTomato, jLabelSettingsLongBreak, jLabelSettingsShortBreak;
    private static JSlider jSliderCiclos, jSliderTomato, jSliderLongBreak, jSliderShortBreak;
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
        setSize(300, 400);
        setLocation(600, 300);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(JFrameTomatoMain.resourceBundle
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
        jButtonSairSettings.addActionListener(new JButtonSairSettingsLintener());
    }

    private void setButtonsOnExit() {
        JFrameTomatoMain.jButtonStart.setText(JFrameTomatoMain.resourceBundle.getString("buttonStart"));
        JFrameTomatoMain.jButtonPause.setEnabled(false);
        JFrameTomatoMain.jButtonStart.setEnabled(true);
        JFrameTomatoMain.jButtonReset.setEnabled(false);
    }

    private void setTimeStartValue() {
        JFrameTomatoMain.timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
        JFrameTomatoMain.timerStart.set(Calendar.SECOND, 0);
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
        jPanelSettings.setBounds(20,20,300,300);

    }

    private void setJPanelSettings() {
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings,
                BoxLayout.Y_AXIS));
    }

    private void addComponentsToJPanelSettings() {
        jPanelSettings.add(jLabelInfo);
        jPanelSettings.add(jLabelSettingsCiclos);
        jPanelSettings.add(jSliderCiclos);
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
        setJSliderCiclos();
        setJSliderTomato();
        setJSliderShortBreak();
        setJSliderLongBreak();
        setListenersToJSliders();
    }

    private void setJSliderCiclos() {

        jSliderCiclos.setMajorTickSpacing(1);
        jSliderCiclos.setMinorTickSpacing(1);
        jSliderCiclos.setPaintTicks(true);
        jSliderCiclos.setPaintLabels(true);
        jSliderCiclos.setValue(Tomato.getCiclosTime());
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
        jSliderCiclos = new JSlider(
                SwingConstants.HORIZONTAL,1,8,4);
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
        setJSliderCiclosChangeListener();
        setJSliderTomatoChangeListener();
        setJSliderShortBreakChangeListener();
        setJSliderLongBreakChangeListener();
    }

    private void setJSliderCiclosChangeListener() {
        jSliderCiclos.addChangeListener(changeEvent -> {
            stringValorCiclos = String.valueOf("Quantidade de ciclos: "+jSliderCiclos.getValue() + " ciclos");
            Tomato.setCiclosTime(jSliderTomato.getValue());
            jLabelSettingsCiclos.setText(stringValorCiclos);
        });
    }


    private void setJSliderLongBreakChangeListener() {
        jSliderLongBreak.addChangeListener(changeEvent -> {
            JFrameTomatoMain.stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue()
                    + " " + resourceBundle.getString("minutos"));
            Tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jLabelSettingsLongBreak.setText(JFrameTomatoMain.stringValorLongBreak);
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
        jLabelSettingsCiclos = new JLabel();
        jLabelSettingsTomato = new JLabel(resourceBundle.getString("stringTempoPomodoro"));
        jLabelSettingsLongBreak = new JLabel(
                resourceBundle.getString("intervaloLongo"));
        jLabelSettingsShortBreak = new JLabel(
                resourceBundle.getString("intervaloCurto"));
    }


    private class JButtonSairSettingsLintener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setTomatoTimeValues();
            JFrameTomatoMain.jLabelToImageIconSmileys.setIcon(Main.jFrameTomatoMain.getImagePrepared());
            setVisible(false);
            setTimeStartValue();
            JFrameTomatoMain.timerPause = null;
            if (JFrameTomatoMain.timer != null) {
                JFrameTomatoMain.timer.cancel();
            }
            setButtonsOnExit();
            JFrameTomatoMain.jLabelTimeCounter.setText(JFrameTomatoMain.format.format(JFrameTomatoMain.timerStart.getTime()));
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        }
    }


}
