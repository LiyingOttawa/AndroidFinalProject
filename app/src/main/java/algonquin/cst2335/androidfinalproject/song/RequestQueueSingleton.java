/*
 * Filename: RequestQueueSingleton.java
 * Author: Zhaoguo Han
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 * Purpose: This class represents a singleton instance of a RequestQueue, which is used to manage HTTP requests made using Volley library.
 */

package algonquin.cst2335.androidfinalproject.song;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class for managing RequestQueue instance.
 */
public class RequestQueueSingleton {

    private static RequestQueueSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * @param context The context used to create the RequestQueue instance.
     */
    private RequestQueueSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Get the singleton instance of RequestQueueSingleton.
     * @param context The context used to create the RequestQueue instance.
     * @return The singleton instance of RequestQueueSingleton.
     */
    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }
        return instance;
    }

    /**
     * Get the RequestQueue instance.
     * @return The RequestQueue instance.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // Create a new instance of RequestQueue using Volley.newRequestQueue()
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Add a request to the request queue.
     * @param req The request to be added to the request queue.
     * @param <T> The type of the request.
     */
    public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
        getRequestQueue().add(req);
    }
}
