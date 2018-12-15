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
package io.github.reginildo.tomato.utils;

public class Tomato {

    private static int defaultCiclosTime = 4;
    private static int defaultLongBreakTime = 15;
    private static int defaultPomodoroTime = 25;
    private static int defaultShortBreakTime = 5;
    private static int ciclosTime;
    private static int longBreakTime;
    private static int pomodoroTime;
    private static int shortBreakTime;

    public static int getDefaultLongBreakTime() {
        return defaultLongBreakTime;
    }

    public static void setDefaultLongBreakTime(int defaultLongBreakTime) {
        Tomato.defaultLongBreakTime = defaultLongBreakTime;
    }

    public static int getDefaultPomodoroTime() {
        return defaultPomodoroTime;
    }

    public static void setDefaultPomodoroTime(int defaultPomodoroTime) {
        Tomato.defaultPomodoroTime = defaultPomodoroTime;
    }

    public static int getDefaultShortBreakTime() {
        return defaultShortBreakTime;
    }

    public static void setDefaultShortBreakTime(int defaultShortBreakTime) {
        Tomato.defaultShortBreakTime = defaultShortBreakTime;
    }

    public Tomato(int pCiclosTime, int pPomodo, int pShortBreakTime, int pLongBreak) {
        setCiclosTime(pCiclosTime);
        setLongBreakTime(pLongBreak);
        setPomodoroTime(pPomodo);
        setShortBreakTime(pShortBreakTime);
    }

    public static int getLongBreakTime() {
        return longBreakTime;
    }

    public static void setLongBreakTime(int timeOfLongBreak) {
        longBreakTime = timeOfLongBreak;
    }

    public static int getPomodoroTime() {
        return pomodoroTime;
    }

    public static void setPomodoroTime(int timeOfPomodoro) {
        pomodoroTime = timeOfPomodoro;
    }

    public static int getShortBreakTime() {
        return shortBreakTime;
    }

    public static void setShortBreakTime(int timeOfShortBreak) {
        shortBreakTime = timeOfShortBreak;
    }

    public static int getDefaultCiclosTime() {
        return defaultCiclosTime;
    }

    public static void setDefaultCiclosTime(int timeOfCiclos) {
        defaultCiclosTime = timeOfCiclos;
    }

    public static int getCiclosTime() {
        return ciclosTime;
    }

    public static void setCiclosTime(int ciclosTime) {
        Tomato.ciclosTime = ciclosTime;
    }
}
