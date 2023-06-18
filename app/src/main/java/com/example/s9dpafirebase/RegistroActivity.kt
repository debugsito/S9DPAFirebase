package com.example.s9dpafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.s9dpafirebase.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val txtEmail: EditText = findViewById(R.id.txtEmailRegistro)
        val txtPassword: EditText = findViewById(R.id.txtPasswordRegistro)
        val txtFullname: EditText = findViewById(R.id.txtFullname)
        val btnGuardarUsuario: Button = findViewById(R.id.btnCrearUsuario)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")
        btnGuardarUsuario.setOnClickListener{
            auth
                .createUserWithEmailAndPassword(txtEmail.text.toString(),txtPassword.text.toString())
                .addOnSuccessListener { obj ->
                    Snackbar.make(findViewById(android.R.id.content),"Usuario creado"+ (obj.user?.uid
                        ?: "-"),
                        Snackbar.LENGTH_LONG).show()
                    val nuevoUser = UserModel(txtEmail.text.toString(),txtFullname.text.toString(),(obj.user?.uid
                        ?: "-"))
                    collectionRef.add(nuevoUser)
                        .addOnSuccessListener { documentReference ->
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Registro exitoso ID: ${documentReference.id}"
                                    , Snackbar.LENGTH_LONG).show()
                        }
                        .addOnFailureListener{ error ->
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Ocurrió un error: $error"
                                    , Snackbar.LENGTH_LONG).show()
                        }
                    startActivity(Intent(this,LoginActivity::class.java))
                }.addOnFailureListener{error->
                    Snackbar.make(findViewById(android.R.id.content),"Error al crear usuario",
                        Snackbar.LENGTH_LONG).show()
                }
        }

    }
}