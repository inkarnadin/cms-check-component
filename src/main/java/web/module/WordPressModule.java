package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.wordpress.WordPressPluginProcessor;
import web.cms.wordpress.WordPressPluginRequest;
import web.cms.wordpress.WordPressExtensionSource;
import web.cms.wordpress.annotation.WordPress;
import web.cms.wordpress.annotation.WordPressPlugin;
import web.module.provider.WordPressProvider;
import web.struct.Connector;
import web.struct.Processor;
import web.struct.Request;
import web.struct.Source;

public class WordPressModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(WordPressPlugin.class).to(WordPressPluginRequest.class);
        bind(Source.class).annotatedWith(WordPressPlugin.class).to(WordPressExtensionSource.class);
        bind(Processor.class).annotatedWith(WordPressPlugin.class).to(WordPressPluginProcessor.class);

        bind(Connector.class).annotatedWith(WordPress.class).toProvider(WordPressProvider.class);
    }

}
