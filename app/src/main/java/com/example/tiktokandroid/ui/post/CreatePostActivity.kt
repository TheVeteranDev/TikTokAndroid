package com.example.tiktokandroid.ui.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tiktokandroid.MainActivity
import com.example.tiktokandroid.databinding.ActivityCreatepostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatepostBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var videoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityCreatepostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoView: VideoView = binding.newVideoView

        val pickVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                println(result.data?.data)
                videoUri = result.data?.data
                videoView.setVideoURI(videoUri)
                videoView.start()
                videoView.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                }
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        pickVideo.launch(intent)

        val cancelButton: Button = binding.newPostCancelButton
        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val nextButton: Button = binding.newPostNextButton
        supportFragmentManager.setFragmentResultListener("createPostForm", this) { _, bundle ->
            val postText = bundle.getString("postText") ?: ""
            videoUri?.let { uploadVideo(it, postText) }
        }
        nextButton.setOnClickListener {
            val postForm = CreatePostForm()
            postForm.show(supportFragmentManager, "createPostForm")
        }
    }

    private fun uploadVideo(videoUri: Uri, postText: String) {
        val progressBar: ProgressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        val fileName = UUID.randomUUID().toString() + ".mp4"
        val email = auth.currentUser?.email.toString()
        val username = email.replace(Regex("@.*"), "")
        val storageRef = storage.reference.child("$username/$fileName")
        val uploadTask = storageRef.putFile(videoUri)

        val post = Post()
        post.username = username
        post.video = "/$username/$fileName"
        post.text = postText

        uploadTask.addOnSuccessListener {
            db.collection("posts").add(post).addOnSuccessListener {
                progressBar.visibility = View.GONE
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
