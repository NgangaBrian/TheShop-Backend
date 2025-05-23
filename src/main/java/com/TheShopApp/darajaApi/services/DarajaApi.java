package com.TheShopApp.darajaApi.services;

import com.TheShopApp.darajaApi.dtos.*;

public interface DarajaApi {
    /**
     * Returns Daraja Api Access Token Response
     */
    AccessTokenResponse getAccessToken();

    RegisterUrlResponse registerUrl();

    STKPushSynchronousResponse performSTKPushRequest(InternalSTKPushRequest internalSTKPushRequest);
}
