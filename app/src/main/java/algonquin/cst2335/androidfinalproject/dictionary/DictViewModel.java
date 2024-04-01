package algonquin.cst2335.androidfinalproject.dictionary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * The {@code DictViewModel} class is designed to hold and manage UI-related data
 * for the dictionary feature within the Android final project. By inheriting from
 * {@link ViewModel}, it ensures data is handled in a lifecycle-conscious way,
 * facilitating the observation of changes in the data set of dictionaries and the
 * currently selected dictionary entry.
 *
 * @author Feng Qi
 * @version 1.0
 * @since 2024-03-29
 */
public class DictViewModel extends ViewModel {
    /**
     * A MutableLiveData container for the list of dictionary entries.
     * Observers can watch this data for changes to update the UI accordingly.
     */
    private final MutableLiveData<ArrayList<Dict>> dicts = new MutableLiveData<>();

    /**
     * A MutableLiveData container for the currently selected dictionary entry.
     * This allows the UI to react and update whenever a new entry is selected.
     */
    private final MutableLiveData<Dict> selectedDict = new MutableLiveData<>();

    /**
     * Gets the LiveData container for the list of dictionary entries.
     * @return LiveData container that can be observed for changes.
     */
    public LiveData<ArrayList<Dict>> getDicts() {
        return dicts;
    }

    /**
     * Gets the LiveData container for the currently selected dictionary entry.
     * @return LiveData container that can be observed for changes.
     */
    public LiveData<Dict> getSelectedDict() {
        return selectedDict;
    }

    /**
     * Updates the list of dictionary entries.
     * @param newDicts The new list of dictionary entries.
     */
    public void setDicts(ArrayList<Dict> newDicts) {
        dicts.setValue(newDicts);
    }

    /**
     * Updates the currently selected dictionary entry.
     * @param newDict The new dictionary entry to be selected.
     */
    public void setSelectedDict(Dict newDict) {
        selectedDict.setValue(newDict);
    }
}