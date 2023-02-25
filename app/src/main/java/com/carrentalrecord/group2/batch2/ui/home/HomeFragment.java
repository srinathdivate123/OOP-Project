package com.carrentalrecord.group2.batch2.ui.home;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.carrentalrecord.group2.batch2.R;
import com.carrentalrecord.group2.batch2.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;

public class HomeFragment extends Fragment {



private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();



        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        Dialog popUpScreen;
        popUpScreen = new Dialog(getContext());




        final Button search = binding.searchRecord;
        final EditText queryAadharfield = binding.editTextAadharNumber;
        final ImageView profileImage = binding.profileImage;
        final LinearLayout resultLayout = binding.queryResultLayout;
        final LinearLayout searchLayout = binding.searchContainingLayout;
        final Button completeOrder = binding.completeOrder;
        final Button discardResult = binding.discardResult;

        TextView nameLabel = binding.nameLabel;
        TextView aadhaarCardLabel = binding.aadhaarCardLabel;
        TextView addressLabel = binding.addressLabel;
        TextView carTypeLabel = binding.carTypeLabel;
        TextView commissionPeriodLabel = binding.commissionPeriodLabel;

        final int[] previousTextCount = {0};

        storeValue("955674884675","");
        storeValue("955674884675"+"Name","Anurag Tekam");
        storeValue("955674884675"+"Address","Near Vit Chowk Bibwewadi");
        storeValue("955674884675"+"Model","Maruti Suzuki 800");
        storeValue("955674884675"+"Period","24-02-2023  to  29-02-2023");




        discardResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
            }
        });

        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);

                Toast.makeText(getContext().getApplicationContext(),"Transaction Successfully Completed",Toast.LENGTH_SHORT).show();
                removeRecord(queryAadharfield.getText().toString().replaceAll("-",""));
            }
        });



        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    String aadhaarNumber = queryAadharfield.getText().toString();

                    if (aadhaarNumber.length() == 4 && previousTextCount[0] != 5) {
                        queryAadharfield.setText(aadhaarNumber + "-");
                    } else if (aadhaarNumber.length() == 9 && previousTextCount[0] != 10) {
                        queryAadharfield.setText(aadhaarNumber + "-");
                    }else if(aadhaarNumber.length() == 5 && previousTextCount[0]==4){


                        aadhaarNumber = aadhaarNumber.substring(0,4) + "-" + aadhaarNumber.substring(4,5);
                        queryAadharfield.setText(aadhaarNumber);

                    }else if(aadhaarNumber.length()==10 && previousTextCount[0]==9){

                        aadhaarNumber = aadhaarNumber.substring(0,9) + "-" + aadhaarNumber.substring(9,10);
                        queryAadharfield.setText(aadhaarNumber);

                    }



                    queryAadharfield.setSelection(queryAadharfield.getText().length());

                previousTextCount[0] = queryAadharfield.getText().toString().length();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        queryAadharfield.addTextChangedListener(textWatcher);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String queryNumber =  queryAadharfield.getText().toString();
                aadhaarCardLabel.setText("Aadhaar Number : " + queryNumber);
                queryNumber = queryNumber.replaceAll("-",
                        "");


                if(queryNumber.length() == 12){
                    if(valueExist(queryNumber)){

                        nameLabel.setText("Name : " + retriveValue(queryNumber+"Name"));
                        //aadhaarCardLabel.setText("Aadhaar Number : " + queryNumber);
                        addressLabel.setText("Address : " + retriveValue(queryNumber + "Address"));
                        carTypeLabel.setText("Car Model : " + retriveValue(queryNumber+"Model"));
                        commissionPeriodLabel.setText("Period : " + retriveValue(queryNumber+"Period"));

                        resultLayout.setVisibility(View.VISIBLE);
                        searchLayout.setVisibility(View.GONE);

                        //popUpScreen.setContentView(resultLayout);



//                        Glide.with(getContext())
//                                .load("https://images.unsplash.com/profile-1446404465118-3a53b909cc82?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=3ef46b07bb19f68322d027cb8f9ac99f")
//                                .into(profileImage);
//                        profileImage.setVisibility(View.VISIBLE);


                        if(valueExist(queryNumber+"Image")){

                            Glide.with(getContext())
                                    .load(queryNumber+"Image")
                                    .into(profileImage);
                            profileImage.setVisibility(View.VISIBLE);
                        }else{

                            profileImage.setBackgroundResource(R.drawable.rentigo_icon);
                            profileImage.setVisibility(View.VISIBLE);
                        }




                    }else{
                        resultLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext().getApplicationContext(),"The query doesn't exist in records.",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    resultLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid aadhar card no.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;




    }



    public void storeValue(String key,String data){

        SharedPreferences localStorage = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor localStorageWriter = localStorage.edit();
        localStorageWriter.putString(key, data);

        localStorageWriter.apply();

    }

    public void removeRecord(String key){

        SharedPreferences localStorage = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor localStorageWriter = localStorage.edit();
        localStorageWriter.remove(key);

        localStorageWriter.apply();

    }


    public String retriveValue(String key){

        SharedPreferences sh = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString(key,"");
        return s1;

    }

    public boolean valueExist(String key){

        SharedPreferences sh = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        return sh.contains(key);

    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}