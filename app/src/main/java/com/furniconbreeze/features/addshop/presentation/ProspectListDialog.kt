package com.furniconbreeze.features.addshop.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.furniconbreeze.R
import com.furniconbreeze.app.Pref
import com.furniconbreeze.app.domain.ProspectEntity
import com.furniconbreeze.app.domain.StageEntity
import com.furniconbreeze.widgets.AppCustomEditText
import com.furniconbreeze.widgets.AppCustomTextView


class ProspectListDialog: DialogFragment() {

    private lateinit var rv_common_dialog_list: RecyclerView
    private lateinit var mContext: Context
    private lateinit var dialog_header_TV: AppCustomTextView
    private lateinit var et_search: AppCustomEditText
    private var adapter: ProspectListAdapter? = null
    private lateinit var iv_close_icon: ImageView

    companion object {

        private lateinit var onSelectItem: (ProspectEntity) -> Unit
        private var mProsList: ArrayList<ProspectEntity>? = null

        fun newInstance(stageList: ArrayList<ProspectEntity>?, function: (ProspectEntity) -> Unit): ProspectListDialog {
            val dialogFragment = ProspectListDialog()
            mProsList = stageList
            onSelectItem = function
            return dialogFragment
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(true)

        val v = inflater.inflate(R.layout.dialog_list, container, false)

        isCancelable = false

        initView(v)
        initTextChangeListener()
        return v
    }

    private fun initView(v: View) {
        dialog_header_TV = v.findViewById(R.id.dialog_header_TV)
        rv_common_dialog_list = v.findViewById(R.id.rv_common_dialog_list)
        rv_common_dialog_list.layoutManager = LinearLayoutManager(mContext)
        adapter = ProspectListAdapter(mContext, mProsList, { pros: ProspectEntity ->
            dismiss()
            //AppUtils.hideSoftKeyboardFromDialog((mContext as DashboardActivity))
            onSelectItem(pros)
        })

        rv_common_dialog_list.adapter = adapter

        /*6-12-2021*/
        if(Pref.IsnewleadtypeforRuby){
            dialog_header_TV.text = "Prospect"
        }
        else{
            dialog_header_TV.text = "Stage"
        }

//        dialog_header_TV.text = "Stage"
        et_search = v.findViewById(R.id.et_search)
        iv_close_icon = v.findViewById(R.id.iv_close_icon)

        iv_close_icon.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                dismiss()
            }
        }
    }

    private fun initTextChangeListener() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(et_search.text.toString().trim()) && et_search.text.toString().trim().length >= 3)
                    adapter?.filter?.filter(et_search.text.toString().trim())
            }
        })
    }
}