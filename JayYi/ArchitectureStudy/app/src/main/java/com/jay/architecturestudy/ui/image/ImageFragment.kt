package com.jay.architecturestudy.ui.image

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jay.architecturestudy.R
import com.jay.architecturestudy.model.Image
import com.jay.architecturestudy.model.ResponseNaverQuery
import com.jay.architecturestudy.network.Api
import com.jay.architecturestudy.ui.BaseFragment
import com.jay.architecturestudy.util.toPx
import com.jay.architecturestudy.widget.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragemnt_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageFragment(layoutId: Int = R.layout.fragemnt_image) : BaseFragment(layoutId) {

    private lateinit var imageAdapter: ImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            imageAdapter = ImageAdapter(activity)
                .also {
                    recycler_view.run {
                        adapter = it
                        layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                        addItemDecoration(SpacesItemDecoration(12.toPx(), 6.toPx(), 11.toPx()))
                    }
                }
        }
    }

    override fun search(keyword: String) {
        Api.getImages(keyword)
            .enqueue(object : Callback<ResponseNaverQuery<Image>> {
                override fun onFailure(call: Call<ResponseNaverQuery<Image>>, t: Throwable) {
                    Log.e("Image", "error=${t.message}")
                }

                override fun onResponse(
                    call: Call<ResponseNaverQuery<Image>>,
                    response: Response<ResponseNaverQuery<Image>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body() ?: return
                        imageAdapter.setData(body.items)
                    }
                }

            })
    }
}