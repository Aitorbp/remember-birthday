package com.example.rememberdate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDateFragment extends Fragment {

    AdaptarListBirthDay adaptarListBirthDay;
    ListView listView;
    ArrayList<Birthday> elementBirthdayArray = new ArrayList<>();
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_list_date, container, false);

        listView =(ListView) RootView.findViewById((R.id.listViewBithdays));
        adaptarListBirthDay = new AdaptarListBirthDay(getActivity().getApplicationContext(), R.layout.item_layout, elementBirthdayArray);

            listView.setAdapter(adaptarListBirthDay);
        mainActivity = (MainActivity) getActivity();


        //Método para comparar fechas
        mainActivity.sortByAge();

        return RootView;
    }


    public void sendAllData(ArrayList<Birthday> birthdaysAll) {

        elementBirthdayArray = birthdaysAll;
    }
}
