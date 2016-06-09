package com.example.bipin.gharbetti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bipin on 6/8/16.
 */
public class PaidListAdapter extends RecyclerView.Adapter<PaidListAdapter.MyViewHolder> {

    LayoutInflater inflater;
    Context context;

    public PaidListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PaidListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.individuallist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaidListAdapter.MyViewHolder holder, int position)  {



    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView roomNo, name, amount;


        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //int pos=getAdapterPosition();
            Intent intent = new Intent(context, TransactionActivity.class);
           // intent.putExtra("positionKey",pos);
            context.startActivity(intent);

        }
    }
}
