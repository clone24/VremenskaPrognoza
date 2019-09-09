package com.filip.vremenskaprognoza.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.filip.vremenskaprognoza.BuildConfig;
import com.filip.vremenskaprognoza.R;

import static android.Manifest.permission.CALL_PHONE;
import static com.filip.vremenskaprognoza.common.Common.CONTACT_MAIL;
import static com.filip.vremenskaprognoza.common.Common.DATE_OF_APP;
import static com.filip.vremenskaprognoza.common.Common.PHONE_NUMBER;

public class InfoFragment extends Fragment {

    private TextView numberPhone;
    private TextView contactEmail;
    private TextView dateOfApp;
    private TextView versionApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets(view);
        initData();
        initListeners();
    }

    private void initWidgets(View view) {
        numberPhone = view.findViewById(R.id.numberPhone);
        contactEmail = view.findViewById(R.id.contactEmail);
        dateOfApp = view.findViewById(R.id.dateOfApp);
        versionApp = view.findViewById(R.id.versionAppValue);
    }

    private void initData() {
        String versionName = BuildConfig.VERSION_NAME;
        numberPhone.setText(PHONE_NUMBER);
        contactEmail.setText(CONTACT_MAIL);
        dateOfApp.setText(DATE_OF_APP);
        versionApp.setText(versionName);
    }

    private void initListeners() {
        numberPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhone();
            }
        });
        contactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

    }

    private void dialPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PHONE_NUMBER));
        if (ContextCompat.checkSelfPermission(getActivity(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }

    private void sendMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", CONTACT_MAIL, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
