package com.furniconbreeze.features.photoReg.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.furniconbreeze.CustomStatic
import com.furniconbreeze.R
import com.furniconbreeze.app.Pref
import com.furniconbreeze.app.domain.AddShopDBModelEntity
import com.furniconbreeze.features.chat.model.ChatListDataModel
import com.furniconbreeze.features.myjobs.model.CustomerDataModel
import com.furniconbreeze.features.nearbyshops.model.NewOrderModel
import com.furniconbreeze.features.nearbyshops.presentation.NearByShopsListAdapter
import com.furniconbreeze.features.nearbyshops.presentation.NearByShopsListClickListener
import com.furniconbreeze.features.photoReg.model.UserListResponseModel
import com.squareup.picasso.Cache
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_user_list_face_attend.view.*
import kotlinx.android.synthetic.main.row_user_list_face_regis.view.*
import kotlinx.android.synthetic.main.row_user_list_face_regis.view.photo_reg_user_name_tv

class AdapterUserList (var mContext: Context,var customerList:ArrayList<UserListResponseModel>,val listner:PhotoRegUserListner,private val getSize: (Int) -> Unit):
        RecyclerView.Adapter<AdapterUserList.MyViewHolder>(), Filterable {

    private val layoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(mContext)
    }

    private var mList: ArrayList<UserListResponseModel>? = null
    private var tempList: ArrayList<UserListResponseModel>? = null
    private var filterList: ArrayList<UserListResponseModel>? = null

    init {
        mList = ArrayList()
        tempList = ArrayList()
        filterList = ArrayList()

        mList?.addAll(customerList)
        tempList?.addAll(customerList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = layoutInflater.inflate(R.layout.row_user_list_face_regis, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems()
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindItems(){
            itemView.apply {
                //photo_reg_user_name_tv.text = mList?.get(adapterPosition)?.user_name + "   (  "+ mList?.get(adapterPosition)?.user_login_id+"  )"
                photo_reg_user_name_tv.text = mList?.get(adapterPosition)?.user_name + " ( "+ mList?.get(adapterPosition)?.OtherID+" )"
                photo_reg_user_ph_tv.text = " "+mList?.get(adapterPosition)?.emp_phone_no
//                photo_reg_dd_name_tv.text="Distributor : "+mList?.get(adapterPosition)?.ShowDDInFaceRegistration
//                photo_reg_dd_name_tv.text="Distributor Surise Manali Himachal Limited "
                click_for_photo_reg_tv.setOnClickListener{listner?.getUserInfoOnLick(mList?.get(adapterPosition)!!)}
                click_for_update_type_tv.setOnClickListener{listner?.updateTypeOnClick(mList?.get(adapterPosition)!!)}
                sync_whatsapp_iv.setOnClickListener{listner?.getWhatsappOnLick(mList?.get(adapterPosition)?.emp_phone_no.toString())}
                photo_reg_user_ph_tv.setOnClickListener{listner?.getPhoneOnLick(mList?.get(adapterPosition)?.emp_phone_no.toString())}
                //sync_delete_iv.setOnClickListener{listner?.deletePicOnLick(mList?.get(adapterPosition)!!)}
                sync_delete_iv_red.setOnClickListener{listner?.deletePicOnLick(mList?.get(adapterPosition)!!)}

                photo_reg_user_ph_update_name.setOnClickListener{listner?.updateUserNameOnClick(mList?.get(adapterPosition)!!)}

                if(mList?.get(adapterPosition)?.emp_phone_no!!.length>0){
                    photo_reg_user_ph_update_tv.text="Update Contact No"
                }else{
                    photo_reg_user_ph_update_tv.text="Add Contact No"
                }
                photo_reg_user_ph_update_tv.setOnClickListener{
                    if(mList?.get(adapterPosition)?.emp_phone_no!!.length>0){
                        listner?.updateContactOnClick(mList?.get(adapterPosition)!!)
                    }else{
                        listner?.addContactOnClick(mList?.get(adapterPosition)!!)
                    }
                }

                //iv_aadhaar_ion.setOnClickListener{listner?.getAadhaarOnLick(mList?.get(adapterPosition)!!)}

                //if(mList?.get(adapterPosition)?.IsAadhaarRegistered!!){
                if(mList?.get(adapterPosition)?.aadhar_image_link!!.contains("CommonFolder")){
                    iv_aadhaar_ion.setImageResource(R.drawable.ic_aadhaar_icon_done)
                }else{
                    iv_aadhaar_ion.setImageResource(R.drawable.ic_aadhaar_icon)
                }

                if(mList?.get(adapterPosition)?.isFaceRegistered!!){
                    sync_image_view.setOnClickListener{listner?.viewPicOnLick(mList?.get(adapterPosition)?.face_image_link!!,mList?.get(adapterPosition)?.user_name!!)}
                }


                if(mList?.get(adapterPosition)?.isFaceRegistered!!){
                    sync_status_failed_iv.visibility=View.GONE
                    sync_status_iv.visibility=View.VISIBLE
                    //sync_delete_iv.setImageResource(R.drawable.trash_red)

                    //sync_delete_iv_red.visibility=View.VISIBLE
                    //sync_delete_iv.visibility=View.GONE

                    //sync_delete_iv.isEnabled=true
                    click_for_photo_reg_tv.isEnabled=false
                    try{
                        click_for_photo_reg_tv.text="Registered on "+mList?.get(adapterPosition)?.registration_date_time!!
                    }
                    catch (ex:java.lang.Exception){

                    }
                    click_for_photo_reg_tv.setTextColor(resources.getColor(R.color.color_custom_green))
                }else{
                    sync_status_failed_iv.visibility=View.VISIBLE
                    sync_status_iv.visibility=View.GONE
                    //sync_delete_iv.setImageResource(R.drawable.ic_delete)

                    //sync_delete_iv_red.visibility=View.GONE
                    //sync_delete_iv.visibility=View.VISIBLE

                    //sync_delete_iv.isEnabled=false
                    click_for_photo_reg_tv.isEnabled=true
                    click_for_photo_reg_tv.text="Click for Registration"
                    click_for_photo_reg_tv.setTextColor(resources.getColor(R.color.color_custom_red))
                }


                //if(mList?.get(adapterPosition)?.IsPhotoDeleteShow!!){
                if(Pref.IsPhotoDeleteShow){
                    sync_delete_iv_red.visibility=View.VISIBLE
                    ll_row_user_list_face_regis_tagline.visibility=View.VISIBLE
                }else{
                    sync_delete_iv_red.visibility=View.GONE
                    ll_row_user_list_face_regis_tagline.visibility=View.GONE
                }

                if(Pref.IsShowTypeInRegistration){
                    if(mList?.get(adapterPosition)?.IsShowTypeInRegistrationForSpecificUser!!){
                        click_for_update_type_tv.visibility=View.VISIBLE
                    }else{
                        click_for_update_type_tv.visibility=View.GONE
                    }
                }else{
                    click_for_update_type_tv.visibility=View.GONE
                }

                if(!mList?.get(adapterPosition)?.type_name.equals(""))
                    photo_reg_user_type_name_tv.text= "Sales Rep Type : "+ mList?.get(adapterPosition)?.type_name!!

                if(!mList?.get(adapterPosition)?.aadhar_image_link!!.contains("CommonFolder") && mList?.get(adapterPosition)?.isFaceRegistered!!){
                    photo_reg_user_old_reg_tv.visibility=View.VISIBLE
                }else{
                    //photo_reg_user_old_reg_tv.visibility=View.GONE
                    if(mList?.get(adapterPosition)?.Registered_with!!.length>0){
                        photo_reg_user_old_reg_tv.visibility=View.VISIBLE
                        photo_reg_user_old_reg_tv.setTextColor(resources.getColor(R.color.color_custom_green))
                        photo_reg_user_old_reg_tv.text= "Registered with\n"+mList?.get(adapterPosition)?.Registered_with!!+"     "
                    }else{
                        photo_reg_user_old_reg_tv.visibility=View.GONE
                    }

                }

                if(mList?.get(adapterPosition)?.isFaceRegistered!!){

                }else{
                    sync_image_view.setColorFilter(ContextCompat.getColor(getContext(), R.color.black_50))
                }

                if(Pref.UpdateUserName){
                    photo_reg_user_ph_update_name.visibility=View.VISIBLE
                }else{
                    photo_reg_user_ph_update_name.visibility=View.GONE
                }

                if(Pref.IsAllowClickForPhotoRegister){
                    click_for_photo_reg_tv.visibility=View.VISIBLE
                }else{
                    click_for_photo_reg_tv.visibility=View.GONE
                }

                if(mList?.get(adapterPosition)?.UpdateOtherID!!){
                    photo_reg_user_other_id_update_tv.visibility=View.VISIBLE
                }else{
                    photo_reg_user_other_id_update_tv.visibility=View.GONE
                }
                if(mList?.get(adapterPosition)?.UpdateUserID!!){
                    photo_reg_user_login_id_update_tv.visibility=View.VISIBLE
                }else{
                    photo_reg_user_login_id_update_tv.visibility=View.GONE
                }
                photo_reg_user_other_id_update_tv.setOnClickListener{listner?.updateOtherIDOnClick(mList?.get(adapterPosition)!!)}
                photo_reg_user_login_id_update_tv.setOnClickListener{listner?.updateLoginIDOnClick(mList?.get(adapterPosition)!!)}

                iv_aadhaar_ion.visibility=View.GONE

                try{
                    if(mList?.get(adapterPosition)?.Employee_Designation!!.length>0){
                        photo_reg_user_designation_name_tv.text = mList?.get(adapterPosition)?.Employee_Designation!!
                    }else{
                        photo_reg_user_designation_name_tv.text = ""
                    }
                }catch (ex:Exception){
                    photo_reg_user_designation_name_tv.text = ""
                }


            }
        }
    }


    override fun getFilter(): Filter {
        return SearchFilter()
    }

    inner class SearchFilter : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val results = FilterResults()

            filterList?.clear()

            tempList?.indices!!
                    .filter { tempList?.get(it)?.user_name?.toLowerCase()?.contains(p0?.toString()?.toLowerCase()!!)!!  ||
                            tempList?.get(it)?.user_login_id?.toLowerCase()?.contains(p0?.toString()?.toLowerCase()!!)!!}
                    .forEach { filterList?.add(tempList?.get(it)!!) }

            results.values = filterList
            results.count = filterList?.size!!

            return results
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {

            try {
                filterList = results?.values as ArrayList<UserListResponseModel>?
                mList?.clear()
                val hashSet = HashSet<String>()
                if (filterList != null) {

                    filterList?.indices!!
                            .filter { hashSet.add(filterList?.get(it)?.user_name!!) }
                            .forEach { mList?.add(filterList?.get(it)!!) }

                    getSize(mList?.size!!)

                    notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refreshList(list: ArrayList<UserListResponseModel>) {
        mList?.clear()
        mList?.addAll(list)

        tempList?.clear()
        tempList?.addAll(list)

        if (filterList == null)
            filterList = ArrayList()
        filterList?.clear()

        notifyDataSetChanged()
    }



}