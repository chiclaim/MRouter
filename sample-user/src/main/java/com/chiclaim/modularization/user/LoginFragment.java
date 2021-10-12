package com.chiclaim.modularization.user;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiclaim.modularization.business.BusinessConstants;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Created by johnny on 2018/2/10.
 */
@Route(path = BusinessConstants.USER_LOGIN_FRAGMENT)
public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_layout, container, false);
    }
}
