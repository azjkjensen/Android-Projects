package info.jkjensen.familymap.UI.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import info.jkjensen.familymap.UI.MainActivity;
import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.R;
import info.jkjensen.familymap.WebTools.HttpClient;

/**
 * Created by Jk on 3/16/2016.
 */
public class LoginFragment extends Fragment {

    private FamilyMap mFamilyMap;

    private Button mSignInButton;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mHostIP;
    private EditText mPort;

    private URL mPostUrl;
    private URL mGetUserInfoURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFamilyMap = FamilyMap.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mSignInButton = (Button) v.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPostUrl = new URL("http://" + getHostIP() + ":" + getPort() + "/user/login");
                } catch (Exception e){
                    Log.e("http", e.getMessage());
                }

                Toast toast = Toast.makeText(getActivity(), mPostUrl.toString(),
                        Toast.LENGTH_SHORT);
//                toast.show();

                LoginRequestTask task = new LoginRequestTask();
                //Makes the http POST call to login and then the GET call to retrieve user data
                task.execute();
            }
        });

        mUsername = (EditText) v.findViewById(R.id.username_et);

        mPassword = (EditText) v.findViewById(R.id.password_et);

        mHostIP = (EditText) v.findViewById(R.id.host_et);
        mFamilyMap.setHostIP(mHostIP.getText().toString());
        mHostIP.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mFamilyMap.setHostIP(mHostIP.getText().toString());
                return true;
            }
        });

        mPort= (EditText) v.findViewById(R.id.port_et);
        mFamilyMap.setPort(mPort.getText().toString());

        return v;
    }

    public String getUsername(){
        return mUsername.getText().toString();
    }

    public String getPassword(){
        return mPassword.getText().toString();
    }

    public String getHostIP() {
        return mHostIP.getText().toString();
    }

    public String getPort() {
        return mPort.getText().toString();
    }

    private class LoginRequestTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            try {
                String result = new HttpClient()
                        .userLogin(mPostUrl, getUsername(), getPassword());
                if(result == "error"){
                    throw new Exception("Failed to connect, check your IP");
                }
                JSONObject response = new JSONObject(result);
                Log.i("http", response.toString(2));

                if(!response.has("Authorization")) {
                    return response.getString("message");
                }

                mFamilyMap.setAuthToken(response.getString("Authorization"));
                mFamilyMap.setUsername(response.getString("userName"));
                mFamilyMap.setUserId(response.getString("personId"));
                mFamilyMap.mIsUserLoggedIn = true;

//                ((MainActivity) getActivity()).onLogin();
            } catch (Exception e){
                Log.e("http", e.getMessage());
                return e.getMessage();
            }
            return "ERROR";
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            if(mFamilyMap.mIsUserLoggedIn) {
                try {
                    mGetUserInfoURL = new URL("http://" + getHostIP() + ":" + getPort() +
                            "/person/" + mFamilyMap.getUserId());
                } catch (MalformedURLException m) {
                    Log.e("http", m.getMessage());
                }

                mFamilyMap.setHostIP(mHostIP.getText().toString());

                RetrieveUserInfoTask task = new RetrieveUserInfoTask();
                task.execute();
            }
            else {
                Toast.makeText(getActivity(),
                        message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class RetrieveUserInfoTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String result = new HttpClient()
                        .get(mGetUserInfoURL, mFamilyMap.getAuthToken());
                JSONObject response = new JSONObject(result);
                Log.i("http", response.toString(2));

                mFamilyMap.setUserFirstName(response.getString("firstName"));
                mFamilyMap.setUserLastName(response.getString("lastName"));
                mFamilyMap.setUserGender(response.getString("gender"));


            } catch (JSONException jsone){
                Log.e("http", jsone.getMessage());
                Toast failedToast = Toast.makeText(getActivity(),
                        "Login Failed. Please try again.", Toast.LENGTH_LONG);
                failedToast.show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Welcome " + mFamilyMap.getFullName(),
                    Toast.LENGTH_SHORT).show();

            ((MainActivity) getActivity()).onLogin();
        }
    }
}
