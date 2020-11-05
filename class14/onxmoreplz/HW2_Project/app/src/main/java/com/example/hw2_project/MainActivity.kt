package com.example.hw2_project

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    //Naver 영화 검색 API 사용을 위한 App 등록
    private val clienId = "fQFY7M9rMOVD2KDT8Aaq"
    private val clientSecret = "v8aD8p_Ri0"

    private lateinit var editTextMovieName : EditText
    private lateinit var buttonSearch : Button
    private lateinit var recyclerView : RecyclerView

    //어댑터 선언
    private val adapter = RecyclerViewAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSearch = findViewById<Button>(R.id.button_search)
        editTextMovieName = findViewById<EditText>(R.id.edittext_movie_name)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //리사이클러뷰 동일한 크기의 아이템 사용
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        //[검색] 버튼 클릭 리스너
        buttonSearch.setOnClickListener {

            //사용자가 EditTextView에 아무값도 넣지 않았을 때
            if (editTextMovieName.text.isEmpty()) {
                Toast.makeText(this, "영화 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //API
            requestByOkhttp(editTextMovieName.text.toString())

            //키보드 내리기
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editTextMovieName.windowToken, 0)
        }
    }

    private fun requestByOkhttp(vararg movieName : String){
        //OkHttp로 요청
        val text = URLEncoder.encode("${movieName[0]}", "UTF-8")

        val url = URL("https://openapi.naver.com/v1/search/movie.json?query=${text}&display=10&start=1&genre=")

        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", clienId)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .method("GET", null)
            .build()

        //요청을 위한 Client 객체 생성
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback{

            //실패 리스너
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            //성공 리스너
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                //Gson 라이브러리 사용
                val gson = GsonBuilder().create()
                val moviefeed = gson.fromJson<MovieList>(body, MovieList::class.java)

                //어댑터 연결
                runOnUiThread{
                    adapter.movieListChange(moviefeed.items)

                    editTextMovieName.setText("")
                }
            }

        })

    }

}