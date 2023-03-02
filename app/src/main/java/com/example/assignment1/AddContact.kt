package com.example.assignment1

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment1.databinding.ActivityAddContactBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddContact : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Add Contact"

        dialog = ProgressDialog(this)
        dialog.setMessage("Loading ...")
        dialog.setCancelable(false)

        binding.btnSave.setOnClickListener {
            if (binding.txtConAddress.text.isNotEmpty() && binding.txtConNumber.text.isNotEmpty()
                && binding.txtConName.text.isNotEmpty()) {
                addContact(Contact("",binding.txtConName.text.toString(), binding.txtConNumber.text.toString(),
                    binding.txtConAddress.text.toString()))
            }else{
                Toast.makeText(this, "Please, All info are required !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addContact(contact: Contact){
        dialog.show()
        Firebase.firestore.collection("contacts")
            .document()
            .set(contact)
            .addOnSuccessListener {
                if (dialog.isShowing) dialog.dismiss()
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                if (dialog.isShowing) dialog.dismiss()
                Toast.makeText(this, "Error while saving contact !", Toast.LENGTH_SHORT).show()
            }
    }
}