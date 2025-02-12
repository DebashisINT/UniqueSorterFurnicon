package com.furniconbreeze.features.activities.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.furniconbreeze.R
import com.furniconbreeze.app.AppDatabase
import com.furniconbreeze.app.NetworkConstant
import com.furniconbreeze.app.Pref
import com.furniconbreeze.app.domain.AddDoctorEntity
import com.furniconbreeze.app.domain.AddDoctorProductListEntity
import com.furniconbreeze.app.domain.AddShopDBModelEntity
import com.furniconbreeze.app.utils.AppUtils
import com.furniconbreeze.app.utils.InputFilterDecimal
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.base.presentation.BaseActivity
import com.furniconbreeze.base.presentation.BaseFragment
import com.furniconbreeze.features.activities.api.ActivityRepoProvider
import com.furniconbreeze.features.activities.model.AddChemistProductModel
import com.furniconbreeze.features.activities.model.AddDoctorVisitInputModel
import com.furniconbreeze.features.activities.model.ProductListModel
import com.furniconbreeze.features.dashboard.presentation.DashboardActivity
import com.furniconbreeze.widgets.AppCustomEditText
import com.furniconbreeze.widgets.AppCustomTextView

import com.pnikosis.materialishprogress.ProgressWheel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_achievement.view.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class EditDoctorActivityFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mContext: Context

    private lateinit var ic_search_icon: AppCompatImageView
    private lateinit var et_search: AppCustomEditText
    private lateinit var ic_close_icon: ImageView
    private lateinit var rv_search_list: RecyclerView
    private lateinit var ll_yes: LinearLayout
    private lateinit var iv_yes: ImageView
    private lateinit var ll_no: LinearLayout
    private lateinit var iv_no: ImageView
    private lateinit var et_doc_remark: AppCustomEditText
    private lateinit var ll_qty: LinearLayout
    private lateinit var iv_qty: ImageView
    private lateinit var ll_vol: LinearLayout
    private lateinit var iv_vol: ImageView
    private lateinit var ll_qty_product: LinearLayout
    private lateinit var ic_qty_search_icon: AppCompatImageView
    private lateinit var ic_perscriber_close_icon: AppCompatImageView
    private lateinit var et_search_qty: AppCustomEditText
    private lateinit var progress_wheel: ProgressWheel
    private lateinit var rv_qty_search_list: RecyclerView
    private lateinit var til_qty_vol_txt: TextInputLayout
    private lateinit var et_qty_vol_txt: AppCustomEditText
    private lateinit var ll_sample_yes: LinearLayout
    private lateinit var iv_sample_yes: ImageView
    private lateinit var ll_sample_no: LinearLayout
    private lateinit var iv_sample_no: ImageView
    private lateinit var ll_sample_product: LinearLayout
    private lateinit var ic_sample_search_icon: AppCompatImageView
    private lateinit var ic_sample_close_icon: AppCompatImageView
    private lateinit var et_search_sample: AppCustomEditText
    private lateinit var rv_sample_search_list: RecyclerView
    private lateinit var ll_crm: LinearLayout
    private lateinit var iv_crm: ImageView
    private lateinit var ll_cme: LinearLayout
    private lateinit var iv_cme: ImageView
    private lateinit var ll_crm_cme_selection: LinearLayout
    private lateinit var ll_money: LinearLayout
    private lateinit var iv_money: ImageView
    private lateinit var ll_kind: LinearLayout
    private lateinit var iv_kind: ImageView
    private lateinit var til_amount: TextInputLayout
    private lateinit var et_amount: AppCustomEditText
    private lateinit var til_what: TextInputLayout
    private lateinit var et_what: AppCustomEditText
    private lateinit var tv_from_date: AppCustomTextView
    private lateinit var tv_to_date: AppCustomTextView
    private lateinit var et_volume_crm_cme: AppCustomEditText
    private lateinit var ll_gift_yes: LinearLayout
    private lateinit var iv_gift_yes: ImageView
    private lateinit var ll_gift_no: LinearLayout
    private lateinit var iv_gif_no: ImageView
    private lateinit var et_which_kind: AppCustomEditText
    private lateinit var tv_save: AppCustomTextView
    private lateinit var tv_visit_date: AppCustomTextView
    private lateinit var et_remarks_mr: AppCustomEditText
    private lateinit var rl_add_doctor_main: RelativeLayout
    private lateinit var tv_sample_product_count: AppCustomTextView
    private lateinit var tv_qty_product_count: AppCustomTextView
    private lateinit var tv_product_count: AppCustomTextView
    private lateinit var til_kind: TextInputLayout
    private lateinit var rl_qty_vol_txt: RelativeLayout
    private lateinit var rl_amount: RelativeLayout
    private lateinit var rl_kind: RelativeLayout
    private lateinit var rl_what: RelativeLayout
    private lateinit var tv_vol_pre: AppCustomTextView

    private var qtyProductList: ArrayList<ProductListModel>? = null
    private var sampleProductList: ArrayList<ProductListModel>? = null
    private var productAdapter: AddChemistProductAdapter? = null
    private var pobProductAdapter: AddChemistPobProductAdapter? = null
    private var sampleProductAdapter: AddChemistSampleAdapter? = null
    private var productList: ArrayList<ProductListModel>? = null
    private var selectedProductList: ArrayList<ProductListModel>? = null
    private var selectedQtyProductList: ArrayList<ProductListModel>? = null
    private var selectedSampleProductList: ArrayList<ProductListModel>? = null

    private var myCalendar = Calendar.getInstance(Locale.ENGLISH)
    private var nextDate = ""
    private var fromDate = ""
    private var toDate = ""
    private var dateStatus = -1
    private var fromDateLong = 0L
    private var toDateLong = 0L

    companion object {

        private var docActivity: AddDoctorEntity? = null

        fun newInstance(objects: Any): EditDoctorActivityFragment {
            val fragment = EditDoctorActivityFragment()

            if (!TextUtils.isEmpty(objects.toString())) {
                if (objects is AddDoctorEntity) {
                    docActivity = objects
                }
            }

            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        productList = ArrayList()
        qtyProductList = ArrayList()
        sampleProductList = ArrayList()

        selectedProductList = ArrayList()
        selectedQtyProductList = ArrayList()
        selectedSampleProductList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_add_doctor, container, false)

        initView(view)
        initClickListener()
        initTextListener()
        setData()

        return view
    }

    private fun initView(view: View) {
        ic_search_icon = view.findViewById(R.id.ic_search_icon)
        et_search = view.findViewById(R.id.et_search)
        ic_close_icon = view.findViewById(R.id.ic_close_icon)
        tv_visit_date = view.findViewById(R.id.tv_visit_date)

        tv_save = view.findViewById(R.id.tv_save)
        et_remarks_mr = view.findViewById(R.id.et_remarks_mr)

        rv_search_list = view.findViewById(R.id.rv_search_list)
        rv_search_list.layoutManager = LinearLayoutManager(mContext)

        ll_yes = view.findViewById(R.id.ll_yes)
        iv_yes = view.findViewById(R.id.iv_yes)
        ll_no = view.findViewById(R.id.ll_no)
        iv_no = view.findViewById(R.id.iv_no)

        rl_add_doctor_main = view.findViewById(R.id.rl_add_doctor_main)

        progress_wheel = view.findViewById(R.id.progress_wheel)
        progress_wheel.stopSpinning()

        et_doc_remark = view.findViewById(R.id.et_doc_remark)
        ll_qty = view.findViewById(R.id.ll_qty)
        iv_qty = view.findViewById(R.id.iv_qty)
        ll_vol = view.findViewById(R.id.ll_vol)
        iv_vol = view.findViewById(R.id.iv_vol)
        ll_qty_product = view.findViewById(R.id.ll_qty_product)
        ic_qty_search_icon = view.findViewById(R.id.ic_qty_search_icon)
        ic_perscriber_close_icon = view.findViewById(R.id.ic_perscriber_close_icon)
        et_search_qty = view.findViewById(R.id.et_search_qty)

        rv_qty_search_list = view.findViewById(R.id.rv_qty_search_list)
        rv_qty_search_list.layoutManager = LinearLayoutManager(mContext)

        til_qty_vol_txt = view.findViewById(R.id.til_qty_vol_txt)
        et_qty_vol_txt = view.findViewById(R.id.et_qty_vol_txt)
        ll_sample_yes = view.findViewById(R.id.ll_sample_yes)
        iv_sample_yes = view.findViewById(R.id.iv_sample_yes)
        ll_sample_no = view.findViewById(R.id.ll_sample_no)
        iv_sample_no = view.findViewById(R.id.iv_sample_no)
        ll_sample_product = view.findViewById(R.id.ll_sample_product)
        ic_sample_search_icon = view.findViewById(R.id.ic_sample_search_icon)
        ic_sample_close_icon = view.findViewById(R.id.ic_sample_close_icon)
        et_search_sample = view.findViewById(R.id.et_search_sample)

        rv_sample_search_list = view.findViewById(R.id.rv_sample_search_list)
        rv_sample_search_list.layoutManager = LinearLayoutManager(mContext)

        ll_crm = view.findViewById(R.id.ll_crm)
        iv_crm = view.findViewById(R.id.iv_crm)
        ll_cme = view.findViewById(R.id.ll_cme)
        iv_cme = view.findViewById(R.id.iv_cme)
        ll_crm_cme_selection = view.findViewById(R.id.ll_crm_cme_selection)
        ll_money = view.findViewById(R.id.ll_money)
        iv_money = view.findViewById(R.id.iv_money)
        ll_kind = view.findViewById(R.id.ll_kind)
        iv_kind = view.findViewById(R.id.iv_kind)
        til_amount = view.findViewById(R.id.til_amount)
        et_amount = view.findViewById(R.id.et_amount)
        til_what = view.findViewById(R.id.til_what)
        et_what = view.findViewById(R.id.et_what)
        tv_from_date = view.findViewById(R.id.tv_from_date)
        tv_to_date = view.findViewById(R.id.tv_to_date)
        et_volume_crm_cme = view.findViewById(R.id.et_volume_crm_cme)
        ll_gift_yes = view.findViewById(R.id.ll_gift_yes)
        iv_gift_yes = view.findViewById(R.id.iv_gift_yes)
        ll_gift_no = view.findViewById(R.id.ll_gift_no)
        iv_gif_no = view.findViewById(R.id.iv_gif_no)
        et_which_kind = view.findViewById(R.id.et_which_kind)
        tv_sample_product_count = view.findViewById(R.id.tv_sample_product_count)
        tv_qty_product_count = view.findViewById(R.id.tv_qty_product_count)
        tv_product_count = view.findViewById(R.id.tv_product_count)
        til_kind = view.findViewById(R.id.til_kind)
        rl_qty_vol_txt = view.findViewById(R.id.rl_qty_vol_txt)
        rl_amount = view.findViewById(R.id.rl_amount)
        rl_kind = view.findViewById(R.id.rl_kind)
        rl_what = view.findViewById(R.id.rl_what)
        tv_vol_pre = view.findViewById(R.id.tv_vol_pre)
        tv_vol_pre.text = AppUtils.spannedString(tv_vol_pre.text.toString().trim())

        et_amount.filters = arrayOf<InputFilter>(InputFilterDecimal(8, 2))
        et_volume_crm_cme.filters = arrayOf<InputFilter>(InputFilterDecimal(8, 2))

        val list = AppDatabase.getDBInstance()?.productListDao()?.getAll()

        if (list != null) {
            for (i in list.indices) {
                val productListModel = ProductListModel()
                productListModel.id = list[i].id
                /*productListModel.brand = list[i].brand
                productListModel.brand_id = list[i].brand_id
                productListModel.category = list[i].category
                productListModel.category_id = list[i].category_id
                productListModel.date = list[i].date*/
                productListModel.product_name = list[i].product_name
                /*productListModel.watt = list[i].watt
                productListModel.watt_id = list[i].watt_id*/

                productList?.add(productListModel)
            }

            for (i in list.indices) {
                val productListModel = ProductListModel()
                productListModel.id = list[i].id
                productListModel.product_name = list[i].product_name

                qtyProductList?.add(productListModel)
            }

            for (i in list.indices) {
                val productListModel = ProductListModel()
                productListModel.id = list[i].id
                productListModel.product_name = list[i].product_name

                sampleProductList?.add(productListModel)
            }
        }

        initProductAdapter()
        initQtyProductAdapter()
        initSampleProductAdapter()
    }

    private fun initProductAdapter() {
        if (productList != null) {

            //pobProductList = ArrayList(productList)

            productAdapter = AddChemistProductAdapter(mContext, productList, object : AddChemistProductAdapter.OnProductClickListener {
                override fun showList(isShowList: Boolean) {
                    if (isShowList) {
                        if (!TextUtils.isEmpty(et_search.text.toString().trim()))
                            rv_search_list.visibility = View.VISIBLE
                        else
                            rv_search_list.visibility = View.GONE
                    } else
                        rv_search_list.visibility = View.GONE
                }

                override fun onCheckClick(product: ProductListModel, isSelected: Boolean) {
                    if (isSelected)
                        selectedProductList?.add(product)
                    else
                        selectedProductList?.remove(product)
                    productAdapter?.notifyDataSetChanged()

                    if (selectedProductList?.size!! > 0)
                        tv_product_count.text = "(" + selectedProductList?.size + " product selected)"
                    else
                        tv_product_count.text = ""
                }
            })
            rv_search_list.adapter = productAdapter
        }
    }

    private fun initQtyProductAdapter() {
        pobProductAdapter = AddChemistPobProductAdapter(mContext, qtyProductList, object : AddChemistPobProductAdapter.OnProductClickListener {
            override fun showList(isShowList: Boolean) {
                if (isShowList) {
                    if (!TextUtils.isEmpty(et_search_qty.text.toString().trim()))
                        rv_qty_search_list.visibility = View.VISIBLE
                    else
                        rv_qty_search_list.visibility = View.GONE
                } else
                    rv_qty_search_list.visibility = View.GONE
            }

            override fun onCheckClick(product: ProductListModel, isSelected: Boolean) {
                if (isSelected)
                    selectedQtyProductList?.add(product)
                else
                    selectedQtyProductList?.remove(product)
                pobProductAdapter?.notifyDataSetChanged()

                if (selectedQtyProductList?.size!! > 0) {
                    if (iv_qty.isSelected)
                        tv_qty_product_count.text = "(" + selectedQtyProductList?.size + " qty product selected)"
                    else if (iv_vol.isSelected)
                        tv_qty_product_count.text = "(" + selectedQtyProductList?.size + " vol product selected)"
                } else
                    tv_qty_product_count.text = ""
            }
        })
        rv_qty_search_list.adapter = pobProductAdapter
    }

    private fun initSampleProductAdapter() {
        sampleProductAdapter = AddChemistSampleAdapter(mContext, sampleProductList, object : AddChemistSampleAdapter.OnProductClickListener {
            override fun showList(isShowList: Boolean) {
                if (isShowList) {
                    if (!TextUtils.isEmpty(et_search_sample.text.toString().trim()))
                        rv_sample_search_list.visibility = View.VISIBLE
                    else
                        rv_sample_search_list.visibility = View.GONE
                } else
                    rv_sample_search_list.visibility = View.GONE
            }

            override fun onCheckClick(product: ProductListModel, isSelected: Boolean) {
                if (isSelected)
                    selectedSampleProductList?.add(product)
                else
                    selectedSampleProductList?.remove(product)
                sampleProductAdapter?.notifyDataSetChanged()

                if (selectedSampleProductList?.size!! > 0)
                    tv_sample_product_count.text = "(" + selectedSampleProductList?.size + " sample product selected)"
                else
                    tv_sample_product_count.text = ""
            }
        })
        rv_sample_search_list.adapter = sampleProductAdapter
    }

    private fun initTextListener() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!TextUtils.isEmpty(et_search.text.toString().trim())) {
                    ic_search_icon.visibility = View.GONE
                    ic_close_icon.visibility = View.VISIBLE
                } else {
                    ic_search_icon.visibility = View.VISIBLE
                    ic_close_icon.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //if (!TextUtils.isEmpty(et_grp_search.text.toString().trim()) /*&& et_grp_search.text.toString().trim().length >= 2*/)
                productAdapter?.filter?.filter(et_search.text.toString().trim())
            }
        })

        et_search_qty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!TextUtils.isEmpty(et_search_qty.text.toString().trim())) {
                    ic_qty_search_icon.visibility = View.GONE
                    ic_perscriber_close_icon.visibility = View.VISIBLE
                } else {
                    ic_qty_search_icon.visibility = View.VISIBLE
                    ic_perscriber_close_icon.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //if (!TextUtils.isEmpty(et_grp_search.text.toString().trim()) /*&& et_grp_search.text.toString().trim().length >= 2*/)
                pobProductAdapter?.filter?.filter(et_search_qty.text.toString().trim())
            }
        })

        et_search_sample.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!TextUtils.isEmpty(et_search_sample.text.toString().trim())) {
                    ic_sample_search_icon.visibility = View.GONE
                    ic_sample_close_icon.visibility = View.VISIBLE
                } else {
                    ic_sample_search_icon.visibility = View.VISIBLE
                    ic_sample_close_icon.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //if (!TextUtils.isEmpty(et_grp_search.text.toString().trim()) /*&& et_grp_search.text.toString().trim().length >= 2*/)
                sampleProductAdapter?.filter?.filter(et_search_sample.text.toString().trim())
            }
        })
    }

    private fun initClickListener() {
        ll_yes.setOnClickListener(this)
        ll_no.setOnClickListener(this)
        tv_save.setOnClickListener(this)
        ic_sample_close_icon.setOnClickListener(this)
        ic_close_icon.setOnClickListener(this)
        ic_perscriber_close_icon.setOnClickListener(this)
        rl_add_doctor_main.setOnClickListener(null)
        tv_visit_date.setOnClickListener(this)
        ll_qty.setOnClickListener(this)
        ll_vol.setOnClickListener(this)
        ll_sample_yes.setOnClickListener(this)
        ll_sample_no.setOnClickListener(this)
        ll_crm.setOnClickListener(this)
        ll_cme.setOnClickListener(this)
        ll_money.setOnClickListener(this)
        ll_kind.setOnClickListener(this)
        tv_from_date.setOnClickListener(this)
        tv_to_date.setOnClickListener(this)
        ll_gift_yes.setOnClickListener(this)
        ll_gift_no.setOnClickListener(this)
    }

    private fun setData() {
        et_doc_remark.setText(docActivity?.doc_remark)

        if (docActivity?.prescribe_status == 1) {
            iv_yes.isSelected = true
            iv_no.isSelected = false
        }
        else if (docActivity?.prescribe_status == 0) {
            iv_yes.isSelected = false
            iv_no.isSelected = true
        }

        if (docActivity?.qty_status == 1) {
            iv_qty.isSelected = true
            iv_vol.isSelected = false
            rl_qty_vol_txt.visibility = View.VISIBLE
            et_qty_vol_txt.setText(docActivity?.qty_text)
            ll_qty_product.visibility = View.VISIBLE
        }
        else if (docActivity?.qty_status == 0) {
            iv_qty.isSelected = false
            iv_vol.isSelected = true
            rl_qty_vol_txt.visibility = View.VISIBLE
            et_qty_vol_txt.setText(docActivity?.qty_text)
            ll_qty_product.visibility = View.VISIBLE
        }

        if (docActivity?.sample_status == 1) {
            iv_sample_yes.isSelected = true
            iv_sample_no.isSelected = false
            ll_sample_product.visibility = View.VISIBLE
        }
        else if (docActivity?.sample_status == 0) {
            iv_sample_yes.isSelected = false
            iv_sample_no.isSelected = true
            ll_sample_product.visibility = View.GONE
        }

        if (docActivity?.crm_status == 1) {
            iv_crm.isSelected = true
            iv_cme.isSelected = false
            ll_crm_cme_selection.visibility = View.VISIBLE
        }
        else if (docActivity?.crm_status == 0) {
            iv_crm.isSelected = false
            iv_cme.isSelected = true
            ll_crm_cme_selection.visibility = View.VISIBLE
        }

        if (docActivity?.money_status == 1) {
            iv_money.isSelected = true
            iv_kind.isSelected = false
            rl_amount.visibility = View.VISIBLE
            rl_what.visibility = View.GONE
            et_amount.setText(docActivity?.amount)
        }
        else if (docActivity?.money_status == 0) {
            iv_money.isSelected = false
            iv_kind.isSelected = true
            rl_amount.visibility = View.GONE
            rl_what.visibility = View.VISIBLE
            et_what.setText(docActivity?.what)
        }

        if (!TextUtils.isEmpty(docActivity?.crm_from_date)) {
            fromDate = docActivity?.crm_from_date!!
            fromDateLong = AppUtils.convertDateStringToLong(fromDate)
            tv_from_date.text = AppUtils.changeAttendanceDateFormat(docActivity?.crm_from_date!!)
        }

        if (!TextUtils.isEmpty(docActivity?.crm_to_date)) {
            toDate = docActivity?.crm_to_date!!
            toDateLong = AppUtils.convertDateStringToLong(fromDate)
            tv_to_date.text = AppUtils.changeAttendanceDateFormat(docActivity?.crm_to_date!!)
        }

        et_volume_crm_cme.setText(docActivity?.volume)

        if (docActivity?.gift_status == 1){
            iv_gift_yes.isSelected = true
            iv_gif_no.isSelected = false
            rl_kind.visibility = View.VISIBLE
            et_which_kind.setText(docActivity?.which_kind)
        }
        else if (docActivity?.gift_status == 0){
            iv_gift_yes.isSelected = false
            iv_gif_no.isSelected = true
            rl_kind.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(docActivity?.visit_date)) {
            nextDate = docActivity?.visit_date!!
            tv_visit_date.text = AppUtils.changeAttendanceDateFormat(docActivity?.visit_date!!)
        }

        et_remarks_mr.setText(docActivity?.remarks_mr)


        val productListDb = AppDatabase.getDBInstance()!!.addDocProductDao().getDataIdPodWise(docActivity?.doc_visit_id!!, 0)
        productListDb?.forEach {
            val productListModel = ProductListModel()
            productListModel.id = it.product_id?.toInt()!!
            productListModel.product_name = it.product_name
            selectedProductList?.add(productListModel)
        }

        if (selectedProductList!!.isNotEmpty()) {

            for (i in productList?.indices!!) {
                for (j in selectedProductList!!.indices) {
                    if (selectedProductList!![j].id == productList?.get(i)?.id) {
                        productList?.get(i)?.isChecked = true
                        break
                    }
                }
            }

            initProductAdapter()
            tv_product_count.text = "(" + selectedProductList?.size + " product selected)"
            rv_search_list.visibility = View.VISIBLE
            et_search.setText(selectedProductList!![0].product_name!!.substring(0, 7))
            productAdapter?.filter?.filter(et_search.text.toString().trim())
        }

        val qtyProductListDb = AppDatabase.getDBInstance()!!.addDocProductDao().getDataIdPodWise(docActivity?.doc_visit_id!!, 1)
        qtyProductListDb?.forEach {
            val productListModel = ProductListModel()
            productListModel.id = it.product_id?.toInt()!!
            productListModel.product_name = it.product_name
            selectedQtyProductList?.add(productListModel)
        }

        if (selectedQtyProductList!!.isNotEmpty()) {

            for (i in qtyProductList?.indices!!) {
                for (j in selectedQtyProductList!!.indices) {
                    if (selectedQtyProductList!![j].id == qtyProductList?.get(i)?.id) {
                        qtyProductList?.get(i)?.isChecked = true
                        break
                    }
                }
            }

            initQtyProductAdapter()
            if (iv_qty.isSelected)
                tv_qty_product_count.text = "(" + selectedQtyProductList?.size + " qty product selected)"
            else if (iv_vol.isSelected)
                tv_qty_product_count.text = "(" + selectedQtyProductList?.size + " vol product selected)"
            rv_qty_search_list.visibility = View.VISIBLE
            et_search_qty.setText(selectedQtyProductList!![0].product_name!!.substring(0, 7))
            pobProductAdapter?.filter?.filter(et_search_qty.text.toString().trim())
        }

        val sampleProductListDb = AppDatabase.getDBInstance()!!.addDocProductDao().getDataIdPodWise(docActivity?.doc_visit_id!!, 2)
        sampleProductListDb?.forEach {
            val productListModel = ProductListModel()
            productListModel.id = it.product_id?.toInt()!!
            productListModel.product_name = it.product_name
            selectedSampleProductList?.add(productListModel)
        }

        if (selectedSampleProductList!!.isNotEmpty()) {

            for (i in sampleProductList?.indices!!) {
                for (j in selectedSampleProductList!!.indices) {
                    if (selectedSampleProductList!![j].id == sampleProductList?.get(i)?.id) {
                        sampleProductList?.get(i)?.isChecked = true
                        break
                    }
                }
            }

            initSampleProductAdapter()
            tv_sample_product_count.text = "(" + selectedSampleProductList?.size + " sample product selected)"
            rv_sample_search_list.visibility = View.VISIBLE
            et_search_sample.setText(selectedSampleProductList!![0].product_name!!.substring(0, 7))
            sampleProductAdapter?.filter?.filter(et_search_sample.text.toString().trim())
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.ll_yes -> {
                if (!iv_yes.isSelected) {
                    iv_yes.isSelected = true
                    iv_no.isSelected = false
                }
            }

            R.id.ll_no -> {
                if (!iv_no.isSelected) {
                    iv_yes.isSelected = false
                    iv_no.isSelected = true
                }
            }

            R.id.ll_qty -> {
                if (!iv_qty.isSelected) {
                    iv_qty.isSelected = true
                    iv_vol.isSelected = false
                    ll_qty_product.visibility = View.VISIBLE
                    rl_qty_vol_txt.visibility = View.VISIBLE
                }
            }

            R.id.ll_vol -> {
                if (!iv_vol.isSelected) {
                    iv_qty.isSelected = false
                    iv_vol.isSelected = true
                    ll_qty_product.visibility = View.VISIBLE
                    rl_qty_vol_txt.visibility = View.VISIBLE
                }
            }

            R.id.ll_sample_yes -> {
                if (!iv_sample_yes.isSelected) {
                    iv_sample_yes.isSelected = true
                    iv_sample_no.isSelected = false
                    ll_sample_product.visibility = View.VISIBLE
                }
            }

            R.id.ll_sample_no -> {
                if (!iv_sample_no.isSelected) {
                    iv_sample_yes.isSelected = false
                    iv_sample_no.isSelected = true
                    ll_sample_product.visibility = View.GONE
                }
            }

            R.id.ll_crm -> {
                if (!iv_crm.isSelected) {
                    iv_crm.isSelected = true
                    iv_cme.isSelected = false
                    ll_crm_cme_selection.visibility = View.VISIBLE
                }
            }

            R.id.ll_cme -> {
                if (!iv_cme.isSelected) {
                    iv_crm.isSelected = false
                    iv_cme.isSelected = true
                    ll_crm_cme_selection.visibility = View.VISIBLE
                }
            }

            R.id.ll_money -> {
                if (!iv_money.isSelected) {
                    iv_money.isSelected = true
                    iv_kind.isSelected = false
                    rl_amount.visibility = View.VISIBLE
                    rl_what.visibility = View.GONE
                }
            }

            R.id.ll_kind -> {
                if (!iv_kind.isSelected) {
                    iv_money.isSelected = false
                    iv_kind.isSelected = true
                    rl_amount.visibility = View.GONE
                    rl_what.visibility = View.VISIBLE
                }
            }

            R.id.tv_from_date -> {
                dateStatus = 0
                val aniDatePicker = DatePickerDialog(mContext, R.style.DatePickerTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                //aniDatePicker.datePicker.minDate = Calendar.getInstance().timeInMillis + (1000 * 60 * 60 * 24)
                //aniDatePicker.datePicker.maxDate = Calendar.getInstance().timeInMillis

                /*if (toDateLong != 0L) {
                    aniDatePicker.datePicker.maxDate = toDateLong
                }*/

                aniDatePicker.show()
            }

            R.id.tv_to_date -> {
                dateStatus = 1
                val aniDatePicker = DatePickerDialog(mContext, R.style.DatePickerTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                //aniDatePicker.datePicker.minDate = Calendar.getInstance().timeInMillis + (1000 * 60 * 60 * 24)
                //aniDatePicker.datePicker.maxDate = Calendar.getInstance().timeInMillis

                /*if (fromDateLong != 0L) {
                    aniDatePicker.datePicker.minDate = fromDateLong
                }*/

                aniDatePicker.show()
            }

            R.id.ll_gift_yes -> {
                if (!iv_gift_yes.isSelected) {
                    iv_gift_yes.isSelected = true
                    iv_gif_no.isSelected = false
                    rl_kind.visibility = View.VISIBLE
                }
            }

            R.id.ll_gift_no -> {
                if (!iv_gif_no.isSelected) {
                    iv_gift_yes.isSelected = false
                    iv_gif_no.isSelected = true
                    rl_kind.visibility = View.GONE
                }
            }

            R.id.tv_save -> {
                when {
                    selectedProductList!!.size == 0 -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_product))
                    TextUtils.isEmpty(et_doc_remark.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_doc_remark))
                    !iv_yes.isSelected && !iv_no.isSelected -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_prescriber))
                    !iv_qty.isSelected && !iv_vol.isSelected -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_vol_qty))
                    iv_qty.isSelected && TextUtils.isEmpty(et_qty_vol_txt.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_qty_text))
                    iv_vol.isSelected && TextUtils.isEmpty(et_qty_vol_txt.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_vol_text))
                    iv_vol.isSelected && selectedQtyProductList!!.size == 0 -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_vol_product))
                    iv_qty.isSelected && selectedQtyProductList!!.size == 0 -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_qty_product))
                    !iv_sample_yes.isSelected && !iv_sample_no.isSelected -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_sample))
                    iv_sample_yes.isSelected && selectedSampleProductList!!.size == 0 -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_sample_product))
                    !iv_crm.isSelected && !iv_cme.isSelected -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_crm_cme))
                    !iv_money.isSelected && !iv_kind.isSelected -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_money_kind))
                    iv_money.isSelected && TextUtils.isEmpty(et_amount.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.amount_error))
                    iv_kind.isSelected && TextUtils.isEmpty(et_what.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_what_kind))
                    TextUtils.isEmpty(tv_from_date.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_from_contract_period))
                    TextUtils.isEmpty(tv_to_date.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_to_contract_period))
                    TextUtils.isEmpty(et_volume_crm_cme.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_vol))
                    !iv_gift_yes.isSelected && !iv_gif_no.isSelected -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_gift))
                    iv_gift_yes.isSelected && TextUtils.isEmpty(et_which_kind.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_which_kind))
                    TextUtils.isEmpty(tv_visit_date.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_visit_date))
                    TextUtils.isEmpty(et_remarks_mr.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_enter_remarks_mr))
                    else -> {
                        if (iv_cme.isSelected || iv_crm.isSelected) {
                            if (fromDateLong > toDateLong)
                                (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_valid_from_date))
                            else
                                saveData()
                        } else
                            saveData()
                    }
                }
            }

            R.id.ic_perscriber_close_icon -> {
                et_search_qty.setText("")
                rv_qty_search_list.visibility = View.GONE
                ic_qty_search_icon.visibility = View.VISIBLE
                ic_perscriber_close_icon.visibility = View.GONE
            }

            R.id.ic_close_icon -> {
                et_search.setText("")
                rv_search_list.visibility = View.GONE
                ic_search_icon.visibility = View.VISIBLE
                ic_close_icon.visibility = View.GONE
            }

            R.id.ic_sample_close_icon -> {
                et_search_sample.setText("")
                rv_sample_search_list.visibility = View.GONE
                ic_sample_search_icon.visibility = View.VISIBLE
                ic_sample_close_icon.visibility = View.GONE
            }

            R.id.tv_visit_date -> {
                dateStatus = 2
                val aniDatePicker = DatePickerDialog(mContext, R.style.DatePickerTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                aniDatePicker.datePicker.minDate = Calendar.getInstance(Locale.ENGLISH).timeInMillis + (1000 * 60 * 60 * 24)
                aniDatePicker.show()
            }
        }
    }

    private val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        when (dateStatus) {
            0 -> {
                fromDate = AppUtils.getFormattedDateForApi(myCalendar.time)
                fromDateLong = AppUtils.convertDateStringToLong(fromDate)
                tv_from_date.text = AppUtils.changeAttendanceDateFormat(AppUtils.getDobFormattedDate(myCalendar.time))
            }
            1 -> {
                toDate = AppUtils.getFormattedDateForApi(myCalendar.time)
                toDateLong = AppUtils.convertDateStringToLong(toDate)
                tv_to_date.text = AppUtils.changeAttendanceDateFormat(AppUtils.getDobFormattedDate(myCalendar.time))
            }
            2 -> {
                nextDate = AppUtils.getFormattedDateForApi(myCalendar.time)
                tv_visit_date.text = AppUtils.changeAttendanceDateFormat(AppUtils.getDobFormattedDate(myCalendar.time))
            }
        }
    }


    private fun saveData() {
        docActivity?.let { docEntity ->
            if (!TextUtils.isEmpty(et_remarks_mr.text.toString().trim()))
                docEntity.remarks_mr = et_remarks_mr.text.toString().trim()
            else
                docEntity.remarks_mr = ""

            docEntity.visit_date = nextDate
            docEntity.crm_from_date = fromDate
            docEntity.crm_to_date = toDate

            if (!TextUtils.isEmpty(et_volume_crm_cme.text.toString().trim()))
                docEntity.volume = et_volume_crm_cme.text.toString().trim()
            else
                docEntity.volume = ""

            if (!TextUtils.isEmpty(et_doc_remark.text.toString().trim()))
                docEntity.doc_remark = et_doc_remark.text.toString().trim()
            else
                docEntity.doc_remark = ""

            if (!TextUtils.isEmpty(et_qty_vol_txt.text.toString().trim()))
                docEntity.qty_text = et_qty_vol_txt.text.toString().trim()
            else
                docEntity.qty_text = ""

            if (iv_yes.isSelected)
                docEntity.prescribe_status = 1
            else if (iv_no.isSelected)
                docEntity.prescribe_status = 0

            if (iv_qty.isSelected)
                docEntity.qty_status = 1
            else if (iv_vol.isSelected)
                docEntity.qty_status = 0

            if (iv_sample_yes.isSelected)
                docEntity.sample_status = 1
            else if (iv_sample_no.isSelected)
                docEntity.sample_status = 0

            if (iv_crm.isSelected)
                docEntity.crm_status = 1
            else if (iv_cme.isSelected)
                docEntity.crm_status = 0

            if (iv_money.isSelected) {
                docEntity.money_status = 1

                if (!TextUtils.isEmpty(et_amount.text.toString().trim()))
                    docEntity.amount = et_amount.text.toString().trim()
                else
                    docEntity.amount = ""

                docEntity.what = ""
            } else if (iv_kind.isSelected) {
                docEntity.money_status = 0

                if (!TextUtils.isEmpty(et_what.text.toString().trim()))
                    docEntity.what = et_what.text.toString().trim()
                else
                    docEntity.what = ""

                docEntity.amount = ""
            }

            if (iv_gift_yes.isSelected) {
                docEntity.gift_status = 1

                if (!TextUtils.isEmpty(et_which_kind.text.toString().trim()))
                    docEntity.which_kind = et_which_kind.text.toString().trim()
                else
                    docEntity.which_kind = ""
            } else if (iv_gif_no.isSelected) {
                docEntity.gift_status = 0
                docEntity.which_kind = ""
            }
            docEntity.isUploaded = false

            if (selectedProductList?.size!! > 0 || selectedQtyProductList?.size!! > 0 || selectedSampleProductList?.size!! > 0) {

                if (selectedProductList?.size!! > 0) {
                    AppDatabase.getDBInstance()!!.addDocProductDao().deleteStatusDocIdWise(docEntity.doc_visit_id, 0)
                    for (i in selectedProductList?.indices!!) {
                        val docProduct = AddDoctorProductListEntity()
                        AppDatabase.getDBInstance()!!.addDocProductDao().insertAll(docProduct.apply {
                            doc_visit_id = docEntity.doc_visit_id
                            shop_id = docEntity.shop_id
                            product_status = 0
                            product_id = selectedProductList?.get(i)?.id?.toString()
                            product_name = selectedProductList?.get(i)?.product_name
                        })
                    }
                }

                if (selectedQtyProductList?.size!! > 0) {
                    AppDatabase.getDBInstance()!!.addDocProductDao().deleteStatusDocIdWise(docEntity.doc_visit_id, 1)
                    for (i in selectedQtyProductList?.indices!!) {
                        val pobChemEntity = AddDoctorProductListEntity()
                        pobChemEntity.doc_visit_id = docEntity.doc_visit_id
                        pobChemEntity.shop_id = docEntity.shop_id
                        pobChemEntity.product_status = 1
                        pobChemEntity.product_id = selectedQtyProductList?.get(i)?.id?.toString()
                        pobChemEntity.product_name = selectedQtyProductList?.get(i)?.product_name

                        AppDatabase.getDBInstance()!!.addDocProductDao().insertAll(pobChemEntity)
                    }
                }

                if (iv_sample_yes.isSelected) {
                    if (selectedSampleProductList?.size!! > 0) {
                        AppDatabase.getDBInstance()!!.addDocProductDao().deleteStatusDocIdWise(docEntity.doc_visit_id, 2)
                        for (i in selectedSampleProductList?.indices!!) {
                            val pobChemEntity = AddDoctorProductListEntity()
                            pobChemEntity.doc_visit_id = docEntity.doc_visit_id
                            pobChemEntity.shop_id = docEntity.shop_id
                            pobChemEntity.product_status = 2
                            pobChemEntity.product_id = selectedSampleProductList?.get(i)?.id?.toString()
                            pobChemEntity.product_name = selectedSampleProductList?.get(i)?.product_name

                            AppDatabase.getDBInstance()!!.addDocProductDao().insertAll(pobChemEntity)
                        }
                    }
                } else
                    selectedSampleProductList?.clear()
            }

            AppDatabase.getDBInstance()!!.addDocDao().updateDoc(docEntity)

            /*(mContext as DashboardActivity).showSnackMessage("Doctor added successfully")
        (mContext as DashboardActivity).onBackPressed()*/

            val docVisit = AddDoctorVisitInputModel()

            if (!TextUtils.isEmpty(docEntity.doc_visit_id))
                docVisit.doc_visit_id = docEntity.doc_visit_id!!

            if (!TextUtils.isEmpty(docEntity.amount))
                docVisit.amount = docEntity.amount!!

            docVisit.next_visit_date = nextDate

            if (!TextUtils.isEmpty(docEntity.volume))
                docVisit.crm_volume = docEntity.volume!!

            if (!TextUtils.isEmpty(docEntity.remarks_mr))
                docVisit.remarks_mr = docEntity.remarks_mr!!

            if (!TextUtils.isEmpty(docEntity.doc_remark))
                docVisit.doc_remarks = docEntity.doc_remark!!

            if (!TextUtils.isEmpty(docEntity.shop_id))
                docVisit.shop_id = docEntity.shop_id!!

            docVisit.user_id = Pref.user_id!!
            docVisit.session_token = Pref.session_token!!
            docVisit.from_cme_date = fromDate
            docVisit.to_crm_date = toDate
            docVisit.is_crm = docEntity.crm_status
            docVisit.is_gift = docEntity.gift_status
            docVisit.is_money = docEntity.money_status
            docVisit.is_prescriber = docEntity.prescribe_status
            docVisit.is_qty = docEntity.qty_status
            docVisit.is_sample = docEntity.sample_status

            if (!TextUtils.isEmpty(docEntity.qty_text))
                docVisit.qty_vol_text = docEntity.qty_text!!

            if (!TextUtils.isEmpty(docEntity.what))
                docVisit.what = docEntity.what!!

            if (!TextUtils.isEmpty(docEntity.which_kind))
                docVisit.which_kind = docEntity.which_kind!!

            val productList = ArrayList<AddChemistProductModel>()
            for (i in selectedProductList?.indices!!) {
                val product = AddChemistProductModel()
                product.product_id = selectedProductList?.get(i)?.id?.toString()!!
                product.product_name = selectedProductList?.get(i)?.product_name!!
                productList.add(product)
            }
            docVisit.product_list = productList

            val podProductList = ArrayList<AddChemistProductModel>()
            for (i in selectedQtyProductList?.indices!!) {
                val product = AddChemistProductModel()
                product.product_id = selectedQtyProductList?.get(i)?.id?.toString()!!
                product.product_name = selectedQtyProductList?.get(i)?.product_name!!
                podProductList.add(product)
            }
            docVisit.qty_product_list = podProductList

            val sampleProductList = ArrayList<AddChemistProductModel>()
            for (i in selectedSampleProductList?.indices!!) {
                val product = AddChemistProductModel()
                product.product_id = selectedSampleProductList?.get(i)?.id?.toString()!!
                product.product_name = selectedSampleProductList?.get(i)?.product_name!!
                sampleProductList.add(product)
            }
            docVisit.sample_product_list = sampleProductList

            callUploadDocVisitApi(docVisit)
        }
    }

    private fun callUploadDocVisitApi(docVisit: AddDoctorVisitInputModel) {

        if (!AppUtils.isOnline(mContext)) {
            (mContext as DashboardActivity).showSnackMessage("Doctor edited successfully")
            (mContext as DashboardActivity).onBackPressed()
            return
        }

        val shop = AppDatabase.getDBInstance()!!.addShopEntryDao().getShopDetail(docVisit.shop_id)

        if (!shop.isUploaded) {
            (mContext as DashboardActivity).showSnackMessage("Doctor edited successfully")
            (mContext as DashboardActivity).onBackPressed()
            return
        }

        Timber.d("=====EDIT DOCTOR VISIT INPUT PARAMS (EDIT DOCTOR)======")
        Timber.d("USER ID===> " + docVisit.user_id)
        Timber.d("SESSION ID====> " + docVisit.session_token)
        Timber.d("DOCTOR VISIT ID====> " + docVisit.doc_visit_id)
        Timber.d("SHOP_ID====> " + docVisit.shop_id)
        Timber.d("AMOUNT====> " + docVisit.amount)
        Timber.d("NEXT VISIT DATE====> " + docVisit.next_visit_date)
        Timber.d("VOLUME====> " + docVisit.crm_volume)
        Timber.d("DOCTOR REMARKS====> " + docVisit.doc_remarks)
        Timber.d("REMARKS MR====> " + docVisit.remarks_mr)
        Timber.d("FROM DATE====> " + docVisit.from_cme_date)
        Timber.d("TO DATE====> " + docVisit.to_crm_date)
        Timber.d("IS GIFT====> " + docVisit.is_gift)
        Timber.d("IS CRM====> " + docVisit.is_crm)
        Timber.d("IS MONEY====> " + docVisit.is_money)
        Timber.d("IS PRESCRIBER====> " + docVisit.is_prescriber)
        Timber.d("IS QTY====> " + docVisit.is_qty)
        Timber.d("IS SAMPLE====> " + docVisit.is_sample)
        Timber.d("QTY VOL TEXT====> " + docVisit.qty_vol_text)
        Timber.d("WHAT====> " + docVisit.what)
        Timber.d("WHICH====> " + docVisit.which_kind)
        Timber.d("PRODUCT LIST SIZE====> " + docVisit.product_list.size)
        Timber.d("QTY PRODUCT LIST SIZE====> " + docVisit.qty_product_list.size)
        Timber.d("SAMPLE PRODUCT LIST SIZE====> " + docVisit.sample_product_list.size)
        Timber.d("=========================================================")

        val repository = ActivityRepoProvider.activityRepoProvider()
        progress_wheel.spin()
        BaseActivity.compositeDisposable.add(
                repository.uploadDoctorVisit(docVisit)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            progress_wheel.stopSpinning()
                            val response = result as BaseResponse

                            Timber.d("EDIT DOCTOR VISIT DETAILS : " + "RESPONSE : " + response.status + "\n" + "Time : " + AppUtils.getCurrentDateTime() + ", USER :" + Pref.user_name + ", MESSAGE : " + response.message)

                            if (response.status == NetworkConstant.SUCCESS) {
                                AppDatabase.getDBInstance()!!.addDocDao().updateIsUploaded(true, docVisit.doc_visit_id)
                                (mContext as DashboardActivity).showSnackMessage(response.message!!)
                            } else
                                (mContext as DashboardActivity).showSnackMessage("Doctor edited successfully")

                            (mContext as DashboardActivity).onBackPressed()
                        }, { error ->
                            Timber.d("EDIT DOCTOR VISIT DETAILS : " + "ERROR : " + "\n" + "Time : " + AppUtils.getCurrentDateTime() + ", USER :" + Pref.user_name + ", MESSAGE : " + error.localizedMessage)
                            progress_wheel.stopSpinning()
                            error.printStackTrace()
                            (mContext as DashboardActivity).showSnackMessage("Doctor edited successfully")
                            (mContext as DashboardActivity).onBackPressed()
                        })
        )

    }
}