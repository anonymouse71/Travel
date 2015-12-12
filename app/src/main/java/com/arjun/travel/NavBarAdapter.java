package com.arjun.travel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by arjun on 9/10/15.
 */
public class NavBarAdapter extends RecyclerView.Adapter<NavBarAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    List<NavBarData> data = Collections.emptyList();
    private Context context;
    public NavBarAdapter(Context context, List<NavBarData> data ){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NavBarData current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.title.setTag(R.id.listTitle,current.getIconId());
        /*holder.icon.setImageResource(current.getIconId());*/
        /*holder.icon.setImageURI(Uri.parse(current.getIconUri()));*/
        holder.icon.setImageDrawable(current.getThumb_d());
        holder.discription.setText(current.getDiscription());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),holder.title.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        TextView title;
        ImageView icon;
        TextView discription;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listTitle);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            discription = (TextView) itemView.findViewById(R.id.listDisc);
            itemView.setOnTouchListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            LinearLayout li =(LinearLayout) v.findViewById(R.id.custom_row_layout);
            TextView textView = (TextView) v.findViewById(R.id.listTitle);
            li.setBackgroundColor(Color.parseColor("#EEEEEE"));
            context.startActivity(new Intent(context,CarsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id",textView.getTag(R.id.listTitle).toString()));
            return false;

        }

    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

}
