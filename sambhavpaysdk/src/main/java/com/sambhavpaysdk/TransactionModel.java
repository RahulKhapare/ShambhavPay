package com.sambhavpaysdk;

import android.text.TextUtils;

public class TransactionModel {

    String openssl_cipher_name;
    String key_len;
    String sp_salt_key;
    String PG_URL;
    String PGStatusCheck_URL;
    String PGCancel_URL;
    String PGRefund_URL;
    String OrderNo;
    String TotalAmount;
    String CurrencyName;
    String MeTransReqType;
    String RecurringPeriod;
    String RecurringDay;
    String NoOfRecurring;
    String Mid;
    String AddField1;
    String AddField2;
    String AddField3;
    String AddField4;
    String AddField5;
    String AddField6;
    String AddField7;
    String AddField8;
    String AddField9;
    String AddField10;
    String Address;
    String City;
    String State;
    String Pincode;
    String Source;
    String EmailId;
    String MobileNo;
    String EmailInvoice;
    String MobileInvoice;
    String ResponseUrl;
    String Optional1;
    String Optional2;
    String CustomerName;
    String Tid;
    String SecretKey;
    String SaltKey;
    String TxnRefNo;
    String TxnAmount;
    String CancelAmount;
    String CancelOrderNo;
    String RefundAmount;
    String RefundOrderNo;


    public String getOpenssl_cipher_name() {
        return openssl_cipher_name;
    }

    public void setOpenssl_cipher_name(String openssl_cipher_name) {
        this.openssl_cipher_name = openssl_cipher_name;
    }

    public String getKey_len() {
        return key_len;
    }

    public void setKey_len(String key_len) {
        this.key_len = key_len;
    }

    public String getSp_salt_key() {
        return sp_salt_key;
    }

    public void setSp_salt_key(String sp_salt_key) {
        this.sp_salt_key = sp_salt_key;
    }

    public String getPG_URL() {
        return PG_URL;
    }

    public void setPG_URL(String PG_URL) {
        this.PG_URL = PG_URL;
    }

    public String getPGStatusCheck_URL() {
        return PGStatusCheck_URL;
    }

    public void setPGStatusCheck_URL(String PGStatusCheck_URL) {
        this.PGStatusCheck_URL = PGStatusCheck_URL;
    }

    public String getPGCancel_URL() {
        return PGCancel_URL;
    }

    public void setPGCancel_URL(String PGCancel_URL) {
        this.PGCancel_URL = PGCancel_URL;
    }

    public String getPGRefund_URL() {
        return PGRefund_URL;
    }

    public void setPGRefund_URL(String PGRefund_URL) {
        this.PGRefund_URL = PGRefund_URL;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    public String getMeTransReqType() {
        return MeTransReqType;
    }

    public void setMeTransReqType(String meTransReqType) {
        MeTransReqType = meTransReqType;
    }

    public String getRecurringPeriod() {
        return RecurringPeriod;
    }

    public void setRecurringPeriod(String recurringPeriod) {
        if(!TextUtils.isEmpty(recurringPeriod)){
            RecurringPeriod = recurringPeriod;
        }else {
            RecurringPeriod = "NA";
        }
    }

    public String getRecurringDay() {
        return RecurringDay;
    }

    public void setRecurringDay(String recurringDay) {
        RecurringDay = recurringDay;
    }

    public String getNoOfRecurring() {
        return NoOfRecurring;
    }

    public void setNoOfRecurring(String noOfRecurring) {
        NoOfRecurring = noOfRecurring;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public String getAddField1() {
        return AddField1;
    }

    public void setAddField1(String addField1) {
        if(!TextUtils.isEmpty(addField1)){
            AddField1 = addField1;
        }else {
            AddField1 = "null";
        }
    }

    public String getAddField2() {
        return AddField2;
    }

    public void setAddField2(String addField2) {
        if(!TextUtils.isEmpty(addField2)){
            AddField2 = addField2;
        }else {
            AddField2 = "null";
        }
    }

    public String getAddField3() {
        return AddField3;
    }

    public void setAddField3(String addField3) {
        if(!TextUtils.isEmpty(addField3)){
            AddField3 = addField3;
        }else {
            AddField3 = "null";
        }
    }

    public String getAddField4() {
        return AddField4;
    }

    public void setAddField4(String addField4) {
        if(!TextUtils.isEmpty(addField4)){
            AddField4 = addField4;
        }else {
            AddField4 = "null";
        }
    }

    public String getAddField5() {
        return AddField5;
    }

    public void setAddField5(String addField5) {
        if(!TextUtils.isEmpty(addField5)){
            AddField5 = addField5;
        }else {
            AddField5 = "null";
        }
    }

    public String getAddField6() {
        return AddField6;
    }

    public void setAddField6(String addField6) {
        if(!TextUtils.isEmpty(addField6)){
            AddField6 = addField6;
        }else {
            AddField6 = "null";
        }
    }

    public String getAddField7() {
        return AddField7;
    }

    public void setAddField7(String addField7) {
        if(!TextUtils.isEmpty(addField7)){
            AddField7 = addField7;
        }else {
            AddField7 = "null";
        }
    }

    public String getAddField8() {
        return AddField8;
    }

    public void setAddField8(String addField8) {
        if(!TextUtils.isEmpty(addField8)){
            AddField8 = addField8;
        }else {
            AddField8 = "null";
        }
    }

    public String getAddField9() {
        return AddField9;
    }

    public void setAddField9(String addField9) {
        if(!TextUtils.isEmpty(addField9)){
            AddField9 = addField9;
        }else {
            AddField9 = "null";
        }
    }

    public String getAddField10() {
        return AddField10;
    }

    public void setAddField10(String addField10) {
        if(!TextUtils.isEmpty(addField10)){
            AddField10 = addField10;
        }else {
            AddField10 = "null";
        }
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        if(!TextUtils.isEmpty(address)){
            Address = address;
        }else {
            Address = "null";
        }
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        if(!TextUtils.isEmpty(city)){
            City = city;
        }else {
            City = "null";
        }
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        if(!TextUtils.isEmpty(state)){
            State = state;
        }else {
            State = "null";
        }
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        if(!TextUtils.isEmpty(pincode)){
            Pincode = pincode;
        }else {
            Pincode = "null";
        }
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        if(!TextUtils.isEmpty(source)){
            Source = source;
        }else {
            Source = "null";
        }
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        if(!TextUtils.isEmpty(emailId)){
            EmailId = emailId;
        }else {
            EmailId = "null";
        }
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        if(!TextUtils.isEmpty(mobileNo)){
            MobileNo = mobileNo;
        }else {
            MobileNo = "null";
        }
    }

    public String getEmailInvoice() {
        return EmailInvoice;
    }

    public void setEmailInvoice(String emailInvoice) {
        if(!TextUtils.isEmpty(emailInvoice)){
            EmailInvoice = emailInvoice;
        }else {
            EmailInvoice = "null";
        }
    }

    public String getMobileInvoice() {
        return MobileInvoice;
    }

    public void setMobileInvoice(String mobileInvoice) {
        if(!TextUtils.isEmpty(mobileInvoice)){
            MobileInvoice = mobileInvoice;
        }else {
            MobileInvoice = "null";
        }
    }

    public String getResponseUrl() {
        return ResponseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        ResponseUrl = responseUrl;
    }

    public String getOptional1() {
        return Optional1;
    }

    public void setOptional1(String optional1) {
        if(!TextUtils.isEmpty(optional1)){
            Optional1 = optional1;
        }else {
            Optional1 = "null";
        }
    }

    public String getOptional2() {
        return Optional2;
    }

    public void setOptional2(String optional2) {
        if(!TextUtils.isEmpty(optional2)){
            Optional2 = optional2;
        }else {
            Optional2 = "null";
        }
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        if(!TextUtils.isEmpty(customerName)){
            CustomerName = customerName;
        }else {
            CustomerName = "null";
        }
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getSaltKey() {
        return SaltKey;
    }

    public void setSaltKey(String saltKey) {
        SaltKey = saltKey;
    }

    public String getTxnRefNo() {
        return TxnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
        TxnRefNo = txnRefNo;
    }

    public String getTxnAmount() {
        return TxnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        TxnAmount = txnAmount;
    }

    public String getCancelAmount() {
        return CancelAmount;
    }

    public void setCancelAmount(String cancelAmount) {
        CancelAmount = cancelAmount;
    }

    public String getCancelOrderNo() {
        return CancelOrderNo;
    }

    public void setCancelOrderNo(String cancelOrderNo) {
        CancelOrderNo = cancelOrderNo;
    }

    public String getRefundAmount() {
        return RefundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        RefundAmount = refundAmount;
    }

    public String getRefundOrderNo() {
        return RefundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        RefundOrderNo = refundOrderNo;
    }
}
