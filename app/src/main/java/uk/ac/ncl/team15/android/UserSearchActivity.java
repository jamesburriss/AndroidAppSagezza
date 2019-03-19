package uk.ac.ncl.team15.android;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.retrofit.models.ModelUsers;
import uk.ac.ncl.team15.android.util.UserSearchResultListBuilder;

public class UserSearchActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_user_search);

        lv = findViewById(R.id.userResultList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_people, menu);
        MenuItem item = menu.findItem(R.id.impli_search);
        SearchView searchView = (SearchView)item.getActionView();

        //searchView.setOnCloseListener();
        /* searchView.setOnSearchClickListener(v -> {
            getWindow().setTitle("");
        }); */
        
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
                Call<ModelUsers> callMu = SaggezzaApplication.getRetrofitService().users(query, 1);
                callMu.enqueue(new Callback<ModelUsers>() {
                    @Override
                    public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                        if (response.code() == 200) {
                            ModelUsers responseBody = response.body();
                            List<ModelUser> users = responseBody.getUsers();

                            // TODO: Find a better way to update this list without setAdapter
                            lv.setAdapter(new UserSearchResultListBuilder(users).buildSimpleAdapter(UserSearchActivity.this));
                        }
                        else {
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
        return super.onCreateOptionsMenu(menu);
    }
}

