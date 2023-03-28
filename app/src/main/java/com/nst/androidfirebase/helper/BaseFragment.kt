package com.nst.androidfirebase.helper

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

class BaseFragment : Fragment() {

    fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm= activity?.getSystemService(context.INPUT_METHOD_SERVICE) AS InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}




