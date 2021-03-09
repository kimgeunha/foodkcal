//package com.developndesign.firebaseautomlvisionedge;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class KcalAdapter extends RecyclerView.Adapter<KcalAdapter.KcalViewHolder> {
//
//    private ArrayList<ShowKcal> arrayList;
//    private Context context;
//
//
//    public KcalAdapter(ArrayList<ShowKcal> arrayList, Context context) {
//        this.arrayList = arrayList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public KcalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_kcal,parent,false);
//        KcalViewHolder holder = new KcalViewHolder(view);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull KcalViewHolder holder, int position) {
//
//        Glide.with(holder.itemView)
//                .load(arrayList.get(position).getProfile())
//                .into(holder.profile);
//        holder.kcal.setText(String.valueOf(arrayList.get(position).getKcal()));
//        holder.Amount.setText(String.valueOf(arrayList.get(position).getAcount()));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return (arrayList !=null ? arrayList.size() : 0);
//    }
//
//    public static class KcalViewHolder extends RecyclerView.ViewHolder {
//        ImageView profile;
//        TextView kcal;
//        TextView Amount;
//
//        public KcalViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.profile = itemView.findViewById(R.id.profile);
//            this.Amount = itemView.findViewById(R.id.amount);
//            this.kcal = itemView.findViewById(R.id.kcal);
//        }
//    }
//}
