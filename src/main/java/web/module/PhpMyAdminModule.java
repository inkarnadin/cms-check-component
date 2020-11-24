package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.http.GetRequest;
import web.module.annotation.Get;
import web.struct.Parser;
import web.db.phpmyadmin.PhpMyAdminVersionParser;
import web.struct.Connector;
import web.struct.Processor;
import web.http.Request;
import web.db.phpmyadmin.PhpMyAdminVersionProcessor;
import web.db.phpmyadmin.annotation.PhpMyAdmin;
import web.db.phpmyadmin.annotation.PhpMyAdminVersion;
import web.module.provider.PhpMyAdminProvider;

public class PhpMyAdminModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);
        bind(Parser.class).annotatedWith(PhpMyAdminVersion.class).to(PhpMyAdminVersionParser.class);
        bind(Processor.class).annotatedWith(PhpMyAdminVersion.class).to(PhpMyAdminVersionProcessor.class);

        bind(Connector.class).annotatedWith(PhpMyAdmin.class).toProvider(PhpMyAdminProvider.class);
    }

}