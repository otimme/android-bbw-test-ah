package nl.ah.test.bbw

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeshared.model.MediaClip

class BlueBillyWigVideoActivity : AppCompatActivity(), BBNativePlayerViewDelegate {

    private var view: FrameLayout? = null
    private var playerView: BBNativePlayerView? = null
    private var length: Long? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPlayer()
        initView()
        setContentView(view)
        playerView?.player?.play()
    }

    override fun didTriggerMediaClipLoaded(playerView: BBNativePlayerView, clipData: MediaClip?) {
        super.didTriggerMediaClipLoaded(playerView, clipData)
        val clipLength = clipData?.length?.toLong() ?: 0L
        val clipId = clipData?.id.orEmpty()
        length = clipLength
        id = clipId
    }

    private fun initView() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK
        view = FrameLayout(this).apply {
            setBackgroundColor(Color.BLACK)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addView(playerView)
        }
    }

    private fun initPlayer() {
        playerView = BBNativePlayer.createPlayerView(
            context = this,
            jsonUrl = "https://allerhande.bbvms.com/p/home_app_playout_nl/c/6177239.json",
        ).also {
            it.delegate = this
        }
    }

    override fun onPause() {
        playerView?.player?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        playerView?.let {
            it.player?.pause()
            it.delegate = null
            it.removeAllViews()
            it.destroy()
        }
        playerView = null
        view?.removeAllViews()
        view = null
        super.onDestroy()
    }
}
