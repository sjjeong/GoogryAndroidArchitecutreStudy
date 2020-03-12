package com.byiryu.study.ui

import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byiryu.study.R
import com.byiryu.study.model.data.MovieItem
import kotlinx.android.synthetic.main.view_main_item.view.*

class MainRecyclerHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun binding(item: MovieItem) {
        Glide.with(itemView.image)
            .load(item.image)
            .error(R.color.colorPrimaryDark)
            .placeholder(R.color.colorPrimaryDark)
            .into(itemView.image)

        itemView.title.text = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
        itemView.rating.rating = item.userRating / 2f
        itemView.publishDate.text = item.pubDate
        itemView.director.text = item.director
        itemView.actor.text = item.actor
    }
}