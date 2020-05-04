package com.example.sample.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sample.R;
import com.example.sample.modals.VoterData;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoterListAdapter extends RecyclerView.Adapter<VoterListAdapter.MyViewHolder> implements Filterable {



    private List<VoterData> userModelList;
    private List<VoterData> userModelListFull;
    private Context context;
    private DatabaseReference myRef;
    private String checkOutTime;

    public VoterListAdapter(List<VoterData> userModelList, Context context) {
        this.userModelList = userModelList;
        userModelListFull = userModelList;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    userModelListFull = userModelList;
                } else {

                    ArrayList<VoterData> filteredList = new ArrayList<>();

                    for (VoterData androidVersion : userModelList) {

                        if (androidVersion.getVoterName().toLowerCase().contains(charString) ||
                                androidVersion.getVoterMobile().contains(charString) ||
                                androidVersion.getVoterDistrict().toLowerCase().contains(charString) ||
                                androidVersion.getVoterMandal().toLowerCase().contains(charString) ||
                                androidVersion.getVoterState().toLowerCase().contains(charString)
                        ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    userModelListFull = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userModelListFull;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userModelListFull = (ArrayList<VoterData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtDepId)
        TextView txtDepId;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtEmail)
        TextView txtEmail;
        @BindView(R.id.txtPhone)
        TextView txtPhone;
        @BindView(R.id.txtmeetTo)
        TextView txtmeetTo;
        @BindView(R.id.txtMandal)
        TextView txtMandal;
        @BindView(R.id.txtDistrict)
        TextView txtDistrict;
        @BindView(R.id.txtState)
        TextView txtState;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.voter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VoterData userModel = userModelListFull.get(position);
        holder.txtDepId.setText("User ID :  " + userModel.getVoterID());
        holder.txtName.setText("Name :  " + userModel.getVoterName());
        holder.txtEmail.setText("Email :  " + userModel.getVoterEmail());
        holder.txtPhone.setText("Phone : " + userModel.getVoterMobile());
        holder.txtmeetTo.setText("Status :  " + userModel.getVoterStatus());
        holder.txtMandal.setText("Mandal \n" + userModel.getVoterMandal());
        holder.txtDistrict.setText("District \n" + userModel.getVoterDistrict());
        holder.txtState.setText("State \n" + userModel.getVoterState());


        Glide.with(context).load(userModel.getImageUrl()).into(holder.imgProfile);


    }

    @Override
    public int getItemCount() {
        return userModelListFull.size();
    }


}