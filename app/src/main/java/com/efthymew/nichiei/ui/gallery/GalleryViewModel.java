package com.efthymew.nichiei.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.mlkit.vision.common.InputImage;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<InputImage> inputImage;

    public GalleryViewModel() {
        inputImage = new MutableLiveData<>();
    }

    public LiveData<InputImage> getImage() {
        return inputImage;
    }

    public void setImage(InputImage newImage) {
        this.inputImage.setValue(newImage);
    }
}