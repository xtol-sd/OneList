package com.example.scavengerhunt;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private String username;
    private String password;
    private String email;
    private ProgressDialog progressDialog;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setupButtonCallbacks();
    }

    @Override
    public void onResume() {
        super.onResume();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null && currentUser.getObjectId() != null) {
            username = currentUser.getUsername();
            usernameEditText.setText(username);
            email = currentUser.getEmail();
            emailEditText.setText(email);
            startActivity(new Intent(this, MainMenuActivity.class));
            finish();
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private final FindCallback userFindCallback = new FindUserCallback();

    private void showToast(String message) {
        ScavengerHuntApplication.getInstance().showToast(LoginActivity.this,
                message);
    }

    private void setupButtonCallbacks() {
        usernameEditText = (EditText) findViewById(R.id.textbox_loginUsername);
        passwordEditText = (EditText) findViewById(R.id.textbox_loginPassword);
        emailEditText = (EditText) findViewById(R.id.textbox_loginEmail);
        Button continueButton = (Button) findViewById(R.id.loginbutton_continue);
        continueButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                email = emailEditText.getText().toString();
                
                if (isNotValidEntry(username)) {
                    showToast(getString(R.string.hint_username));
                    return;
                }
                if (isNotValidEntry(password)) {
                    showToast(getString(R.string.hint_password));
                    return;
                }
                if (!isValidEmail(email)) {
                    showToast(getString(R.string.hint_email));
                    return;
                }
                
                progressDialog = ProgressDialog.show(LoginActivity.this,
                        getString(R.string.label_login_please_wait),
                        getString(R.string.label_query_in_progress) + " '"
                                + username + "'");
                queryForUser();
            }
        });
        Button cancelButton = (Button) findViewById(R.id.loginbutton_cancel);
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ParseUser.logOut();
                finish();
            }
        });
    }

    private void queryForUser(){
        List<ParseQuery> parseUserQueryList = new ArrayList<ParseQuery>();
        ParseQuery parseUsernameQuery = ParseUser.getQuery();
        parseUsernameQuery.whereEqualTo("username", username);
        parseUserQueryList.add(parseUsernameQuery);
        ParseQuery parseEmailQuery = ParseUser.getQuery();
        parseEmailQuery.whereEqualTo("email", email);
        parseUserQueryList.add(parseEmailQuery);
        ParseQuery parseUserQuery = ParseQuery.or(parseUserQueryList);
        parseUserQuery.findInBackground(userFindCallback);
    }
    
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    
    private boolean isNotValidEntry(String s){
        if (s == null || s.length() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    private final SignUpCallback signinCallback = new SignUpCallback() {
        @Override
        public void done(ParseException exception) {
            dismissProgressDialog();
            if (exception == null) {
                Log.d(TAG + ".doSignUp",
                        "Success!  User account created for username="
                                + LoginActivity.this.username);
                startActivity(new Intent(LoginActivity.this,
                        MainMenuActivity.class));
                finish();
            } else {
                showToast(getString(R.string.label_signupErrorMessage) + " "
                        + getString(R.string.label_loginPleaseTryAgainMessage));
            }
        }
    };

    private void doSignUp() {
        progressDialog = ProgressDialog.show(LoginActivity.this,
                getString(R.string.label_login_please_wait),
                getString(R.string.label_signup_in_progress));
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(signinCallback);
    }

    private final LogInCallback loginCallback = new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException exception) {
            dismissProgressDialog();
            if (user != null) {
                Log.d(TAG + ".doParseLogin",
                        "Success!  Current User ObjectId: "
                                + user.getObjectId());
                startActivity(new Intent(LoginActivity.this,
                        MainMenuActivity.class));
                finish();
            } else {
                Log.d(TAG + ".doParseLogin", "Failed", exception);
                showToast(getString(R.string.label_loginErrorMessage) + " "
                        + exception.getMessage() + ".  "
                        + getString(R.string.label_loginPleaseTryAgainMessage));
            }
        }
    };

    private void doLogin() {
        progressDialog = ProgressDialog.show(LoginActivity.this,
                getString(R.string.label_login_please_wait),
                getString(R.string.label_login_in_progress) + " '" + username
                        + "'");
        ParseUser.logInInBackground(username, password, loginCallback);
    }

    class FindUserCallback extends FindCallback {
        @Override
        public void done(List<ParseObject> userList, ParseException exception) {
            dismissProgressDialog();
            if (exception == null) {
                if (userList != null && userList.size() > 0) {
                    ParseUser user = (ParseUser) userList.get(0);
                    if (username != null) {
                        String existingUsername = user.getUsername();
                        if (!username.equals(existingUsername)) {
                            usernameEditText.setText("");
                            usernameEditText.requestFocus();
                            username = null;
                            showToast(getString(R.string.label_loginUsernameAlreadyExists));
                            return;
                        }
                    }
                    if (email != null) {
                        String existingEmail = user.getEmail();
                        if (!email.equals(existingEmail)) {
                            emailEditText.setText("");
                            emailEditText.requestFocus();
                            email = null;
                            showToast(getString(R.string.label_loginEmailAlreadyExists));
                            return;
                        }
                    }
                    doLogin();
                } else
                    doSignUp();
            } else
                showToast(getString(R.string.label_signupErrorMessage) + " "
                        + getString(R.string.label_loginPleaseTryAgainMessage));
        }
    }

}