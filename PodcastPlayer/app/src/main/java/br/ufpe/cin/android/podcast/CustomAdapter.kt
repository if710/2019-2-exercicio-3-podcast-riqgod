package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_episode_detail.view.*
import kotlinx.android.synthetic.main.itemlista.view.*

//Based in the THREAD class, where Card was replaced by ItemFeed and Person was modified to fit ItemFeed
public class CustomAdapter( val listItemFeed:List<ItemFeed>, val context: Context) : RecyclerView.Adapter<ItemFeedHolder>() {

    //to inflate the item layout and create the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFeedHolder{
        var inflater = LayoutInflater.from(context)
        //passing the itemlista layout
        var v = inflater.inflate(R.layout.itemlista,parent,false)
        return ItemFeedHolder(v)
    }

    override fun getItemCount(): Int {
        //getting the size of the list of item feed
        return listItemFeed.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ItemFeedHolder, position: Int) {
        //binding the item in the list in the right position
        val item = listItemFeed[position]
        holder.Bind(item,context) //call binding
         //To change body of created functions use File | Settings | File Templates.
    }

}

class ItemFeedHolder(private val feed: View) : RecyclerView.ViewHolder(feed), View.OnClickListener{
//the class that will be recycling
    //getting the reference to the proper ID about the view
    var title = feed.item_title
    var button = feed.item_action
    var date = feed.item_date
    //just saving the links and description in a variable to pass to the Activity episode detail class in the right time
    var downloadLink:String = ""
    var link:String = ""
    var description:String = ""

    //binding
    fun Bind(item:ItemFeed, context:Context){
        //updating the value of each variable
        //updating the text
        title.text = item.title
        date.text = item.pubDate
        //updating the strings
        downloadLink = item.downloadLink
        link = item.link
        description = item.description

        //making the button have a event, creating an intent to download the podcast selected
        button.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_VIEW
            intent.data = Uri.parse(downloadLink)
            it.context.startActivity(intent)
            Toast.makeText(context,"Downloading... ${item.downloadLink} <- url", Toast.LENGTH_SHORT).show()
        }
        feed.setOnClickListener(this) // passing the reference, got many errors here in the past, when in the overriding function was not updating
    }

    override fun onClick(v: View) {
        //switching screen with the new activity
        val intent = Intent(v.context, EpisodeDetailActivity::class.java)
        //passing the paremters to the activity episode detail class
        intent.putExtra("title",title.text)
        intent.putExtra("date",date.text)
        intent.putExtra("downloadLink",downloadLink)
        intent.putExtra("link",link)
        intent.putExtra("description",description)
        startActivity(v.context, intent, null)
    }
}