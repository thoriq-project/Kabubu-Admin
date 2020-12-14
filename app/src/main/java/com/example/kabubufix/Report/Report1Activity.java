package com.example.kabubufix.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.kabubufix.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class Report1Activity extends AppCompatActivity {


    private LineChart lineChart;
    private ArrayList<Entry> pengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report1);

        lineChart = findViewById(R.id.line_chart_view);

        final String[] labelTanggal = new String[]{

                " ", "1 Jul", "2 Jul", "3 Jul", "4 Jul", "5 Jul", "6 Jul", "7 Jul",
                "8 Jul", "9 Jul", "10 Jul", "11 Jul", "12 Jul", "13 Jul", "14 Jul",
                "15 Jul", "16 Jul", "17 Jul", "18 Jul", "19 Jul", "20 Jul", "21 Jul",
                "22 Jul", "23 Jul", "24 Jul", "25 Jul", "26 Jul", "27 Jul", "28 Jul",
                "29 Jul", "30 Jul", "31 Jul"
        };


        lineChart = findViewById(R.id.line_chart_view);
        pengguna = new ArrayList<>();
        pengguna.add(new Entry(0f, 0f));
        pengguna.add(new Entry(1f, 3f));
        pengguna.add(new Entry(2f, 4f));
        pengguna.add(new Entry(3f, 2f));
        pengguna.add(new Entry(4f, 5f));
        pengguna.add(new Entry(5f, 3f));
        pengguna.add(new Entry(6f, 1f));
        pengguna.add(new Entry(7f, 4f));

        pengguna.add(new Entry(8f, 3f));
        pengguna.add(new Entry(9f, 4f));
        pengguna.add(new Entry(10f, 2f));
        pengguna.add(new Entry(11f, 5f));
        pengguna.add(new Entry(12f, 3f));
        pengguna.add(new Entry(13f, 1f));
        pengguna.add(new Entry(14f, 4f));

        pengguna.add(new Entry(15f, 3f));
        pengguna.add(new Entry(16f, 4f));
        pengguna.add(new Entry(17f, 2f));
        pengguna.add(new Entry(18f, 5f));
        pengguna.add(new Entry(19f, 3f));
        pengguna.add(new Entry(20f, 1f));
        pengguna.add(new Entry(21f, 3f));

        pengguna.add(new Entry(22f, 4f));
        pengguna.add(new Entry(23f, 3f));
        pengguna.add(new Entry(24f, 2f));
        pengguna.add(new Entry(25f, 2f));
        pengguna.add(new Entry(26f, 0f));
        pengguna.add(new Entry(27f, 0f));
        pengguna.add(new Entry(28f, 0f));


        pengguna.add(new Entry(29f, 0f));
        pengguna.add(new Entry(30f, 0f));
        pengguna.add(new Entry(31f, 0f));

        LineDataSet lineDataSet = new LineDataSet(pengguna, " Jumlah Pengguna ");
        lineDataSet.setColor(Color.YELLOW);
        lineDataSet.setLineWidth(0f);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setCircleRadius(5f);
//        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setHighLightColor(Color.BLUE);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(15f);
//        lineDataSet.setGradientColor(Color.RED, Color.BLUE);
//        lineDataSet.getGradientColor().setStartColor(Color.RED);

//        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineDataSet.setDrawFilled(true);
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_line_chart);
            lineDataSet.setFillDrawable(drawable);
        } else
            lineDataSet.setFillColor(Color.BLACK);
//
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
//
//        YAxis yAxisLeft = lineChart.getAxisLeft();
//        yAxisLeft.setGranularity(1f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
//                return super.getAxisLabel(value, axis);
                return labelTanggal[(int) value % labelTanggal.length];
            }
        });



        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineData.setValueFormatter(new MyValueFormatted());

        lineChart.setData(lineData);
        lineChart.getDescription().setText("Data pengguna periode bulsn Juli 2020");
        lineChart.setDrawGridBackground(true);
        lineChart.animate();
        lineChart.setScaleMinima(5,1);
        lineChart.setGridBackgroundColor(Color.WHITE);
        lineChart.animateXY(2000, 2000);
        lineChart.getDescription().setTextColor(Color.WHITE);



//        lineChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        lineChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
//        lineChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        lineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        lineChart.getLegend().setWordWrapEnabled(true);
//        lineChart.getLegend().setDrawInside(false);
    }

    private class MyValueFormatted extends IndexAxisValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            return ((int)value) + "User";
        }
    }
}
