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

public class DateChooserAction {

    public DateChooser getSource() {
        return source;
    }

    protected void setSource(DateChooser source) {
        this.source = source;
    }

    public boolean isDayChanged() {
        return dayChanged;
    }

    protected void setDayChanged(boolean dayChanged) {
        this.dayChanged = dayChanged;
    }

    public boolean isMonthChanged() {
        return monthChanged;
    }

    protected void setMonthChanged(boolean monthChanged) {
        this.monthChanged = monthChanged;
    }

    public boolean isYearChanged() {
        return yearChanged;
    }

    protected void setYearChanged(boolean yearChanged) {
        this.yearChanged = yearChanged;
    }

    public int getChangeType() {
        return changeType;
    }

    public DateChooserAction(int changeType) {
        this.changeType = changeType;
    }

    public static final int USER_SELECT = 1;
    public static final int METHOD_SET = 2;
    private final int changeType;
    private DateChooser source;
    boolean dayChanged;
    boolean monthChanged;
    boolean yearChanged;
}
