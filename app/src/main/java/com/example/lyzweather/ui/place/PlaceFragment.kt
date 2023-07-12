package com.example.lyzweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lyzweather.MainActivity
import com.example.lyzweather.R
import com.example.lyzweather.ui.weather.WeatherActivity

class PlaceFragment:Fragment() {


    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    //新写法是什么
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is MainActivity && viewModel.isPlacedSaved()){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }


        val layoutManager = LinearLayoutManager(activity)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.layoutManager=layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        recyclerView?.adapter =adapter

        val searchPlaceEdit = view?.findViewById<EditText>(R.id.searchPlaceEdit)
        val bgImageView = view?.findViewById<ImageView>(R.id.bgImageView)

        searchPlaceEdit?.addTextChangedListener { text: Editable? ->
            val content = text.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recyclerView?.visibility = View.GONE
                bgImageView?.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        //this变为viewLifecyclerOwner
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val place = result.getOrNull()
            if (place!=null){
                recyclerView?.visibility = View.VISIBLE
                bgImageView?.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(place)
            }else{
                Toast.makeText(activity,"未能查询到此地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

}