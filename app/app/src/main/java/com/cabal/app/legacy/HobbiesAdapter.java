package com.cabal.app.legacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cabal.app.R;
import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.HobbyTypeModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.RecViewHolder> {

    private List<HobbyTypeModel> hobbies;
    private Map<String,Boolean> switchedElement;

    public HobbiesAdapter(List<HobbyTypeModel> hobbies) {
        this.hobbies = hobbies;
        switchedElement = new HashMap<>();
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hobby_type_item, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        HobbyTypeModel hobbyTypeModel = hobbies.get(position);

        holder.subItem.setVisibility(hobbyTypeModel.isExpanded() ? View.VISIBLE : View.GONE);
        holder.hobbyTitle.setText(hobbyTypeModel.getName());

        if (holder.subItem.getChildCount() == 0) {
            for (HobbyModel hobbyModel : hobbyTypeModel.getHobbies()) {
                switchedElement.put(hobbyModel.getName(),false);
                View view = LayoutInflater.from(holder.subItem.getContext()).inflate(R.layout.hobby_item, holder.subItem, false);
                Glide.with(holder.subItem.getContext()).load(hobbyModel.getDescription()).into((ImageView) view.findViewById(R.id.photoHobby));
                ((TextView) view.findViewById(R.id.nameHobby)).setText(hobbyModel.getName());
                view.setOnClickListener(view1 -> {
                    Switch switchToggle = view.findViewById(R.id.switchHobby);
                    switchToggle.setChecked(!switchToggle.isChecked());
                    switchedElement.put(hobbyModel.getName(),switchToggle.isChecked());
                });
                holder.subItem.addView(view);
            }
        } else {
            for (int i = 0; i < hobbyTypeModel.getHobbies().length; i++) {
                String hobbyName = hobbyTypeModel.getHobbies()[i].getName();
                ((TextView) holder.subItem.getChildAt(i).findViewById(R.id.nameHobby)).setText(hobbyName);
                ((Switch) holder.subItem.getChildAt(i).findViewById(R.id.switchHobby)).setChecked(switchedElement.get(hobbyName));
            }
        }
        holder.itemView.setOnClickListener(v -> {
                boolean expanded = hobbyTypeModel.isExpanded();
                hobbyTypeModel.setExpanded(!expanded);
                notifyItemChanged(position);
            });
    }

    @Override
    public int getItemCount() {
        return hobbies == null ? 0 : hobbies.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        private TextView hobbyTitle;
        private LinearLayout subItem;

        public RecViewHolder(View itemView) {
            super(itemView);
            hobbyTitle = itemView.findViewById(R.id.hobbyTypeTitle);
            subItem = itemView.findViewById(R.id.subItem);
        }
    }

}
