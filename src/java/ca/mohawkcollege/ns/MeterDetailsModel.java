/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import java.util.Date;

/**
 *
 * @author Nenad
 */
public class MeterDetailsModel {

    private Date meterDate;
    private int meterReading;
    private double amountUsed;
    private double billTotal;

    public Date getMeterDate() {
        return meterDate;
    }

    public void setMeterDate(Date meterDate) {
        this.meterDate = meterDate;
    }

    public int getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(int meterReading) {
        this.meterReading = meterReading;
    }

    public double getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(double amountUsed) {
        this.amountUsed = amountUsed;
    }

    public double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(double billTotal) {
        this.billTotal = billTotal;
    }

    public MeterDetailsModel(Date meterDate, int meterReading, double amountUsed, double billTotal) {
        this.meterDate = meterDate;
        this.meterReading = meterReading;
        this.amountUsed = amountUsed;
        this.billTotal = billTotal;
    }
}
