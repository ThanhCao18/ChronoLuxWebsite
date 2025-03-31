package com.hau.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hau.constant.SystemConstant;
import com.hau.dto.UserFaceBookDto;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class UserFaceBookUtil {
    private UserFaceBookDto userFaceBookDto;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserFaceBookUtil(UserFaceBookDto userFaceBookDto) {
        this.userFaceBookDto = userFaceBookDto;
    }

    public UserFaceBookDto getUserFaceBookDto() {
        return userFaceBookDto;
    }

    public void setUserFaceBookDto(UserFaceBookDto userFaceBookDto) {
        this.userFaceBookDto = userFaceBookDto;
    }

    public UserFaceBookUtil(String accessToken) {
        super();
        this.accessToken = accessToken;

    }
    public static String getToken(String code) throws ClientProtocolException, IOException
    {
        String response = Request.Post(SystemConstant.FACEBOOK_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", SystemConstant.FACEBOOK_CLIENT_ID)
                                .add("client_secret", SystemConstant.FACEBOOK_CLIENT_SECRET)
                                .add("redirect_uri", SystemConstant.FACEBOOK_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", SystemConstant.GOOGLE_GRANT_TYPE).build()
                ).execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;

    }
    public static UserFaceBookUtil toUser(final String accessToken) throws ClientProtocolException,
            IOException {

        String link = SystemConstant.FACEBOOK_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        UserFaceBookDto fbAccount= new Gson().fromJson(response, UserFaceBookDto.class);

        return new UserFaceBookUtil(fbAccount);

    }
}
