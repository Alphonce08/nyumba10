package com.example.nyumbakumi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(var context: Context, var data:ArrayList<House>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtHouseNumber:TextView
        var mTxtHouseSize:TextView
        var mTxtHousePrice:TextView
        var imgHousePic:ImageView
        var btnDelete:Button
        var btnUpdate:Button
        

        init {
            this.mTxtHouseNumber = row?.findViewById(R.id.mTvHousesNumber) as TextView
            this.mTxtHousePrice = row?.findViewById(R.id.mTvHousePrice) as TextView
            this.mTxtHouseSize = row?.findViewById(R.id.mTvHousesize) as TextView
            this.imgHousePic = row?.findViewById(R.id.mimgHousePic) as ImageView
            this.btnDelete = row?.findViewById(R.id.mBtnDelete) as Button
            this.btnUpdate = row?.findViewById(R.id.mBtnupdate) as Button
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.house_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:House = getItem(position) as House
        viewHolder.mTxtHouseNumber.text = item.houseNumber
        viewHolder.mTxtHousePrice.text = item.housePrice
        viewHolder.mTxtHouseSize.text = item.houseSize
        Glide.with(context).load(item.houseImage).into(viewHolder.imgHousePic)
        viewHolder.btnDelete.setOnClickListener {
            var delRef = FirebaseDatabase.getInstance()
                .getReference().child("Houses/"+item.houseId)
            delRef.removeValue()
        }
        viewHolder.btnUpdate.setOnClickListener {

        }
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}