package com.sustainability;

import java.sql.Date;

public class FillPercentOverflow {
    private int fillPercent;
    private boolean overflow;


    public FillPercentOverflow(int fillPercent, boolean overflow) {
        this.fillPercent = fillPercent;
        this.overflow = overflow;
    }
    public int getFillPercent() {
        return fillPercent;
    }


    public boolean isOverflow() {
        return overflow;
    }

    public boolean convertOverflow(int bitFromDatabase) {
        if (bitFromDatabase == 1) {
            return true;
        } else if (bitFromDatabase == 0) {
            return false;
        }
        return false;
    }
}
