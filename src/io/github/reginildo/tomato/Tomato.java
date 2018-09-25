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
package io.github.reginildo.tomato;

class Tomato {

    private int longBreakTime;
    private int pomodoroTime;
    private int shortBreakTime;

    Tomato(int pomodo, int shortBreakTime, int longBreak) {
        setLongBreakTime(longBreak);
        setPomodoroTime(pomodo);
        setShortBreakTime(shortBreakTime);
    }

    int getLongBreakTime() {
        // return long break time in milliseconds
        return longBreakTime / 60 / 1000;
    }

    void setLongBreakTime(int longBreakTime) {
        // set long break time in milliseconds
        this.longBreakTime = longBreakTime * 60 * 1000;
    }

    int getPomodoroTime() {
        // return pomodoro time in milliseconds
        return pomodoroTime / 60 / 1000;
    }

    void setPomodoroTime(int pomodoroTime) {
        // set pomodoro time in milliseconds
        this.pomodoroTime = pomodoroTime * 60 * 1000;
    }

    int getShortBreakTime() {
        // return short break time in milliseconds
        return shortBreakTime / 60 / 1000;
    }

    void setShortBreakTime(int shortBreakTime) {
        // set short break time in milliseconds
        this.shortBreakTime = shortBreakTime * 60 * 1000;
    }


}
