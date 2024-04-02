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
    // Key constants for arguments
    private static final String DICT_NAME = "dict_name";
    private static final String DICT_DEFINITION = "dict_definition";

    public DictDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of DictDetailsFragment using the provided dictionary entry details.
     *
     * @param dictName The name of the dictionary entry.
     * @param dictDefinition The definition of the dictionary entry.
     * @return A new instance of fragment DictDetailsFragment.
     */
    public static DictDetailsFragment newInstance(String dictName, String dictDefinition) {
        DictDetailsFragment fragment = new DictDetailsFragment();
        Bundle args = new Bundle();
        args.putString(DICT_NAME, dictName);
        args.putString(DICT_DEFINITION, dictDefinition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DictDetailsLayoutBinding binding = DictDetailsLayoutBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            // Get the dictionary name and definition from the arguments
            String dictName = getArguments().getString(DICT_NAME);
            String dictDefinition = getArguments().getString(DICT_DEFINITION);

            // Display the name and definition in the UI
            binding.dictNameText.setText(dictName);
            // Assuming you want to display the definition as HTML formatted text
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Spanned spannedDefinition = Html.fromHtml(dictDefinition, Html.FROM_HTML_MODE_LEGACY);
                binding.summaryTitle.setText(spannedDefinition);
            } else {
                // Use a deprecated method for older devices
                Spanned spannedDefinition = Html.fromHtml(dictDefinition);
                binding.summaryTitle.setText(spannedDefinition);
            }
        }

        return binding.getRoot();
    }
}
