package com.cabal.app.Utils;

import com.cabal.app.models.EventModel;

import java.util.ArrayList;
import java.util.List;

public class Filters {

    public static boolean AUTHOR_FILTER = false;

    public static boolean GUEST_FILTER = false;

    public static boolean FINISHED_FILTER = false;

    public static List<EventModel> performFiltering(List<EventModel> list) {
        //TODO: Upgrade after pumping Java to 8+
        List<EventModel> resultList = new ArrayList<>(list);
        if (AUTHOR_FILTER) {
            List<EventModel> modelsToRemove = new ArrayList<>();
            for (EventModel model : list) {
                if (!model.getCreator().equals(User.getUserNick())) {
                    modelsToRemove.add(model);
                }
            }
            resultList.removeAll(modelsToRemove);
        }
        if (GUEST_FILTER) {
            List<EventModel> modelsToRemove = new ArrayList<>();
            for (EventModel model : list) {
                if (model.getCreator().equals(User.getUserNick())) {
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
