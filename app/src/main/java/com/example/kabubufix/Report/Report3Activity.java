package com.example.kabubufix.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.kabubufix.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Report3Activity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report3);

        barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> kutipan = new ArrayList<>();
        kutipan.add(new BarEntry(1, 3));
        kutipan.add(new BarEntry(2, 1));
        kutipan.add(new BarEntry(3, 4));
        kutipan.add(new BarEntry(4, 2));
        kutipan.add(new BarEntry(5, 2));
        kutipan.add(new BarEntry(6, 1));
        kutipan.add(new BarEntry(7, 3));

        kutipan.add(new BarEntry(8, 3));
        kutipan.add(new BarEntry(9, 1));
        kutipan.add(new BarEntry(10, 4));
        kutipan.add(new BarEntry(11, 2));
        kutipan.add(new BarEntry(12, 2));
        kutipan.add(new BarEntry(13, 1));
        kutipan.add(new BarEntry(14, 3));

        kutipan.add(new BarEntry(15, 3));
        kutipan.add(new BarEntry(16, 2));
        kutipan.add(new BarEntry(17, 2));
        kutipan.add(new BarEntry(18, 3));
        kutipan.add(new BarEntry(19, 4));
        kutipan.add(new BarEntry(20, 5));
        kutipan.add(new BarEntry(21, 5));

        kutipan.add(new BarEntry(22, 3));
        kutipan.add(new BarEntry(23, 2));
        kutipan.add(new BarEntry(24, 5));
        kutipan.add(new BarEntry(25, 3));
        kutipan.add(new BarEntry(26, 0));
        kutipan.add(new BarEntry(27, null));
        kutipan.add(new BarEntry(28, null));

        kutipan.add(new BarEntry(29, null));
        kutipan.add(new BarEntry(30, null));
        kutipan.add(new BarEntry(31, null));

        ArrayList<BarEntry> puisi = new ArrayList<>();
        puisi.add(new BarEntry(1, 1));
        puisi.add(new BarEntry(2, 2));
        puisi.add(new BarEntry(3, 3));
        puisi.add(new BarEntry(4, 2));
        puisi.add(new BarEntry(5, 3));
        puisi.add(new BarEntry(6, 2));
        puisi.add(new BarEntry(7, 5));

        puisi.add(new BarEntry(8, 3));
        puisi.add(new BarEntry(9, 2));
        puisi.add(new BarEntry(10, 1));
        puisi.add(new BarEntry(11, 1));
        puisi.add(new BarEntry(12, 5));
        puisi.add(new BarEntry(13, 2));
        puisi.add(new BarEntry(14, 3));

        puisi.add(new BarEntry(15, 1));
        puisi.add(new BarEntry(16, 4));
        puisi.add(new BarEntry(17, 4));
        puisi.add(new BarEntry(18, 1));
        puisi.add(new BarEntry(19, 3));
        puisi.add(new BarEntry(20, 2));
        puisi.add(new BarEntry(21, 2));

        puisi.add(new BarEntry(22, 2));
        puisi.add(new BarEntry(23, 1));
        puisi.add(new BarEntry(24, 5));
        puisi.add(new BarEntry(25, 3));
        puisi.add(new BarEntry(26, null));
        puisi.add(new BarEntry(27, null));
        puisi.add(new BarEntry(28, null));

        puisi.add(new BarEntry(29, null));
        puisi.add(new BarEntry(30, null));
        puisi.add(new BarEntry(31, null));

        String mainColor = "#249EA0";
        final String[] labelTanggal = new String[]{

                " ", "1 Jul", "2 Jul", "3 Jul", "4 Jul", "5 Jul", "6 Jul", "7 Jul",
                "8 Jul", "9 Jul", "10 Jul", "11 Jul", "12 Jul", "13 Jul", "14 Jul",
                "15 Jul", "16 Jul", "17 Jul", "18 Jul", "19 Jul", "20 Jul", "21 Jul",
                "22 Jul", "23 Jul", "24 Jul", "25 Jul", "26 Jul", "27 Jul", "28 Jul",
                "29 Jul", "30 Jul", "31 Jul"
        };


        BarDataSet setKutipan = new BarDataSet(kutipan, "Kutipan");
        setKutipan.setColor(Color.parseColor(mainColor));
        setKutipan.setValueTextColor(Color.BLACK);
        setKutipan.setValueTextSize(10f);

        BarDataSet setPuisi = new BarDataSet(puisi, "Puisi");
        setPuisi.setColor(Color.YELLOW);
        setPuisi.setValueTextColor(Color.BLACK);
        setPuisi.setValueTextSize(10f);

        BarData barData = new BarData(setKutipan, setPuisi);
        barData.setBarWidth(0.25f);
        barData.setValueFormatter(new MyValueFormatted());

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);

        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labelTanggal[(int) value % labelTanggal.length];


            }
        });

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);




        barChart.setData(barData);
//        barChart.getBarData().setBarWidth(1);
        barChart.setFitBars(false);
        barChart.getDescription().setText("Data pengguna periode bulan Juli 2020");
        barChart.animateY(2000);
        barChart.setScaleMinima(5,1);
        barChart.groupBars(1f,0.5f, 0f);
        barChart.setDrawValueAboveBar(true);

        barChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        barChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        barChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        barChart.getLegend().setWordWrapEnabled(true);
        barChart.getLegend().setDrawInside(false);
        barChart.getLegend().setForm(Legend.LegendForm.SQUARE);



    }

    private class MyValueFormatted extends IndexAxisValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            return ((int)value) + "";
        }
    }

}
