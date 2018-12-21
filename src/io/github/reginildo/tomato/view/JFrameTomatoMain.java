/*
 * Copyright (C) 2018 Reginildo Sousa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.reginildo.tomato.view;

import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebRadioButtonMenuItem;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.tabbedpane.WebTabbedPane;
import io.github.reginildo.tomato.main.Main;
import io.github.reginildo.tomato.utils.Locales;
import io.github.reginildo.tomato.utils.Tomato;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jetbrains.annotations.Contract;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

/* JFrameTomatoMain.java
 * JFrame pricipal do aplicativo.
 * */

public final class JFrameTomatoMain extends WebFrame {
    static WebPanel webPanelMain = new WebPanel();
    static WebPanel webPanelSettings = new WebPanel();
    public static WebTabbedPane tabbedPane1;
    public WebPanel webPanelSuper = new WebPanel();
    static Tomato tomato = new Tomato(Tomato.getDefaultCiclosTime(),
            Tomato.getDefaultPomodoroTime(),
            Tomato.getDefaultShortBreakTime(),
            Tomato.getDefaultLongBreakTime());

    private Font fontTahoma = new Font("Tahoma", Font.PLAIN, 18);
    private Font fontArial = new Font("Arial", Font.BOLD, 85);
    private Font fontInfoSettings = new Font("Arial", Font.BOLD, 26);

    static WebLabel webLabelToImageIconSmileys, webLabelTimeCounterView, webLabelShowCiclos, webLabelFoco;
    private static boolean showTimeView;
    private static WebLabel webLabelHora;
    static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("io.github.reginildo.tomato/Labels", Locales.getLocaleDefault());
    private WebRadioButtonMenuItem radioButtonMenuItemPT_BR, radioButtonMenuItemEN_US, radioButtonMenuItemKlingon;
    private int confirmDialog;
    private int startOver;
    private static int countInterations = 1;
    private boolean timeToShortBreak;
    private boolean timeToLongBreak;
    private boolean timeToPomodoro;
    static Timer timer = null;
    static WebButton webButtonStart, webButtonPause, webButtonReset;
    private JMenuItem jMenuItemSettings, jMenuItemQuit, jMenuItemAbout;
    private static JFrameSettings jFrameSettings;
    static String stringValorLongBreak;
    static Calendar timerStart = Calendar.getInstance();
    static Date timerPause;
    static final SimpleDateFormat format = new SimpleDateFormat(
            "mm:ss");
    private JMenu jMenuFile, jMenuLanguage, jMenuHelp;
    private WebMenuBar jMenuBar;
    static WebPanel jPanelButtons, jPanelTomatoMain, jPanelTomatoInfo, jPanelDetails, jPanelTimer;
    private ImageIcon imageWork, imageEnjoy, imageSuccess, imagePrepared;
    private Player player;
    private static WebButton jButtonSalvarSettings;
    private JPanel jPanelSettings;
    private static WebSlider jSliderCiclos;
    private static WebSlider jSliderTomato;
    private static WebSlider jSliderLongBreak;
    private static WebSlider jSliderShortBreak;
    private static WebLabel jLabelSettingsCiclos, jLabelSettingsTomato, jLabelSettingsLongBreak, jLabelSettingsShortBreak;


    private static String stringIntervaloCurto =
            resourceBundle.getString("intervaloCurto");
    private static String stringIntervaloLongo = resourceBundle.getString("intervaloLongo");
    private static String stringValorShortBreak;
    private JLabel jLabelInfo = new JLabel("Ajuste os tempos:");

    public JFrameTomatoMain() {
        setJLabelsSettings();
        setJSliderSettings();
        setJButtonSairSettings();
        setJPanelsSettings();
        // todo é aqui que tem que mudar
        //setContentPane(jPanelSettings);
        invokeAndShow();
        jFrameSettings = new JFrameSettings();
        initThreadHour();
        setInitTimerStart();
        setImageIcons();
        setJLabels();
        setWebPanels();
        setThisJMenuBar();
        setJMenuBar(jMenuBar);
        setContentPane(webPanelSuper);

        setTitle("Pomodoro tempo");
        setSize(300, 460);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setShowTimeView(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void invokeAndShow() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    //UIManager.setLookAndFeel(new MetalLookAndFeel());
                    UIManager.setLookAndFeel(Main.webLookAndFeel);
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setThisJMenuBar() {
        setJMenus();
        jMenuBar = new WebMenuBar();
        jMenuBar.setMenuBarStyle(MenuBarStyle.standalone);
        jMenuBar.setBackground(Color.orange);
        jMenuBar.setBorderPainted(false);
        jMenuBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuLanguage);
        jMenuBar.add(jMenuHelp);
    }

    private void setJLabels() {
        webLabelHora = new WebLabel();
        webLabelToImageIconSmileys = new WebLabel();
        webLabelToImageIconSmileys.setIcon(getImagePrepared());
        webLabelToImageIconSmileys.setVisible(true);
        webLabelTimeCounterView = new WebLabel("00:00");
        webLabelTimeCounterView.setDrawShade(true);
        webLabelTimeCounterView.setFont(fontArial);
        webLabelTimeCounterView.setText(format.format(timerStart.getTime()));
        webLabelTimeCounterView.setForeground(new Color(217, 133, 12));
        webLabelTimeCounterView.setShadeColor(Color.BLACK);
        webLabelShowCiclos = new WebLabel();
        webLabelShowCiclos.setFont(fontTahoma);
        webLabelShowCiclos.setVisible(false);
        webLabelFoco = new WebLabel("Mantenha o foco!");
        webLabelFoco.setFont(fontTahoma);
        webLabelFoco.setVisible(false);
    }

    private void setWebPanels() {
        setWebButtons();
        jPanelButtons = new WebPanel(new FlowLayout());
        jPanelButtons.setUndecorated(false);
        jPanelButtons.setMargin(10, 10, 10, 10);
        jPanelButtons.setRound(StyleConstants.largeRound);
        jPanelButtons.add(webButtonStart);
        jPanelButtons.add(webButtonPause);
        jPanelButtons.add(webButtonReset);

        jPanelTomatoInfo = new WebPanel();
        jPanelTomatoInfo.setLayout(new BoxLayout(jPanelTomatoInfo, BoxLayout.Y_AXIS));
        jPanelDetails = new WebPanel();
        jPanelDetails.setForeground(Color.darkGray);
        //jPanelDetails.setLayout(new GridBagLayout());
        jPanelDetails.setLayout(new GridLayout());
        //jPanelDetails.setLayout(new BoxLayout(jPanelDetails, BoxLayout.Y_AXIS));
        jPanelTimer = new WebPanel();
        jPanelTimer.add(webLabelTimeCounterView);
        jPanelTimer.setUndecorated(false);
        jPanelTimer.setMargin(2, 10, 10, 10);
        jPanelTimer.setRound(StyleConstants.decorationRound);

        jPanelDetails.add(webLabelShowCiclos);
        jPanelDetails.add(webLabelFoco);
        jPanelDetails.add(webLabelHora);

        jPanelTomatoInfo.add(webLabelToImageIconSmileys);

        jPanelTomatoMain = new WebPanel(new FlowLayout());
        jPanelTomatoMain.setUndecorated(false);
        jPanelTomatoMain.setMargin(10, 20, 20, 20);
        jPanelTomatoMain.setRound(StyleConstants.bigRound);
        jPanelTomatoMain.add(jPanelTimer);
        jPanelTomatoMain.add(jPanelButtons);
        jPanelTomatoMain.add(jPanelTomatoInfo);
        jPanelTomatoMain.add(jPanelDetails);
        webPanelMain.add(jPanelTomatoMain);
        webPanelSettings.add(jPanelSettings);
        webPanelSuper.add(getPreview());


    }

    @Contract(pure = true)
    private static boolean isShowTimeView() {
        return showTimeView;
    }

    /**
     * @param timeView For to see a label with the date and time now
     */
    static void setShowTimeView(boolean timeView) {
        JFrameTomatoMain.showTimeView = timeView;
    }

    private static void initThreadHour() {
        new Thread(new HoraThread()).start();
    }

    private void setImageIcons() {
        imageWork = new ImageIcon(getClass().getResource(
                "/io/github/reginildo/" +
                        "tomato/images/icon_smiley_work.png"));
        imageEnjoy = new ImageIcon(getClass().getResource(
                "/io/github/reginildo/" +
                        "tomato/images/icon_smiley_enjoy.png"));
        imageSuccess = new ImageIcon(getClass().getResource(
                "/io/github/reginildo/" +
                        "tomato/images/icon_smiley_sucess.png"));
        imagePrepared = new ImageIcon(getClass().getResource(
                "/io/github/reginildo/" +
                        "tomato/images/icon_smiley_prepared.png"));
    }

    private void setInitTimerStart() {
        timerStart.set(Calendar.MINUTE, 25);
        timerStart.set(Calendar.SECOND, 0);
    }

    private void setWebButtons() {
        webButtonStart = new WebButton(resourceBundle.getString("buttonStart"));
        webButtonStart.addActionListener(new JButtonStartListener());
        webButtonStart.setMnemonic(KeyEvent.VK_S);
        webButtonStart.addMouseMotionListener(new JButtonStartMotionListener());
        //webButtonStart.setEnabled(true);

        webButtonPause = new WebButton(resourceBundle.getString("buttonPause"));
        webButtonPause.addActionListener(new JButtonPauseListener());
        webButtonPause.setMnemonic(KeyEvent.VK_P);
        webButtonPause.addMouseMotionListener(new JButtonPauseMotionListener());

        webButtonReset = new WebButton(resourceBundle.getString("buttonReset"));
        webButtonReset.addActionListener(new JButtonResetListener());
        webButtonReset.setMnemonic(KeyEvent.VK_R);
        webButtonReset.addMouseMotionListener(new JButtonResetMotionListener());
        webButtonPause.setEnabled(false);
        webButtonReset.setEnabled(false);
    }

    private void setJMenuItens() {
        jMenuItemSettings = new JMenuItem(resourceBundle.getString("menuItemSetting"));
        jMenuItemSettings.addMouseMotionListener(new JMenuItemSettingsMotionListener());
        jMenuItemSettings.addActionListener(new JMenuItemSettingsListener());
        jMenuItemSettings.setMnemonic(KeyEvent.VK_E);

        jMenuItemQuit = new JMenuItem("Quit");
        jMenuItemQuit.addActionListener(new JMenuItemQuitListener());
        jMenuItemQuit.addMouseMotionListener(new JMenuItemQuitMotionListener());
        jMenuItemQuit.setMnemonic(KeyEvent.VK_Q);

        jMenuItemAbout = new JMenuItem("About");
        jMenuItemAbout.addActionListener(new JMenuItemAboutListener());
    }

    private void refreshLanguage() {
        setVisible(false);
        repaint();
        setVisible(true);
        //Swing
    }

    private void setButtonGroupLanguages() {
        setJRadioButtonMenuItens();
        ButtonGroup buttonGroupLanguages = new ButtonGroup();
        buttonGroupLanguages.add(radioButtonMenuItemPT_BR);
        buttonGroupLanguages.add(radioButtonMenuItemEN_US);
        buttonGroupLanguages.add(radioButtonMenuItemKlingon);
    }

    private void setJRadioButtonMenuItens() {
        radioButtonMenuItemPT_BR =
                new WebRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemPT_BR"));
        radioButtonMenuItemEN_US =
                new WebRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemEN_US"));
        radioButtonMenuItemKlingon =
                new WebRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemKlingon"));
        radioButtonMenuItemPT_BR.addActionListener(new RadioButtonMenuItemPT_BRListener());
        radioButtonMenuItemEN_US.addActionListener(new RadioButtonMenuItemEN_USListener());
        radioButtonMenuItemKlingon.addActionListener(new RadioButtonMenuItemKlingonListener());
    }

    private void setJMenus() {
        setButtonGroupLanguages();
        setJMenuItens();
        jMenuFile = new JMenu(resourceBundle
                .getString("menuFile"));
        jMenuLanguage = new JMenu(resourceBundle
                .getString("menuLanguage"));
        jMenuLanguage.add(radioButtonMenuItemPT_BR);
        jMenuLanguage.add(radioButtonMenuItemEN_US);
        jMenuLanguage.add(radioButtonMenuItemKlingon);
        jMenuFile.setMnemonic(KeyEvent.VK_F);
        jMenuHelp = new JMenu("Help");
        jMenuHelp.add(jMenuItemAbout);
        jMenuFile.add(jMenuItemSettings);
        jMenuFile.add(jMenuItemQuit);
    }

    private void startPomodoroTimer() {
        if (timerPause == null) {
            if (jFrameSettings != null) {
                timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getDefaultPomodoroTime());
            }
            timerStart.set(Calendar.SECOND, 0);
        } else {
            timerStart.setTime(timerPause);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTaskPomodoro(), 0, 1000);
    }

    private static void playAlarm() {
        Toolkit.getDefaultToolkit().beep();
    }

    private void setTimerStartAndTimeCounterView() {
        try {
            timerStart.set(Calendar.SECOND, (
                    timerStart.get(Calendar.SECOND) - 1));
            webLabelTimeCounterView.setText(format.format(timerStart.getTime()));
            webLabelShowCiclos.setText("Ainda faltam " + Tomato.getCiclosTime() + " ciclos");
            webLabelShowCiclos.setVisible(true);
            webLabelFoco.setVisible(true);
            if (isTheLastOneCiclo()) {
                webLabelShowCiclos.setText("É o ultimo! Continue!");
            }
            if (isTimeToShortBreak() || isTimeToLongBreak()) {
                webLabelShowCiclos.setVisible(false);
                webLabelFoco.setVisible(false);
            } else {
                webLabelShowCiclos.setVisible(true);
                webLabelFoco.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isTimeToShortBreak() {
        if (countInterations == 2 ||
                countInterations == 4 ||
                countInterations == 6) {
            timeToShortBreak = true;
        }
        return timeToShortBreak;
    }

    private boolean isTimeToLongBreak() {
        if (countInterations >= 8) {
            timeToLongBreak = true;
        }
        return timeToLongBreak;
    }

    private boolean isTimeToPomodoro() {
        if (countInterations == 1 ||
                countInterations == 3 ||
                countInterations == 5 ||
                countInterations == 7) {
            timeToPomodoro = true;
        }
        return timeToPomodoro;
    }

    private void startShortBreakTimer() {
        if (timerPause == null) {
            if (jFrameSettings != null) {
                timerStart.set(Calendar.MINUTE, Tomato.getShortBreakTime());
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getDefaultShortBreakTime());
            }
            timerStart.set(Calendar.SECOND, 0);
        } else {
            timerStart.setTime(timerPause);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTaskShortBreak(), 0, 1000);
    }

    private void startLongBreakTimer() {
        if (timerPause == null) {
            if (jFrameSettings != null) {
                timerStart.set(Calendar.MINUTE, Tomato.getLongBreakTime());
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getDefaultLongBreakTime());
            }
            timerStart.set(Calendar.SECOND, 0);
        } else {
            timerStart.setTime(timerPause);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTaskLongBreak(), 0, 1000);
    }

    private boolean isTheEnd() {
        return ((timerStart.get(Calendar.MINUTE) == 0) && (timerStart.get(Calendar.SECOND) == 0));
    }

    @Contract(pure = true)
    private ImageIcon getImageWork() {
        return imageWork;
    }

    @Contract(pure = true)
    private ImageIcon getImageEnjoy() {
        return imageEnjoy;
    }

    @Contract(pure = true)
    private ImageIcon getImageSuccess() {
        return imageSuccess;
    }

    @Contract(pure = true)
    ImageIcon getImagePrepared() {
        return imagePrepared;
    }

    private void setTimeToShortBreak(boolean timeShortBreak) {
        this.timeToShortBreak = timeShortBreak;
    }

    private void setTimeToLongBreak(boolean timeLongBreak) {
        this.timeToLongBreak = timeLongBreak;
    }

    private void setTimeToPomodoro(boolean timePomodoro) {
        this.timeToPomodoro = timePomodoro;
    }

    public Component getPreview() {
        setupTabbedPane();
        return new GroupPanel(1, false, tabbedPane1);
    }

    private static void setupTabbedPane() {
        tabbedPane1 = new WebTabbedPane();
        tabbedPane1.setPreferredSize(new Dimension(290, 450));
        tabbedPane1.setTabPlacement(WebTabbedPane.TOP);
        webPanelSettings.setBackground(new Color ( 255, 212, 161 ));
        tabbedPane1.addTab("Pomodoro",webPanelMain);
        tabbedPane1.addTab("Set the Timer",webPanelSettings);
        tabbedPane1.setBackgroundAt ( 1, new Color ( 255, 212, 161 ) );


    }

    /* metodos settings*/


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

    private void setJButtonSairSettings() {
        jButtonSalvarSettings = new WebButton(resourceBundle.getString("buttonSave"));
        jButtonSalvarSettings.setMnemonic(KeyEvent.VK_S);
        jButtonSalvarSettings.addActionListener(new JButtonSalvarSettingsLintener());
        jButtonSalvarSettings.addMouseMotionListener(new JButtonSairSettingsMotionListener());
    }

    private void setButtonsOnExit() {
        JFrameTomatoMain.webButtonStart.setText(JFrameTomatoMain.resourceBundle.getString("buttonStart"));
        JFrameTomatoMain.webButtonPause.setEnabled(false);
        JFrameTomatoMain.webButtonStart.setEnabled(true);
        JFrameTomatoMain.webButtonReset.setEnabled(false);
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

    private void setJPanelsSettings() {
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
        jPanelSettings.add(jButtonSalvarSettings);
    }

    private void setJSliderSettings() {
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

    private void setJLabelsSettings() {
        jLabelSettingsCiclos = new WebLabel();
        jLabelSettingsTomato = new WebLabel(resourceBundle.getString("stringTempoPomodoro"));
        jLabelSettingsLongBreak = new WebLabel(
                resourceBundle.getString("intervaloLongo"));
        jLabelSettingsShortBreak = new WebLabel(
                resourceBundle.getString("intervaloCurto"));
        jLabelInfo.setFont(fontInfoSettings);
    }

    private class JButtonSalvarSettingsLintener implements ActionListener {
        //todo nao permitir valores zerados

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((jSliderTomato.getValue() == 0) ||
                    (getjSliderLongBreak().getValue() == 0)
                    || (getjSliderShortBreak().getValue() == 0)) {
                JOptionPane.showMessageDialog(null, "Insira algum valor acima de zero.");

            } else {
                setTomatoTimeValues();
                JFrameTomatoMain.webLabelToImageIconSmileys.setIcon(Main.jFrameTomatoMain.getImagePrepared());
                setTimeStartValue();
                JFrameTomatoMain.timerPause = null;
                if (JFrameTomatoMain.timer != null) {
                    JFrameTomatoMain.timer.cancel();
                }
                setButtonsOnExit();
                JFrameTomatoMain.jPanelDetails.setVisible(false);
                JFrameTomatoMain.webLabelTimeCounterView.setText(
                        JFrameTomatoMain.format.format(JFrameTomatoMain.timerStart.getTime()));
            }

        }
    }

    private class JButtonSairSettingsMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            jButtonSalvarSettings.setToolTipText(
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
            getjSliderTomato().setToolTipText(String.valueOf(
                    getjSliderTomato().getValue() + " " +
                            resourceBundle.getString("minutos")));

        }
    }

    private class JSliderTomatoChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(getjSliderTomato().getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setPomodoroTime(getjSliderTomato().getValue());
            jLabelSettingsTomato.setText(valorTomato);
        }
    }

    private class JSliderShortBreakChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(getjSliderShortBreak().getValue() + " " +
                    resourceBundle.getString("minutos"));
            Tomato.setShortBreakTime(getjSliderShortBreak().getValue());
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        }
    }

    private class JSliderLongBreakChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JFrameTomatoMain.stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(getjSliderLongBreak().getValue()
                    + " " + resourceBundle.getString("minutos"));
            Tomato.setLongBreakTime(getjSliderLongBreak().getValue());
            jLabelSettingsLongBreak.setText(JFrameTomatoMain.stringValorLongBreak);

        }
    }

    private class JSliderCiclosChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            String stringValorCiclos = String.valueOf("Quantidade de ciclos: " + getjSliderCiclos().getValue() + " ciclos");
            Tomato.setCiclosTime(getjSliderTomato().getValue());
            jLabelSettingsCiclos.setText(stringValorCiclos);

        }
    }

    private class JButtonPauseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (webButtonPause.isEnabled()) {
                timerPause = timerStart.getTime();
                timer.cancel();
                webButtonPause.setEnabled(false);
                webButtonReset.setEnabled(true);
                webButtonStart.setEnabled(true);
            }
        }
    }

    private class JButtonStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            TickTackThread tickTackThread = new TickTackThread();
//            tickTackThread.start();
            if (webButtonStart.isEnabled()) {
                Tomato.setPomodoroTime(timerStart.get(Calendar.MINUTE));
                webLabelToImageIconSmileys.setIcon(getImageWork());
                webButtonStart.setEnabled(false);
                webButtonPause.setEnabled(true);
                jPanelDetails.setVisible(true);
                startPomodoroTimer();
            }
        }
    }

    private class JButtonResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            webLabelToImageIconSmileys.setIcon(getImagePrepared());
            if (isTimeToShortBreak()) {
                timerStart.set(Calendar.MINUTE, Tomato.getShortBreakTime());
            } else if (isTimeToLongBreak()) {
                timerStart.set(Calendar.MINUTE, Tomato.getLongBreakTime());
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
            }
            webLabelShowCiclos.setVisible(false);
            webLabelFoco.setVisible(false);
            timerStart.set(Calendar.SECOND, 0);
            timerPause = null;
            timer.cancel();
            webButtonStart.setText(resourceBundle.getString("buttonStart"));
            webButtonPause.setEnabled(false);
            webButtonStart.setEnabled(true);
            webButtonReset.setEnabled(false);
            webLabelTimeCounterView.setText(format.format(
                    timerStart.getTime()));
        }
    }

    private class JMenuItemSettingsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //jFrameSettings.setVisible(true);

        }
    }

    private class JMenuItemQuitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class JMenuItemAboutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new JFrameAbout();
        }
    }

    private class RadioButtonMenuItemPT_BRListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resourceBundle = ResourceBundle.
                    getBundle("io.github.reginildo.tomato/Labels",
                            Locales.getLocale_pt_BR());
            resourceBundle.keySet();
            refreshLanguage();
        }
    }

    private class RadioButtonMenuItemEN_USListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resourceBundle = ResourceBundle.
                    getBundle("io.github.reginildo.tomato/Labels", Locales.getLocale_en_US());
            resourceBundle.keySet();
            refreshLanguage();
        }
    }

    private class RadioButtonMenuItemKlingonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resourceBundle = ResourceBundle.
                    getBundle("io.github.reginildo.tomato/Labels",
                            Locales.getLocale_tlh());
            resourceBundle.keySet();
            refreshLanguage();
        }
    }

    private class JMenuItemSettingsMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            jMenuFile.setToolTipText("Configurar intervalos de tempo");
        }
    }

    private class JButtonStartMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            webButtonStart.setToolTipText("Iniciar pomodoro");
        }
    }

    private class JButtonPauseMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            webButtonPause.setToolTipText("Pausa pomodoro");
        }
    }

    private class JButtonResetMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            webButtonReset.setToolTipText("Reiniciando");
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            webButtonReset.setToolTipText("Reiniciar pomodoro");
        }
    }

    private class JMenuItemQuitMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            jMenuItemQuit.setToolTipText("Quit the application");
        }
    }

    private static class HoraThread implements Runnable {
        private static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);

        @Override
        public void run() {
            while (JFrameTomatoMain.isShowTimeView()) {
                JFrameTomatoMain.webLabelHora.setLayout(new FlowLayout(FlowLayout.TRAILING));
                JFrameTomatoMain.webLabelHora.setText(dateFormat.format(new Date()));
                JFrameTomatoMain.webLabelHora.setForeground(Color.DARK_GRAY);
            }
        }
    }

    private class TimerTaskPomodoro extends TimerTask {
        @Override
        public void run() {
            setTimerStartAndTimeCounterView();
            if (isTheEnd()) {
                RingThread ringThread = new RingThread();
                ringThread.start();
                countInterations++;
                timer.cancel();
                if (isTheLastOneCiclo()) {
                    startOver = WebOptionPane.showConfirmDialog(null,
                            "\nParabéns!!! Você concluiu! \niniciar um novo ciclo?");
                    if (startOver == WebOptionPane.YES_OPTION) {
                        player.close();
                        Tomato.setCiclosTime(JFrameSettings.getjSliderCiclos().getValue());
                        Tomato.setLongBreakTime(JFrameSettings.getjSliderLongBreak().getValue());
                        Tomato.setShortBreakTime(JFrameSettings.getjSliderShortBreak().getValue());
                        JFrameTomatoMain.countInterations = 1;
                        startPomodoroTimer();
                    } else {
                        player.close();
                    }
                } else {
                    confirmDialog = WebOptionPane.showConfirmDialog(
                            null, "Hora de relaxar.\nIniciar o intervalo curto?");
                    if (confirmDialog == WebOptionPane.YES_OPTION && (isTimeToShortBreak())) {
                        player.close();
                        webLabelToImageIconSmileys.setIcon(getImageEnjoy());
                        Tomato.setCiclosTime(Tomato.getCiclosTime() - 1);
                        setTimeToLongBreak(false);
                        setTimeToPomodoro(false);
                        startShortBreakTimer();
                    } else if (confirmDialog == WebOptionPane.YES_OPTION && (isTimeToLongBreak())) {
                        player.close();
                        webLabelToImageIconSmileys.setIcon(getImageSuccess());
                        setTimeToShortBreak(false);
                        setTimeToPomodoro(false);
                        startLongBreakTimer();
                    } else {
                        player.close();
                    }
                }
            }
        }
    }

    @Contract(pure = true)
    private boolean isTheLastOneCiclo() {
        return Tomato.getCiclosTime() == 1;
    }

    private class TimerTaskShortBreak extends TimerTask {
        @Override
        public void run() {
            setTimerStartAndTimeCounterView();
            if (isTheEnd()) {
                RingThread ringThread = new RingThread();
                ringThread.start();
                //playAlarm();
                countInterations++;
                timer.cancel();
                confirmDialog = WebOptionPane.showConfirmDialog(
                        null, "Fim do short break.\nIniciar o novo pomodoro?");
                if (confirmDialog == WebOptionPane.YES_OPTION && (isTimeToPomodoro())) {
                    player.close();
                    webLabelToImageIconSmileys.setIcon(getImageWork());
                    setTimeToShortBreak(false);
                    setTimeToLongBreak(false);
                    startPomodoroTimer();
                } else if (confirmDialog == WebOptionPane.YES_OPTION && (isTimeToLongBreak())) {
                    player.close();
                    webLabelToImageIconSmileys.setIcon(getImageSuccess());
                    setTimeToShortBreak(false);
                    setTimeToPomodoro(false);
                    startLongBreakTimer();
                }
            }
        }
    }

    private class TimerTaskLongBreak extends TimerTask {
        @Override
        public void run() {
            setTimerStartAndTimeCounterView();
            if (isTheEnd()) {
                RingThread ringThread = new RingThread();
                ringThread.start();
                //playAlarm();
                countInterations++;
                timer.cancel();
                confirmDialog = WebOptionPane.showConfirmDialog(
                        null, "Fim do Long Break.\nIniciar um novo ciclo?");
                if (confirmDialog == WebOptionPane.YES_OPTION) {
                    player.close();
                    webLabelToImageIconSmileys.setIcon(getImageWork());
                    countInterations = 1;
                    setTimeToLongBreak(false);
                    setTimeToShortBreak(false);
                    startPomodoroTimer();
                }
            }
        }
    }

    class TickTackThread extends Thread {
        public void run() {
            try {
                String audioTickTack = "ticking-noise.mp3";
                InputStream inputStream = this.getClass().getResourceAsStream(
                        "/io/github/reginildo/tomato/audio/" + audioTickTack);
                player = new Player(inputStream);
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    class RingThread extends Thread {
        public void run() {
            try {
                String audioRing = "clock-ringing.mp3";
                InputStream inputStream = this.getClass().getResourceAsStream(
                        "/io/github/reginildo/tomato/audio/" + audioRing);
                player = new Player(inputStream);
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }
}
