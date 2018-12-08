package io.github.reginildo.tomato;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.ResourceBundle;

class JFrameSettings extends JFrame {

    private static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("io.github.reginildo.tomato/Labels", Locales.localeDefault);
    private JPanel jPanelSettings;
    private static JButton jButtonSairSettings;
    private Tomato tomato = new Tomato(25, 5, 15);
    private static String stringIntervaloCurto =
            resourceBundle.getString("intervaloCurto");
    private static String stringIntervaloLongo = resourceBundle.getString("intervaloLongo");
    private static String stringValorShortBreak;
    private JLabel jLabelInfo = new JLabel("Ajuste os tempos:");
    private static JLabel jLabelSettingsCiclos, jLabelSettingsTomato, jLabelSettingsLongBreak, jLabelSettingsShortBreak;
    private static JSlider jSliderCiclos, jSliderTomato, jSliderLongBreak, jSliderShortBreak;
    private Font fontInfoSettings = new Font("Arial",
            Font.BOLD, 26);

    JFrameSettings() {
        setJLabels();
        setJSliders();
        setJButtonSair();
        setJPanels();
        setContentPane(jPanelSettings);
        setSize(300, 400);
        setLocation(600, 300);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(JFrameTomatoMain.resourceBundle
                .getString("stringTomatoSettingTitle"));
        setVisible(true);
    }

    private void setJButtonSair() {
        jButtonSairSettings = new JButton(resourceBundle.getString("buttonExit"));
        jButtonSairSettings.setMnemonic(KeyEvent.VK_S);
        jButtonSairSettings.addActionListener(new JButtonSairSettingsLintener());
        jButtonSairSettings.addMouseMotionListener(new JButtonSairSettingsMotionListener());
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

    private static void setTomatoTimeValues() {
        Tomato.setPomodoroTime(jSliderTomato.getValue());
        Tomato.setShortBreakTime(jSliderShortBreak.getValue());
        Tomato.setLongBreakTime(jSliderLongBreak.getValue());
    }

    private void setJPanels() {
        jPanelSettings = new JPanel();
        jPanelSettings.setBounds(20,20,300,300);
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings,
                BoxLayout.Y_AXIS));
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
        jSliderCiclos = new JSlider(
                SwingConstants.HORIZONTAL,1,8,4);
        jSliderCiclos.setMajorTickSpacing(1);
        jSliderCiclos.setMinorTickSpacing(1);
        jSliderCiclos.setPaintTicks(true);
        jSliderCiclos.setPaintLabels(true);
        jSliderCiclos.setValue(Tomato.getCiclosTime());
        jSliderCiclos.addChangeListener(new JSliderCiclosChangeListener());

        jSliderTomato = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 25);
        jSliderTomato.setMajorTickSpacing(10);
        jSliderTomato.setMinorTickSpacing(1);
        jSliderTomato.setPaintTicks(true);
        jSliderTomato.setPaintLabels(true);
        jSliderTomato.setValue(Tomato.getPomodoroTime());
        jSliderTomato.setValueIsAdjusting(true);
        jSliderTomato.addChangeListener(new JSliderTomatoChangeListener());
        jSliderTomato.addMouseMotionListener(new JSliderTomatoMotionListener());

        jSliderShortBreak = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 5);
        jSliderShortBreak.addMouseMotionListener(new JSliderShortBreakMotionListener());
        jSliderShortBreak.setMajorTickSpacing(10);
        jSliderShortBreak.setMinorTickSpacing(1);
        jSliderShortBreak.setPaintLabels(true);
        jSliderShortBreak.setPaintTicks(true);
        jSliderShortBreak.setValue(Tomato.getShortBreakTime());
        jSliderShortBreak.addChangeListener(new JSliderShortBreakChangeListener());

        jSliderLongBreak = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 15);
        jSliderLongBreak.setMajorTickSpacing(10);
        jSliderLongBreak.setMinorTickSpacing(1);
        jSliderLongBreak.setPaintTicks(true);
        jSliderLongBreak.setPaintLabels(true);
        jSliderLongBreak.setValue(Tomato.getLongBreakTime());
        jSliderLongBreak.addMouseMotionListener(new JSliderLongBreakMotionListener());
        jSliderLongBreak.addChangeListener(new JSliderLongBreakChangeListener());
    }

    private void setJLabels() {
        jLabelSettingsCiclos = new JLabel();
        jLabelSettingsTomato = new JLabel(resourceBundle.getString("stringTempoPomodoro"));
        jLabelSettingsLongBreak = new JLabel(
                resourceBundle.getString("intervaloLongo"));
        jLabelSettingsShortBreak = new JLabel(
                resourceBundle.getString("intervaloCurto"));
        jLabelInfo.setFont(fontInfoSettings);
    }

    private class JButtonSairSettingsLintener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrameSettings.setTomatoTimeValues();
            JFrameTomatoMain.jLabelToImageIconSmileys.setIcon(Main.jFrameTomatoMain.getImagePrepared());
            setVisible(false);
            setTimeStartValue();
            JFrameTomatoMain.timerPause = null;
            if (JFrameTomatoMain.timer != null) {
                JFrameTomatoMain.timer.cancel();
            }
            setButtonsOnExit();
            JFrameTomatoMain.jLabelTimeCounterView.setText(JFrameTomatoMain.format.format(JFrameTomatoMain.timerStart.getTime()));
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    }

    private class JButtonSairSettingsMotionListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            JFrameSettings.jButtonSairSettings.setToolTipText(
                    "Desejar sair das configurações?");

        }
    }

    private class JSliderLongBreakMotionListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

            jSliderLongBreak.setToolTipText(
                    String.valueOf(jSliderLongBreak.getValue()
                            + " " + resourceBundle.getString("minutos")));

        }
    }

    private class JSliderShortBreakMotionListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            jSliderShortBreak.setToolTipText(
                    String.valueOf(jSliderShortBreak.getValue()
                            + " " + resourceBundle.getString("minutos")));

        }
    }

    private class JSliderTomatoMotionListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            JFrameSettings.jSliderTomato.setToolTipText(String.valueOf(
                    JFrameSettings.jSliderTomato.getValue() + " " +
                            resourceBundle.getString("minutos")));

        }
    }

    private class JSliderTomatoChangeListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(JFrameSettings.jSliderTomato.getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setPomodoroTime(JFrameSettings.jSliderTomato.getValue());
            JFrameSettings.jLabelSettingsTomato.setText(valorTomato);
        }
    }

    private class JSliderShortBreakChangeListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            JFrameSettings.stringValorShortBreak = JFrameSettings.stringIntervaloCurto
                    + String.valueOf(JFrameSettings.jSliderShortBreak.getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setShortBreakTime(JFrameSettings.jSliderShortBreak.getValue());
            JFrameSettings.jLabelSettingsShortBreak.setText(JFrameSettings.stringValorShortBreak);
        }
    }

    private class JSliderLongBreakChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JFrameTomatoMain.stringValorLongBreak = JFrameSettings.stringIntervaloLongo
                    + String.valueOf(JFrameSettings.jSliderLongBreak.getValue()
                    + " " + resourceBundle.getString("minutos"));
            Tomato.setLongBreakTime(JFrameSettings.jSliderLongBreak.getValue());
            JFrameSettings.jLabelSettingsLongBreak.setText(JFrameTomatoMain.stringValorLongBreak);

        }
    }

    private class JSliderCiclosChangeListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            String stringValorCiclos = String.valueOf("Quantidade de ciclos: " + jSliderCiclos.getValue() + " ciclos");
            Tomato.setCiclosTime(jSliderTomato.getValue());
            jLabelSettingsCiclos.setText(stringValorCiclos);

        }
    }
}
