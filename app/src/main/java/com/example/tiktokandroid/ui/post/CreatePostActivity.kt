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

        // Initialize the video view where the user picked video will display
        val videoView: VideoView = binding.newVideoView

        // Sets up the video picker to return a result
        // If that result is OK, set the videoUri to the video picked
        val pickVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                videoUri = result.data?.data

                // Sets the uri to the video view and plays/loops it
                videoView.setVideoURI(videoUri)
                videoView.start()
                videoView.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                }
            } else {
                // If the user hits the back arrow in the picker
                // acts like cancel and sends them back to the main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Opens the video picker for the user to select a video to post
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        pickVideo.launch(intent)

        // Cancel button will send user to main activity if clicked
        val cancelButton: Button = binding.newPostCancelButton
        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Next button opens the dialog for the user to input text for the post
        val nextButton: Button = binding.newPostNextButton

        // sets up a listener to return the text typed in by the user
        supportFragmentManager.setFragmentResultListener("createPostForm", this) { _, bundle ->
            val postText = bundle.getString("postText") ?: ""

            // submits the video and text from the dialog to be uploaded
            videoUri?.let { uploadVideo(it, postText) }
        }

        // When next button is clicked opens the post text dialog.
        nextButton.setOnClickListener {
            val postForm = CreatePostForm()
            postForm.show(supportFragmentManager, "createPostForm")
        }
    }

    /**
     * Uploads the video to firebase storage and inserts a new document
     * in firebase datastore
     */
    private fun uploadVideo(videoUri: Uri, postText: String) {
        // Make the progress bar visible so the user knows its saving
        val progressBar: ProgressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        // Set the filename as a random UUID
        // This takes naming the file out of the users hands and
        // allows for uploading of the same video to another post
        val fileName = UUID.randomUUID().toString() + ".mp4"

        // Set the email to be the current firebase user email
        val email = auth.currentUser?.email.toString()

        // set the username for the post to everything before
        // the @ symbol in the email
        val username = email.replace(Regex("@.*"), "")

        // StorageRef is the path to save the video to in firebase storage
        // username being the folder, then we tie all videos a user uploaded
        // to their account
        val storageRef = storage.reference.child("$username/$fileName")
        val uploadTask = storageRef.putFile(videoUri)

        // Create the post object and set all the values
        val post = Post()
        post.username = username
        post.video = "/$username/$fileName"
        post.text = postText

        // Upload the video
        uploadTask.addOnSuccessListener {
            db.collection("posts").add(post).addOnSuccessListener {
                // Hide the progress bar again
                progressBar.visibility = View.GONE

                // Send the user back to the Main Activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
