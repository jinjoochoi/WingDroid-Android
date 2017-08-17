package com.example.choijinjoo.wingdroid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.example.choijinjoo.wingdroid.source.remote.firebase.UserDataSource;
import com.example.choijinjoo.wingdroid.ui.MainActivity;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;

import butterknife.BindView;

import static com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper.Config.AUTH_TOKEN;

/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.imgvGithub) ImageView imgvGithub;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static final int REQUEST_CODE_OAUTH = 101;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = (firebaseAuth) -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null)
                moveToMainActivity();

        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_login;
    }

    @Override
    protected void initLayout() {
        imgvGithub.setOnClickListener(it ->
                startActivityForResult(GithubActivity.getStartIntent(this), REQUEST_CODE_OAUTH));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OAUTH && resultCode == RESULT_OK)
            signInWithCredential();

    }

    private void moveToMainActivity() {
        startActivity(MainActivity.getStartIntent(LoginActivity.this));
        finish();
    }

    private void saveUser(){
        User me = new User(FirebaseAuth.getInstance().getCurrentUser(),
                SharedPreferenceHelper.getInstance().getStringValue(this, SharedPreferenceHelper.Config.FCM_TOKEN,""));
        UserDataSource.getInstance().addUser(me);
    }

    private void signInWithCredential() {
        String token = SharedPreferenceHelper.getInstance().getStringValue(this, AUTH_TOKEN, "");
        AuthCredential credential = GithubAuthProvider.getCredential(token);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,
                        (task) -> {
                            if (task.isSuccessful()) {
                                saveUser();
                                moveToMainActivity();
                            }
                            else
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        });
    }
}
