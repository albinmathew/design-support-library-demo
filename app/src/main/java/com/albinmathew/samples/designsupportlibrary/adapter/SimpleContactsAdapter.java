package com.albinmathew.samples.designsupportlibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albinmathew.samples.designsupportlibrary.ContactDetailActivity;
import com.albinmathew.samples.designsupportlibrary.models.Contact;
import com.albinmathew.samples.designsupportlibrary.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author albin
 * @date 3/6/15
 */

public class SimpleContactsAdapter
        extends RecyclerView.Adapter<SimpleContactsAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<Contact> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView mImageView;
        public final TextView mContactName;
        public final TextView mNumber;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mContactName = (TextView) view.findViewById(android.R.id.text1);
            mNumber = (TextView) view.findViewById(android.R.id.text2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContactName.getText()+ " '" + mNumber.getText();
        }
    }

    public SimpleContactsAdapter(Context context, List<Contact> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBoundString = mValues.get(position).getId();
        holder.mContactName.setText(mValues.get(position).getDisplayName());
        holder.mNumber.setText(mValues.get(position).getPhoneNumber());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra(ContactDetailActivity.CONTACT_NAME, mValues.get(position).getDisplayName());
                intent.putExtra(ContactDetailActivity.CONTACT_IMAGE,mValues.get(position).getImageURI());
                context.startActivity(intent);
            }
        });

        Glide.with(holder.mImageView.getContext())
                .load(mValues.get(position).getImageURI())
                .fitCenter()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}