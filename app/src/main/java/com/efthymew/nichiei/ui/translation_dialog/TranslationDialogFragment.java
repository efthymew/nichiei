package com.efthymew.nichiei.ui.translation_dialog;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efthymew.nichiei.R;
import com.efthymew.nichiei.databinding.FragmentTranslationDialogBinding;
import com.efthymew.nichiei.ui.gallery.GalleryViewModel;
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
    public FragmentTranslationDialogBinding translationDialogBinding;
    public GalleryViewModel imageModel;
    private Dialog dialog;

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
        imageModel = new ViewModelProvider(requireActivity()).get(GalleryViewModel.class);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = builder.create();
        return dialog;
    }

    public void detectText(final TranslationDialogViewModel model) {
        InputImage image = imageModel.getImage().getValue();
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
                                model.setTranslation(visionText.getText());
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