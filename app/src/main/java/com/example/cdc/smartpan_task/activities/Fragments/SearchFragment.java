package com.example.cdc.smartpan_task.activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Data.Countries;
import com.example.cdc.smartpan_task.activities.Interface.GetFunctionsInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.cdc.smartpan_task.activities.Interface.GetFunctionsInterface.COUNTRIES_URL;

public class SearchFragment extends Fragment {

    View view;
    ListView searchListView;
    List<Countries> countriesList = new ArrayList<>();
    CountriesAdapter countriesAdapter;
    EditText searchEditText;

    public SearchFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        init();

        countriesAdapter = new CountriesAdapter(getActivity(), countriesList);
        searchListView.setAdapter(countriesAdapter);

        /**
        * Event on edit text to search in countries lisr
        * */
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int textlength = charSequence.length();
                ArrayList<Countries> tempArrayList = new ArrayList<>();

                for(Countries c : countriesList){
                    if (textlength <= c.getName().length()){
                        if (c.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            tempArrayList.add(c);
                        }
                    }
                }

                countriesAdapter = new CountriesAdapter(getActivity(), tempArrayList);
                searchListView.setAdapter(countriesAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    private void init(){
        searchListView = (ListView) view.findViewById(R.id.search_listView);
        searchEditText = (EditText) view.findViewById(R.id.search_editText);

        getCountries();
    }

    /**
    * Get All Countries Using Retrofit Interface Class
    * */
    private void getCountries(){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(COUNTRIES_URL).build();
        GetFunctionsInterface getFunctionsInterface = restAdapter.create(GetFunctionsInterface.class);

        getFunctionsInterface.getAllCountries(new Callback<List<Countries>>() {
            @Override
            public void success(List<Countries> countries, Response response) {
                for(int i = 0; i < countries.size(); i++){
                    Countries country = new Countries();

                    country.setName(countries.get(i).getName());
                    country.setCapital(countries.get(i).getCapital());

                    countriesList.add(country);

                    System.out.println("Countries: " + country.getName());
                }
                countriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CountriesAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater inflater;
        List<Countries> countryList = null;
        ArrayList<Countries> countryArrayList;

        public CountriesAdapter(Context mContext, List<Countries> countryList){
            this.mContext = mContext;
            this.countryList = countryList;
            inflater = LayoutInflater.from(mContext);
            this.countryArrayList = new ArrayList<>();
            this.countryArrayList.addAll(countryList);
        }

        class CountryViewHolder{
            TextView countryName, CapitalName;
        }

        @Override
        public int getCount() {
            return countryList.size();
        }

        @Override
        public Object getItem(int i) {
            return countryList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final CountryViewHolder holder;

            if(view == null){
                holder = new CountryViewHolder();
                view = inflater.inflate(R.layout.search_row, null);
                holder.countryName = (TextView) view.findViewById(R.id.country_name_textView);
                holder.CapitalName = (TextView) view.findViewById(R.id.capital_name_textView);
                view.setTag(holder);
            }
            else{
                holder = (CountryViewHolder) view.getTag();
            }

            holder.countryName.setText(countryList.get(i).getName());
            holder.CapitalName.setText(countryList.get(i).getCapital());

            return view;
        }
    }
}
