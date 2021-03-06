package com.efthymew.nichiei.ui.gallery;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.efthymew.nichiei.R;
import com.efthymew.nichiei.ui.translation_dialog.TranslationDialogViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GalleryFragment extends Fragment {

    private Button selectImageButton;
    private Uri imageUri;
    private ContentResolver cr;
    private TranslationDialogViewModel translationViewModel;
    private List<String> text;

    private LinearLayout parent;
    int SELECT_PICTURE = 200;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        cr = getContext().getContentResolver();
        selectImageButton = root.findViewById(R.id.BSelectImage);
        parent = root.findViewById(R.id.linear_layout_for_images);
        translationViewModel = ViewModelProviders.of(getActivity()).get(TranslationDialogViewModel.class);
        selectImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage();
                    }
                }
        );
        return root;
    }

    private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();

                // update viewmodel image
                try {
                    translationViewModel.setImage(InputImage.fromFilePath(getContext(), selectedImageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = selectedImageUri;
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    for (int i = 0; i < 3; i++) {
                        ImageView im = new ImageView(getContext());
                        im.setImageURI(selectedImageUri);
                        im.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        im.setAdjustViewBounds(true);
                        parent.addView(im);
                    }
                }
            }
        }
    }
}