package com.cabal.app.hobbies_edit_list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cabal.app.R;
import com.cabal.app.Utils.User;
import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.HobbyTypeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Hobbies {

    private static Map<HobbyModel, Boolean> switchesState = new LinkedHashMap<>();
    private static List<HobbyTypeModel> hobbyTypeModels;
    private static final String TAG = "Hobbies";

    public static void initializeHobbies(Activity activity) {
        LinearLayout linearView = activity.findViewById(R.id.hobbiesContainer);
        if (linearView == null) {
            return;
        }
        if (hobbyTypeModels == null) {
            hobbyTypeModels = new ArrayList<>();
            loadHobbies(activity, hobbyTypeModels, linearView);
        } else {
            for (HobbyTypeModel model : hobbyTypeModels) {
                View hobbyTypeModel = initializeHobbyTypeView(model, linearView);
                linearView.addView(hobbyTypeModel);
            }
        }
    }

    public static void tickLikedHobbies() {
        for (HobbyModel model : User.getHobbies()) {
            switchesState.put(model, true);
        }
    }

    public static List<HobbyTypeModel> loadHobbies(Context context, List<HobbyTypeModel> hobbyTypeModels, LinearLayout linearView) {

        return hobbyTypeModels;
    }


    public static int[] getCheckedIds() {
        return switchesState.keySet().stream()
                .filter(s -> switchesState.get(s))
                .map(HobbyModel::getId)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static View initializeHobbyTypeView(HobbyTypeModel model, LinearLayout parent) {
        View hobbyTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_type_item, parent, false);
        ((TextView) hobbyTypeView.findViewById(R.id.hobbyTypeTitle)).setText(model.getName());
        LinearLayout hobbies = hobbyTypeView.findViewById(R.id.subItem);
        for (HobbyModel hobbyModel : model.getHobbies()) {
            View hobby = initializeHobbyView(hobbyModel, hobbies);
            hobbies.addView(hobby);
        }
        setHobbiesListVisibility(hobbies, model.isExpanded());
        setArrowsVisibility(hobbyTypeView, model.isExpanded());
        hobbyTypeView.setOnClickListener(view1 -> {
            boolean expanded = model.isExpanded();
            model.setExpanded(!expanded);
            setHobbiesListVisibility(hobbies, model.isExpanded());
            setArrowsVisibility(view1, model.isExpanded());
        });
        return hobbyTypeView;
    }

    private static View initializeHobbyView(HobbyModel model, LinearLayout parent) {
        View hobbyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_item, parent, false);
        ((TextView) hobbyView.findViewById(R.id.nameHobby)).setText(model.getName());
        Glide.with(hobbyView.getContext()).load(model.getDescription()).into((ImageView) hobbyView.findViewById(R.id.photoHobby));
        Switch switchToggle = hobbyView.findViewById(R.id.switchHobby);
        hobbyView.setOnClickListener(hobby -> {
            switchToggle.setChecked(!switchToggle.isChecked());
            switchesState.put(model, switchToggle.isChecked());
        });
        Boolean switchState = switchesState.keySet().stream().anyMatch(mod -> mod.getId() == model.getId());
        switchToggle.setChecked(switchState == null ? false : switchState);
        if (switchState == null) {
            switchesState.put(model, false);
        }
        return hobbyView;
    }

    private static void setHobbiesListVisibility(LinearLayout list, boolean visible) {
        list.setVisibility(visible ? VISIBLE : GONE);
    }

    private static void setArrowsVisibility(View view, boolean visible) {
        ImageView arrowDown = view.findViewById(R.id.iconArrowDown);
        ImageView arrowUp = view.findViewById(R.id.iconArrowUp);
        arrowUp.setVisibility(visible ? VISIBLE : GONE);
        arrowDown.setVisibility(visible ? GONE : VISIBLE);
    }
}
