package web.env.phpmyadmin;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import web.analyzer.check.ExtendEnvironmentAnalyzer;
import web.cms.CMSType;
import web.env.AbstractEnvironmentProcessor;
import web.env.EnvType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class PhpMyAdminCheckProcessor extends AbstractEnvironmentProcessor {

    private static final EnvType envType = EnvType.PHP_MY_ADMIN;

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    @SneakyThrows
    public void process() {
        ExtendEnvironmentAnalyzer extendEnvironmentAnalyzer = new ExtendEnvironmentAnalyzer(request, parser, destination).prepare(host, envType);
        extendEnvironmentAnalyzer.checkPhpMyAdmin(new String[] {
                "phpmyadmin/doc/html/index.html",
                "phpmyadmin/Documentation.html",
                "myadmin/Documentation.html"
        });
        System.out.println(destination.fetch().get(0));
    }

}
