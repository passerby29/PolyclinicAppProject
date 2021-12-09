package com.example.polyclinic20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class Adapter_Workers extends ArrayAdapter<Workers> {
    private LayoutInflater inflater;
    private int layout;
    private List<Workers> workers;

    public Adapter_Workers(Context context, int resource, List<Workers> workers) {
        super(context, resource, workers);
        this.workers = workers;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        TextView name = (TextView) view.findViewById(R.id.textView_namew);
        TextView spec = (TextView) view.findViewById(R.id.textView_specw);
        ImageView photo = (ImageView) view.findViewById(R.id.imageView_worker);
        Workers workerss = workers.get(position);
        name.setText(workerss.getName());
        spec.setText(workerss.getSpec());
        photo.setImageResource(workerss.getPhoto());
        return view;
    }
}
