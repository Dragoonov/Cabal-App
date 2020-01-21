package com.cabal.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cabal.app.Utils.Filters;
import com.cabal.app.Utils.User;
import com.cabal.app.models.EventModel;

import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> implements Filterable {

    private List<EventModel> models;
    private List<EventModel> filteredModels;

    public MyEventsAdapter(List<EventModel> models) {
        this.models = models;
        this.filteredModels = models;
    }

    @NonNull
    @Override
    public MyEventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_events_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return filteredModels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<EventModel> filteredList = Filters.INSTANCE.performFiltering(models);
                results.count = filteredList.size();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredModels =  (List<EventModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView myEventName;
        TextView myEventDate;
        TextView myEventLocation;
        ImageView adminStar;
        ImageView myEventPicture;
        Button myEventRateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(view -> (
                    (AppCompatActivity)view.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            EventDetailsFragment.Companion.newInstance(filteredModels.get(getAdapterPosition())))
                    .addToBackStack(null)
                    .commit());
            myEventName = itemView.findViewById(R.id.myEventName);
            myEventDate = itemView.findViewById(R.id.myEventDate);
            myEventLocation = itemView.findViewById(R.id.myEventLocation);
            adminStar = itemView.findViewById(R.id.myEventAdminStar);
            myEventPicture = itemView.findViewById(R.id.myEventPicture);
            myEventRateButton = itemView.findViewById(R.id.myEventRateUsers);

            myEventRateButton.setOnClickListener(v -> {
                FragmentManager manager = ((AppCompatActivity)v.getContext())
                    .getSupportFragmentManager();
                manager.beginTransaction()
                    .replace(R.id.fragment_container,new RatingFragment())
                    .addToBackStack(null)
                    .commit();
            });
        }

        void bind(int position) {
            EventModel model = filteredModels.get(position);
            myEventName.setText(model.getName());
            myEventDate.setText(model.getDate());
            myEventLocation.setText(model.getLocation());
            adminStar.setVisibility(User.INSTANCE.getLoggedUser().getNick().equals(model.getCreator()) ? View.VISIBLE : View.GONE);
            Glide.with(myEventName.getContext()).load(model.getImage()).into(myEventPicture);
        }
    }
}