package com.furniconbreeze.features.contacts

import com.furniconbreeze.app.domain.CallHisEntity
import com.furniconbreeze.app.domain.CompanyMasterEntity
import com.furniconbreeze.app.domain.SourceMasterEntity
import com.furniconbreeze.app.domain.StageMasterEntity
import com.furniconbreeze.app.domain.StatusMasterEntity
import com.furniconbreeze.app.domain.TeamAllListEntity
import com.furniconbreeze.app.domain.TeamListEntity
import com.furniconbreeze.app.domain.TypeMasterEntity
import com.furniconbreeze.base.BaseResponse

data class ContactGr(var gr_id:String="",var gr_name:String="")
data class ContactDtls(var gr_name:String="",var contact_id:String="",var addr:String="", var name:String="", var number:String="", var isTick:Boolean = false)
data class ScheduleContactDtls(val contact_name:String="", val contact_number:String="", val contact_id:String="", var isTick:Boolean = false)

data class ContactMasterRes(var company_list:ArrayList<CompanyMasterEntity>):BaseResponse()
data class TypeMasterRes(var type_list:ArrayList<TypeMasterEntity>):BaseResponse()
data class StatusMasterRes(var status_list:ArrayList<StatusMasterEntity>):BaseResponse()
data class TeamListRes(var member_list:ArrayList<TeamListEntity>):BaseResponse()
data class TeamAllListRes(var member_list:ArrayList<TeamAllListEntity>):BaseResponse()


data class CustomData(var id:String="",var name:String="")
data class CustomDataForAssignedto(var id:String="",var name:String="",var number:String="")

data class SourceMasterRes(var source_list:ArrayList<SourceMasterEntity>):BaseResponse()
data class StageMasterRes(var stage_list:ArrayList<StageMasterEntity>):BaseResponse()

data class CallHisDtls(var user_id:String="",var call_his_list:ArrayList<CallHisEntity> = ArrayList()) :BaseResponse()

data class CompanyReqData(var created_by:String = "",var session_token:String="",var company_name_list:ArrayList<CompanyName> = ArrayList())

data class CompanyName(var company_name:String="")

data class AutoMailDtls(var automail_sending_email:String="",var automail_sending_pass:String="",var recipient_email_ids:String=""):BaseResponse()


