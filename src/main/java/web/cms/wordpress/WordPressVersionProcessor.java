package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.printer.Printer;
import web.struct.Destination;
import web.struct.assignment.VersionAssigner;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WordPressVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionList).prepare(host);
        versionAnalyzer.checkViaMainPageMetaTag(new Pattern[] {
                Pattern.compile("<meta name=\"[gG]enerator\" content=\"WordPress\\s(.*?)\" />")
        });
        versionAnalyzer.checkViaSinceScript(Pattern.compile("@since\\s(.*?)\\s"), new String[] {
                "wp-includes/js/admin-bar.js",
                "wp-includes/js/api-request.js",
                "wp-includes/js/autosave.js",
                "wp-includes/js/comment-reply.js",
                "wp-includes/js/customize-base.js",
                "wp-includes/js/customize-preview.js",
                "wp-includes/js/customize-preview-nav-menus.js",
                "wp-includes/js/customize-preview-widgets.js",
                "wp-includes/js/customize-selective-refresh.js",
                "wp-includes/js/heartbeat.js",
                "wp-includes/js/media-audiovideo.js",
                "wp-includes/js/media-grid.js",
                "wp-includes/js/media-views.js",
                "wp-includes/js/wp-auth-check.js",
                "wp-includes/js/wp-backbone.js",
                "wp-includes/js/wp-embed.js",
                "wp-includes/js/wp-emoji.js",
                "wp-includes/js/wp-emoji-loader.js",
                "wp-includes/js/wp-pointer.js",
        }, false);

        assign(destination, versionList);
        printer.print(destination);
    }

}
