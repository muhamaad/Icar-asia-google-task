//package test.com.zarea.googletask.view;
//
//import android.accounts.Account;
//import android.accounts.AccountManager;
//import android.accounts.AccountManagerCallback;
//import android.accounts.AccountManagerFuture;
//import android.accounts.OperationCanceledException;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//
//import java.util.HashMap;
//
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//import test.com.zarea.googletask.R;
//import test.com.zarea.googletask.model.TaskList;
//import test.com.zarea.googletask.retrofit.restInterface.ListsInterface;
//import test.com.zarea.googletask.util.StaticString;
//
//public class LogIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
//    private static int RC_SIGN_IN = 9000;
//    private SignInButton signInButton;
//    private GoogleSignInOptions gso;
//    private GoogleApiClient mGoogleApiClient;
//    private Toolbar toolbar;
//    private AccountManager accountManager;
//    Account[] accounts;
//    String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/tasks";
//    private RestAdapter adapter = new RestAdapter.Builder().setEndpoint(StaticString.SERVER).build();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_activity);
//        initViews();
//        init();
//    }
//
//    private void initViews() {
//        toolbar= (Toolbar) findViewById(R.id.toolbar);
//        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        setSupportActionBar(toolbar);
//    }
//
////    @Override
////    protected Dialog onCreateDialog(int id) {
////        switch (id) {
////            case DIALOG_ACCOUNTS:
////                AlertDialog.Builder builder = new AlertDialog.Builder(this);
////                builder.setTitle("Select a Google account");
////                final Account[] accounts = accountManager.getAccountsByType("com.google");
////                final int size = accounts.length;
////                String[] names = new String[[]size];
////                for (int i = 0; i < size; i++) {
////                    names[[]i] = accounts[[]i].name;
////                }
////                builder.setItems(names, new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int which) {
////                        // Stuff to do when the account is selected by the user
////                        gotAccount(accounts[[]which]);
////                    }
////                });
////                return builder.create();
////        }
////        return null;
////    }
//    private void init() {
//         accountManager = AccountManager.get(LogIn.this);
//         accounts = accountManager.getAccountsByType("com.google");
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
//        builder.setTitle("Select a Google account");
//        int size = accounts.length;
//        String [] names = new String[size];
//        for(int i =0; i<size;i++){
//            names[i] = accounts[i].name;
//        }
//        builder.setItems(names, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                accountManager.getAuthToken(accounts[which], AUTH_TOKEN_TYPE, null, LogIn.this, new AccountManagerCallback<Bundle>() {
//                    public void run(AccountManagerFuture<Bundle> future) {
//                        try {
//                            // If the user has authorized your application to use the tasks API
//                            // a token is available.
//                            String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
//                            // Now you can use the Tasks API...
////                            Toast.makeText(LogIn.this, token, Toast.LENGTH_SHORT).show();
//                            Log.d("tokentestteset", token);
//                            ListsInterface listsInterface = adapter.create(ListsInterface.class);
//                            HashMap<String,String> hashMap = new HashMap<String, String>();
//                            hashMap.put("maxResults","100");
//                            hashMap.put("accessToken",token);
//                            hashMap.put("key",token);
//                            listsInterface.getLists(hashMap, new Callback<TaskList>() {
//                                @Override
//                                public void success(TaskList taskList, Response response) {
//                                    Toast.makeText(LogIn.this, "done", Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void failure(RetrofitError error) {
//                                    Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } catch (OperationCanceledException e) {
//                            // TODO: The user has denied you access to the API, you should handle that
//                        } catch (Exception e) {
//                        }
//                    }
//                }, null);
//            }
//        });
//        builder.create();
//        builder.show();
//       /* test.com.zarea.googletask.database.Auth auth = test.com.zarea.googletask.database.Auth.getAuth();
//        if(auth==null) {
//            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(getResources().getString(R.string.server_clint_id))
//                    .requestServerAuthCode(getResources().getString(R.string.server_clint_id))
//                    .requestEmail()
//                    .build();
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                            .enableAutoManage(this, this)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                    .build();
//            signInButton.setOnClickListener(this);
//        }else{
//            Intent intent = new Intent(LogIn.this,TaskListActivity.class);
//            startActivity(intent);
//            finish();
//        }*/
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_button:
//                signIn();
//                break;
//        }
//    }
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Toast.makeText(LogIn.this, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == RC_SIGN_IN) {
//                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//                handleSignInResult(result);
//            }
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = result.getSignInAccount();
//            test.com.zarea.googletask.database.Auth auth = new test.com.zarea.googletask.database.Auth(acct.getId(),acct.getIdToken(),acct.getServerAuthCode(),acct.getDisplayName(),acct.getEmail());
//            auth.save();
//            Toast.makeText(LogIn.this,"Welcome " +acct.getDisplayName(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LogIn.this,TaskListActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            // Signed out, show unauthenticated UI.
//            Toast.makeText(LogIn.this, "failed", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
