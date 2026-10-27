package com.MarcL.agenda_recyclerview

import com.MarcL.agenda_recyclerview.ContactAdapter.ItemClickListener
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // data to populate the RecyclerView with
        val contactList = ArrayList<Contact>()
        val nombres = arrayOf("Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Sofía", "David", "Elena")
        val apellidos = arrayOf("Pérez", "López", "Gómez", "Rodríguez", "Martínez", "García", "Sánchez", "Fernández", "Díaz", "Ruiz")
        for (i in 1..40){
            val nombre = nombres.random()
            val apellido = apellidos.random()
            val nombreCompleto = nombre + " " + apellido

            val telefonoAleatorio = (600000000..799999999).random().toString()

            contactList.add(Contact(nombreCompleto, telefonoAleatorio))
        }

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvContacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(this, contactList)

        adapter.setClickListener(this)
        recyclerView.adapter = adapter
    }

    public override fun onItemClick(view: View?, position: Int) {
        val contacto = adapter.getItem(position)

        Toast.makeText(
            this,
            "You clicked: " + contacto.name,
            Toast.LENGTH_SHORT
        ).show()
    }
}