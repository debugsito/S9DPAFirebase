package com.example.s9dpafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()
        val tvCurso: TextView = findViewById(R.id.tvCurso)
        val tvNota: TextView = findViewById(R.id.tvNota)
        val tvUID: TextView = findViewById(R.id.tvUID)

        db.collection("users")
            .addSnapshotListener{ snapshots, e ->
                if(e!=null){
                    Log.w("Firebase","listen:error", e)
                    return@addSnapshotListener
                }
                for (dc in snapshots!!.documentChanges){
                    when(dc.type){
                        DocumentChange.Type.ADDED -> {
                            Log.d("Firebase","data: "+ dc.document.data)
                            tvCurso.text = dc.document.data["email"].toString()
                            tvNota.text = dc.document.data["fullname"].toString()
                            tvUID.text = dc.document.data["uid"].toString()
                        }
                        DocumentChange.Type.MODIFIED -> {
                            tvCurso.text = dc.document.data["email"].toString()
                            tvNota.text = dc.document.data["fullname"].toString()
                            tvUID.text = dc.document.data["uid"].toString()
                        }
                        DocumentChange.Type.REMOVED -> Log.d("Firebase","Removed data: "+ dc.document.data)
                    }
                }
            }

    }
}