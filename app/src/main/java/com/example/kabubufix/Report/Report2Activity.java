package com.example.kabubufix.Report;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.kabubufix.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Report2Activity extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);

        pieChart = findViewById(R.id.pieChartView);

        ArrayList<PieEntry> budayawan = new ArrayList<>();
        budayawan.add(new PieEntry(32, "Emha Ainun Nadjib"));
        budayawan.add(new PieEntry(40, "Sujiwo Tejo"));
        budayawan.add(new PieEntry(10, "Gus Mus"));
        budayawan.add(new PieEntry(12, "Gus Dur"));
        budayawan.add(new PieEntry(11, "Nurcholis Majid"));
        budayawan.add(new PieEntry(21, "Umar Kayam"));
        budayawan.add(new PieEntry(9, "Sutardji Calzoum Bachri"));
        budayawan.add(new PieEntry(20, "W S Rendra"));
        budayawan.add(new PieEntry(12, "Ridwan Saidi"));
        budayawan.add(new PieEntry(19, "Taufik Ismail"));
        budayawan.add(new PieEntry(25, "Frans Magniz Suseno"));

        PieDataSet pieDataSet = new PieDataSet(budayawan, " ");
        pieDataSet.setColors(ColorTemplate.createColors(WARNA_PASTEL));
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(13);
        pieDataSet.setValueLinePart1OffsetPercentage(20.f);
        pieDataSet.setValueLinePart1Length(10f);
        pieDataSet.setValueLinePart2Length(10f);
        pieDataSet.setValueLineWidth(2);
        pieDataSet.setValueLineColor(Color.RED);
        pieDataSet.setUsingSliceColorAsValueLineColor(true);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new MyValueFormatted());

        pieChart.setData(pieData);
        pieChart.setCenterText("Dari total 211 \n event pencarian");
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setDrawCenterText(true);
        pieChart.animate();
        pieChart.animateX(2000);
        pieChart.animateY(2000);
        pieChart.setHoleRadius(30);
        pieChart.setTransparentCircleRadius(40);
        pieChart.setDrawSlicesUnderHole(true);
        pieChart.getDescription().setText("Data periode bulan Juli 2020");
        pieChart.setUsePercentValues(true);

        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pieChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getLegend().setDrawInside(false);
        pieChart.setEntryLabelColor(Color.TRANSPARENT);
        pieChart.setDrawRoundedSlices(true);


    }

    public static final int[] WARNA_PASTEL = {
            Color.rgb(206,84,104), Color.rgb(208,85,167),
            Color.rgb(171,85,208), Color.rgb(85,94,208),
            Color.rgb(85,175,208), Color.rgb(85,208,155),
            Color.rgb(106,208,85), Color.rgb(62,164,185),
            Color.rgb(208,155,85), Color.rgb(208,118,85),
            Color.rgb(242,235,35), Color.rgb(35,207,242),

    };

    private class MyValueFormatted extends IndexAxisValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            return ((int)value) + "%";
        }
    }

}

