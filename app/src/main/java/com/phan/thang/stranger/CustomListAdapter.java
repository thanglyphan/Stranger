package com.phan.thang.stranger;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.text.TextPaint;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 18.08.2016.
 */
public class CustomListAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private List<String> items;
    private Typeface tf;
    private String font;

    public CustomListAdapter(Context context, int layoutResourceId, List<String> items, String FONT) {
        super(context, layoutResourceId, items);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.font = FONT;
        this.tf = Typeface.createFromAsset(getContext().getAssets(), String.format("fonts/%s", FONT));


    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(layoutResourceId, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.tv);

        if(items.get(position) != null )
        {
            text.setTextColor(Color.BLACK);
            text.setText("\t" + items.get(position));
            text.setBackgroundColor(Color.WHITE);
            int color = Color.argb( 235, 255, 255, 255 );
            text.setBackgroundColor(color);
            text.setTextSize(23);
            if(items.get(position).equals("Profile")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lead_pencil, 0, R.drawable.chevron_right, 0);
            }else if(items.get(position).equals("Terms of use")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, R.drawable.chevron_right, 0);
            }else if(items.get(position).equals("Notifications")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.comment_alert_outline, 0, R.drawable.chevron_right, 0);
            }else if(items.get(position).equals("Contact us")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_ru, 0, R.drawable.chevron_right, 0);
            }
        }
        text.setTypeface(tf);
        text.setTextColor(Color.BLACK);

        return mView;
    }
}
