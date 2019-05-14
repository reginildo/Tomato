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
package io.github.reginildo.tomato.main;

import com.alee.laf.WebLookAndFeel;
import io.github.reginildo.tomato.view.JFrameTomatoMain;
import io.github.reginildo.tomato.view.SplashScreen;

import javax.swing.*;

public class Main {
    public static JFrameTomatoMain jFrameTomatoMain;
    public static WebLookAndFeel webLookAndFeel = new WebLookAndFeel();


    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndExit();
        jFrameTomatoMain = new JFrameTomatoMain();
        //setLookAndFeel(aplicationLookAndFeelMetal);
    }




}
