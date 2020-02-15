package com.codingblocks.edtech;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link start_chapter_second_page.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link start_chapter_second_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class start_chapter_second_page extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public start_chapter_second_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment start_chapter_second_page.
     */
    // TODO: Rename and change types and number of parameters
    public static start_chapter_second_page newInstance(String param1, String param2) {
        start_chapter_second_page fragment = new start_chapter_second_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_chapter_second_page, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public class start_chapter_second_page extends Fragment  {

        TextView chapter_name,translated_notes ;
        Button listen ;
        Button btn_done_save_notes ;
        FloatingActionButton scanqr ;
        Boolean flag = false ;
        ArrayList<String> matches ;
        TextToSpeech tts;
        String translatedinput,scannedinput ;
        public static SpeechRecognition speechRecognition  ;
        public static boolean isListening =false;
        public static         String str = "" ;
        public static Spinner spinner ;
        public static String TAG = "check" ;
        public static AudioManager audioManager ;
        public static     Thread t ;
        FirebaseTranslatorOptions options;
        public static  String language ;


        public start_chapter_second_page() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_start_chapter_second_page, container, false);

            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
            speechRecognition  = new SpeechRecognition(getContext());
            speechRecognition.useGoogleImeRecognition(false,null) ;
            speechRecognition.useOnlyOfflineRecognition(true) ;
            final String value = getArguments().getString("chapterName");
            final String value1 = getArguments().getString("chapeterSubject") ;

            speechRecognition.setSpeechRecognitionPermissionListener(new OnSpeechRecognitionPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Log.d("check", "onPermissionGranted: ");
                }

                @Override
                public void onPermissionDenied() {
                    Log.d(TAG, "onPermissionDenied: ");
                }
            });
            //Adding Toolbar
            chapter_name=view.findViewById(R.id.frag_start_chapter_second_txtview_chapter_name);
            chapter_name.setText(value);
//
            speechRecognition.setSpeechRecognitionListener(new OnSpeechRecognitionListener() {
                @Override
                public void OnSpeechRecognitionStarted() {
                    Log.d(TAG, "OnSpeechRecognitionStarted: ");
                }

                @Override
                public void OnSpeechRecognitionStopped() {
                    Log.d(TAG, "OnSpeechRecognitionStopped: ");


                }

                @Override
                public void OnSpeechRecognitionFinalResult(String s) {



                    t = new Thread(){
                        @Override
                        public void run() {
                            startspeaking(s);
                        }
                    };
                    t.start();
                    calltoagain(true);


                }

                @Override
                public void OnSpeechRecognitionCurrentResult(String s) {
                    Log.d(TAG, "OnSpeechRecognitionCurrentResult: ");


                }

                @Override
                public void OnSpeechRecognitionError(int i, String s) {
                    calltoagain(true);

                }
            });





            // chapter_name = view.findViewById(R.id.frag_start_chapter_second_txtview_chapter_name) ;
            //    translated_notes = view.findViewById(R.id.frag_start_chapter_second_txtview_notes) ;
            listen = view.findViewById(R.id.frag_start_chapter_second_tglbtn_lisening) ;
            btn_done_save_notes = view.findViewById(R.id.frag_start_chapter_second_button_done) ;
            scanqr = view.findViewById(R.id.frag_start_chapter_second_float_button_qrcode) ;
            listen.setBackgroundResource(R.drawable.ic_play_button);
//scanqr.setImageResource(R.drawable.ic_qr);
            listen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isListening == false) {
                        listen.setBackgroundResource(R.drawable.ic_pause2);
                        speechRecognition.startSpeechRecognition();

                    }
                    else
                    {
                        listen.setBackgroundResource(R.drawable.ic_play_button);
                        speechRecognition.stopSpeechRecognition();

                    }
                    isListening=!isListening;
                }

            });
            spinner=view.findViewById(R.id.frag_start_chapter_second_spinner);
            language=spinner.getSelectedItem().toString();



//       Toolbar toolbar=view.findViewById(R.id.frag_start_chapter_second_txtview_chapter_name);
////        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setTitle(value);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //how to go back write ur code here
//            }
//        });
            btn_done_save_notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notes notes = new Notes()  ;
                    Log.d("show scanned notes",qr_fragment.scannednotes);
                    notes.setChapter_name(value);
                    notes.setGenerated_notes("Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar");
                    notes.setScanned_notes(qr_fragment.scannednotes);
                    notes.setScanned_test("Who is rohit kumar?,Who is rohit kumar?Who is rohit kumar?Who is rohit kumar?Who is rohit kumar?,Who is rohit kumar?");
                    notes.setSubject(value1);
                    MainActivity.myappdatabaseclass.myDaoforchapter().addNotes(notes);

                    MainActivity.fragmentManager.popBackStack();

                    MainActivity.fragmentManager.beginTransaction().add(R.id.new_container,new home()).addToBackStack(null).commit();


                }
            });
            scanqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.fragmentManager.beginTransaction().add(R.id.new_container,new qr_fragment()).addToBackStack(null).commit();
                }
            });




            return view ;
        }
        public static void calltoagain(boolean b) {
            speechRecognition.stopSpeechRecognition();
            speechRecognition.startSpeechRecognition();

        }
        expandableadapter expandableadapter = new expandableadapter(getContext(), listgroup, listitem);
            expandableListView.setAdapter(expandableadapter);
            Log.d("child click", "outside adapter");
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("child click", "into child");
                int i = 0;
                String s = (String) expandableadapter.getChild(groupPosition, childPosition);
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                String  notes_scanned = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_scanned_notes(s) ;
                Fragment fragment=new notes_fragment();
                Bundle b  = new Bundle() ;
                b.putString("notes",notes_scanned);
                b.putString("chaptername",s);
                fragment.setArguments(b);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container,fragment)
                        .addToBackStack(null).commit() ;



                return true;
            }
        });


            return view;
    }


    }
