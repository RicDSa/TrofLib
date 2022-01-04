package com.pm.troflib.fragments.update_book

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.troflib.R
import com.pm.troflib.api.dto.BookDto
import com.pm.troflib.api.requests.BookApi
import com.pm.troflib.api.retrofit.ServiceBuilder
import com.pm.troflib.utils.Utils.Companion.getToken
import com.pm.troflib.utils.Utils.Companion.hideKeyboard
import com.pm.troflib.utils.Utils.Companion.somethingWentWrong
import com.pm.troflib.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_update_book.*
import kotlinx.android.synthetic.main.fragment_update_book.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateBookFragment : Fragment() {
    private val args by navArgs<UpdateBookFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_book, container, false)

        setHasOptionsMenu(true)

        view.update_book_title.setText(args.currentBook.title)
        view.update_book_author.setText(args.currentBook.author)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu_book, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_delete_book) {
            deleteReport()
        }

        if (item.itemId == R.id.menu_update_book) {
            updateReport()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateReport() {
        if (TextUtils.isEmpty(update_book_title.text.toString()) || TextUtils.isEmpty(
                update_book_description.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_and_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            val request = ServiceBuilder.buildService(BookApi::class.java)
            val call = request.updateReport(
                token = "Bearer ${getToken()}",
                id = args.currentReport.id,
                title = update_book_title.text.toString(),
                description = update_report_description.text.toString()
            )

            call.enqueue(object : Callback<BookDto> {
                override fun onResponse(call: Call<BookDto>, response: Response<BookDto>) {
                    if (response.isSuccessful) {
                        val report: BookDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_updated_book),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateBookFragment_to_booksListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateBookFragment_to_userLoginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<BookDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
    }

    private fun deleteReport() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->

            val request = ServiceBuilder.buildService(BookApi::class.java)
            val call = request.deleteReport(
                token = "Bearer ${getToken()}",
                id = args.currentReport.id
            )

            call.enqueue(object : Callback<BookDto> {
                override fun onResponse(call: Call<BookDto>, response: Response<BookDto>) {
                    if (response.isSuccessful) {
                        val report: BookDto = response.body()!!

                        if(report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_deleted_book),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateBookFragment_to_reportsListFragment)
                        }
                        else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        if(response.code() == 401){
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateBookFragment_to_userLoginFragment)
                            })
                        }
                        else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<BookDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete_book))
        builder.setMessage(getString(R.string.question_delete_book))
        builder.create().show()
    }
}