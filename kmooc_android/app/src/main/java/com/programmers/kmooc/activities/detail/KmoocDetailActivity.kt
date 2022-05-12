package com.programmers.kmooc.activities.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.databinding.ActivityKmookDetailBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.network.ImageLoader
import com.programmers.kmooc.utils.DateUtil
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocDetailViewModel
import com.programmers.kmooc.viewmodels.KmoocDetailViewModelFactory

class KmoocDetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PARAM_COURSE_ID = "param_course_id"
    }

    private lateinit var binding: ActivityKmookDetailBinding
    private lateinit var viewModel: KmoocDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { finish() }

        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocDetailViewModelFactory(kmoocRepository)).get(
            KmoocDetailViewModel::class.java
        )

        val courseId = intent.getStringExtra(INTENT_PARAM_COURSE_ID)
        if(courseId == null || courseId.isEmpty()){
            finish()
            return
        }

        binding = ActivityKmookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setDetailInfo(lecture: Lecture){
        binding.webView.loadData(lecture.overview ?: "","text/html","UTF-8")
        binding.webView.visibility = (lecture.overview ?.isEmpty() == false).toVisibility()
    }
}