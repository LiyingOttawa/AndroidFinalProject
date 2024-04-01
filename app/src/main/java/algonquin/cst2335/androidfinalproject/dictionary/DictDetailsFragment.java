package algonquin.cst2335.androidfinalproject.dictionary;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproject.databinding.DictDetailsLayoutBinding;

/**
 * The {@code DictDetailsFragment} class is responsible for showing a detailed view of a dictionary entry
 * in the Android final project. It's a fragment that extends {@link Fragment}, focusing on displaying
 * the name and summary of a selected dictionary entry.
 *
 * @author Feng Qi
 * @version 1.0
 * @since 2024-03-29
 */
/**
 * The {@code DictDetailsFragment} class is responsible for showing a detailed view of a dictionary entry
 * in the Android final project. It's a fragment that extends {@link Fragment}, focusing on displaying
 * the name and summary of a selected dictionary entry.
 */
public class DictDetailsFragment extends Fragment {
    private Dict selected;

    /**
     * Creates a new fragment instance to display details of a particular dictionary entry.
     *
     * @param d The dictionary entry to be detailed.
     */
    public DictDetailsFragment(Dict d) {
        selected = d;
    }

    /**
     * Inflates the fragment's layout, populating it with information from the selected dictionary entry.
     *
     * @param inflater           Used to inflate the views in the fragment.
     * @param container          Optional parent to which the fragment's UI may be attached.
     * @param savedInstanceState Contains data from a previous instance of the fragment, if any.
     * @return The root View of the inflated layout for this fragment, or null.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout using data binding
        DictDetailsLayoutBinding binding = DictDetailsLayoutBinding.inflate(inflater);

        // Display the name of the dictionary entry in the UI
        binding.dictNameText.setText(selected.dictName);

        // Format the summary text from HTML and display it
        Spanned spannedText = Html.fromHtml(selected.summary, Html.FROM_HTML_MODE_LEGACY);
        binding.summaryTitle.setText(spannedText);

        return binding.getRoot();
    }
}