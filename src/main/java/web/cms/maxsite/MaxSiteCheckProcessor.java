package web.cms.maxsite;

import com.google.inject.Inject;
import okhttp3.Response;
import web.cms.CMSType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public class MaxSiteCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    MaxSiteCheckProcessor(@Get Request request,
                          TextParser<Boolean> parser,
                          Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        checkViaMainPageGenerator();
        checkViaMainPageSpecifyKeywords();

        if (successAttempt.get() > 0)
            destination.insert(0, String.format(successMessage, CMSType.MAXSITE_CMS.getName(), successAttempt, attempt));
    }

    private void checkViaMainPageGenerator() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile(".*<meta name=\"generator\" content=\"(.*?)\">");

        attempt.incrementAndGet();

        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                parser.configure(pattern, 0);
                if (parser.parse(body))
                    successAttempt.incrementAndGet();
            }
        }
    }

    private void checkViaMainPageSpecifyKeywords() {
        Integer[] codes = { 200 };
        String[] messages = {
                "application/maxsite",
                "maxsite/plugins",
                "maxsite/templates",
                "maxsite/common"
        };

        attempt.incrementAndGet();

        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                for (String message : messages) {
                    Pattern pattern = Pattern.compile(message);
                    parser.configure(pattern, 0);
                    if (parser.parse(body)) {
                        successAttempt.incrementAndGet();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
