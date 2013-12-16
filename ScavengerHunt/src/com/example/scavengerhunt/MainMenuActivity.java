package com.example.scavengerhunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class MainMenuActivity extends Activity {
    private Button newGameButton;
    private Button playGameButton;
    private Button myGamesButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        ParseInstallation installation = ParseInstallation
                .getCurrentInstallation();
        installation.put("owner", ParseUser.getCurrentUser());
        installation.saveInBackground();
        setupButtonCallbacks();
    }

    public void onResume() {
        super.onResume();
    }

    private void setupButtonCallbacks() {
        playGameButton = (Button) findViewById(R.id.mainMenuButton_playGame);
        playGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this,
                        InvitedGamesList.class));
            }
        });

        newGameButton = (Button) findViewById(R.id.mainMenuButton_newGame);
        newGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this,
                        CreateGame.class));
            }
        });

        myGamesButton = (Button) findViewById(R.id.mainMenuButton_myGames);
        myGamesButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this,
                        MyGamesList.class));
            }
        });

    }

    // /////////////////////////////////////////////////////
    // Menu Handler
    // /////////////////////////////////////////////////////

    /**
     * The create options menu event listener. Invoked at the time to create the
     * menu.
     * 
     * @param the
     *            menu to create
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /**
     * The options item selected event listener. Invoked when a menu item has
     * been selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuitem_prefs:
            // Intent i = new Intent(mThisActivity, PrefsActivity.class);
            // mThisActivity.startActivity(i);
            return true;
        case R.id.menuitem_logout:
            ParseUser.logOut();
            finish();
            return true;
        default:
            break;
        }
        return false;
    }

}
