/*
 * The MIT License
 *
 * Copyright 2023 daviramos.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.lacamentohoraextra.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class RDate {

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public RDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public RDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        init(calendar);
    }

    public RDate(Calendar calendar) {
        init(calendar);
    }

    public RDate() {
        init(Calendar.getInstance());
    }

    public void init(Calendar calendar) {
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
    }

    private int day;
    private int month;
    private int year;

    public int compareTo(RDate date) {
        return toDate().compareTo(date.toDate());
    }

    @Override
    public String toString() {
        return year + "" + month + "" + day;
    }

    public boolean equals(RDate date) {
        return year == date.getYear() && month == date.getMonth() && day == date.getDay();
    }

    public boolean isBetweenOf(RDate from, RDate to) {
        Date date = toDate();
        return date.after(from.toDate()) && date.before(to.toDate());
    }

    public Date toDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String st = day + "-" + month + "-" + year;
        try {
            return df.parse(st);
        }
        catch (ParseException e) {
            throw new RuntimeException("Date format error");
        }
    }

    public RDate copy() {
        return new RDate(day, month, year);
    }
}
