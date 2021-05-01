package com.efthymew.nichiei.ui.translation_dialog;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efthymew.nichiei.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TranslationDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslationDialogFragment extends DialogFragment {
    public static String TAG = "TranslationDialog";
    public ViewDataBinding binding;
    public TranslationDialogFragment() {
        // Required empty public constructor
    }

    public static TranslationDialogFragment newInstance() {
        TranslationDialogFragment tdf = new TranslationDialogFragment();
        return tdf;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_translation_dialog, container, false);
        binding = DataBindingUtil.bind(v);
        return v;
    }

    public void detectText(final FragmentManager ft, final String tag) {
        InputImage image = viewModel.getImage();
        if (image == null) {
            Log.i("Notif", "No image selected!");
            return;
        }
        Log.i("Notif:", "Beginner worker text detection!");
        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                // ...
                                Log.i("Text processed!", visionText.getText());
                                viewModel.setTranslation(visionText.getText());
                                show(ft, tag);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });

    }
}