package nrc.albumoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

public class LoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "e5afc7c1b0274c258089878b5cfbae74";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    //Creates bundle to store oauth code to use web api on next page
                    final Bundle extras = new Bundle();
                    extras.putString("AUTHENTICATION", response.getAccessToken());
                    Log.d("Success:", response.getAccessToken());
                    Intent intent2 = new Intent(this, MenuActivity.class);
                    intent2.putExtras(extras);
                    startActivity(intent2);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.d("Failure:", response.getError() + "");
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    Log.d("Uhhhh:", "LOL IDK");
            }
        }
    }
}
