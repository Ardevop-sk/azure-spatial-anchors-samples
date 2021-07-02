// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
package com.microsoft.sampleandroid;

import android.os.AsyncTask;

import com.example.mypackage.api.AnchorApi;
import com.example.mypackage.invoker.ApiClient;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import retrofit2.Call;

// Gets an anchor GUID from the service detailed in the
// Azure Spatial Anchors share anchors across devices tutorial
// Consumes the anchor number associated with the GUID for easier typing
class AnchorGetter extends AsyncTask<String, Void, String> {

  private final ApiClient apiClient;
  private final Consumer<String> anchorLocatedCallback;

  public AnchorGetter(ApiClient apiClient, Consumer<String> anchorLocatedCallback) {
    this.apiClient = apiClient;
    this.anchorLocatedCallback = anchorLocatedCallback;
  }

  @Override
  protected String doInBackground(String... input) {
    return getAnchor(input[0]);
  }

  @Override
  protected void onPostExecute(String result) {
    if (this.anchorLocatedCallback != null) {
      this.anchorLocatedCallback.accept(result);
    }
  }

  private String getAnchor(String AnchorNumber) {
    String ret;
    try {
      AnchorApi anchorApi = apiClient.createService(AnchorApi.class);
      Call<String> anchorsApiCall = anchorApi.anchorsAnchorNumberGet(Long.valueOf(AnchorNumber));
      ret = anchorsApiCall.execute().body();
    } catch (Exception e) {
      ret = e.getMessage();
    }

    return ret;
  }
}
