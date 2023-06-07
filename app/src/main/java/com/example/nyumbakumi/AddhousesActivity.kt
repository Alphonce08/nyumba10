package com.example.nyumbakumi

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class AddhousesActivity : AppCompatActivity() {
    lateinit var edtHouseNumber:EditText
    lateinit var edtHouseSize:EditText
    lateinit var edtHousePrice:EditText
    lateinit var imageview:ImageView
    lateinit var btnUpload:Button
    lateinit var progress:ProgressDialog
    val PICK_IMAGE_REQUEST = 100
    lateinit var filepath:Uri
    lateinit var firebaseStore:FirebaseStorage
    lateinit var storageReference:StorageReference
    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhouses)
        edtHouseNumber = findViewById(R.id.mEdtHouseNumber)
        edtHousePrice = findViewById(R.id.mEdtprice)
        edtHouseSize = findViewById(R.id.mEdtSize)
        imageview = findViewById(R.id.mimage)
        btnUpload = findViewById(R.id.mBtnUpload)
        progress = ProgressDialog(this)
        progress.setTitle("Upload")
        progress.setMessage("Please wait...")

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = firebaseStore.getReference()
        mAuth = FirebaseAuth.getInstance()
        imageview.setOnClickListener {
            //open the gallery to select an image
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Select House picture"), PICK_IMAGE_REQUEST)
        }
        btnUpload.setOnClickListener {
            var houseNumber = edtHouseNumber.text.toString().trim()
            var houseSize = edtHouseSize.text.toString().trim()
            var housePrice = edtHousePrice.text.toString().trim()
            var imageId = System.currentTimeMillis().toString()
            var userId = mAuth.currentUser?.uid
            //check if the user is submitting empty fields
            if (houseNumber.isEmpty()){
                edtHouseNumber.setError("Please wait...")
                edtHouseNumber.requestFocus()
            }else if (housePrice.isEmpty()){
                edtHousePrice.setError("Please wait...")
                edtHousePrice.requestFocus()
            }else if (houseSize.isEmpty()){
                edtHouseSize.setError("Please wait...")
                edtHouseSize.requestFocus()
            } else{
                //proceed to upload data
                if (filepath == null){
                    Toast.makeText(applicationContext, "Choose Image", Toast.LENGTH_SHORT).show()
                }else {
                    var ref = storageReference.child("Houses/$imageId")
                    progress.show()
                    ref.putFile(filepath).addOnCompleteListener {
                        progress.dismiss()
                    if (it.isSuccessful){
                        //proceed to store other data into the db
                        ref.downloadUrl.addOnSuccessListener {
                            var imageUrl = it.toString()
                            var houseData = House(houseNumber,houseSize,housePrice,userId!!,imageId,imageUrl)
                            var dbRef = FirebaseDatabase.getInstance()
                                .getReference().child("Houses/$imageId")
                            dbRef.setValue(houseData)
                            Toast.makeText(applicationContext, "Upload successful", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,it.exception!!.message,Toast.LENGTH_SHORT).show()
                    }
                    }

                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK)
            if (data == null || data.data == null){
                return
            }
        filepath = data!!.data!!
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            imageview.setImageBitmap(bitmap)
        }catch (e:IOException){
            e.printStackTrace()
        }
    }
}