/*
 * Copyright (C) 2018 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package customhttp3.recipes;

import java.io.IOException;
import customhttp3.Credentials;
import customhttp3.Interceptor;
import customhttp3.OkHttpClient;
import customhttp3.Request;
import customhttp3.Response;

public final class PreemptiveAuth {
  private final OkHttpClient client;

  public PreemptiveAuth() {
    client = new OkHttpClient.Builder()
        .addInterceptor(
            new BasicAuthInterceptor("publicobject.com", "jesse", "password1"))
        .build();
  }

  public void run() throws Exception {
    Request request = new Request.Builder()
        .url("https://publicobject.com/secrets/hellosecret.txt")
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

      System.out.println(response.body().string());
    }
  }

  public static void main(String... args) throws Exception {
    new PreemptiveAuth().run();
  }

  static final class BasicAuthInterceptor implements Interceptor {
    private final String credentials;
    private final String host;

    BasicAuthInterceptor(String host, String username, String password) {
      this.credentials = Credentials.basic(username, password);
      this.host = host;
    }

    @Override public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      if (request.url().host().equals(host)) {
        request = request.newBuilder()
            .header("Authorization", credentials)
            .build();
      }
      return chain.proceed(request);
    }
  }
}
