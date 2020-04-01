package com.synnlabz.expensooze;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recycleview_config {
    private Context mContext;
    private BillAddaptor mBillAddaptor;
    private Dialog myDialog;
    private DatabaseHelper db;

    public void setConfig(RecyclerView recyclerView, Context context, List<Bill> bills, List<String> keys){
        mContext = context;
        mBillAddaptor = new BillAddaptor(bills,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBillAddaptor);
        myDialog = new Dialog(context);
        db = new DatabaseHelper(context);
    }

    class BillItemView extends RecyclerView.ViewHolder{

        private TextView Nametxt,Amounttxt;
        private String key;

        public BillItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_bill,parent,false));
            Nametxt = (TextView) itemView.findViewById(R.id.nameTxt);
            Amounttxt = (TextView) itemView.findViewById(R.id.amountTxt);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ShowPopup(v,mBillAddaptor.mBill.get(mBillAddaptor.mKey.indexOf(key)));
                    //Toast.makeText(mContext,"There are no previous records"+key+" "+mBillAddaptor.mBill.get(mBillAddaptor.mKey.indexOf(key)),Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(Bill bill,String key){
            Nametxt.setText(bill.getCID());
            Amounttxt.setText(bill.getAmount());
            this.key = key;
        }



    }

    class BillAddaptor extends RecyclerView.Adapter<BillItemView>{
        private List<Bill> mBill;
        private List<String> mKey;

        public BillAddaptor(List<Bill> mBill, List<String> mKey) {
            this.mBill = mBill;
            this.mKey = mKey;
        }


        @NonNull
        @Override
        public BillItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BillItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BillItemView holder, int position) {
            holder.bind(mBill.get(position),mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mBill.size();
        }
    }


    public void ShowPopup(View v,Bill bill) {
        TextView txtdate,txtcat,txtamount,txtremark;
        Button btnDelete,btnClose;
        final String ID = bill.getRID();
        myDialog.setContentView(R.layout.activity_custome_pop_up);
        txtcat =(TextView) myDialog.findViewById(R.id.textCat);
        txtdate =(TextView) myDialog.findViewById(R.id.textDate);
        txtamount =(TextView) myDialog.findViewById(R.id.textAmount);
        txtremark =(TextView) myDialog.findViewById(R.id.textRemark);
        txtcat.setText(bill.getCID());
        txtdate.setText(bill.getYr()+"-"+bill.getMon()+"-"+bill.getMday());
        txtamount.setText(bill.getAmount());
        txtremark.setText(bill.getRemark());
        btnDelete = (Button) myDialog.findViewById(R.id.btndelete);
        btnClose = (Button) myDialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.DeleteRec(ID);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
