package info.jkjensen.familymap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import info.jkjensen.familymap.Model.FamilyMap;

public class MainActivity extends FragmentActivity {

    FamilyMap mFamilyMap;
    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFamilyMap = FamilyMap.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        mLoginFragment = (LoginFragment) fm.findFragmentById(R.id.fragment_container);
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mLoginFragment)
                    .commit();
        }

    }

    public void onLogin(){
        //remove login fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .remove(mLoginFragment)
                .commit();
        //add map fragment
    }
}
