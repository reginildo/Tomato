package io.github.reginildo.tomato.view;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.slider.WebSlider;
import io.github.reginildo.tomato.utils.Locales;
import io.github.reginildo.tomato.main.Main;
import io.github.reginildo.tomato.utils.Tomato;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.ResourceBundle;

final class JFrameSettings extends WebFrame {

    private static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("io.github.reginildo.tomato/Labels", Locales.getLocaleDefault());
    private JPanel jPanelSettings;
    private static WebButton jButtonSairSettings;
    static Tomato tomato = new Tomato(Tomato.getDefaultCiclosTime(),
            Tomato.getDefaultPomodoroTime(),
            Tomato.getDefaultShortBreakTime(),
            Tomato.getDefaultLongBreakTime());
    private static String stringIntervaloCurto =
            resourceBundle.getString("intervaloCurto");
    private static String stringIntervaloLongo = resourceBundle.getString("intervaloLongo");
    private static String stringValorShortBreak;
    private JLabel jLabelInfo = new JLabel("Ajuste os tempos:");
    private static WebLabel jLabelSettingsCiclos, jLabelSettingsTomato, jLabelSettingsLongBreak, jLabelSettingsShortBreak;
    private static WebSlider jSliderCiclos;
    private static WebSlider jSliderTomato;
    private static WebSlider jSliderLongBreak;
    private static WebSlider jSliderShortBreak;
    private Font fontInfoSettings = new Font("Arial",
            Font.BOLD, 26);

    JFrameSettings() {
        JFrameTomatoMain.invokeAndShow();
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
        setVisible(false);
    }

    private void invokeAndShow() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    //UIManager.setLookAndFeel(new MetalLookAndFeel());
                    //UIManager.setLookAndFeel(new WindowsLookAndFeel());
                    UIManager.setLookAndFeel(new WebLookAndFeel());
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public final static JSlider getjSliderCiclos() {
        return jSliderCiclos;
    }

    public final static JSlider getjSliderTomato() {
        return jSliderTomato;
    }

    public final static JSlider getjSliderLongBreak() {
        return jSliderLongBreak;
    }

    public final static JSlider getjSliderShortBreak() {
        return jSliderShortBreak;
    }

    private void setJButtonSair() {
        jButtonSairSettings = new WebButton(resourceBundle.getString("buttonExit"));
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
        Tomato.setCiclosTime(getjSliderCiclos().getValue());
        Tomato.setPomodoroTime(getjSliderTomato().getValue());
        Tomato.setShortBreakTime(getjSliderShortBreak().getValue());
        Tomato.setLongBreakTime(getjSliderLongBreak().getValue());

    }

    private void setJPanels() {
        jPanelSettings = new JPanel();
        jPanelSettings.setBounds(20, 20, 300, 300);
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings,
                BoxLayout.Y_AXIS));
        jPanelSettings.add(jLabelInfo);
        jPanelSettings.add(jLabelSettingsCiclos);
        jPanelSettings.add(getjSliderCiclos());
        jPanelSettings.add(jLabelSettingsTomato);
        jPanelSettings.add(getjSliderTomato());
        jPanelSettings.add(jLabelSettingsShortBreak);
        jPanelSettings.add(getjSliderShortBreak());
        jPanelSettings.add(jLabelSettingsLongBreak);
        jPanelSettings.add(getjSliderLongBreak());
        jPanelSettings.add(jButtonSairSettings);
    }

    private void setJSliders() {
        jSliderCiclos = new WebSlider(
                SwingConstants.HORIZONTAL, 1, 8, 4);
        getjSliderCiclos().setMajorTickSpacing(1);
        getjSliderCiclos().setMinorTickSpacing(1);
        getjSliderCiclos().setPaintTicks(true);
        getjSliderCiclos().setPaintLabels(true);
        getjSliderCiclos().setValue(Tomato.getDefaultCiclosTime());
        getjSliderCiclos().addChangeListener(new JSliderCiclosChangeListener());

        jSliderTomato = new WebSlider(
                SwingConstants.HORIZONTAL, 0, 50, 25);
        getjSliderTomato().setMajorTickSpacing(10);
        getjSliderTomato().setMinorTickSpacing(1);
        getjSliderTomato().setPaintTicks(true);
        getjSliderTomato().setPaintLabels(true);
        getjSliderTomato().setValue(Tomato.getPomodoroTime());
        getjSliderTomato().setValueIsAdjusting(true);
        getjSliderTomato().addChangeListener(new JSliderTomatoChangeListener());
        getjSliderTomato().addMouseMotionListener(new JSliderTomatoMotionListener());

        jSliderShortBreak = new WebSlider(
                SwingConstants.HORIZONTAL, 0, 50, 5);
        getjSliderShortBreak().addMouseMotionListener(new JSliderShortBreakMotionListener());
        getjSliderShortBreak().setMajorTickSpacing(10);
        getjSliderShortBreak().setMinorTickSpacing(1);
        getjSliderShortBreak().setPaintLabels(true);
        getjSliderShortBreak().setPaintTicks(true);
        getjSliderShortBreak().setValue(Tomato.getShortBreakTime());
        getjSliderShortBreak().addChangeListener(new JSliderShortBreakChangeListener());

        jSliderLongBreak = new WebSlider(
                SwingConstants.HORIZONTAL, 0, 50, 15);
        getjSliderLongBreak().setMajorTickSpacing(10);
        getjSliderLongBreak().setMinorTickSpacing(1);
        getjSliderLongBreak().setPaintTicks(true);
        getjSliderLongBreak().setPaintLabels(true);
        getjSliderLongBreak().setValue(Tomato.getLongBreakTime());
        getjSliderLongBreak().addMouseMotionListener(new JSliderLongBreakMotionListener());
        getjSliderLongBreak().addChangeListener(new JSliderLongBreakChangeListener());
    }

    private void setJLabels() {
        jLabelSettingsCiclos = new WebLabel();
        jLabelSettingsTomato = new WebLabel(resourceBundle.getString("stringTempoPomodoro"));
        jLabelSettingsLongBreak = new WebLabel(
                resourceBundle.getString("intervaloLongo"));
        jLabelSettingsShortBreak = new WebLabel(
                resourceBundle.getString("intervaloCurto"));
        jLabelInfo.setFont(fontInfoSettings);
    }

    private class JButtonSairSettingsLintener implements ActionListener {
        //todo nao permitir valores zerados

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((JFrameSettings.jSliderTomato.getValue() == 0) ||
                    (JFrameSettings.getjSliderLongBreak().getValue() == 0)
                    || (JFrameSettings.getjSliderShortBreak().getValue() == 0)) {
                JOptionPane.showMessageDialog(null, "Insira algum valor acima de zero.");

            } else {
                JFrameSettings.setTomatoTimeValues();
                JFrameTomatoMain.webLabelToImageIconSmileys.setIcon(Main.jFrameTomatoMain.getImagePrepared());
                setVisible(false);
                setTimeStartValue();
                JFrameTomatoMain.timerPause = null;
                if (JFrameTomatoMain.timer != null) {
                    JFrameTomatoMain.timer.cancel();
                }
                setButtonsOnExit();
                JFrameTomatoMain.jPanelDetails.setVisible(false);
                JFrameTomatoMain.webLabelTimeCounterView.setText(
                        JFrameTomatoMain.format.format(JFrameTomatoMain.timerStart.getTime()));
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }

        }
    }

    private class JButtonSairSettingsMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            JFrameSettings.jButtonSairSettings.setToolTipText(
                    "Desejar sair das configurações?");

        }
    }

    private class JSliderLongBreakMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

            getjSliderLongBreak().setToolTipText(
                    String.valueOf(getjSliderLongBreak().getValue()
                            + " " + resourceBundle.getString("minutos")));

        }
    }

    private class JSliderShortBreakMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            getjSliderShortBreak().setToolTipText(
                    String.valueOf(getjSliderShortBreak().getValue()
                            + " " + resourceBundle.getString("minutos")));

        }
    }

    private class JSliderTomatoMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            JFrameSettings.getjSliderTomato().setToolTipText(String.valueOf(
                    JFrameSettings.getjSliderTomato().getValue() + " " +
                            resourceBundle.getString("minutos")));

        }
    }

    private class JSliderTomatoChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(JFrameSettings.getjSliderTomato().getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setPomodoroTime(JFrameSettings.getjSliderTomato().getValue());
            JFrameSettings.jLabelSettingsTomato.setText(valorTomato);
        }
    }

    private class JSliderShortBreakChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JFrameSettings.stringValorShortBreak = JFrameSettings.stringIntervaloCurto
                    + String.valueOf(JFrameSettings.getjSliderShortBreak().getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setShortBreakTime(JFrameSettings.getjSliderShortBreak().getValue());
            JFrameSettings.jLabelSettingsShortBreak.setText(JFrameSettings.stringValorShortBreak);
        }
    }

    private class JSliderLongBreakChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JFrameTomatoMain.stringValorLongBreak = JFrameSettings.stringIntervaloLongo
                    + String.valueOf(JFrameSettings.getjSliderLongBreak().getValue()
                    + " " + resourceBundle.getString("minutos"));
            Tomato.setLongBreakTime(JFrameSettings.getjSliderLongBreak().getValue());
            JFrameSettings.jLabelSettingsLongBreak.setText(JFrameTomatoMain.stringValorLongBreak);

        }
    }

    private class JSliderCiclosChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            String stringValorCiclos = String.valueOf("Quantidade de ciclos: " + getjSliderCiclos().getValue() + " ciclos");
            Tomato.setCiclosTime(getjSliderTomato().getValue());
            JFrameSettings.jLabelSettingsCiclos.setText(stringValorCiclos);

        }
    }
}
