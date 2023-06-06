package com.example.nyumbakumi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    lateinit var cardAddHouses:CardView
    lateinit var cardViewHouses:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        cardAddHouses = findViewById(R.id.mCardAddHouses)
        cardViewHouses = findViewById(R.id.mCardViewHouses)
        cardAddHouses.setOnClickListener {
            startActivity(Intent(applicationContext,AddhousesActivity::class.java))
        }
        cardViewHouses.setOnClickListener {

        }
    }
}