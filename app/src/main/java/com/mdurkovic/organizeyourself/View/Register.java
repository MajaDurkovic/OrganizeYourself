package com.mdurkovic.organizeyourself.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdurkovic.organizeyourself.DB.DatabaseHelper;
import com.mdurkovic.organizeyourself.R;

public class Register extends AppCompatActivity {

    private EditText mTextemail;
    private EditText mTextpassword;
    private EditText mTextcnfpassword;
    private Button mBtnRegister;
    private TextView mTxtViewLogin;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        mTextemail = (EditText) findViewById(R.id.email);
        mTextpassword = (EditText) findViewById(R.id.password);
        mTextcnfpassword = (EditText) findViewById(R.id.cnfpassword);
        mBtnRegister = (Button) findViewById(R.id.btnRegister);
        mTxtViewLogin = (TextView) findViewById(R.id.txtLogin);
        mTxtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(Register.this, Login.class);
                startActivity(LoginIntent);

            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = mTextemail.getText().toString().trim();
                String pass = mTextpassword.getText().toString().trim();
                String conf = mTextcnfpassword.getText().toString().trim();

                if (pass.equals(conf)) {

                    long val = db.addUser(mail, pass);
                    if (val > 0) {
                        Toast.makeText(Register.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(Register.this, Login.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(Register.this, "Error Registering", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Register.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mTextemail.addTextChangedListener(loginTextWatcher);
        mTextpassword.addTextChangedListener(loginTextWatcher);
        mTextcnfpassword.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String emailInput = mTextemail.getText().toString().trim();
            String passInput = mTextpassword.getText().toString().trim();
            String confInput = mTextcnfpassword.getText().toString().trim();

            mBtnRegister.setEnabled(!emailInput.isEmpty() && !passInput.isEmpty() && !confInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}