package com.hobbymeetingapp.app.Utils;

import com.hobbymeetingapp.app.models.EventModel;

import java.util.ArrayList;
import java.util.List;

public class Filters {

    public static boolean MINE_FILTER = false;

    public static boolean FINISHED_FILTER = false;

    public static List<EventModel> performFiltering(List<EventModel> list) {
        List<EventModel> resultList = new ArrayList<>(list);
        if (MINE_FILTER) {
            List<EventModel> modelsToRemove = new ArrayList<>();
            for (EventModel model : list) {
                if (!model.getCreator().equals(User.getUserNick())) {
                    modelsToRemove.add(model);
                }
            }
            resultList.removeAll(modelsToRemove);
        }
        if (FINISHED_FILTER) {
            List<EventModel> modelsToRemove = new ArrayList<>();
            for (EventModel model : list) {
                if (!model.isFinished()) {
                    modelsToRemove.add(model);
                }
            }
            resultList.removeAll(modelsToRemove);
        }
        return resultList;
    }
}
