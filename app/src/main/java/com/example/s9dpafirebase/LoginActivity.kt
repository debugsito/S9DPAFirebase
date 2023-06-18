package com.example.s9dpafirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var auth = FirebaseAuth.getInstance();
        val txtEmail = findViewById<EditText>(R.id.etEmail)
        val txtPassword = findViewById<EditText>(R.id.etPassword)
        val btn = findViewById<Button>(R.id.btnLogin)
        val btnRegisterUser = findViewById<Button>(R.id.btnRegisterNew)

        btnRegisterUser.setOnClickListener {
            startActivity(Intent(this,RegistroActivity::class.java))
        }
        btn.setOnClickListener {
            val correo = txtEmail.text.toString();
            val clave = txtPassword.text.toString();

            auth.signInWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        Snackbar.make(findViewById(android.R.id.content),"Las credenciales son invlidas",Snackbar.LENGTH_LONG).show()
                    }
                }
        }
    }
}