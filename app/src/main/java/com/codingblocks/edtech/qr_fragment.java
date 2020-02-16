package com.codingblocks.edtech;


import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class qr_fragment extends Fragment {


    public class qr_fragment extends Fragment {
        private CodeScanner mCodeScanner;
        public static String scannednotes = "" ;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            final Activity activity = getActivity();
            View root = inflater.inflate(R.layout.fragment_qr_fragment, container, false);
            CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(activity, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scannednotes += result.getText();
                            mCodeScanner.releaseResources();
                            Toast.makeText(getContext(),"Click to scan next",Toast.LENGTH_SHORT).show();

                        }







                    });

                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
            return root;
        }

        @Override
        public void onResume() {
            super.onResume();
            mCodeScanner.startPreview();
        }

        @Override
        public void onPause() {
            mCodeScanner.releaseResources();
            super.onPause();
        }
        int randomNumber;
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,


        };



    }
