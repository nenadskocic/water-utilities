/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import de.toolforge.googlechartwrapper.BarChart;
import de.toolforge.googlechartwrapper.coder.*;
import de.toolforge.googlechartwrapper.data.BarChartDataSerie;
import de.toolforge.googlechartwrapper.data.DataScalingSet;
import de.toolforge.googlechartwrapper.label.*;
import de.toolforge.googlechartwrapper.style.BarWidthAndSpacing;
import java.awt.Color;
import java.awt.Dimension;
import de.toolforge.googlechartwrapper.label.ChartTitle;

/**
 *
 * @author Nenad
 */
@Named(value = "adminMainBean")
@SessionScoped
public class AdminMainBean implements Serializable {

    @PersistenceContext(unitName = "WaterUtilitiesPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    private String meterID;
    private String displayMeterID;
    private List<Integer> graphData;

    public String getDisplayMeterID() {
        return displayMeterID;
    }

    MeterDetailsModel detailsModel;
    private FeeType feeType = new FeeType();

    public FeeType getFeeType() {
        return feeType;
    }
    private List<MeterDetailsModel> meterDetailsModels = null;
    private List<MeterReading> meterReadings = null;
    private List<UserMeter> userMeters = null;

    public AdminMainBean() {
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public List<UserMeter> getUserMeterList() {
        List<UserMeter> meterReadingList = (em.createNamedQuery("UserMeter.findAll").getResultList());
        return meterReadingList;
    }

    private float amt = 0.0F;

    public String getMeterInformation(String meterID) {
        Query meterIdQ = em.createNamedQuery("MeterReading.findByMeterId", MeterReading.class);
        UserMeter userMeterUM = em.createNamedQuery("UserMeter.findByMeterId", UserMeter.class)
                .setParameter("meterId", meterID).getSingleResult();
        Query feeQ = em.createNamedQuery("FeeType.findAll", FeeType.class);

        this.meterReadings = meterIdQ.setParameter("meterId", userMeterUM).getResultList();
        this.meterDetailsModels = new LinkedList<>();
        this.feeType = (FeeType) feeQ.getSingleResult();

        displayMeterID = userMeterUM.getMeterId();

        int currentReading = 0;
        int prevReading = 0;

        for (MeterReading readingClass : this.meterReadings) {
            detailsModel = null;
            float amount;

            amt = 0;
            double bill = 0;

            currentReading = readingClass.getReading();

            if (prevReading == 0) {

                BigDecimal billBD = billTotal();
                bill = billBD.doubleValue();
                detailsModel = new MeterDetailsModel(readingClass.getReadingDate(),
                        readingClass.getReading(), 0.0, bill);
                //currentReading = readingClass.getReading();
                //prevReading = currentReading;
            } else {
                // currentReading = readingClass.getReading();
                amount = currentReading - prevReading;
                //prevReading = currentReading;
                amt = amount / 50.0F;
                BigDecimal billBD = billTotal();
                bill = billBD.doubleValue();

                detailsModel = new MeterDetailsModel(readingClass.getReadingDate(),
                        readingClass.getReading(), amt, bill);
            }

            prevReading = currentReading;
            meterDetailsModels.add(detailsModel);

        }
        return "getdata";
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    public float getAmt() {
        return amt;
    }

    public float lowAmount() {
        float totalAmount = getAmt();

        if (totalAmount == 0) {
            return 0;
        } else if (totalAmount < 5 && totalAmount >= 0) {
            return totalAmount;
        } else {
            return 5;
        }
    }

    public float medAmount() {
        float totalAmount = getAmt();

        if (totalAmount - 5 <= 0) {
            return 0;
        } else if (totalAmount - 5 < 25 && totalAmount - 5 > 0) {
            return totalAmount - 5;
        } else {
            return 25;
        }
    }

    public float highAmount() {
        float totalAmount = getAmt();

        if (totalAmount - 30 > 0) {
            return totalAmount - 30;
        } else {
            return 0;
        }
    }

    BigDecimal lowAmountCost, medAmountCost, 
            highAmountCost, subtotal, taxVal, billTotal;

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTaxVal() {
        return taxVal;
    }

    public BigDecimal getMedAmountCost() {
        return medAmountCost;
    }

    public BigDecimal getHighAmountCost() {
        return highAmountCost;
    }

    public BigDecimal lowAmountCost() {
        float f = this.lowAmount();
        lowAmountCost = new BigDecimal(Float.toString(f));
        return lowAmountCost.multiply(feeType.getLowRate());
    }

    public BigDecimal getLowAmountCost() {
        return lowAmountCost;
    }

    public BigDecimal medAmountCost() {
        float f = this.medAmount();
        medAmountCost = new BigDecimal(Float.toString(f));
        return medAmountCost.multiply(feeType.getMediumRate());
    }

    public BigDecimal highAmountCost() {
        float f = this.highAmount();
        highAmountCost = new BigDecimal(Float.toString(f));
        return highAmountCost.multiply(feeType.getHighRate());
    }

    public BigDecimal billSubtotal() {
        subtotal = this.lowAmountCost().add(this.medAmountCost())
                .add(this.highAmountCost()).add(feeType.getAdminFee());
        return subtotal;
    }

    public BigDecimal billTotal() {
        float tax = 1.13F;
        taxVal = new BigDecimal(Float.toString(tax));
        return this.billSubtotal().multiply(taxVal);
    }

    public List<MeterDetailsModel> getMeterDetailsModels() {
        return meterDetailsModels;
    }

    public void setMeterDetailsModels(List<MeterDetailsModel> meterDetailsModels) {
        this.meterDetailsModels = meterDetailsModels;
    }

    private String chartUrl = "";

    public String barChart() {
        graphData = new ArrayList<>();
        double maxAmount = 0;

        AxisLabelContainer x = new AxisLabelContainer(AxisType.XAxis);
        AxisLabelContainer y = new AxisLabelContainer(AxisType.YAxis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");

        int count = 0;
        for (int i = 0; i < meterDetailsModels.size(); i++) {
            if (i % 4 == 0) {
                x.addLabel(new AxisLabel(dateFormat.format(meterDetailsModels.get(i).getMeterDate())));
                count++;
            }
            graphData.add((int) meterDetailsModels.get(i).getAmountUsed());
            if (meterDetailsModels.get(i).getAmountUsed() > maxAmount) {
                maxAmount = meterDetailsModels.get(i).getAmountUsed();
            }
        }

        x.setAxisRange(new AxisRange(0, count - 1, 1));
        x.setAxisStyle(new AxisStyle(Color.black));
        x.getAxisStyle().setAlignment(AxisStyle.CENTER_ALIGN);

        int yMax = maxY(maxAmount, 100);
        int yInc = yMax / 10;
        y.setAxisRange(new AxisRange(0, yMax, yInc));
        BarChartDataSerie s = new BarChartDataSerie.BarChartDataSerieBuilder(graphData)
                .color(Color.red)
                .legend(new ChartLegend("Monthly Bill Amount"))
                .build();

        BarChart bc = new BarChart(new Dimension(850, 350), BarChart.BarChartOrientation.Horizontal, BarChart.BarChartStyle.Grouped);

        bc.setChartTitle(new ChartTitle("Bill Summary Chart for " + displayMeterID));
        bc.setEncoder(EncoderFactory.getEncoder(EncodingType.TextEncoding));

        bc.addBarChartDataSerie(s);

        DataScalingSet ds = new DataScalingSet(0, yMax);
        bc.addDataScalingSet(ds);

        bc.addAxisLabelContainer(x);
        bc.addAxisLabelContainer(y);

        AxisLabelContainer xl = new AxisLabelContainer(AxisType.XAxis);
        AxisLabel xlabel = new AxisLabel("Date");
        xlabel.setPos(50);
        xl.setUseLabelPositions(true);
        xl.addLabel(xlabel);
        xl.setAxisStyle(new AxisStyle(Color.black));
        bc.addAxisLabelContainer(xl);

        AxisLabelContainer yl = new AxisLabelContainer(AxisType.YAxis);
        AxisLabel ylabel = new AxisLabel("$");
        ylabel.setPos(50);
        yl.setUseLabelPositions(true);
        yl.addLabel(ylabel);
        yl.setAxisStyle(new AxisStyle(Color.black));
        bc.addAxisLabelContainer(yl);

        bc.setBarWidthAndSpacing(BarWidthAndSpacing.newAutomaticallyResize());
        bc.setAutoResizing(true);

        setChartUrl(bc.getUrl());
        return "summary_graph";
    }

    public String getChartUrl() {
        return chartUrl;
    }

    public void setChartUrl(String chartUrl) {
        this.chartUrl = chartUrl;
    }

    private int maxY(double max, int inc) {
        int yMax = inc;
        while (yMax < max) {
            yMax += inc;
        }
        return yMax;
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
