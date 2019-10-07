package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class EpisodeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)
        //getting the UI elements by the ID's
        val titleText = findViewById<TextView>(R.id.title)
        val descriptionText = findViewById<TextView>(R.id.description)
        val link = findViewById<TextView>(R.id.link)
        val downloadLinkText = findViewById<TextView>(R.id.downloadLink)
        val dateText = findViewById<TextView>(R.id.date)

        //updating the UI elements with the parameters passed by the CustomAdapter onClick method
        dateText.text = "Date: "+intent.getStringExtra("date")
        downloadLinkText.text = "download link: "+intent.getStringExtra("downloadLink")
        link.text = "Link: "+intent.getStringExtra("link")
        descriptionText.text = "Description: "+intent.getStringExtra("description")
        titleText.text = intent.getStringExtra("title")

    }
}
