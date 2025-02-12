package com.furniconbreeze.features.activities.presentation

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniconbreeze.R
import com.furniconbreeze.app.domain.AddChemistEntity
import com.furniconbreeze.app.utils.AppUtils
import kotlinx.android.synthetic.main.inflate_doc_chemist_activity_item.view.*

import java.lang.Exception


/**
 * Created by Saikat on 07-01-2020.
 */
class ChemistActivityAdapter(private val context: Context, private val activityList: ArrayList<AddChemistEntity>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ChemistActivityAdapter.MyViewHolder>() {

    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(context, activityList, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = layoutInflater.inflate(R.layout.inflate_doc_chemist_activity_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(context: Context, activityList: ArrayList<AddChemistEntity>, listener: OnItemClickListener) {
            try {
                if (adapterPosition % 2 == 0)
                    itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.report_screen_bg))
                else
                    itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))

                if (activityList[adapterPosition].isUploaded!!)
                    itemView.iv_activity_sync_status.setImageResource(R.drawable.ic_registered_shop_sync)
                else {
                    itemView.iv_activity_sync_status.setImageResource(R.drawable.ic_registered_shop_not_sync)
                    itemView.iv_activity_sync_status.setOnClickListener {
                        listener.onSyncClick(adapterPosition)
                    }
                }

                itemView.tv_activity_view.setOnClickListener {
                    listener.onViewClick(adapterPosition)
                }

                itemView.iv_activity_edit.setOnClickListener {
                    listener.onEditClick(adapterPosition)
                }

                if (!TextUtils.isEmpty(activityList[adapterPosition].visit_date))
                    itemView.tv_activity_date.text = AppUtils.changeAttendanceDateFormat(activityList[adapterPosition].visit_date!!)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface OnItemClickListener {
        fun onViewClick(adapterPosition: Int)

        fun onSyncClick(adapterPosition: Int)

        fun onEditClick(adapterPosition: Int)
    }
}