package com.shambhavpay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.sambhavpaysdk.TransactionModel;
import com.sambhavpaysdk.TransactionProcess;
import com.shambhavpay.R;
import com.shambhavpay.databinding.ActivityMainBinding;
import com.shambhavpay.util.Click;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();
    }

    private void initView() {
        setData();
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.etxOrderNo.setText("ORD" + getRandomNumberString());
    }

    private void setData() {

        binding.etxMerchantID.setText("900000000000002");
        binding.etxTransactionType.setText("S");
        binding.etxOrderNo.setText("ORD" + getRandomNumberString());
        binding.etxSecretKey.setText("scrdvvMOyoj1nVnppuPDerEPE5bHp1EFgOA");
        binding.etxSlatKey.setText("salac294u7QMOa9TBrsV29TqqbargFVhN");
        binding.etxTotalAmount.setText("10.50");
        binding.etxCurrencyName.setText("INR");
        binding.etxResponseUrl.setText("https://dev.digiinterface.com/2022/sambhavpay-pg/response.php");

        hideClick(binding.etxMerchantID);
        hideClick(binding.etxOrderNo);
        hideClick(binding.etxSecretKey);
        hideClick(binding.etxSlatKey);
        hideClick(binding.etxCurrencyName);
        hideClick(binding.etxTransactionType);
//        hideClick(binding.etxTotalAmount);
        hideClick(binding.etxResponseUrl);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click.preventTwoClick(v);

                binding.etxOrderNo.setText("ORD" + getRandomNumberString());

                TransactionModel model = new TransactionModel();

                model.setMid(binding.etxMerchantID.getText().toString().trim());
                model.setSecretKey(binding.etxSecretKey.getText().toString().trim());
                model.setSaltKey(binding.etxSlatKey.getText().toString().trim());
                model.setOrderNo(binding.etxOrderNo.getText().toString().trim());
                model.setTotalAmount(binding.etxTotalAmount.getText().toString().trim());
                model.setCurrencyName(binding.etxCurrencyName.getText().toString().trim());
                model.setMeTransReqType(binding.etxTransactionType.getText().toString().trim());
                model.setResponseUrl(binding.etxResponseUrl.getText().toString().trim());
                model.setAddField1(binding.etxAddField1.getText().toString().trim());
                model.setAddField2(binding.etxAddField2.getText().toString().trim());
                model.setAddField3(binding.etxAddField3.getText().toString().trim());
                model.setCustomerName(binding.etxCustomerName.getText().toString().trim());
                model.setEmailId(binding.etxEmail.getText().toString().trim());
                model.setMobileNo(binding.etxMobileNumber.getText().toString().trim());
                model.setAddress(binding.etxAddress.getText().toString().trim());
                model.setCity(binding.etxCity.getText().toString().trim());
                model.setState(binding.etxState.getText().toString().trim());

                TransactionProcess transactionProcess = new TransactionProcess(model, activity);
                transactionProcess.startTransaction();


            }
        });

    }

    private void hideClick(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setClickable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}