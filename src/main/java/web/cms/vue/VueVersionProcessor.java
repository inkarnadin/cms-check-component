package web.cms.vue;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.JsScriptDissector;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class VueVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.VUE_JS;

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        String[] paths = JsScriptDissector.dissect(host, request);
        if (paths.length != 0) {
            VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination).prepare(host, cmsType);
            versionAnalyzer.checkViaSinceScript(Pattern.compile("Vue\\.js v(.*)"), paths, true);
        }
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
