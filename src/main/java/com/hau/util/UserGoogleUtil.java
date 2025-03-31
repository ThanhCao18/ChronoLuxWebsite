package com.hau.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hau.constant.SystemConstant;
import com.hau.dto.UserGoogleDto;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class UserGoogleUtil {
    private UserGoogleDto userGoogleDto;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserGoogleUtil(UserGoogleDto userGoogleDto) {
        this.userGoogleDto = userGoogleDto;
    }

    public UserGoogleDto getUserGoogleDto() {
        return userGoogleDto;
    }

    public void setUserGoogleDto(UserGoogleDto userGoogleDto) {
        this.userGoogleDto = userGoogleDto;
    }

    public UserGoogleUtil(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(SystemConstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", SystemConstant.GOOGLE_CLIENT_ID)
                        .add("client_secret", SystemConstant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", SystemConstant.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", SystemConstant.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static UserGoogleUtil toUser( String accessToken) throws ClientProtocolException, IOException {
        String link = SystemConstant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDto googlePojo = new Gson().fromJson(response, UserGoogleDto.class);

        return new UserGoogleUtil(googlePojo);
    }
}
