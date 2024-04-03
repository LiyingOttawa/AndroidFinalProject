/*
 * Filename: RecipesViewModel.java
 * Purpose: ViewModel class for managing recipe data.
 * Author: Liying Guo
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 */
package algonquin.cst2335.androidfinalproject.recipe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;

/**
 * ViewModel class for managing recipe data.
 */
public class RecipesViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Recipe>> recipes = new MutableLiveData<>();
}
