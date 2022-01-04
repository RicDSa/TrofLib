package com.pm.troflib.fragments.book_list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.troflib.R
import com.cmm.noteit_cmm.api_noteit.models.Report
import com.cmm.noteit_cmm.api_noteit.requests.ReportsApi
import com.cmm.noteit_cmm.api_noteit.retrofit.ServiceBuilder
import com.cmm.noteit_cmm.utils.Utils.Companion.getToken
import com.cmm.noteit_cmm.utils.Utils.Companion.getUserIdInSession
import com.cmm.noteit_cmm.utils.Utils.Companion.hideKeyboard
import com.cmm.noteit_cmm.utils.Utils.Companion.somethingWentWrong
import com.cmm.noteit_cmm.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_reports_list.*
import kotlinx.android.synthetic.main.fragment_reports_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksListFragment {
}