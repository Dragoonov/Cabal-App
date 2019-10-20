package com.hobbymeetingapp.app.hobbies_edit_list;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.hobbymeetingapp.app.R;
import com.hobbymeetingapp.app.Utils.JsonLoader;
import com.hobbymeetingapp.app.models.HobbyModel;
import com.hobbymeetingapp.app.models.HobbyTypeModel;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Hobbies {

    public static void initializeHobbies(AppCompatActivity activity){

        List<HobbyTypeModel> hobbyTypeModels = JsonLoader.loadHobbies(activity);
        LinearLayout linearView = activity.findViewById(R.id.hobbiesContainer);

        assert hobbyTypeModels != null;
        for(HobbyTypeModel model : hobbyTypeModels) {
            View hobbyTypeModel = initializeHobbyTypeView(model,linearView);
            linearView.addView(hobbyTypeModel);
        }
    }

    private static View initializeHobbyTypeView(HobbyTypeModel model, LinearLayout parent){
        View hobbyTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_type_item, parent,false);
        ((TextView)hobbyTypeView.findViewById(R.id.hobbyTypeTitle)).setText(model.getType());
        LinearLayout hobbies = hobbyTypeView.findViewById(R.id.subItem);
        for(HobbyModel hobbyModel : model.getHobbies()) {
            View hobby = initializeHobbyView(hobbyModel,hobbies);
            hobbies.addView(hobby);
        }
        setHobbiesListVisibility(hobbies,model.isExpanded());
        setArrowsVisibility(hobbyTypeView,model.isExpanded());
        hobbyTypeView.setOnClickListener(view1 -> {
            boolean expanded = model.isExpanded();
            model.setExpanded(!expanded);
            setHobbiesListVisibility(hobbies,model.isExpanded());
            setArrowsVisibility(view1,model.isExpanded());
        });
        return hobbyTypeView;
    }

    private static View initializeHobbyView(HobbyModel model, LinearLayout parent){
        View hobbyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_item,parent,false);
        ((TextView)hobbyView.findViewById(R.id.nameHobby)).setText(model.getName());
        Glide.with(hobbyView.getContext()).load(model.getPhotoUrl()).into((ImageView) hobbyView.findViewById(R.id.photoHobby));
        hobbyView.setOnClickListener(hobby -> {
            Switch switchToggle = hobby.findViewById(R.id.switchHobby);
            switchToggle.setChecked(!switchToggle.isChecked());
        });
        return hobbyView;
    }

    private static void setHobbiesListVisibility(LinearLayout list, boolean visible){
        list.setVisibility(visible ? VISIBLE : GONE);
    }

    private static void setArrowsVisibility(View view, boolean visible){
        ImageView arrowDown = view.findViewById(R.id.iconArrowDown);
        ImageView arrowUp = view.findViewById(R.id.iconArrowUp);
        arrowUp.setVisibility(visible ? VISIBLE : GONE);
        arrowDown.setVisibility(visible ? GONE : VISIBLE);
    }
}
