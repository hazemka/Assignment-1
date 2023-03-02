package com.example.assignment1

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.databinding.ContactItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ContactAdapter(var context: Context, var data: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(var binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.binding.txtContactAddress.text = data[position].address
        holder.binding.txtContactName.text = data[position].name
        holder.binding.txtContactPhone.text = data[position].number
        holder.binding.btnDelete.setOnClickListener {
            deleteContact(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun deleteContact(position:Int){
        val dialog = ProgressDialog(context)
        dialog.setMessage("Loading ...")
        dialog.setCancelable(false)
        dialog.show()
        Firebase.firestore.collection("contacts")
            .document(data[position].id)
            .delete()
            .addOnSuccessListener {
                if (dialog.isShowing) dialog.dismiss()
                data.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                if (dialog.isShowing) dialog.dismiss()
                Toast.makeText(context, "Error while deleting !", Toast.LENGTH_SHORT).show()
            }
    }
}