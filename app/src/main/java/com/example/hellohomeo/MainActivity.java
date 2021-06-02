package com.example.hellohomeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hellohomeo.database.User;
import com.example.hellohomeo.database.UserViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    View rootLayout;
    RecyclerView rv;
    UserAdapter adapter;
    Button refreshButton, deleteButton;

    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);
        adapter = new UserAdapter();
        rv.setAdapter(adapter);

        viewModel.getAllUsers().observe(this, users -> {
            if(users != null){
                adapter.updateUsers(new ArrayList(users));
            }
                }
        );

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchData();
                Toast.makeText(MainActivity.this, "Refreshing ", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void init() {
        rootLayout = findViewById(R.id.rootLayout);
        rv = findViewById(R.id.recyclerView);
        refreshButton = findViewById(R.id.refreshButton);
        deleteButton = findViewById(R.id.deleteButton);
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserViewModel.class);
    }

    void fetchData() {
        String url = "https://api.spacexdata.com/v4/crew";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        ArrayList<User> userList = new ArrayList();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                User userData = new User(jsonObject.getString("name"),
                                        jsonObject.getString("agency"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("wikipedia"),
                                        jsonObject.getString("status"));
                                viewModel.insert(userData) ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

//                        adapter.updateUsers(userList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching details", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);


    }
}