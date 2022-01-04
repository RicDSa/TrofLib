package com.pm.troflib.fragments.book.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pm.troflib.R
import com.pm.troflib.api.dto.BookDto
import com.pm.troflib.api.requests.BookApi
import com.pm.troflib.api.retrofit.ServiceBuilder
import com.pm.troflib.utils.Utils.Companion.getToken
import com.pm.troflib.utils.Utils.Companion.getUserIdInSession
import com.pm.troflib.utils.Utils.Companion.hideKeyboard
import com.pm.troflib.utils.Utils.Companion.somethingWentWrong
import com.pm.troflib.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_add_book.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBookFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_book, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_book, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add_book) {
            addBook()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addBook() {
        if (TextUtils.isEmpty(add_book_title.text.toString()) || TextUtils.isEmpty(
                add_book_author.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_and_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            llProgressBar.bringToFront()
            llProgressBar.visibility = View.VISIBLE

            val request = ServiceBuilder.buildService(BookApi::class.java)
            val call = request.createBook(
                token = "Bearer ${getToken()}",
                users_id = getUserIdInSession(),
                title = add_book_title.text.toString(),
                author = add_book_author.text.toString(),
                genre = add_book_genre.text.toString(),
                dtlaunch = add_book_dtlaunch.text.toString(),
                publishcompany = add_book_publishcompany.text.toString(),
                npages = Integer.parseInt(add_book_npages.text.toString()),
                cover = add_book_cover.text.toString()
            )

            call.enqueue(object : Callback<BookDto> {
                override fun onResponse(call: Call<BookDto>, response: Response<BookDto>) {
                    llProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val book: BookDto = response.body()!!

                        if (book.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_added_new_book),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addBookFragment_to_booksListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        book.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_addBookFragment_to_userLoginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<BookDto>, t: Throwable) {
                    llProgressBar.visibility = View.GONE
                    somethingWentWrong()
                }
            })
        }
    }
}