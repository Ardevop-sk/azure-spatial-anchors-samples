// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
package com.microsoft.sampleandroid;

import android.os.AsyncTask;

import com.example.mypackage.api.AnchorApi;
import com.example.mypackage.invoker.ApiClient;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import retrofit2.Call;

// Posts an anchor GUID to the service detailed in the
// Azure Spatial Anchors share anchors across devices tutorial
// Provides the anchor number associated with the GUID for easier typing
class AnchorPoster extends AsyncTask<String, Void, String> {

  private final Consumer<String> anchorPostedCallback;
  private final ApiClient apiClient;

  public AnchorPoster(ApiClient apiClient, Consumer<String> anchorPostedCallback) {
    this.anchorPostedCallback = anchorPostedCallback;
    this.apiClient = apiClient;
  }

  @Override
  protected String doInBackground(String... input) {
    return postAnchor(input[0]);
  }

  @Override
  protected void onPostExecute(String result) {
    if (anchorPostedCallback != null) {
      anchorPostedCallback.accept(result);
    }
  }

  private String postAnchor(String anchor) {
    String ret;
    try {
      AnchorApi anchorApi = apiClient.createService(AnchorApi.class);
      Call<Long> anchorsApiCall = anchorApi.anchorsPost(anchor);
      ret = anchorsApiCall.execute().body() + "";
    } catch (Exception e) {
      ret = e.getMessage();
    }

    return ret;
  }
}
