package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.adapter.UserListAdapter;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.retrofit.models.ModelUsers;

public class UserSearchActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        lv = findViewById(R.id.userResultList);

        lv.setOnItemClickListener((adapter, v, position, id) -> {
            ModelUser mu = (ModelUser) lv.getItemAtPosition(position);
            int selectedUserId = mu.getId();

            Intent profileIntent = new Intent(UserSearchActivity.this, UserProfileActivity.class);
            profileIntent.putExtra("_userId", selectedUserId);
            startActivity(profileIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_people, menu);
        MenuItem item = menu.findItem(R.id.impli_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQuery(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter.getFilter().filter(newText);
                return onQuery(newText);
            }

            private boolean onQuery(String query) {
                Call<ModelUsers> callMu = SaggezzaApplication.getInstance().getRetrofitService().users(query, null, 1);
                callMu.enqueue(new Callback<ModelUsers>() {
                    @Override
                    public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                        if (response.code() == 200) {
                            ModelUsers responseBody = response.body();
                            List<ModelUser> users = responseBody.getUsers();

                            // TODO: Find a better way to update this list without setAdapter
                            lv.setAdapter(new UserListAdapter(UserSearchActivity.this, users));
                        } else {
                            Toast.makeText(UserSearchActivity.this, "Error performing search", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelUsers> call, Throwable throwable) {
                        Toast.makeText(UserSearchActivity.this, "Error performing search", Toast.LENGTH_LONG).show();
                    }
                });
                // adapter.clear(); // NOTE: Uncomment this to clear list while waiting for network

                return false;
            }
        });

        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        return super.onCreateOptionsMenu(menu);
    }
}

