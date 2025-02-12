package com.furniconbreeze.features.nearbyshops.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import androidx.fragment.app.DialogFragment
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.pnikosis.materialishprogress.ProgressWheel
import com.furniconbreeze.R
import com.furniconbreeze.app.AppDatabase
import com.furniconbreeze.app.NetworkConstant
import com.furniconbreeze.app.domain.AddShopDBModelEntity
import com.furniconbreeze.app.domain.MeetingTypeEntity
import com.furniconbreeze.app.domain.PartyStatusEntity
import com.furniconbreeze.app.utils.AppUtils
import com.furniconbreeze.app.utils.Toaster
import com.furniconbreeze.base.presentation.BaseActivity
import com.furniconbreeze.features.addshop.api.typeList.TypeListRepoProvider
import com.furniconbreeze.features.addshop.model.PartyStatusResponseModel
import com.furniconbreeze.features.addshop.presentation.PartyStatusAdapter
import com.furniconbreeze.features.login.api.LoginRepositoryProvider
import com.furniconbreeze.features.login.model.mettingListModel.MeetingListResponseModel
import com.furniconbreeze.widgets.AppCustomEditText
import com.furniconbreeze.widgets.AppCustomTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Saikat on 17-01-2020.
 */
class UpdateBankDetailsDialog : DialogFragment(), View.OnClickListener {

    private lateinit var mContext: Context

    private lateinit var dialogHeader: AppCustomTextView
    private lateinit var dialogCancel: AppCustomTextView
    private lateinit var dialogOk: AppCustomTextView
    private lateinit var iv_close_icon: ImageView
    private lateinit var et_account_holder: AppCustomEditText
    private lateinit var et_account: AppCustomEditText
    private lateinit var et_bank_name: AppCustomEditText
    private lateinit var et_ifsc: AppCustomEditText
    private lateinit var progress_wheel: ProgressWheel
    private lateinit var et_upi: AppCustomEditText

    companion object {

        private lateinit var mHeader: String
        private lateinit var mLeftBtn: String
        private lateinit var mRightBtn: String
        private var mIsCancelable: Boolean = true
        private lateinit var mListener: OnButtonClickListener
        private var isShowEditText = false
        private var isShowCross = false
        private lateinit var shop: AddShopDBModelEntity

        fun getInstance(header: String, leftCancel: String, rightOk: String, isCancelable: Boolean, mIsShowEditText: Boolean,
                        mIsShowCross: Boolean, shop: AddShopDBModelEntity, listener: OnButtonClickListener): UpdateBankDetailsDialog {
            val cardFragment = UpdateBankDetailsDialog()
            mHeader = header
            mLeftBtn = leftCancel
            mRightBtn = rightOk
            mListener = listener
            mIsCancelable = isCancelable
            isShowEditText = mIsShowEditText
            isShowCross = mIsShowCross
            this.shop = shop
            return cardFragment
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window!!.setBackgroundDrawableResource(R.drawable.rounded_corner_white_bg)
        val v = inflater.inflate(R.layout.dialog_update_bank_details, container, false)
        isCancelable = mIsCancelable

        initView(v)

        return v
    }

    private fun initView(v: View) {
        dialogHeader = v.findViewById(R.id.dialog_header_TV)
        dialogCancel = v.findViewById(R.id.cancel_TV)
        dialogOk = v.findViewById(R.id.ok_TV)
        dialogOk.isSelected = true

        et_upi = v.findViewById(R.id.et_upi)
        et_ifsc = v.findViewById(R.id.et_ifsc)
        et_bank_name = v.findViewById(R.id.et_bank_name)
        et_account = v.findViewById(R.id.et_account)
        et_account_holder = v.findViewById(R.id.et_account_holder)
        progress_wheel = v.findViewById(R.id.progress_wheel)
        progress_wheel.stopSpinning()

        iv_close_icon = v.findViewById(R.id.iv_close_icon)

        dialogHeader.text = mHeader
        dialogCancel.text = mLeftBtn
        dialogOk.text = mRightBtn

        et_account_holder.setText(shop.account_holder)
        et_account.setText(shop.account_no)
        et_bank_name.setText(shop.bank_name)
        et_ifsc.setText(shop.ifsc_code)
        et_upi.setText(shop.upi_id)

        dialogCancel.setOnClickListener(this)
        dialogOk.setOnClickListener(this)
        iv_close_icon.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cancel_TV -> {
                if (!mIsCancelable)
                    mListener.onLeftClick()
                dismiss()
            }
            R.id.ok_TV -> {
                if (!TextUtils.isEmpty(et_account.text.toString().trim())) {
                    if (AppUtils.isOnline(mContext)) {
                        dismiss()
                        mListener.onRightClick(et_account_holder.text.toString().trim(), et_account.text.toString().trim(),
                                et_bank_name.text.toString().trim(), et_ifsc.text.toString().trim(), et_upi.text.toString().trim())
                    }
                    else
                        Toaster.msgShort(mContext, "Your network connection is offine. Make it online to proceed with update.")
                }
                else
                    Toaster.msgShort(mContext, "Please enter account number.")
            }
            R.id.iv_close_icon -> {
                dismiss()
            }
        }
    }

    interface OnButtonClickListener {
        fun onLeftClick()
        fun onRightClick(accountHolder: String, accountNo: String, bankName: String, ifsc: String, upi: String)
    }
}