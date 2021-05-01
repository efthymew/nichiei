package com.efthymew.nichiei.ui.translation_dialog;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.mlkit.vision.common.InputImage;

public class TranslationDialogViewModel extends BaseObservable {
    private InputImage image = null;
    private String translation = null;

    public TranslationDialogViewModel() {
    }

    @Bindable
    public InputImage getImage() {
        return image;
    }

    public void setImage(InputImage newImage) {
        this.image = newImage;
    }

    @Bindable
    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String text) {
        this.translation = text;
        notifyPropertyChanged(BR.translation);
    }
}
