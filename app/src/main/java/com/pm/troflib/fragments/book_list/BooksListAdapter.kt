package com.pm.troflib.fragments.book_list

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.troflib.R
import com.pm.troflib.api.models.Book
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_books_list.view.*

class BooksListAdapter(userIdInSession: String?) : RecyclerView.Adapter<BooksListAdapter.MyViewHolder>() {
    private var booksList = emptyList<Book>()
    private  val _userIdInSession = userIdInSession
    private  var  _ctx : Context? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _ctx = parent.context

        return BooksListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row_books_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = booksList[position]
        holder.itemView.books_list_title .text = currentItem.title
        holder.itemView.books_list_author.text = currentItem.author
        holder.itemView.books_list_genre.text = currentItem.genre
        holder.itemView.books_list_dtlaunch.text = currentItem.dtlaunch

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout_books_list.setOnClickListener {
            if(_userIdInSession == currentItem.users_id.toString()){
                val action =
                    BooksListFragmentDirections.actionBooksListFragmentToUpdateBookFragment(
                        currentItem
                    )
                holder.itemView.findNavController().navigate(action)
            }
            else {
                Toast.makeText(_ctx,R.string.only_edit_your_books, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    fun setData(booksList: List<Book>){
        this.booksList = booksList
        notifyDataSetChanged()
    }
}