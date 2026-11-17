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
    val contactList = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val fabAdd = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddContact)
        fabAdd.setOnClickListener {
            mostrarDialogoNuevoContacto()
        }
    }

    public override fun onItemClick(view: View?, position: Int) {
        val contacto = adapter.getItem(position)

        Toast.makeText(
            this,
            "You clicked: " + contacto.name,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun mostrarDialogoNuevoContacto() {
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val inputNombre = android.widget.EditText(this).apply {
            hint = "Nombre y apellidos"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS
        }

        val inputTelefono = android.widget.EditText(this).apply {
            hint = "Teléfono"
            inputType = android.text.InputType.TYPE_CLASS_PHONE
        }

        layout.addView(inputNombre)
        layout.addView(inputTelefono)

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Nuevo contacto")
            .setView(layout)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = inputNombre.text.toString().trim()
                val telefono = inputTelefono.text.toString().trim()

                if (nombre.isNotEmpty() && telefono.isNotEmpty()) {
                    contactList.add(Contact(nombre, telefono))
                    val nuevaPosicion = contactList.size - 1
                    adapter.notifyItemInserted(nuevaPosicion)
                    recyclerView.scrollToPosition(nuevaPosicion)
                } else {
                    Toast.makeText(this, "Rellena ambos campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

}