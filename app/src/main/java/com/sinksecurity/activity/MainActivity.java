package com.sinksecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sinksecurity.R;
import com.sinksecurity.backend.DeviceAdapter;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeviceAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeviceManager.loadData(this);
        buildRecyclerView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_device_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               goToActivity(AddDeviceActivity.class);
            }
        });
    }

    private void buildRecyclerView(){
        recyclerView = findViewById(R.id.deviceListView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        adapter = new DeviceAdapter(DeviceManager.getDeviceList());
        adapter.setItemClickListener(new DeviceAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SinkSecurityDevice device = DeviceManager.getDevice(position);

                Intent intent = new Intent(MainActivity.this, DevicePageActivity.class);
                intent.putExtra("SinkSecurityDevice", device);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                DeviceManager.removeDevice(position);
                DeviceManager.saveData(MainActivity.this);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(controller);

        DeviceManager.setDeviceAdapter(adapter);
    }

    @Override
    public void onTopResumedActivityChanged(boolean isTopResumedActivity) {
        super.onTopResumedActivityChanged(isTopResumedActivity);

        if(adapter != null && isTopResumedActivity == true) {
            recyclerView.startLayoutAnimation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                return searchDeviceList(item);
            case R.id.action_device_page:
                return goToActivity(DevicePageActivity.class);
            case R.id.action_settings:
                return goToActivity(PreferencesActivity.class);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean searchDeviceList(MenuItem item){
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private boolean goToActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        return true;
    }

}