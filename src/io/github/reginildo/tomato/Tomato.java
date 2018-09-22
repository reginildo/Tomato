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

/**
 *
 * @author reginildo
 */
class Tomato{

    private int longBreak;
    private int pomodoro;
    private int shortBreak;

    Tomato(int pomodo, int shortBreak, int longBreak){
        this.longBreak = longBreak;
        this.pomodoro = pomodo;
        this.shortBreak = shortBreak;
    }

    int getLongBreak() {
        return longBreak;
    }

    void setLongBreak(int longBreak) {
        this.longBreak = longBreak;
    }

    int getPomodoro() {
        return pomodoro;
    }

    void setPomodoro(int pomodoro) {
        this.pomodoro = pomodoro;
    }

    int getShortBreak() {
        return shortBreak;
    }

    void setShortBreak(int shortBreak) {
        this.shortBreak = shortBreak;
    }


}
