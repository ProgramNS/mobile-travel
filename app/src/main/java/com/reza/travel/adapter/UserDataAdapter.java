package com.reza.travel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reza.travel.R;
import com.reza.travel.model.UserDataModel;

import java.util.List;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {
    private List<UserDataModel> userDataModelList;
    private onUserDataActionListener actionListener;

    public interface onUserDataActionListener{
        void onDeleteClicked(UserDataModel userData, int position);
        void onEditClicked(UserDataModel userData,  int position);
    }

    public UserDataAdapter(List<UserDataModel> userDataModelList, onUserDataActionListener actionListener){
        this.userDataModelList = userDataModelList;
        this.actionListener = actionListener;

    }

    @NonNull
    @Override
    public UserDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return userDataModelList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        UserDataModel userData = userDataModelList.get(position);

        holder.tvUsername.setText(String.format("Username : %s", userData.getUsername()));
        holder.tvName.setText(String.format("Nama : %s", userData.getNameUser()));
        holder.tvHp.setText(String.format("No Hp : %s", userData.getHp()));

        holder.btnEdit.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onEditClicked(userData, position);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onDeleteClicked(userData, position);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvName,tvHp;
        Button btnEdit,btnDelete;

        public ViewHolder(@NonNull View ItemView){
            super(ItemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvName = itemView.findViewById(R.id.tvName);
            tvHp = itemView.findViewById(R.id.tvHp);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }


    }
}
