package algonquin.cst2335.androidfinalproject.ui.SunriseSunset;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<String> latitude = new MutableLiveData<>();
    private MutableLiveData<String> longitude = new MutableLiveData<>();
    private MutableLiveData<String> sunriseTime = new MutableLiveData<>();
    private MutableLiveData<String> sunsetTime = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    // Method to update latitude
    public void setLatitude(String lat) {
        latitude.setValue(lat);
    }

    // Method to update longitude
    public void setLongitude(String lng) {
        longitude.setValue(lng);
    }

    // Method to get latitude
    public LiveData<String> getLatitude() {
        return latitude;
    }

    // Method to get longitude
    public LiveData<String> getLongitude() {
        return longitude;
    }

    // Method to update sunrise time
    public void setSunriseTime(String time) {
        sunriseTime.setValue(time);
    }

    // Method to update sunset time
    public void setSunsetTime(String time) {
        sunsetTime.setValue(time);
    }

    // Method to get sunrise time
    public LiveData<String> getSunriseTime() {
        return sunriseTime;
    }

    // Method to get sunset time
    public LiveData<String> getSunsetTime() {
        return sunsetTime;
    }

    // Method to set whether location is favorite
    public void setIsFavorite(boolean favorite) {
        isFavorite.setValue(favorite);
    }

    // Method to get whether location is favorite
    public LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }
}
