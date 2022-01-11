package com.pm.troflib.fragments.book_list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.troflib.R
import com.pm.troflib.api.models.Book
import com.pm.troflib.api.requests.BookApi
import com.pm.troflib.api.retrofit.ServiceBuilder
import com.pm.troflib.utils.Utils.Companion.getToken
import com.pm.troflib.utils.Utils.Companion.getUserIdInSession
import com.pm.troflib.utils.Utils.Companion.hideKeyboard
import com.pm.troflib.utils.Utils.Companion.somethingWentWrong
import com.pm.troflib.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_books_list.*
import kotlinx.android.synthetic.main.fragment_books_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksListFragment : Fragment(){
    private  var  _view : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books_list, container, false)
        _view = view

        setHasOptionsMenu(true)

        getAndSetData(view)

        view.btn_add_new_book_from_books_list.setOnClickListener() {
            findNavController().navigate(R.id.action_booksListFragment_to_addBookFragment)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.books_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.user_logout) {
            logout()
        }

        if(item.itemId == R.id.books_list_refresh){
            _view?.let { getAndSetData(it) }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getAndSetData(view: View) {

        view.llProgressBarList.bringToFront()
        view.llProgressBarList.visibility = View.VISIBLE


        val adapter = BooksListAdapter(getUserIdInSession())

        val recyclerView = view.recyclerview_books_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(BookApi::class.java)
        val call = request.getBooks(token = "Bearer ${getToken()}")

        call.enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {

                llProgressBarList.visibility = View.GONE

                if (response.isSuccessful) {
                    val books: List<Book> = response.body()!!
                    adapter.setData(books)
                } else {
                    if (response.code() == 401) {
                        unauthorized(navigatonHandlder = {
                            findNavController().navigate(R.id.action_booksListFragment_to_userLoginFragment)
                        })
                    } else {
                        somethingWentWrong()
                    }
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                llProgressBarList.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
            preferences.edit().putString("token", null).apply()
            findNavController().navigate(R.id.action_booksListFragment_to_userLoginFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString((R.string.logout_question)))
        builder.create().show()
    }
}