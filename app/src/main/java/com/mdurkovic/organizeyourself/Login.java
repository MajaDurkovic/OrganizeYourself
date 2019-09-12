package com.mdurkovic.organizeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText mTextemail;
    EditText mTextpassword;
    Button mBtnLogin;
    TextView mTxtRegister;

    DatabaseHelp db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelp(this);

        mTextemail = (EditText)findViewById(R.id.email);
        mTextpassword = (EditText)findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mTxtRegister = (TextView)findViewById(R.id.txtRegister);
        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, Register.class );
                startActivity(registerIntent);
            }
        });



        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mTextemail.getText().toString().trim();
                String pass = mTextpassword.getText().toString().trim();
                boolean res = db.checkUser(mail, pass);
                if(res == true)
                {
                    Intent mainActivityIntent = new Intent(Login.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                }
                else
                {
                    Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
                }


            }
        });



// ograničenje na gumb za prijavu - ako nisu sva polja popunjena(!isEmpty()) ne može se kliknuti na njega
        mTextemail.addTextChangedListener(loginTextWatcher);
        mTextpassword.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String emailInput = mTextemail.getText().toString().trim();
            String passInput = mTextpassword.getText().toString().trim();

            mBtnLogin.setEnabled(!emailInput.isEmpty() && !passInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

}


