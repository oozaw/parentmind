package com.capstone.parentmind.view.video.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.capstone.parentmind.BuildConfig
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ActivityDetailVideoBinding
import com.capstone.parentmind.utils.extractVideoId
import com.capstone.parentmind.utils.makeToast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment


class DetailVideoActivity : AppCompatActivity() {
    private var _binding: ActivityDetailVideoBinding? = null
    private val binding get() = _binding!!

    private lateinit var video: ArticlesItem

    private val apiKey: String = BuildConfig.API_KEY
    private lateinit var youtubeVideoPlayer: YouTubePlayer
    private var isVideoFullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ParentMind)
        _binding = ActivityDetailVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intent.getParcelableExtra<ArticlesItem>(EXTRA_VIDEO)?.let { video = it }

        setupView()
        setupAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (youtubeVideoPlayer != null && isVideoFullscreen) {
            youtubeVideoPlayer.setFullscreen(false)
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }

    private fun setupView() {
        // youtube player
        setupYoutubePlayer()

        binding.tvVideoTitle.text = video.title
        binding.tvVideoSource.text = video.source
        binding.tvVideoDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(video.body, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(video.body)
        }
    }

    private fun setupAction() {
        binding.btnBackToolbar.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun setupYoutubePlayer() {
        @Suppress("DEPRECATION")
        val ytPlayer = fragmentManager.findFragmentById(R.id.youtube_player) as YouTubePlayerFragment

        ytPlayer.initialize(
            apiKey,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    youTubePlayer: YouTubePlayer,
                    wasRestored: Boolean
                ) {
                    youtubeVideoPlayer = youTubePlayer
                    if (!wasRestored) {
                        extractVideoId(video.link)?.let {
                            youTubePlayer.loadVideo(it)
                        }
                    }
                    youTubePlayer.play()

                    youTubePlayer.setOnFullscreenListener {
                        isVideoFullscreen = it
                    }
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider?,
                    youTubeInitializationResult: YouTubeInitializationResult?
                ) {
                    makeToast(applicationContext, "Video player initialization failed")
                }
            })
    }

    companion object {
        const val EXTRA_VIDEO = "video"
    }
}