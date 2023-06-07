package com.example.nyumbakumi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class ViewhousesActivity : AppCompatActivity() {
    lateinit var listHouses:ListView
    lateinit var houses:ArrayList<House>
    lateinit var adapter:CustomAdapter
    lateinit var progress:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewhouses)
        listHouses = findViewById(R.id.mListHouses)
        houses = ArrayList()
        adapter = CustomAdapter(this,houses)
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
        var ref = FirebaseDatabase.getInstance()
            .getReference().child("Houses")
        progress.show()
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                houses.clear()
                for (snap in snapshot.children){
                    var nyumba = snap.getValue(House::class.java)
                    houses.add(nyumba!!)
                    adapter.notifyDataSetChanged()
                    progress.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "DB inaccessible", Toast.LENGTH_SHORT).show()
            }
        })
        listHouses.adapter = adapter
    }
}