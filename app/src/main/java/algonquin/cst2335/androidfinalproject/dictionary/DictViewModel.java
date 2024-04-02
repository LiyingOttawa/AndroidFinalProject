package algonquin.cst2335.androidfinalproject.dictionary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

/**
 * The {@code DictViewModel} class is designed to hold and manage UI-related data
 * for the dictionary feature within the Android final project. By inheriting from
 * {@link ViewModel}, it ensures data is handled in a lifecycle-conscious way,
 * facilitating the observation of changes in the data set of dictionaries, the
 * currently selected dictionary entry, and multiple selected dictionary entries.
 *  @author Feng Qi
 *  @version 1.0
 *  @since 2024-03-29
 */
public class DictViewModel extends ViewModel {
    /**
     * Holds the list of dictionaries. Observers can observe this data to receive updates.
     */
    public final MutableLiveData<ArrayList<Dict>> dicts = new MutableLiveData<>(new ArrayList<>());

    /**
     * Holds the currently selected dictionary entry. Observers can observe this data to receive updates.
     */
    public final MutableLiveData<Dict> selectedDicts = new MutableLiveData<>();
}

