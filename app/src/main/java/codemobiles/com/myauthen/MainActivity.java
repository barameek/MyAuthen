package codemobiles.com.myauthen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codemobiles.util.CMAssetBundle;
import com.codemobiles.util.UserBean;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mFBLoginBtn;
    private Button mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindWidget();
        setWidgetEvent();

        CMAssetBundle.copyAssetFile("Account.db", false, getApplicationContext());

    }

    private void setWidgetEvent() {

       mConfirmBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getApplicationContext(), "Confirm Clicked", Toast.LENGTH_SHORT).show();
               MyDBAdapter dbAdapter = new MyDBAdapter(getApplicationContext());
               //dbAdapter.insert(new UserBean("lek","1234", 0));
               //dbAdapter.update(new UserBean("lek", "aaaaa", 1));
               //UserBean result = dbAdapter.query("lek");
               //Snackbar.make(v,result.toString(),Snackbar.LENGTH_SHORT).show();

               final String _usernasme = mUsernameEditText.getText().toString();
               final String _password = mPasswordEditText.getText().toString();

               UserBean result = dbAdapter.query(_usernasme);
               if (result !=null && result.password.equals(_password)){
                   Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
               }else if (result == null){
                   Toast.makeText(getApplicationContext(), "Create an account", Toast.LENGTH_SHORT).show();
                   dbAdapter.insert(new UserBean(_usernasme,_password,0));
               }else{
                   Toast.makeText(getApplicationContext(), "Update account's password", Toast.LENGTH_SHORT).show();
                   dbAdapter.update(new UserBean(_usernasme, _password, 0));
               }

               startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
               //startActivities();

           }
       });

        mFBLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "FB Login Clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void bindWidget() {
        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText)findViewById(R.id.passwordEditText);
        mConfirmBtn = (Button)findViewById(R.id.confirmButton);
        mFBLoginBtn = (Button)findViewById(R.id.fbLoginButton);
    }


}
