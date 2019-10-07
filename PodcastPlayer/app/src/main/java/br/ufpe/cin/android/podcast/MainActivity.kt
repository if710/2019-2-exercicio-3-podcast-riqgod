package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init database
        val database = ItemFeedDB.getDatabase(this@MainActivity)
        //init list of items feed
        var itemsFeed = listOf<ItemFeed>()
        doAsync {
            try{
                //trying to get the URL podcast xml rss
                val xmlLink = URL("https://www.genkidama.com.br/blog/category/anikencast/feed/podcast/").readText()
                //parsing the xml to get the list of items feed
                itemsFeed = Parser.parse(xmlLink)
                //adding to the database the items
                for (item in itemsFeed) {
                    database.itemFeedDAO().addItem(item)
                    println(item)
                }

            }catch (e: Throwable){
               // Toast.makeText(ctx,"Internet error! loading stored data...",Toast.LENGTH_SHORT).show() //this line broke the code and I wasted 2h xD
                println("Internet error! loading stored data..."+e.toString())
                itemsFeed = database.itemFeedDAO().allItems()
            } finally{
                //updating the recyclerview UI
                uiThread{
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = CustomAdapter(itemsFeed, this@MainActivity )
                    recyclerView.addItemDecoration(
                        DividerItemDecoration(this@MainActivity,LinearLayoutManager.VERTICAL)
                    )
                }
            }

        }
    }
}
