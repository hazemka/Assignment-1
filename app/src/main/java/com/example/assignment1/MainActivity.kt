package com.example.assignment1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment1.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Contacts"

        dialog = ProgressDialog(this)
        dialog.setMessage("Loading ...")
        dialog.setCancelable(false)

        binding.btnAddContact.setOnClickListener {
            startActivity(Intent(this, AddContact::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getContacts()
    }

    private fun getContacts(){
        dialog.show()
        val contacts = ArrayList<Contact>()
        Firebase.firestore.collection("contacts")
            .get()
            .addOnSuccessListener {
                for (i in it){
                    contacts.add(Contact(i.id,i.getString("name")!!
                    ,i.getString("number")!!,i.getString("address")!!))
                }
                val contactAdapter = ContactAdapter(this,contacts)
                binding.rvContacts.adapter = contactAdapter
                binding.rvContacts.layoutManager = LinearLayoutManager(this)
                if (dialog.isShowing) dialog.dismiss()
            }
            .addOnFailureListener {
                if (dialog.isShowing) dialog.dismiss()
                Toast.makeText(this, "Error while loading contacts", Toast.LENGTH_SHORT).show()
            }
    }
}