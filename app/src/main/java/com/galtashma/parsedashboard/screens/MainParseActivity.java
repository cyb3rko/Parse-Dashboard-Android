package com.galtashma.parsedashboard.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.galtashma.parsedashboard.ParseServerConfig;
import com.galtashma.parsedashboard.ParseServerConfigStorage;
import com.galtashma.parsedashboard.R;
import com.galtashma.parsedashboard.adapters.ParseAppsAdapter;

import java.util.List;

public class MainParseActivity extends AppCompatActivity implements MaterialDialog.SingleButtonCallback {

    private MaterialDialog dialog = null;
    private ParseServerConfigStorage storage;

    private LinearLayout emptyStateLayout;
    private ListView configuredServersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });

        emptyStateLayout = (LinearLayout) findViewById(R.id.empty_state_layout);
        configuredServersView = (ListView) findViewById(R.id.configured_servers_list);

        storage = new ParseServerConfigStorage(getApplicationContext());
        Log.i("TAG", storage.getServers().toString());

        toggleMainScreen(isMainScreenEmpty());

        List<ParseServerConfig> list = storage.getServers();
        ParseAppsAdapter adapter = new ParseAppsAdapter(this, list);
        configuredServersView.setAdapter(adapter);
    }

    private boolean isMainScreenEmpty(){
        return storage.getServers().isEmpty();
    }

    private void toggleMainScreen(boolean isEmpty){
        if(isEmpty){
            configuredServersView.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            configuredServersView.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }

    private void showDialog(){
        this.dialog = new MaterialDialog.Builder(this)
                .title("Add Parse Server")
                .customView(R.layout.add_app_dialog, true)
                .positiveText("OK")
                .onPositive(this)
                .show();
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (dialog != null){
            View v = dialog.getCustomView();
            EditText appName = v.findViewById(R.id.inputAppName);
            EditText appId = v.findViewById(R.id.inputAppId);
            EditText masterKey = v.findViewById(R.id.inputAppMasterKey);
            EditText serverUrl = v.findViewById(R.id.inputServerUrl);

            ParseServerConfig serverConfig = new ParseServerConfig(
                    appName.getText().toString(),
                    appId.getText().toString(),
                    masterKey.getText().toString(),
                    serverUrl.getText().toString());

            storage.saveServer(serverConfig);
        }
    }
}
