package web.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static web.http.Headers.COOKIE;
import static web.http.Headers.USER_AGENT_HEADER;

public class GetRequest extends AbstractRequest {

    @Override
    public Response send(Host host) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request.Builder builder = new Request.Builder()
                    .url(host.createUrl())
                    .get()
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE);

            if (host.isBegetProtection())
                builder.addHeader(COOKIE, BEGET_PROTECTION_COOKIE);

            return client.newCall(builder.build()).execute();
        } catch (Exception xep) {
            return error(xep.getLocalizedMessage());
        }
    }

}