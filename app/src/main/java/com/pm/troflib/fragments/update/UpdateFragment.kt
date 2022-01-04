package com.pm.troflib.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.troflib.R
import com.pm.troflib.data.entities.Book
import com.pm.troflib.data.viewmodel.BookViewModel
import com.pm.troflib.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mBookViewModel: BookViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        view.updateBookName.setText(args.currentBook.name)

        setHasOptionsMenu(true)

        return view;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu_book, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_update) {
            updateBook()
        }

        if (item.itemId == R.id.menu_delete) {
            deleteBook()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateBook() {
        if (!isValid()) {
            return Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_book_name),
                    Toast.LENGTH_LONG
            ).show()
        }
        val book = Book(args.currentBook.id, updateBookName.text.toString(),updateBookGenre.text.toString(),updateBookAuthor.text.toString(),
        updateBookDatlaunch.text.toString(),updateBookPubcom.text.toString(),Integer.parseInt(updateBookNpag.toString()),updateBookEnc.text.toString())

        mBookViewModel.updateBook(book)

        Toast.makeText(
                requireContext(),
                getString(R.string.book_successfully_updated),
                Toast.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun deleteBook() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mBookViewModel.deleteBook(args.currentBook)
            Toast.makeText(
                    requireContext(),
                    getString(R.string.book_successfully_deleted),
                    Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_delete))
        builder.create().show()
    }

    private fun isValid(): Boolean {
        return !TextUtils.isEmpty(updateBookName.text.toString())
    }
}
