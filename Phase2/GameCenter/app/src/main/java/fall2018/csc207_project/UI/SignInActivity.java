package fall2018.csc207_project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fall2018.csc207_project.GameCenter.GlobalCenter;

public class SignInActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc207_project.R.layout.signin_page);
        globalCenter = (GlobalCenter)getIntent().getSerializableExtra("GlobalCenter");
        addLoginButtonListener();
    }

    private void addLoginButtonListener() {
        Button loginButton = findViewById(fall2018.csc207_project.R.id.signin_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSignIn();
            }
        });
    }

    private void checkSignIn() {
        EditText User = findViewById(fall2018.csc207_project.R.id.signin_username);
        EditText Password = findViewById(fall2018.csc207_project.R.id.signin_password);
        String userName = User.getText().toString();
        String password = Password.getText().toString();
        if(globalCenter.signIn(userName, password)){
            localCenter();
        }else{
            Toast.makeText(getApplicationContext(), "Login Failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void localCenter(){
        Intent tmp = new Intent(this, LocalCenterActivity.class);
        tmp = tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        globalCenter.saveAll(getApplicationContext());
    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, GlobalActivity.class);
        startActivity(tmp);
        finish();
    }
}
