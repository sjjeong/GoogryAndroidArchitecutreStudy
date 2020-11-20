package kr.dktsudgg.androidarchitecturestudy

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kr.dktsudgg.androidarchitecturestudy.data.model.MovieItem
import kr.dktsudgg.androidarchitecturestudy.data.repository.NaverMovieRepositoryImpl
import kr.dktsudgg.androidarchitecturestudy.view.adapter.MovieListAdapter
import kr.dktsudgg.androidarchitecturestudy.view.ui.MovieContract
import kr.dktsudgg.androidarchitecturestudy.view.ui.MoviePresenter

class MainActivity : BaseActivity<MoviePresenter>(), MovieContract.View, View.OnClickListener {

    private lateinit var moviePresenter: MovieContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviePresenter = MoviePresenter(NaverMovieRepositoryImpl(), this);

        /**
         * 영화 검색결과 리스트 보여주는 RecyclerView에 어댑터 연결 및 목록 구분선 추가
         */
        searchedMovieList.adapter = MovieListAdapter()
        searchedMovieList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        /**
         * 검색 버튼 OnClicklistener 등록
         */
        movieSearchBtn.setOnClickListener(this)
    }

    /**
     * 클릭 이벤트 처리 메소드
     */
    override fun onClick(clickedView: View?) {
        when (clickedView?.id) {
            R.id.movieSearchBtn -> {    // 검색 버튼 클릭 시, 검색어 입력한 내용을 가지고 검색 수행
                val searchText = movieSearchEditText.text.toString()
                moviePresenter.searchMovies(searchText)
            }
            else -> {
            }
        }
    }

    override fun updateSearchedMovieList(data: List<MovieItem>) {
        (searchedMovieList.adapter as MovieListAdapter).refreshData(data)
    }

}