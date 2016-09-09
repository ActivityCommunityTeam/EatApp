package com.dijiaapp.eatserviceapp.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.dijiaapp.eatserviceapp.R;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_name_et)
    EditText mLoginNameEt;
    @BindView(R.id.login_password_et)
    EditText mLoginPasswordEt;
    @BindView(R.id.login_rememberPassword)
    CheckBox mLoginRememberPassword;
    @BindView(R.id.login_auto_login)
    CheckBox mLoginAutoLogin;
    @BindView(R.id.login_bt)
    Button mLoginBt;
    @BindView(R.id.login_form)
    ScrollView mLoginForm;
    @BindView(R.id.login_progress)
    ProgressBar mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginBt.setEnabled(false);
        initLogin();
        initLoginBt();
    }



    private void initLoginBt() {
        RxView.clicks(mLoginBt).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {

            }
        });
    }

    private void initLogin() {
        Observable<CharSequence> usernameOs = RxTextView.textChanges(mLoginNameEt).skip(1);
        Observable<CharSequence> passwordOs = RxTextView.textChanges(mLoginPasswordEt).skip(1);

        Observable.combineLatest(usernameOs, passwordOs, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                boolean usernameBl = !TextUtils.isEmpty(charSequence);
                boolean passwordBl = !TextUtils.isEmpty(charSequence2);
                if (!usernameBl) {
                    mLoginNameEt.setError("请输入用户名");
                } else {
                    mLoginNameEt.setError(null);
                }

                if (!passwordBl)
                    mLoginPasswordEt.setError("请输入密码");
                else
                    mLoginPasswordEt.setError(null);

                return usernameBl && passwordBl;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                mLoginBt.setEnabled(aBoolean);
            }
        });
    }
}
