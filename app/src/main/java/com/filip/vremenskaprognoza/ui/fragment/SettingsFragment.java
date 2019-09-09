package com.filip.vremenskaprognoza.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.filip.vremenskaprognoza.R;
import com.filip.vremenskaprognoza.common.SharedPrefs;

import static com.filip.vremenskaprognoza.common.Common.BOOLEAN_KEY;
import static com.filip.vremenskaprognoza.common.Common.DATA_KEY;

public class SettingsFragment extends Fragment {

    private EditText inputEmail;
    private ConstraintLayout layout;
    private TextView email;
    private Button saveLogoutBtn;
    private Button changeColorButton;
    private SharedPrefs sharedPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefs = SharedPrefs.getInstance(getActivity());
        initWidgets(view);
        initData();
        initListeners();
    }

    private void initWidgets(View view) {
        inputEmail = view.findViewById(R.id.inputUserEmail);
        email = view.findViewById(R.id.userMail);
        saveLogoutBtn = view.findViewById(R.id.saveLogoutBtn);
        changeColorButton = view.findViewById(R.id.changeColorButton);
        layout = view.findViewById(R.id.layout);
    }

    private void initData() {
        if (sharedPrefs.getData(DATA_KEY).isEmpty()) {

            inputEmail.setVisibility(View.VISIBLE);
            email.setVisibility(View.INVISIBLE);
            saveLogoutBtn.setText(getString(R.string.save_user));


        } else {
            inputEmail.setVisibility(View.INVISIBLE);
            email.setVisibility(View.VISIBLE);
            saveLogoutBtn.setText(getString(R.string.logout_user));
            email.setText(sharedPrefs.getData(DATA_KEY));
        }

        if (sharedPrefs.getColor(BOOLEAN_KEY) == false) {
            setBlueColor();

        } else {
            setRedColor();

        }
    }

    private void initListeners() {
        saveLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveLogoutBtn.getText().equals(getString(R.string.save_user))) {
                    saveUser();
                } else {
                    logoutUser();
                }

                inputEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);

            }
        });

        changeColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPrefs.getColor(BOOLEAN_KEY) == false) {
                    sharedPrefs.changeColor(BOOLEAN_KEY, true);
                    setRedColor();

                } else {
                    sharedPrefs.changeColor(BOOLEAN_KEY, false);
                    setBlueColor();
                }
            }
        });
    }

    private void logoutUser() {
        sharedPrefs.clear();

    }

    private void saveUser() {
        if (inputEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(),getString(R.string.enter_email),Toast.LENGTH_SHORT).show();
        } else {
            if(isValidEmail(inputEmail.getText().toString())){
                sharedPrefs.saveData(DATA_KEY, inputEmail.getText().toString());
            }else{
                Toast.makeText(getActivity(),getString(R.string.not_valid_email),Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void setBlueColor() {
        saveLogoutBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.get_weather_btn));
        changeColorButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.get_weather_btn));
        layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));

    }

    private void setRedColor() {
        saveLogoutBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.grey_button));
        changeColorButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.grey_button));
        layout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.blue_bg));
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;

        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}