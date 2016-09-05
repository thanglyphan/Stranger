package com.phan.thang.stranger;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.Time;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LineHeightSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by thang on 27.08.2016.
 */
public class CustomListAdapterChannel extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private List<LoveNote> items;
    private Typeface tf;
    private String font;


    public CustomListAdapterChannel(Context context, int layoutResourceId, List<LoveNote> items, String FONT) {
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

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(items.get(position).message + "\n\n");

            String LeftText = "- " + items.get(position).username;
            String RightText = DateDifference.getTimeLeft(items.get(position).dateSent);
            final String fullText = LeftText + "\n " + RightText;

            final SpannableString styledResultText = new SpannableString(fullText);
            styledResultText.setSpan(new TypefaceSpan(getContext(), "SansSerifFLF.otf"), 0, fullText.length(), 0);
            styledResultText.setSpan(new AbsoluteSizeSpan(33), 0, fullText.length(), 0);
            styledResultText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, fullText.length(), 0);
            styledResultText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), LeftText.length(), fullText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledResultText.setSpan(new SetLineOverlap(true), 1, fullText.length()-2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledResultText.setSpan(new SetLineOverlap(false), fullText.length()-1, fullText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(styledResultText);
            text.setText(builder);

            //TODO: Add time counter.
            text.setBackgroundColor(Color.WHITE);
            int color = Color.argb( 235, 255, 255, 255 );
            text.setBackgroundColor(color);
            text.setTextSize(18);
        }

        text.setTypeface(tf);
        text.setTextColor(Color.BLACK);

        return mView;
    }


    //Helper class!
    private static class SetLineOverlap implements LineHeightSpan {
        private int originalBottom = 15;        // init value ignored
        private int originalDescent = 13;       // init value ignored
        private Boolean overlap;                // saved state
        private Boolean overlapSaved = false;   // ensure saved values only happen once

        SetLineOverlap(Boolean overlap) {
            this.overlap = overlap;
        }

        @Override
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v,
                                 Paint.FontMetricsInt fm) {
            if (overlap) {
                if (!overlapSaved) {
                    originalBottom = fm.bottom;
                    originalDescent = fm.descent;
                    overlapSaved = true;
                }
                fm.bottom += fm.top;
                fm.descent += fm.top;
            } else {
                // restore saved values
                fm.bottom = originalBottom;
                fm.descent = originalDescent;
                overlapSaved = false;
            }
        }
    }
}
