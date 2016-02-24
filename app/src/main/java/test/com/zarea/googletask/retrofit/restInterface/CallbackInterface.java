package test.com.zarea.googletask.retrofit.restInterface;

import android.util.Log;

import java.net.SocketTimeoutException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import test.com.zarea.googletask.retrofit.body.RestError;

/**
 * Created by zarea on 2/23/16.
 */
public abstract class CallbackInterface<T> implements Callback<T> {

    public abstract void failure(RestError restError);

    public abstract void success(T t, Response response);

    @Override
    public void failure(RetrofitError error) {

        //TODO- Due to error in parsing gson!!
        RestError restError = (RestError) error.getBodyAs(RestError.class);

        if (restError == null)
            restError = new RestError();

        switch (error.getKind()) {
            case HTTP:
                if (error.getResponse().getStatus() == 401) {
                    //TODO - if 401 so just logout.
                    restError = new RestError(restError.getCode(), "Invalid credentials. Please verify login info.", error.getKind().name());
                } else {
                    /*
                    restError = (error.getResponse() != null && error.getMessage() != null) ?
                            new RestError(error.getResponse().getStatus(), error.getMessage(), error.getKind().name())
                            : new RestError(500, "Sorry, something went wrong!", error.getKind().name());
                            */
                    String errorDetails = (restError.getErrorDetails() == null || restError.getErrorDetails().isEmpty()) ? "Sorry, something went wrong!" : restError.getErrorDetails();
                    restError = new RestError(restError.getCode(), errorDetails, restError.getErrorDetails());
                }
                break;
            case NETWORK:
                if (error.getCause() instanceof SocketTimeoutException)
                    restError = new RestError(500, "Connection Timeout. Please verify your internet connection.", error.getKind().name());
                else
                    restError = new RestError(500, "No Connection. Please verify your internet connection.", error.getKind().name());
                break;
            case CONVERSION:
            case UNEXPECTED:
                restError = new RestError(500, "Sorry, something went wrong!", error.getKind().name());
                break;
        }

        Log.e("ConnectionError", "ErrorType:" + restError.toString());
        failure(restError);
    }
}
