package com.furniconbreeze.features.NewQuotation.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furniconbreeze.R
import com.furniconbreeze.app.domain.NewOrderGenderEntity
import com.furniconbreeze.features.NewQuotation.adapter.TaxListAdapter
import com.furniconbreeze.features.NewQuotation.interfaces.TaxOnclick
import com.furniconbreeze.features.viewAllOrder.interf.GenderListOnClick
import com.furniconbreeze.widgets.AppCustomTextView

class FreightListDialog: DialogFragment() {

    private lateinit var header: AppCustomTextView
    private lateinit var close: ImageView
    private lateinit var rv_gender: RecyclerView
    private  var adapter: TaxListAdapter? = null
    private lateinit var mContext: Context

    companion object{
        private lateinit var onSelectItem: (String) -> Unit
        private var mFreightList: ArrayList<String>? = null

        fun newInstance(gList: ArrayList<String>, function: (String) -> Unit): FreightListDialog {
            val dialogFragment = FreightListDialog()
            mFreightList = gList
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
        dialog?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.dialog_tax_list, container, false)

        isCancelable = false

        initView(v)
        return v
    }

    private fun initView(v: View){
        header=v.findViewById(R.id.tv_dialog_list_header)
        close=v.findViewById(R.id.iv_dialog_gender_list_close_icon)
        rv_gender=v.findViewById(R.id.rv_dialog_list)
        rv_gender.layoutManager = LinearLayoutManager(mContext)

        header.text="Select Freight"

        adapter=TaxListAdapter(mContext, mFreightList!!,object: TaxOnclick {
            override fun OnClick(obj: String) {
                dismiss()
                onSelectItem(obj)
            }
        })
        rv_gender.adapter=adapter

        close.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                dismiss()
            }
        }
    }

}