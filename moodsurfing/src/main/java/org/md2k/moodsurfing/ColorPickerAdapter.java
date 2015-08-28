package org.md2k.moodsurfing;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerAdapter extends BaseAdapter {

    private Context context;
    // list which holds the colors to be displayed
    private List<Integer> colorList = new ArrayList<Integer>();
    // width of grid column
    int colorGridColumnWidth;
    public static String colors[][] = {{"822111", "AC2B16", "CC3A21", "E66550", "EFA093", "F6C5BE"},
            {"AA8831", "D5AE49", "F2C960", "FCDA83", "FCE8B3", "FEF1D1"},
            {"076239", "0B804B", "149E60", "44B984", "89D3B2", "B9E4D0"},
            {"1C4587", "285BAC", "3C78D8", "6D9EEB", "A4C2F4", "C9DAF8"},
            {"41236D", "653E9B", "8E63CE", "B694E8", "D0BCF1", "E4D7F5"},
            {"000000", "434343", "666666", "999999", "CCCCCC", "EFEFEF"}};

    public ColorPickerAdapter(Context context) {
        this.context = context;

        // defines the width of each color square
        colorGridColumnWidth = 150;

        // for convenience and better reading, we place the colors in a two dimension array

        colorList = new ArrayList<>();

        // add the color array to the list
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                colorList.add(Color.parseColor("#" + colors[i][j]));
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // can we reuse a view?
        if (convertView == null) {
            imageView = new ImageView(context);
            // set the width of each color square
            imageView.setLayoutParams(new GridView.LayoutParams(colorGridColumnWidth, colorGridColumnWidth));

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setBackgroundColor(colorList.get(position));
        imageView.setId(position);
        return imageView;
    }

    public int getCount() {
        return colorList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
