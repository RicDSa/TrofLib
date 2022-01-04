package com.pm.troflib.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pm.troflib.R
import com.pm.troflib.data.entities.Book
import com.pm.troflib.data.viewmodel.BookViewModel
import com.pm.troflib.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var mBookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        return view;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_book, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add) {
            addProduct()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addProduct() {
        if (!isValid()) {
            return Toast.makeText(
                requireContext(),
                getString(R.string.empty_book_name),
                Toast.LENGTH_LONG
            ).show()
        }

        val book = Book(0, bookName.text.toString(),bookgenre.text.toString(),bookauthor.text.toString(),bookdatlaunch.text.toString(),bookpubcom.text.toString(),Integer.parseInt(booknpag.text.toString()),bookenc.text.toString())

        mBookViewModel.addBook(book)

        Toast.makeText(
            requireContext(),
            getString(R.string.book_successfully_added),
            Toast.LENGTH_LONG
        ).show()

        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    private fun isValid() : Boolean {
        return !(TextUtils.isEmpty(bookName.text.toString()) && TextUtils.isEmpty(bookgenre.text.toString()) && TextUtils.isEmpty(bookauthor.text.toString())
                && TextUtils.isEmpty(bookdatlaunch.text.toString()) && TextUtils.isEmpty(bookpubcom.text.toString()) && TextUtils.isEmpty(booknpag.text.toString()) && TextUtils.isEmpty(bookenc.text.toString()))
    }
}