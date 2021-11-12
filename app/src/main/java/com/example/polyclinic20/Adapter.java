package com.example.polyclinic20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class Adapter extends ArrayAdapter<Timetable> {
    private LayoutInflater inflater;
    private int layout;
    private List<Timetable> timetable;

    public Adapter(Context context, int resource, List<Timetable> timetable) {
        super(context, resource, timetable);
        this.timetable = timetable;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        TextView name = (TextView) view.findViewById(R.id.textView_name);
        TextView spec = (TextView) view.findViewById(R.id.textView_spec);
        TextView time_from = (TextView) view.findViewById(R.id.textView_time);
        TextView time_to = (TextView) view.findViewById(R.id.textView_date);
        Timetable timetables = timetable.get(position);
        name.setText(timetables.getName());
        spec.setText(timetables.getSpec());
        time_from.setText(timetables.getTime_from());
        time_to.setText(timetables.getTime_to());
        return view;
    }
}
