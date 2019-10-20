package com.hobbymeetingapp.app;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hobbymeetingapp.app.models.UserModel;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<UserModel> models;

    public RatingAdapter(List<UserModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rating,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, int position) {
        String text = holder.userDescription.getContext().getString(R.string.rate_user, models.get(position).getNick());
        holder.userDescription.setText(text);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView userDescription;
        ImageView smile;
        ImageView meh;
        ImageView frown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userDescription = itemView.findViewById(R.id.rateUserDescription);
            smile = itemView.findViewById(R.id.rateSmile);
            meh = itemView.findViewById(R.id.rateMeh);
            frown = itemView.findViewById(R.id.rateFrown);

            smile.setOnClickListener(view -> choose(smile));
            meh.setOnClickListener(view -> choose(meh));
            frown.setOnClickListener(view -> choose(frown));
        }

        private void setGreyed(ImageView v)
        {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            v.setColorFilter(cf);
            v.setImageAlpha(64);
        }

        private void setUngreyed(ImageView v)
        {
            v.setColorFilter(null);
            v.setImageAlpha(255);
        }

        private void choose(ImageView v) {
            if(v.equals(smile)){
                setGreyed(meh);
                setGreyed(frown);
                setUngreyed(smile);
            } else if(v.equals(frown)){
                setGreyed(smile);
                setGreyed(meh);
                setUngreyed(frown);
            } else {
                setGreyed(smile);
                setGreyed(frown);
                setUngreyed(meh);
            }
        }
    }
}
