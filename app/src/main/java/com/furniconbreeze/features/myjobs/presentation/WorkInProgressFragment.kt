package com.furniconbreeze.features.myjobs.presentation

import android.Manifest
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.furniconbreeze.R
import com.furniconbreeze.app.NetworkConstant
import com.furniconbreeze.app.Pref
import com.furniconbreeze.app.domain.AddShopDBModelEntity
import com.furniconbreeze.app.utils.AppUtils
import com.furniconbreeze.app.utils.PermissionUtils
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.base.presentation.BaseActivity
import com.furniconbreeze.base.presentation.BaseFragment
import com.furniconbreeze.features.dashboard.presentation.DashboardActivity
import com.furniconbreeze.features.location.LocationWizard
import com.furniconbreeze.features.myjobs.api.MyJobRepoProvider
import com.furniconbreeze.features.myjobs.model.*
import com.furniconbreeze.widgets.AppCustomEditText
import com.furniconbreeze.widgets.AppCustomTextView
import com.pnikosis.materialishprogress.ProgressWheel
import com.themechangeapp.pickimage.PermissionHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class WorkInProgressFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mContext: Context

    private lateinit var tv_date: AppCustomTextView
    private lateinit var tv_time: AppCustomTextView
    private lateinit var et_attachment: AppCustomEditText
    private lateinit var et_photo: AppCustomEditText
    private lateinit var tv_service_due: AppCustomTextView
    private lateinit var et_service_completed: AppCustomEditText
    private lateinit var tv_next_date: AppCustomTextView
    private lateinit var tv_next_time: AppCustomTextView
    private lateinit var et_remarks: AppCustomEditText
    private lateinit var submit_button_TV: AppCustomTextView
    private lateinit var progress_wheel: ProgressWheel
    private lateinit var rl_wip_main: RelativeLayout
    private lateinit var tv_service_due_header: AppCustomTextView
    private lateinit var tv_service_completed_header: AppCustomTextView

    private val myCalendar: Calendar by lazy {
        Calendar.getInstance(Locale.ENGLISH)
    }

    private var isNexDate = false
    private var dataPath = ""
    private var imagePath = ""
    private var dateMilis = 0L
    private var timeMilis = 0L
    private var nextTimeMilis = 0L
    private var permissionUtils: PermissionUtils? = null
    private var shopList = ArrayList<AddShopDBModelEntity>()
    private var isAttachment = false
    private var selectedDate = ""
    private var selectedNextDate = ""

    private var customerdata: CustomerDataModel? = null

    companion object {
        fun newInstance(mcustomerdata: Any): WorkInProgressFragment {
            val fragment = WorkInProgressFragment()

            if (mcustomerdata is CustomerDataModel) {
                val bundle = Bundle()
                bundle.putSerializable("customer", mcustomerdata)
                fragment.arguments = bundle
            }

            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        customerdata = arguments?.getSerializable("customer") as CustomerDataModel?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_wip, container, false)

        initView(view)
        initClickListener()

        getWIPStatusApi()

        return view
    }

    private fun initView(view: View) {
        view.apply {
            tv_date = findViewById(R.id.tv_date)
            tv_time = findViewById(R.id.tv_time)
            et_attachment = findViewById(R.id.et_attachment)
            et_photo = findViewById(R.id.et_photo)
            tv_service_due = findViewById(R.id.tv_service_due)
            et_service_completed = findViewById(R.id.et_service_completed)
            tv_next_date = findViewById(R.id.tv_next_date)
            tv_next_time = findViewById(R.id.tv_next_time)
            et_remarks = findViewById(R.id.et_remarks)
            submit_button_TV = findViewById(R.id.submit_button_TV)
            progress_wheel = findViewById(R.id.progress_wheel)
            rl_wip_main = findViewById(R.id.rl_wip_main)
            tv_service_completed_header = findViewById(R.id.tv_service_completed_header)
            tv_service_due_header = findViewById(R.id.tv_service_due_header)
        }

        progress_wheel.stopSpinning()
    }

    private fun initClickListener() {
        submit_button_TV.setOnClickListener(this)
        tv_date.setOnClickListener(this)
        tv_time.setOnClickListener(this)
        tv_next_date.setOnClickListener(this)
        tv_next_time.setOnClickListener(this)
        et_attachment.setOnClickListener(this)
        et_photo.setOnClickListener(this)
        rl_wip_main.setOnClickListener(null)
    }

    private fun getWIPStatusApi() {
        if (!AppUtils.isOnline(mContext)) {
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_internet))
            return
        }

        progress_wheel.spin()
        val repository = MyJobRepoProvider.jobRepoProvider()
        BaseActivity.compositeDisposable.add(
                repository.getWipSettings(customerdata?.id!!)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val response = result as CustomerWIPStatusModel
                            progress_wheel.stopSpinning()

                            if (response.status == NetworkConstant.SUCCESS) {
                                tv_service_due_header.text = "Service due for ${response.uom_text}"
                                tv_service_completed_header.text = "Service Completed for ${response.uom_text}:"
                                et_service_completed.hint = "Enter no. in ${response.uom_text}"
                                tv_service_due.text = "${response.service_due_for} ${response.uom_text}"
                            }

                        }, { error ->
                            progress_wheel.stopSpinning()
                            error.printStackTrace()
                            (mContext as DashboardActivity).showSnackMessage("ERROR")
                        })
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.submit_button_TV -> {
                checkValidation()
            }

            R.id.tv_date -> {
                isNexDate = false
                val datePicker = android.app.DatePickerDialog(mContext, R.style.DatePickerTheme, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))

                datePicker.show()
            }

            R.id.tv_time -> {
                val cal = Calendar.getInstance(Locale.ENGLISH)

                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    timeMilis = cal.timeInMillis
                    tv_time.text = SimpleDateFormat("hh:mm a").format(cal.time)
                }

                val timePicker = TimePickerDialog(mContext, R.style.DatePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
                timePicker.show()
            }

            R.id.tv_next_date -> {
                if (TextUtils.isEmpty(tv_date.text.toString().trim()))
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_date_first))
                else {
                    isNexDate = true
                    val datePicker = android.app.DatePickerDialog(mContext, R.style.DatePickerTheme, date, myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH))

                    datePicker.show()
                }
            }

            R.id.tv_next_time -> {
                val cal = Calendar.getInstance(Locale.ENGLISH)

                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    nextTimeMilis = cal.timeInMillis
                    tv_next_time.text = SimpleDateFormat("hh:mm a").format(cal.time)
                }

                val timePicker = TimePickerDialog(mContext, R.style.DatePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
                timePicker.show()
            }

            R.id.et_attachment -> {
                isAttachment = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    initPermissionCheck()
                else {
                    showPictureDialog()
                }
            }

            R.id.et_photo -> {
                isAttachment = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    initPermissionCheck()
                else {
                    (mContext as DashboardActivity).captureImage()
                }
            }
        }
    }

    private fun initPermissionCheck() {

        //begin mantis id 26741 Storage permission updation Suman 22-08-2023
        var permissionList = arrayOf<String>( Manifest.permission.CAMERA)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            permissionList += Manifest.permission.READ_MEDIA_IMAGES
            permissionList += Manifest.permission.READ_MEDIA_AUDIO
            permissionList += Manifest.permission.READ_MEDIA_VIDEO
        }else{
            permissionList += Manifest.permission.WRITE_EXTERNAL_STORAGE
            permissionList += Manifest.permission.READ_EXTERNAL_STORAGE
        }
//end mantis id 26741 Storage permission updation Suman 22-08-2023

        permissionUtils = PermissionUtils(mContext as Activity, object : PermissionUtils.OnPermissionListener {
            override fun onPermissionGranted() {
                if (isAttachment)
                    showPictureDialog()
                else
                    (mContext as DashboardActivity).captureImage()
            }

            override fun onPermissionNotGranted() {
                (mContext as DashboardActivity).showSnackMessage(getString(R.string.accept_permission))
            }

        },permissionList)// arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    fun onRequestPermission(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionUtils?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(mContext)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture Image", "Select file from file manager")
        pictureDialog.setItems(pictureDialogItems,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        0 -> selectImageInAlbum()
                        1 -> {
                            //(mContext as DashboardActivity).openFileManager()
                            launchCamera()
                        }
                        2 -> {
                            (mContext as DashboardActivity).openFileManager()
                        }
                    }
                })
        pictureDialog.show()
    }

    private fun launchCamera() {
        (mContext as DashboardActivity).captureImage()
    }

    private fun selectImageInAlbum() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        (mContext as DashboardActivity).startActivityForResult(galleryIntent, PermissionHelper.REQUEST_CODE_STORAGE)
    }

    fun setImage(file: File) {
        if (isAttachment) {
            et_attachment.setText(file.name)
            dataPath = file.absolutePath
        } else {
            imagePath = file.absolutePath
            et_photo.setText(file.name)
        }
    }

    val date = android.app.DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        if (!isNexDate) {
            selectedDate = AppUtils.getFormattedDateForApi(myCalendar.time)
            tv_date.text = AppUtils.getBillingDateFromCorrectDate(AppUtils.getFormattedDateForApi(myCalendar.time))
            dateMilis = myCalendar.timeInMillis
        } else {
            selectedNextDate = AppUtils.getFormattedDateForApi(myCalendar.time)
            tv_next_date.text = AppUtils.getBillingDateFromCorrectDate(AppUtils.getFormattedDateForApi(myCalendar.time))
        }
    }

    private fun checkValidation() {
        when {
            TextUtils.isEmpty(tv_date.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_date))
            TextUtils.isEmpty(tv_time.text.toString().trim()) -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_time))
            !TextUtils.isEmpty(tv_next_time.text.toString().trim()) && nextTimeMilis <= timeMilis -> (mContext as DashboardActivity).showSnackMessage(getString(R.string.error_select_proper_time))
            else -> {
                submitWIPApi()
            }
        }
    }

    private fun submitWIPApi() {
        if (!AppUtils.isOnline(mContext)) {
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_internet))
            return
        }

        val wipInput = WIPSubmit(Pref.session_token!!, Pref.user_id!!, customerdata?.id!!, selectedDate, tv_time.text.toString().trim(),
                tv_service_due.text.toString().trim(), et_service_completed.text.toString().trim(), selectedNextDate, tv_next_time.text.toString().trim(),
                et_remarks.text.toString().trim(), AppUtils.getCurrentISODateTime(), Pref.current_latitude, Pref.current_longitude, LocationWizard.getNewLocationName(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble()))

        progress_wheel.spin()
        if (!TextUtils.isEmpty(et_attachment.text.toString().trim()) || !TextUtils.isEmpty(et_photo.text.toString().trim())) {
            val imgList = ArrayList<WIPImageSubmit>()

            if (!TextUtils.isEmpty(et_attachment.text.toString()))
                imgList.add(WIPImageSubmit(dataPath, "attachment"))

            if (!TextUtils.isEmpty(et_photo.text.toString()))
                imgList.add(WIPImageSubmit(imagePath, "image"))

            val repository = MyJobRepoProvider.jobMultipartRepoProvider()
            BaseActivity.compositeDisposable.add(
                    repository.submitWIP(wipInput, imgList, mContext)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                val response = result as BaseResponse
                                progress_wheel.stopSpinning()
                                (mContext as DashboardActivity).showSnackMessage(response.message!!)

                                if (response.status == NetworkConstant.SUCCESS) {
                                    (mContext as DashboardActivity).isSubmit = true
                                    (mContext as DashboardActivity).onBackPressed()
                                }

                            }, { error ->
                                progress_wheel.stopSpinning()
                                error.printStackTrace()
                                (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                            })
            )
        }
        else {
            val repository = MyJobRepoProvider.jobRepoProvider()
            BaseActivity.compositeDisposable.add(
                    repository.submitWIP(wipInput)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                val response = result as BaseResponse
                                progress_wheel.stopSpinning()
                                (mContext as DashboardActivity).showSnackMessage(response.message!!)

                                if (response.status == NetworkConstant.SUCCESS) {
                                    (mContext as DashboardActivity).isSubmit = true
                                    (mContext as DashboardActivity).onBackPressed()
                                }

                            }, { error ->
                                progress_wheel.stopSpinning()
                                error.printStackTrace()
                                (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                            })
            )
        }
    }
}