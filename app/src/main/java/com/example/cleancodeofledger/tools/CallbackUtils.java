package com.example.cleancodeofledger.tools;

import androidx.activity.result.ActivityResult;
import java.io.IOException;

public class CallbackUtils {
    public interface APIReturn {
        void Callback(boolean isok, String DataOrErrorMsg);

    }

    public interface messageReturn {
        void Callback(String message);
    }

    public interface IntReturn {
        void Callback(int message);
    }

    public interface noReturn {
        void Callback();

    }

    public interface ReturnData<T> {
        void Callback(Boolean isOK, String ErrorMsg, T data);

    }

    public interface ActivityReturn {
        void Callback(ActivityResult activityResult);

    }

    public interface TokenReturn {
        void Callback();

    }

    public interface DeviceReturn{
        void Callback(Boolean deviceReturn);

    }

    public interface TimeoutReturn{
        void Callback(IOException timeout);

    }
}
