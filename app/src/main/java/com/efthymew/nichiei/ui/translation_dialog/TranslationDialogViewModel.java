package com.efthymew.nichiei.ui.translation_dialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.mlkit.vision.common.InputImage;

public class TranslationDialogViewModel extends ViewModel {
    private MutableLiveData<InputImage> image = new MutableLiveData<>();
    private MutableLiveData<String> translation = new MutableLiveData<>();

    public TranslationDialogViewModel() {
    }

    public LiveData<InputImage> getImage() {
        return image;
    }

    public void setImage(InputImage newImage) {
        image.setValue(newImage);
    }

    public LiveData<String> getTranslation() {
        return translation;
    }

    public void setTranslation(String text) {
        translation.setValue(text);
    }
}
