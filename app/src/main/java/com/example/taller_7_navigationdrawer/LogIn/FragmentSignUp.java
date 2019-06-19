package com.example.taller_7_navigationdrawer.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taller_7_navigationdrawer.MainActivity;
import com.example.taller_7_navigationdrawer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentSignUp extends Fragment {

    private FirebaseAuth mAuth;
    private String TAG = "FragmentSignUp";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(final View view){
        Button btnSignUpNow = view.findViewById(R.id.btnSignUpNow);

        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtEmail = view.findViewById(R.id.txtSignEmail);
                EditText txtPassword = view.findViewById(R.id.txtSignPassword);

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (validateFields(email, password)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    startApp(view);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "No fue posible registrar el usuario.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
        });
    }
    private boolean validateFields(String email, String password){
        boolean isOk = true;
        if(email.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar un correo.", Toast.LENGTH_SHORT).show();
            isOk = false;
        }
        else
            if(password.isEmpty()){
                Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una contraseña.", Toast.LENGTH_SHORT).show();
                isOk = false;
            }

        return isOk;
    }
    private void startApp(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        //intent.putExtra("RESULT_VALUE", resultado.toString());
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
