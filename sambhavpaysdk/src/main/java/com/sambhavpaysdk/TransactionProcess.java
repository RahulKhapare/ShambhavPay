package com.sambhavpaysdk;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class TransactionProcess {

    private String saltKey = "Asdf@1234";
    private String openssl_cipher_name = "aes-256-cbc";
    private int key_len = 35;
    private String response_url = "https://dev.digiinterface.com/2022/sambhavpay-pg/response.php";

    private TransactionModel transactionModel;
    private Context context;

    public TransactionProcess(TransactionModel transactionModel, Context context) {
        this.transactionModel = transactionModel;
        this.context = context;
    }

    public void startTransaction() {
        String string = transactionModel.getMid();

        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("MID is not present");
            return;
        }

        string = transactionModel.getSecretKey();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("SecretKey is not present");
            return;
        }

        string = transactionModel.getSaltKey();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("SaltKey is not present");
            return;
        }

        string = transactionModel.getOrderNo();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("Order Number is not present");
            return;
        }


        string = transactionModel.getTotalAmount();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("Amount is not present");
            return;
        } else if (!string.contains(".")) {
            showToast("Invalid Amount , Please Convert it into Rupees with two decimal");
            return;
        }

        string = transactionModel.getCurrencyName();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("Currency Name is not present");
            return;
        }

        string = transactionModel.getMeTransReqType();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("Transaction Request Type is not present");
            return;
        }

        string = transactionModel.getResponseUrl();
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null")) {
            showToast("Response Url is not present");
            return;
        }

        string = transactionModel.getEmailId();
        if (!string.isEmpty() && !string.equalsIgnoreCase("null")) {
            if (!validEmail(string)) {
                showToast("Enter valid email id");
                return;
            }
        }

        string = transactionModel.getMobileNo();
        if (!string.isEmpty() && !string.equalsIgnoreCase("null")) {
            if (string.length() != 10) {
                showToast("Enter 10 digit mobile number");
                return;
            }
        }

        transactionModel.setPG_URL("https://pg.Sambhavpay.com/RequestPG");
        transactionModel.setPGStatusCheck_URL("https://pg.sambhavpay.com/StatusPG");
        transactionModel.setPGCancel_URL("https://pg.sambhavpay.com/CancelPG");
        transactionModel.setPGRefund_URL("https://pg.sambhavpay.com/RefundPG");

        setAmountData();

        //"Mid|OrderNo|TotalAmount|CurrencyName|MeTransReqType|AddField1|AddField2|AddField3|AddField4|AddField5|AddField6|AddField7|AddField8|AddField9|AddField10|EmailId|MobileNo|Address|City|State|Pincode|Source|EmailInvoice|MobileInvoice|ResponseUrl|Optional1|Optional2|CustomerName";

        Log.e("TAG", "parseAmount: " + transactionModel.getTotalAmount());

        string = transactionModel.getMid() + "," + transactionModel.getOrderNo() + "," + transactionModel.getTotalAmount() + "," +
                transactionModel.getCurrencyName() + "," + transactionModel.getMeTransReqType() + "," +
                transactionModel.getAddField1() + "," + transactionModel.getAddField2() + "," + transactionModel.getAddField3() + "," +
                transactionModel.getAddField4() + "," + transactionModel.getAddField5() + "," + transactionModel.getAddField6() + "," +
                transactionModel.getAddField7() + "," + transactionModel.getAddField8() + "," + transactionModel.getAddField9() + "," +
                transactionModel.getAddField10() + "," + transactionModel.getEmailId() + "," + transactionModel.getMobileNo() + "," +
                transactionModel.getAddress() + "," + transactionModel.getCity() + "," + transactionModel.getState() + "," +
                transactionModel.getPincode() + "," + transactionModel.getSource() + "," + transactionModel.getEmailInvoice() + "," +
                transactionModel.getMobileInvoice() + "," + transactionModel.getResponseUrl() + "," + transactionModel.getOptional1() + "," +
                transactionModel.getOptional2() + "," + transactionModel.getCustomerName();


        Log.e("TAG", "TransactionData: " + string);

//        String encryptString = encryptResponseString(string);
        String encryptString = encryptKey(string);

//        String decryptString = decryptKey(encryptString);

        if (encryptString.equalsIgnoreCase("Encryption Failed")) {
            showToast("Encryption Failed");
            return;
        } else {
            Log.e("TAG", "CheckEncryptionKey: " + encryptString);
            String checkSum = getCheckSum(transactionModel.getSaltKey(), transactionModel.getOrderNo(), transactionModel.getTotalAmount());
            if (checkSum.equalsIgnoreCase("Failed CheckSum")) {
                showToast("Failed CheckSum");
                return;
            } else {
                loadPaymentPage(encryptString.trim(), checkSum.trim());
                Log.e("TAG", "CheckSumKey: " + checkSum);
            }

        }

    }

    private void setAmountData() {

        Double amount = Double.parseDouble(transactionModel.getTotalAmount());
        int paise_amount = (int) (amount * 100);
        transactionModel.setTotalAmount(paise_amount + "");
    }

    public String encryptKey(String strToEncrypt) {
        String encryptedString = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                IvParameterSpec ivspec = new IvParameterSpec(iv);

                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(transactionModel.getSecretKey().toCharArray(), saltKey.getBytes(), 65536, 256);
                SecretKey tmp = factory.generateSecret(spec);
                SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//                Cipher cipher = Cipher.getInstance(openssl_cipher_name);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
                encryptedString = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            } catch (Exception e) {
                Log.e("TAG", "encryptKeyEXP: " + e.getMessage());
                System.out.println("Error while encrypting: " + e.toString());
                encryptedString = "Encryption Failed";
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                return Base64.getEncoder().encodeToString(encrypted).trim();
//            } else {
//                return android.util.Base64.encodeToString(encrypted, android.util.Base64.DEFAULT).trim();
//            }

        } else {
            showToast("Unable to encrypt, require minimum sdk is 26");
            encryptedString = "Encryption Failed";
        }
        return encryptedString;
    }

    public String decryptKey(String strToDecrypt) {
        String returnString = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                IvParameterSpec ivspec = new IvParameterSpec(iv);

                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(transactionModel.getSecretKey().toCharArray(), saltKey.getBytes(), 65536, 256);
                SecretKey tmp = factory.generateSecret(spec);
                SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//                Cipher cipher = Cipher.getInstance(openssl_cipher_name);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
                returnString = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            } catch (Exception e) {
                returnString = "Decryption Failed";
            }
        } else {
            showToast("Unable to encrypt, require minimum sdk is 26");
            returnString = "Decryption Failed";
        }

        return returnString;
    }

    public String getCheckSum(String secretKey, String orderNo, String amount) {

        Log.e("TAG", "getCheckSumKey: " + secretKey);
        Log.e("TAG", "getCheckSumOrderNo: " + orderNo);
        Log.e("TAG", "getCheckSumAMT: " + amount);

        String returnValue = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String dataString = orderNo + "," + amount;
                byte[] mac = null;
                String key_64 = Base64.getEncoder().encodeToString(secretKey.getBytes("UTF-8"));
                SecretKey key = new SecretKeySpec(key_64.getBytes("UTF-8"), "HmacSHA512");
                Mac m = Mac.getInstance("HmacSHA512");
                m.init(key);
                m.update(dataString.getBytes("UTF-8"));
                mac = m.doFinal();
                String hashValue = hex(mac);
                returnValue = hashValue;
            } else {
                showToast("Unable to encrypt, require minimum sdk is 26");
                returnValue = "Failed CheckSum";
            }

        } catch (Exception e) {
            returnValue = "Failed CheckSum";
        }

        return returnValue;
    }


    public String hex(byte[] input)
            throws Exception {
        char[] HEX_TABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer sb = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4 & 0xF)]);
            sb.append(HEX_TABLE[(input[i] & 0xF)]);
        }
        return sb.toString();
    }

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void showToast(String msg) {
        Toast toast = new Toast(context);
        View toast_view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        TextView textView = toast_view.findViewById(R.id.txtToast);
        textView.setText(msg);
        toast.setView(toast_view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void loadPaymentPage(String encrypt, String checkSum) {

        final View view = LayoutInflater.from(context).inflate(R.layout.loading_view, null, false);
        WebView webView = view.findViewById(R.id.webView);// new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportZoom(true);
        webView.setClickable(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.addJavascriptInterface(new ReadPostData(), "Android");
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.clearMatches();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("onPageStarted", url);
            }

            @Override
            public void onPageFinished(WebView v, String url) {
                view.findViewById(R.id.linearLayout).setVisibility(View.GONE);
                Log.e("onPageFinished", url);
            }
        });

        String encryptedString = encrypt;
        encryptedString = encryptedString.replace(" ", "");
        encryptedString = encryptedString.replace("\n", "");
        encryptedString = encryptedString.replace("\t", "");

        try {
            encryptedString = "mid=" + URLEncoder.encode(transactionModel.getMid(), "UTF-8") +
                    "&encryptReq=" + URLEncoder.encode(encryptedString, "UTF-8") + "&checkSum=" + URLEncoder.encode(checkSum, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            showToast(e.toString());
            e.printStackTrace();
        }

        Log.e("TAG", "loadPaymentPageData: " + encryptedString);

        webView.loadUrl(encryptedString);

//        webView.postUrl(transactionModel.getPG_URL(), encryptedString.getBytes());
        webView.postUrl("https://dev.digiinterface.com/2022/sambhavpay-pg/test.php", encryptedString.getBytes());

        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private class ReadPostData {
        @JavascriptInterface
        public void showToast(String response, String checksum, String mid) {
            Log.e("TAG", "web_response: " + response);
            Log.e("TAG", "web_checksum: " + checksum);
            Log.e("TAG", "web_mid: " + mid);
            String decryptedString = decryptKey(response);
            Log.e("TAG", "web_decryptKey: " + decryptedString);
            loadPaymentStatus(decryptedString);

        }
    }

    public void loadPaymentStatus(String statusKey) {

        final View view = LayoutInflater.from(context).inflate(R.layout.payment_status_view, null, false);

        TextView txtMid = view.findViewById(R.id.txtMid);
        TextView txtOrderNo = view.findViewById(R.id.txtOrderNo);
        TextView txtTrnNo = view.findViewById(R.id.txtTrnNo);
        TextView txtTotalAmt = view.findViewById(R.id.txtTotalAmt);
        TextView txtCurrencyName = view.findViewById(R.id.txtCurrencyName);
        TextView txtTrnType = view.findViewById(R.id.txtTrnType);
        TextView txtField1 = view.findViewById(R.id.txtField1);
        TextView txtField2 = view.findViewById(R.id.txtField2);
        TextView txtField3 = view.findViewById(R.id.txtField3);
        TextView txtField4 = view.findViewById(R.id.txtField4);
        TextView txtField5 = view.findViewById(R.id.txtField5);
        TextView txtField6 = view.findViewById(R.id.txtField6);
        TextView txtField7 = view.findViewById(R.id.txtField7);
        TextView txtField8 = view.findViewById(R.id.txtField8);
        TextView txtField9 = view.findViewById(R.id.txtField9);
        TextView txtField10 = view.findViewById(R.id.txtField10);
        TextView txtEmail = view.findViewById(R.id.txtEmail);
        TextView txtMobile = view.findViewById(R.id.txtMobile);
        TextView txtAddress = view.findViewById(R.id.txtAddress);
        TextView txtCity = view.findViewById(R.id.txtCity);
        TextView txtState = view.findViewById(R.id.txtState);
        TextView txtPincode = view.findViewById(R.id.txtPincode);
        TextView txtResponseCode = view.findViewById(R.id.txtResponseCode);
        TextView txtResponseMsg = view.findViewById(R.id.txtResponseMsg);
        TextView txtPayAmt = view.findViewById(R.id.txtPayAmt);
        TextView txtTnsResDate = view.findViewById(R.id.txtTnsResDate);

        String[] items = statusKey.split(",");

        //900000000000002,ORD261714,TXN22062714292735624444787,1050,INR,S,8611693,null,null,null,null,null,null,null,null,null,,,null,null,null,null,01,Transaction Failed,1050,27/06/2022 14:33:06,

        txtMid.setText(items[0]);
        txtOrderNo.setText(items[1]);
        txtTrnNo.setText(items[2]);
        txtTotalAmt.setText(parseAmount(items[3]));
        txtCurrencyName.setText(items[4]);
        txtTrnType.setText(items[5]);
        txtField1.setText(items[6]);
        txtField2.setText(items[7]);
        txtField3.setText(items[8]);
        txtField4.setText(items[9]);
        txtField5.setText(items[10]);
        txtField6.setText(items[11]);
        txtField7.setText(items[12]);
        txtField8.setText(items[13]);
        txtField9.setText(items[14]);
        txtField10.setText(items[15]);
        txtEmail.setText(items[16]);
        txtMobile.setText(items[17]);
        txtAddress.setText(items[18]);
        txtCity.setText(items[19]);
        txtState.setText(items[20]);
        txtPincode.setText(items[21]);
        txtResponseCode.setText(items[22]);
        txtResponseMsg.setText(items[23]);
        txtPayAmt.setText(parseAmount(items[24]));
        txtTnsResDate.setText(items[25]);

//        for (String item : items) {
//            String value = item;
//            if (txtMid.getText().toString().trim().equals("")) {
//                txtMid.setText(value);
//            } else if (txtOrderNo.getText().toString().trim().equals("")) {
//                txtOrderNo.setText(value);
//            } else if (txtTrnNo.getText().toString().trim().equals("")) {
//                txtTrnNo.setText(value);
//            } else if (txtTotalAmt.getText().toString().trim().equals("")) {
//                txtTotalAmt.setText(value);
//            } else if (txtCurrencyName.getText().toString().trim().equals("")) {
//                txtCurrencyName.setText(value);
//            } else if (txtTrnType.getText().toString().trim().equals("")) {
//                txtTrnType.setText(value);
//            } else if (txtField1.getText().toString().trim().equals("")) {
//                txtField1.setText(value);
//            } else if (txtField2.getText().toString().trim().equals("")) {
//                txtField2.setText(value);
//            } else if (txtField3.getText().toString().trim().equals("")) {
//                txtField3.setText(value);
//            } else if (txtField4.getText().toString().trim().equals("")) {
//                txtField4.setText(value);
//            } else if (txtField5.getText().toString().trim().equals("")) {
//                txtField5.setText(value);
//            } else if (txtField6.getText().toString().trim().equals("")) {
//                txtField6.setText(value);
//            } else if (txtField7.getText().toString().trim().equals("")) {
//                txtField7.setText(value);
//            } else if (txtField8.getText().toString().trim().equals("")) {
//                txtField8.setText(value);
//            } else if (txtField9.getText().toString().trim().equals("")) {
//                txtField9.setText(value);
//            } else if (txtField10.getText().toString().trim().equals("")) {
//                txtField10.setText(value);
//            } else if (txtEmail.getText().toString().trim().equals("")) {
//                txtEmail.setText(value);
//            } else if (txtMobile.getText().toString().trim().equals("")) {
//                txtMobile.setText(value);
//            } else if (txtAddress.getText().toString().trim().equals("")) {
//                txtAddress.setText(value);
//            } else if (txtCity.getText().toString().trim().equals("")) {
//                txtCity.setText(value);
//            } else if (txtState.getText().toString().trim().equals("")) {
//                txtState.setText(value);
//            } else if (txtPincode.getText().toString().trim().equals("")) {
//                txtPincode.setText(value);
//            } else if (txtResponseCode.getText().toString().trim().equals("")) {
//                txtResponseCode.setText(value);
//            } else if (txtResponseMsg.getText().toString().trim().equals("")) {
//                txtResponseMsg.setText(value);
//            } else if (txtPayAmt.getText().toString().trim().equals("")) {
//                txtPayAmt.setText(value);
//            } else if (txtTnsResDate.getText().toString().trim().equals("")) {
//                txtTnsResDate.setText(value);
//            }
//        }

        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private String parseAmount(String amount) {
        Double returnValue = 0.0d;
        try {
            returnValue = (double) Double.parseDouble(amount) / (double) 100;
        } catch (Exception e) {
            returnValue = 0.0d;
        }
        return returnValue + "";
    }

}
