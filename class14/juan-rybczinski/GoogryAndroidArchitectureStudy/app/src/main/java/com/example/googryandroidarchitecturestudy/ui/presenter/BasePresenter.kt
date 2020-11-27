package com.example.googryandroidarchitecturestudy.ui.presenter

import android.webkit.URLUtil
import com.example.googryandroidarchitecturestudy.data.repository.MovieRepository
import com.example.googryandroidarchitecturestudy.domain.RecentSearch
import com.example.googryandroidarchitecturestudy.domain.UrlResource
import com.example.googryandroidarchitecturestudy.ui.contract.BaseContract
import java.util.*

abstract class BasePresenter(
    private val view: BaseContract.View,
    private val repository: MovieRepository
) : BaseContract.Presenter {
    override fun selectUrlItem(item: UrlResource) {
        if (URLUtil.isValidUrl(item.link)) {
            view.showUrlResource(item)
        }
    }
}