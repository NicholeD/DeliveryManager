package com.kenzie.immutabletime;

public final class ImmutableTime {

    public static final int MAX_MINUTES_IN_HOUR = 60;
    public static final int MAX_HOURS_IN_DAY = 24;
    public static final int MIN_MINUTES_IN_HOUR = 0;
    public static final int MIN_HOURS_IN_DAY = 0;

    private final int hour;
    private final int minute;

    public ImmutableTime(int hour, int minute) {
        if (hour > (MAX_HOURS_IN_DAY - 1) || hour < MIN_HOURS_IN_DAY ||
                minute < MIN_MINUTES_IN_HOUR || minute > (MAX_MINUTES_IN_HOUR - 1)) {
            throw new IllegalArgumentException();
        }
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
            return minute;
    }

    public ImmutableTime plusMinutes(int minutesToAdd) {
       Integer newHour = hour;
       Integer newMinute = minute + minutesToAdd;
        Integer minsToHrs = newMinute/MAX_MINUTES_IN_HOUR;
        Integer minsRemaining = newMinute % MAX_MINUTES_IN_HOUR;

        if (newMinute > MAX_MINUTES_IN_HOUR) {
            newMinute = minsRemaining;
            if ((newHour + minsToHrs) > MAX_HOURS_IN_DAY) {
                newHour = (newHour + minsToHrs) - MAX_HOURS_IN_DAY;
            } else {
                newHour = newHour + minsToHrs;
            }
        }

        return new ImmutableTime(newHour, newMinute);
    }

    public ImmutableTime plusHours(int hoursToAdd) {
        Integer newHour = hour + hoursToAdd;
        Integer newMinute = minute;

        if (newHour > MAX_HOURS_IN_DAY) {
            newHour = newHour - MAX_HOURS_IN_DAY;
        }

        return new ImmutableTime(newHour, newMinute);
    }

    public ImmutableTime minusMinutes(int minutesToSubtract) {
        Integer newHour = hour;
        Integer newMinute = minute;
        Integer minsToHrs = minutesToSubtract/MAX_MINUTES_IN_HOUR;
        Integer minsRemaining = minutesToSubtract % MAX_MINUTES_IN_HOUR;

        if (minutesToSubtract > MAX_MINUTES_IN_HOUR) {
            newHour = newHour - minsToHrs;
            if (newMinute < minsRemaining) {
                newHour--;
                newMinute = MAX_MINUTES_IN_HOUR - (minsRemaining - newMinute);
            } else {
                newMinute = newMinute - minsRemaining;
            }
        } else if (minutesToSubtract > newMinute){
            newHour--;
            newMinute = MAX_MINUTES_IN_HOUR - (minutesToSubtract - newMinute);
        } else {
            newMinute = newMinute - minutesToSubtract;
        }
        return new ImmutableTime(newHour, newMinute);
    }

    public ImmutableTime minusHours (int hoursToSubtract) {
        int newHour = hour;
        int newMinute = minute;
        if (hoursToSubtract > MAX_HOURS_IN_DAY) {
            newHour = hoursToSubtract % MAX_HOURS_IN_DAY;
        } else if (hoursToSubtract > newHour) {
            newHour = MAX_HOURS_IN_DAY - (hoursToSubtract - newHour);
        } else {
            newHour = newHour - hoursToSubtract;
        }
        return new ImmutableTime(newHour, newMinute);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(hour < 10 ? "0" : "").append(hour);
        buf.append(minute < 10 ? ":0" : ":").append(minute);
        return buf.toString();
    }
}