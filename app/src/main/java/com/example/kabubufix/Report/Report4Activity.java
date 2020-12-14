package com.example.kabubufix.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.kabubufix.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Report4Activity extends AppCompatActivity {

    private CombinedChart combinedChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report4);

        combinedChart = findViewById(R.id.combineChart);

        final String[] labelTanggal = new String[]{

                " ", "1 Jul", "2 Jul", "3 Jul", "4 Jul", "5 Jul", "6 Jul", "7 Jul",
                "8 Jul", "9 Jul", "10 Jul", "11 Jul", "12 Jul", "13 Jul", "14 Jul",
                "15 Jul", "16 Jul", "17 Jul", "18 Jul", "19 Jul", "20 Jul", "21 Jul",
                "22 Jul", "23 Jul", "24 Jul", "25 Jul", "26 Jul", "27 Jul", "28 Jul",
                "29 Jul", "30 Jul", "31 Jul"
        };

        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{

                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labelTanggal[(int) value % labelTanggal.length];
            }
        });

        CombinedData data = new CombinedData();
        data.setData(dataMusik());
        data.setData(dataUser());

        combinedChart.setData(data);
        combinedChart.setScaleMinima(5,1);
        combinedChart.invalidate();
        combinedChart.animateXY(2000,2000);
        combinedChart.getDescription().setText("Data periode bulan Juli 2020");

    }

    private BarData dataMusik(){

        String mainColor = "#249EA0";

        ArrayList<BarEntry> musik = new ArrayList<>();
        musik.add(new BarEntry(0,0));
        musik.add(new BarEntry(1,2));
        musik.add(new BarEntry(2,4));
        musik.add(new BarEntry(3,4));
        musik.add(new BarEntry(4,3));
        musik.add(new BarEntry(5,4));
        musik.add(new BarEntry(6,5));
        musik.add(new BarEntry(7,2));
        musik.add(new BarEntry(8,3));
        musik.add(new BarEntry(9,1));
        musik.add(new BarEntry(10,5));
        musik.add(new BarEntry(11,3));
        musik.add(new BarEntry(12,4));
        musik.add(new BarEntry(13,5));
        musik.add(new BarEntry(14,4));
        musik.add(new BarEntry(15,6));
        musik.add(new BarEntry(16,6));
        musik.add(new BarEntry(17,5));
        musik.add(new BarEntry(18,4));
        musik.add(new BarEntry(19,3));
        musik.add(new BarEntry(20,6));
        musik.add(new BarEntry(21,5));
        musik.add(new BarEntry(22,4));
        musik.add(new BarEntry(23,2));
        musik.add(new BarEntry(24,1));
        musik.add(new BarEntry(25,7));
        musik.add(new BarEntry(26,0));
        musik.add(new BarEntry(27,0));
        musik.add(new BarEntry(28,0));
        musik.add(new BarEntry(29,0));
        musik.add(new BarEntry(30,0));
        musik.add(new BarEntry(31,0));

        BarDataSet barDataSet = new BarDataSet(musik,"Jumlah Musik Yang Dimainkan");
        barDataSet.setColor(Color.parseColor(mainColor));
        barDataSet.setValueTextSize(12f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.5f;

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(barWidth);
        barData.setValueFormatter(new Report4Activity.MyValueFormatted());

        return barData;

    }

    private LineData dataUser(){

        ArrayList<Entry> user = new ArrayList<>();
        user.add(new Entry(0f, 0f));
        user.add(new Entry(1f, 3f));
        user.add(new Entry(2f, 4f));
        user.add(new Entry(3f, 2f));
        user.add(new Entry(4f, 5f));
        user.add(new Entry(5f, 3f));
        user.add(new Entry(6f, 1f));
        user.add(new Entry(7f, 4f));

        user.add(new Entry(8f, 3f));
        user.add(new Entry(9f, 4f));
        user.add(new Entry(10f, 2f));
        user.add(new Entry(11f, 5f));
        user.add(new Entry(12f, 3f));
        user.add(new Entry(13f, 1f));
        user.add(new Entry(14f, 4f));

        user.add(new Entry(15f, 3f));
        user.add(new Entry(16f, 4f));
        user.add(new Entry(17f, 2f));
        user.add(new Entry(18f, 5f));
        user.add(new Entry(19f, 3f));
        user.add(new Entry(20f, 1f));
        user.add(new Entry(21f, 3f));

        user.add(new Entry(22f, 4f));
        user.add(new Entry(23f, 3f));
        user.add(new Entry(24f, 2f));
        user.add(new Entry(25f, 2f));
        user.add(new Entry(26f, 0f));
        user.add(new Entry(27f, 0f));
        user.add(new Entry(28f, 0f));


        user.add(new Entry(29f, 0f));
        user.add(new Entry(30f, 0f));
        user.add(new Entry(31f, 0f));

        LineDataSet lineDataSet = new LineDataSet(user, "Jumlah User");
        lineDataSet.setColor(Color.YELLOW);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new MyValueFormatted());
        return lineData;
    }

    private class MyValueFormatted extends IndexAxisValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            return ((int)value) + "";
        }
    }

}
